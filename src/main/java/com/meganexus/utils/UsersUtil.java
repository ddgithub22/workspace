package com.meganexus.utils;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meganexus.login.dao.LoginDao;
import com.meganexus.login.service.LoginService;
import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.security.JwtTokenUtil;



@Component
public class UsersUtil {
	 
	 @Resource(name="loginService")
	 private LoginService loginService;
	
	 @Autowired
     private JwtTokenUtil jwtTokenUtil;
	 
	 public AppUserDetails getCreatedUser(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		AppUserDetails createdUser = null;
		if(header!=null){
		if (header.startsWith("Bearer ")) {
			String username = jwtTokenUtil.getUsernameFromToken(header.substring(7));
			createdUser = loginService.getUserByName(username);
		}
	 }
		return createdUser;
	}
	 
	 public boolean validateToken(HttpServletRequest request) throws Exception{
		 String header = request.getHeader("Authorization");
		 boolean validToken = false;
		 if(null==header){
			 validToken = false;
		 }else{
			// validate token expiry
			final Date expiration = jwtTokenUtil.getExpirationDateFromToken(header.substring(7));
			validToken =  expiration.before(new Date())? false : true;
		 }
		 return validToken;
	  }
	
}
