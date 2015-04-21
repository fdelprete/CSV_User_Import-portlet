package com.fmdp.csvuserimport.portlet.model;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
*/


public class CsvOrgBean {
	long liferayOrganizationId;
	long userId;
	long parentOrganizationId ;
	String companyname;
	String name;
	String type;
	String address1;
	String address2;
	String city;
	String zip;
	String state;
	String phone;
	String fax;
	boolean recursable ;
	String region;
    String country;
    int statusId;
    String comments;
    boolean site;
    String impStatus;

	public long getLiferayOrganizationId() {
		return liferayOrganizationId;
	}

	public void setLiferayOrganizationId(long liferayOrganizationId) {
		this.liferayOrganizationId = liferayOrganizationId;
	}

    public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public boolean getRecursable() {
		return recursable;
	}

	public void setRecursable(boolean recursable) {
		this.recursable = recursable;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean getSite() {
		return site;
	}

	public void setSite(boolean site) {
		this.site = site;
	}

	public String getImpStatus() {
		return impStatus;
	}

	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}
	
	@Override
	public String toString() {
		return "OrgBean [liferayOrganizationId="+ liferayOrganizationId + ", userId=" + userId + ", parentOrganizationId="
				+ parentOrganizationId + ", name=" + name + ", type=" + type
				+ ", recursable=" + recursable + ", region=" + region + ", country="
				+ country + ", statusId=" + statusId +", comments=" + comments
				+ ", site=" + site + "]";
	}


}
