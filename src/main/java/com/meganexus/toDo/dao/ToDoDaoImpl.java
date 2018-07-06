package com.meganexus.toDo.dao;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.ParameterMode;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.hibernate.result.UpdateCountOutput;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.preferences.vo.ClusterLevels;
import com.meganexus.preferences.vo.GlobalLevels;
import com.meganexus.preferences.vo.GlobalsLevels;
import com.meganexus.preferences.vo.LDULevels;
import com.meganexus.preferences.vo.ManagerLevels;
import com.meganexus.preferences.vo.ProviderLevels;
import com.meganexus.preferences.vo.TeamLevels;
import com.meganexus.toDo.vo.GroupCountsVo;
import com.meganexus.toDo.vo.InterventionsCountVo;
import com.meganexus.toDo.vo.TimeSensitiveCountsVo;
import com.meganexus.toDo.vo.ToDoGroupCountsVo;
import com.meganexus.toDo.vo.ToDoSheetsVo;
import com.meganexus.utils.Messages;

/**
 * 
 * @author arunkumar.k
 * @version 1.0
 *
 */

@Repository("toDoDao")
public class ToDoDaoImpl implements ToDoDao {
	
	private static final Logger logger = Logger.getLogger(ToDoDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
     * 
     * (non-Javadoc)
     * @see com.meganexus.dao.ToDoDao#getTotalSheetsInfo(com.meganexus.vo.ToDoSheetsVo)
     */
	@SuppressWarnings("unchecked")
	@Override
	public  JSONObject getTotalSheetsInfo(int userId,ArrayList<UserPreference> userList){
		logger.info("ToDoDaoImpl getTotalSheetsInfo Executing");
		Session session = null;
		List<Object[]> results = null;
		List<Object[]> resultsGrp = null;
		List<ToDoSheetsVo> groupObjects = new ArrayList<ToDoSheetsVo>();
		List<List<ToDoSheetsVo>> segmentgroupObjects = new ArrayList<List<ToDoSheetsVo>>();
		List<List<List<ToDoSheetsVo>>> totalSheetList = new ArrayList<List<List<ToDoSheetsVo>>>();
		ToDoSheetsVo toDoSheetsVo = null;
		JSONObject jsonGrp = new JSONObject();
		List<Object[]> timeSensitiveResults = null;
		List<Object[]> interventionsResults = null;
		//String selectedValue = "";
		Transaction trx = null;
		String multiSelect = "0";
		if(userList.size()>1){
			multiSelect = "1";
		}
		 logger.info("mutiSelect :"+multiSelect);
		 String totalSelectedValue = getSelectedValue(userList,multiSelect);
			 try {
			 //logger.info("selectedValue : "+totalSelectedValue);
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			Query query = session.createQuery("select id,groupName,displayOrder from TodoGroups order by displayOrder");
			results = query.list();
			for (Object[] result : results) {
				segmentgroupObjects = new ArrayList<List<ToDoSheetsVo>>();
				if (result[0] != null) {
					groupObjects = new ArrayList<ToDoSheetsVo>();

					//String groupQuery = "select ts.id,fis.fileName,ts.name,ts.description,fis.recordCount,ts.displayOrder,tg.id,tg.groupName,tg.displayOrder from TodoSetting as ts inner join ts.fileInfoMaster as fis inner join ts.todoGroups as tg where ts.todoGroups.id=:groupId order by ts.displayOrder";
					/*String groupQuery = "select ts.Id as fileInfoId,fim.FileName,ts.Name,ts.Description,SUM(TodoCount)-ISNULL((Select SUM(AllCount) from UserTodos Where UserId=:userId and todoId=ts.Id),0) as RecordCount,ts.DisplayOrder as displayOrder,tg.Id,tg.GroupName,tg.DisplayOrder as groupDisplayOrder from daily.TodoSettings ts inner join daily.FileInfoMaster fim on ts.FileInfoId=fim.Id inner join daily.TodoGroups tg on ts.GroupId=tg.Id inner join daily.TodoItems ti on ts.Id=ti.TodoId and  ts.GroupId=:groupId and ti.Provider='CPA Cheshire and Gtr Manchester' group by  ts.Id ,fim.FileName,ts.Name,ts.Description,fim.RecordCount,ts.DisplayOrder,tg.Id,tg.GroupName,tg.DisplayOrder order by ts.DisplayOrder;";
					 Query groupsQuery = session.createSQLQuery(groupQuery);
					 groupsQuery.setParameter("userId",userId);
					 groupsQuery.setParameter("groupId", (Integer) result[0]);*/
					logger.info(" daily.GetTodoFileCountbyGroupId ****** Param1(GroupId) :"+result[0]  +" Param2(selectedValue) : " +totalSelectedValue + " Param3(userId) : "+userId);
					 Query query2 =  session.createSQLQuery("{call daily.GetTodoFileCountbyGroupId(?,?,?)}");
					 query2.setInteger(0,(Integer) result[0]);
					 query2.setString(1,totalSelectedValue);
					 query2.setInteger(2,userId);
					 query2.executeUpdate();
					 resultsGrp = query2.list();
					 for (Object[] resultGrp : resultsGrp) {
						toDoSheetsVo = new ToDoSheetsVo();
						if (resultGrp[0] != null) {
							toDoSheetsVo.setFileInfoId(resultGrp[0].toString());
						}
						if (resultGrp[1] != null) {
							toDoSheetsVo.setFileName(resultGrp[1].toString());
						}
						if (resultGrp[2] != null) {
							toDoSheetsVo.setSheetName(resultGrp[2].toString());
						}
						if (resultGrp[3] != null) {
							toDoSheetsVo.setSheetDescription(resultGrp[3].toString());
						}
						if (resultGrp[4] != null) {
							toDoSheetsVo.setRecordCount(resultGrp[4].toString());
						}
						if (resultGrp[11] != null) {
							toDoSheetsVo.setDisplayOrder(resultGrp[11].toString());
						}
						if (resultGrp[12] != null) {
							toDoSheetsVo.setGroupId(resultGrp[12].toString());
						}
						if (resultGrp[13] != null) {
							toDoSheetsVo.setGroupName(resultGrp[13].toString());
						}
						if(resultGrp[14] != null){
						   toDoSheetsVo.setGroupDisplayOrder(resultGrp[14].toString());
						}
						groupObjects.add(toDoSheetsVo);
					}
					segmentgroupObjects.add(groupObjects);
					jsonGrp.put(result[1].toString(),groupObjects);
					groupObjects = null;
				}
				totalSheetList.add(segmentgroupObjects);
				segmentgroupObjects = null;
			}
			trx.commit();
			logger.info("ToDoDaoImpl getTotalSheetsInfo Executed successfully ");
		} catch (Exception ex) {
			logger.error("Exception while Retrieving total todo sheets " + ex.getMessage(), ex);
			trx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return jsonGrp;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.dao.ToDoDao#getGroupFilterCounts()
	 */
	@Override
	public JSONObject getGroupFilterCounts(int userId,ArrayList<UserPreference> userList){
		logger.info("ToDoDaoImpl getGroupFilterCounts Executing");
		Session session = null;
		List<Object[]> results = null;
		List<Object[]> interventionResults = null;
		List<TimeSensitiveCountsVo> timeSensitiveList = new ArrayList<TimeSensitiveCountsVo>();
		List<InterventionsCountVo>  interventionsList = new ArrayList<InterventionsCountVo>();
		Query query  = null;
		Query query2 = null;
		TimeSensitiveCountsVo timeSensitiveCountsVo = null;
		InterventionsCountVo  interventionsCountsVo = null;
		JSONObject jsonCounts = new JSONObject();
		String multiSelect = "0";
		if(userList.size()>1){
			multiSelect = "1";
		}
	 try{
		 session = sessionFactory.openSession();
		 //query = session.createSQLQuery("Select TodoId,GroupName,B.Name,B.Description,A.Provider,SUM(TodoCount) AllCount,SUM(OneDayCount) OneDayCount,SUM(OneWeekCount) OneWeekCount,SUM(OverDueCount) OverDueCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id and C.DisplayOrder=1 and A.Provider='CPA Cheshire and Gtr Manchester' group by TodoId,GroupName,B.Name,B.Description,A.Provider,B.DisplayOrder order by B.DisplayOrder,GroupName,B.Name,A.Provider");
		 //TIME Interventions Count
		 /*query = session.createSQLQuery("Select TodoId,GroupName,B.Name,B.Description,A.Provider,SUM(TodoCount)-ISNULL((Select SUM(AllCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) AllCount,SUM(OneDayCount)-ISNULL((Select SUM(OneDayCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) OneDayCount,SUM(OneWeekCount)-ISNULL((Select SUM(OneWeekCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) OneWeekCount,SUM(OverDueCount)-ISNULL((Select SUM(OverDueCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) OverDueCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id and C.DisplayOrder=1 and A.Provider='CPA Cheshire and Gtr Manchester' group by TodoId,GroupName,B.Name,B.Description,A.Provider,B.DisplayOrder order by B.DisplayOrder,GroupName,B.Name,A.Provider");
		 query.setParameter("userId",userId);*/
		 String totalSelectedValue = getSelectedValue(userList,multiSelect);
		 logger.info("multiSelect :"+multiSelect);
		 logger.info(" daily.GetTodoFileCountbyGroupId ****** Param1(GrouipId) :1" +" Param2(selectedValue) : " +totalSelectedValue + " Param3(userId) : "+userId);
		 query =  session.createSQLQuery("{call daily.GetTodoFileCountbyGroupId(?,?,?)}");
		 query.setInteger(0,1);
		 query.setString(1,totalSelectedValue);
		 query.setInteger(2,userId);
		 query.executeUpdate();
		 results = query.list();
		 for (Object[] result : results) {
			 timeSensitiveCountsVo = new TimeSensitiveCountsVo();
			 if(result[0]!=null){
				 timeSensitiveCountsVo.setToDoId(result[0].toString());
			 }
			 if(result[2]!=null){
				 timeSensitiveCountsVo.setSheetName(result[2].toString());
			 }
			 if(result[3]!=null){
				 timeSensitiveCountsVo.setSheetDescription(result[3].toString());
			 }
			 if(result[4]!=null){
				 timeSensitiveCountsVo.setAllCount(result[4].toString());
			 }
			 if(result[5]!=null){
				 timeSensitiveCountsVo.setOneDayCount(result[5].toString());
			 }
			 if(result[6]!=null){
				 timeSensitiveCountsVo.setOneWeekCount(result[6].toString());
			 }
			 if(result[7]!=null){
				 timeSensitiveCountsVo.setOverDueCount(result[7].toString());
			 }
			 if(result[13]!=null){
				 timeSensitiveCountsVo.setGroupName(result[13].toString());
			 }
			 
			 timeSensitiveList.add(timeSensitiveCountsVo);
		  }
		 
		  //query2 = session.createSQLQuery("Select TodoId,GroupName,B.Name,B.Description,A.Provider,SUM(TodoCount) AllCount,SUM(LowRiskCount) LowRiskCount,SUM(HighRiskCount) HighRiskCount,SUM(FailingCount) FailingCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id and C.DisplayOrder=2 and A.Provider='CPA Cheshire and Gtr Manchester' group by TodoId,GroupName,B.Name,B.Description,A.Provider,B.DisplayOrder order by B.DisplayOrder,GroupName,B.Name,A.Provider");
		  //query2 = session.createSQLQuery("Select TodoId,GroupName,B.Name,B.Description,A.Provider,SUM(TodoCount)-ISNULL((Select SUM(AllCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) AllCount,SUM(LowRiskCount)-ISNULL((Select SUM(LowRiskCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0)  LowRiskCount,SUM(HighRiskCount)-ISNULL((Select SUM(HighRiskCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) HighRiskCount,SUM(FailingCount)-ISNULL((Select SUM(FailingCount) from UserTodos Where UserId=:userId and todoId=A.TodoId),0) FailingCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id and C.DisplayOrder=2 and A.Provider='CPA Cheshire and Gtr Manchester' group by TodoId,GroupName,B.Name,B.Description,A.Provider,B.DisplayOrder order by B.DisplayOrder,GroupName,B.Name,A.Provider");
		  //query.setParameter("userId",userId);
		  //interventionResults = query2.list();
		 logger.info("daily.GetTodoFileCountbyGroupId ****** Param1(GrouipId) :3" +" Param2(selectedValue) : " +totalSelectedValue + " Param3(userId) : "+userId);
		 query2 =  session.createSQLQuery("{call daily.GetTodoFileCountbyGroupId(?,?,?)}");
		 query2.setInteger(0,3);
		 query2.setString(1,totalSelectedValue);
		 query2.setInteger(2,userId);
		 query2.executeUpdate();
		 interventionResults = query2.list();
		  for(Object[] reslt : interventionResults){
			     interventionsCountsVo = new InterventionsCountVo();
			     if(reslt[0]!=null){
			    	 interventionsCountsVo.setToDoId(reslt[0].toString());
			     }
			     if(reslt[13]!=null){
			    	 interventionsCountsVo.setGroupName(reslt[13].toString());
			     }
			     if(reslt[2]!=null){
			    	 interventionsCountsVo.setSheetName(reslt[2].toString());
			     }
			     if(reslt[3]!=null){
			    	 interventionsCountsVo.setSheetDescription(reslt[3].toString());
			     }
			    /* if(reslt[4]!=null){
			    	 interventionsCountsVo.setProvider(reslt[4].toString());
			     }*/
			     if(reslt[4]!=null){
			    	 interventionsCountsVo.setAllCount(reslt[4].toString());
			     }
			     if(reslt[8]!=null){
			    	 interventionsCountsVo.setLowRiskCount(reslt[8].toString());
			     }
			     if(reslt[9]!=null){
			    	 interventionsCountsVo.setHighRiskCount(reslt[9].toString());
			     }
			     if(reslt[10]!=null){
			    	 interventionsCountsVo.setFailingCount(reslt[10].toString());
			     }
			     interventionsList.add(interventionsCountsVo);
		   }
		  jsonCounts.put("TimeSensitiveCounts", timeSensitiveList);
		  jsonCounts.put("InterventionsCounts", interventionsList);
		}
		catch(Exception ex){
			logger.info("Exception while getting group filter counts:"+ex.getCause(),ex);
		}
	    finally{
	    	if(session!=null){
	    		session.close();
	    	}
	    }
		return jsonCounts;
	}
	
	/*
	  * (non-Javadoc)
	  * @see com.meganexus.toDo.dao.ToDoDao#getGroupWiseCounts()
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupCountsVo> getGroupWiseCounts(int userId,ArrayList<UserPreference> userList) {
		logger.info("ToDoDaoImpl getGroupWiseCounts Executing");
		Session session = null;
		List<Object[]> results = null;
		List<GroupCountsVo> groupsCount = new ArrayList<GroupCountsVo>();
		GroupCountsVo groupsCountVo = null;
		Transaction trx = null;
		int prefCount = 0;
		 String multiSelect="0";
		 if(userList.size()>1){
			multiSelect = "1";
		  }
		  try{
			    logger.info("multiSelect:"+multiSelect);
			    String totalSelectedValue=getSelectedValue(userList,multiSelect);
				session = sessionFactory.openSession();
				trx = session.beginTransaction();
				//Query query = session.createSQLQuery("Select GroupName,A.Provider,SUM(TodoCount)as AllCount,SUM(OneDayCount) as OneDayCount,SUM(OneWeekCount) as OneWeekCount,SUM(OverDueCount) as OverDueCount,SUM(LowRiskCount) as LowRiskCount,SUM(HighRiskCount) as HighRiskCount,SUM(FailingCount) as FailingCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id and A.Provider='CPA Cheshire and Gtr Manchester' group by GroupName,A.Provider,C.DisplayOrder order by C.DisplayOrder,GroupName,A.Provider");
				//Query query = session.createSQLQuery("Select GroupName,A.Provider,SUM(TodoCount)-ISNULL((Select SUM(AllCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as AllCount,SUM(OneDayCount)-ISNULL((Select SUM(OneDayCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as OneDayCount,SUM(OneWeekCount)-ISNULL((Select SUM(OneWeekCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as OneWeekCount,SUM(OverDueCount)-ISNULL((Select SUM(OverDueCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as OverDueCount,SUM(LowRiskCount)-ISNULL((Select SUM(LowRiskCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as LowRiskCount,SUM(HighRiskCount)-ISNULL((Select SUM(HighRiskCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as HighRiskCount,SUM(FailingCount)-ISNULL((Select SUM(FailingCount) from UserTodos A,daily.TodoSettings B,daily.TodoGroups Cd Where A.TodoId=B.id and b.GroupId=cd.id and UserId=:userId and cd.GroupName=C.GroupName),0) as FailingCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id and A.Provider='CPA Cheshire and Gtr Manchester' group by GroupName,A.Provider,C.DisplayOrder order by C.DisplayOrder,GroupName,A.Provider;");
				//query.setParameter("userId",userId);
				 logger.info("daily.GetTodoGroupCount ****** Param1(selecetedValue) :"+totalSelectedValue+ " Param2(userId) : " +userId);
				 Query query2 =  session.createSQLQuery("{call daily.GetTodoGroupCount(?,?)}");
				 query2.setString(0,totalSelectedValue);
				 query2.setInteger(1,userId);
				 query2.executeUpdate();
				 results = query2.list();
                for(Object[] result:results){
                	groupsCountVo = new GroupCountsVo();
                	if(result[0]!=null){
                		groupsCountVo.setGroupName(result[0].toString());
                	}
                	if(result[1]!=null){
                		groupsCountVo.setGroupDisplayOrder(result[1].toString());
                	}
                	if(result[2]!=null){
                		groupsCountVo.setAllCount(result[2].toString());
                	}
                	if(result[3]!=null){
                		groupsCountVo.setOneDayCount(result[3].toString());
                	}
                	if(result[4]!=null){
                		groupsCountVo.setOneWeekCount(result[4].toString());
                	}
                	if(result[5]!=null){
                		groupsCountVo.setOverDueCount(result[5].toString());
                	}
                	if(result[6]!=null){
                		groupsCountVo.setLowRiskCount(result[6].toString());
                	}
                	if(result[7]!=null){
                		groupsCountVo.setHighRiskCount(result[7].toString());
                	}
                	if(result[8]!=null){
                		groupsCountVo.setFailingCount(result[8].toString());
                	}
                	groupsCount.add(groupsCountVo);
                }
		 }
		catch(Exception ex){
			logger.error("Exception while getting getGroupWiseCounts " + ex.getMessage(), ex);
		}
		finally{
			if (session != null) {
				session.close();
			}
		}
		return groupsCount;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.dao.ToDoDao#getTotalCounts()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject getTotalCounts(int userId,ArrayList<UserPreference> userList) {
		logger.info("ToDoDaoImpl getTotalCount Executing");
		Session session = null;
		List<Object[]> results = null;
		JSONObject obj = null;
		int prefCount = 0;
		 String multiSelect="0";
		 if(userList.size()>1){
			multiSelect = "1";
		  }
		String totalSelectedValue=getSelectedValue(userList,multiSelect);
		try {
			obj = new JSONObject();
			session = sessionFactory.openSession();
			//Query query = session.createSQLQuery("Select SUM(AllCount) AllCount,SUM(OneDayCount) OneDayCount,SUM(OneWeekCount) OneWeekCount,SUM(OverDueCount) OverDueCount,SUM(LowRiskCount) LowRiskCount,SUM(HighRiskCount) HighRiskCount,SUM(FailingCount) FailingCount from (Select GroupName,A.Provider,SUM(TodoCount) AllCount,CASE WHEN SUM(OneDayCount)=0 THEN SUM(TodoCount) ELSE SUM(OneDayCount) END OneDayCount,CASE WHEN SUM(OneWeekCount)=0 THEN SUM(TodoCount) ELSE SUM(OneWeekCount) END OneWeekCount,CASE WHEN SUM(OverDueCount)=0 THEN SUM(TodoCount) ELSE SUM(OverDueCount) END OverDueCount,CASE WHEN SUM(LowRiskCount)=0 THEN SUM(TodoCount) ELSE SUM(LowRiskCount) END LowRiskCount,CASE WHEN SUM(HighRiskCount)=0 THEN SUM(TodoCount) ELSE SUM(HighRiskCount) END HighRiskCount,CASE WHEN SUM(FailingCount)=0 THEN SUM(TodoCount) ELSE SUM(FailingCount) END FailingCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id group by GroupName,A.Provider,C.DisplayOrder ) A Where A.Provider='CPA Cheshire and Gtr Manchester'");
			 //Query query = session.createSQLQuery("Select SUM(AllCount)-ISNULL((Select SUM(AllCount) from UserTodos Where UserId=:userId),0) AllCount,SUM(OneDayCount)-ISNULL((Select SUM(OneDayCount) from UserTodos Where UserId=:userId),0) OneDayCount,SUM(OneWeekCount)-ISNULL((Select SUM(OneWeekCount) from UserTodos Where UserId=:userId),0) OneWeekCount,SUM(OverDueCount)-ISNULL((Select SUM(OverDueCount) from UserTodos Where UserId=:userId),0) OverDueCount,SUM(LowRiskCount)-ISNULL((Select SUM(LowRiskCount) from UserTodos Where UserId=:userId),0) LowRiskCount,SUM(HighRiskCount)-ISNULL((Select SUM(HighRiskCount) from UserTodos Where UserId=:userId),0) HighRiskCount,SUM(FailingCount)-ISNULL((Select SUM(FailingCount) from UserTodos Where UserId=:userId),0) FailingCount from (Select GroupName,A.Provider,SUM(TodoCount) AllCount,CASE WHEN SUM(OneDayCount)=0 THEN SUM(TodoCount) ELSE SUM(OneDayCount) END OneDayCount,CASE WHEN SUM(OneWeekCount)=0 THEN SUM(TodoCount) ELSE SUM(OneWeekCount) END OneWeekCount,CASE WHEN SUM(OverDueCount)=0 THEN SUM(TodoCount) ELSE SUM(OverDueCount) END OverDueCount,CASE WHEN SUM(LowRiskCount)=0 THEN SUM(TodoCount) ELSE SUM(LowRiskCount) END LowRiskCount,CASE WHEN SUM(HighRiskCount)=0 THEN SUM(TodoCount) ELSE SUM(HighRiskCount) END HighRiskCount,CASE WHEN SUM(FailingCount)=0 THEN SUM(TodoCount) ELSE SUM(FailingCount) END FailingCount from daily.TodoItems A,daily.TodoSettings B,daily.TodoGroups C Where A.TodoId=B.Id and B.GroupId=C.Id group by GroupName,A.Provider,C.DisplayOrder) A Where A.Provider='CPA Cheshire and Gtr Manchester'");
			//query.setParameter("userId",userId);
			logger.info("multiSelect :" +multiSelect);
			logger.info(" daily.GetTodoOverAllCount ****** Param1(selectedValue) :"+totalSelectedValue +" Param2(userId) : "+userId);
			Query query = session.createSQLQuery("{call daily.GetTodoOverAllCount(?,?)}");
			query.setString(0,totalSelectedValue);
			query.setInteger(1,userId);
			query.executeUpdate();
			results = query.list();
			for(Object[] result : results){
				obj = new JSONObject();
				if(result[0]!=null){
					obj.put("AllCount",result[0].toString());
				}else{
					obj.put("AllCount","0");
				}
				if(result[1]!=null){
					obj.put("OneDayCount",result[1].toString());
				}else{
					obj.put("OneDayCount","0");
				}
				if(result[2]!=null){
					obj.put("OneWeekCount",result[2].toString());
				}else{
					obj.put("OneWeekCount","0");
				}
				if(result[3]!=null){
					obj.put("OverDueCount",result[3].toString());
				}else{
					obj.put("OverDueCount","0");
				}
				if(result[4]!=null){
					obj.put("LowRiskCount",result[4].toString());
				}else{
					obj.put("LowRiskCount","0");
				}
				if(result[5]!=null){
					obj.put("HighRiskCount",result[5].toString());
				}else{
					obj.put("HighRiskCount","0");
				}
				if(result[5]!=null){
					obj.put("FailingCount",result[6].toString());
				}else{
					obj.put("FailingCount","0");
				}
			}
		} catch (Exception ex) {
			logger.error("Exception while getting getTotalCount" + ex.getMessage(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}
	 
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.dao.ToDoDao#getToDoSheetDetails(int)
	 */
    @SuppressWarnings("unchecked")
	public List<LinkedHashMap<String, String>> getToDoSheetDetails(int fileInfoId,String countType){
    	logger.info("ToDoDaoImpl getTotalSheetsInfo Executing");
		Session session = null;
		Transaction trx = null;
		String sheetRecords = null;
		List<Object[]> SheetList = null;
		LinkedHashMap<String, String> sheetMap = null;
		JSONObject partJson = null;
		JSONObject totalJson = null;
	    ArrayList<LinkedHashMap<String, String>> totalList = new ArrayList<LinkedHashMap<String, String>>();
	    JSONParser parser = new JSONParser(); 
		try {
			 logger.info(" daily.GetTodobyId ****** Param1(TodoId) :"+fileInfoId+ " Param2(Filter) : " +countType + " Param3(sortColumnname) : 1"+" Param4(sortOrder) : 1");
             session = sessionFactory.openSession();
			 trx = session.beginTransaction();
			 ProcedureCall procedureCall =  session.createStoredProcedureCall("daily.GetTodobyId");
			 procedureCall.registerParameter("TodoId",Integer.class,ParameterMode.IN);
			 procedureCall.registerParameter("Filter",String.class,ParameterMode.IN);
			 procedureCall.registerParameter("sortColumnname",String.class,ParameterMode.IN);
			 procedureCall.registerParameter("sortOrder",String.class,ParameterMode.IN);
			 procedureCall.registerParameter("sheetHeaders",String.class, ParameterMode.OUT);
			 procedureCall.getParameterRegistration("TodoId").bindValue(fileInfoId);
			 procedureCall.getParameterRegistration("Filter").bindValue(countType);
			 procedureCall.getParameterRegistration("sortColumnname").bindValue("1");
			 procedureCall.getParameterRegistration("sortOrder").bindValue("1");
			 ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			 ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			 SheetList = resultSetOutput.getResultList();
		     String SheetHeaders   = (String) procedureCall.getOutputs().getOutputParameterValue("sheetHeaders");
		     //logger.info("SheetHeaders :"+SheetHeaders);
		     if(SheetHeaders!=null && resultSetOutput!=null){
		     String[] columns = SheetHeaders.split(",");
		     Gson gson = new Gson();
		     for (Object[] result : SheetList) {
		    	  sheetMap = new LinkedHashMap<String, String>();
		    	    for(int i=0;i<columns.length;i++){
		    	    	 if(columns[i]!=null){
		    	    		 if(result[i]==null){
		    	    			 result[i] = "";
		    	    		 }
		    	    		 sheetMap.put(columns[i],result[i].toString());
		    	    	 }
		    	    }
		    	    String json = gson.toJson(sheetMap,LinkedHashMap.class);
		    	    partJson = (JSONObject) parser.parse(json);
		    	    totalList.add(sheetMap);
		     }
		     }
            trx.commit();  			
		}
		catch(Exception ex){
		  trx.rollback();
		  logger.error("Exception while getting getToDoSheetDetails :" + ex.getCause(), ex);
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
    	return totalList;
    }
    
    /*
     * (non-Javadoc)
     * @see com.meganexus.toDo.dao.ToDoDao#sortGridRecord(int, java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public List<LinkedHashMap<String, String>> sortGridRecord(int fileInfoId,String countType,String sortColumnname,String sortOrder){
    	logger.info("ToDoDaoImpl getTotalSheetsInfo Executing");
		Session session = null;
		Transaction trx = null;
		String sheetRecords = null;
		List<Object[]> SheetList = null;
		LinkedHashMap<String, String> sheetMap = null;
		JSONObject partJson = null;
		JSONObject totalJson = null;
	    ArrayList<LinkedHashMap<String, String>> totalList = new ArrayList<LinkedHashMap<String, String>>();
	    JSONParser parser = new JSONParser(); 
		//sortOrder - asc or desc
	    try {
	    	 logger.info("daily.GetTodobyId ****** Param1(TodoId) :"+fileInfoId+ " Param2(Filter) : " +countType + " Param3(sortColumnname) : "+sortColumnname+" Param4(sortOrder) : "+sortOrder);
             session = sessionFactory.openSession();
			 trx = session.beginTransaction();
			 ProcedureCall procedureCall =  session.createStoredProcedureCall("daily.GetTodobyId");
			 procedureCall.registerParameter("TodoId",Integer.class,ParameterMode.IN);
			 procedureCall.registerParameter("Filter",String.class,ParameterMode.IN);
			 procedureCall.registerParameter("sortColumnname",String.class,ParameterMode.IN);
			 procedureCall.registerParameter("sortOrder",String.class,ParameterMode.IN);
			 procedureCall.registerParameter("sheetHeaders",String.class, ParameterMode.OUT);
			 procedureCall.getParameterRegistration("TodoId").bindValue(fileInfoId);
			 procedureCall.getParameterRegistration("Filter").bindValue(countType);
			 procedureCall.getParameterRegistration("sortColumnname").bindValue(sortColumnname);
			 procedureCall.getParameterRegistration("sortOrder").bindValue(sortOrder);
			 ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			 ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			 SheetList = resultSetOutput.getResultList();
		     String SheetHeaders   = (String) procedureCall.getOutputs().getOutputParameterValue("sheetHeaders");
		     //logger.info("SheetHeaders :"+SheetHeaders);
		     if(SheetHeaders!=null && resultSetOutput!=null){
		     String[] columns = SheetHeaders.split(",");
		     Gson gson = new Gson();
		     for (Object[] result : SheetList) {
		    	  sheetMap = new LinkedHashMap<String, String>();
		    	    for(int i=0;i<columns.length;i++){
		    	    	 if(columns[i]!=null){
		    	    		 if(result[i]==null){
		    	    			 result[i] = "";
		    	    		 }
		    	    		 sheetMap.put(columns[i],result[i].toString());
		    	    	 }
		    	    }
		    	    String json = gson.toJson(sheetMap,LinkedHashMap.class);
		    	    partJson = (JSONObject) parser.parse(json);
		    	    totalList.add(sheetMap);
		     }
		     }
            trx.commit();  			
		}
		catch(Exception ex){
		  trx.rollback();
		  logger.error("Exception while getting getToDoSheetDetails :" + ex.getCause(), ex);
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
    	return totalList;
    }
    
    /*
     * (non-Javadoc)
     * @see com.meganexus.toDo.dao.ToDoDao#generateExcelSheet(int)
     */
    
    @SuppressWarnings("unchecked")
	public String generateExcelSheet(int TodoId,String excelDownloadPath,String FILES_POOL_SIZE,String countType,ArrayList<UserPreference> userList,int userId){
    	 logger.info("ToDoDaoImpl generateExcelSheet Executing");
 		 Session session = null;
 		 Transaction trx = null;
 		 String sheetRecords = null;
 		 List<Object[]> SheetList = null;
 		 String fileGeneration = "created";
 		 String SheetHeaders = null;
 		 String fileName = null;
 		 int prefCount = 0;
 		 List<List> totalList = null;
 		 ArrayList prKey = new ArrayList();
 		 boolean dupFlag = false;
 		 String multiSelect="0";
		 if(userList.size()>1){
			multiSelect = "1";
		  }
 		 try{
 			 String selectedValue = getSelectedValueGrid(userList,multiSelect);
 			 logger.info("multiSelect : "+multiSelect);
 			 logger.info("daily.GetTodoExcelbyId ****** Param1(TodoId) :"+TodoId+ " Param2(Filter) : " +countType + " Param3(selectedValue) : "+selectedValue+" Param4(userId) : "+userId);
 			 session = sessionFactory.openSession();
 			 trx = session.beginTransaction();
 			 //String selectedValue = "";
 			 ProcedureCall procedureCall =  session.createStoredProcedureCall("daily.GetTodoExcelbyId");
 			 procedureCall.registerParameter("TodoId",Integer.class,ParameterMode.IN);
 			 procedureCall.registerParameter("Filter",String.class,ParameterMode.IN);
 			 procedureCall.registerParameter("selectedValue",String.class,ParameterMode.IN);
 			 procedureCall.registerParameter("userId",Integer.class,ParameterMode.IN);
 			 procedureCall.registerParameter("columnnames",String.class, ParameterMode.OUT);
 			 procedureCall.getParameterRegistration("TodoId").bindValue(TodoId);
 			 procedureCall.getParameterRegistration("Filter").bindValue(countType);
 			 procedureCall.getParameterRegistration("selectedValue").bindValue(selectedValue);
 			 procedureCall.getParameterRegistration("userId").bindValue(userId);
 			 ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			 ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			 SheetList = resultSetOutput.getResultList();
			 SheetHeaders   = (String) procedureCall.getOutputs().getOutputParameterValue("columnnames");
 		     //Date date = new Date();
 		     DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
 	         Date dateobj = new Date();
 		     fileName = df.format(dateobj);
 			 fileGeneration = generateExcel(SheetHeaders,SheetList,fileName,excelDownloadPath,FILES_POOL_SIZE);
 		     trx.commit();
		 }
 		 catch(Exception ex){
 			 trx.rollback();
 			 logger.error("Exception in generateExcelSheet : "+ex.getCause(),ex);
 			 fileGeneration="failed";
 		 }
 		 finally{
 			 if(session!=null){
 				 session.close();
 			 }
 		 }
    	return fileGeneration;
    }

   // public String generateExcel(String sheetHeaders,List<Object[]>  sheetList,String fileName,String excelDownloadPath,String FILES_POOL_SIZE) throws IOException{
	 public String generateExcel(String sheetHeaders,List<Object[]> sheetList,String fileName,String excelDownloadPath,String FILES_POOL_SIZE) throws IOException{
	 FileOutputStream fileOut = null;
	 File outputFile = null;
	 Workbook workbook = new XSSFWorkbook();
	 Sheet sheet = workbook.createSheet("sheet 1");
	 
	 CreationHelper createHelper = workbook.getCreationHelper();
	  // Create a Font for styling header cells
     Font headerFont = workbook.createFont();
     //headerFont.setBold(true);
     headerFont.setFontHeightInPoints((short) 13);
     headerFont.setColor(IndexedColors.BLUE.getIndex());
     headerFont.setBoldweight((short) 13);
     
     // Create a CellStyle with the font
     CellStyle headerCellStyle = workbook.createCellStyle();
     headerCellStyle.setFont(headerFont);
	 
  // Create a Row
     Row headerRow = sheet.createRow(0);
     String[] columns = sheetHeaders.split(",");
     // Creating cells
     for(int i = 0; i < columns.length; i++) {
         Cell cell = headerRow.createCell(i);
         cell.setCellValue(columns[i]);
         cell.setCellStyle(headerCellStyle);
     }
     // Create Cell Style for formatting Date
     CellStyle dateCellStyle = workbook.createCellStyle();
     dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
     int rowNum = 1;
     for(int i = 0;i<sheetList.size();i++){
    	 Row row = sheet.createRow(rowNum++);
    	 Object result[] = sheetList.get(i);
    	 int columnValue = 0;  
    	 for(int j=0;j<result.length;j++){
    		 if(result[j]!=null){
    		 row.createCell(columnValue).setCellValue(result[j].toString());
   	    	 columnValue++;
    		 }
    		 else{
    			 columnValue++;
    		 }
    	 }
     }
	 
     for(int i = 0; i < columns.length; i++) {
         sheet.autoSizeColumn(i);
     }
	try {
		//logger.info(" file path : "+excelDownloadPath.trim()+"/"+fileName+".xlsx");
		outputFile = new File(excelDownloadPath.trim());
		if (!outputFile.exists()) {
			outputFile.mkdirs();
			outputFile = new File(excelDownloadPath.trim()+"/"+fileName+".xlsx");
		 }else{
			 outputFile = new File(excelDownloadPath.trim()+"/"+fileName+".xlsx");
	      }
		File[] totalFiles = new File(excelDownloadPath.trim()).listFiles();
		ArrayList<Long> al =  new ArrayList<Long>(Integer.parseInt(FILES_POOL_SIZE));
		for(File file :totalFiles){
			     //String fileNames = file.getName();
			     String fileNames = (file.getName().substring(0,file.getName().indexOf(".")));
			     al.add(Long.parseLong(fileNames,10));
		}
		Collections.sort(al,new Comparator<Object>(){
			@Override
			public int compare(Object o1, Object o2) {
				Long l1 = (Long)o1;
				Long l2 = (Long)o2;
				 if(l1>l2){
					 return 1;
				 }
				 else if(l1<l2){
					 return -1;
				 }
				 else{
					 return 0;
				 }
			}
		});
		if(totalFiles.length >= Integer.parseInt(FILES_POOL_SIZE)){
			File delFile = new File(excelDownloadPath.trim()+"/"+al.get(0).toString()+".xlsx");
			boolean delFlag = deleteFile(delFile);
			//logger.info("deleted file status :"+delFlag+" delfileName : "+al.get(0).toString());
		}
		fileOut = new FileOutputStream(outputFile);
		workbook.write(fileOut);
	} catch (FileNotFoundException e) {
		logger.error("Exception at creating excel file :"+e.getCause(),e);
	}
	finally{
		if(fileOut!=null){
		fileOut.close();
		}
	}
	 return fileName;
 }
 
  public boolean deleteFile(File file) {
		boolean delFlag = false;
		try {
			delFlag = file.delete();
		} catch (Exception ex) {
			logger.error("Exception while deleting the file:" + ex.getMessage(), ex);
		}
		return delFlag;
	}

public JSONObject getToDoPreferencesOld() {
	logger.info("getToDoPreferences executing");
	Session session = null;
	List<Object[]> results = null;
	boolean providerFlag = true;
	boolean clusterFlag = true;
	boolean lduFlag = true;
	boolean teamFlag = true;
	boolean managerFlag = true;
	
	HashSet<String> providerSet = new HashSet<String>();
	HashSet<String> clusterSet = new HashSet<String>();
	HashSet<String> lduSet = new HashSet<String>();
	HashSet<String> teamSet = new HashSet<String>();
	HashSet<String> managerSet = new HashSet<String>();
	
	LinkedHashMap<Integer, GlobalsLevels> providerMap = new LinkedHashMap<Integer, GlobalsLevels>();
	LinkedHashMap<Integer, GlobalsLevels> clusterMap = new LinkedHashMap<Integer, GlobalsLevels>();
	LinkedHashMap<Integer, GlobalsLevels> lduMap = new LinkedHashMap<Integer, GlobalsLevels>();
	LinkedHashMap<Integer, GlobalsLevels> teamMap = new LinkedHashMap<Integer, GlobalsLevels>();
	LinkedHashMap<Integer, GlobalsLevels> managerMap = new LinkedHashMap<Integer, GlobalsLevels>();
	
	String EventProvider = "EventProvider";
	String EventCluster = "EventCluster";
	String EventLDU = "EventLDU";
	String EventTeam = "EventTeam";
	String EventManager = "EventManager";
	JSONObject totalJsonObj = null;

	GlobalsLevels globalLevels = null;
	
	totalJsonObj = new JSONObject();
	JSONObject providerJson = new JSONObject();
	JSONObject clusterJson = new JSONObject();
	JSONObject lduJson = new JSONObject();
	JSONObject teamJson = new JSONObject();
	JSONObject managerJson = new JSONObject();

	int providerCount = 0;
	int clusterCount = 0;
	int lduCount = 0;
	int teamCount = 0;
	int managerCount = 0;
	Gson gson = new Gson();
	JSONParser parser = new JSONParser();
	LinkedHashMap<String, JSONObject> totalMap = new LinkedHashMap<String, JSONObject>();

	try {
		session = sessionFactory.openSession();
		Query query = session.createSQLQuery("Select distinct Provider,Cluster,LDU,Team,Manager from daily.TodoItems order by Provider,Cluster,LDU,Team,Manager");
		results = query.list();
		for (Object[] result : results) {
			if (result[0] != null) {
				if (!(providerSet.contains(result[0].toString()))) {
					providerFlag = providerSet.add(result[0].toString());
					if (providerFlag == true) {
						providerCount = providerCount + 1;
						globalLevels = new GlobalsLevels();
						globalLevels.setLevelId(String.valueOf(providerCount));
						globalLevels.setLevelName(EventProvider);
						globalLevels.setLevelValue(result[0].toString());
						providerMap.put(providerCount, globalLevels);
					}
				}
			}
			if (result[1] != null) {
				if (!(clusterSet.contains(result[1].toString()))) {
					clusterFlag = clusterSet.add(result[1].toString());
					if (clusterFlag == true) {
						clusterCount = clusterCount + 1;
						globalLevels = new GlobalsLevels();
						globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount));
						globalLevels.setLevelName(EventCluster);
						globalLevels.setLevelValue(result[1].toString());
						globalLevels.setEventProvider(result[0].toString());
						globalLevels.setEventCluster(result[1].toString());
						globalLevels.setEventLDU("");
						globalLevels.setEventTeam("");
						globalLevels.setEventManager("");
						clusterMap.put(clusterCount, globalLevels);
					}
				}
			}
			if (result[2] != null) {
				if (!(lduSet.contains(result[2].toString()))) {
					lduFlag = lduSet.add(result[2].toString());
					if (lduFlag == true) {
						lduCount = lduCount + 1;
						globalLevels = new GlobalsLevels();
						globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount)+ "." + String.valueOf(lduCount));
						globalLevels.setLevelName(EventLDU);
						globalLevels.setLevelValue(result[2].toString());
						globalLevels.setEventProvider(result[0].toString());
						globalLevels.setEventCluster(result[1].toString());
						globalLevels.setEventLDU(result[2].toString());
						globalLevels.setEventTeam("");
						globalLevels.setEventManager("");
						lduMap.put(lduCount, globalLevels);
					}
				}
			}
			if (result[3] != null) {
				if (!(teamSet.contains(result[3].toString()))) {
					teamFlag = teamSet.add(result[3].toString());
					if (teamFlag == true) {
						teamCount = teamCount + 1;
						globalLevels = new GlobalsLevels();
						globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount)+ "." + String.valueOf(lduCount) + "." + String.valueOf(teamCount));
						globalLevels.setLevelName(EventTeam);
						globalLevels.setLevelValue(result[3].toString());
						globalLevels.setEventProvider(result[0].toString());
						globalLevels.setEventCluster(result[1].toString());
						globalLevels.setEventLDU(result[2].toString());
						globalLevels.setEventTeam(result[3].toString());
						globalLevels.setEventManager("");
						teamMap.put(teamCount, globalLevels);
					}
				}
			}
			if (result[4] != null) {
				if (!(managerSet.contains(result[4].toString()))) {
					if (managerFlag == true) {
						managerCount = managerCount + 1;
						globalLevels = new GlobalsLevels();
						globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount)+ "." + String.valueOf(lduCount) + "." + String.valueOf(teamCount) + "."
						+ String.valueOf(managerCount));
						globalLevels.setLevelName(EventManager);
						globalLevels.setLevelValue(result[4].toString());
						globalLevels.setEventProvider(result[0].toString());
						globalLevels.setEventCluster(result[1].toString());
						globalLevels.setEventLDU(result[2].toString());
						globalLevels.setEventTeam(result[3].toString());
						globalLevels.setEventManager(result[4].toString());
						managerMap.put(managerCount, globalLevels);
					}
				}
			}

			String providerjsonStr = gson.toJson(providerMap, LinkedHashMap.class);
			String clusterjsonStr = gson.toJson(clusterMap, LinkedHashMap.class);
			String ldujsonStr = gson.toJson(lduMap, LinkedHashMap.class);
			String teamjsonStr = gson.toJson(teamMap, LinkedHashMap.class);
			String managerjsonStr = gson.toJson(managerMap, LinkedHashMap.class);

			providerJson = (JSONObject) parser.parse(providerjsonStr);
			clusterJson = (JSONObject) parser.parse(clusterjsonStr);
			lduJson = (JSONObject) parser.parse(ldujsonStr);
			teamJson = (JSONObject) parser.parse(teamjsonStr);
			managerJson = (JSONObject) parser.parse(managerjsonStr);

			totalMap.put("EventProviders", providerJson);
			totalMap.put("EventClusters", clusterJson);
			totalMap.put("EventLDUS", lduJson);
			totalMap.put("EventTeams", teamJson);
			totalMap.put("EventManagers", managerJson);
			totalJsonObj.put("Preferences", totalMap);
		}
	} catch (Exception ex) {
		logger.error("Exception at getGlobalPreferences " + ex.getCause(), ex);
	} finally {
		managerMap = null;
		teamMap = null;
		lduMap = null;
		teamMap = null;
		totalMap = null;
		if (session != null) {
			session.close();
		}
	}
	return totalJsonObj;
}


