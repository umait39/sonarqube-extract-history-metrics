package com.cqmetrics.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MetricsType {
	
	CLASSES("classes", Constants.INT),
	NCLOC("ncloc", Constants.INT),
    FUNCTIONS("functions",Constants.INT),
	FILES("files",Constants.INT),
	DIRECTORIES("directories",Constants.INT),
	COMMENT_LINES_DENSITY("comment_lines_density",Constants.PERCENT),
	CODE_SMELLS("code_smells", Constants.INT),
	NEW_CODE_SMELLS("new_code_smells", Constants.INT),
	SQALE_RATING("sqale_rating", Constants.RATING),
	NEW_MAINTAINABILITY_RATING("new_maintainability_rating", Constants.RATING),
	SQALE_INDEX("sqale_index", Constants.DAYS),
	NEW_TECHNICAL_DEBT("new_technical_debt", Constants.DAYS),
	SQALE_DEBT_RATIO("sqale_debt_ratio", Constants.PERCENT),
	NEW_SQALE_DEBT_RATIO("new_sqale_debt_ratio", Constants.PERCENT),
	BUGS("bugs", Constants.INT),
	NEW_BUGS("new_bugs", Constants.INT),
	RELIABILITY_RATING("reliability_rating", Constants.RATING),
	NEW_RELIABILITY_RATING("new_reliability_rating", Constants.RATING),
	RELIABILITY_REMEDIATION_EFFORT("reliability_remediation_effort", Constants.DAYS),
	NEW_RELIABILITY_REMEDIATION_EFFORT("new_reliability_remediation_effort", Constants.DAYS),
	VULNERABILITIES("vulnerabilities", Constants.INT),
	NEW_VULNERABILITIES("new_vulnerabilities", Constants.INT),
	SECURITY_RATING("security_rating", Constants.RATING),
	NEW_SECURITY_RATING("new_security_rating", Constants.RATING),
	SECURITY_REMEDIATION_EFFORT("security_remediation_effort", Constants.DAYS),
	NEW_SECURITY_REMEDIATION_EFFORT("new_security_remediation_effort", Constants.DAYS),
	NEW_VIOLATIONS("new_violations", Constants.INT),
	NEW_BLOCKER_VIOLATIONS("new_blocker_violations", Constants.INT),
	NEW_CRITICAL_VIOLATIONS("new_critical_violations", Constants.INT),
	NEW_MAJOR_VIOLATIONS("new_major_violations", Constants.INT),
	NEW_MINOR_VIOLATIONS("new_minor_violations", Constants.INT),
	VIOLATIONS("violations", Constants.INT),
	BLOCKER_VIOLATIONS("blocker_violations", Constants.INT),
	CRITICAL_VIOLATIONS("critical_violations", Constants.INT),
	MAJOR_VIOLATIONS("major_violations", Constants.INT),
	MINOR_VIOLATIONS("minor_violations", Constants.INT),
	OPEN_ISSUES("open_issues", Constants.INT),
	REOPENED_ISSUES("reopened_issues", Constants.INT),
	TESTS("tests", Constants.INT),
	SKIPPED_TESTS("skipped_tests", Constants.INT),
	UNCOVERED_LINES("uncovered_lines", Constants.INT),
	TEST_ERRORS("test_errors", Constants.INT),
	TEST_SUCCESS_DENSITY("test_success_density", Constants.PERCENT),
	COVERAGE("coverage", Constants.PERCENT),
	NEW_COVERAGE("new_coverage", Constants.PERCENT),
	LINE_COVERAGE("line_coverage", Constants.PERCENT),
	NEW_LINE_COVERAGE("new_line_coverage", Constants.PERCENT),
	BRANCH_COVERAGE("branch_coverage", Constants.PERCENT),
	NEW_BRANCH_COVERAGE("new_branch_coverage", Constants.PERCENT),
	DUPLICATED_LINES_DENSITY("duplicated_lines_density", Constants.PERCENT),
	DUPLICATED_BLOCKS("duplicated_blocks", Constants.INT),
	DUPLICATED_LINES("duplicated_lines", Constants.INT),
	NEW_DUPLICATED_LINES("new_duplicated_lines", Constants.INT),
	NEW_DUPLICATED_LINES_DENSITY("new_duplicated_lines_density", Constants.INT),
	COMPLEXITY("complexity", Constants.INT),
	FUNCTION_COMPLEXITY("function_complexity", Constants.DOUBLE),
	CLASS_COMPLEXITY("class_complexity", Constants.DOUBLE),
	FILE_COMPLEXITY("file_complexity", Constants.DOUBLE), 
	COGNITIVE_COMPLEXITY("cognitive_complexity", Constants.INT),
	COMPLEXITY_IN_CLASSES("complexity_in_classes", Constants.INT),
	COMPLEXITY_IN_FUNCTIONS("complexity_in_functions", Constants.INT),
	AFFERENT_COUPLINGS("afferent-couplings",Constants.INT),
	EFFERENT_COUPLINGS("efferent-couplings",Constants.INT),
	PACKAGE_DEPENDENCY_CYCLES("package-dependency-cycles",Constants.INT),
	PROJECTS("projects",Constants.INT),
	SECURITY_HOTSPOTS("security_hotspots",Constants.INT),
	SECURITY_HOTSPOTS_REVIEWED("security_hotspots_reviewed",Constants.PERCENT),
	NEW_SECURITY_HOTSPOTS_REVIEWED("new_security_hotspots_reviewed",Constants.PERCENT),
	NEW_SECURITY_HOTSPOTS("new_security_hotspots",Constants.INT),
	NEW_SECURITY_REVIEW_RATING("new_security_review_rating", Constants.RATING),
	SECURITY_REVIEW_RATING("security_review_rating", Constants.RATING),
	DEFAULT_TYPE("default_type",Constants.STRING);

	private static final Logger logger = LoggerFactory.getLogger(MetricsType.class);
	private final String metricKey;
    private final String metricType;

    /**
     * A mapping between the metric key and its corresponding type to facilitate lookup by code.
     */
    private static Map<String, String> keyToTypeMapping;

    private MetricsType(String key, String type){
        this.metricKey = key;
        this.metricType = type;
    }

    public static String getMetricsType(String key){
        if(keyToTypeMapping == null){
            initMapping();
        }
        return keyToTypeMapping.get(key);
    }

    private static void initMapping(){
    	
    	keyToTypeMapping = new HashMap<>();
        for(MetricsType m : values()){
        	keyToTypeMapping.put(m.metricKey, m.metricType);
        }
        logger.info("keyToTypeMapping: "+ keyToTypeMapping);
    }

    public String getMetricType(){
        return metricType;
    }

    public String getMetricKey(){
        return metricKey;
    }
	
}
