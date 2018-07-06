package com.meganexus.wmt.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meganexus.login.model.User;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.utils.ResponseMessage;
import com.meganexus.utils.ResponseMessageJson;
import com.meganexus.utils.ResponseMessageString;
import com.meganexus.wmt.service.WMTService;
import com.meganexus.wmt.vo.WMTBandVo;
import com.meganexus.wmt.vo.WMTCashManagerVo;
import com.meganexus.wmt.vo.WMTSelectPreference;

@RestController
@RequestMapping("/wmt")
public class WMTController{
	
	private static final Logger LOGGER = Logger.getLogger(WMTController.class);
	
	@Autowired
	private CommonUtils commonUtils;
	
	@Resource(name = "wmtService")
	private WMTService wmtService;

	/**
	 * To load wmtBands
	 * @param request
	 * @return
	 */
	
	@PostMapping(path="/Bands",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> wmtBands(@RequestParam(value="selectedValue",defaultValue="1") String selectedValue,@RequestParam(value="multiSelect",defaultValue="0") String multiSelect,@RequestParam(value="grade",defaultValue=Messages.GRADE_ALL) String grade,HttpServletRequest request) {
		LOGGER.info("WMTController wmtBands Executing");
	    ArrayList<WMTSelectPreference> prefList = getPreferencesListFromArray(selectedValue);
		ArrayList<WMTBandVo> wmtBand = wmtService.getBandDetails(multiSelect,grade,prefList);
	    return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_OK, wmtBand),HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param selectedValue
	 * @param grade
	 * @param request
	 * @return
	 */
	
	@PostMapping(path="/caseList",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> caseList(@RequestParam(value="selectedValue") String selectedValue,@RequestParam(value="grade",defaultValue=Messages.GRADE_ALL) String grade,HttpServletRequest request) {
		LOGGER.info("WMTController caseList executing");
		ArrayList<LinkedHashMap<String, String>> wmtCaseList =  wmtService.getCaseList(selectedValue,grade) ;
		return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_OK, wmtCaseList),HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param selectedValue
	 * @param grade
	 * @param request
	 * @return
	 */
	@PostMapping(path="/cashManager",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> cashManager(@RequestParam(value="selectedValue",defaultValue="1") String selectedValue,@RequestParam(value="grade",defaultValue=Messages.GRADE_ALL) String grade,HttpServletRequest request) {
		LOGGER.info("WMTController cashManager executing");
		selectedValue = formSelectedValueCashManager(selectedValue);
		ArrayList<WMTCashManagerVo> wmtCaseManager =  wmtService.getCashManager(selectedValue);
		return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_OK, wmtCaseManager),HttpStatus.OK);
	}
	
	/**
	 * To Delete Cash Manager
	 * @param id
	 * @param request
	 * @return
	 */
	@PostMapping(path="/deleteCashManager",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageString> deleteCashManager(@RequestParam(value="id") String id,HttpServletRequest request) {
		 LOGGER.info("WMTController deleteCashManager executing");
		 String status = wmtService.deleteCashManager(id);
		 if(status.equals(Messages.DELETE_SUCCESS)){
			 return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponse(Messages.STATUS_OK, status),HttpStatus.OK);
		 }else{
			 return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponse(Messages.SATUS_ERROR, status),HttpStatus.OK); 
		 }
	}
	
