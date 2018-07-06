package com.meganexus.login.model;
import java.io.Serializable;
import javax.persistence.*;

import com.meganexus.toDo.model.TodoSetting;

import java.util.Date;

/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@Table(name="UserTodos",schema="dbo")
/*@NamedQuery(name="User.findAll", query="SELECT u FROM User u")*/
public class UserTodos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue 
	@Column(name="FileId",nullable=false)
	private int fileId;

	@Column(name="AllCount",nullable=true)
    private int allCount;
    
	@Column(name="oneDayCount",nullable=true)
    private int oneDayCount;

	@Column(name="OneWeekCount",nullable=true)
    private int oneWeekCount;
    
	@Column(name="OverDueCount",nullable=true)
    private int overDueCount;
    
	@Column(name="LowRiskCount",nullable=true)
    private int lowRiskCount;
    
	@Column(name="HighRiskCount",nullable=true)
    private int highRiskCount;
    
	@Column(name="FailingCount",nullable=true)
    private int failingCount;
    
	@Column(name="CreatedDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TodoSetting getToDoSettings() {
		return toDoSettings;
	}

	public void setToDoSettings(TodoSetting toDoSettings) {
		this.toDoSettings = toDoSettings;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="UserId", referencedColumnName="UserId", foreignKey=@ForeignKey(name = "UserId"))
	private User user;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="TodoId", referencedColumnName="Id", foreignKey=@ForeignKey(name = "TodoId"))
	private TodoSetting toDoSettings;
	
	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public int getOneDayCount() {
		return oneDayCount;
	}

	public void setOneDayCount(int oneDayCount) {
		this.oneDayCount = oneDayCount;
	}

	public int getOneWeekCount() {
		return oneWeekCount;
	}

	public void setOneWeekCount(int oneWeekCount) {
		this.oneWeekCount = oneWeekCount;
	}

	public int getOverDueCount() {
		return overDueCount;
	}

	public void setOverDueCount(int overDueCount) {
		this.overDueCount = overDueCount;
	}

	public int getLowRiskCount() {
		return lowRiskCount;
	}

	public void setLowRiskCount(int lowRiskCount) {
		this.lowRiskCount = lowRiskCount;
	}

	public int getHighRiskCount() {
		return highRiskCount;
	}

	public void setHighRiskCount(int highRiskCount) {
		this.highRiskCount = highRiskCount;
	}

	public int getFailingCount() {
		return failingCount;
	}

	public void setFailingCount(int failingCount) {
		this.failingCount = failingCount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}