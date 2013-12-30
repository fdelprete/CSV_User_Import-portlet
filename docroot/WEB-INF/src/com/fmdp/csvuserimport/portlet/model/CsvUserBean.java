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

	@Override
	public String toString() {
		return "UserBean [liferayUserId=" + liferayUserId + ", username="
				+ username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", male=" + male + ", password="
				+ password + ", jobTitle=" + jobTitle +", birthday=" + birthday.toString() + ", status=" + impStatus + "]";
	}


}
