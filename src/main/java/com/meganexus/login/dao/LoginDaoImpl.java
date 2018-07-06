package com.meganexus.login.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;

import com.meganexus.login.vo.AppUserDetails;
import com.meganexus.toDo.vo.ToDoGroupCountsVo;
import com.meganexus.utils.Messages;
import com.meganexus.utils.SessionKey;

/**
 * @author arunkumar.k
 * @version 1.0
 */

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao{

	private static final Logger logger = Logger.getLogger(LoginDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public AppUserDetails getUserByName(String userName,boolean clearCheckList) {
		Session session = null;
		String status = null;
		List<Object[]> results = null;
		Transaction  tx  = null;
		AppUserDetails userDetails = null;
		try {
		    //logger.info("##### username = "+userName+" password :"+password); 
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Query query = session.createQuery("select userId from User where userName=:username and password=:password");
			Query query = session.createSQLQuery("select users.UserId,users.UserName,users.FirstName,users.LastName,users.EmailId,roles.RoleName,roles.RoleId  from dbo.Users users inner join dbo.Roles roles on users.RoleId=roles.RoleId where users.UserName=:username");
			query.setParameter("username",userName.trim());
			results = query.list();
			if (results != null && results.size() == 0) {
				status = Messages.WRONG_CREDENTIALS;
			} else if (results != null && results.size() > 0) {
				status = Messages.SUCCESS_LOGIN;
                for(Object[] result:results){
                	userDetails = new AppUserDetails();
                	 if(result[0]!=null){
                		 userDetails.setUserId(result[0].toString()); 
                	 }
                	 if(result[1]!=null){
                		 userDetails.setUserName(result[1].toString());
                	 }
                	 if(result[2]!=null){
                		 userDetails.setFirstName(result[2].toString());
                	 }
                	 if(result[3]!=null){
                		 userDetails.setLastName(result[3].toString());
                	 }
                	 if(result[4]!=null){
                		 userDetails.setEmailId(result[4].toString());
                	 }
                	 if(result[5]!=null){
                		 userDetails.setRoleName(result[5].toString());
                	 }
                	 if(result[6]!=null){
                		 userDetails.setRoleId(result[6].toString());
                	 }
                }
                if(clearCheckList){
                	Query query2 = session.createSQLQuery("Delete from dbo.UserTodos where UserId=:UserId");
                	query2.setParameter("UserId",userDetails.getUserId());
                	query2.executeUpdate();
                	
                	Query query3 = session.createSQLQuery("update dbo.Users set IsActive=1 where UserName=:userName");
                    query3.setParameter("userName",userDetails.getUserName().trim());
                    query3.executeUpdate();
                }
                tx.commit();
				//userSession.setAttribute(SessionKey.MEMBER_PRFILE,userDetails);
			} else {
				 //status = Messages.WRONG_CREDENTIALS;
				 tx.rollback();
			}
		} catch (Exception ex) {
			 //status = Messages.LOGIN_UN_EXPECTED_ERROR;
			logger.error("Exception at LoginDaoImpl :" + ex.getCause(), ex);
			 tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return userDetails;
	}
	@Override
	public String getPasswordByUserName(String userName) {
		Session session = null;
		String password = null;
	    try{
	    	session = sessionFactory.openSession();
	    	Query query = session.createSQLQuery("select users.Password from dbo.Users where UserName=:userName");
	    	query.setParameter("userName",userName.trim());
            if(query.uniqueResult()!=null){
            	password = query.uniqueResult().toString();
            }
	    }
	    catch(Exception ex){
	       logger.error("Exception at getPasswordByUserName :"+ex.getCause(),ex);	
	    }
	    finally{
	    	if(session!=null){
	    		session.close();
	    	}
	    }
		return password;
	}
}
