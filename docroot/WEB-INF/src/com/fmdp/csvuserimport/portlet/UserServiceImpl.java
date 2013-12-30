package com.fmdp.csvuserimport.portlet;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */
import java.util.Calendar;
import java.util.Locale;
import javax.portlet.ActionRequest;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.fmdp.csvuserimport.portlet.model.CsvUserBean;


public class UserServiceImpl {
  /** The Constant _log. */
	private static Log _log = LogFactoryUtil.getLog(UserServiceImpl.class);

  /** Singleton instance. */
  private static UserServiceImpl INSTANCE = new UserServiceImpl();

  private UserServiceImpl() {
  }

  public static UserServiceImpl getInstance() {
    return INSTANCE;
  }

  public User addUser(final ActionRequest request,
                      final CsvUserBean userBean) {
    User user = addLiferayUser(request, userBean);
    if (user != null) {
	    userBean.setLiferayUserId(user.getUserId());
	    if (_log.isInfoEnabled()){
	    	_log.info("User: " + userBean.getFirstName() + " " + userBean.getLastName() + " was added to liferay.");
	    }
	} else {
		    if (_log.isInfoEnabled()){
		    	_log.info("User: " + userBean.getFirstName() + " " + userBean.getLastName() + " not added to liferay: " + userBean.getImpStatus());
		    }
    }
    return user;
  }

  private User addLiferayUser(final ActionRequest request,
                             final CsvUserBean userBean) {
    User user = null;
    try {
      ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

      long creatorUserId = themeDisplay.getUserId(); // default liferay user
      long companyId = themeDisplay.getCompanyId(); // default company
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
      long[] roleIds = null;
      String role = ParamUtil.getString(request, "roleId");
      if (role != "") {
    	  roleIds = new long[1];
    	  roleIds[0] = Long.valueOf(role).longValue();
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

 

}
