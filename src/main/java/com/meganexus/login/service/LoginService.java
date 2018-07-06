package com.meganexus.login.service;

import javax.servlet.http.HttpSession;

import com.meganexus.login.vo.AppUserDetails;

/**
 * @author arunkumar.k
 * @version 1.0
 */

public interface LoginService{
    public AppUserDetails getUserByName(String username) throws Exception;
   // public AppUserDetails getUserDetailsByName(String username) throws Exception;
    
    
    
    
    
    
}
