package com.meganexus.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.meganexus.login.service.AppUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private Logger logger = Logger.getLogger(WebSecurityConfig.class);

	@Autowired
    private AppUserDetailService userDetailService;
	
    /*@Override*/
   /* public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailService);
        auth.ldapAuthentication().userSearchFilter("(uid={0})").userSearchBase("").contextSource()
        .url("ldap://www.zflexldap.com/dc=zflexsoftware,dc=com")
        .managerDn("cn=ro_admin,ou=sysadmins,dc=zflexsoftware,dc=com").managerPassword("zflexpass");
    }*/
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailService);
    }
    
    @Override
      protected void configure(HttpSecurity http) throws Exception {
       //Collection<? extends GrantedAuthority> userName= SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()   // only to test swagger
            .antMatchers(HttpMethod.POST,"/multipleSave").permitAll()  // only to test file upload from UI /savefile
            .antMatchers("/index.jsp").permitAll()
            .antMatchers("/dist/**").permitAll()// only to test file upload from UI /savefile
            .antMatchers("/lib/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/images/**").permitAll()
            .antMatchers("/configuration/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/v2/**").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
           // .antMatchers("/users").hasAnyAuthority("ADMIN")
            //.antMatchers("/updateGroup").hasAnyAuthority("GROUPADMIN")
           // .antMatchers("/groups").hasAnyAuthority("GROUPADMIN")
            .antMatchers("/**").permitAll()
           // .anyRequest().authenticated()
            .and()
            // We filter the api/login requests
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
            // And filter other requests to check the presence of JWT in header
            .addFilterBefore(new JWTAuthenticationFilter(userDetailService),
                    UsernamePasswordAuthenticationFilter.class).exceptionHandling().accessDeniedPage("/403");
      }
}
