package com.meganexus.toDo.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author Arunkumar.K
 * The persistent class for the FTE database table.
 * 
 */
@Entity
@Table(name="FTE",schema="daily")
public class Fte implements Serializable {
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

	@Column(name="FTE")
	private String fte;

	@Column(name="LDU")
	private String ldu;

	@Column(name="Manager")
	private String manager;

	@Column(name="Provider")
	private String provider;

	@Column(name="Team")
	private String team;

	public Fte() {
	}

	public String getCluster() {
		return this.cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getFte() {
		return this.fte;
	}

	public void setFte(String fte) {
		this.fte = fte;
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