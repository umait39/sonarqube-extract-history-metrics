package com.cqmetrics;

 
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqmetrics.conf.AppProperties;
import com.cqmetrics.domain.SonarMetrics;
import com.cqmetrics.exceptions.SonarMetricsException;
import com.cqmetrics.utils.Constants;
import com.cqmetrics.utils.Util;


public class RestAPIMetricsExtractor {

	private static final Logger logger = LoggerFactory.getLogger(RestAPIMetricsExtractor.class);
	
	private AppProperties appProperties = null;

	public RestAPIMetricsExtractor(AppProperties appProps) {
		this.appProperties = appProps;

	}

	public void getMetricsFromRestAPIForSpecificDate() throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "getMetricsFromRestAPIForSpecificDate ");
		try {
			
			SonarMetrics sonarMetrics =  consumerSonarWebAPI();		
			Util.populateMetricsMap(sonarMetrics, appProperties.getProjectKey());
						
			if (Boolean.TRUE.equals(appProperties.getjDependFlag())) {
				Util.writeIntoExcel(
						appProperties.getMetrics() + Constants.COMMA_SEPARATOR + appProperties.getjdependMetrics());
			} else {
				Util.writeIntoExcel(appProperties.getMetrics());
			}
		
			
		} 
		catch(NotFoundException e) {
			throw new SonarMetricsException("Please check if correct Project Key is given", e);
		}
		catch(BadRequestException e) {
			throw new SonarMetricsException("URL is not correct - Please check if all required parameters are given correctly in property file ", e);
		}
		catch(ProcessingException e) {
			throw new SonarMetricsException("Check if Sonarserver is Up", e);
		}
		catch (Exception e) {
			logger.error("Exception in getMetricsFromRestAPIForSpecificDate : ", e);
			
		}

	}

private SonarMetrics consumerSonarWebAPI()   {
		
		
		Client client = ClientBuilder.newClient();
		String urlString = getSonarWebServiceUrl();
		logger.info("URL:  {}",urlString);
		WebTarget target = client.target(urlString);	
	    return target.request(MediaType.APPLICATION_JSON).get(SonarMetrics.class);
		
	}


private String getSonarWebServiceUrl(  ) {
		logger.info(Constants.INSIDE_FUNCTION+Constants.COLON_SEPARATOR+ "getSonarWebServiceUrl");

		if (Boolean.TRUE.equals(appProperties.getjDependFlag())) {
			logger.info("{} {}",Constants.CALLING_API_JDEPEND_METRICS,appProperties.getProjectKey());
			return new StringBuilder().append(appProperties.getBaseuri().trim())
					.append(appProperties.getMetrics().trim())
					.append(Constants.COMMA_SEPARATOR)
					.append(appProperties.getjdependMetrics().trim())
					.append(appProperties.getCompArgument().trim())
					.append(appProperties.getProjectKey().trim())
					.append(appProperties.getFromArgument().trim())
					.append(appProperties.getFromDate().trim())
					.append(appProperties.getToArgument().trim())
					.append(appProperties.getToDate().trim())					
					.toString();
		} else {
			logger.info("{} {}",Constants.CALLING_API_WITHOUT_JDEPEND_METRICS,appProperties.getProjectKey());
			return new StringBuilder().append(appProperties.getBaseuri().trim())
					.append(appProperties.getMetrics().trim())
					.append(appProperties.getCompArgument().trim())
					.append(appProperties.getProjectKey().trim())
					.append(appProperties.getFromArgument().trim())
					.append(appProperties.getFromDate().trim())
					.append(appProperties.getToArgument().trim())
					.append(appProperties.getToDate().trim())					
					.toString();
		}

		
	}



	

}
