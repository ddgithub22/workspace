package com.meganexus.wmt.dao;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;

import com.meganexus.wmt.vo.WMTBandVo;
import com.meganexus.wmt.vo.WMTCashManagerVo;
import com.meganexus.wmt.vo.WMTSelectPreference;

public interface WMTDao {
	public ArrayList<WMTBandVo> getBandDetails(String multiSelect,String grade,ArrayList<WMTSelectPreference> userList);
	public ArrayList<LinkedHashMap<String, String>> getCaseList(String selectedValue, String grade);
	public ArrayList<WMTCashManagerVo> getCashManager(String selectedValue);
	public String deleteCashManager(String id);
	public String updateCashManager(String id,String startDate, String endDate, String hours,String type);
	public JSONObject getWMTPreferences();
}
