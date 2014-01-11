package com.fmdp.csvuserimport.portlet;

import java.util.ArrayList;
import java.util.List;
import javax.portlet.ActionRequest;
import com.fmdp.csvuserimport.portlet.model.CsvUserBean;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */
public class UserCacheEngine {
	
	private static Log _log = LogFactoryUtil.getLog(UserCacheEngine.class);

	private UserCSVReader userReader = UserCSVReader.getInstance();

	private List<CsvUserBean> users = new ArrayList<CsvUserBean>();

	private static UserCacheEngine INSTANCE = new UserCacheEngine();

	private UserCacheEngine() {
		init();
	}

	private void init() {
	//	_log.info("Initialising users.");
	//	users = userReader.readUsers();
	}

	public static UserCacheEngine getInstance() {
		return INSTANCE;
	}

	public List<CsvUserBean> getUsers(ActionRequest actionRequest, String TheFile) {
		if (_log.isDebugEnabled()){
			_log.debug("Initialising users.");
		}
		users = userReader.readUsers(actionRequest, TheFile);
		return users;
	}

	public void setUsers(List<CsvUserBean> users) {
		this.users = users;
	}
	
}