	/**
	 * 
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @param hours
	 * @param type
	 * @param request
	 * @return
	 */
	@PostMapping(path="/updateCashManager",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageString> updateCashManager(@RequestParam(value="id") String id,@RequestParam(value="startDate") String startDate,@RequestParam(value="endDate") String endDate,@RequestParam(value="hours") String hours,@RequestParam(value="type") String type,HttpServletRequest request) {
	     LOGGER.info("updateCashManager executing");
	     String status = wmtService.updateCashManager(id, startDate, endDate, hours, type);
		 if(status.equals(Messages.UPDATE_SUCCESS)){
			 return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponse(Messages.STATUS_OK, status),HttpStatus.OK);
		 }else{
			 return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponse(Messages.SATUS_ERROR, status),HttpStatus.OK); 
		 }
	}
	
	
	@GetMapping(path="/WMTPreferences",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> getToDoPreferences(HttpServletRequest request){
		LOGGER.info("PreferencesController getGlobalPreference Executing");
		JSONObject prefJson = wmtService.getWMTPreferences();
		return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK,prefJson));
	}
	
	
	
	
	  /**
     * To Get usersList
     * @param preferenceDetails
     * @param userId
     * @return
     */
	public ArrayList<WMTSelectPreference> getPreferencesListFromArray(String preferenceDetails){
		//LOGGER.info("preferenceDetails : "+preferenceDetails);
		WMTSelectPreference userPreference = null;
		User user = null;
		ArrayList<WMTSelectPreference> usersList = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		String eventSelect   = Messages.EventSelect;
		
		try {
			usersList = new ArrayList<WMTSelectPreference>();
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
					userPreference = new WMTSelectPreference();
					
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
					if (jsonPart.get(Messages.EventSelect) != null) {
						eventSelect = (String) jsonPart.get(Messages.EventSelect);
						userPreference.setSelectedLevel(eventSelect);
					} else {
						userPreference.setSelectedLevel("");
					}
					
					usersList.add(userPreference);
				}
			}else{
				userPreference = new WMTSelectPreference();
				userPreference.setProvider(Messages.DEFAULT_EVENTPROVIDER);
				userPreference.setCluster("");
				userPreference.setLdu("");
				userPreference.setTeam("");
				userPreference.setManager("");
				userPreference.setSelectedLevel(Messages.DEFAULT_LEVEL);
				usersList.add(userPreference);
			}
		} catch (ParseException pe) {
			LOGGER.error("Parse Exception at WMTController getPreferencesListFromArray: " + pe.getCause(), pe);
			//return ResponseEntity.ok().body(Messages.SATUS_ERROR);
		} catch (Exception ex) {
			LOGGER.error("Exception at WMTController getPreferencesListFromArray : " + ex.getCause(), ex);
			//return ResponseEntity.ok().body(commonUtils.populateErrorResponse(Messages.SATUS_ERROR));
		}
		return usersList;
	}
	
	
	
	/**
	 * 
	 * @param preferenceDetails
	 * @return
	 */
	public ArrayList<WMTSelectPreference> getPreferencesListWMT(String preferenceDetails){
		WMTSelectPreference userPreference = null;
		ArrayList<WMTSelectPreference> usersList = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		String eventSelect   = Messages.EventSelect;
		
		JSONParser parser = new JSONParser();
		try {
			usersList = new ArrayList<WMTSelectPreference>();
			if(!(preferenceDetails.equals("1")))
			{
			JSONObject json = (JSONObject) parser.parse(preferenceDetails);
			Set<String> keys = new TreeSet<>(json.keySet());
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				userPreference = new WMTSelectPreference();
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
				
				if(jsonPart.get(Messages.EventSelect)!=null) { 
					eventSelect = (String) jsonPart.get(Messages.EventSelect);  
					userPreference.setSelectedLevel(eventSelect);
				}else{
					userPreference.setSelectedLevel(null);
				}
				
				usersList.add(userPreference);
			}
			}else{
				userPreference = new WMTSelectPreference();
				userPreference.setProvider(Messages.DEFAULT_EVENTPROVIDER);
				userPreference.setCluster("");
				userPreference.setLdu("");
				userPreference.setTeam("");
				userPreference.setManager("");
				userPreference.setSelectedLevel(Messages.DEFAULT_LEVEL);
				usersList.add(userPreference);
			}
		} catch (ParseException pe) {
			LOGGER.error("Parse Exception at usersList : " + pe.getCause(), pe);
			//return ResponseEntity.ok().body(Messages.SATUS_ERROR);
		} catch (Exception ex) {
			LOGGER.error("Exception at usersList : " + ex.getCause(), ex);
			//return ResponseEntity.ok().body(commonUtils.populateErrorResponse(Messages.SATUS_ERROR));
		}
		return usersList;
	}
	
	public String formSelectedValueCashManager(String selectedValue){
		if(selectedValue.equals("1")){
			selectedValue = "and Provider="+"'"+Messages.DEFAULT_EVENTPROVIDER+"'";
		}else{
			selectedValue = selectedValue.replace(Messages.EventProvider,"Provider").replace(Messages.EventCluster ,"Cluster").replace(Messages.EventLDU,"LDU").replace(Messages.EventTeam,"Team").replace(Messages.EventManager,"Manager");
			selectedValue = "and "+selectedValue;
		}
		return selectedValue;
	}
	
}
