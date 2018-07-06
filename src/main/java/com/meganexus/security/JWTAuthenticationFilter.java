package com.meganexus.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import com.meganexus.login.service.AppUserDetailService;


public class JWTAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	private AppUserDetailService userDetailService;
	
	public JWTAuthenticationFilter(AppUserDetailService userDetailService){
		 userDetailService = userDetailService;
	}
	
  @Override
  public void doFilter(ServletRequest request,
             ServletResponse response,
             FilterChain filterChain)
      throws IOException, ServletException {
    //System.out.println("****security == JWTAuthenticationFilter ******");
    filterChain.doFilter(request,response);
  }
}
