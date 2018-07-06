package com.meganexus.toDo.vo;

public class InterventionsCountVo {
	
	 private String toDoId;
	 private String groupName;
     private String sheetName;
     private String sheetDescription;
     /*private String provider;*/
     private String allCount;
     private String lowRiskCount;
     private String highRiskCount;
     private String failingCount;
     
     public String getToDoId() {
 		return toDoId;
 	}
 	public void setToDoId(String toDoId) {
 		this.toDoId = toDoId;
 	}
 	public String getGroupName() {
 		return groupName;
 	}
 	public void setGroupName(String groupName) {
 		this.groupName = groupName;
 	}
 	public String getSheetName() {
 		return sheetName;
 	}
 	public void setSheetName(String sheetName) {
 		this.sheetName = sheetName;
 	}
 	public String getSheetDescription() {
 		return sheetDescription;
 	}
 	public void setSheetDescription(String sheetDescription) {
 		this.sheetDescription = sheetDescription;
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
