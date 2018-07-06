package com.meganexus.preferences.model;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the PreferenceMaster database table.
 * 
 */
@Entity
@Table(name="PreferenceMaster")
public class PreferenceMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id", unique=true, nullable=false)
	private int id;

	@Column(name="CreatedDate", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="Name", nullable=false,length=500)
	private String name;

	@Column(name="PreferenceJson", nullable=false)
	private String preferenceJson;

	public PreferenceMaster() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreferenceJson() {
		return this.preferenceJson;
	}

	public void setPreferenceJson(String preferenceJson) {
		this.preferenceJson = preferenceJson;
	}

}