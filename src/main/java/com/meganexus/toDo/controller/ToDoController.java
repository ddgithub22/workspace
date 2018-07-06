package com.meganexus.toDo.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meganexus.login.model.User;
import com.meganexus.login.service.LoginService;
import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.toDo.service.ToDoService;
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.utils.ResponseMessage;
import com.meganexus.utils.ResponseMessageJson;
import com.meganexus.utils.ResponseMessageSingle;
import com.meganexus.utils.ResponseMessageString;
import com.meganexus.utils.SessionKey;
import com.meganexus.utils.UsersUtil;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.meganexus.toDo.vo.GroupCountsVo;
import com.meganexus.toDo.vo.ToDoGroupCountsVo;
import com.meganexus.toDo.vo.ToDoSheetsVo;

/**
 * @author arunkumar.k
 * @version 1.0
 */

@RestController
@PropertySource("classpath:application-${spring.profiles.active}.properties")
@RequestMapping("/toDo")
public class ToDoController {
	
	private static final Logger LOGGER = Logger.getLogger(ToDoController.class);

	@Resource(name = "toDoService")
	private ToDoService toDoService;
 
	@Autowired
	private CommonUtils commonUtils;

	@Value("${excelFile.downloadPath}")
	private String excelDownloadPath;

	@Value("${file.poolSize}")
	private String FILES_POOL_SIZE;
	
	@Autowired
	private UsersUtil usersUtil;
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	/**
	 * 
	 * @return ResponseMessageJson
	 * @throws Exception 
	 */
	
