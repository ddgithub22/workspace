package com.meganexus.login.model;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@Table(name="Users",schema="dbo")
/*@NamedQuery(name="User.findAll", query="SELECT u FROM User u")*/
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue 
	@Column(name="UserId",nullable=false)
	private int userId;

	@Column(name="CreatedBy",nullable=false)
	private String createdBy;

	@Column(name="CreatedDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="EmailId",length=200)
	private String emailId;

	@Column(name="FirstName",length=100)
	private String firstName;

	@Column(name="IsActive",nullable=false)
	private boolean isActive;

	@Column(name="LastName",length=100)
	private String lastName;

	@Column(name="Password",nullable=false)
	private String password;

	/*@Column(name="RoleId",nullable=false)
	private int roleId;*/
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="RoleId", referencedColumnName="RoleId", foreignKey=@ForeignKey(name = "RoleId"))
	private Role role;

	@Column(name="UserName",nullable=false,length=60)
	private String userName;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Object getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Object getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Object getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}*/

	public Object getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}