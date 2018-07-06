package com.meganexus.performance.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.ParameterMode;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.meganexus.performance.vo.MetricsVo;
import com.meganexus.performance.vo.PerfSelectPreference;
import com.meganexus.utils.Messages;

@Repository("performanceDao")
public class PerformanceDaoImpl implements PerformanceDao{
	private static final Logger logger = Logger.getLogger(PerformanceDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public JSONObject getPerformancePreferences() {
		logger.info("getPerformancePreferences executing");
		Session session = null;
		Query query = null;
		JSONObject globPrefJson = null;
		String globPrefStr = "";
		try {
			session = sessionFactory.openSession();
			query = session.createQuery("select preferenceJson from PreferenceMaster where name='PerformancePreference'");
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

	@Override
	public ArrayList<MetricsVo> getMetrics(String month, String multiSelect, ArrayList<PerfSelectPreference> userList) {
		logger.info("PerformanceDaoImpl getMetrics executing");
		Session session = null;
		Transaction trx = null;
		Query query = null;
		List<Object[]> results = null;
		MetricsVo metricsVo = null;
		ArrayList<MetricsVo> metricsList = new ArrayList<MetricsVo>();
		try{
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			int prefCount = 0;
			for (PerfSelectPreference userPreference : userList) {
				prefCount = prefCount + 1;
				String EventProvider = userPreference.getProvider();
				String EventCluster = userPreference.getCluster();
				String EventLDU = userPreference.getLdu();
				String EventTeam = userPreference.getTeam();
				String EventManager = userPreference.getManager();
				String EventSelect  = userPreference.getSelectedLevel();
				String selectedValue = formSelecetedValue(EventProvider, EventCluster,EventLDU,EventTeam,EventManager,multiSelect,EventSelect);
				logger.info("param1(SelectedLevel) :"+EventSelect +" param2(SelectedValue):"+selectedValue+ " param3(month):"+month+ " param4(multiSelect) : "+multiSelect);
				 ProcedureCall procedureCall =  session.createStoredProcedureCall("daily.GetPerformanceItems");
				 procedureCall.registerParameter("selectedlevel",String.class,ParameterMode.IN);
				 procedureCall.registerParameter("selectedValue",String.class,ParameterMode.IN);
				 procedureCall.registerParameter("month",String.class,ParameterMode.IN);
				 procedureCall.registerParameter("multiselect",Integer.class,ParameterMode.IN);
				 procedureCall.getParameterRegistration("selectedlevel").bindValue(EventSelect);
				 procedureCall.getParameterRegistration("selectedValue").bindValue(selectedValue);
				 procedureCall.getParameterRegistration("month").bindValue(month);
				 procedureCall.getParameterRegistration("multiselect").bindValue(Integer.parseInt(multiSelect));
				 ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
				 ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
				 results = resultSetOutput.getResultList();
				for (Object[] result : results) {
					metricsVo = new MetricsVo();
					 if(result[0]!=null){
						 metricsVo.setSelectedValue(result[0].toString());
					 }
					 if(result[1]!=null){
						 metricsVo.setPerformanceId(result[1].toString());
					 } 
					 if(result[2]!=null){
						 metricsVo.setMetric(result[2].toString());
					 }
					 if(result[3]!=null){
						 metricsVo.setName(result[3].toString());
					 }
					 if(result[4]!=null){
						 metricsVo.setPerformance(result[4].toString());
					 }
					 if(result[5]!=null){
						 metricsVo.setTasks(result[5].toString());
					 }
					 if(result[6]!=null){
						 metricsVo.setPositive(result[6].toString());
					 }
					 if(result[7]!=null){
						 metricsVo.setNegative(result[7].toString());
					 }
					 if(result[8]!=null){
						 metricsVo.setNeutral(result[8].toString());
					 }
					 if(result[9]!=null){
						 metricsVo.setRisk(result[9].toString());
					 }
					 if(result[10]!=null){
						 metricsVo.setTarget(result[10].toString());
					 }
					 if(result[11]!=null){
						 metricsVo.setTrigger(result[11].toString());
					 }
					 if(result[12]!=null){
						 metricsVo.setWeight(result[12].toString());
					 }
					metricsList.add(metricsVo);
				}
			}
		}
		catch(Exception ex){
		  logger.error("Exception at PerformanceDaoImpl getMetrics :"+ex.getCause(),ex);
		}
		finally{
           if(session!=null){
        	   session.close();
           }
		}
		return metricsList;
	}
	
	 /**
	  * 
	  * @param EventProvider
	  * @param EventCluster
	  * @param EventLDU
	  * @param EventTeam
	  * @param EventManager
	  * @return 
	  */
	 public String formSelecetedValue(String EventProvider,String EventCluster,String EventLDU,String EventTeam,String EventManager,String multiSelect,String EventSelect){
		String selectedValue = "";
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
		}else if(multiSelect.equals("0")){
			  if(EventSelect.equals(Messages.EventProvider)){
				  selectedValue = "(EventProvider="+"'"+EventProvider+"'"+")";
			  }else if(EventSelect.equals(Messages.EventCluster)){
			  selectedValue = "(EventProvider="+"'"+EventProvider+"'"+")";
			  }else if(EventSelect.equals(Messages.EventLDU)){
				  selectedValue = "(EventProvider="+"'"+EventProvider+ "'"+" and EventCluster=" +"'"  +EventCluster+ "')";
			  }else if(EventSelect.equals(Messages.EventTeam)){
			    selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+ " and EventCluster=" +"'"  +EventCluster+ "'"+ " and EventLDU="+"'"  +EventLDU +"')";
			  }else if(EventSelect.equals(Messages.EventManager)){
				  selectedValue = "(EventProvider="+"'"+EventProvider+  "'"+  "and EventCluster=" +"'"  +EventCluster+  "'" + " and  EventLDU=" +"'"  +EventLDU+  "'"+ " and EventTeam=" +"'" +EventTeam+"')"; 
			  }else{
				  selectedValue=""; 
			  }
		}
		return selectedValue;
	  }

	 
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.performance.dao.PerformanceDao#getCaseList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LinkedHashMap<String, String>> getCaseList(String performanceId, String month,
			String selectedValue, String filter) {
		logger.info("PerformanceDaoImpl getCaseList executing");
		Session session = null;
		Transaction trx = null;
		Query query = null;
		List<Object[]> results = null;
		List<Object[]> SheetList = null;
		LinkedHashMap<String, String> sheetMap = null;
		ArrayList<LinkedHashMap<String, String>> totalList = new ArrayList<LinkedHashMap<String, String>>();
		try {
			logger.info("daily.GetPerformanceCaseList **** Param1(performanceId) : "+performanceId+ " Param2(month) : "+month+ " Param3(selectedValue) : "+selectedValue + " Param4(filter) : "+filter);
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			ProcedureCall procedureCall = session.createStoredProcedureCall("daily.GetPerformanceCaseList");
			procedureCall.registerParameter("performanceId", Integer.class, ParameterMode.IN);
			procedureCall.registerParameter("month", String.class, ParameterMode.IN);
			procedureCall.registerParameter("selectedValue", String.class, ParameterMode.IN);
			procedureCall.registerParameter("Filter", String.class, ParameterMode.IN);
			procedureCall.registerParameter("columnnames", String.class, ParameterMode.OUT);
			procedureCall.getParameterRegistration("performanceId").bindValue(Integer.parseInt(performanceId));
			procedureCall.getParameterRegistration("month").bindValue(month);
			procedureCall.getParameterRegistration("selectedValue").bindValue(selectedValue);
			procedureCall.getParameterRegistration("Filter").bindValue(filter);
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			SheetList = resultSetOutput.getResultList();
			String SheetHeaders = (String) procedureCall.getOutputs().getOutputParameterValue("columnnames");
			logger.info("SheetHeaders :"+SheetHeaders);
			if (SheetHeaders != null && resultSetOutput != null) {
				String[] columns = SheetHeaders.split(",");
				Gson gson = new Gson();
				for (Object[] result : SheetList) {
					sheetMap = new LinkedHashMap<String, String>();
					for (int i = 0; i < columns.length; i++) {
						if (columns[i] != null) {
							if (result[i] == null) {
								result[i] = "";
							}
							sheetMap.put(columns[i], result[i].toString());
						}
					}
					totalList.add(sheetMap);
				}
			}
			trx.commit();
		} catch (Exception ex) {
			logger.error("Exception at PerformanceDaoImpl getCaseList : " + ex.getCause(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return totalList;
	}

   @Override
   public String insertNotes(String performanceId, String uniqueId, String Notes,int userId){
	    logger.info("PerformanceDaoImpl insertNotes executing");
	    Session session = null;
		Transaction trx = null;
		Query query = null;
		String status = Messages.SAVE_SUCCESS;
	    try{
	    	logger.info("performanceId : "+performanceId+ " uniqueId : "+uniqueId+ " Notes : "+Notes);
	    	session = sessionFactory.openSession();
	    	trx = session.beginTransaction();
	    	query = session.createSQLQuery("insert into daily.PerformanceNotes(PerformanceId,UniqueId,Notes,CreatedBy,CreatedDate) values(:PerformanceId,:UniqueId,:Notes,:CreatedBy,GETDATE())");
	    	query.setParameter("PerformanceId",performanceId);
	    	query.setParameter("UniqueId",uniqueId);
	    	query.setParameter("Notes",Notes);
	    	query.setParameter("CreatedBy",userId);
	    	query.executeUpdate();
	    	trx.commit();
	      }
	   catch(Exception ex){
		  logger.info("Exception at PerformanceDaoImpl insertNotes : "+ex.getClass(),ex);  
		  status = Messages.SAVE_FAIL;
		  trx.rollback();
	   }
	   return status;
   }

@Override
public String updateNotes(String performanceId, String uniqueId, String Notes, int userId) {
	  logger.info("PerformanceDaoImpl updateNotes executing");
	    Session session = null;
		Transaction trx = null;
		Query query = null;
		String status = Messages.UPDATE_SUCCESS;
	    try{
	    	logger.info("performanceId : "+performanceId+ " uniqueId : "+uniqueId+ " Notes : "+Notes);
	    	session = sessionFactory.openSession();
	    	trx = session.beginTransaction();
	    	query = session.createSQLQuery("update daily.PerformanceNotes set Notes=:notes,ModifiedBy=:ModifiedBy,ModifiedDate=GETDATE() where PerformanceId=:PerformanceId and UniqueId=:UniqueId");
	    	query.setParameter("notes",Notes);
	    	query.setParameter("ModifiedBy",userId);
	    	query.setParameter("PerformanceId",performanceId);
	    	query.setParameter("UniqueId",uniqueId);
	    	query.executeUpdate();
	    	trx.commit();
	      }
	   catch(Exception ex){
		  logger.info("Exception at PerformanceDaoImpl updateNotes : "+ex.getClass(),ex);  
		  status = Messages.UPDATE_FAIL;
		  trx.rollback();
	   }
	   return status;
}

@Override
public JSONObject selectNotes(String performanceId, String uniqueId) {
	 logger.info("PerformanceDaoImpl selectNotes Executing");
	 Session session = null;
     Transaction trx = null;
     Query query = null;
     JSONObject jsonNote = new JSONObject();
	try{
		logger.info("performanceId : "+performanceId+ " uniqueId : "+uniqueId);    
		session = sessionFactory.openSession();
		query = session.createSQLQuery("select Notes from daily.PerformanceNotes where PerformanceId=:PerformanceId and UniqueId=:UniqueId");
		query.setParameter("PerformanceId",performanceId);
		query.setParameter("UniqueId",uniqueId);
		jsonNote.put("Note",query.list().toString());
	  }
	catch(Exception ex){
		jsonNote.put("Note","");
		logger.info("Exception at PerformanceDaoImpl selectNotes : "+ex.getClass(),ex);  
	}
	return jsonNote;
}

}
