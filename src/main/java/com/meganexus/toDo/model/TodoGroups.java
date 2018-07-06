package com.meganexus.toDo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*import javax.persistence.Temporal;
import javax.persistence.TemporalType;*/
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.type.TrueFalseType;


/**
 * @author arunkumar.k
 * The persistent class for the TodoGroups database table.
 */
@Entity
@Table(name="TodoGroups",schema="daily")
public class TodoGroups implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name="Id") 
	private int id;
	
	@Column(name="GroupName",nullable=false,length=1000)
	private String groupName;
	
	@Column(name="Description",length=1000)
	private String description;
	
	@Column(name="DisplayOrder")
	private int displayOrder;
	
	@Column(name="CreatedDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	/*@OneToMany(mappedBy="todoGroup")
	private Set<TodoSetting> todoSettings;

	public Set<TodoSetting> getTodoSettings() {
		return todoSettings;
	}

	public void setTodoSettings(Set<TodoSetting> todoSettings) {
		this.todoSettings = todoSettings;
	}*/

	public TodoGroups(){
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "TodoGroups [id=" + id + ", groupName=" + groupName + ", description=" + description + ", displayOrder="
				+ displayOrder + ", createdDate=" + createdDate + "]";
	}
}
