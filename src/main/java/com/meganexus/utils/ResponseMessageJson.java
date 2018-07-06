package com.meganexus.utils;

import java.util.List;

import org.json.simple.JSONObject;

public class ResponseMessageJson {
	private String status;
	private String msg;
	//private String code;
	private JSONObject data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/*public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}*/
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
}