@Override
public JSONObject getToDoPreferences() {
	logger.info("getToDoPreferences executing");
	Session session = null;
	Transaction trx = null;
	Query query = null;
	JSONObject globPrefJson = null;
	String globPrefStr = "";
	try {
		session = sessionFactory.openSession();
		query = session.createQuery("select preferenceJson from PreferenceMaster where name='TodoPreference'");
		if (query.uniqueResult() != null) {
			globPrefStr = query.uniqueResult().toString();
		}
		JSONParser parser = new JSONParser();
		globPrefJson = (JSONObject) parser.parse(globPrefStr);
	} catch (Exception ex) {
		logger.error("Exception at getToDoPreferences executing " + ex.getCause(), ex);
	} finally {
		if (session != null) {
			session.close();
		}
	}
	return globPrefJson;
}

/*
 * (non-Javadoc)
 * @see com.meganexus.toDo.dao.ToDoDao#getToDoPreferences()
 */
public JSONObject getToDoPreferencesOld2() {
	logger.info("getGlobalPreferences preferences executing");
	Session session = null;
	String EventProvider = "EventProvider";
	String EventCluster = "EventCluster";
	String EventLDU = "EventLDU";
	String EventTeam = "EventTeam";
	String EventManager = "EventManager";
	JSONObject totalJsonObj = null;
	totalJsonObj = new JSONObject();

	List<Object[]> level1results = null;
	//ArrayList totalList = new ArrayList();
	try {
		session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select distinct provider from daily.TodoItems order by 1");
		level1results = query.list();

		GlobalLevels globalLevel = new GlobalLevels();
		List<ProviderLevels> providers = new ArrayList<ProviderLevels>();
		List<ClusterLevels> clusters = new ArrayList<ClusterLevels>();
		List<LDULevels> ldus = new ArrayList<LDULevels>();
        List<TeamLevels> teams = new ArrayList<TeamLevels>();
        List<ManagerLevels> managers = new ArrayList<ManagerLevels>();

		for (Object result : level1results) {
			ProviderLevels provider = new ProviderLevels();
		
			if (result!= null) {
				if (!(providers.contains(result.toString()))) {
					provider.setLevelName(EventProvider);
					provider.setLevelValue(result.toString());
					provider.setEventProvider(result.toString());
					provider.setEventCluster("");
					provider.setEventLDU("");
					provider.setEventTeam("");
					provider.setEventManager("");
				}
			}
			List<Object[]> level2results = null;
			Query level2query = session.createSQLQuery(
					"select distinct cluster from daily.TodoItems Where provider='" + result.toString() + "' order by 1");
			level2results = level2query.list();
			ClusterLevels cluster = null;
			
			for (Object result2 : level2results) {
				if (result2 != null) {
					if (!(clusters.contains(result2.toString()))) {
						cluster = new ClusterLevels();
						cluster.setLevelName(EventCluster);
						cluster.setLevelValue(result2.toString());
						cluster.setEventProvider(result.toString());
						cluster.setEventCluster(result2.toString());
						cluster.setEventLDU("");
						cluster.setEventTeam("");
						cluster.setEventManager("");
						clusters.add(cluster);
					}
				}
				
	            List<Object[]> level3results = null;	
	            LDULevels ldu = null;
				Query level3query = session.createSQLQuery("select distinct LDU from daily.TodoItems Where Cluster='" + result2.toString() + "' order by 1");
				level3results = level3query.list();
				    for(Object result3:level3results){
				    	  if(result3 != null){
				    		   if(!(ldus.contains(result3.toString()))){
				    			    ldu = new LDULevels();
				    			    ldu.setLevelName(EventLDU);
				    			    ldu.setLevelValue(result3.toString());
				    			    ldu.setEventProvider(result.toString());
				    			    ldu.setEventCluster(result2.toString());
				    			    ldu.setEventLDU(result3.toString());
				    			    ldu.setEventTeam("");
				    			    ldu.setEventManager("");
				    			    ldus.add(ldu);
				    		     }
				    	  }
				    	  
				    	  List<Object[]> level4results = null;
				    	  TeamLevels team = null;
				          Query level4query = session.createSQLQuery("select distinct Team from daily.TodoItems where LDU='" + result3.toString() + "' order by 1"); 	  
				          level4results = level4query.list();
				           for(Object result4:level4results){
				        	   if(result4 != null){
					    		   if(!(teams.contains(result4.toString()))){
					    			   team = new TeamLevels();
					    			   team.setLevelName(EventTeam);
					    			   team.setLevelValue(result4.toString());
					    			   team.setEventProvider(result.toString());
					    			   team.setEventCluster(result2.toString());
					    			   team.setEventLDU(result3.toString());
					    			   team.setEventTeam(result4.toString());
					    			   team.setEventManager("");
					    			   teams.add(team);
					    		     }
					    	  }
				        	   
				        	   List<Object[]> level5results = null;
                               ManagerLevels manager = null;
                               Query level5query = session.createSQLQuery("select distinct Manager from daily.TodoItems where Team='" + result4.toString() + "' order by 1");
                               level5results = level5query.list();
                               for(Object result5 : level5results){
                            	    if(result5!=null){
                            	    	 if(!(managers.contains(result5.toString()))){
                            	    	   manager = new ManagerLevels();
                            	    	   manager.setLevelName(EventManager);
                            	    	   manager.setLevelValue(result5.toString());
                            	    	   manager.setEventProvider(result.toString());
                            	    	   manager.setEventCluster(result2.toString());
                            	    	   manager.setEventLDU(result3.toString());
                            	    	   manager.setEventTeam(result4.toString());
                            	    	   manager.setEventManager(result5.toString());
                            	    	   managers.add(manager);
  						    		     }
                            	    }
                               }
				        	   team.setManagersList(managers);
				        	   teams.add(team);
				        	   managers = new ArrayList<ManagerLevels>();
				           }
				           ldu.setTeamsList(teams);
				           ldus.add(ldu);
				           teams = new ArrayList<TeamLevels>();
				    	  
				    }
				    cluster.setLdusList(ldus);
				    clusters.add(cluster);
				    ldus = new ArrayList<LDULevels>();
			}
			provider.setClustersList(clusters);
			providers.add(provider);
			clusters = new ArrayList<ClusterLevels>();
		}
		//totalList.add(providers);
		globalLevel.setProviderLists(providers);
		totalJsonObj.put("EventProviders", globalLevel);
	} catch (Exception ex) {
		logger.error("Exception at getGlobalPreferences " + ex.getCause(), ex);
	} finally {
		if (session != null) {
			session.close();
		}

	}
	return totalJsonObj;
}

