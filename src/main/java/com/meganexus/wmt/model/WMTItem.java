package com.meganexus.wmt.model;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the WMTItems database table.
 * 
 */
@Entity
@Table(name="WMTItems")
/*@NamedQuery(name="WMTItem.findAll", query="SELECT w FROM WMTItem w")*/
public class WMTItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue 
	@Column(name="ID",nullable=false)
	private int id;

	@Column(name="Adj", precision=10, scale=4)
	private BigDecimal adj;

	@Column(name="Band0")
	private int band_0;

	@Column(name="Band1")
	private int band_1;

	@Column(name="Band2")
	private int band_2;

	@Column(name="Band3")
	private int band_3;

	@Column(name="Band4")
	private int band_4;

	@Column(name="Capacity", precision=10, scale=4)
	private BigDecimal capacity;

	@Column(name="Cases")
	private int cases;

	@Column(name="CM", precision=10, scale=4)
	private BigDecimal cm;

	@Column(name="Community")
	private int community;

	@Column(name="Custody")
	private int custody;

	@Column(name="Dormant")
	private int dormant;

	@Column(name="EventCluster",length=500)
	private String event_Cluster;

	@Column(name="EventLDU",length=500)
	private String event_LDU;

	@Column(name="EventManager",length=500)
	private String event_Manager;

	@Column(name="EventProvider",length=500)
	private String event_Provider;

	@Column(name="EventTeam",length=500)
	private String event_Team;

	@Column(name="FTE", precision=10, scale=4)
	private BigDecimal fte;

	@Column(name="Grade", length=10)
	private String grade;

	@Column(name="LastUploadDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUploadDate;

	@Column(name="SCM", precision=10, scale=4)
	private BigDecimal scm;

	@Column(name="Team",length=2504)
	private String team;

	@Column(name="Work", precision=10, scale=4)
	private BigDecimal work;

	public WMTItem() {
	}

	public BigDecimal getAdj() {
		return this.adj;
	}

	public void setAdj(BigDecimal adj) {
		this.adj = adj;
	}

	public int getBand_0() {
		return this.band_0;
	}

	public void setBand_0(int band_0) {
		this.band_0 = band_0;
	}

	public int getBand_1() {
		return this.band_1;
	}

	public void setBand_1(int band_1) {
		this.band_1 = band_1;
	}

	public int getBand_2() {
		return this.band_2;
	}

	public void setBand_2(int band_2) {
		this.band_2 = band_2;
	}

	public int getBand_3() {
		return this.band_3;
	}

	public void setBand_3(int band_3) {
		this.band_3 = band_3;
	}

	public int getBand_4() {
		return this.band_4;
	}

	public void setBand_4(int band_4) {
		this.band_4 = band_4;
	}

	public BigDecimal getCapacity() {
		return this.capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	public int getCases() {
		return this.cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public BigDecimal getCm() {
		return this.cm;
	}

	public void setCm(BigDecimal cm) {
		this.cm = cm;
	}

	public int getCommunity() {
		return this.community;
	}

	public void setCommunity(int community) {
		this.community = community;
	}

	public int getCustody() {
		return this.custody;
	}

	public void setCustody(int custody) {
		this.custody = custody;
	}

	public int getDormant() {
		return this.dormant;
	}

	public void setDormant(int dormant) {
		this.dormant = dormant;
	}

	public String getEvent_Cluster() {
		return this.event_Cluster;
	}

	public void setEvent_Cluster(String event_Cluster) {
		this.event_Cluster = event_Cluster;
	}

	public String getEvent_LDU() {
		return this.event_LDU;
	}

	public void setEvent_LDU(String event_LDU) {
		this.event_LDU = event_LDU;
	}

	public String getEvent_Manager() {
		return this.event_Manager;
	}

	public void setEvent_Manager(String event_Manager) {
		this.event_Manager = event_Manager;
	}

	public String getEvent_Provider() {
		return this.event_Provider;
	}

	public void setEvent_Provider(String event_Provider) {
		this.event_Provider = event_Provider;
	}

	public String getEvent_Team() {
		return this.event_Team;
	}

	public void setEvent_Team(String event_Team) {
		this.event_Team = event_Team;
	}

	public BigDecimal getFte() {
		return this.fte;
	}

	public void setFte(BigDecimal fte) {
		this.fte = fte;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getLastUploadDate() {
		return this.lastUploadDate;
	}

	public void setLastUploadDate(Date lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

	public BigDecimal getScm() {
		return this.scm;
	}

	public void setScm(BigDecimal scm) {
		this.scm = scm;
	}

	public Object getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public BigDecimal getWork() {
		return this.work;
	}

	public void setWork(BigDecimal work) {
		this.work = work;
	}

}