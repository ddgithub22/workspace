package com.meganexus.performance.model;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the PerformanceNotes database table.
 * 
 */
@Entity
@Table(name="PerformanceNotes")
public class PerformanceNote implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@EmbeddedId
	private PerformanceNotePK id;*/
	@Id
	@Column(name="PerformanceId", unique=true, nullable=false)
	private int performanceId;
	@Id
	@Column(name="UniqueId", unique=true, nullable=false, length=500)
	private String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getPerformanceId() {
		return performanceId;
	}

	public void setPerformanceId(int performanceId) {
		this.performanceId = performanceId;
	}

	@Column(name="CreatedBy",length=60)
	private int createdBy;

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="ModifiedBy",length=60)
	private int modifiedBy;

	@Column(name="ModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name="Notes")
	private String notes;

	public PerformanceNote() {
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}