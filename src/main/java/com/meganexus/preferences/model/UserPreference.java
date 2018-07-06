package com.meganexus.preferences.model;

import java.io.Serializable;
import javax.persistence.*;

import com.meganexus.login.model.Role;
import com.meganexus.login.model.User;

/**
 * The persistent class for the UserPreferences database table.
 * 
 */
@Entity
@Table(name="UserPreferences",schema="dbo")
/*@NamedQuery(name="UserPreference.findAll", query="SELECT u FROM UserPreference u")*/
public class UserPreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="PrefId",nullable=false)
	private int prefId;

	@Column(name="Cluster")
	private String cluster;

	@Column(name="LDU",length=500)
	private String ldu;

	@Column(name="Manager",length=500)
	private String manager;

	@Column(name="Provider",length=500)
	private String provider;

	@Column(name="Team",length=500)
	private String team;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="UserId", referencedColumnName="UserId", foreignKey=@ForeignKey(name = "UserId"))
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserPreference() {
	}

	public int getPrefId() {
		return this.prefId;
	}

	public void setPrefId(int prefId) {
		this.prefId = prefId;
	}

	public String getCluster() {
		return this.cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getLdu() {
		return this.ldu;
	}

	public void setLdu(String ldu) {
		this.ldu = ldu;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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
}