package com.meganexus.preferences.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.meganexus.performance.model.Staffhierarchy;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.preferences.vo.ClusterLevels;
import com.meganexus.preferences.vo.GlobalLevels;
import com.meganexus.preferences.vo.GlobalPreferencesVo;
import com.meganexus.preferences.vo.LDULevels;
import com.meganexus.preferences.vo.ManagerLevels;
import com.meganexus.preferences.vo.ProviderLevels;
import com.meganexus.preferences.vo.TeamLevels;
import com.meganexus.toDo.dao.ToDoDaoImpl;
import com.meganexus.utils.Messages;
import com.meganexus.utils.SessionKey;

@Repository("globalPreferencesDao")
public class GlobalPreferencesDaoImpl implements GlobalPreferencesDao {

	private static final Logger logger = Logger.getLogger(GlobalPreferencesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.preferences.dao.GlobalPreferencesDao#getStaffGlobalPreferences()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Staffhierarchy> getStaffGlobalPreferences() {
		 Session session  =  null;
		 List<Staffhierarchy> results = null;
		 try{
			 session  =  sessionFactory.openSession();
			 Criteria criteria = session.createCriteria(Staffhierarchy.class);
			 results  = criteria.list();
		 }
		 catch(Exception ex){
			 logger.error("Exception at GetStaffHierarchyDetailsImpl "+ex.getCause(),ex);
		 }
		 finally{
			 if(session!=null){
				 session.close();
			 }
		 }
		 return results;
	}
	
	
	@Override
	public JSONObject getGlobalPreferences() {
		logger.info("getWMTPreferences executing");
		Session session = null;
		Transaction trx = null;
		Query query = null;
		JSONObject globPrefJson = null;
		String globPrefStr = "";
		try {
			session = sessionFactory.openSession();
			query = session.createQuery("select preferenceJson from PreferenceMaster where name='GlobalPreference'");
			if (query.uniqueResult() != null) {
				globPrefStr = query.uniqueResult().toString();
			}
			JSONParser parser = new JSONParser();
			globPrefJson = (JSONObject) parser.parse(globPrefStr);
		} catch (Exception ex) {
			logger.error("Exception at getWMTPreferences executing " + ex.getCause(), ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return globPrefJson;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.preferences.dao.GlobalPreferencesDao#getGlobalPreferences()
	 */
	/*@Override*/
	public JSONObject getGlobalPreferencesOld() {
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
			Query query = session.createSQLQuery("select distinct provider from daily.STAFFHIERARCHY order by 1");
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
				ClusterLevels cluster = null;
				Query level2query = session.createSQLQuery(
						"select distinct cluster from daily.STAFFHIERARCHY Where provider='" + result.toString() + "' order by 1");
				level2results = level2query.list();
				
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
					Query level3query = session.createSQLQuery("select distinct SDU from daily.STAFFHIERARCHY Where cluster='" + result2.toString() + "' order by 1");
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
					          Query level4query = session.createSQLQuery("select distinct Team from daily.STAFFHIERARCHY where SDU='" + result3.toString() + "' order by 1"); 	  
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
                                   Query level5query = session.createSQLQuery("select distinct StaffName from daily.STAFFHIERARCHY where Team='" + result4.toString() + "' order by 1");
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
	
	
	
	
	
	
	
	/*public JSONObject getGlobalPreferencesOld() {
		logger.info("getGlobalPreferences preferences executing");
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
		
		LinkedHashMap<Integer, GlobalLevels> providerMap = new LinkedHashMap<Integer, GlobalLevels>();
		LinkedHashMap<Integer, GlobalLevels> clusterMap = new LinkedHashMap<Integer, GlobalLevels>();
		LinkedHashMap<Integer, GlobalLevels> lduMap = new LinkedHashMap<Integer, GlobalLevels>();
		LinkedHashMap<Integer, GlobalLevels> teamMap = new LinkedHashMap<Integer, GlobalLevels>();
		LinkedHashMap<Integer, GlobalLevels> managerMap = new LinkedHashMap<Integer, GlobalLevels>();
		
		String EventProvider = "EventProvider";
		String EventCluster = "EventCluster";
		String EventLDU = "EventLDU";
		String EventTeam = "EventTeam";
		String EventManager = "EventManager";
		JSONObject totalJsonObj = null;

		GlobalLevels globalLevels = null;
		
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
		LinkedHashMap<String, ArrayList> totalMap = new LinkedHashMap<String, ArrayList>();
		
		ArrayList<GlobalLevels> providerLists = new ArrayList<GlobalLevels>();
		ArrayList<GlobalLevels> clustersLists = new ArrayList<GlobalLevels>();
		ArrayList<GlobalLevels> ldusLists = new ArrayList<GlobalLevels>();
		ArrayList<GlobalLevels> teamsLists = new ArrayList<GlobalLevels>();
		ArrayList<GlobalLevels> managersLists = new ArrayList<GlobalLevels>();
		
	    ArrayList clustersList = new ArrayList();
		ArrayList ldusList = new ArrayList();
		ArrayList teamsList = new ArrayList();
		ArrayList managersList = new ArrayList();
		int globalprovider = 0;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("select distinct 1,provider,cluster,sdu,team,staff_Name from Staffhierarchy");
			results = query.list();
			
			for (Object[] result : results) {
				if (result[1] != null) {
					if (!(providerSet.contains(result[1].toString()))) {
						providerFlag = providerSet.add(result[1].toString());
						if (providerFlag == true) {
							providerCount = providerCount + 1;
							globalprovider = providerCount-1;
							globalLevels = new GlobalLevels();
							globalLevels.setLevelId(String.valueOf(providerCount));
							globalLevels.setLevelName(EventProvider);
							globalLevels.setLevelValue(result[1].toString());
							globalLevels.setProviderId(String.valueOf(providerCount));
							globalLevels.setEventProvider(EventProvider);
							//globalLevels.setClustersList(clustersList);
							//providerMap.put(providerCount, globalLevels);
							providerLists.add(globalLevels);
						}
					}
				}
				if (result[2] != null) {
					if (!(clusterSet.contains(result[2].toString()))) {
						clusterFlag = clusterSet.add(result[2].toString());
						if (clusterFlag == true) {
							clusterCount = clusterCount + 1;
							globalLevels = new GlobalLevels();
							globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount));
							globalLevels.setLevelName(EventCluster);
							globalLevels.setLevelValue(result[2].toString());
							globalLevels.setEventProvider(result[1].toString());
							globalLevels.setEventCluster(result[2].toString());
							globalLevels.setEventLDU("");
							globalLevels.setEventTeam("");
							globalLevels.setEventManager("");
							globalLevels.setProviderId(String.valueOf(providerCount));
							globalLevels.setClusterId(String.valueOf(clusterCount));
							clustersLists.add(globalLevels);
							//clusterMap.put(clusterCount, globalLevels);
						}
					}
				}
				if (result[3] != null) {
					if (!(lduSet.contains(result[3].toString()))) {
						lduFlag = lduSet.add(result[3].toString());
						if (lduFlag == true) {
							lduCount = lduCount + 1;
							globalLevels = new GlobalLevels();
							globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount)+ "." + String.valueOf(lduCount));
							globalLevels.setLevelName(EventLDU);
							globalLevels.setLevelValue(result[3].toString());
							globalLevels.setEventProvider(result[1].toString());
							globalLevels.setEventCluster(result[2].toString());
							globalLevels.setEventLDU(result[3].toString());
							globalLevels.setEventTeam("");
							globalLevels.setEventManager("");
							globalLevels.setProviderId(String.valueOf(providerCount));
							globalLevels.setClusterId(String.valueOf(clusterCount));
							globalLevels.setLduId(String.valueOf(lduCount));
							ldusLists.add(globalLevels);
							//lduMap.put(lduCount, globalLevels);
						}
					}
				}
				if (result[4] != null) {
					if (!(teamSet.contains(result[4].toString()))) {
						teamFlag = teamSet.add(result[4].toString());
						if (teamFlag == true) {
							teamCount = teamCount + 1;
							globalLevels = new GlobalLevels();
							globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount)+ "." + String.valueOf(lduCount) + "." + String.valueOf(teamCount));
							globalLevels.setLevelName(EventTeam);
							globalLevels.setLevelValue(result[4].toString());
							globalLevels.setEventProvider(result[1].toString());
							globalLevels.setEventCluster(result[2].toString());
							globalLevels.setEventLDU(result[3].toString());
							globalLevels.setEventTeam(result[4].toString());
							globalLevels.setEventManager("");
							globalLevels.setProviderId(String.valueOf(providerCount));
							globalLevels.setClusterId(String.valueOf(clusterCount));
							globalLevels.setLduId(String.valueOf(lduCount));
							globalLevels.setTeamId(String.valueOf(teamCount));
							teamsLists.add(globalLevels);
							//teamMap.put(teamCount, globalLevels);
						}
					}
				}
				if (result[5] != null) {
					if (!(managerSet.contains(result[5].toString()))) {
						if (managerFlag == true) {
							managerCount = managerCount + 1;
							globalLevels = new GlobalLevels();
							globalLevels.setLevelId(String.valueOf(providerCount) + "." + String.valueOf(clusterCount)+ "." + String.valueOf(lduCount) + "." + String.valueOf(teamCount) + "."
							+ String.valueOf(managerCount));
							globalLevels.setLevelName(EventManager);
							globalLevels.setLevelValue(result[5].toString());
							globalLevels.setEventProvider(result[1].toString());
							globalLevels.setEventCluster(result[2].toString());
							globalLevels.setEventLDU(result[3].toString());
							globalLevels.setEventTeam(result[4].toString());
							globalLevels.setEventManager(result[5].toString());
							globalLevels.setProviderId(String.valueOf(providerCount));
							globalLevels.setClusterId(String.valueOf(clusterCount));
							globalLevels.setLduId(String.valueOf(lduCount));
							globalLevels.setTeamId(String.valueOf(teamCount));
							globalLevels.setManagerId(String.valueOf(managerCount));
							managersLists.add(globalLevels);
							//managerMap.put(managerCount, globalLevels);
						}
					}
				}

				String providerjsonStr = gson.toJson(providerMap, LinkedHashMap.class);
				String clusterjsonStr = gson.toJson(clusterMap, LinkedHashMap.class);
				String ldujsonStr = gson.toJson(lduMap, LinkedHashMap.class);
				String teamjsonStr = gson.toJson(teamMap, LinkedHashMap.class);
				String managerjsonStr = gson.toJson(managerMap, LinkedHashMap.class);
				
				//providerJson = (JSONObject) parser.parse(providerLists);
				//clusterJson = (JSONObject) parser.parse(clusterjsonStr);
				//lduJson = (JSONObject) parser.parse(ldujsonStr);
				//teamJson = (JSONObject) parser.parse(teamjsonStr);
				//managerJson = (JSONObject) parser.parse(managerjsonStr);
				
				totalMap.put("EventProviders", providerJson);
				totalMap.put("EventClusters", clusterJson);
				totalMap.put("EventLDUS", lduJson);
				totalMap.put("EventTeams", teamJson);
				totalMap.put("EventManagers", managerJson);
				
				//Gson gson = new GsonBuilder().create();
				
				//JsonArray providerArray = gson.toJsonTree(providerLists).getAsJsonArray();
				//JsonArray clusterArray  = gson.toJsonTree(clustersLists).getAsJsonArray();
				//JsonArray lduArray  = gson.toJsonTree(ldusLists).getAsJsonArray();
				//JsonArray teamArray  = gson.toJsonTree(teamsLists).getAsJsonArray();
				//JsonArray managerArray = gson.toJsonTree(managersLists).getAsJsonArray();
				
				
				*//**************** working on changes *********************************//*
				
				totalMap.put("EventProviders", providerLists);
				totalMap.put("EventClusters", clustersLists);
				totalMap.put("EventLDUS", ldusLists);
				totalMap.put("EventTeams", teamsLists);
				totalMap.put("EventManagers", managersLists);
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
	}*/
	
	

	@Override
	public int saveUserPreference(ArrayList<UserPreference> userList,int userId) {
		 logger.info("GlobalPreferencesDaoImpl saveUserPreference executing");
		 int RecordCount = 0;
		 Session session = null;
		 Transaction trx = null;
		 boolean deleteFlag = true;
		 try{
			 session = sessionFactory.openSession();
			 trx = session.beginTransaction();
			    if(deleteFlag = true){
				  Query query2 = session.createSQLQuery("Delete from dbo.UserPreferences where UserId=:userid");
				  query2.setParameter("userid", userId);
				  query2.executeUpdate();
				  deleteFlag = false;
				  logger.info("Delteted successfully for user : "+userId);
				  }
			 
			 for(UserPreference userPreference:userList){
				  SQLQuery query = session.createSQLQuery("insert into dbo.UserPreferences(Provider,Cluster,LDU,Team,Manager,UserId) values (:provider,:cluster,:ldu,:team,:manager,:userid)");
                  query.setParameter("provider",userPreference.getProvider());
                  query.setParameter("cluster",userPreference.getCluster());
                  query.setParameter("ldu",userPreference.getLdu());
                  query.setParameter("team",userPreference.getTeam());
                  query.setParameter("manager",userPreference.getManager());
                  query.setParameter("userid",userPreference.getUser().getUserId());
                  logger.info("Saved successfully for user : "+userPreference.getUser().getUserId());
                  //logger.info(query.toString());
                  RecordCount +=query.executeUpdate();
			 }
			 trx.commit();
			 logger.info("Record count:"+RecordCount);
		 }
		 catch(Exception ex){
			 logger.error("Exception at saving user preferences :"+ex.getCause(),ex);
			 trx.rollback();
		 }
         finally{
        	 if(session!=null){
        		session.close();
        	 }
         }
		return RecordCount;
	}

	
	public JSONObject loadSelectedPreferencesOld(String userId) {
		logger.info("globalPreferencesDaoImpl loadSelectedPreferencesOld preferences executing");
		Session session = null;
		List<Object[]> results = null;
		JSONObject totalJson = new JSONObject();
		JSONObject recordJson = null;
		Query query = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		try{
			session = sessionFactory.openSession();
			//query = session.createSQLQuery("select Provider,Cluster,LDU,Team,Manager from dbo.UserPreferences where UserId=2 order by PrefId");
			query = session.createSQLQuery("select Provider,Cluster,LDU,Team,Manager from dbo.UserPreferences where UserId=:userId order by PrefId");
			query.setParameter("userId",userId);
			results = query.list();
			int recCount = 0;
			for(Object[] result:results){
				recCount = recCount+1;
				recordJson = new JSONObject();
				if(result[0]!=null){
					recordJson.put(eventProvider, result[0].toString()); 					
				}
				if(result[1]!=null){
					recordJson.put(eventCluster, result[1].toString());
				}
				if(result[2]!=null){
					recordJson.put(eventLDU, result[2].toString());
				}
			    if(result[3]!=null){
			    	recordJson.put(eventTeam, result[3].toString());
			    }
			    if(result[4]!=null){
			    	recordJson.put(eventManager, result[4].toString());
			    }
			    totalJson.put(recCount,recordJson);
			}
		}
		catch(Exception ex){
			logger.error("Exception at loadSelectedPreferencesOld " + ex.getCause(), ex);
		}
		
		return totalJson;
	}
	
	@Override
	public ArrayList loadSelectedPreferences(String userId) {
		logger.info("globalPreferencesDaoImpl loadSelectedPreferencesOld preferences executing");
		Session session = null;
		List<Object[]> results = null;
		JSONObject totalJson = new JSONObject();
		JSONObject recordJson = null;
		Query query = null;
		String eventProvider = Messages.EventProvider;
		String eventCluster = Messages.EventCluster;
		String eventLDU = Messages.EventLDU;
		String eventTeam = Messages.EventTeam;
		String eventManager = Messages.EventManager;
		String eventSelect  = Messages.EventSelect;
		
		ArrayList recList =  new ArrayList();
		try{
			session = sessionFactory.openSession();
			//query = session.createSQLQuery("select Provider,Cluster,LDU,Team,Manager from dbo.UserPreferences where UserId=2 order by PrefId");
			query = session.createSQLQuery("select Provider,Cluster,LDU,Team,Manager from dbo.UserPreferences where UserId=:userId order by PrefId");
			query.setParameter("userId",userId);
			results = query.list();
			int recCount = 0;
			for(Object[] result:results){
				recCount = recCount+1;
				recordJson = new JSONObject();
				if(result[0]!=null){
					recordJson.put(eventProvider, result[0].toString());
				}
				if(result[1]!=null){
					recordJson.put(eventCluster, result[1].toString());
				}
				if(result[2]!=null){
					recordJson.put(eventLDU, result[2].toString());
				}
			    if(result[3]!=null){
			    	recordJson.put(eventTeam, result[3].toString());
			    }
			    if(result[4]!=null){
			    	recordJson.put(eventManager, result[4].toString());
			    }
			    if(!(result[0].toString().trim().equals("")) && !(result[1].toString().trim().equals("")) && !(result[2].toString().trim().equals("")) && !(result[3].toString().trim().equals("")) && !(result[4].toString().trim().equals(""))){
			    	eventSelect = Messages.EventManager;
			    }else if(!(result[0].toString().trim().equals("")) && !(result[1].toString().trim().equals("")) && !(result[2].toString().trim().equals("")) && !(result[3].toString().trim().equals(""))){
			    	eventSelect = Messages.EventTeam;
			    }else if( !(result[0].toString().trim().equals("")) && !(result[1].toString().trim().equals("")) && !(result[2].toString().trim().equals(""))){
			    	eventSelect = Messages.EventLDU;
			    }else if(!(result[0].toString().trim().equals("")) && !(result[1].toString().trim().equals(""))){
			    	eventSelect = Messages.EventCluster;
			    }else if(!(result[0].toString().trim().equals(""))){
			    	eventSelect = Messages.EventProvider;
			    }else{
			    	eventSelect = Messages.EventProvider;
			    }
			    recordJson.put(Messages.EventSelect,eventSelect);
			    recList.add(recordJson);
			}
		}
		catch(Exception ex){
			logger.error("Exception at loadSelectedPreferencesOld " + ex.getCause(), ex);
		}
		
		return recList;
	}

	//LinkedHashMap<String, ArrayList> totalMap = new LinkedHashMap<String, ArrayList>();<String, ArrayList> totalMap = new LinkedHashMap<String, ArrayList>();

}




