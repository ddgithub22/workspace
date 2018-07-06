package com.meganexus.wmt.vo;
import javax.persistence.Column;

public class WMTSelectPreference {
	
	private String provider;
	private String cluster;
	private String ldu;
	private String manager;
	private String team;
	private String selectedLevel;
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
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
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getSelectedLevel() {
		return selectedLevel;
	}
	public void setSelectedLevel(String selectedLevel) {
		this.selectedLevel = selectedLevel;
	}
	

}
