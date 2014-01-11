package com.fmdp.csvuserimport.portlet.model;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
*/

import java.util.Date;

public class CsvUserBean {
	long liferayUserId;
	String username;
	String email;
	String firstName;
	String lastName;
	String password;
	boolean male;
	String jobTitle;
	Date birthday;
	String impStatus;
	String cf1;
	String cf2;
	String cf3;
	String cf4;
	String cf5;
	String cf6;
	String cf7;
	String cf8;
	String cf9;
	String cf10;
	String cf11;
	String cf12;
	String cf13;
	String cf14;
	String cf15;
	String cf16;
	String cf17;
	String cf18;
	String cf19;
	String cf20;

	public long getLiferayUserId() {
		return liferayUserId;
	}

	public void setLiferayUserId(long liferayUserId) {
		this.liferayUserId = liferayUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getImpStatus() {
		return impStatus;
	}

	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}

	public String getCf1() {
		return cf1;
	}

	public void setCf1(String cf1) {
		this.cf1 = cf1;
	}

	public String getCf2() {
		return cf2;
	}

	public void setCf2(String cf2) {
		this.cf2 = cf2;
	}

	public String getCf3() {
		return cf3;
	}

	public void setCf3(String cf3) {
		this.cf3 = cf3;
	}

	public String getCf4() {
		return cf4;
	}

	public void setCf4(String cf4) {
		this.cf4 = cf4;
	}

	public String getCf5() {
		return cf5;
	}

	public void setCf5(String cf5) {
		this.cf5 = cf5;
	}

	public String getCf6() {
		return cf6;
	}

	public void setCf6(String cf6) {
		this.cf6 = cf6;
	}

	public String getCf7() {
		return cf7;
	}

	public void setCf7(String cf7) {
		this.cf7 = cf7;
	}

	public String getCf8() {
		return cf8;
	}

	public void setCf8(String cf8) {
		this.cf8 = cf8;
	}

	public String getCf9() {
		return cf9;
	}

	public void setCf9(String cf9) {
		this.cf9 = cf9;
	}

	public String getCf10() {
		return cf10;
	}

	public void setCf10(String cf10) {
		this.cf10 = cf10;
	}

	public String getCf11() {
		return cf11;
	}

	public void setCf11(String cf11) {
		this.cf11 = cf11;
	}

	public String getCf12() {
		return cf12;
	}

	public void setCf12(String cf12) {
		this.cf12 = cf12;
	}

	public String getCf13() {
		return cf13;
	}

	public void setCf13(String cf13) {
		this.cf13 = cf13;
	}

	public String getCf14() {
		return cf14;
	}

	public void setCf14(String cf14) {
		this.cf14 = cf14;
	}

	public String getCf15() {
		return cf15;
	}

	public void setCf15(String cf15) {
		this.cf15 = cf15;
	}

	public String getCf16() {
		return cf16;
	}

	public void setCf16(String cf16) {
		this.cf16 = cf16;
	}

	public String getCf17() {
		return cf17;
	}

	public void setCf17(String cf17) {
		this.cf17 = cf17;
	}

	public String getCf18() {
		return cf18;
	}

	public void setCf18(String cf18) {
		this.cf18 = cf18;
	}

	public String getCf19() {
		return cf19;
	}

	public void setCf19(String cf19) {
		this.cf19 = cf19;
	}

	public String getCf20() {
		return cf20;
	}

	public void setCf20(String cf20) {
		this.cf20 = cf20;
	}

	@Override
	public String toString() {
		return "UserBean [liferayUserId=" + liferayUserId + ", username="
				+ username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", male=" + male + ", password="
				+ password + ", jobTitle=" + jobTitle +", birthday=" + birthday.toString()
				+ ", cf1=" + cf1 + ", cf2=" + cf2 + ", cf3=" + cf3 + ", cf4=" + cf4 + ", cf5=" + cf5
				+ ", cf6=" + cf6 + ", cf7=" + cf7 + ", cf8=" + cf8 + ", cf9=" + cf9 + ", cf10=" + cf10
				+ ", cf11=" + cf11 + ", cf12=" + cf12 + ", cf13=" + cf13 + ", cf14=" + cf14 + ", cf15=" + cf15
				+ ", cf16=" + cf16 + ", cf17=" + cf17 + ", cf18=" + cf18 + ", cf19=" + cf19 + ", cf20=" + cf20
				+ ", status=" + impStatus + "]";
	}


}