	/*@GetMapping(path="/getToDoTasks",produces = MediaType.APPLICATION_JSON_VALUE)*/
	@PostMapping(path="/getToDoTasks",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> toDoTasks(@RequestParam(value="preferenceDetails",defaultValue = "1") String preferenceDetails,HttpServletRequest request) throws Exception {
		 LOGGER.info("ToDoController getToDoTasks Executing");
		 JSONObject totalSheet = null;
		 if(usersUtil.validateToken(request)){
		    AppUserDetails userDetails = usersUtil.getCreatedUser(request);
		    int userId = Integer.parseInt(userDetails.getUserId());
           ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails,userId);
		  totalSheet = toDoService.getTotalSheets(userId,usersList);
		  return new ResponseEntity<ResponseMessageJson>(commonUtils.populateResponseJson(Messages.STATUS_OK, totalSheet),HttpStatus.OK);
		}else{
		  return new ResponseEntity<ResponseMessageJson>(commonUtils.populateResponseJson(Messages.ACCESS_RESTRICTED, totalSheet),HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @return ResponseMessage
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	/*@GetMapping(path="/getGroupWiseCount",produces = MediaType.APPLICATION_JSON_VALUE)*/
	@PostMapping(path="/getGroupWiseCount",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> groupWiseCount(@RequestParam(value="preferenceDetails",defaultValue = "1") String preferenceDetails,HttpServletRequest request) throws NumberFormatException, Exception {
		LOGGER.info("ToDoController getGroupWiseCount Executing");
		 //int userId = Integer.parseInt(userDetails.getUserId());
		 //boolean validToken = true;
		if(usersUtil.validateToken(request)){
		  AppUserDetails userDetails = usersUtil.getCreatedUser(request);
	     //int userId=1; // hard coded
		  String userId = userDetails.getUserId();
		 ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails,Integer.parseInt(userId));
		 ArrayList<GroupCountsVo> groupCounts = null;
		 groupCounts = (ArrayList<GroupCountsVo>) toDoService.getGroupWiseCount(Integer.parseInt(userId),usersList);
		 return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_OK, groupCounts),HttpStatus.OK);
	    }
	    else{
	    	 return new ResponseEntity<ResponseMessage>(commonUtils.populateErrorResponse(Messages.ACCESS_RESTRICTED,true),HttpStatus.OK);
	    }
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	//@GetMapping(path="/getOverallTask",produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(path="/getOverallTask",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> overallTask(@RequestParam(value="preferenceDetails",defaultValue = "1") String preferenceDetails,HttpServletRequest request) throws NumberFormatException, Exception {
		LOGGER.info("ToDoController getOverallTask Executing");
		JSONObject totalCount = null;
		//boolean validToken = true;
		if(usersUtil.validateToken(request)){
		 AppUserDetails userDetails = usersUtil.getCreatedUser(request);
		 int userId = Integer.parseInt(userDetails.getUserId());
		 //int userId=1; // hard coded
		 ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails,userId);
		 totalCount = toDoService.getOverallTask(userId,usersList);
		 return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK, totalCount));
	    }else{
	     return new ResponseEntity<ResponseMessageJson>(commonUtils.populateErrorResponseJson(Messages.ACCESS_RESTRICTED,true),HttpStatus.OK);
	    }
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	
	/*@GetMapping(path="/groupFilterCounts",produces = MediaType.APPLICATION_JSON_VALUE)*/
	@PostMapping(path="/groupFilterCounts",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> groupFilterCounts(@RequestParam(value="preferenceDetails",defaultValue = "1") String preferenceDetails,HttpServletRequest request) throws NumberFormatException, Exception {
		LOGGER.info("ToDoController getGroupFilterCounts Executing");
		JSONObject groupFilterCounts = null;
		 //int userId = Integer.parseInt(userDetails.getUserId());
		if(usersUtil.validateToken(request)){
			 AppUserDetails userDetails = usersUtil.getCreatedUser(request);
			 int userId = Integer.parseInt(userDetails.getUserId());
			 ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails,userId);
		     groupFilterCounts = toDoService.getGroupFilterCounts(userId,usersList);
		     return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK, groupFilterCounts));
		}else{
			 return new ResponseEntity<ResponseMessageJson>(commonUtils.populateErrorResponseJson(Messages.ACCESS_RESTRICTED,true),HttpStatus.OK);
		}
	} 
	
	    /*@GetMapping(path="/sortRecords/{fileInfoId}/{countType}",produces = MediaType.APPLICATION_JSON_VALUE)*/
	    @PostMapping(path="/sortRecords/{fileInfoId}/{countType}",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<ResponseMessage> sortRecords(@PathVariable("fileInfoId") String fileInfoId,@PathVariable("countType") String countType,@RequestParam(value="sortColumnname",defaultValue = "1") String sortColumnname,@RequestParam(value="sortOrder",defaultValue = "1") String sortOrder,@RequestParam String preferenceDetails,HttpServletRequest request) throws Exception {
		LOGGER.info("ToDoController getToDoSheetRecords Executing");
		ArrayList toDoRecords = null;
		if(usersUtil.validateToken(request)){
		 AppUserDetails userDetails = usersUtil.getCreatedUser(request);
		int userId = Integer.parseInt(userDetails.getUserId());	 
		int fileInfoid = 0;
		if(sortColumnname==null || sortOrder==null){
			sortColumnname = "1";
			sortOrder="1";
		}
		try {
			if (fileInfoId != null && StringUtils.isInteger(fileInfoId)){
				fileInfoid = Integer.parseInt(fileInfoId);
			}
		} catch (NumberFormatException Nfe) {
			LOGGER.error("Exception at processing : " + Nfe, Nfe.getCause());
		}
		   if(countType.equalsIgnoreCase("allCount")){
			   countType = Messages.allCount;
		   }else if(countType.equalsIgnoreCase("oneDayCount")){
			   countType = Messages.oneDayCount;
		   }else if(countType.equalsIgnoreCase("oneWeekCount")){
			   countType = Messages.oneWeekCount;
		   }else if(countType.equalsIgnoreCase("overDueCount")){
			   countType = Messages.overDueCount;
		    }else if(countType.equalsIgnoreCase("lowRiskCount")){
		    	countType = Messages.lowRiskCount;
		    }else if(countType.equalsIgnoreCase("highRiskCount")){
		    	countType = Messages.highRiskCount;
		    }else if(countType.equalsIgnoreCase("failingCount")){
		    	countType = Messages.failingCount;
		    }
		  ArrayList<UserPreference> usersList =  getPreferencesListFromArray(preferenceDetails,userId);
		  toDoRecords = toDoService.getTodoSheetPreferences(fileInfoid, countType, sortColumnname, sortOrder, usersList,userId);
		  if (toDoRecords == null) {
		    return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_NOTFOUND,new ArrayList()));
			//return new ResponseEntity<ResponseMessage>(commonUtils.populateResponse(Messages.STATUS_NOTFOUND,new ArrayList()),HttpStatus.OK);
		     }
		    return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK,toDoRecords));
		   }
		   else{
		 return new ResponseEntity<ResponseMessage>(commonUtils.populateErrorResponse(Messages.ACCESS_RESTRICTED,true),HttpStatus.OK);
		   }
	}
	
	/**
	 * 
	 * @param fileInfoId
	 * @param response
	 * @throws Exception 
	 */
	//@GetMapping(path = "/getToDoExporttoExcel/{fileInfoId}/{countType}")
	@GetMapping(path="/getToDoExporttoExcel/{fileInfoId}/{countType}",produces = MediaType.APPLICATION_JSON_VALUE)
	public void toDoExporttoExcel(@PathVariable("fileInfoId") String fileInfoId,@PathVariable("countType") String countType,@RequestParam String preferenceDetails,HttpServletRequest request,HttpServletResponse response) throws Exception {
		LOGGER.info("ToDoController toDoExporttoExcel Executing");
		int fileInfoid = 0;
		FileInputStream fis = null;
		if (usersUtil.validateToken(request)) {
			AppUserDetails userDetails = usersUtil.getCreatedUser(request);
			try {
				if (fileInfoId != null && StringUtils.isInteger(fileInfoId)) {
					fileInfoid = Integer.parseInt(fileInfoId);
				}
				int userId = Integer.parseInt(userDetails.getUserId());
				ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails, userId);
				String toDoFile = toDoService.getToDoExporttoExcel(fileInfoid, excelDownloadPath, FILES_POOL_SIZE,
						countType, usersList, userId);
				fis = new FileInputStream(new File(excelDownloadPath.trim() + "/" + toDoFile + ".xlsx"));
				response.addHeader("Content-disposition", "attachment;filename=" + toDoFile + ".xlsx");
				response.setContentType("application/vnd.ms-excel");
				// Copy the stream to the response's output stream.
				IOUtils.copy(fis, response.getOutputStream());
				response.flushBuffer();
			} catch (NumberFormatException Nfe) {
				LOGGER.error("Exception at processing file Id : " + Nfe.getCause(), Nfe);
			} catch (Exception ex) {
				LOGGER.error("Exception at processing file Id : " + ex.getCause(), ex);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException ioe) {
						LOGGER.error("IO Exception while copying file:" + ioe, ioe.getCause());
					}
				}
			}
		}
	}
	
	@GetMapping(path="/getToDoPreferences",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageJson> getToDoPreferences(HttpServletRequest request){
		LOGGER.info("PreferencesController getGlobalPreference Executing");
		JSONObject prefJson = toDoService.getToDoPreferences();
		return ResponseEntity.ok().body(commonUtils.populateResponseJson(Messages.STATUS_OK,prefJson));
	}
	
	/*@GetMapping(path="/getToDoSheetRecords/{fileInfoId}/{countType}",produces = MediaType.APPLICATION_JSON_VALUE)*/
	@PostMapping(path="/getToDoSheetRecords/{fileInfoId}/{countType}",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> toDoSheetByPreference(@PathVariable("fileInfoId") String fileInfoId,@PathVariable("countType") String countType,@RequestParam String preferenceDetails,HttpServletRequest request) throws Exception {
	    LOGGER.info("ToDo Controller saveGlobalPreferences Executing");
	    boolean tokenValidation = true;
	    ArrayList toDoRecords = null;
	    if (usersUtil.validateToken(request)) {
		AppUserDetails userDetails = usersUtil.getCreatedUser(request);
		String sortColumnname="1";
		String sortOrder="1";
		//int userId = 1;
		int userId = Integer.parseInt(userDetails.getUserId());
		ArrayList<UserPreference> usersList = getPreferencesListFromArray(preferenceDetails,userId);
	    if(countType.equalsIgnoreCase("allCount")){
			   countType = Messages.allCount;
		   }else if(countType.equalsIgnoreCase("oneDayCount")){
			   countType = Messages.oneDayCount;
		   }else if(countType.equalsIgnoreCase("oneWeekCount")){
			   countType = Messages.oneWeekCount;
		   }else if(countType.equalsIgnoreCase("overDueCount")){
			   countType = Messages.overDueCount;
		    }else if(countType.equalsIgnoreCase("lowRiskCount")){
		    	countType = Messages.lowRiskCount;
		    }else if(countType.equalsIgnoreCase("highRiskCount")){
		    	countType = Messages.highRiskCount;
		    }else if(countType.equalsIgnoreCase("failingCount")){
		    	countType = Messages.failingCount;
		    }
		int fileInfoid = Integer.parseInt(fileInfoId);
		toDoRecords = toDoService.getTodoSheetPreferences(fileInfoid, countType, sortColumnname, sortOrder, usersList,userId);
		return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK,toDoRecords));
	} 
	else{
		return new ResponseEntity<ResponseMessage>(commonUtils.populateErrorResponse(Messages.ACCESS_RESTRICTED),HttpStatus.UNAUTHORIZED);
	  }
	}
	
	@PostMapping(path="/saveCheckList",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageString> savecheckList(@RequestParam(value="fileInfoId",defaultValue = "0") String fileInfoId,@RequestParam(value="FileId",defaultValue = "0") String FileId,@RequestParam(value="checkStatus",defaultValue = "0") String checkStatus,HttpServletRequest request) throws Exception {
		LOGGER.info("ToDoController savecheckList executing"); 
		String status = null; 
		try{
			if(usersUtil.validateToken(request)){
				LOGGER.info("fileInfoId : "+fileInfoId +"FileId :"+FileId+ "checkStatus :"+checkStatus);
				if(fileInfoId.equals("0") || FileId.equals("0")){
					return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponseErrorString(Messages.PARAMERROR,true),HttpStatus.OK);
				}
				AppUserDetails userDetails = usersUtil.getCreatedUser(request);
				int userId  = Integer.parseInt(userDetails.getUserId()); 
		        status =  toDoService.saveCheckList(Integer.parseInt(fileInfoId),userId,Integer.parseInt(FileId),checkStatus);
			}
			else{
				return new ResponseEntity<ResponseMessageString>(commonUtils.populateResponseErrorString(Messages.ACCESS_RESTRICTED,true),HttpStatus.UNAUTHORIZED);
			}
			}
		catch(Exception ex){
			LOGGER.error("Exception at savecheckList" + ex.getCause(), ex);
			return ResponseEntity.ok().body(commonUtils.populateResponseErrorString(Messages.SATUS_ERROR,true));
		}
		return ResponseEntity.ok().body(commonUtils.populateResponse(Messages.STATUS_OK,status));
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
			LOGGER.error("Parse Exception at ToDoController getPreferencesListFromArray: " + pe.getCause(), pe);
			//return ResponseEntity.ok().body(Messages.SATUS_ERROR);
		} catch (Exception ex) {
			LOGGER.error("Exception at ToDoController getPreferencesListFromArray : " + ex.getCause(), ex);
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
			LOGGER.error("Parse Exception at usersList : " + pe.getCause(), pe);
			//return ResponseEntity.ok().body(Messages.SATUS_ERROR);
		} catch (Exception ex) {
			LOGGER.error("Exception at usersList : " + ex.getCause(), ex);
			//return ResponseEntity.ok().body(commonUtils.populateErrorResponse(Messages.SATUS_ERROR));
		}
		return usersList;
	}
	
}
