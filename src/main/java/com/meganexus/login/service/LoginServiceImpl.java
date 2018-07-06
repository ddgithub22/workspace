package com.meganexus.login.service;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import com.meganexus.login.dao.LoginDao;
import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.utils.AESenc;

/**
 * @author arunkumar.k
 * @version 1.0
 */

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	 @Resource(name="loginDao")
     private LoginDao loginDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.login.service.LoginService#getUserByName(java.lang.String)
	 */
	@Override
	public AppUserDetails getUserByName(String username) throws Exception {
		return loginDao.getUserByName(username,false);
	}
	
}
