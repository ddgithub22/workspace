package com.meganexus.toDo.vo;

public class TimeSensitiveCountsVo {
	
     private String toDoId;
	 private String groupName;
     private String sheetName;
     private String sheetDescription;
     /*private String provider;*/
     private String allCount;
     private String oneDayCount;
     private String oneWeekCount;
     private String overDueCount;
 	
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
 	

}
