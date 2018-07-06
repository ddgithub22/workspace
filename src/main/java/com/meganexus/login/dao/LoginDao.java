package com.meganexus.login.dao;

import javax.servlet.http.HttpSession;

import com.meganexus.login.vo.AppUserDetails;

/**
 * 
 * @author arunkumar.k
 * @version 1.0
 */
public interface LoginDao {
	public AppUserDetails getUserByName(String userName,boolean clearCheckList);
	public String getPasswordByUserName(String userName);
}
