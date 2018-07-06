package com.meganexus.toDo.vo;

public class GroupCountsVo {
	
	private String groupName;
	private String groupDisplayOrder;
	
	public String getGroupDisplayOrder() {
		return groupDisplayOrder;
	}
	public void setGroupDisplayOrder(String groupDisplayOrder) {
		this.groupDisplayOrder = groupDisplayOrder;
	}
	
	//private String provider;
	private String allCount;
    private String oneDayCount;
    private String oneWeekCount;
    private String overDueCount;
    private String lowRiskCount;
    private String highRiskCount;
    private String failingCount;
    
    public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	/*public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}*/
	
	public String getAllCount() {
		return allCount;
	}
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	public String getOneDayCount() {
		return oneDayCount;
	}
	public void setOneDayCount(String oneDayCount) {
		this.oneDayCount = oneDayCount;
	}
	public String getOneWeekCount() {
		return oneWeekCount;
	}
	public void setOneWeekCount(String oneWeekCount) {
		this.oneWeekCount = oneWeekCount;
	}
	public String getOverDueCount() {
		return overDueCount;
	}
	public void setOverDueCount(String overDueCount) {
		this.overDueCount = overDueCount;
	}
	public String getLowRiskCount() {
		return lowRiskCount;
	}
	public void setLowRiskCount(String lowRiskCount) {
		this.lowRiskCount = lowRiskCount;
	}
	public String getHighRiskCount() {
		return highRiskCount;
	}
	public void setHighRiskCount(String highRiskCount) {
		this.highRiskCount = highRiskCount;
	}
	public String getFailingCount() {
		return failingCount;
	}
	public void setFailingCount(String failingCount) {
		this.failingCount = failingCount;
	}
	
}
