package com.meganexus.admin.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SSISLog database table.
 * 
 */
@Entity
@Table(name="SSISLog",schema="daily")
public class SSISLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="EventID",nullable=false)
	private int eventID;
	
	@Column(name="ContainerDuration")
	private int containerDuration;

	@Column(name="EndTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@Column(name="EventType",length=20)
	private String eventType;

	@Column(name="Host",length=50)
	private String host;

	@Column(name="Message",length=250)
	private String message;

	@Column(name="PackageDuration")
	private int packageDuration;

	@Column(name="PackageName",length=50)
	private String packageName;

	@Column(name="ProccessedRows")
	private int proccessedRows;

	@Column(name="StartTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Column(name="TaskName",length=50)
	private String taskName;

	public SSISLog() {
	}

	public int getContainerDuration() {
		return this.containerDuration;
	}

	public void setContainerDuration(int containerDuration) {
		this.containerDuration = containerDuration;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getEventID() {
		return this.eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPackageDuration() {
		return this.packageDuration;
	}

	public void setPackageDuration(int packageDuration) {
		this.packageDuration = packageDuration;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getProccessedRows() {
		return this.proccessedRows;
	}

	public void setProccessedRows(int proccessedRows) {
		this.proccessedRows = proccessedRows;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}