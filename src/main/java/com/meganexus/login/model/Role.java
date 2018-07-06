package com.meganexus.login.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the Roles database table.
 * 
 */
@Entity
@Table(name="Roles",schema="dbo")
/*@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")*/
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="RoleId",nullable=false)
	private int roleId;

	@Column(name="CreatedBy",nullable=false,length=60)
	private String createdBy;

	@Column(name="CreatedDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="Description",length=1000)
	private String description;

	@Column(name="RoleName",nullable=false,length=200)
	private String roleName;

	public Role() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}