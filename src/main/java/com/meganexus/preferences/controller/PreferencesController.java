package com.meganexus.preferences.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meganexus.login.model.User;
import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.preferences.service.GlobalPreferenceService;
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.utils.ResponseMessage;
import com.meganexus.utils.ResponseMessageJson;
import com.meganexus.utils.ResponseMessageString;
import com.meganexus.utils.SessionKey;
import com.meganexus.utils.UsersUtil;

@RestController
@RequestMapping("/preferences")
public class PreferencesController {
	
private static final Logger LOGGER = Logger.getLogger(PreferencesController.class);
	
	@Resource(name = "globalPreferenceService")
    private GlobalPreferenceService globalPreferenceService;
	
	@Autowired
	private CommonUtils commonUtils;
	
	@Autowired
	private UsersUtil usersUtil;
	
	@GetMapping(path="/getGlobalPreferences",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> getGlobalPreference(HttpServletRequest request){
		LOGGER.info("PreferencesController getGlobalPreference Executing");
		JSONObject globalJson = globalPreferenceService.getGlobalService();
		return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK,globalJson));
	}
	
	@PostMapping(path="/saveGlobalPreferences",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<ResponseMessageString> saveGlobalPreferences(@RequestParam String preferenceDetails,
			HttpServletRequest request) throws Exception {
		    LOGGER.info("PreferencesController saveGlobalPreferences Executing");
		    //LOGGER.info("preferenceDetails : "+preferenceDetails);
            if(usersUtil.validateToken(request))   		
            {
            AppUserDetails userDetails = usersUtil.getCreatedUser(request);
		    String saveStatus = null;
			JSONParser parser = new JSONParser();
			UserPreference userPreference = null;
			User user = null;
			//ArrayList<UserPreference> usersList = null;
			String eventProvider = Messages.EventProvider;
			String eventCluster = Messages.EventCluster;
			String eventLDU = Messages.EventLDU;
			String eventTeam = Messages.EventTeam;
			String eventManager = Messages.EventManager;
			String userId = "";
			ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails,Integer.parseInt(userDetails.getUserId()));
			saveStatus = globalPreferenceService.saveGlobalPreferences(usersList,Integer.parseInt(userDetails.getUserId()));
			return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK, saveStatus));
		} 
		else{
			return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponseErrorString(Messages.ACCESS_RESTRICTED,true),HttpStatus.UNAUTHORIZED);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	
	@GetMapping(path = "/loadSelectedPreferences", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> loadSelectedPreferences(HttpServletRequest request) throws Exception {
		boolean tokenValidation = true;
		ArrayList recordList = null;
		if (usersUtil.validateToken(request)){
			AppUserDetails userDetails = usersUtil.getCreatedUser(request);
			recordList = globalPreferenceService.loadSelectedPreferences(userDetails.getUserId());
			return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK, recordList));
		} else {
			return new ResponseEntity<ResponseMessage>(commonUtils.populateErrorResponse(Messages.ACCESS_RESTRICTED, true), HttpStatus.UNAUTHORIZED);
		}
	}
	
	  /**
     * To Get usersList
     * @param preferenceDetails
     * @param userId
     * @return
     */
	public ArrayList<UserPreference> getPreferencesListFromArray(String preferenceDetails,int userId){
		UserPreference userPreference = null;
		User user = null;
		ArrayList<UserPreference> usersList = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		try {
			usersList = new ArrayList<UserPreference>();
			if(!(preferenceDetails.equals("1")))
			{
				JSONParser parser = new JSONParser();
				JSONArray arrayObj=null;
				Object object=null;
				//JSONObject json = (JSONObject) parser.parse(preferenceDetails);
				object=parser.parse(preferenceDetails);
				arrayObj=(JSONArray) object;
				for(int i=0;i<arrayObj.size();i++){
					JSONObject jsonPart = (JSONObject) arrayObj.get(i);
					userPreference = new UserPreference();
					user = new User();
					if (jsonPart.get(Messages.EventProvider) != null) {
						eventProvider = (String) jsonPart.get(Messages.EventProvider);
						userPreference.setProvider(eventProvider);
					} else {
						userPreference.setProvider(null);
					}
					if (jsonPart.get(Messages.EventCluster) != null) {
						eventCluster = (String) jsonPart.get(Messages.EventCluster);
						userPreference.setCluster(eventCluster);
					} else {
						userPreference.setCluster(null);
					}
					if (jsonPart.get(Messages.EventLDU) != null) {
						eventLDU = (String) jsonPart.get(Messages.EventLDU);
						userPreference.setLdu(eventLDU);
					} else {
						userPreference.setLdu(null);
					}
					if (jsonPart.get(Messages.EventTeam) != null) {
						eventTeam = (String) jsonPart.get(Messages.EventTeam);
						userPreference.setTeam(eventTeam);
					} else {
						userPreference.setTeam(null);
					}
					if (jsonPart.get(Messages.EventManager) != null) {
						eventManager = (String) jsonPart.get(Messages.EventManager);
						userPreference.setManager(eventManager);
					} else {
						userPreference.setManager(null);
					}
					user.setUserId(userId);
					userPreference.setUser(user);
					usersList.add(userPreference);
				}
			}else{
				userPreference = new UserPreference();
				user = new User();
				userPreference.setProvider(Messages.DEFAULT_EVENTPROVIDER);
				userPreference.setCluster("");
				userPreference.setLdu("");
				userPreference.setTeam("");
				userPreference.setManager("");
				userPreference.setUser(user);
				usersList.add(userPreference);
			}
		} catch (ParseException pe) {
			LOGGER.error("Parse Exception at PreferencesController : " + pe.getCause(), pe);
			//return ResponseEntity.ok().body(Messages.SATUS_ERROR);
		} catch (Exception ex) {
			LOGGER.error("Exception at PreferencesController : " + ex.getCause(), ex);
			//return ResponseEntity.ok().body(commonUtils.populateErrorResponse(Messages.SATUS_ERROR));
		}
		return usersList;
	}
	
	  /**
     * To Get usersList
     * @param preferenceDetails
     * @param userId
     * @return
     */
	public ArrayList<UserPreference> getPreferencesListFromObject(String preferenceDetails,int userId){
		UserPreference userPreference = null;
		User user = null;
		ArrayList<UserPreference> usersList = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		JSONParser parser = new JSONParser();
		try {
			usersList = new ArrayList<UserPreference>();
			if(!(preferenceDetails.equals("1")))
			{
			JSONObject json = (JSONObject) parser.parse(preferenceDetails);
		
			Set<String> keys = new TreeSet<>(json.keySet());
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				userPreference = new UserPreference();
				user = new User();
				JSONObject jsonPart = (JSONObject) json.get(iter.next());
				if (jsonPart.get(Messages.EventProvider) != null) {
					eventProvider = (String) jsonPart.get(Messages.EventProvider);
					userPreference.setProvider(eventProvider);
				} else {
					userPreference.setProvider(null);
				}
				if (jsonPart.get(Messages.EventCluster) != null) {
					eventCluster = (String) jsonPart.get(Messages.EventCluster);
					userPreference.setCluster(eventCluster);
				} else {
					userPreference.setCluster(null);
				}
				if (jsonPart.get(Messages.EventLDU) != null) {
					eventLDU = (String) jsonPart.get(Messages.EventLDU);
					userPreference.setLdu(eventLDU);
				} else {
					userPreference.setLdu(null);
				}
				if (jsonPart.get(Messages.EventTeam) != null) {
					eventTeam = (String) jsonPart.get(Messages.EventTeam);
					userPreference.setTeam(eventTeam);
				} else {
					userPreference.setTeam(null);
				}
				if (jsonPart.get(Messages.EventManager) != null) {
					eventManager = (String) jsonPart.get(Messages.EventManager);
					userPreference.setManager(eventManager);
				} else {
					userPreference.setManager(null);
				}
				user.setUserId(userId);
				userPreference.setUser(user);
				usersList.add(userPreference);
			}
			}else{
				userPreference = new UserPreference();
				user = new User();
				userPreference.setProvider(Messages.DEFAULT_EVENTPROVIDER);
				userPreference.setCluster("");
				userPreference.setLdu("");
				userPreference.setTeam("");
				userPreference.setManager("");
				userPreference.setUser(user);
				usersList.add(userPreference);
			}
		} catch (ParseException pe) {
			LOGGER.error("Parse Exception at PreferencesController : " + pe.getCause(), pe);
			//return ResponseEntity.ok().body(Messages.SATUS_ERROR);
		} catch (Exception ex) {
			LOGGER.error("Exception at PreferencesController : " + ex.getCause(), ex);
			//return ResponseEntity.ok().body(commonUtils.populateErrorResponse(Messages.SATUS_ERROR));
		}
		return usersList;
	}
	
 }