/*
 * (non-Javadoc)
 * @see com.meganexus.toDo.dao.ToDoDao#getTodoSheetPreferences(int, java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList, int)
 */
@Override
public ArrayList getTodoSheetPreferences(int fileInfoId, String countType, String sortColumnname, String sortOrder,
		ArrayList<UserPreference> userList,int userId) {
	logger.info("getTodoSheetPreferences executing");
	Session session = null;
	Transaction trx = null;
	String sheetRecords = null;
	List<Object[]> SheetList = null;
	LinkedHashMap<String, String> sheetMap = null;
	JSONObject partJson = null;
	JSONObject totalJson = null;
	ArrayList<LinkedHashMap<String, String>> partList = new ArrayList<LinkedHashMap<String, String>>();
    ArrayList<LinkedHashMap<String, String>> totalList = new ArrayList<LinkedHashMap<String, String>>();
    ArrayList<String> prKey = new ArrayList<String>();
    JSONParser parser = new JSONParser();
    String multiSelect = "0";
	if(userList.size()>1){
		multiSelect = "1";
	}
    logger.info("multiSelect:"+multiSelect);
	String selectedValue = getSelectedValueGrid(userList,multiSelect);
    try{
     session = sessionFactory.openSession();
	 trx = session.beginTransaction();
	 logger.info("daily.GetTodoPreferencebyId ****** Param1(TodoId) : "+fileInfoId+ " Param2(Filter) : " +countType + " Param3(sortColumnname) : "+sortColumnname+" Param4(sortOrder) : "+sortOrder + " Param5(selectedValue) :"+selectedValue+ " Param6(userId) :"+userId);
	 ProcedureCall procedureCall =  session.createStoredProcedureCall("daily.GetTodoPreferencebyId");
	 procedureCall.registerParameter("TodoId",Integer.class,ParameterMode.IN);
	 procedureCall.registerParameter("Filter",String.class,ParameterMode.IN);
	 procedureCall.registerParameter("sortColumnname",String.class,ParameterMode.IN);
	 procedureCall.registerParameter("sortOrder",String.class,ParameterMode.IN);
	 procedureCall.registerParameter("selectedValue",String.class,ParameterMode.IN);
	 procedureCall.registerParameter("userId",Integer.class,ParameterMode.IN);
	 procedureCall.registerParameter("columnnames",String.class, ParameterMode.OUT);
	 procedureCall.getParameterRegistration("TodoId").bindValue(fileInfoId);
	 procedureCall.getParameterRegistration("Filter").bindValue(countType);
	 procedureCall.getParameterRegistration("sortColumnname").bindValue(sortColumnname);
	 procedureCall.getParameterRegistration("sortOrder").bindValue(sortOrder);
	 procedureCall.getParameterRegistration("selectedValue").bindValue(selectedValue);
	 procedureCall.getParameterRegistration("userId").bindValue(userId);
	 ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
	 ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
	 SheetList = resultSetOutput.getResultList();
     String SheetHeaders   = (String) procedureCall.getOutputs().getOutputParameterValue("columnnames");
     if(SheetHeaders!=null && resultSetOutput!=null){
     String[] columns = SheetHeaders.split(",");
     Gson gson = new Gson();
     for (Object[] result : SheetList) {
    	    	sheetMap = new LinkedHashMap<String, String>();
    	    	    for(int i=0;i<columns.length;i++){
    	    	    	 if(columns[i]!=null){
    	    	    		 if(result[i]==null){
    	    	    			 result[i] = "";
    	    	    		 }
    	    	    		 sheetMap.put(columns[i],result[i].toString());
    	    	    	 }
    	    	    }
    	    	    String json = gson.toJson(sheetMap,LinkedHashMap.class);
    	    	    partJson = (JSONObject) parser.parse(json);
    	    	    partList.add(sheetMap);
     }
     }
     trx.commit();
    }
    catch(Exception ex){
      logger.error("Exception at getTodoSheetPreferences :"+ex.getCause(),ex);
      trx.rollback();
    }
    finally{
    	if(session!=null){
    		session.close();
    	}
    }
	return partList;
}

