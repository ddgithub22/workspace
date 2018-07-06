package com.meganexus.wmt.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.meganexus.wmt.dao.WMTDao;
import com.meganexus.wmt.vo.WMTBandVo;
import com.meganexus.wmt.vo.WMTCashManagerVo;
import com.meganexus.wmt.vo.WMTSelectPreference;

@Service("wmtService")
public class WMTServiceImpl implements WMTService{

	@Resource(name="wmtDao")
	private WMTDao wmtDao;
	
	@Override
	public ArrayList<WMTBandVo> getBandDetails(String multiSelect,String grade,
			ArrayList<WMTSelectPreference> prefList) {
		return wmtDao.getBandDetails(multiSelect,grade,prefList);
	}
	
	@Override
	public ArrayList<LinkedHashMap<String, String>> getCaseList(String selectedValue, String grade) {
		return wmtDao.getCaseList(selectedValue, grade);
	}

	@Override
	public ArrayList<WMTCashManagerVo> getCashManager(String selectedValue) {
		return wmtDao.getCashManager(selectedValue);
	}

	@Override
	public String deleteCashManager(String id) {
		return wmtDao.deleteCashManager(id);
	}

	@Override
	public String updateCashManager(String id,String startDate, String endDate, String hours,String type) {
		return wmtDao.updateCashManager(id, startDate, endDate, hours, type);
	}

	@Override
	public JSONObject getWMTPreferences() {
		return wmtDao.getWMTPreferences();
	}

}
