package com.cqmetrics.exceptions;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqmetrics.utils.Constants;

/**
 * Exception Handling Class
 * @author krishnanu
 *
 */
public class SonarMetricsException extends Exception{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SonarMetricsException.class);
	
	

	 public SonarMetricsException(String customMessage, Exception e) {
		super(e);
		logger.error(Constants.EXCEPTION_OCCURED+Constants.NEW_LINE+Constants.ERROR_MESSAGE+ Constants.PLACE_HOLDER+Constants.NEW_LINE+Constants.EXCEPTION_MESSAGE
				+ Constants.PLACE_HOLDER,customMessage,e.getMessage()+Constants.SEPARATOR);
		
	}
	
	 /***
	  * 
	  * @param exception
	  */
	 public SonarMetricsException(IOException exception) 
	 {
		 super(exception);	 
	
 	 } 

	/**
	 * Handles all Exceptions
	 * @param exception
	 */
	 public SonarMetricsException(Exception exception) {
	  	  super(exception);	  	  
	
	}
}