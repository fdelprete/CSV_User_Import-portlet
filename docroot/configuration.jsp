<%@ include file="/init.jsp"%>
<portlet:defineObjects />

<%

PortletPreferences preferences = renderRequest.getPreferences();
 
String portletResource = ParamUtil.getString(request, "portletResource");
 
if (Validator.isNotNull(portletResource)) {
    preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String csvSeparator = preferences.getValue("csvSeparator","EXCEL_NORTH_EUROPE_PREFERENCE");

String address1CsvStatus = preferences.getValue("address1CsvStatus","ignore");
String address2CsvStatus = preferences.getValue("address2CsvStatus","ignore");
String cityCsvStatus = preferences.getValue("cityCsvStatus","ignore");
String zipCsvStatus = preferences.getValue("zipCsvStatus","ignore");
String stateCsvStatus = preferences.getValue("stateCsvStatus","ignore");
String countryCsvStatus = preferences.getValue("countryCsvStatus","ignore");
String phoneCsvStatus = preferences.getValue("phoneCsvStatus","ignore");
String faxCsvStatus = preferences.getValue("faxCsvStatus","ignore");
String commentsCsvStatus = preferences.getValue("commentsCsvStatus","ignore");


String maleCsvStatus = preferences.getValue("maleCsvStatus","ignore");
String jobtitleCsvStatus = preferences.getValue("jobtitleCsvStatus","ignore");
String birthdayCsvStatus = preferences.getValue("birthdayCsvStatus","ignore");
String birthdayCsvOptions = preferences.getValue("birthdayCsvOptions","dd-MM-yyyy");
String[] currentCustomFields = preferences.getValue("customFields","").split(",");

String tabs2 = ParamUtil.getString(request, "tabs2", "basic-csv");
String redirect = ParamUtil.getString(request, "redirect");
Enumeration<String> attributeNames = user.getExpandoBridge().getAttributeNames();			

%>

<liferay-portlet:actionURL var="configurationURL"
	portletConfiguration="true" />
<liferay-portlet:renderURL portletConfiguration="true"
	var="configurationRenderURL">
	<!-- varImpl="configurationRenderURL" -->
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>
<aui:form method="post" action="<%=configurationURL.toString()%>"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCsvImport();" %>'>
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="<%=Constants.CMD%>" type="hidden"
		value="<%=Constants.UPDATE%>" />
	<aui:input name="redirect" type="hidden"
		value="<%= configurationRenderURL.toString() %>" />
	<liferay-ui:success key="success"
	message="csv-config-saved"/>
		
	<%
	String tabs1Names = "basic-csv";

	if (attributeNames.hasMoreElements()) {
		tabs1Names = tabs1Names.concat(",custom-csv");
	}
	%>
	<liferay-ui:tabs names="<%= tabs1Names %>" param="tabs2"
		url="<%= configurationRenderURL %>" />
	<c:choose>
		<c:when test='<%= tabs2.equals("basic-csv") %>'>
		
			<aui:fieldset cssClass='<%= renderResponse.getNamespace() + "prefList" %>'>
				<aui:field-wrapper inlineField="true">
					<liferay-ui:toggle id="table-toggle-csv-pref_id"
						hideMessage='<%= "&laquo; " + LanguageUtil.get(request, "hide-csv-pref") %>'
						showMessage='<%= LanguageUtil.get(request, "show-csv-pref") + " &raquo; " %>'
						defaultShowContent="false" />
					<div id="table-toggle-csv-pref_id"
						style='display: <liferay-ui:toggle-value defaultValue="false" id="table-toggle-csv-pref_id"/>; padding-top:10px;'>
						<table border="0" class="table table-striped">
							<tr class="a">
								<td align="left"><strong><liferay-ui:message key="option" /></strong></td>
								<th align="center"><liferay-ui:message key="quote-char" /></th>
								<th align="center"><liferay-ui:message key="delimiter-char" /></th>
								<th align="center"><liferay-ui:message key="end-of-line-symbols" /></th>
							</tr>
								<tr class="b">
								<td align="left">STANDARD_PREFERENCE</td>
								<td align="center">&quot;</td>
								<td align="center">,</td>
								<td align="center">\r\n</td>
							</tr>
							<tr class="a">
								<td align="left">EXCEL_PREFERENCE</td>
								<td align="center">&quot;</td>
								<td align="center">,</td>
								<td align="center">\n</td>
							</tr>
							<tr class="b">
								<td align="left">EXCEL_NORTH_EUROPE_PREFERENCE</td>
								<td align="center">&quot;</td>
								<td align="center">;</td>
								<td align="center">\n</td>
							</tr>
							<tr class="a">
								<td align="left">TAB_PREFERENCE</td>
								<td align="center">&quot;</td>
								<td align="center">\t</td>
								<td align="center">\n</td>
							</tr>
						</table>
					</div>
				</aui:field-wrapper>
				<aui:select label="csv-separator" name="csvSeparator"
					showEmptyOption="false">
					<aui:option label='standard-pref'
						selected='<%= csvSeparator.equals("STANDARD_PREFERENCE") %>' value="STANDARD_PREFERENCE" />
					<aui:option label='excel-pref'
						selected='<%= csvSeparator.equals("EXCEL_PREFERENCE") %>' value="EXCEL_PREFERENCE" />
					<aui:option label='excel-north-europe-pref'
						selected='<%= csvSeparator.equals("EXCEL_NORTH_EUROPE_PREFERENCE") %>' value="EXCEL_NORTH_EUROPE_PREFERENCE" />
					<aui:option label='tab-pref'
						selected='<%= csvSeparator.equals("TAB_PREFERENCE") %>' value="TAB_PREFERENCE" />
				</aui:select>
				<div class="separator"></div>
				<table class="table table-bordered table-hover table-striped">
					<thead class="table-columns">
						<tr>
							<th class="table-first-header"><liferay-ui:message
									key="csv-field" /></th>
							<th><liferay-ui:message key="use-ignore" /></th>
							<th class="table-last-header"><liferay-ui:message
									key="other-settings" /></th>
						</tr>
					</thead>
					<tbody class="table-data">
						<tr>
							<td class="table-cell  first">male</td>
							<td class="table-cell">
								<aui:select label=""
									name="maleCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= maleCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= maleCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">jobTitle</td>
							<td class="table-cell">
								<aui:select label=""
									name="jobtitleCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= jobtitleCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= jobtitleCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">birthday</td>
							<td class="table-cell">
								<aui:select label=""
									name="birthdayCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= birthdayCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= birthdayCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last">
								<aui:select label="date-format"
									name="birthdayCsvOptions" helpMessage="select-date-format"
									showEmptyOption="false">
									<aui:option label='dd-MM-yyyy'
										selected='<%= birthdayCsvOptions.equals("dd-MM-yyyy") %>'
										value="dd-MM-yyyy" />
									<aui:option label='dd/MM/yyyy'
										selected='<%= birthdayCsvOptions.equals("dd/MM/yyyy") %>'
										value="dd/MM/yyyy" />
									<aui:option label='MM-dd-yyyy'
										selected='<%= birthdayCsvOptions.equals("MM-dd-yyyy") %>'
										value="MM-dd-yyyy" />
									<aui:option label='MM/dd/yyyy'
										selected='<%= birthdayCsvOptions.equals("MM/dd/yyyy") %>'
										value="MM/dd/yyyy" />
									<aui:option label='yyyyMMdd'
										selected='<%= birthdayCsvOptions.equals("yyyyMMdd") %>'
										value="yyyyMMdd" />
								</aui:select></td>
						</tr>
					</tbody>
				</table>
				<div class="separator"></div>
				<table class="table table-bordered table-hover table-striped">
					<thead class="table-columns">
						<tr>
							<th class="table-first-header"><liferay-ui:message
									key="csv-field" /></th>
							<th><liferay-ui:message key="use-ignore" /></th>
							<th class="table-last-header"><liferay-ui:message
									key="other-settings" /></th>
						</tr>
					</thead>
					<tbody class="table-data">
						<tr>
							<td class="table-cell  first">address1</td>
							<td class="table-cell">
								<aui:select label=""
									name="address1CsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= address1CsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= address1CsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">address2</td>
							<td class="table-cell">
								<aui:select label=""
									name="address2CsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= address2CsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= address2CsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">city</td>
							<td class="table-cell">
								<aui:select label=""
									name="cityCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= cityCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= cityCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">zip</td>
							<td class="table-cell">
								<aui:select label=""
									name="zipCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= zipCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= zipCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">state</td>
							<td class="table-cell">
								<aui:select label=""
									name="stateCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= stateCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= stateCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">country</td>
							<td class="table-cell">
								<aui:select label=""
									name="countryCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= countryCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= countryCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">phone</td>
							<td class="table-cell">
								<aui:select label=""
									name="phoneCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= phoneCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= phoneCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">fax</td>
							<td class="table-cell">
								<aui:select label=""
									name="faxCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= faxCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= faxCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
						</tr>
						<tr>
							<td class="table-cell  first">comments</td>
							<td class="table-cell">
								<aui:select label=""
									name="commentsCsvStatus" showEmptyOption="false">
									<aui:option label='ignore'
										selected='<%= commentsCsvStatus.equals("ignore") %>'
										value="ignore" />
									<aui:option label='optional'
										selected='<%= commentsCsvStatus.equals("optional") %>'
										value="optional" />
								</aui:select></td>
							<td class="table-cell  last"></td>
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
			<aui:fieldset
				cssClass='<%= renderResponse.getNamespace() + "prefList" %>'>
				<aui:input name='customFields' type="hidden"
					value="<%= StringUtil.merge(currentCustomFields) %>" />
				<%

				// Left list
				List leftList = new ArrayList();

				for (String currentCf : currentCustomFields) {
					if (!currentCf.equals(""))
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

				<liferay-ui:input-move-boxes leftBoxName="currentCustomFields"
					leftList="<%= leftList %>" leftReorder="true" leftTitle="current"
					rightBoxName="availableCustomFields" rightList="<%= rightList %>"
					rightTitle="available" />

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