package com.fmdp.csvuserimport.portlet;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */

import java.util.List;

import javax.portlet.ActionRequest;

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Region;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.fmdp.csvuserimport.portlet.model.CsvOrgBean;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.CountryServiceUtil;


public class OrgServiceImpl {
	private static Log _log = LogFactoryUtil.getLog(OrgServiceImpl.class);

	private static OrgServiceImpl INSTANCE = new OrgServiceImpl();

	private OrgServiceImpl() {
	}

	public static OrgServiceImpl getInstance() {
		return INSTANCE;
	}

	public Organization addOrg(ActionRequest request,
			CsvOrgBean orgBean) {
		Organization org = addLiferayOrg(request, orgBean);
		if (org != null) {
			orgBean.setLiferayOrganizationId(org.getOrganizationId());
			if (_log.isDebugEnabled()){
				_log.debug("Org: " + orgBean.getName() + " was added to liferay.");
			}
		} else {
			if (_log.isDebugEnabled()){
				_log.debug("Org: " + orgBean.getName() + " not added to liferay: " + orgBean.getImpStatus());
			}
		}
		return org;
	}

	private Organization addLiferayOrg(ActionRequest request,
			CsvOrgBean orgBean) {
		Organization org = null;
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			long creatorUserId = themeDisplay.getUserId(); 
			long companyId = themeDisplay.getCompanyId(); 

			long parentOrganizationId = com.liferay.portal.model.OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

			String orgType = OrganizationConstants.TYPE_REGULAR_ORGANIZATION;

			long countryId, regionId;
			String countryName = "Italy";
			if (orgBean.getCountry() != "") {
				countryName = orgBean.getCountry();
			}

			Country country = null;
			try {
				country = CountryServiceUtil.getCountryByName(countryName);
			} catch (NoSuchCountryException e) {
				country = null;
			}
			if (country != null) {
				countryId = country.getCountryId();
				Region region = null;
				try {
					region = RegionServiceUtil.getRegion(countryId, orgBean.getState());	
				} catch (NoSuchRegionException e) {
					region = null;
				}

				if (region != null) {
					regionId = region.getRegionId();
				} else {
					regionId = 0;
				}
			} else {
				countryId = 0;
				regionId = 0;
			}

			String companyName = orgBean.getCompanyname();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(request);

			org = null;
			boolean orgbycompanyname_exists = true;

			try {
				org = OrganizationLocalServiceUtil.getOrganization(companyId, companyName);
			} catch (NoSuchOrganizationException nsue) {
				orgbycompanyname_exists = false;
			}

			if((!orgbycompanyname_exists))
			{
				org = OrganizationLocalServiceUtil.addOrganization(creatorUserId,
						parentOrganizationId,
						companyName,
						orgType,
						regionId,
						countryId,
						ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
						orgBean.getComments(),
						false,
						serviceContext);

				org = OrganizationLocalServiceUtil.updateOrganization(org);

				Indexer indexer = IndexerRegistryUtil.getIndexer(Organization.class);

				indexer.reindex(org);

				List<ListType> addressTypes = ListTypeServiceUtil.getListTypes(Organization.class.getName()+ ListTypeConstants.ADDRESS); 
				int personalAddressTypeId = 0;

				for (ListType addressType : addressTypes) {   
					String addressTypeName = addressType.getName();   
					if(addressTypeName.equalsIgnoreCase("Billing")) {   
						personalAddressTypeId = (int) addressType.getListTypeId();   
					}   
				}

				AddressLocalServiceUtil.addAddress(
						creatorUserId, 
						Organization.class.getName(), 
						org.getOrganizationId(), 
						orgBean.getAddress1(), 
						orgBean.getAddress2(), 
						"", 
						orgBean.getCity(), 
						orgBean.getZip(),
						regionId, 
						countryId, 
						personalAddressTypeId, 
						true, 
						true,
						serviceContext);

				List<ListType> phoneTypes = ListTypeServiceUtil.getListTypes(Organization.class.getName()
						+ ListTypeConstants.PHONE);

				int localPhoneTypeId = 0;
				for (ListType phoneType : phoneTypes) {  
					String phoneTypeName = phoneType.getName();  
					if (phoneTypeName.equalsIgnoreCase("Local")) {  
						localPhoneTypeId = (int) phoneType.getListTypeId();  
					}  
				}  
				PhoneLocalServiceUtil.addPhone(
						creatorUserId, 
						Organization.class.getName(), 
						org.getOrganizationId(), 
						orgBean.getPhone(),
						"",
						localPhoneTypeId,
						true,
						serviceContext);
				int faxTypeId = 0;   
				for (ListType phoneType : phoneTypes) {  
					String phoneTypeName = phoneType.getName();  
					if (phoneTypeName.equalsIgnoreCase("Fax")) {  
						faxTypeId = (int) phoneType.getListTypeId();  
					}  
				}  
				PhoneLocalServiceUtil.addPhone(
						creatorUserId, 
						Organization.class.getName(), 
						org.getOrganizationId(), 
						orgBean.getFax(),
						"",
						faxTypeId,
						false,
						serviceContext);

				orgBean.setImpStatus("Organization imported.");
			} else {
				String msg_exists = "";
				if (orgbycompanyname_exists) {
					msg_exists = msg_exists + "Company Name is not unique.";
				}

				orgBean.setImpStatus(msg_exists);
				org = null;
			}
		} catch (PortalException e) {
			_log.error(e);
		} catch (SystemException e) {
			_log.error(e);
		}
		return org;
	}


}