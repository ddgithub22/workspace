package com.meganexus.toDo.vo;

/**
 * @author Arunkumar.K
 *
 */
public class ToDoSheetDetailsVo {
	
	private String provider;
    private String cluster;
    private String ldu;
    private String team;
    private String caseManager;
    private String crnEventNo;
    private String createdDate;
    private String latestEnforcement;
    private String untagged;
    
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
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getCaseManager() {
		return caseManager;
	}
	public void setCaseManager(String caseManager) {
		this.caseManager = caseManager;
	}
	public String getCrnEventNo() {
		return crnEventNo;
	}
	public void setCrnEventNo(String crnEventNo) {
		this.crnEventNo = crnEventNo;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLatestEnforcement() {
		return latestEnforcement;
	}
	public void setLatestEnforcement(String latestEnforcement) {
		this.latestEnforcement = latestEnforcement;
	}
	public String getUntagged() {
		return untagged;
	}
	public void setUntagged(String untagged) {
		this.untagged = untagged;
	}
}
