package com.meganexus.performance.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;

import com.meganexus.performance.vo.MetricsVo;
import com.meganexus.performance.vo.PerfSelectPreference;

public interface PerformanceService {
	
	public JSONObject getPerformancePreferences(); 
	public ArrayList<MetricsVo> getMetrics(String month,String multiSelect,ArrayList<PerfSelectPreference> userList);
	public ArrayList<LinkedHashMap<String, String>> getCaseList(String performanceId,String month,String selectedValue,String filter);
	public String saveNotes(String performanceId,String uniqueId,String Notes,int userId);
	public String updateNotes(String performanceId,String uniqueId,String Notes,int userId);
	public JSONObject selectNotes(String performanceId,String uniqueId);
	
}
