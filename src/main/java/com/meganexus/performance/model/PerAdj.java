package com.meganexus.performance.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author Arunkumar.K
 * The persistent class for the PerAdj database table.
 * 
 */
@Entity
@Table(name="perAdj",schema="daily")
public class PerAdj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name="Id")
	private int Id;

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	@Column(name="Cluster")
	private String cluster;
	
	@Column(name="LDU")
	private String ldu;

	@Column(name="Manager")
	private String manager;

	@Column(name="Provider")
	private String provider;
	
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getLdu() {
		return ldu;
	}
	public void setLdu(String ldu) {
		this.ldu = ldu;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Column(name="[End Date]")
	private String end_Date;

	@Column(name="Hours")
	private String hours;

	@Column(name="[Start Date]")
	private String start_Date;

	@Column(name="Team")
	private String team;

	@Column(name="Type")
	private String type;

	public PerAdj() {
	}

	public String getEnd_Date() {
		return this.end_Date;
	}

	public void setEnd_Date(String end_Date) {
		this.end_Date = end_Date;
	}

	public String getHours() {
		return this.hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getStart_Date() {
		return this.start_Date;
	}

	public void setStart_Date(String start_Date) {
		this.start_Date = start_Date;
	}

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}