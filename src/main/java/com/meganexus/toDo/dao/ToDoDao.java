package com.meganexus.toDo.dao;
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
 */

public interface ToDoDao {
	/*public List<List<List<ToDoSheetsVo>>> getTotalSheetsInfo();*/
	public JSONObject getTotalSheetsInfo(int userId,ArrayList<UserPreference> userList);
	public List<GroupCountsVo> getGroupWiseCounts(int userId,ArrayList<UserPreference> userList);
	public JSONObject getTotalCounts(int userId,ArrayList<UserPreference> userList);
	public JSONObject getGroupFilterCounts(int userId,ArrayList<UserPreference> userList);
	public List getToDoSheetDetails(int fileInfoId,String countType);
	public List<LinkedHashMap<String, String>> sortGridRecord(int fileInfoId,String countType,String sortColumnname,String sortOrder);
	public String generateExcelSheet(int fileInfoId,String excelDownloadPath,String FILES_POOL_SIZE,String countType,ArrayList<UserPreference> userList,int userId);
	public boolean deleteFile(File file);
	public JSONObject getToDoPreferences();
	public ArrayList getTodoSheetPreferences(int fileInfoId,String countType,String sortColumnname,String sortOrder,ArrayList<UserPreference> userList,int userId);
    public String saveCheckList(int todoId,int userId,int fileId,String checkStatus);
}   
