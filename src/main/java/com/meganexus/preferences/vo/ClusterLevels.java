package com.meganexus.preferences.vo;

import java.util.List;

public class ClusterLevels {
	
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
	
	private List<LDULevels> ldusList;

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

	public String getEventProvider() {
		return eventProvider;
	}

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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
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

	public List<LDULevels> getLdusList() {
		return ldusList;
	}

	public void setLdusList(List<LDULevels> ldusList) {
		this.ldusList = ldusList;
	}

}
