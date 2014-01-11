package com.fmdp.csvuserimport.portlet;



import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


public class ConfigurationActionImpl implements ConfigurationAction {
	private static Log _log = LogFactoryUtil.getLog(ConfigurationActionImpl.class);
	
    public void processAction(PortletConfig portletConfig,
            ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {
 
        String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
        if (!cmd.equals(Constants.UPDATE)) {
            return;
        }
		
        String tabs2 = ParamUtil.getString(actionRequest, "tabs2", "basic-csv");
        String csvSeparator = null;
        String maleCsvStatus = null;
        String jobtitleCsvStatus = null;
        String birthdayCsvStatus = null;
        String birthdayCsvOptions = null;
        String customFields = "";


        if (tabs2.equals("basic-csv")) {
	        csvSeparator = ParamUtil.getString(actionRequest, "csvSeparator", "EXCEL_NORTH_EUROPE_PREFERENCE");
	        maleCsvStatus = ParamUtil.getString(actionRequest, "maleCsvStatus", "ignore");
	        jobtitleCsvStatus = ParamUtil.getString(actionRequest, "jobtitleCsvStatus", "ignore");
	        birthdayCsvStatus = ParamUtil.getString(actionRequest, "birthdayCsvStatus", "ignore");
	        birthdayCsvOptions = ParamUtil.getString(actionRequest, "birthdayCsvOptions", "dd-MM-yyyy");
	        if(_log.isDebugEnabled()) {
	        	_log.debug("csvSeparator " + csvSeparator);
	        	_log.debug("maleCsvStatus " + maleCsvStatus);
	        	_log.debug("jobtitleCsvStatus " + jobtitleCsvStatus);
	        	_log.debug("birthdayCsvStatus " + birthdayCsvStatus);
	        	_log.debug("birthdayCsvOptions " + birthdayCsvOptions);
	        }
        } else {
        	customFields = ParamUtil.getString(actionRequest, "customFields", "");
	        if(_log.isDebugEnabled()) {
	        	_log.debug("customFields " + customFields);
	        }
        }
 try {
        String portletResource = ParamUtil.getString(actionRequest,"portletResource");
        PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
        if (tabs2.equals("basic-csv")) {
	        preferences.setValue("csvSeparator", csvSeparator);
	        preferences.setValue("maleCsvStatus", maleCsvStatus);
	        preferences.setValue("jobtitleCsvStatus", jobtitleCsvStatus);
	        preferences.setValue("birthdayCsvStatus", birthdayCsvStatus);
	        preferences.setValue("birthdayCsvOptions", birthdayCsvOptions);
        } else {
        	preferences.setValue("customFields", customFields);
        }
        preferences.store();
 
        SessionMessages.add(actionRequest, "success");
        SessionMessages.add(
        		                actionRequest,
        		                portletConfig.getPortletName() +
        		                SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
        		                portletResource);
        
 	}catch (Exception e){
	   e.printStackTrace();
	        }
    }
 
    public String render(PortletConfig portletConfig,
            RenderRequest renderRequest, RenderResponse renderResponse)
            throws Exception {
 
        return "/configuration.jsp";
    }
}
