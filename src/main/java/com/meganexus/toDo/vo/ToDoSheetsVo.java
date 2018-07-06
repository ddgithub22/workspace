package com.meganexus.toDo.vo;

public class ToDoSheetsVo {
	
	private String fileInfoId;
    private String fileName;
	private String sheetName;
	private String sheetDescription;
	private String recordCount;
	private String displayOrder;
	private String groupId;
	private String groupName;
	private String groupDisplayOrder;
	
	public String getGroupDisplayOrder() {
		return groupDisplayOrder;
	}
	public void setGroupDisplayOrder(String groupDisplayOrder) {
		this.groupDisplayOrder = groupDisplayOrder;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getFileInfoId() {
		return fileInfoId;
	}
	public void setFileInfoId(String fileInfoId) {
		this.fileInfoId = fileInfoId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	
}
