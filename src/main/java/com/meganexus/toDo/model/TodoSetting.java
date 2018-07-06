package com.meganexus.toDo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Arunkumar.K
 * The persistent class for the TodoSettings database table.
 * 
 */
@Entity
@Table(name="TodoSettings",schema="daily")
/*@NamedStoredProcedureQuery(
		name = "GetTodobyId", 
		procedureName = "daily.GetTodobyId", 
		parameters = { 
			@StoredProcedureParameter(mode = ParameterMode.IN,  type = Integer.class, name = "fileInfoId"), 
			@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "todoResult"), 
		}
	)*/
public class TodoSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Id")
	private int id;

	@Column(name="Description",length=500)
	private String description;
	
	@OneToOne()
	@JoinColumn(name="FileInfoId", referencedColumnName="Id", foreignKey=@ForeignKey(name = "FileInfoId"))
	private FileInfoMaster fileInfoMaster;

	public FileInfoMaster getFileInfoMaster() {
		return fileInfoMaster;
	}

	public void setFileInfoMaster(FileInfoMaster fileInfoMaster) {
		this.fileInfoMaster = fileInfoMaster;
	}

	@Column(name="Name")
	private String name;
	
	@Column(name="DisplayOrder")
	private int displayOrder;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="GroupId", referencedColumnName="Id", foreignKey=@ForeignKey(name = "GroupId"))
	private TodoGroups todoGroups;

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public TodoGroups getTodoGroups() {
		return todoGroups;
	}

	public void setTodoGroups(TodoGroups todoGroups) {
		this.todoGroups = todoGroups;
	}

	public TodoSetting() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	/*@Override
	public String toString() {
		return "TodoSetting [id=" + id + ", description=" + description 
				+ ", fileInfoMaster=" + fileInfoMaster + ", name=" + name + ", displayOrder=" + displayOrder
				+ ", todoGroups=" + todoGroups + "]";
	}*/
}