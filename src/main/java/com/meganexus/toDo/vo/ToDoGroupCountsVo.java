package com.meganexus.toDo.vo;

public class ToDoGroupCountsVo {
	
	public String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String groupId;
	public String totalGroupCount;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTotalGroupCount() {
		return totalGroupCount;
	}
	public void setTotalGroupCount(String totalGroupCount) {
		this.totalGroupCount = totalGroupCount;
	}
	
}
