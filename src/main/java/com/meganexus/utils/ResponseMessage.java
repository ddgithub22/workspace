package com.meganexus.utils;
import java.util.List;

/**
 * 
 * @author arunkumar.k
 * @version 1.0
 */

    public class ResponseMessage {
	private String status;
	private String msg;
	/*private String code;*/
	private List data;
	//private String encodedImage;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/*public String getEncodedImage() {
		return encodedImage;
	}
	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}*/
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the code
	 */
	/*public String getCode() {
		return code;
	}
	*//**
	 * @param code the code to set
	 *//*
	public void setCode(String code) {
		this.code = code;
	}*/
	/**
	 * @return the data
	 */
	public List getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List data) {
		this.data = data;
	}
}
