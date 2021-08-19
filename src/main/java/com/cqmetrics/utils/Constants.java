package com.cqmetrics.utils;


public class Constants {

	

	/**
	 * Declare the Constants
	 *  
	 */
	public static final String URL_NOT_VALID = "Invalid Url Exception";
	public static final String CONNECTION_REFUSED = "Connection Refused. Check SonarServer is Up" ;
	public static final String IO_EXCEPTION_EXCEL="IO Exception while wrting Excel";
	public static final String EXCEPTION_OCCURED = "\n\n*************** An Exception Occured : *****************\n ";
	public static final String ERROR_MESSAGE = "Error Message : ";
	public static final String EXCEPTION_MESSAGE = "Exception Message : ";
	public static final String ERROR_CODE = "Error Code : ";
	public static final String URL = "URL: ";
	public static final String SEPARATOR ="\n\n*********************************************************\n";
	public static final String PLACE_HOLDER="{}";
	public static final String METRICS_MAP_DETAILS ="*********Metrics Details *************";
	public static final String NEW_LINE="\n";
	public static final String WEB_SERVICE_URL="Web Service URL : ";
	public static final String CALLING_API_JDEPEND_METRICS = "Calling Sonar API with JDepend Metrics for Project :: ";
	public static final String CALLING_API_WITHOUT_JDEPEND_METRICS = "Calling Sonar API without JDepend Metrics for Project :: ";
	public static final String COMMA_SEPARATOR = ",";
	public static final String COLON_SEPARATOR = " : ";
	public static final String INSIDE_FUNCTION = "Inside Function";
	public static final String EXEL_GENERATED_SUCCESSFULLY = "Metrics Extracted to Excel Successfully";
	public static final String ZERO_VALUE = "0";
	public static final String PROJECT_ID = "id";
	public static final String PROJECT_NAME = "name";
	public static final String PROJECT_KEY = "key";
	public static final String RATING = "rating";
	public static final String INT="int";
	public static final String PERCENT ="percent";
	public static final String DAYS = "days";
	public static final String STRING = "string";
	public static final String DOUBLE = "double";
	public static final String JSON_INPUT="json_input";
	public static final String JSON_DATA="json_data";
	public static final String LIVE_DATA = "live_data";

			
	private static int totalProjectCnt = 0;
	
	
	public static int getTotalProjectCnt() {
		return totalProjectCnt;
	}
	public static void setTotalProjectCnt(int totalProjectCnt) {
		Constants.totalProjectCnt = totalProjectCnt;
	}

	
	
}
