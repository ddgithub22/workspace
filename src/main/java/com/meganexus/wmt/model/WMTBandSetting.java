package com.meganexus.wmt.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * @author Arunkumar.K
 * The persistent class for the WMTBandSettings database table.
 * 
 */
@Entity
@Table(name="WMTBandSettings",schema="daily")
public class WMTBandSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Id")
	private int id;

	@Column(name="BandCapacity")
	private BigDecimal bandCapacity;

	@Column(name="BandName")
	private String bandName;

	@Column(name="[Custody%]")
	private BigDecimal custody_;

	@Column(name="CustodyWaiting")
	private BigDecimal custodyWaiting;

	@Column(name="OwnerShipWaiting")
	private BigDecimal ownerShipWaiting;

	public WMTBandSetting() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBandCapacity() {
		return this.bandCapacity;
	}

	public void setBandCapacity(BigDecimal bandCapacity) {
		this.bandCapacity = bandCapacity;
	}

	public String getBandName() {
		return this.bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public BigDecimal getCustody_() {
		return this.custody_;
	}

	public void setCustody_(BigDecimal custody_) {
		this.custody_ = custody_;
	}

	public BigDecimal getCustodyWaiting() {
		return this.custodyWaiting;
	}

	public void setCustodyWaiting(BigDecimal custodyWaiting) {
		this.custodyWaiting = custodyWaiting;
	}

	public BigDecimal getOwnerShipWaiting() {
		return this.ownerShipWaiting;
	}

	public void setOwnerShipWaiting(BigDecimal ownerShipWaiting) {
		this.ownerShipWaiting = ownerShipWaiting;
	}

}