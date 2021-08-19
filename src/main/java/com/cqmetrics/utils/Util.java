package com.cqmetrics.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqmetrics.conf.AppProperties;
import com.cqmetrics.domain.SonarMetrics;
import com.cqmetrics.exceptions.SonarMetricsException;

public class Util {

	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	private static Map<String, String> sonarMetricsMap = null;
	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;

	private Util() {
		
	}

	
	/**
	 * From the metrics map, get the value for each metrics in the property file and
	 * write into excel
	 * 
	 * @param metricsStr
	 * @param rowCnt
	 * @throws SonarMetricsException
	 */
	public static void writeIntoExcel(String metricsStr) throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "writeIntoExcel ");

		String[] metricList =metricsStr.split( Constants.COMMA_SEPARATOR);

		
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("Metrics");
			createHeaderRow(metricList);
			createMetricDataRows(metricList);
			outputToExcelFile();

	}
	
	
	/**
	 * From the metrics map, get the value for each metrics in the property file and
	 * write into excel
	 * 
	 * @param metricsStr
	 * @param rowCnt
	 * @throws SonarMetricsException
	 */
	public static void writeIntoExcel(String metricsStr, int rowCnt) throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "writeIntoExcel ");

		String[] metricList =metricsStr.split( Constants.COMMA_SEPARATOR);

		
		if(rowCnt ==0) {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("Metrics");
			createHeaderRow(metricList);				
		}
		createMetricDataRows(metricList,rowCnt);
		
		if (rowCnt + 1 == Constants.getTotalProjectCnt())
			outputToExcelFile();

		
	}
	
	//For Multiple json files call this function
		public static void writeIntoExcel(String metricsStr,boolean lastfileReached,int fileCounter) throws SonarMetricsException {
			logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "writeIntoExcel ");

			String[] metricList =metricsStr.split( Constants.COMMA_SEPARATOR);

			
				if(fileCounter ==0) {
					workbook = new XSSFWorkbook();
					sheet = workbook.createSheet("Metrics");
					createHeaderRow(metricList);				
				}
				
				createMetricDataRows(metricList,fileCounter);
				
				if(lastfileReached) 
					outputToExcelFile();

		}
		

	/**
	 * Creates the header row of the excel 
	 * @param metricList
	 */
	private static void createHeaderRow(String[] metricList) {

		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "createHeaderRow");
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		setCustomCellStyle(cellStyle, 12, true, BorderStyle.THIN, IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(0);
		int cellnum = 0;

		// Create header cell for Project Key
		Cell cell = row.createCell(cellnum++);
		cell.setCellValue("Project Key");
		cell.setCellStyle(cellStyle);

		// Create header cell for Metrics
		for (String metric : metricList) {
			cell = row.createCell(cellnum++);
			cell.setCellValue(metric);
			cell.setCellStyle(cellStyle);

		}

	}
	
	/**
	 * Function to create data rows in excel
	 * 
	 * @param metricList
	 * @param rowCnt
	 */
	private static void createMetricDataRows(String[] metricList) {
		int cellnum = 0;
		logger.info("{}{}createMetricDataRows",Constants.INSIDE_FUNCTION , Constants.COLON_SEPARATOR );

		CellStyle cellStyle = workbook.createCellStyle();
		setCustomCellStyle(cellStyle, 12, false, BorderStyle.THIN, IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(1);

		Cell cell = row.createCell(cellnum++);
		cell.setCellValue(sonarMetricsMap.get(Constants.PROJECT_KEY));
		cell.setCellStyle(cellStyle);

		logger.info(Constants.METRICS_MAP_DETAILS);
		logger.info("Project Key: {}" , sonarMetricsMap.get(Constants.PROJECT_KEY));
		String metricValue = null;
		for (String metricKey : metricList) {

			cell = row.createCell(cellnum++);
			metricValue = sonarMetricsMap.get(metricKey);
			if (metricValue != null) {
				setMetricDataforCell(cell, metricKey);
				logger.info("{}{}{}",metricKey , Constants.COLON_SEPARATOR , metricValue);
			} else {
				cell.setCellValue(Integer.valueOf(Constants.ZERO_VALUE));
				logger.info("{}{}{}",metricKey , Constants.COLON_SEPARATOR , Constants.ZERO_VALUE);
			}

			cell.setCellStyle(cellStyle);
		}

	}

	
	/**
	 * Function to create data rows in excel
	 * 
	 * @param metricList
	 * @param rowCnt
	 */
	private static void createMetricDataRows(String[] metricList,int rowCnt) {
		int cellnum = 0;
		logger.info("{}{}createMetricDataRows",Constants.INSIDE_FUNCTION , Constants.COLON_SEPARATOR );

		CellStyle cellStyle = workbook.createCellStyle();
		setCustomCellStyle(cellStyle, 12, false, BorderStyle.THIN, IndexedColors.BLACK.getIndex());

		Row row = sheet.createRow(rowCnt+1);

		Cell cell = row.createCell(cellnum++);
		cell.setCellValue(sonarMetricsMap.get(Constants.PROJECT_KEY));
		cell.setCellStyle(cellStyle);

		logger.info(Constants.METRICS_MAP_DETAILS);
		logger.info("Project Key: {}" , sonarMetricsMap.get(Constants.PROJECT_KEY));
		String metricValue = null;
		for (String metricKey : metricList) {

			cell = row.createCell(cellnum++);
			metricValue = sonarMetricsMap.get(metricKey);
			if (metricValue != null) {
				setMetricDataforCell(cell, metricKey);
				logger.info("{}{}{}",metricKey , Constants.COLON_SEPARATOR , metricValue);
			} else {
				cell.setCellValue(Integer.valueOf(Constants.ZERO_VALUE));
				logger.info("{}{}{}",metricKey , Constants.COLON_SEPARATOR , Constants.ZERO_VALUE);
			}

			cell.setCellStyle(cellStyle);
		}

	}

	private static void setMetricDataforCell(Cell cell, String metricKey) {

		String metricValue = sonarMetricsMap.get(metricKey);
		try {

			   	String metricType = MetricsType.getMetricsType(metricKey);
				if (metricType == null) {
					metricType = MetricsType.DEFAULT_TYPE.getMetricType();
					logger.info(" Metric Type not defined in enum class for Key {}  Setting to default String Type " , metricKey);
				}
				switch (metricType) {
				
				case Constants.RATING:
					cell.setCellValue(getRatingValue(metricValue));
					break;
				case Constants.INT:
					cell.setCellValue(Double.valueOf(metricValue).intValue());
					break;
				case Constants.PERCENT:
					cell.setCellValue(Math.round(Double.valueOf(metricValue) * 100.0) / 100.0);
					break;
				case Constants.DOUBLE:
					cell.setCellValue(Math.round(Double.valueOf(metricValue) * 100.0) / 100.0);
					break;
				case Constants.DAYS:
					cell.setCellValue(getEffortValue(metricValue));
					/**
					 * CellStyle textFormatStyle = workbook.createCellStyle();
					 * textFormatStyle.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text"))
					 * ; cell.setCellStyle(textFormatStyle);
					 */
					break;
				case Constants.STRING:
					cell.setCellValue(metricValue);
					break;
				default:
					cell.setCellValue(metricValue);
				}
			
		} catch (NumberFormatException e) {
			/* Setting the value received from API in case of conversion exception */
			logger.error("Error in converting to number values. Setting to string value ");
			cell.setCellValue(metricValue);

		}

	}

	/**
	 * Converts the minutes into days,hours and minutes
	 * @param minutes
	 * @return
	 */
	private static String getEffortValue(String minutes) {
		

		int time = 0;
		int hrs = 0;
		int days = 0;
		int mins = 0;
		int rem = 0;
		StringBuilder sb = new StringBuilder();
		if (minutes != null) {
			time = Integer.parseInt(minutes);
			if (time > 0) {
				days = time / 1440;
				rem = time % 1440;
				hrs = rem / 60;
				mins = rem % 60;

				sb.append(days + "D:");				
				sb.append(hrs + "H:");		
				sb.append(mins + "M");
			}
		}
		if (sb.length() > 0)
			return sb.toString();
		else
			return minutes;
	}

	/**
	 * Returns appropriate Rating Value as per Sonar defined Standards.
	 * @param ratingValue
	 * @return
	 */
	private static String getRatingValue(String ratingValue) {

		int ratingNumber = Double.valueOf(ratingValue).intValue();
		
	    switch (ratingNumber) {
		case 1:
			return "A";
		case 2:
			return "B";
		case 3:
			return "C";
		case 4:
			return "D";
		case 5:
			return "E";
		default:
			return "";
		}

	}

	private static void outputToExcelFile() throws SonarMetricsException {

		try (FileOutputStream out = new FileOutputStream(new File("SonarMetrics.xlsx"))) {
			// Write the workbook in file system
			logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "outputToExcelFile");

			workbook.write(out);

    		workbook.close();

		} catch (IOException e) {
			throw new SonarMetricsException(e);
		}

	}

	private static void setCustomCellStyle(CellStyle cellStyle, int fontHeight, boolean fontBoldflag,
			BorderStyle borderStyle, int bottomBorderColor) {

		Font font = sheet.getWorkbook().createFont();
		font.setBold(fontBoldflag);
		font.setFontHeightInPoints((short) fontHeight);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setBorderBottom(borderStyle);
		cellStyle.setBorderTop(borderStyle);
		cellStyle.setBorderRight(borderStyle);
		cellStyle.setBorderLeft(borderStyle);
		cellStyle.setBottomBorderColor((short) bottomBorderColor);
		

	}

	 public static AppProperties readAndLoadPropertiesFile() throws SonarMetricsException {
		 
		  logger.info("Inside readAndLoadPropertiesFile ");

	      Properties prop = null;
	      AppProperties appProps = new AppProperties();
	      try( FileInputStream fis = new FileInputStream("./application.properties")) {
	        
	         prop = new Properties();
	         prop.load(fis);
	         loadProperties(prop,appProps);

	        
	      } catch(FileNotFoundException fnfe) {
	    	  throw new SonarMetricsException("Property file not found in root folder  ",fnfe);
	      } catch(IOException ioe) {
	    	  throw new SonarMetricsException(ioe);
	      } 
	      
	      return appProps;
	   }

	private static void loadProperties(Properties prop, AppProperties appProps) throws SonarMetricsException{
		logger.info("Inside loadPropertiesFile ");
		 try {
	
		appProps.setBaseuri(prop.getProperty("baseuri"));
		appProps.setRunMode(prop.getProperty("runmode"));
		appProps.setMetrics(prop.getProperty("metrics"));
		appProps.setCompArgument(prop.getProperty("compArgument"));
		appProps.setjDependFlag(Boolean.valueOf(prop.getProperty("jdependFlag")));
		appProps.setjdependMetrics(prop.getProperty("jdependmetrics"));
		appProps.setProjectKey(prop.getProperty("projectKey"));
		appProps.setFromArgument(prop.getProperty("fromArgument"));
		appProps.setToArgument(prop.getProperty("toArgument"));
		appProps.setFromDate(prop.getProperty("fromDate"));
		appProps.setToDate(prop.getProperty("toDate"));
	    appProps.setProjectKeyList(prop.getProperty("projectKeyList"));

		 }
		catch(Exception e) {
			throw new SonarMetricsException("Error in loading property from Properties ",e);
		}	
}

	
	
	/**
	 * Read the Sonarmetrics POJO returned from WebSerive and populate it in
	 * HashMap.
	 * 
	 * @param sonarMetrics
	 * @throws SonarMetricsException 
	 */
	public static void populateMetricsMap(SonarMetrics sonarMetrics,String projectKey) throws SonarMetricsException  {

		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "populateMetricsMap ");
        try {
		sonarMetricsMap = new HashMap<>();
		sonarMetricsMap.put(Constants.PROJECT_KEY, projectKey);
		
		String value =null; 
	
		for (int i =0;i<sonarMetrics.getMeasures().size();i++) {
			 value = (sonarMetrics.getMeasures().get(i).getHistory().get(0).getValue()==null)? "0" :sonarMetrics.getMeasures().get(i).getHistory().get(0).getValue();
			 sonarMetricsMap.put(sonarMetrics.getMeasures().get(i).getMetric(),value);			
		}			
        }catch(IndexOutOfBoundsException e) {
			throw new SonarMetricsException("Please check if correct Date is given: From Date < To Date/ Proper date on which analysis done is given",e);

        }
	}

	
}
