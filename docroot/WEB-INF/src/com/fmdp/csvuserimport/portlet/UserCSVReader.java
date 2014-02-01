package com.fmdp.csvuserimport.portlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.fmdp.csvuserimport.portlet.model.CsvUserBean;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */
public class UserCSVReader {
  /** The Constant LOGGER. */
  private static final Log _log = LogFactoryUtil.getLog(UserCSVReader.class);
  
  /** Singleton instance. */
  private static UserCSVReader INSTANCE = new UserCSVReader();

  private static final Charset UTF8 = Charset.forName("UTF-8");
  
  private UserCSVReader() {
  }

  public static UserCSVReader getInstance() {
    return INSTANCE;
  }

  final CellProcessor[] processors = new CellProcessor[] {
		  new NotNull(), new NotNull(), new NotNull(), new NotNull(), new NotNull(), 
		  new Optional(new ParseBool()), null, new Optional(new ParseDate("dd-MM-yyyy")),
		  null, null, null, null, null, 
		  null, null, null, null, null, 
		  null, null, null, null, null, 
		  null, null, null, null, null};

  public List<CsvUserBean> readUsers(final ActionRequest request, String Fname) {
    ICsvBeanReader inFile = null;

    List<CsvUserBean> users = new ArrayList<CsvUserBean>();
    
    if(_log.isDebugEnabled()) {
      _log.debug("Try to open the uploaded csv file");
    }
      String urldecoded = UserCSVReader.decodeUrl(Fname.replace('/', File.separatorChar));
      	
    try {
    	String portletInstanceId = (String) request.getAttribute(WebKeys.PORTLET_ID);
    	PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletInstanceId);
    	String csvSeparator = preferences.getValue("csvSeparator", "EXCEL_NORTH_EUROPE_PREFERENCE");
    	String maleCsvStatus = preferences.getValue("maleCsvStatus","ignore");
    	String jobtitleCsvStatus = preferences.getValue("jobtitleCsvStatus","ignore");
    	String birthdayCsvStatus = preferences.getValue("birthdayCsvStatus","ignore");
    	String birthdayCsvOptions = preferences.getValue("birthdayCsvOptions","dd-MM-yyyy");
    	String customFields = preferences.getValue("customFields","");
    	String[] cFs = customFields.split(",");
    	int k = 0;
		String[] cFields = new String[] {
				null, null, null, null, null,
				null, null, null, null, null,
				null, null, null, null, null,
				null, null, null, null, null};
		String cfName = "";
    	for (int j = 0; j < 20 && j < cFs.length; j++) {
			k = j + 1;
			cfName = "cf" + k;
    		cFields[j] = cfName;
    	}
    	for(int z = k; z < 20; z++){
    		cFields[z] = null;
    	}
    	CsvPreference pref = null;
    	if (csvSeparator.equals("EXCEL_PREFERENCE")){
    		pref = CsvPreference.EXCEL_PREFERENCE;
    	} else if (csvSeparator.equals("STANDARD_PREFERENCE")){
    		pref = CsvPreference.STANDARD_PREFERENCE;
    	} else if (csvSeparator.equals("TAB_PREFERENCE")){
    		pref = CsvPreference.TAB_PREFERENCE;
    	} else {
    		pref = CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE;
    	}
    	String impMale = null;    		
        if (maleCsvStatus.equals("optional")){
        	impMale = "male";
        }
        String impJobTitle = null;
        if (jobtitleCsvStatus.equals("optional")){
        	impJobTitle = "jobTitle";
        }
        String impBirthday = null;
        if (birthdayCsvStatus.equals("optional")){
        	impBirthday = "birthday";
        	if(!birthdayCsvOptions.equals("dd-MM-yyyy")){
        		processors[7] = new Optional(new ParseDate(birthdayCsvOptions));
        	}
        }
        final String[] header = new String[28];
        header[0] = "username";
        header[1] ="email";
        header[2] = "firstName"; 
        header[3] = "lastName";
        header[4] ="password";
        header[5] = impMale; 
        header[6] = impJobTitle; 
        header[7] = impBirthday;
    	for (int j = 8; j < 28 ; j++) {
    		header[j] = cFields[j-8];
    	}
    	if(_log.isDebugEnabled()) {
        	_log.debug("Header for mapping: "+ Arrays.toString(header));
        }
    	inFile = new CsvBeanReader(new FileReader(urldecoded), pref);
       
    	// header verify
    	final String[] header_temp = inFile.getHeader(true);
    	List<String> expectedHeaders = Arrays.asList("username","email","firstName","lastName",
        		"password","male","jobTitle","birthday"
        		,"cf1","cf2","cf3","cf4","cf5"
        		,"cf6","cf7","cf8","cf9","cf10"
        		,"cf11","cf12","cf13","cf14","cf15"
        		,"cf16","cf17","cf18","cf19","cf20");

        if (!Arrays.asList(header_temp).containsAll(expectedHeaders)){
            // not all headers present - handle appropriately 
            // (e.g. throw exception)
            if(_log.isDebugEnabled()) {
            	_log.debug("Header in CSV file: "+ Arrays.toString(header_temp));
                _log.debug("Expected header not found in the CSV file.");
            }

        	SessionErrors.add(request,"expected-header-not-found-in-the-csv-file");
        	try {
                inFile.close();
              } catch (IOException e) {
              }
        	return null;
        }

        if(_log.isDebugEnabled()) {
            _log.debug("Reading users with properties: " + Arrays.toString(header) + " from CSV file.");
        }
        CsvUserBean user;
        while ((user = inFile.read(CsvUserBean.class, header, processors)) != null) {
          users.add(user);
        }
        if(_log.isDebugEnabled()) {
            _log.debug(users.size() + " users were read from CSV file.");
        }
      } catch (FileNotFoundException fnfe) {
    	  if(_log.isErrorEnabled()) {
    		  _log.error("Can't find CSV file with users " + fnfe);
    	  }
      } catch (IOException ioe) {
  		ioe.printStackTrace();
      } catch (PortalException e) {
		e.printStackTrace();
	} catch (SystemException e) {
		e.printStackTrace();
	} finally {
        try {
          inFile.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
      }

      return users;
  }
  static String decodeUrl(String url) {
	        String decoded = url;
	        if (url != null && url.indexOf('%') >= 0) {
	            int n = url.length();
	            StringBuffer buffer = new StringBuffer();
	            ByteBuffer bytes = ByteBuffer.allocate(n);
	            for (int i = 0; i < n;) {
	                if (url.charAt(i) == '%') {
	                    try {
                        do {
	                            byte octet = (byte) Integer.parseInt(url.substring(i + 1, i + 3), 16);
	                            bytes.put(octet);
	                            i += 3;
	                        } while (i < n && url.charAt(i) == '%');
	                        continue;
	                    } catch (RuntimeException e) {
	                        // malformed percent-encoded octet, fall through and
	                        // append characters literally
	                    } finally {
	                        if (bytes.position() > 0) {
	                            bytes.flip();
	                            buffer.append(UTF8.decode(bytes).toString());
	                            bytes.clear();
	                        }
	                    }
	                }
	                buffer.append(url.charAt(i++));
	            }
	            decoded = buffer.toString();
	        }
	        return decoded;
	    }
}