@Override
public String saveCheckList(int todoId,int userId,int fileId,String checkStatus) {
	logger.info("#param 1 todoId :"+ todoId + " #param2 userId : "+userId+ " #param3 fileId :"+fileId);
	Session session = null;
	Transaction trx = null;
	List<Object[]> result;
    Query query = null;
	String status = Messages.SAVEFAIL;
    try{
	  session = sessionFactory.openSession();
      trx = session.beginTransaction();
      logger.info("checkStatus :"+checkStatus);
      if(checkStatus.equals("0")){
    	  logger.info("Delete user exec for todoId :"+todoId+ " userId :"+userId+ " fileId :"+fileId); 
          query = session.createSQLQuery("Delete from UserTodos Where UserId=:userId and TodoId=:todoId and FileId=:fileId");
          query.setParameter("userId", userId);
          query.setParameter("todoId", todoId);
          query.setParameter("fileId", fileId);
          query.executeUpdate();
          status = Messages.SAVE_SUCCESS;
          }
      else{
      logger.info("Insert proc exec for todoId :"+todoId+ " userId :"+userId+ " fileId :"+fileId); 	  
      query = session.createSQLQuery("{call dbo.InsertUserTodos(?,?,?)}");
      query.setInteger(0,todoId);
      query.setInteger(1,userId);
      query.setInteger(2,fileId);
      query.executeUpdate();
      status = Messages.SAVE_SUCCESS;
      }
      trx.commit();
    }
	catch(Exception ex){
      logger.error("Exception at saveCheckList :"+ex.getCause(),ex);		
	  trx.rollback();
	}finally{
		if(session!=null){
			session.close();
		}
	   }
	return status;
	
}

