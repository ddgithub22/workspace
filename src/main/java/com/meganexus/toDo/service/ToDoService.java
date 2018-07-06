package com.meganexus.toDo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.toDo.vo.GroupCountsVo;
import com.meganexus.toDo.vo.ToDoGroupCountsVo;
import com.meganexus.toDo.vo.ToDoSheetsVo;

/**
 * @author arunkumar.k
 * @version 1.0
 * 
 */

public interface ToDoService {
   public JSONObject getTotalSheets(int userId,ArrayList<UserPreference> userList);
   public List<GroupCountsVo> getGroupWiseCount(int userId,ArrayList<UserPreference> userList);
   public JSONObject getOverallTask(int userId,ArrayList<UserPreference> userList);
   public JSONObject getGroupFilterCounts(int userId,ArrayList<UserPreference> userList);
   public List getTodoSheetRecords(int fileInfoId,String countType);
   public List sortGridRecord(int fileInfoId,String countType,String sortColumnname,String sortOrder);
   public String  getToDoExporttoExcel(int fileInfoId,String excelDownloadPath,String FILES_POOL_SIZE,String countType,ArrayList<UserPreference> userList,int userId);
   public JSONObject getToDoPreferences(); 
   public ArrayList getTodoSheetPreferences(int fileInfoId,String countType,String sortColumnname,String sortOrder,ArrayList<UserPreference> userList,int userId);
   public String saveCheckList(int todoId,int userId,int fileId,String checkStatus);
}
