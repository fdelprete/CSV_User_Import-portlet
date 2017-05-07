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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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


        String address1CsvStatus = null;
        String address2CsvStatus = null;
        String cityCsvStatus = null;
        String zipCsvStatus = null;
        String stateCsvStatus = null;
        String countryCsvStatus = null;
        String phoneCsvStatus = null;
        String faxCsvStatus = null;
        String commentsCsvStatus = null;

        if (tabs2.equals("basic-csv")) {
	        csvSeparator = ParamUtil.getString(actionRequest, "csvSeparator", "EXCEL_NORTH_EUROPE_PREFERENCE");
	        maleCsvStatus = ParamUtil.getString(actionRequest, "maleCsvStatus", "ignore");
	        jobtitleCsvStatus = ParamUtil.getString(actionRequest, "jobtitleCsvStatus", "ignore");
	        birthdayCsvStatus = ParamUtil.getString(actionRequest, "birthdayCsvStatus", "ignore");
	        birthdayCsvOptions = ParamUtil.getString(actionRequest, "birthdayCsvOptions", "dd-MM-yyyy");
	        
	        address1CsvStatus = ParamUtil.getString(actionRequest, "address1CsvStatus","ignore");
	        address2CsvStatus = ParamUtil.getString(actionRequest, "address2CsvStatus","ignore");
	        cityCsvStatus = ParamUtil.getString(actionRequest, "cityCsvStatus","ignore");
	        zipCsvStatus = ParamUtil.getString(actionRequest, "zipCsvStatus","ignore");
	        stateCsvStatus = ParamUtil.getString(actionRequest, "stateCsvStatus","ignore");
	        countryCsvStatus = ParamUtil.getString(actionRequest, "countryCsvStatus","ignore");
	        phoneCsvStatus = ParamUtil.getString(actionRequest, "phoneCsvStatus","ignore");
	        faxCsvStatus = ParamUtil.getString(actionRequest, "faxCsvStatus","ignore");
	        commentsCsvStatus = ParamUtil.getString(actionRequest, "commentsCsvStatus","ignore");
	        
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
	        preferences.setValue("address1CsvStatus", address1CsvStatus);
	        preferences.setValue("address2CsvStatus", address2CsvStatus);
	        preferences.setValue("cityCsvStatus", cityCsvStatus);
	        preferences.setValue("zipCsvStatus", zipCsvStatus);
	        preferences.setValue("stateCsvStatus", stateCsvStatus);
	        preferences.setValue("countryCsvStatus", countryCsvStatus);
	        preferences.setValue("phoneCsvStatus", phoneCsvStatus);
	        preferences.setValue("faxCsvStatus", faxCsvStatus);
	        preferences.setValue("commentsCsvStatus", commentsCsvStatus);

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

	@Override
	public void include(PortletConfig arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
