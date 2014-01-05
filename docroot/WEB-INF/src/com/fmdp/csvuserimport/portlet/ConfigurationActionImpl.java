package com.fmdp.csvuserimport.portlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
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
        String howmanyCf = "0";
        String cfName = "";
		int k = 0;
		String[] customFields = new String[20];

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId = themeDisplay.getUserId(); 
        //long companyId = themeDisplay.getCompanyId(); // default company
        User user = UserLocalServiceUtil.getUser(userId);
		Enumeration<String> attributeNames = user.getExpandoBridge().getAttributeNames();			
		List<String> listCf = Collections.list(attributeNames);

        if (tabs2.equals("basic-csv")) {
	        csvSeparator = ParamUtil.getString(actionRequest, "csvSeparator", ";");
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
        	howmanyCf = ParamUtil.getString(actionRequest, "howmanyCf", "0");
    		for (int j = 0; j < listCf.size() && j < 20; j++) {
    			k = j + 1;
    			cfName = "cF" + k;
    			customFields[j] = ParamUtil.getString(actionRequest, cfName, "");
    			 if(_log.isDebugEnabled()) {
    				 _log.debug(cfName + " " + customFields[j]);
    			 }
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
        	preferences.setValue("howmanyCf", howmanyCf);
    		for (int j = 0; j < listCf.size() && j < 20; j++) {
    			k = j + 1;
    			cfName = "cF" + k;
    			preferences.setValue(cfName,customFields[j]);
    		}        	
        }
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
