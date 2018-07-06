package com.meganexus.utils;

public interface Messages {
	String WRONG_CREDENTIALS = "The username or password you entered is incorrect.";
	String WRONG_PASSWORD = "Incorrect passsword, Please try again.";
	String CHAR_NOT_ALLOWED_FN = "Invalid Number. Only Numbers are allowed";
	
	String MISSING_CREDENTIALS = "Please provide the username and password.";
	String SAVE_SUCCESS = "Successfully Saved.";
	String SAVE_FAIL = "Save Failed";
	String SUCCESS_LOGIN = "Login successful.";
	
	String SUCCESS_LOGOUT = "You have successfully logged out.";
	String LOGOUT_FAIL = "Logout Failed";
	
	String ACCESS_RESTRICTED = "UnAuthorized Access.";
	String INCORRECT_CUR_PASS = "Current password is incorrect.";
	String SAME_NEW_PASS = "New password is same as current password.";
	String UN_EXPECTED_ERROR = "Unexpected error occured.";
	String LOGIN_UN_EXPECTED_ERROR = "Unexpected error occured.";
	String LOGOUT_UN_EXPECTED_ERROR = "Unexpected error occured.";
	String EMPTY_KEY_VALUE = "Key value cannot be blank.";
	String SESSION_TMEOUT = "Session timed out. Please login again.";
	String SESSION_TMEOUT_MESSAGE = "SESSION TIMEOUT";
	String STATUS_OK = "OK";
	String OK_CODE = "200";

	String STATUS_NOTFOUND = "NOT FOUND";
	String NOTFOUND_CODE   = "404";
	
	String STATUS_CREATED = "CREATED";
	String CREATED_CODE   = "201";
	
	//String STATUS_UNAUTHORIZED = "Unauthorized Access";
	String UNAUTHORIZED_CODE = "401";
	
	String SATUS_ERROR = "Error";
	String ERROR_CODE  = "500";
	
	String LOGIN_SUCCESS_PAGE = "ToDo";
	String LOGIN_FAILURE_PAGE = "Login";
    String LOGOUT_FAILURE_PAGE = "Dashboard";	
    String LOGIN_PAGE = "Login";
	
	String EventProvider = "EventProvider";
	String EventCluster = "EventCluster";
	String EventLDU = "EventLDU";
	String EventTeam = "EventTeam";
	String EventManager = "EventManager";
	String EventSelect = "EventSelect";
	String checkStatus  = "CheckStatus";
	
	
	String TimeSensitiveCount = "TODOTimeSensitiveCount";
	String InterventionsCount = "TODOInterventionsCount";
	
	String allCount =  "All";
	String oneDayCount = "OneDay";
	String oneWeekCount = "OneWeek";
	String overDueCount = "OverDue";
    String lowRiskCount = "LowRisk";
    String highRiskCount = "HighRisk";
    String failingCount = "Failing";
    
    String SAVEFAIL = "Error";
    String DEFAULT_EVENTPROVIDER = "CPA Cheshire and Gtr Manchester";
    String DEFAULT_LEVEL = "EventProvider";
    
    String PARAMERROR = "Wrong value for parameter"; 
    
    String GRADE_ALL = "All";
    String GRADE_CM  = "CM";
    String GRADE_SCM = "SCM";
    
    String SelectedLevel_Provider = "EventProvider";
    String SelectedLevel_Cluster = "EventCluster";
    String SelectedLevel_LDU = "EventLDU";
    String SelectedLevel_Team = "EventTeam";
    String SelectedLevel_Manager = "EventManager";
    
    String DELETE_SUCCESS = "Deleted Successfully";
    String DELETE_FAIL = "Failed to Delete";
    
    String UPDATE_SUCCESS = "Updated Successfully";
    String UPDATE_FAIL = "Updation Failed";
    
    String MONTH_LAST = "Last Month";
    String MONTH_THIS = "This Month";
    
    String PFM_OUTCOME_ALL = "All";
    String PFM_OUTCOME_POSITIVE = "Positive";
    String PFM_OUTCOME_NEGATIVE = "Negative";
    String PFM_OUTCOME_NEUTRAL =  "Neutral";
    
    
    //Last Month or This Month
}
