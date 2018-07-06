package com.meganexus.preferences.vo;

public class userPreferencesVo {
	
	private int    prefId;
	private String cluster;
	private String ldu;
	private String manager;
	private String provider;
	private String team;
	private String checkStatus;
	private String userId;
	
	public int getPrefId() {
		return prefId;
	}
	public void setPrefId(int prefId) {
		this.prefId = prefId;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getLdu() {
		return ldu;
	}
	public void setLdu(String ldu) {
		this.ldu = ldu;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
