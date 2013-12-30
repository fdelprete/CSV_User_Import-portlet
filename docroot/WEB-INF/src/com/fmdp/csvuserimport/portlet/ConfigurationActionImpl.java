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
 
        String csvSeparator = ParamUtil.getString(actionRequest, "csvSeparator", ";");
        String maleCsvStatus = ParamUtil.getString(actionRequest, "maleCsvStatus", "ignore");
        String jobtitleCsvStatus = ParamUtil.getString(actionRequest, "jobtitleCsvStatus", "ignore");
        String birthdayCsvStatus = ParamUtil.getString(actionRequest, "birthdayCsvStatus", "ignore");
        String birthdayCsvOptions = ParamUtil.getString(actionRequest, "birthdayCsvOptions", "dd-MM-yyyy");
        if(_log.isInfoEnabled()) {
        	_log.info("csvSeparator " + csvSeparator);
        	_log.info("maleCsvStatus " + maleCsvStatus);
        	_log.info("jobtitleCsvStatus " + jobtitleCsvStatus);
        	_log.info("birthdayCsvStatus " + birthdayCsvStatus);
        	_log.info("birthdayCsvOptions " + birthdayCsvOptions);
        }
 try {
        String portletResource = ParamUtil.getString(actionRequest,"portletResource");
        PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
 
        preferences.setValue("csvSeparator", csvSeparator);
        preferences.setValue("maleCsvStatus", maleCsvStatus);
        preferences.setValue("jobtitleCsvStatus", jobtitleCsvStatus);
        preferences.setValue("birthdayCsvStatus", birthdayCsvStatus);
        preferences.setValue("birthdayCsvOptions", birthdayCsvOptions);
        
        preferences.store();
 
        //SessionMessages.add(actionRequest, portletConfig.getPortletName()+ ".doConfigure");
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
