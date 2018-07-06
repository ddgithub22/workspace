package com.meganexus.preferences.vo;

import java.util.ArrayList;


public class GlobalsLevels {
	
	
	private String levelName;
	private String levelValue;
	private String levelId;
	private String  eventProvider;
	private String  eventCluster;
	private String  eventLDU;
	private String  eventTeam;
	private String  eventManager;
	
	private String  providerId;
	private String  clusterId;
	private String  lduId;
	private String  teamId;
	private String  managerId;
	private ArrayList clustersList;
	private ArrayList ldusList;
	private ArrayList teamsList;
	private ArrayList managersList;
	
	/*private LinkedHashMap<String,LinkedHashMap> subLevel;*/

	/*public LinkedHashMap getSubLevel() {
		return subLevel;
	}
	public void setSubLevel(LinkedHashMap subLevel) {
		this.subLevel = subLevel;
	}*/
	
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public ArrayList getClustersList() {
		return clustersList;
	}
	public void setClustersList(ArrayList clustersList) {
		this.clustersList = clustersList;
	}
	public ArrayList getLdusList() {
		return ldusList;
	}
	public void setLdusList(ArrayList ldusList) {
		this.ldusList = ldusList;
	}
	public ArrayList getTeamsList() {
		return teamsList;
	}
	public void setTeamsList(ArrayList teamsList) {
		this.teamsList = teamsList;
	}
	public ArrayList getManagersList() {
		return managersList;
	}
	public void setManagersList(ArrayList managersList) {
		this.managersList = managersList;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getLduId() {
		return lduId;
	}
	public void setLduId(String lduId) {
		this.lduId = lduId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getEventProvider() {
		return eventProvider;
	}
	/*public LinkedHashMap<String, LinkedHashMap> getSubLevel() {
		return subLevel;
	}
	public void setSubLevel(LinkedHashMap<String, LinkedHashMap> subLevel) {
		this.subLevel = subLevel;
	}*/
	public void setEventProvider(String eventProvider) {
		this.eventProvider = eventProvider;
	}
	public String getEventCluster() {
		return eventCluster;
	}
	public void setEventCluster(String eventCluster) {
		this.eventCluster = eventCluster;
	}
	public String getEventLDU() {
		return eventLDU;
	}
	public void setEventLDU(String eventLDU) {
		this.eventLDU = eventLDU;
	}
	public String getEventTeam() {
		return eventTeam;
	}
	public void setEventTeam(String eventTeam) {
		this.eventTeam = eventTeam;
	}
	public String getEventManager() {
		return eventManager;
	}
	public void setEventManager(String eventManager) {
		this.eventManager = eventManager;
	}
	
    //private GlobalPreferencesVo globalPreferencesVo;
    
    
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelValue() {
		return levelValue;
	}
	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	/*public GlobalPreferencesVo getGlobalPreferencesVo() {
		return globalPreferencesVo;
	}
	public void setGlobalPreferencesVo(GlobalPreferencesVo globalPreferencesVo) {
		this.globalPreferencesVo = globalPreferencesVo;
	}*/
	
}
