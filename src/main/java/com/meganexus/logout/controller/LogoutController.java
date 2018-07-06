package com.meganexus.logout.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meganexus.login.service.LoginService;
import com.meganexus.logout.service.LogoutService;
import com.meganexus.toDo.controller.ToDoController;
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.utils.ResponseMessageJson;
import com.meganexus.utils.ResponseMessageString;
import com.meganexus.utils.SessionKey;

@RestController
@RequestMapping("/logout")
public class LogoutController {
	private static final Logger LOGGER = Logger.getLogger(ToDoController.class);
	@Resource(name="logoutService")
    private LogoutService logoutService;
	@Autowired
	private CommonUtils commonUtils;
	/*@PostMapping(path="/authorize",produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)*/
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageString> logoutUser(HttpServletRequest request) throws Exception {
		LOGGER.info("ToDoController getToDoTasks Executing");
		//HttpSession session = request.getSession(false);
		String logoutStatus = null;
		String PAGE_FORWARD = null;
	    logoutStatus = logoutService.logoutUser(request);
		if(logoutStatus.equals(Messages.SUCCESS_LOGOUT)){
		   PAGE_FORWARD =  Messages.LOGIN_PAGE;
		   //session.invalidate();
		  }else{
			 PAGE_FORWARD = Messages.LOGOUT_FAILURE_PAGE;
			 }
	    return ResponseEntity.ok().body(commonUtils.populateResponse(logoutStatus,PAGE_FORWARD));
			}

}
