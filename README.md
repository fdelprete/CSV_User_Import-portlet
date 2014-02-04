<h2>Introduction</h2>
This is a control panel Liferay portlet you can use to import and create portal users from a CSV file.
<h4>How to use</h4>
<ol>
<li>Prepare the CSV file (see below for the CSV header that is mandatory)
<li>Optionally select the regular role and/or the organization to assign to the imported users
<li>Upload the file
<li>Done!
</ol>
The import process checks that the screen name and the email address are unique. If a user with a non unique username or email is present in the csv file, that user will be skipped and after the import process completes they'll be shown in a table.
With the Configuration page of the portlet you can control some options and, if they are configured, you can also import your Liferay user custom fields.
<h3>The CSV file</h3>
The first line in the CSV file is mandatory and is exactly the following:<br/>
<code>username;email;firstName;lastName;password;male;jobTitle;birthday;cf1;cf2;cf3;cf4;cf5;cf6;cf7;cf8;cf9;cf10;cf11;cf12;cf13;cf14;cf15;cf16;cf17;cf18;cf19;cf20</code><br/>
<br/>
The separator could be a semicolon ";" or a comma ",". You can change it from the Configuration page.<br/>
The default is a semicolon ";".
<h4>The columns</h4>
The column <strong>username</strong> will be the Lifeary users's Screen Name and is mandatory.<br/>
The column <strong>email</strong> will be the Lifeary users's Email and is mandatory.<br/>
The column <strong>firstName</strong> will be the Lifeary users's First name and is mandatory.<br/>
The column <strong>lastName</strong> will be the Lifeary users's Last name and it could be mandatory (it depends from the Portal Settings->Users->Last name required option).<br/>
The column <strong>password</strong> will be the Lifeary users's password and is mandatory.<br/>
The column <strong>male</strong> will be the Lifeary users's Ses and sould be "true" (for "male") or "false" (for "female"). In the configuration page you can "Ignore" this column.<br/>
The column <strong>jobTitle</strong> will be the Lifeary users's Job title. In the configuration page you can "Ignore" this column.<br/>
The column <strong>birthday</strong> will be the Lifeary users's Birthday. In the configuration page you can "Ignore" this column and set the date format you'll use in the CSV file.<br/>
The columns <strong>cf1</strong>, <strong>cf2</strong>, ..., <strong>cf20</strong> will be mapped to maximun 20 custom fileds. In the configuration page, in the "Custom fields" tab, you can select from the available custom fields, the fields you need to import. In the current box, the first field will be mapped to the cf1 column and so on.<br/>
<br/>
In the configuration page, in the "Basic" tab, you can "Ignore" some columns: the column have to be present in the CSV file, but the corresponding values will be ignored during the import.
If the column is marked as "Optional" the corresponding values, if presents, will be registered for the user.
<h3>Examples</h3>
Here some examples for a CSV file.
<h4>Example 1: basic CSV file</h4>
<code>username;email;firstName;lastName;password;male;jobTitle;birthday;cf1;cf2;cf3;cf4;cf5;cf6;cf7;cf8;cf9;cf10;cf11;cf12;cf13;cf14;cf15;cf16;cf17;cf18;cf19;cf20<br/>
m.rossi;mario@pippo.com;Mario;Rossi;qwerty67;;;;;;;;;;;;;;;;;;;;;;;<br/>
g.bianchi;g.bianchi@pippo.com;Gino;Bianchi;qwerty67;;;;;;;;;;;;;;;;;;;;;;;<br/>
</code>
<br/>
In the configuration page:
<ul>
<li>"Basic" tab</li>
<ul>
<li>The "CSV separator" is "semicolon".
<li>For the fields male, jobTitle and birthday choose the "Ignore" option.</li>
</ul>
<li>"Custom fields" tab</li>
<ul>
<li>The Current box is empty.</li>
</ul>
</ul>
<h4>Example 2: CSV file with sex, job title and birthday</h4>
<code>username;email;firstName;lastName;password;male;jobTitle;birthday;cf1;cf2;cf3;cf4;cf5;cf6;cf7;cf8;cf9;cf10;cf11;cf12;cf13;cf14;cf15;cf16;cf17;cf18;cf19;cf20<br/>
m.rossi;mario@pippo.com;Mario;Rossi;qwerty67;true;Web Master;16-06-1966;;;;;;;;;;;;;;;;;;;;<br/>
g.bianchi;g.bianchi@pippo.com;Gino;Bianchi;qwerty67;true;SEO Manager;28-12-1980;;;;;;;;;;;;;;;;;;;;<br/>
</code>
<br/>
In the configuration page:
<ul>
<li>"Basic" tab</li>
<ul>
<li>The "CSV separator" is "semicolon".
<li>For the fields male, jobTitle and birthday choose the "Otional" option.</li>
<li>For the field "birthday" choose the "dd-MM-yyyy" date format option.</li>
</ul>
<li>"Custom fields" tab</li>
<ul>
<li>The Current box is empty.</li>
</ul>
</ul>
<h4>Example 3: CSV file with sex, NO job title, birthday and 2 custom fields</h4>
In your portal you have 2 custom fields defined for the User entity:
<ul>
<li>Crmcod is a "Text" type custom field you want to use in order to store the external CRM code number</li>
<li>Skills is a "Group of Text Values" type custom field you want to use in order to store the technical skills the user has. (One or more in: Android Sdk,ASP.NET,Objective C,J2EE,Java,Php)</li>
</ul>
<code>username;email;firstName;lastName;password;male;jobTitle;birthday;cf1;cf2;cf3;cf4;cf5;cf6;cf7;cf8;cf9;cf10;cf11;cf12;cf13;cf14;cf15;cf16;cf17;cf18;cf19;cf20<br/>
m.rossi;mario@pippo.com;Mario;Rossi;qwerty67;true;;16-06-1966;crm1245;"Android Sdk,ASP.NET";;;;;;;;;;;;;;;;;;<br/>
g.bianchi;g.bianchi@pippo.com;Gino;Bianchi;qwerty67;true;;28-12-1980;crm782-a;"Objective C,J2EE,Php";;;;;;;;;;;;;;;;;;<br/>
</code>
<br/>
<strong>Please note</strong> the quotes enclosing the cf2 column values that will be imported into the "Skill" field.
<br/>
In the configuration page:
<ul>
<li>"Basic" tab</li>
<ul>
<li>The "CSV separator" is "semicolon".
<li>For the fields male and birthday choose the "Otional" option.</li>
<li>For the field "jobTitle" choose the "Ignore" option.
<li>For the field "birthday" choose the "dd-MM-yyyy" date format option.</li>
</ul>
<li>"Custom fields" tab</li>
<ul>
<li>The Current box contains if the exact order (from top to down) Crmcod and Skills.</li>
</ul>
</ul>

<h3>Requirements and info</h3>
This portlet is based on the original work of Paul Butenko (tks Paul).<br>
http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
<br/>
It uses <a href="http://supercsv.sourceforge.net/">SuperCSV</a> java package.<br/>
This portlet is for the 6.2.0-GA1 CE and for EE version of Liferay.
Here in the <a href="https://www.liferay.com/marketplace/-/mp/application/33064852">Liferay Marketplace</a> you can download the portlet for previous Liferay versions.

