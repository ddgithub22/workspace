package com.meganexus.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author arunkumar.k
 * @version 1.0
 */
@Component
public class CommonUtils {
	
	private static final Logger LOGGER = Logger.getLogger(CommonUtils.class);
	public static final String DATE_FORMAT = "dd MMMM, yyyy";
	public static final String DATE_FORMAT1 = "dd-MM-yyyy";
	public static final String DATE_FORMAT2 = "yyyyMMdd";
	public static final String DATE_FORMAT3 = "yyyy-MM-dd";
	public static final String DATE_FORMAT4 = "dd-MMMM-yyyy";
	public static final String TIER_EXPIRY_FORMAT = "MMM dd, yyyy hh:mm:ss aa";
	public static final DateFormat DATE_FORMAT6 = new SimpleDateFormat("dd-MMM-yyyy");
	public static final DateFormat DATE_FORMAT7 = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String getFormattedDate(Date inputDate){		
		try{
			Calendar currentCalDate = Calendar.getInstance();
			currentCalDate.setTime(inputDate);
			String dayNumberSuffix = getDayNumberSuffix(currentCalDate.get(Calendar.DAY_OF_MONTH));
		    DateFormat dateFormat = new SimpleDateFormat(" d'<sup>" + dayNumberSuffix + "</sup>' MMMM yyyy");
		    return dateFormat.format(currentCalDate.getTime());		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date stringToDateConverter(DateFormat format, String dateString){
		Date date=null;
		try {
			date = format.parse(dateString);
	 
		} catch (ParseException e) {
			LOGGER.error("Error in stringToDateConverter ==============>>>>>>>"+e);
		}
		return date;
	}
	
	
	private static String getDayNumberSuffix(int day) {
	    if (day >= 11 && day <= 13) {
	        return "th";
	    }
	    switch (day % 10) {
	    case 1:
	        return "st";
	    case 2:
	        return "nd";
	    case 3:
	        return "rd";
	    default:
	        return "th";
	    }
	}
	
	public enum ResponseStatusEnum{
		SUCCESS("success"), ERROR("error"), WARNING("warning");
		
		private final String label;
		
		ResponseStatusEnum(String label) {
	        this.label = label;
	    }
		
		public String getLabel(){
			return label;
		}
	}
	
	public static String formatDate(Date date){
		try {
			if(date != null){
			    return getFormattedDate(date);		    
			}else{
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String formatDate(Date date, String dateFormat){
		try {
			if(date != null){
				if(StringUtils.isBlank(dateFormat)){
					dateFormat = DATE_FORMAT3;
				}
			    return DateFormatUtils.format(date, dateFormat) ;	    
			}else{
				return "";
			}
		} catch (Exception e) {
			LOGGER.error("*************ERROR WHILE FORMATING DATE****************");
			return "";
		}
	}
	
	public static Date convertDate(String date){
		try {
			if(date != null){
				return new SimpleDateFormat(DATE_FORMAT3, Locale.ENGLISH).parse(date);
			}else{
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("*************ERROR WHILE PARSING DATE****************");
			return null;
		}
	}
	
	public static Date convertDate1(String date){
		try {
			if(date != null){
				Date newDate = new SimpleDateFormat(DATE_FORMAT1, Locale.ENGLISH).parse(date);
				String newDateStr = formatDate(newDate, DATE_FORMAT2);
				return new SimpleDateFormat(DATE_FORMAT2, Locale.ENGLISH).parse(newDateStr);
			}else{
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("*************ERROR WHILE PARSING DATE****************");
			return null;
		}
	}
	
	public static Date convertDate(String date, String inputFormat, String outputFormat){
		try {
			if(date != null){
				inputFormat = inputFormat != null ? inputFormat : DATE_FORMAT1;
				outputFormat = outputFormat != null ? outputFormat : DATE_FORMAT2;
				
				Date newDate = new SimpleDateFormat(inputFormat, Locale.ENGLISH).parse(date);
				String newDateStr = formatDate(newDate, outputFormat);
				return new SimpleDateFormat(outputFormat, Locale.ENGLISH).parse(newDateStr);
			}else{
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("*************ERROR WHILE PARSING DATE****************");
			return null;
		}
	}
	
	public ResponseMessage populateErrorResponse(String msg){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(ResponseStatusEnum.ERROR.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(null);
		return responseMessage;
	}
	
	public ResponseMessage populateErrorResponse(String msg, boolean isSessionExpired){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(ResponseStatusEnum.ERROR.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(isSessionExpired ? Messages.SESSION_TMEOUT : null);
		responseMessage.setData(null);
		return responseMessage;
	}
	
	public ResponseMessage populateWarningResponse(String msg){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(ResponseStatusEnum.WARNING.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(null);
		return responseMessage;
	}
	 
	@SuppressWarnings("rawtypes")
	public ResponseMessage populateResponse(String msg,List data){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(ResponseStatusEnum.SUCCESS.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(data);
		return responseMessage;
	}
	
	
	public ResponseMessageJson populateResponseJson(String msg,JSONObject data){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageJson responseMessage = new ResponseMessageJson();
		responseMessage.setStatus(ResponseStatusEnum.SUCCESS.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(data);
		return responseMessage;
	}
	
	public ResponseMessageJson populateErrorResponseJson(String msg, boolean isSessionExpired){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageJson responseMessage = new ResponseMessageJson();
		responseMessage.setStatus(ResponseStatusEnum.ERROR.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(isSessionExpired ? Messages.SESSION_TMEOUT : null);
		responseMessage.setData(null);
		return responseMessage;
	}
	
	
	public ResponseMessageString populateResponse(String msg,String data){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageString responseMessage = new ResponseMessageString();
		responseMessage.setStatus(ResponseStatusEnum.SUCCESS.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(data);
		return responseMessage;
	}
	
	public ResponseMessageString populateResponseError(String msg,boolean isSessionExpired){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageString responseMessage = new ResponseMessageString();
		responseMessage.setStatus(ResponseStatusEnum.ERROR.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(null);
		return responseMessage;
	}
	
	public ResponseMessageString populateResponseLoginError(String msg,String data){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageString responseMessage = new ResponseMessageString();
		responseMessage.setStatus(ResponseStatusEnum.ERROR.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(data);
		return responseMessage;
	}
	
	
	
	public ResponseMessageString populateResponseErrorString(String msg,boolean isSessionExpired){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageString responseMessage = new ResponseMessageString();
		responseMessage.setStatus(ResponseStatusEnum.ERROR.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(null);
		return responseMessage;
	}
	
	public ResponseMessageSingle populateResponse(String msg,Object data){
		msg = null != msg ? msg.replaceAll("\\<.*?\\>", "") : msg;
		ResponseMessageSingle responseMessage = new ResponseMessageSingle();
		responseMessage.setStatus(ResponseStatusEnum.SUCCESS.label);
		responseMessage.setMsg(msg);
		//responseMessage.setCode(code);
		responseMessage.setData(data);
		return responseMessage;
	}

	public static Date stringToDateConverter(DateFormat dateFormat7, Date travelDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
