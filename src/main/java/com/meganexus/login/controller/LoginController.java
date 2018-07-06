package com.meganexus.login.controller;


import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.meganexus.login.dao.LoginDao;
import com.meganexus.login.service.LoginService;
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.utils.ResponseMessageString;
import com.microsoft.sqlserver.jdbc.StringUtils;

/**
 * @author arunkumar.k
 * @version 1.0
 */


/*@RestController
@RequestMapping("/login")*/
public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private CommonUtils commonUtils;
	
	@Resource(name="loginService")
    private LoginService loginService;
	  
	    @PostMapping(path="/authorize",produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResponseMessageString> authorizeUser(@RequestBody Map<String, String> userDetails,HttpSession session) {
		logger.info("LoginController Executing");
		String loginStatus = null;
		String userName = userDetails.get("username");
		String password = userDetails.get("password");
	    String PAGE_FORWARD = null;
		try {
	        if(userName==null || password==null)
	        {
	          loginStatus = Messages.MISSING_CREDENTIALS;
	          PAGE_FORWARD =  Messages.LOGIN_FAILURE_PAGE;
	          return ResponseEntity.ok().body(commonUtils.populateResponseLoginError(loginStatus,PAGE_FORWARD));
	        }
			else if (userName.equals(StringUtils.EMPTY) || password.equals(StringUtils.EMPTY)) {
				 loginStatus = Messages.MISSING_CREDENTIALS;
				 PAGE_FORWARD =  Messages.LOGIN_FAILURE_PAGE;
				 return ResponseEntity.ok().body(commonUtils.populateResponseLoginError(loginStatus,PAGE_FORWARD));
			} else {
				//loginStatus = loginService.authorizeUser(userName, password);
				loginStatus = Messages.SUCCESS_LOGIN;
				if(loginStatus.equals(Messages.SUCCESS_LOGIN)){
					PAGE_FORWARD =  Messages.LOGIN_SUCCESS_PAGE;
					//logger.info("token**************"+RequestContextHolder.currentRequestAttributes().getSessionId());
				}else{
					PAGE_FORWARD = Messages.LOGIN_FAILURE_PAGE;
					loginStatus =  Messages.WRONG_CREDENTIALS;
					return ResponseEntity.ok().body(commonUtils.populateResponseLoginError(loginStatus,PAGE_FORWARD));
				}
			}
		} catch (Exception ex) {
		    loginStatus = Messages.LOGIN_UN_EXPECTED_ERROR;
		    PAGE_FORWARD = Messages.LOGIN_FAILURE_PAGE;
			logger.error("Exception at login controller : " + ex, ex.getCause());
			return ResponseEntity.ok().body(commonUtils.populateResponseLoginError(loginStatus,PAGE_FORWARD));
		}
		return ResponseEntity.ok().body(commonUtils.populateResponse(loginStatus,RequestContextHolder.currentRequestAttributes().getSessionId()));
	}
	    
	  
}
