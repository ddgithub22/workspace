package com.meganexus.preferences.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.meganexus.performance.model.Staffhierarchy;
import com.meganexus.preferences.model.UserPreference;

public interface GlobalPreferenceService {
	
     public List<Staffhierarchy> getStaffHierarchy();
     public JSONObject getGlobalService();
     public String saveGlobalPreferences(ArrayList<UserPreference> userList,int userId);
     public ArrayList loadSelectedPreferences(String userId);
}
