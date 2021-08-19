package com.cqmetrics.conf;


public class AppProperties {
  
	
	private String baseuri;	
	private String metrics;
	private String jdependmetrics;	
	private String projectKeyList;
	private Boolean jDependFlag;
	private String compArgument;
	private String runMode;
	private String projectKey;
	private String fromArgument;
	private String toArgument;
	private String fromDate;
	private String toDate;	
	
	
	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getFromArgument() {
		return fromArgument;
	}

	public void setFromArgument(String fromArgument) {
		this.fromArgument = fromArgument;
	}

	public String getToArgument() {
		return toArgument;
	}

	public void setToArgument(String toArgument) {
		this.toArgument = toArgument;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getRunMode() {
		return runMode;
	}

	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	public String getBaseuri() {
		return baseuri;
	}

	public void setBaseuri(String baseuri) {
		this.baseuri = baseuri;
	}

	public String getMetrics() {
		return metrics;
	}

	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}

	public String getjdependMetrics() {
		return jdependmetrics;
	}

	public void setjdependMetrics(String jdependmetrics) {
		this.jdependmetrics = jdependmetrics;
	}

	public String getProjectKeyList() {
		return this.projectKeyList;
	}

	public void setProjectKeyList(String projectKeyList) {
		this.projectKeyList = projectKeyList;
	}


	public Boolean getjDependFlag() {
		return jDependFlag;
	}

	public void setjDependFlag(Boolean jDependFlag) {
		this.jDependFlag = jDependFlag;
	}
 
	public String getCompArgument() {
		return compArgument;
	}

	public void setCompArgument(String compArgument) {
		this.compArgument = compArgument;
	}
	

}