/**
 * 
 * @param userList
 * @param multiSelect
 * @return
 */

public String getSelectedValueGrid(ArrayList<UserPreference> userList,String multiSelect){
	String totalSelectedValue="";
	try {
		int prefCount = 0;
		for(UserPreference userPreference:userList){
			prefCount = prefCount+1;		 
			String selectedValue = "";
			String EventProvider = userPreference.getProvider();
			String EventCluster = userPreference.getCluster();
			String EventLDU = userPreference.getLdu();
			String EventTeam =  userPreference.getTeam();
			String EventManager = userPreference.getManager();
			if(multiSelect.equals("1")){
				if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0  && EventManager.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'" + " and  EventLDU=" +"'"  +EventLDU+  "'" +"and EventTeam=" +"'" +EventTeam+ "'"+  " and EventManager=" +"'"+EventManager+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+  "and EventCluster=" +"'"  +EventCluster+  "'" + " and  EventLDU=" +"'"  +EventLDU+  "'"+ " and EventTeam=" +"'" +EventTeam+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'"+ " and EventLDU="+"'"  +EventLDU +"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+ "'"+" and EventCluster=" +"'"  +EventCluster+ "')";
				}else {
					selectedValue = "(EventProvider="+"'"+EventProvider+"')";
				}
				if (prefCount == userList.size()) {
					if (userList.size() == 1) {
						totalSelectedValue = "(" + selectedValue + ")";
					} else {
						totalSelectedValue = "(" + totalSelectedValue + " or " + selectedValue + ")";
					}
				} else {
					if (totalSelectedValue.trim().length() == 0) {
						totalSelectedValue = selectedValue;
					} else {
						totalSelectedValue = totalSelectedValue + " or " + selectedValue;
					}
				}
			} else if(multiSelect.equals("0")){
				boolean OnlyProvider = false;
				if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0  && EventManager.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'" + " and  EventLDU=" +"'"  +EventLDU+  "'" +"and EventTeam=" +"'" +EventTeam+ "'"+  " and EventManager=" +"'"+EventManager+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+  "and EventCluster=" +"'"  +EventCluster+  "'" + " and  EventLDU=" +"'"  +EventLDU+  "'"+ " and EventTeam=" +"'" +EventTeam+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'"+ " and EventLDU="+"'"  +EventLDU +"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+ "'"+" and EventCluster=" +"'"  +EventCluster+ "')";
				}else {
					selectedValue = "(EventProvider="+"'"+EventProvider+"')";
					OnlyProvider = true;
				}
					if (prefCount == userList.size()) {
						if (userList.size() == 1) {
						   totalSelectedValue = "(" + selectedValue + ")";
						   if (OnlyProvider) {
								totalSelectedValue = "";
							} else {
								totalSelectedValue = "(" + selectedValue + ")";
							}
						} else {
							totalSelectedValue = "(" + totalSelectedValue + " or " + selectedValue + ")";
						}
					} else {
						if (totalSelectedValue.trim().length() == 0) {
							totalSelectedValue = selectedValue;
						} else {
							totalSelectedValue = totalSelectedValue + " or " + selectedValue;
						}
					}
			}
		}
	}
	catch(Exception ex){
		logger.error("Exception at getSelectedValueGrid:"+ex.getCause(),ex);

	}
	return totalSelectedValue;
}

