CSV_User_Import-portlet

<h2>Introduction</h2>
This is a control panel Liferay portlet you can use to import and create portal users from a CSV file.

<h3>The CSV file</h3>
The first line in the CSV file is mandatory and is like the following:<br/>
<code>username;email;firstName;lastName;password;male;jobTitle;birthday</code><br/>
<br/>
The separator could be a semicolon ";" or a comma ",". You can change it from the Configuration page.<br/>
The default is a semicolon ";".
<h4>The columns</h4>
The column <strong>username</strong> will be the Lifeary users's Screen Name and is mandatory.<br/>
The column <strong>email</strong> will be the Lifeary users's Email and is mandatory.<br/>
The column <strong>firstName</strong> will be the Lifeary users's First name and is mandatory.<br/>
The column <strong>lastName</strong> will be the Lifeary users's Last name and it could be mandatory (it depends from the Portal Settings->Users->Last name ruquired option).<br/>
The column <strong>password</strong> will be the Lifeary users's password and is mandatory.<br/>
The column <strong>male</strong> will be the Lifeary users's Ses and sould be "true" (for "male") or "false" (for "female"). In the configuration page you can "Ignore" this column.<br/>
The column <strong>jobTitle</strong> will be the Lifeary users's Job title. In the configuration page you can "Ignore" this column.<br/>
The column <strong>birthday</strong> will be the Lifeary users's Birthday. In the configuration page you can "Ignore" this column and set the date format you'll use in the CSV file.<br/>
<br/>
In the configuration page you can "Ignore" some columns: the column have to be present in the CSV file, but the corresponding values will be ignored during the import.
If the column is marked as "Optional" the corresponding values, if presents, will be registered for the user.
