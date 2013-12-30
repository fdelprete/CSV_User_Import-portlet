<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator"%>
<%@ page import="javax.portlet.PortletPreferences"%>
<%@ page import="com.liferay.util.PwdGenerator"%>
<%@ page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.RoleConstants"%>
<%@ page import="com.fmdp.csvuserimport.portlet.model.CsvUserBean"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.model.Role"%>
<%@ page import="java.util.List" %>

<jsp:useBean id="utenti" class="java.util.ArrayList" 
             type="java.util.List" scope="request" />
<liferay-theme:defineObjects />
<portlet:defineObjects />
<%
String uploadProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
PortletPreferences preferences = renderRequest.getPreferences();
String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
    preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String csvSeparator = preferences.getValue("csvSeparator",";");
//long companyId = themeDisplay.getCompanyId();
List<Role> roles = RoleLocalServiceUtil.getRoles(company.getCompanyId());
String count_good = "";
Integer total_users = 0;
if (Validator.isNotNull(renderRequest.getAttribute("count_good"))) {
	count_good = renderRequest.getAttribute("count_good").toString();
	total_users = utenti.size() + Integer.parseInt(renderRequest.getAttribute("count_good").toString());
}
%>
 
<portlet:actionURL var="uploadCsvURL" name="uploadCsv">
	<portlet:param name="jspPage" value="/view.jsp" />	
</portlet:actionURL>
<liferay-ui:header cssClass="header-back-to" title="CSV User Import">
</liferay-ui:header>


<liferay-ui:upload-progress id="<%= uploadProgressId %>" message="uploading" redirect="<%= uploadCsvURL %>" />
<liferay-ui:success key="success" 
	message='<%= LanguageUtil.format(pageContext, "success-read-and-added", new Object [] {count_good, total_users}) %>' />
<liferay-ui:error key="expected-header-not-found-in-the-csv-file" message="Expected header not found in the CSV file." />
<liferay-ui:error key="error" message="Sorry, an error prevented the upload. Please try again." />
 
<aui:form action="<%= uploadCsvURL %>" enctype="multipart/form-data" method="post" >
	<div class="alert alert-info">
		<liferay-ui:message key="file-must-be-csv" />
		<liferay-ui:message key='<%= LanguageUtil.format(pageContext, "first-row-format", csvSeparator) %>'/>
	</div>
	<aui:fieldset cssClass='fieldset'>
		<aui:input type="file" name="fileName" size="75" helpMessage="load-csv-file"/>
		<br/>
		<br/>
		<aui:select label="role"  name="roleId" helpMessage="select-role" showEmptyOption="true" >
	             <%
	                    for (int i = 0; i < roles.size(); i++) {
	                    	Role role=(Role)roles.get(i);
	                    	if((role.getType()==RoleConstants.TYPE_REGULAR) && (!role.getTitle(themeDisplay.getLanguageId()).equals("Guest")) && (!role.getTitle(themeDisplay.getLanguageId()).equals("Owner"))) {
	             %>
	                    		<aui:option label='<%= role.getTitle(themeDisplay.getLanguageId()) + " - " + role.getTypeLabel()%>'
	                           		value="<%= role.getRoleId() %>" />
	             <%
	                    	}
	                    }
	             %>
	     </aui:select>
     </aui:fieldset>
     <aui:button-row>
     	<input type="submit" value="<liferay-ui:message key="upload" />" onClick="<%= uploadProgressId %>.startProgress(); return true;" />
	</aui:button-row>
	<br/>
	<%
	if (utenti.size()>0){ 
	%>
	<table class="table table-bordered table-hover table-striped"> 
	<thead class="table-columns">
		<tr> 
			<th class="table-first-header"><liferay-ui:message key="firstname-lastname" /></th>
			<th><liferay-ui:message key="email" /></th>
			<th class="table-last-header"><liferay-ui:message key="csv-imp-status" /></th>
		</tr>	
	</thead>
	<tbody class="table-data">
	<%
	for (int i = 0; i < utenti.size(); i++) {
        CsvUserBean utente=(CsvUserBean)utenti.get(i);
    %>
		<tr>
		    <td class="table-cell first"><%= utente.getFirstName() + " " + utente.getLastName()%></td>
		    <td class="table-cell"><%= utente.getEmail()%></td>
		    <td  class="table-cell last"><%= utente.getImpStatus()%></td>
	    </tr>
	<%
	}
	%>
     </tbody>
     </table>
	<%
	} //if (utenti.size()>0)
	%>
     
</aui:form>
 
