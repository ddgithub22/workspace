/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meganexus.login.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.meganexus.login.dao.LoginDao;
import com.meganexus.utils.AESenc;
import org.apache.log4j.Logger;


/**
 * @author arunkumar.k
 *
 */
@Component
public class AppUserDetailService implements UserDetailsService {
	
	private static final Logger LOGGER = Logger.getLogger(AppUserDetailService.class);

	@Resource(name="loginDao")
    private LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//com.reminder.model.User user = userRepository.getUserByName(username);
		 com.meganexus.login.vo.AppUserDetails user = loginDao.getUserByName(username,true);
		if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user!=null){
        	authorities.add(new SimpleGrantedAuthority(user.getRoleName()));
        }
         UserDetails u=null;
		try {
			 u = org.springframework.security.core.userdetails.User.withUsername(username)
			.password(AESenc.decrypt(loginDao.getPasswordByUserName(username)))
			.authorities(authorities)
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(false)
			.build();
		} catch (Exception e){
			LOGGER.error("Exception while setting userDetails : "+e.getCause(),e);
		}
        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user,u.getPassword(), u.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return u;
    }
    
  /*  private Collection<? extends GrantedAuthority> getAuthorities(
      Collection<Role> roles) {
        return getGrantedAuthorities();
    }*/
    
    private List<GrantedAuthority> getGrantedAuthorities( List<GrantedAuthority> authorities) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }
 
/*    private List<String> getPrivileges(Collection<Role> roles) {
  
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
 
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }*/
    
}