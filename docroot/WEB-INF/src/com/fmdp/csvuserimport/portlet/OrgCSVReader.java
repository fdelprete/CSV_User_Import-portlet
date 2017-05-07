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

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.fmdp.csvuserimport.portlet.model.CsvOrgBean;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;

/**
 * @author Filippo Maria Del Prete
 * 
 * based on the original work of Paul Butenko
 * http://java-liferay.blogspot.it/2012/09/how-to-make-users-import-into-liferay.html
 *
 */
public class OrgCSVReader {
  /** The Constant LOGGER. */
  private static final Log _log = LogFactoryUtil.getLog(OrgCSVReader.class);
  
  /** Singleton instance. */
  private static OrgCSVReader INSTANCE = new OrgCSVReader();

  private static final Charset UTF8 = Charset.forName("UTF-8");
  
  private OrgCSVReader() {
  }

  public static OrgCSVReader getInstance() {
    return INSTANCE;
  }

  final CellProcessor[] processors = new CellProcessor[] {
		  new NotNull(), null, null, null, null, 
		  null, null, null, null, null};

  public List<CsvOrgBean> readOrgs(final ActionRequest request, String Fname) {
    ICsvBeanReader inFile = null;

    List<CsvOrgBean> orgs = new ArrayList<CsvOrgBean>();
    
    if(_log.isDebugEnabled()) {
      _log.debug("Try to open the uploaded csv file");
    }
      String urldecoded = OrgCSVReader.decodeUrl(Fname.replace('/', File.separatorChar));
      	
    try {
    	String portletInstanceId = (String) request.getAttribute(WebKeys.PORTLET_ID);
    	PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletInstanceId);
    	String csvSeparator = preferences.getValue("csvSeparator", "EXCEL_NORTH_EUROPE_PREFERENCE");
    	String address1CsvStatus = preferences.getValue("address1CsvStatus","ignore");
    	String address2CsvStatus = preferences.getValue("address2CsvStatus","ignore");
    	String cityCsvStatus = preferences.getValue("cityCsvStatus","ignore");
    	String zipCsvStatus = preferences.getValue("zipCsvStatus","ignore");
    	String stateCsvStatus = preferences.getValue("stateCsvStatus","ignore");
    	String countryCsvStatus = preferences.getValue("countryCsvStatus","ignore");
    	String phoneCsvStatus = preferences.getValue("phoneCsvStatus","ignore");
    	String faxCsvStatus = preferences.getValue("faxCsvStatus","ignore");
    	String commentsCsvStatus = preferences.getValue("commentsCsvStatus","ignore");
    	
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


        String impAddress1 = null;
        if (address1CsvStatus.equals("optional")){
        	impAddress1 = "address1";
        }
        String impAddress2 = null;
        if (address2CsvStatus.equals("optional")){
        	impAddress2 = "address2";
        }
        String impCity = null;
        if (cityCsvStatus.equals("optional")){
        	impCity = "city";
        }
        String impZip = null;
        if (zipCsvStatus.equals("optional")){
        	impZip = "zip";
        }
        String impState = null;
        if (stateCsvStatus.equals("optional")){
        	impState = "state";
        }
        String impCountry = null;
        if (countryCsvStatus.equals("optional")){
        	impCountry = "country";
        }
        String impPhone = null;
        if (phoneCsvStatus.equals("optional")){
        	impPhone = "phone";
        }
        String impFax = null;
        if (faxCsvStatus.equals("optional")){
        	impFax = "fax";
        }
        String impComments = null;
        if (commentsCsvStatus.equals("optional")){
        	impComments = "comments";
        }

        final String[] header = new String[10];
        header[0] = "companyname";
        header[1] = impAddress1;
        header[2] = impAddress2;
        header[3] = impCity;
        header[4] = impZip;
        header[5] = impState; 
        header[6] = impCountry; 
        header[7] = impPhone;
        header[8] = impFax;
        header[9] = impComments;
    	if(_log.isDebugEnabled()) {
        	_log.debug("Header for mapping: "+ Arrays.toString(header));
        }
    	inFile = new CsvBeanReader(new FileReader(urldecoded), pref);
       
    	// header verify
    	final String[] header_temp = inFile.getHeader(true);
    	List<String> expectedHeaders = Arrays.asList("companyname","address1","address2","city",
        		"zip","state","country","phone"
        		,"fax","comments");

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
            	  _log.error(e);
              }
        	return null;
        }

        if(_log.isDebugEnabled()) {
            _log.debug("Reading orgs with properties: " + Arrays.toString(header) + " from CSV file.");
        }
        CsvOrgBean org;
        boolean goLoop;
        goLoop = true;
        long curRow = 0;
        while (goLoop) {
        	try {
        		curRow += 1;
	        	org = inFile.read(CsvOrgBean.class, header, processors);
	        	if (org != null) {
	        		orgs.add(org);
	        	} else {
	        		goLoop = false;
	        	}

        	} catch (SuperCsvConstraintViolationException eCV) {
            	_log.error("NON right VALUE ENCOUNTERD ON ROW "+ inFile.getRowNumber() + " --- "+  eCV.getMessage());

        		SessionErrors.add(request,"non_right_value_ecountered_on_row");
            	request.setAttribute("error_row", curRow);
            	try {
                    inFile.close();
                  } catch (IOException e) {
                	  _log.error(e);
                  }
            	return null;
        	} catch (SuperCsvCellProcessorException ePE){
        		 _log.error("PARSER EXCEPTION ON ROW "+inFile.getRowNumber() + " --- "+  ePE.getMessage());
         		SessionErrors.add(request,"parser_exception_on_row");
            	request.setAttribute("error_row", curRow);
            	try {
                    inFile.close();
                  } catch (IOException e) {
                	  _log.error(e);
                  }
            	return null;

        	} catch (SuperCsvException ex){
        		 _log.error("ERROR ON ROW "+inFile.getRowNumber() + " --- "+  ex.getMessage());
         		SessionErrors.add(request,"error_on_row");
            	request.setAttribute("error_row", curRow);
            	try {
                    inFile.close();
                  } catch (IOException e) {
                	  _log.error(e);
                  }
            	return null;

        		 }

        }
        if(_log.isDebugEnabled()) {
            _log.debug(orgs.size() + " orgs were read from CSV file.");
        }
      } catch (FileNotFoundException fnfe) {
    	  if(_log.isErrorEnabled()) {
    		  _log.error("Can't find CSV file with orgs " + fnfe);
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

      return orgs;
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