package com.meganexus.logout.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Repository;

import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.toDo.vo.ToDoGroupCountsVo;
import com.meganexus.utils.Messages;
import com.meganexus.utils.SessionKey;
import com.meganexus.utils.UsersUtil;

/**
 * @author arunkumar.k
 * @version 1.0
 */

@Repository("logoutDao")
public class LogoutDaoImpl implements LogoutDao{

	private static final Logger logger = Logger.getLogger(LogoutDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired 
	private UsersUtil usersUtil;

	@Override
	public String logoutUser(HttpServletRequest request) {
		Session session = null;
		String status = null;
	    Query query = null;
	    Transaction tx = null;
	    int updatedRecord = 0;
		try {
			AppUserDetails userDetails = usersUtil.getCreatedUser(request);
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			query = session.createSQLQuery("update dbo.Users set IsActive=0 where UserName=:userName");
			query.setParameter("userName", userDetails.getUserName());
			updatedRecord = query.executeUpdate();
			tx.commit();
			if(updatedRecord!=0){
				status = Messages.SUCCESS_LOGOUT;
			}else{
				status = Messages.LOGOUT_FAIL;
			}
		}
		catch(Exception ex){
			status = Messages.LOGOUT_UN_EXPECTED_ERROR;
			tx.rollback();
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return status;
	}
}
