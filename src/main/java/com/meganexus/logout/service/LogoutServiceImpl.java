package com.meganexus.logout.service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import com.meganexus.login.dao.LoginDao;
import com.meganexus.logout.dao.LogoutDao;
import com.meganexus.utils.AESenc;

/**
 * @author arunkumar.k
 * @version 1.0
 */

@Service("logoutService")
public class LogoutServiceImpl implements LogoutService{

	 @Resource(name="logoutDao")
     private LogoutDao logoutDao;

	@Override
	public String logoutUser(HttpServletRequest request) throws Exception {
		return logoutDao.logoutUser(request);
	}
}
