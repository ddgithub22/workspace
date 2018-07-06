package com.meganexus.performance.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.meganexus.performance.dao.PerformanceDao;
import com.meganexus.performance.vo.MetricsVo;
import com.meganexus.performance.vo.PerfSelectPreference;
import com.meganexus.toDo.service.ToDoServiceImpl;

@Service("performanceService")
public class PerformanceServiceImpl implements PerformanceService {

	 private static final Logger LOGGER = Logger.getLogger(PerformanceServiceImpl.class);
		
	 @Resource(name="performanceDao")
	 private PerformanceDao performanceDao;

	@Override
	public JSONObject getPerformancePreferences() {
		return performanceDao.getPerformancePreferences();
	}

	@Override
	public ArrayList<MetricsVo> getMetrics(String month, String multiSelect, ArrayList<PerfSelectPreference> userList) {
		return performanceDao.getMetrics(month, multiSelect, userList);
	}

	@Override
	public ArrayList<LinkedHashMap<String, String>> getCaseList(String performanceId, String month,
			String selectedValue, String filter) {
		return performanceDao.getCaseList(performanceId, month, selectedValue, filter);
	}

	@Override
	public String saveNotes(String performanceId,String uniqueId,String Notes,int userId) {
		return performanceDao.insertNotes(performanceId, uniqueId, Notes,userId);
	}

	@Override
	public String updateNotes(String performanceId, String uniqueId, String Notes, int userId) {
		return performanceDao.updateNotes(performanceId, uniqueId, Notes, userId);
	}

	@Override
	public JSONObject selectNotes(String performanceId, String uniqueId) {
		return performanceDao.selectNotes(performanceId, uniqueId);
	}
	 
}
