package com.meganexus.performance.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * @author Arunkumar.K
 * The persistent class for the PerformanceSettings database table.
 * 
 */
@Entity
@Table(name="PerformanceSettings",schema="daily")
public class PerformanceSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Id")
	private int id;
    
	@Column(name="SL",length=10)
	private String sl;

	@Column(name="FileName",length=500)
	private String fileName;
	
	@Column(name="Description",length=500)
	private String description;

	@Column(name="Target")
	private BigDecimal target;

	@Column(name="Trigger")
	private BigDecimal trigger;

	@Column(name="Weight")
	private BigDecimal weight;

	public PerformanceSetting() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSl() {
		return this.sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public BigDecimal getTarget() {
		return this.target;
	}

	public void setTarget(BigDecimal target) {
		this.target = target;
	}

	public BigDecimal getTrigger() {
		return this.trigger;
	}

	public void setTrigger(BigDecimal trigger) {
		this.trigger = trigger;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}