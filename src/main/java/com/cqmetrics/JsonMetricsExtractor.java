package com.cqmetrics;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cqmetrics.conf.AppProperties;
import com.cqmetrics.domain.SonarMetrics;
import com.cqmetrics.exceptions.SonarMetricsException;
import com.cqmetrics.utils.Constants;
import com.cqmetrics.utils.Util;

public class JsonMetricsExtractor {

	private static final Logger logger = LoggerFactory.getLogger(JsonMetricsExtractor.class);
	private SonarMetrics sonarMetrics = null;
	private AppProperties appProperties = null;

	public JsonMetricsExtractor(AppProperties appProps) {
		this.appProperties = appProps;

	}

	public void getMetricsFromJson() throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "getMetricsFromJson ");
		try {
			ArrayList<String> jsonFileList = readJsonFilesFromRootFolder();
			sonarMetrics = parseJsonData(jsonFileList);
			Util.populateMetricsMap(sonarMetrics, appProperties.getProjectKey());
			if (Boolean.TRUE.equals(appProperties.getjDependFlag())) {
				Util.writeIntoExcel(
						appProperties.getMetrics() + Constants.COMMA_SEPARATOR + appProperties.getjdependMetrics());
			} else {
				Util.writeIntoExcel(appProperties.getMetrics());
			}
		} catch (Exception e) {
			logger.error("Exception in getMetricsFromJson : " , e);
		}

	}
	
	public void getMetricsFromMultipleJson() throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "getMetricsFromMultipleJson ");
		try {
			ArrayList<String> jsonFileList = readJsonFilesFromRootFolder();
			boolean lastFileReached = false;

			if (jsonFileList.isEmpty()) { 
				logger.info("\n***************************************************************************"
						+ "\n\nPlease keep atleast single json file in the root folder to extract metrics"
						+ "\n\n***************************************************************************");

			} else {

				for (int i = 0; i < jsonFileList.size(); i++) {
					
					sonarMetrics = parseMultipleJsonData(jsonFileList.get(i));
					Util.populateMetricsMap(sonarMetrics,jsonFileList.get(i));
					
					if (jsonFileList.size() == i + 1)
						lastFileReached = true;
					
					Util.writeIntoExcel(appProperties.getMetrics(), lastFileReached,i);

				}
			}

		} catch (Exception e) {
			throw new SonarMetricsException(e);

		}

	}	
	
	
	private SonarMetrics parseMultipleJsonData(String jsonFileName) throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "parseMultipleJsonData ");
		if (jsonFileName != null) {
			logger.info("Json File Name to Parse : {}", jsonFileName);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				sonarMetrics = objectMapper.readValue(Paths.get(jsonFileName).toFile(), SonarMetrics.class);
			} catch (IOException e) {
				logger.error("***Error in parsing Json Input File **** ");
				throw new SonarMetricsException( e);
			}

		}

		return sonarMetrics;
	}


	private SonarMetrics parseJsonData(ArrayList<String> jsonFileList) throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "parseJsonData ");
		if (jsonFileList != null && jsonFileList.size() > 1) {
			logger.info("\n***************************************************************************"
					+ "\n\nMultiple Json files Found. Please keep single json file to extract metrics"
					+ "\n\n***************************************************************************");
			System.exit(0);
		} else if (jsonFileList != null && jsonFileList.isEmpty()) {
			logger.info("\n***************************************************************************"
					+ "\n\nPlease keep json file in the root folder to extract metrics"
					+ "\n\n***************************************************************************");
			System.exit(0);
		} else if (jsonFileList != null){
			logger.info("Json File Name to Parse : {}" , jsonFileList.get(0));

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				sonarMetrics = objectMapper.readValue(Paths.get(jsonFileList.get(0)).toFile(), SonarMetrics.class);
			} catch (IOException e) {
				throw new SonarMetricsException("Error in parsing Json Input File ", e);
			}

		}

		return sonarMetrics;
	} 

	private ArrayList<String> readJsonFilesFromRootFolder() {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "readJsonFilesFromRootFolder ");

		ArrayList<String> jsonFileList = new ArrayList<>();
		File file = new File("./");
		String[] files = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".json");
			}
		});

		Collections.addAll(jsonFileList, files);
		return jsonFileList;
	}

}
