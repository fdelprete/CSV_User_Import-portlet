<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<%@ page import="com.liferay.portlet.expando.model.ExpandoBridge" %>
<%@ page import="com.liferay.portlet.expando.ColumnNameException" %><%@
page import="com.liferay.portlet.expando.ColumnTypeException" %><%@
page import="com.liferay.portlet.expando.DuplicateColumnNameException" %><%@
page import="com.liferay.portlet.expando.NoSuchColumnException" %><%@
page import="com.liferay.portlet.expando.ValueDataException" %><%@
page import="com.liferay.portlet.expando.model.CustomAttributesDisplay" %><%@
page import="com.liferay.portlet.expando.model.ExpandoColumn" %><%@
page import="com.liferay.portlet.expando.model.ExpandoColumnConstants" %><%@
page import="com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil" %><%@
page import="com.liferay.portlet.expando.service.permission.ExpandoColumnPermissionUtil" %><%@
page import="com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageWrapper" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %>
<%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="java.util.Enumeration" %>
<%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ObjectValuePair" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.model.*" %><%@
page import="com.liferay.portal.model.impl.*" %>
<%@ page import="java.io.Serializable" %>

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
String[] currentCustomFields = preferences.getValue("customFields","").split(",");

String tabs2 = ParamUtil.getString(request, "tabs2", "basic-csv");
String redirect = ParamUtil.getString(request, "redirect");
Enumeration<String> attributeNames = user.getExpandoBridge().getAttributeNames();			

%>

<liferay-portlet:actionURL var="configurationURL" portletConfiguration= "true"/>
<liferay-portlet:renderURL portletConfiguration="true" 
	var="configurationRenderURL">
	<!-- varImpl="configurationRenderURL" -->
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>
<aui:form method="post" action="<%=configurationURL.toString()%>"
      name= "fm" 
      onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCsvImport();" %>'>
		<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" /> 
		<aui:input name="<%=Constants.CMD%>" type="hidden" value="<%=Constants.UPDATE%>" />
		<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
       	<%
		String tabs1Names = "basic-csv";

		if (attributeNames.hasMoreElements()) {
			tabs1Names = tabs1Names.concat(",custom-csv");
		}
		%>
       	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		param="tabs2"
		url="<%= configurationRenderURL %>"
		/>
		<c:choose>
			<c:when test='<%= tabs2.equals("basic-csv") %>'>
		
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
				</aui:fieldset>
			</c:when>
			<c:when test='<%= tabs2.equals("custom-csv") %>'>
				<div class="alert alert-info">
					<liferay-ui:message key="custom-field-help" />
				</div>
			<%
			List<String> listCf = Collections.list(attributeNames);
			%>
	       	<aui:fieldset cssClass='<%= renderResponse.getNamespace() + "prefList" %>'>
	       	<aui:input name='customFields' type="hidden" value="<%= StringUtil.merge(currentCustomFields) %>" />
			<%

		// Left list

		List leftList = new ArrayList();

		for (String currentCf : currentCustomFields) {
			leftList.add(new KeyValuePair(currentCf, currentCf));
		}

		// Right list

		List rightList = new ArrayList();

		for (String availableCf : listCf) {
			if (!ArrayUtil.contains(currentCustomFields, availableCf)) {
				
				rightList.add(new KeyValuePair(availableCf, availableCf));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>
			
		<liferay-ui:input-move-boxes
			leftBoxName="currentCustomFields"
			leftList="<%= leftList %>"
			leftReorder="true"
			leftTitle="current"
			rightBoxName="availableCustomFields"
			rightList="<%= rightList %>"
			rightTitle="available"
		/>

	       	</aui:fieldset>

			</c:when>
		</c:choose>
<aui:script use="liferay-util-list-fields">
	Liferay.provide(
		window,
		'<portlet:namespace />saveCfs',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />customFields.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentCustomFields);
		},
		['liferay-util-list-fields']
	);
</aui:script>		
<aui:script>
	function <portlet:namespace />saveCsvImport() {
		if (document.<portlet:namespace />fm.<portlet:namespace />tabs2.value=='custom-csv')  {
			if  (document.<portlet:namespace />fm.<portlet:namespace />currentCustomFields.length <= 20) {
				<portlet:namespace />saveCfs();
				submitForm(document.<portlet:namespace />fm);
			} else {
				alert("Maximum number of imported custom fields is 20.");
			}
		} else {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>

        <aui:button-row>
        	<aui:button type="submit" />
        </aui:button-row>
       
</aui:form>
