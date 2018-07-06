package com.meganexus.preferences.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meganexus.login.model.User;
import com.meganexus.performance.model.Staffhierarchy;
import com.meganexus.preferences.dao.GlobalPreferencesDao;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.utils.Messages;


@Service("globalPreferenceService")
public class GlobalPreferenceServiceImpl implements GlobalPreferenceService{

	private static final Logger logger = Logger.getLogger(GlobalPreferenceServiceImpl.class);
	
	@Autowired
   	public  GlobalPreferencesDao globalPreferencesDao;
    
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.performance.service.GlobalPreferenceService#getStaffHierarchy()
	 */
	@Override
	public List<Staffhierarchy> getStaffHierarchy() {
		return globalPreferencesDao.getStaffGlobalPreferences();
	}

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.preferences.service.GlobalPreferenceService#getGlobalService()
	 */
	@Override
	public JSONObject getGlobalService() {
		return globalPreferencesDao.getGlobalPreferences();
	}
	
	@Override
	public String saveGlobalPreferences(ArrayList<UserPreference> userList,int userId) {
		int RecordCount = globalPreferencesDao.saveUserPreference(userList,userId);
		String saveStatus = null;
		 if(RecordCount==0){
			 saveStatus = Messages.SAVE_FAIL;
		  }else{
			  saveStatus = Messages.SAVE_SUCCESS;
		  }
		return saveStatus;
	}

	@Override
	public ArrayList loadSelectedPreferences(String userId) {
		return globalPreferencesDao.loadSelectedPreferences(userId);
	}
}
