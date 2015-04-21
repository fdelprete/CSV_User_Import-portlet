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
import com.fmdp.csvuserimport.portlet.model.CsvOrgBean;

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
			if (uploadRequest.getSize("fileName") == 0) {
				throw new IOException("UploadCsv-File size is 0");
			}
			
			String sourceFileName = uploadRequest.getFileName("fileName");
			File file = uploadRequest.getFile("fileName");

			if (_log.isDebugEnabled()) {
				_log.debug("File name:" + sourceFileName);
			}

			String action = ParamUtil.getString(uploadRequest, "import_type");
			_log.info("action " + action);

			actionRequest.setAttribute("import_type", ParamUtil.getString(uploadRequest, "import_type"));

			if (action.equalsIgnoreCase("O")) {
				OrgServiceImpl osi = OrgServiceImpl.getInstance();
				int count = 1;
				int count_good = 0;

				if (_log.isDebugEnabled()) {
					_log.debug("##### Started importing #####");
				}

				OrgCacheEngine orgCacheEngine = OrgCacheEngine.getInstance();

				if (_log.isDebugEnabled()) {
					_log.debug("Now we're going to add users to portal");
				}
				List<CsvOrgBean> orgs = orgCacheEngine.getOrgs(actionRequest,
						file.getPath());
				if (orgs != null) {
					List<CsvOrgBean> orgsBad = new ArrayList<CsvOrgBean>();
					for (CsvOrgBean org : orgs) {
						if (_log.isDebugEnabled()) {
							_log.debug("Processing " + count + " orgs. "
									+ org.getName());
						}
						count = count + 1;
						osi.addOrg(actionRequest, org);
						if (!org.getImpStatus().equals("Organization imported.")) {
							if (_log.isDebugEnabled()) {
								_log.debug(" Organization not added to portal");
							}
							orgsBad.add(org);
						} else {
							if (_log.isDebugEnabled()) {
								_log.debug(" Organization added to portal");
							}
							count_good = count_good + 1;
						}
					}
					if (_log.isDebugEnabled()) {
						_log.debug(orgs.size() + " Organizations were read from the CSV file");
						_log.debug(count_good + " Organizations were added to portal.");
						_log.debug("##### Finished importing. #####");
					}
		
					SessionMessages.add(actionRequest, "success");
					/* we're using a session variable to hold all the beans of the CSV users
					 * for very large import this is not very good
					 * TODO: split the file or don't use memory but a file to write the status of
					 * the imported user
					 */
					actionRequest.setAttribute("utenti", orgsBad);
					/* the total row number is utenti.size()
					 * 
					 */
					actionRequest.setAttribute("count_good", count_good);
				} else {
					SessionMessages.add(actionRequest, "error");
				}

			} else {
				
				Long roleId = ParamUtil.getLong(uploadRequest, "roleId");
				Long organizationId = ParamUtil.getLong(uploadRequest, "organizationId");
				
				UserServiceImpl usi = UserServiceImpl.getInstance();
				int count = 1;
				int count_good = 0;

				if (_log.isDebugEnabled()) {
					_log.debug("roleId " + roleId);
					_log.debug("organizationId " + organizationId);
					_log.debug("##### Started importing #####");
				}

				UserCacheEngine userCacheEngine = UserCacheEngine.getInstance();

				if (_log.isDebugEnabled()) {
					_log.debug("Now we're going to add users to portal");
				}
				List<CsvUserBean> users = userCacheEngine.getUsers(actionRequest,
						file.getPath());
				if (users != null) {
					List<CsvUserBean> usersBad = new ArrayList<CsvUserBean>();
					for (CsvUserBean user : users) {
						if (_log.isDebugEnabled()) {
							_log.debug("Processing " + count + " user. "
									+ user.getFirstName() + " " + user.getLastName());
						}
						count = count + 1;
						usi.addUser(actionRequest, user, roleId, organizationId);
						if (!user.getImpStatus().equals("User imported.")) {
							if (_log.isDebugEnabled()) {
								_log.debug(" User not added to portal");
							}
							usersBad.add(user);
						} else {
							if (_log.isDebugEnabled()) {
								_log.debug(" User added to portal");
							}
							count_good = count_good + 1;
						}
					}
					if (_log.isDebugEnabled()) {
						_log.debug(users.size() + " Users were read from the CSV file");
						_log.debug(count_good + " Users were added to portal.");
						_log.debug("##### Finished importing. #####");
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
					
				} else {
					SessionMessages.add(actionRequest, "error");
				}

			}
						
		} catch (NullPointerException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Error: " + e);
			}
			SessionMessages.add(actionRequest, "error");
		}

		catch (IOException e1) {
			if (_log.isErrorEnabled()) {
				_log.error("Error Reading The File. Error: " + e1);
			}
			SessionMessages.add(actionRequest, "error");
		}

	}

	public void uploadUserCsv(javax.portlet.ActionRequest actionRequest,
			javax.portlet.ActionResponse actionResponse)
			throws PortletException, IOException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("We are in try");
			}
			
			UploadPortletRequest uploadRequest = PortalUtil
					.getUploadPortletRequest(actionRequest);
			if (uploadRequest.getSize("fileName") == 0) {
				throw new IOException("UploadUserCsv-File size is 0");
			}
			
			actionRequest.setAttribute("import_type", ParamUtil.getString(uploadRequest, "import_type"));
			
			Long roleId = ParamUtil.getLong(uploadRequest, "roleId");
			Long organizationId = ParamUtil.getLong(uploadRequest, "organizationId");
			
			String sourceFileName = uploadRequest.getFileName("fileName");
			File file = uploadRequest.getFile("fileName");

			if (_log.isDebugEnabled()) {
				_log.debug("File name:" + sourceFileName);
			}
			UserServiceImpl usi = UserServiceImpl.getInstance();
			int count = 1;
			int count_good = 0;

			if (_log.isDebugEnabled()) {
				_log.debug("roleId " + roleId);
				_log.debug("organizationId " + organizationId);
				_log.debug("##### Started importing #####");
			}

			UserCacheEngine userCacheEngine = UserCacheEngine.getInstance();

			if (_log.isDebugEnabled()) {
				_log.debug("Now we're going to add users to portal");
			}
			List<CsvUserBean> users = userCacheEngine.getUsers(actionRequest,
					file.getPath());
			if (users != null) {
				List<CsvUserBean> usersBad = new ArrayList<CsvUserBean>();
				for (CsvUserBean user : users) {
					if (_log.isDebugEnabled()) {
						_log.debug("Processing " + count + " user. "
								+ user.getFirstName() + " " + user.getLastName());
					}
					count = count + 1;
					usi.addUser(actionRequest, user, roleId, organizationId);
					if (!user.getImpStatus().equals("User imported.")) {
						if (_log.isDebugEnabled()) {
							_log.debug(" User not added to portal");
						}
						usersBad.add(user);
					} else {
						if (_log.isDebugEnabled()) {
							_log.debug(" User added to portal");
						}
						count_good = count_good + 1;
					}
				}
				if (_log.isDebugEnabled()) {
					_log.debug(users.size() + " Users were read from the CSV file");
					_log.debug(count_good + " Users were added to portal.");
					_log.debug("##### Finished importing. #####");
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
				
			} else {
				SessionMessages.add(actionRequest, "error");
			}
		} catch (NullPointerException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Error: " + e);
			}
			SessionMessages.add(actionRequest, "error");
		}

		catch (IOException e1) {
			if (_log.isErrorEnabled()) {
				_log.error("Error Reading The File. Error: " + e1);
			}
			SessionMessages.add(actionRequest, "error");
		}

	}
	public void uploadOrgCsv(javax.portlet.ActionRequest actionRequest,
			javax.portlet.ActionResponse actionResponse)
			throws PortletException, IOException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("We are in try");
			}
			
			UploadPortletRequest uploadRequest = PortalUtil
					.getUploadPortletRequest(actionRequest);
			if (uploadRequest.getSize("fileName") == 0) {
				throw new IOException("UploadOrgCsv-File size is 0");
			}
			
			actionRequest.setAttribute("import_type", ParamUtil.getString(uploadRequest, "import_type"));
			
			String sourceFileName = uploadRequest.getFileName("fileName");
			File file = uploadRequest.getFile("fileName");

			if (_log.isDebugEnabled()) {
				_log.debug("File name:" + sourceFileName);
			}
			
			OrgServiceImpl osi = OrgServiceImpl.getInstance();
			int count = 1;
			int count_good = 0;

			if (_log.isDebugEnabled()) {
				_log.debug("##### Started importing #####");
			}

			OrgCacheEngine orgCacheEngine = OrgCacheEngine.getInstance();

			if (_log.isDebugEnabled()) {
				_log.debug("Now we're going to add users to portal");
			}
			List<CsvOrgBean> orgs = orgCacheEngine.getOrgs(actionRequest,
					file.getPath());
			if (orgs != null) {
				List<CsvOrgBean> orgsBad = new ArrayList<CsvOrgBean>();
				for (CsvOrgBean org : orgs) {
					if (_log.isDebugEnabled()) {
						_log.debug("Processing " + count + " orgs. "
								+ org.getName());
					}
					count = count + 1;
					osi.addOrg(actionRequest, org);
					if (!org.getImpStatus().equals("Organization imported.")) {
						if (_log.isDebugEnabled()) {
							_log.debug(" Organization not added to portal");
						}
						orgsBad.add(org);
					} else {
						if (_log.isDebugEnabled()) {
							_log.debug(" Organization added to portal");
						}
						count_good = count_good + 1;
					}
				}
				if (_log.isDebugEnabled()) {
					_log.debug(orgs.size() + " Organizations were read from the CSV file");
					_log.debug(count_good + " Organizations were added to portal.");
					_log.debug("##### Finished importing. #####");
				}
	
				SessionMessages.add(actionRequest, "success");
				/* we're using a session variable to hold all the beans of the CSV users
				 * for very large import this is not very good
				 * TODO: split the file or don't use memory but a file to write the status of
				 * the imported user
				 */
				actionRequest.setAttribute("utenti", orgsBad);
				/* the total row number is utenti.size()
				 * 
				 */
				actionRequest.setAttribute("count_good", count_good);
			} else {
				SessionMessages.add(actionRequest, "error");
			}
		} catch (NullPointerException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Error: " + e);
			}
			SessionMessages.add(actionRequest, "error");
		}

		catch (IOException e1) {
			if (_log.isErrorEnabled()) {
				_log.error("Error Reading The File. Error: " + e1);
			}
			SessionMessages.add(actionRequest, "error");
		}

	}

}