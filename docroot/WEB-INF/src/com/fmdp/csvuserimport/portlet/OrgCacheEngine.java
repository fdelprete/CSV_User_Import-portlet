package com.fmdp.csvuserimport.portlet;

import java.util.ArrayList;
import java.util.List;
import javax.portlet.ActionRequest;
import com.fmdp.csvuserimport.portlet.model.CsvOrgBean;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */
public class OrgCacheEngine {
	
	private static Log _log = LogFactoryUtil.getLog(OrgCacheEngine.class);

	private OrgCSVReader orgReader = OrgCSVReader.getInstance();

	private List<CsvOrgBean> orgs = new ArrayList<CsvOrgBean>();

	private static OrgCacheEngine INSTANCE = new OrgCacheEngine();

	private OrgCacheEngine() {
		init();
	}

	private void init() {
	//	_log.info("Initialising users.");
	//	users = userReader.readUsers();
	}

	public static OrgCacheEngine getInstance() {
		return INSTANCE;
	}

	public List<CsvOrgBean> getOrgs(ActionRequest actionRequest, String TheFile) {
		if (_log.isDebugEnabled()){
			_log.debug("Initialising organizations.");
		}
		orgs = orgReader.readOrgs(actionRequest, TheFile);
		return orgs;
	}

	public void setOrgs(List<CsvOrgBean> orgs) {
		this.orgs = orgs;
	}
	
}
