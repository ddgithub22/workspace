package com.meganexus.admin.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SSIS_FileLogs database table.
 * 
 */
@Entity
@Table(name="SSIS_FileLogs",schema="daily")
public class SSIS_FileLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="LogId",nullable=false)
	private int logId;

	@Column(name="CreatedDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="FileName",nullable=false,length=500)
	private String fileName;

	@Column(name="FilePath",nullable=false,length=1000)
	private String filePath;

	@Column(name="Information",length=2000)
	private String information;

	@Column(name="Level",nullable=false,length=20)
	private String level;

	@Column(name="SheetName",nullable=false,length=500)
	private String sheetName;

	public SSIS_FileLog() {
	}

	public int getLogId() {
		return this.logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInformation() {
		return this.information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

}