package com.meganexus.logout.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;

/**
 * @author arunkumar.k
 * @version 1.0
 */

public interface LogoutService{
    public String logoutUser(HttpServletRequest request) throws Exception;
}
