package com.meganexus.preferences.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.meganexus.performance.model.Staffhierarchy;
import com.meganexus.preferences.model.UserPreference;

public interface GlobalPreferencesDao {
	 public List<Staffhierarchy> getStaffGlobalPreferences();
	 public JSONObject getGlobalPreferences(); 
	 public int saveUserPreference(ArrayList<UserPreference> userList,int userId);
	 public ArrayList loadSelectedPreferences(String userId); 
}