public String getSelectedValue(ArrayList<UserPreference> userList,String multiSelect){
	/*String multiSelect = "0";
	if(userList.size()>1){
		multiSelect = "1";
	}*/
	
	String totalSelectedValue="";
	try {
		int prefCount = 0;
		for(UserPreference userPreference:userList){
			prefCount = prefCount+1;		 
			String selectedValue = "";
			String EventProvider = userPreference.getProvider();
			String EventCluster = userPreference.getCluster();
			String EventLDU = userPreference.getLdu();
			String EventTeam =  userPreference.getTeam();
			String EventManager = userPreference.getManager();
			if(multiSelect.equals("1")){
				if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0  && EventManager.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'" + " and  EventLDU=" +"'"  +EventLDU+  "'" +"and EventTeam=" +"'" +EventTeam+ "'"+  " and EventManager=" +"'"+EventManager+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+  "and EventCluster=" +"'"  +EventCluster+  "'" + " and  EventLDU=" +"'"  +EventLDU+  "'"+ " and EventTeam=" +"'" +EventTeam+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'"+ " and EventLDU="+"'"  +EventLDU +"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+ "'"+" and EventCluster=" +"'"  +EventCluster+ "')";
				}else {
					selectedValue = "(EventProvider="+"'"+EventProvider+"')";
				}
				if (prefCount == userList.size()) {
					if (userList.size() == 1) {
						totalSelectedValue = "(" + selectedValue + ")";
					} else {
						totalSelectedValue = "(" + totalSelectedValue + " or " + selectedValue + ")";
					}
				} else {
					if (totalSelectedValue.trim().length() == 0) {
						totalSelectedValue = selectedValue;
					} else {
						totalSelectedValue = totalSelectedValue + " or " + selectedValue;
					}
				}
			} else if(multiSelect.equals("0")){
				boolean OnlyProvider = false;
				if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0  && EventManager.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'" + " and  EventLDU=" +"'"  +EventLDU+  "'" +"and EventTeam=" +"'" +EventTeam+ "'"+  " and EventManager=" +"'"+EventManager+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0 && EventTeam.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+  "and EventCluster=" +"'"  +EventCluster+  "'" + " and  EventLDU=" +"'"  +EventLDU+  "'"+ " and EventTeam=" +"'" +EventTeam+"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0 && EventLDU.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'"+ " and EventLDU="+"'"  +EventLDU +"')";
				}else if(EventProvider.trim().length()>0 && EventCluster.trim().length()>0){
					selectedValue = "(EventProvider="+"'"+EventProvider+ "'"+" and EventCluster=" +"'"  +EventCluster+ "')";
				}else {
					selectedValue = "(EventProvider="+"'"+EventProvider+"')";
					OnlyProvider = true;
				}
					if (prefCount == userList.size()) {
						if (userList.size() == 1) {
						   totalSelectedValue = "(" + selectedValue + ")";
							if (OnlyProvider) {
								totalSelectedValue = "";
							} else {
								totalSelectedValue = "(" + selectedValue + ")";
							}
						} else {
							totalSelectedValue = "(" + totalSelectedValue + " or " + selectedValue + ")";
						}
					} else {
						if (totalSelectedValue.trim().length() == 0) {
							totalSelectedValue = selectedValue;
						} else {
							totalSelectedValue = totalSelectedValue + " or " + selectedValue;
						}
					}
			}
		}
	}
	catch(Exception ex){
		logger.error("Exception at getSelectedValue:"+ex.getCause(),ex);

	}
	return totalSelectedValue;
}
 
 /*public String getSelecetedValuesCount(){
 }*/
 
}
