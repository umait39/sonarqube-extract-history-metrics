package com.cqmetrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqmetrics.conf.AppProperties;
import com.cqmetrics.utils.Constants;
import com.cqmetrics.utils.Util;

public class SonarMetricsExtraction{

	private static final Logger logger = LoggerFactory.getLogger(SonarMetricsExtraction.class);
	 
	public static void main(String[] args) {
		
	      logger.info("Inside main of Metrics Extraction Application");    
	     
	      AppProperties appProperties  = null;
	      try {       
	    	   appProperties = Util.readAndLoadPropertiesFile();
	 		   logger.info("Run Mode is {} ",appProperties.getRunMode());
	    	   if(appProperties.getRunMode().equalsIgnoreCase(Constants.JSON_DATA))   {	    		   
	    		   JsonMetricsExtractor jsonMetricsExtractor = new JsonMetricsExtractor(appProperties);
	    		   jsonMetricsExtractor.getMetricsFromMultipleJson();		   
	    		}else  if(appProperties.getRunMode().equalsIgnoreCase(Constants.LIVE_DATA))   {	   
	    			RestAPIMetricsExtractor restAPIMetricsExtractor = new RestAPIMetricsExtractor(appProperties);
	    			restAPIMetricsExtractor.getMetricsFromRestAPIForSpecificDate();
	    		}else {
	    			 logger.error("\n\n***** Please Specify Proper Run mode for Execution in the Property file ******** \n" );
	    		}
	    	   
	      }  catch (Exception e) {
	    	  logger.error("Exception while Processing ",e);
	      }
	
	 	
}
}
