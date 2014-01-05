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
	String cF1;
	String cF2;
	String cF3;
	String cF4;
	String cF5;
	String cF6;
	String cF7;
	String cF8;
	String cF9;
	String cF10;
	String cF11;
	String cF12;
	String cF13;
	String cF14;
	String cF15;
	String cF16;
	String cF17;
	String cF18;
	String cF19;
	String cF20;

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

	public String getCF1() {
		return cF1;
	}

	public void setCF1(String cF1) {
		this.cF1 = cF1;
	}

	public String getCF2() {
		return cF2;
	}

	public void setCF2(String cF2) {
		this.cF2 = cF2;
	}

	public String getCF3() {
		return cF3;
	}

	public void setCF3(String cF3) {
		this.cF3 = cF3;
	}

	public String getCF4() {
		return cF4;
	}

	public void setCF4(String cF4) {
		this.cF4 = cF4;
	}

	public String getCF5() {
		return cF5;
	}

	public void setCF5(String cF5) {
		this.cF5 = cF5;
	}

	public String getCF6() {
		return cF6;
	}

	public void setCF6(String cF6) {
		this.cF6 = cF6;
	}

	public String getCF7() {
		return cF7;
	}

	public void setCF7(String cF7) {
		this.cF7 = cF7;
	}

	public String getCF8() {
		return cF8;
	}

	public void setCF8(String cF8) {
		this.cF8 = cF8;
	}

	public String getCF9() {
		return cF9;
	}

	public void setCF9(String cF9) {
		this.cF9 = cF9;
	}

	public String getCF10() {
		return cF10;
	}

	public void setCF10(String cF10) {
		this.cF10 = cF10;
	}

	public String getCF11() {
		return cF11;
	}

	public void setCF11(String cF11) {
		this.cF11 = cF11;
	}

	public String getCF12() {
		return cF12;
	}

	public void setCF12(String cF12) {
		this.cF12 = cF12;
	}

	public String getCF13() {
		return cF13;
	}

	public void setCF13(String cF13) {
		this.cF13 = cF13;
	}

	public String getCF14() {
		return cF14;
	}

	public void setCF14(String cF14) {
		this.cF14 = cF14;
	}

	public String getCF15() {
		return cF15;
	}

	public void setCF15(String cF15) {
		this.cF15 = cF15;
	}

	public String getCF16() {
		return cF16;
	}

	public void setCF16(String cF16) {
		this.cF16 = cF16;
	}

	public String getCF17() {
		return cF17;
	}

	public void setCF17(String cF17) {
		this.cF17 = cF17;
	}

	public String getCF18() {
		return cF18;
	}

	public void setCF18(String cF18) {
		this.cF18 = cF18;
	}

	public String getCF19() {
		return cF19;
	}

	public void setCF19(String cF19) {
		this.cF19 = cF19;
	}

	public String getCF20() {
		return cF20;
	}

	public void setCF20(String cF20) {
		this.cF20 = cF20;
	}

	@Override
	public String toString() {
		return "UserBean [liferayUserId=" + liferayUserId + ", username="
				+ username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", male=" + male + ", password="
				+ password + ", jobTitle=" + jobTitle +", birthday=" + birthday.toString() + ", status=" + impStatus + "]";
	}


}
