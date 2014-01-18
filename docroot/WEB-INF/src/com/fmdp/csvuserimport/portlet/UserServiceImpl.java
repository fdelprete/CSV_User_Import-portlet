package com.fmdp.csvuserimport.portlet;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.fmdp.csvuserimport.portlet.model.CsvUserBean;

import org.apache.commons.beanutils.PropertyUtils;

public class UserServiceImpl {
	private static Log _log = LogFactoryUtil.getLog(UserServiceImpl.class);

	private static UserServiceImpl INSTANCE = new UserServiceImpl();

	private UserServiceImpl() {
	}

	public static UserServiceImpl getInstance() {
		return INSTANCE;
	}

	public User addUser(ActionRequest request,
			CsvUserBean userBean, Long roleId, Long organizationId) {
		User user = addLiferayUser(request, userBean, roleId, organizationId);
		if (user != null) {
			userBean.setLiferayUserId(user.getUserId());
			if (_log.isDebugEnabled()){
				_log.debug("User: " + userBean.getFirstName() + " " + userBean.getLastName() + " was added to liferay.");
			}
		} else {
			if (_log.isDebugEnabled()){
				_log.debug("User: " + userBean.getFirstName() + " " + userBean.getLastName() + " not added to liferay: " + userBean.getImpStatus());
			}
		}
		return user;
	}

	private User addLiferayUser(ActionRequest request,
			CsvUserBean userBean, Long roleId, Long organizationId) {
		User user = null;
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			long creatorUserId = themeDisplay.getUserId(); 
			long companyId = themeDisplay.getCompanyId(); 
			boolean autoPassword = false;
			String password1 = userBean.getPassword();
			String password2 = userBean.getPassword();
			boolean autoScreenName = false;
			String screenName = userBean.getUsername();
			String emailAddress = userBean.getEmail();
			long facebookId = 0;
			String openId = "";
			Locale locale = themeDisplay.getLocale();
			String firstName = userBean.getFirstName();
			String middleName = "";
			String lastName = userBean.getLastName();

			int prefixId = 0;

			int suffixId = 0;
			boolean male = userBean.isMale();

			int birthdayMonth = 1;
			int birthdayDay = 1;
			int birthdayYear = 1970;

			if (userBean.getBirthday() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(userBean.getBirthday());
				birthdayMonth = cal.get(Calendar.MONTH);
				birthdayDay = cal.get(Calendar.DAY_OF_MONTH);
				birthdayYear = cal.get(Calendar.YEAR);
			}

			String jobTitle = "";
			if (userBean.getJobTitle() != null) {
				jobTitle = userBean.getJobTitle();
			}

			long[] groupIds = null;

			long[] organizationIds = null;
			if (organizationId != 0 ) {
				organizationIds = new long[1];
				organizationIds[0] = organizationId;
			}
			
			long[] roleIds = null;
			if (roleId != 0 ) {
				roleIds = new long[1];
				roleIds[0] = roleId;
			}

			long[] userGroupIds = null;

			boolean sendEmail = false;

			ServiceContext serviceContext = ServiceContextFactory.getInstance(request);
			user = null;
			boolean userbyscreeenname_exists = true;
			boolean userbyemail_exists = true;

			try {
				user = UserLocalServiceUtil.getUserByScreenName(companyId, screenName);
			} catch (NoSuchUserException nsue) {
				userbyscreeenname_exists = false;
			}
			try {
				user = UserLocalServiceUtil.getUserByEmailAddress(companyId, emailAddress);
			} catch (NoSuchUserException nsue) {
				userbyemail_exists = false;
			}

			if((!userbyscreeenname_exists) & (!userbyemail_exists))
			{
				user = UserLocalServiceUtil.addUser(creatorUserId,
						companyId,
						autoPassword,
						password1,
						password2,
						autoScreenName,
						screenName,
						emailAddress,
						facebookId,
						openId,
						locale,
						firstName,
						middleName,
						lastName,
						prefixId,
						suffixId,
						male,
						birthdayMonth,
						birthdayDay,
						birthdayYear,
						jobTitle,
						groupIds,
						organizationIds,
						roleIds,
						userGroupIds,
						sendEmail,
						serviceContext);

				user.setPasswordReset(false);
				user = UserLocalServiceUtil.updateUser(user);

				UserLocalServiceUtil.updateStatus(user.getUserId(), WorkflowConstants.STATUS_APPROVED);
				Indexer indexer = IndexerRegistryUtil.getIndexer(User.class);

				indexer.reindex(user);
				userBean.setImpStatus("User imported.");
				// the user is created: here we save the custom fields
				saveCustomFields(request, user, userBean);
			} else {
				String msg_exists = "";
				if (userbyscreeenname_exists) {
					msg_exists = msg_exists + "Screen Name is not unique.";
				}
				if (userbyemail_exists) {
					msg_exists = msg_exists + " Email Address is not unique.";
				}

				userBean.setImpStatus(msg_exists);
				user = null;
			}
		} catch (PortalException e) {
			_log.error(e);
		} catch (SystemException e) {
			_log.error(e);
		}
		return user;
	}

	private boolean saveCustomFields(ActionRequest request, User user, CsvUserBean userBean){ 
		boolean retVal = false;
		PortletPreferences preferences;
		try {
			String portletInstanceId = (String) request.getAttribute(WebKeys.PORTLET_ID);

			/*TODO with the current user we have to check if he can use the expando fields
	  	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	    long userId = themeDisplay.getUserId(); 
	    long companyId = themeDisplay.getCompanyId(); 
	    User userAdmin = UserLocalServiceUtil.getUser(userId);
			 */
			preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletInstanceId);
			String customFields = preferences.getValue("customFields", "");
			String cFs[] = customFields.split(",");
			String beanFieldValue = "";
			String cfName = "";
			int k = 0;
			for (int j = 0; j < cFs.length; j++){
				k = j + 1;
				cfName = "cf" + k;
				try {
					beanFieldValue = (String) PropertyUtils.getProperty(userBean, cfName);
					user.getExpandoBridge().setAttribute(cFs[j], beanFieldValue);
					if (_log.isDebugEnabled()){
						_log.debug("User custom field: " + cFs[j] + " " + beanFieldValue);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

				retVal = true;
			}
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}

		return retVal;
	}

}