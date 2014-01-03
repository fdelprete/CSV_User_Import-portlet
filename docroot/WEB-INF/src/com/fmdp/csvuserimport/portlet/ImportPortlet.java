package com.fmdp.csvuserimport.portlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.fmdp.csvuserimport.portlet.model.CsvUserBean;

/**
 * @author Filippo Maria Del Prete
 * 
 */
public class ImportPortlet extends MVCPortlet {
	/**
	 * 
	 */
	private static Log _log = LogFactoryUtil.getLog(ImportPortlet.class);

	/**
	 * @param actionRequest
	 * @param actionResponse
	 * @throws PortletException
	 * @throws IOException
	 */
	public void uploadCsv(javax.portlet.ActionRequest actionRequest,
			javax.portlet.ActionResponse actionResponse)
			throws PortletException, IOException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("We are in try");
			}
			
			UploadPortletRequest uploadRequest = PortalUtil
					.getUploadPortletRequest(actionRequest);
			if (_log.isDebugEnabled()) {
				_log.debug("Size: " + uploadRequest.getSize("fileName"));
			}
			if (uploadRequest.getSize("fileName") == 0) {
				throw new IOException("File size is 0");
			}
			Long roleId = ParamUtil.getLong(uploadRequest, "roleId");
			
			String sourceFileName = uploadRequest.getFileName("fileName");
			File file = uploadRequest.getFile("fileName");

			if (_log.isDebugEnabled()) {
				_log.debug("File name:" + sourceFileName);
			}
			UserServiceImpl usi = UserServiceImpl.getInstance();
			int count = 1;
			int count_good = 0;

			if (_log.isInfoEnabled()) {
				_log.info("##### Started importing #####");
				_log.info("roleId " + ParamUtil.getLong(uploadRequest, "roleId"));
			}
			UserCacheEngine userCacheEngine = UserCacheEngine.getInstance();
			if (_log.isInfoEnabled()) {
				_log.info("Now we're going to add users to portal");
			}
			List<CsvUserBean> users = userCacheEngine.getUsers(actionRequest,
					file.getPath());
			List<CsvUserBean> usersBad = new ArrayList<CsvUserBean>();
			for (CsvUserBean user : users) {
				if (_log.isInfoEnabled()) {
					_log.info("Processing " + count + " user. "
							+ user.getFirstName() + " " + user.getLastName());
				}
				count = count + 1;
				usi.addUser(actionRequest, user, roleId);
				if (!user.getImpStatus().equals("User imported.")) {
					if (_log.isInfoEnabled()) {
						_log.info(" User not added to portal");
					}
					usersBad.add(user);
				} else {
					if (_log.isInfoEnabled()) {
						_log.info(" User added to portal");
					}
					count_good = count_good + 1;
				}
			}
			if (_log.isInfoEnabled()) {
				_log.info(users.size() + " Users were read from the CSV file");
				_log.info(count_good + " Users were added to portal.");
				_log.info("##### Finished importing. #####");
			}

			SessionMessages.add(actionRequest, "success");
			/* we're using a session variable to hold all the beans of the CSV users
			 * for very large import this is not very good
			 * TODO: split the file or don't use memory but a file to write the status of
			 * the imported user
			 */
			actionRequest.setAttribute("utenti", usersBad);
			/* the total row number is utenti.size()
			 * 
			 */
			actionRequest.setAttribute("count_good", count_good);

		} catch (NullPointerException e) {
			_log.error("Error: " + e);
			SessionMessages.add(actionRequest, "error");
		}

		catch (IOException e1) {
			_log.error("Error Reading The File.");
			SessionMessages.add(actionRequest, "error");
		}

	}

}