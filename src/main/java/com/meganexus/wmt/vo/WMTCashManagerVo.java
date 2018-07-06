package com.meganexus.wmt.vo;

public class WMTCashManagerVo {
	
	 private String Id;
	 private String cashManager;
	 private String type;
	 private String hours;
	 private String startDate;
	 private String endDate;
	 private String fteAdjust;

	public String getFteAdjust() {
		return fteAdjust;
	}
	public void setFteAdjust(String fteAdjust) {
		this.fteAdjust = fteAdjust;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getCashManager() {
		return cashManager;
	}
	public void setCashManager(String cashManager) {
		this.cashManager = cashManager;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	

	 
	 
	 
}
