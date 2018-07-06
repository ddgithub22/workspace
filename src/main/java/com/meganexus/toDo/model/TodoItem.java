package com.meganexus.toDo.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the TodoItems database table.
 * 
 */
@Entity
@Table(name="TodoItems")
@NamedQuery(name="TodoItem.findAll", query="SELECT t FROM TodoItem t")
public class TodoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;

	@Column(name="Cluster",nullable=false,length=500)
	private String cluster;

	@Column(name="FailingCount",nullable=false)
	private int failingCount;

	@Column(name="HighRiskCount",nullable=false)
	private int highRiskCount;

	@Column(name="LastUploadDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUploadDate;

	@Column(name="LDU",nullable=false,length=500)
	private String ldu;

	@Column(name="LowRiskCount",nullable=false)
	private int lowRiskCount;

	@Column(name="Manager",nullable=false,length=500)
	private String manager;

	@Column(name="OneDayCount",nullable=false)
	private int oneDayCount;

	@Column(name="OneWeekCount",nullable=false)
	private int oneWeekCount;

	@Column(name="OverDueCount",nullable=false)
	private int overDueCount;

	@Column(name="Provider",nullable=false,length=500)
	private String provider;

	@Column(name="Team",nullable=false,length=500)
	private String team;

	@Column(name="TodoCount")
	private int todoCount;

	@Column(name="TodoId",nullable=false)
	private int todoId;

	public TodoItem() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCluster() {
		return this.cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public int getFailingCount() {
		return this.failingCount;
	}

	public void setFailingCount(int failingCount) {
		this.failingCount = failingCount;
	}

	public int getHighRiskCount() {
		return this.highRiskCount;
	}

	public void setHighRiskCount(int highRiskCount) {
		this.highRiskCount = highRiskCount;
	}

	public Date getLastUploadDate() {
		return this.lastUploadDate;
	}

	public void setLastUploadDate(Timestamp lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

	public String getLdu() {
		return this.ldu;
	}

	public void setLdu(String ldu) {
		this.ldu = ldu;
	}

	public int getLowRiskCount() {
		return this.lowRiskCount;
	}

	public void setLowRiskCount(int lowRiskCount) {
		this.lowRiskCount = lowRiskCount;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public int getOneDayCount() {
		return this.oneDayCount;
	}

	public void setOneDayCount(int oneDayCount) {
		this.oneDayCount = oneDayCount;
	}

	public int getOneWeekCount() {
		return this.oneWeekCount;
	}

	public void setOneWeekCount(int oneWeekCount) {
		this.oneWeekCount = oneWeekCount;
	}

	public int getOverDueCount() {
		return this.overDueCount;
	}

	public void setOverDueCount(int overDueCount) {
		this.overDueCount = overDueCount;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public int getTodoCount() {
		return this.todoCount;
	}

	public void setTodoCount(int todoCount) {
		this.todoCount = todoCount;
	}

	public int getTodoId() {
		return this.todoId;
	}

	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}

}