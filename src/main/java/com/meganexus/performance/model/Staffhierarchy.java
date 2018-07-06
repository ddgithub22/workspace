package com.meganexus.performance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * The persistent class for the STAFFHIERARCHY database table.
 * 
 */
@Entity
@Table(name="STAFFHIERARCHY",schema="daily")
public class Staffhierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="Id",nullable=false)
	private int id;

	@Column(name="Cluster",length=500)
	private String cluster;

	@Column(name="Provider",length=500)
	private String provider;

	@Column(name="SDU",length=500)
	private String sdu;
	
	@Column(name="Team",length=500)
	private String team;

	@Column(name="StaffName",length=500)
	private String staff_Name;

	public Staffhierarchy() {
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

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSdu() {
		return this.sdu;
	}

	public void setSdu(String sdu) {
		this.sdu = sdu;
	}

	public String getStaff_Name() {
		return this.staff_Name;
	}

	public void setStaff_Name(String staff_Name) {
		this.staff_Name = staff_Name;
	}

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}