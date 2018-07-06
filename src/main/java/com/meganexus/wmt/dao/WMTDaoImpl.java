package com.meganexus.wmt.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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
import com.meganexus.utils.CommonUtils;
import com.meganexus.utils.Messages;
import com.meganexus.wmt.vo.WMTBandVo;
import com.meganexus.wmt.vo.WMTCashManagerVo;
import com.meganexus.wmt.vo.WMTSelectPreference;

@Repository("wmtDao")
public class WMTDaoImpl implements WMTDao {
	private static final Logger logger = Logger.getLogger(WMTDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.wmt.dao.WMTDao#getBandDetails(java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList)
	 */
	@Override
	public ArrayList<WMTBandVo> getBandDetails(String multiSelect,String grade,
			ArrayList<WMTSelectPreference> prefList) {
		logger.info("WMTDaoImpl getBandDetails executing");
		Session session = null;
		Transaction trx = null;
		Query query = null;
		List<Object[]> results = null;
		WMTBandVo wmtBand = null;
		ArrayList<WMTBandVo> bandList = new ArrayList<WMTBandVo>();
		try {
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			int prefCount = 0;
			for (WMTSelectPreference userPreference : prefList) {
				prefCount = prefCount + 1;
				String EventProvider = userPreference.getProvider();
				String EventCluster = userPreference.getCluster();
				String EventLDU = userPreference.getLdu();
				String EventTeam = userPreference.getTeam();
				String EventManager = userPreference.getManager();
				String EventSelect  = userPreference.getSelectedLevel();
				String selectedValue = formSelecetedValue(EventProvider, EventCluster,EventLDU,EventTeam,EventManager,multiSelect,EventSelect);
				logger.info("param1(EventSelect) :"+EventSelect +" param2(EventSelect):"+selectedValue+ " param3(grade):"+grade+ " param4(multiSelect) : "+multiSelect);
				query = session.createSQLQuery("{call daily.GetWmtItems(?,?,?,?)}");
				query.setString(0, EventSelect);
				query.setString(1, selectedValue);
				query.setString(2, grade);
				query.setInteger(3, Integer.parseInt(multiSelect));
				query.executeUpdate();
				results = query.list();
				for (Object[] result : results) {
					wmtBand = new WMTBandVo();
					if(result[0] != null){
					    wmtBand.setSelectedValue(result[0].toString());
					}
					if (result[1] != null) {
						wmtBand.setName(result[1].toString());
					}
					if (result[2] != null) {
						wmtBand.setCases(result[2].toString());
					}
					if (result[3] != null) {
						wmtBand.setVolume(result[3].toString());
					}
					if (result[4] != null) {
						wmtBand.setCapacity(result[4].toString());
					}
					if (result[5] != null) {
						wmtBand.setFte(result[5].toString());
					}
					if (result[6] != null) {
						wmtBand.setAdj(result[6].toString());
					}
					if (result[7] != null) {
						wmtBand.setCm(result[7].toString());
					}
					if (result[8] != null) {
						wmtBand.setScm(result[8].toString());
					}
					if (result[9] != null) {
						wmtBand.setBand0(result[9].toString());
					}
					if (result[10] != null) {
						wmtBand.setBand1(result[10].toString());
					}
					if (result[11] != null) {
						wmtBand.setBand2(result[11].toString());
					}
					if (result[12] != null) {
						wmtBand.setBand3(result[12].toString());
					}
					if (result[13] != null) {
						wmtBand.setBand4(result[13].toString());
					}
				    if(result[14] != null){
				    	wmtBand.setCommunity(result[14].toString());
				    }
                    if(result[15] != null){
                    	wmtBand.setCustody(result[15].toString());
                    }
                    if(result[16] != null){
                    	wmtBand.setDormant(result[16].toString());
                    }
                    if(result[17] != null){
                    	wmtBand.setLastUploadDate(result[17].toString());
                    }
					bandList.add(wmtBand);
				}
			}
			trx.commit();
		} catch (Exception ex) {
			logger.error("Exception at getBandDetails : " + ex.getCause(), ex);
			trx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return bandList;
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

	@Override
	public ArrayList<LinkedHashMap<String, String>> getCaseList(String selectedValue, String grade) {
		logger.info("WMTDaoImpl getCaseList executing");
		Session session = null;
		Transaction trx = null;
		Query query = null;
		List<Object[]> results = null;
		List<Object[]> SheetList = null;
		LinkedHashMap<String, String> sheetMap = null;
		ArrayList<LinkedHashMap<String, String>> totalList = new ArrayList<LinkedHashMap<String, String>>();
		try {
			logger.info("Param1(selectedValue) : "+selectedValue+ " Param2(grade) : "+grade);
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			ProcedureCall procedureCall = session.createStoredProcedureCall("daily.GetWmtCaseList");
			procedureCall.registerParameter("selectedValue", String.class, ParameterMode.IN);
			procedureCall.registerParameter("grade", String.class, ParameterMode.IN);
			procedureCall.registerParameter("columnnames", String.class, ParameterMode.OUT);
			procedureCall.getParameterRegistration("selectedValue").bindValue(selectedValue);
			procedureCall.getParameterRegistration("grade").bindValue(grade);
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
			logger.error("Exception at WMTDaoImpl getCaseList : " + ex.getCause(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return totalList;
	}

    /*
     * (non-Javadoc)
     * @see com.meganexus.wmt.dao.WMTDao#getCashManager(java.lang.String)
     */
	@Override
	public ArrayList<WMTCashManagerVo> getCashManager(String selectedValue) {
		Session session = null;
		Transaction trx = null;
		Query query = null;
		List<Object[]> results = null;
		WMTCashManagerVo cashManagerVo = null;
		ArrayList<WMTCashManagerVo> cashManagerList = new ArrayList<WMTCashManagerVo>();
	  try {
		  session = sessionFactory.openSession();
		  query = session.createSQLQuery("select Id,Provider,Cluster,LDU,Team,Manager,Type,Hours,[Start Date],[End Date],Convert(numeric(10,3),ISNULL(Hours,0)/25.99961538) FTE from daily.PerAdj where GETDATE() >=[Start Date] and GETDATE()<=[End Date] "+selectedValue);
		  results = query.list();
		  for (Object[] result : results) {
			     cashManagerVo = new WMTCashManagerVo();
			     if(result[0]!=null){
			    	 cashManagerVo.setId(result[0].toString());
			     }
			     if(result[1]!=null){
			    	 cashManagerVo.setCashManager(result[1].toString()+"/"+result[2].toString()+"/"+result[3].toString()+"/"+result[4].toString()+"/"+result[5].toString());
			     }
			     if(result[6]!=null){
			    	 cashManagerVo.setType(result[6].toString());
			     }
			     if(result[7]!=null){
			    	 cashManagerVo.setHours(result[7].toString());
			     }
			     if(result[8]!=null){
			    	 cashManagerVo.setStartDate(result[8].toString());
			     }
			     if(result[9]!=null){
			    	 cashManagerVo.setEndDate(result[9].toString());
			     }
			     if(result[10]!=null){
			    	 cashManagerVo.setFteAdjust(result[10].toString());
			     }
			     cashManagerList.add(cashManagerVo);
		  }
		 }
		 catch(Exception ex){
			 logger.error("Exception WMTDaoImpl at getCashManager:"+ex.getCause(),ex);
		 }
	    finally{
	    	if(session!=null){
	    		session.close();
	    	}
	    }
		return cashManagerList;
	}


	@Override
	public String deleteCashManager(String id) {
		Session session = null;
		Transaction trx = null;
		Query query = null;
        String status = Messages.DELETE_SUCCESS;
		try{
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			query = session.createSQLQuery("Delete from daily.PerAdj where Id =:id");
			query.setParameter("id",id);
			query.executeUpdate();
			trx.commit();
		}
		catch(Exception ex){
			status = Messages.DELETE_FAIL;
			logger.error("Exception WMTDaoImpl at getCashManager ");
			trx.rollback();
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return status;
	}


	@Override
	public String updateCashManager(String id,String startDate, String endDate, String hours,String type) {
		Session session = null;
		Transaction trx = null;
		Query query = null;
		String status = Messages.UPDATE_SUCCESS;
		try{
			session = sessionFactory.openSession();
			trx = session.beginTransaction();
			String updateCondition = formUpdateParameter(startDate,endDate,hours,type);
			query = session.createSQLQuery("update daily.PerAdj set "+updateCondition+" where Id =:id");
			query.setParameter("id",id);
			query.executeUpdate();
			trx.commit();
		 }
		catch(Exception ex){
			status = Messages.UPDATE_FAIL;
			logger.error("Exception WMTDaoImpl at updateCashManager :"+ex.getCause(),ex);
			trx.rollback();
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return status;
	}
	
	
	public String formUpdateParameter(String startDate, String endDate, String hours,String type){
		String updateCondition = "";
		if(startDate!=null && !(startDate.equals(""))){
			Date start_Date = CommonUtils.convertDate(startDate);
			startDate = CommonUtils.formatDate(start_Date);
			updateCondition = " [Start Date]="+startDate;
		}
		if(endDate!=null && !(endDate.equals(""))){
			Date end_Date = CommonUtils.convertDate(endDate);
			endDate = CommonUtils.formatDate(end_Date);
			 if(updateCondition.trim().length()>0){
				 updateCondition = updateCondition+" and [End Date]="+endDate;
			 }else{
				 updateCondition = " [End Date]="+endDate;
			 }
		}
		if(hours!=null && !(hours.equals(""))){
			 if(updateCondition.trim().length()>0){
				 updateCondition = updateCondition+ " and Hours = "+hours;
			 }else{
				 updateCondition = " Hours = "+hours;
			 }
		}
		if(type!=null && !(type.equals(""))){
			if(updateCondition.trim().length()>0){
				 updateCondition = updateCondition+ " and Type = "+type;
			 }else{
				 updateCondition = " Type = "+type;
			 }
		}
		return updateCondition;
	}

	@Override
	public JSONObject getWMTPreferences() {
		logger.info("getWMTPreferences executing");
		Session session = null;
		Transaction trx = null;
		Query query = null;
		JSONObject wmtPrefJson = null;
		String wmtPrefStr = "";
		try {
			session = sessionFactory.openSession();
			query = session.createQuery("select preferenceJson from PreferenceMaster where name='WMTPreference'");
			if (query.uniqueResult() != null) {
				wmtPrefStr = query.uniqueResult().toString();
			}
			JSONParser parser = new JSONParser();
			wmtPrefJson = (JSONObject) parser.parse(wmtPrefStr);
		} catch (Exception ex) {
			logger.error("Exception at getWMTPreferences executing " + ex.getCause(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return wmtPrefJson;
	}
	
	
	/**
	 * Filter distinct object
	 * @param keyExtractor
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor){
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	
}
