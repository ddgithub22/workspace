package com.meganexus.performance.controller;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.performance.service.PerformanceService;
import com.meganexus.performance.vo.MetricsVo;
import com.meganexus.performance.vo.PerfSelectPreference;
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.utils.ResponseMessage;
import com.meganexus.utils.ResponseMessageJson;
import com.meganexus.utils.ResponseMessageString;
import com.meganexus.utils.UsersUtil;

@RestController
@RequestMapping("/performance")
public class PerformanceController{
	
	private static final Logger LOGGER = Logger.getLogger(PerformanceController.class);
	@Autowired
	private CommonUtils commonUtils;
	@Resource(name="performanceService")
	private PerformanceService performanceService;
	@Autowired
	private UsersUtil usersUtil;
	
	@GetMapping(path = "/getPerformancePreferences", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> getToDoPreferences(HttpServletRequest request) {
		LOGGER.info("PerformanceController getGlobalPreference Executing");
		JSONObject prefJson = performanceService.getPerformancePreferences();
		return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK, prefJson));
	}

	@PostMapping(path = "/Metrics", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> getMetrics(
			@RequestParam(value = "selectedValue", defaultValue = "1") String selectedValue,
			@RequestParam(value = "multiSelect", defaultValue = "0") String multiSelect,
			@RequestParam(value = "month", defaultValue = Messages.MONTH_THIS) String month,
			HttpServletRequest request) {
		LOGGER.info("PerformanceController getMetrics Executing");
		ArrayList<PerfSelectPreference> prefList = getPreferencesListFromArray(selectedValue);
		ArrayList<MetricsVo> metrics = performanceService.getMetrics(month, multiSelect, prefList);
		return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_OK, metrics),
				HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param selectedValue
	 * @param grade
	 * @param request
	 * @return
	 */
	
	@PostMapping(path="/caseList",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> caseList(@RequestParam(value="performanceId") String performanceId,@RequestParam(value="month",defaultValue=Messages.MONTH_THIS) String month,@RequestParam(value="selectedValue") String selectedValue,@RequestParam(value="filter",defaultValue=Messages.PFM_OUTCOME_ALL) String filter,HttpServletRequest request) {
		LOGGER.info("PerformanceController caseList executing");
		//ArrayList<LinkedHashMap<String, String>> wmtCaseList =  wmtService.getCaseList(selectedValue,grade) ;
        ArrayList<LinkedHashMap<String, String>> perfmCaseList =  performanceService.getCaseList(performanceId,month,selectedValue,filter);
		return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_OK, perfmCaseList),HttpStatus.OK);
	}

	
	@PostMapping(path="/addNote",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageString> addNotes(@RequestParam(value="performanceId") String performanceId,@RequestParam(value="UniqueId") String uniqueId,@RequestParam(value="Notes") String Notes,HttpServletRequest request) throws Exception{
	    LOGGER.info("PerformanceController addNotes Executing");
	    String saveStatus = null;
	    if(usersUtil.validateToken(request)){
	       AppUserDetails userDetails = usersUtil.getCreatedUser(request);
	       int userId =   Integer.parseInt(userDetails.getUserId());
	       saveStatus = performanceService.saveNotes(performanceId,uniqueId,Notes,userId);
	       return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK, saveStatus));  
        }else{
        	return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponseErrorString(Messages.ACCESS_RESTRICTED,true),HttpStatus.UNAUTHORIZED);
        }
	 }
	
	@PostMapping(path="/updateNote",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageString> updateNotes(@RequestParam(value="performanceId") String performanceId,@RequestParam(value="UniqueId") String uniqueId,@RequestParam(value="Notes") String Notes,HttpServletRequest request) throws Exception{
	    LOGGER.info("PerformanceController addNotes Executing");
	    String saveStatus = null;
	    if(usersUtil.validateToken(request)){
	       AppUserDetails userDetails = usersUtil.getCreatedUser(request);
	       int userId =   Integer.parseInt(userDetails.getUserId());
	       saveStatus = performanceService.updateNotes(performanceId,uniqueId,Notes,userId);
	       return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK, saveStatus));  
        }else{
        	return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponseErrorString(Messages.ACCESS_RESTRICTED,true),HttpStatus.UNAUTHORIZED);
        }
	 }
	
	@GetMapping(path="/selectNote",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> selectNote(@RequestParam(value="performanceId") String performanceId,@RequestParam(value="UniqueId") String uniqueId,HttpServletRequest request){
		LOGGER.info("PerformanceController selectNote Executing");
		 JSONObject  noteJson = performanceService.selectNotes(performanceId, uniqueId);
		 return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK, noteJson));
	}
	
   /**
     * To Get usersList
     * @param preferenceDetails
     * @param userId
     * @return
     */
	public ArrayList<PerfSelectPreference> getPreferencesListFromArray(String preferenceDetails) {
		PerfSelectPreference userPreference = null;
		ArrayList<PerfSelectPreference> usersList = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		String eventSelect = Messages.EventSelect;
		try {
			usersList = new ArrayList<PerfSelectPreference>();
			if (!(preferenceDetails.equals("1"))) {
				JSONParser parser = new JSONParser();
				JSONArray arrayObj = null;
				Object object = null;
				object = parser.parse(preferenceDetails);
				arrayObj = (JSONArray) object;
				for (int i = 0; i < arrayObj.size(); i++) {
					JSONObject jsonPart = (JSONObject) arrayObj.get(i);
					userPreference = new PerfSelectPreference();

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
			} else {
				userPreference = new PerfSelectPreference();
				userPreference.setProvider(Messages.DEFAULT_EVENTPROVIDER);
				userPreference.setCluster("");
				userPreference.setLdu("");
				userPreference.setTeam("");
				userPreference.setManager("");
				userPreference.setSelectedLevel(Messages.DEFAULT_LEVEL);
				usersList.add(userPreference);
			}
		} catch (ParseException pe) {
			LOGGER.error("Parse Exception at PerformanceController getPreferencesListFromArray: " + pe.getCause(), pe);
		} catch (Exception ex) {
			LOGGER.error("Exception at PerformanceController getPreferencesListFromArray : " + ex.getCause(), ex);
		}
		return usersList;
	}
}
