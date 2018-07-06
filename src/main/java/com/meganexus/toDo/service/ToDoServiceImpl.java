package com.meganexus.toDo.service;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.meganexus.preferences.model.UserPreference;
import com.meganexus.toDo.controller.ToDoController;
import com.meganexus.toDo.dao.ToDoDao;
import com.meganexus.toDo.vo.GroupCountsVo;
import com.meganexus.toDo.vo.ToDoGroupCountsVo;
import com.meganexus.toDo.vo.ToDoSheetsVo;
import com.meganexus.toDo.service.ToDoService;


/**
 * @author arunkumar.k
 * @version 1.0
 */

@Service("toDoService")
public class ToDoServiceImpl implements ToDoService {
	 
    private static final Logger LOGGER = Logger.getLogger(ToDoServiceImpl.class);
	
	@Resource(name="toDoDao")
	private ToDoDao toDoDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#getTotalSheets()
	 */
	@Override
	    public JSONObject getTotalSheets(int userId,ArrayList<UserPreference> userList) {
		return toDoDao.getTotalSheetsInfo(userId,userList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#getGroupWiseCount()
	 */
	@Override
	public List<GroupCountsVo> getGroupWiseCount(int userId,ArrayList<UserPreference> userList) {
		//LOGGER.info("ToDoServiceImpl getGroupWiseCount Executing");
		return toDoDao.getGroupWiseCounts(userId,userList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#getOverallTask()
	 */
	@Override
	public JSONObject getOverallTask(int userId,ArrayList<UserPreference> userList) {
		return toDoDao.getTotalCounts(userId,userList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#getTodoSheetRecords(int)
	 */
	@Override
	public List getTodoSheetRecords(int fileInfoId,String countType) {
		return toDoDao.getToDoSheetDetails(fileInfoId,countType);
	}
   
	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#getToDoExporttoExcel(int, java.lang.String, java.lang.String)
	 */
	@Override
	public String getToDoExporttoExcel(int fileInfoId,String excelDownloadPath,String FILES_POOL_SIZE,String countType,ArrayList<UserPreference> userList,int userId) {
		return toDoDao.generateExcelSheet(fileInfoId,excelDownloadPath,FILES_POOL_SIZE,countType,userList,userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#getGroupFilterCounts()
	 */
	@Override
	public JSONObject getGroupFilterCounts(int userId,ArrayList<UserPreference> userList) {
		return toDoDao.getGroupFilterCounts(userId,userList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.meganexus.toDo.service.ToDoService#sortGridRecord(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List sortGridRecord(int fileInfoId, String countType, String sortColumnname, String sortOrder) {
		return toDoDao.sortGridRecord(fileInfoId, countType, sortColumnname, sortOrder);
	}

	@Override
	public JSONObject getToDoPreferences() {
		return toDoDao.getToDoPreferences();
	}

	@Override
	public ArrayList getTodoSheetPreferences(int fileInfoId, String countType, String sortColumnname, String sortOrder,
			ArrayList<UserPreference> userList,int userId) {
		return toDoDao.getTodoSheetPreferences(fileInfoId, countType, sortColumnname, sortOrder, userList,userId);
	}

	@Override
	public String saveCheckList(int todoId, int userId, int fileId,String checkStatus) {
		return toDoDao.saveCheckList(todoId, userId, fileId,checkStatus);
	}
	
}
