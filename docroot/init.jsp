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
page import="com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil" %><%@ 
page import="java.util.List" %><%@ 
page import="java.util.ArrayList" %><%@ 
page import="java.util.Collection" %><%@ 
page import="java.util.Collections" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageWrapper" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="java.util.Enumeration" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ObjectValuePair" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.model.*" %><%@
page import="com.liferay.portal.model.impl.*" %><%@ 
page import="java.io.Serializable" %>
<%@ page import="com.liferay.util.PwdGenerator"%>
<%@ page import="com.liferay.portal.model.Role"%>
<%@ page import="com.liferay.portal.model.Organization"%>
<%@ page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ page import="com.fmdp.csvuserimport.portlet.model.CsvUserBean"%>
<%@ page import="com.liferay.portal.service.OrganizationLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.RoleConstants"%>
<%@ page import="com.liferay.portal.model.OrganizationConstants"%>


<liferay-theme:defineObjects />
