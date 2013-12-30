<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="com.liferay.portal.kernel.util.Validator"%>
<%@ page import="com.liferay.portal.kernel.util.Constants" %> 

<liferay-theme:defineObjects />
<portlet:defineObjects />
<%

PortletPreferences preferences = renderRequest.getPreferences();
 
String portletResource = ParamUtil.getString(request, "portletResource");
 
if (Validator.isNotNull(portletResource)) {
    preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String csvSeparator = preferences.getValue("csvSeparator",";");
String maleCsvStatus = preferences.getValue("maleCsvStatus","ignore");
String jobtitleCsvStatus = preferences.getValue("jobtitleCsvStatus","ignore");
String birthdayCsvStatus = preferences.getValue("birthdayCsvStatus","ignore");
String birthdayCsvOptions = preferences.getValue("birthdayCsvOptions","dd-MM-yyyy");

%>

<liferay-portlet:actionURL var="configurationURL" portletConfiguration= "true"/>
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />
<aui:form method="post" action="<%=configurationURL.toString()%>"
      name= "<portlet:namespace />fm" > 
		<aui:input name="<%=Constants.CMD%>" type="hidden" value="<%=Constants.UPDATE%>" />
		<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
       <aui:fieldset cssClass='<%= renderResponse.getNamespace() + "prefList" %>'>
             <aui:select label="csv-separator"  name="csvSeparator" showEmptyOption="false" >
	             <aui:option label='semicolon' selected='<%= csvSeparator.equals(";") %>' value=";" />
	             <aui:option label='comma' selected='<%= csvSeparator.equals(",") %>' value="," />             
             </aui:select>
	
	<table class="table table-bordered table-hover table-striped"> 
		<thead class="table-columns">
			<tr> 
				<th class="table-first-header"><liferay-ui:message key="csv-field" /></th>
				<th><liferay-ui:message key="use-ignore" /></th>
				<th class="table-last-header"><liferay-ui:message key="other-settings" /></th>
			</tr>
		</thead>
		<tbody class="table-data">
			<tr>
				<td class="table-cell  first">male</td>
			    <td class="table-cell">
			    	<aui:select label="" name="maleCsvStatus" showEmptyOption="false" >
			            <aui:option label='ignore' selected='<%= maleCsvStatus.equals("ignore") %>' value="ignore" />
			            <aui:option label='optional' selected='<%= maleCsvStatus.equals("optional") %>' value="optional" />             
             		</aui:select>
			    </td>
			    <td  class="table-cell  last"></td>
		    </tr>
		    <tr>
				<td class="table-cell  first">jobTitle</td>
			    <td class="table-cell">
			    	<aui:select label="" name="jobtitleCsvStatus" showEmptyOption="false" >
			            <aui:option label='ignore' selected='<%= jobtitleCsvStatus.equals("ignore") %>' value="ignore" />
			            <aui:option label='optional' selected='<%= jobtitleCsvStatus.equals("optional") %>' value="optional" />             
             		</aui:select>
             	</td>
			    <td  class="table-cell  last"></td>
		    </tr>
			<tr>
				<td class="table-cell  first">birthday</td>
			    <td class="table-cell">
			    	<aui:select label="" name="birthdayCsvStatus" showEmptyOption="false" >
			            <aui:option label='ignore' selected='<%= birthdayCsvStatus.equals("ignore") %>' value="ignore" />
			            <aui:option label='optional' selected='<%= birthdayCsvStatus.equals("optional") %>' value="optional" />             
             		</aui:select>
             	</td>
			    <td class="table-cell  last">
			    	<aui:select label="date-format" name="birthdayCsvOptions" helpMessage="select-date-format" showEmptyOption="false" >
			            <aui:option label='dd-MM-yyyy' selected='<%= birthdayCsvOptions.equals("dd-MM-yyyy") %>' value="dd-MM-yyyy" />
			            <aui:option label='dd/MM/yyyy' selected='<%= birthdayCsvOptions.equals("dd/MM/yyyy") %>' value="dd/MM/yyyy" />             
			            <aui:option label='MM-dd-yyyy' selected='<%= birthdayCsvOptions.equals("MM-dd-yyyy") %>' value="MM-dd-yyyy" />
			            <aui:option label='MM/dd/yyyy' selected='<%= birthdayCsvOptions.equals("MM/dd/yyyy") %>' value="MM/dd/yyyy" />
			            <aui:option label='yyyyMMdd' selected='<%= birthdayCsvOptions.equals("yyyyMMdd") %>' value="yyyyMMdd" />
             		</aui:select></td>
		    </tr>
	    </tbody>
     </table>
             <aui:button-row>
                    <aui:button type="submit" />
             </aui:button-row>
       </aui:fieldset>
</aui:form>
