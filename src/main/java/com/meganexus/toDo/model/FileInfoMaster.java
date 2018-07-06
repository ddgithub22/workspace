package com.meganexus.toDo.model;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;

/**
 * @author arunkumar.k
 * The persistent class for the FileInfoMaster database table.
 * 
 */
@Entity
@Table(name="FileInfoMaster",schema="daily")
public class FileInfoMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Id")
	private int id;

	@Column(name="FileName",length=500)
	private String fileName;

	@Column(name="SheetName",length=500)
	private String sheetName;

	@Column(name="TableName",length=1000)
	private String tableName;
	
	@Column(name="RecordCount")
	private int recordCount;

	@Column(name="FirstUploadDate",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date firstUploadDate;
	
	@Column(name="LastUploadDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUploadDate;

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public Date getFirstUploadDate() {
		return firstUploadDate;
	}

	public void setFirstUploadDate(Date firstUploadDate) {
		this.firstUploadDate = firstUploadDate;
	}

	public Date getLastUploadDate() {
		return lastUploadDate;
	}

	public void setLastUploadDate(Date lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

	public FileInfoMaster() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Object getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}