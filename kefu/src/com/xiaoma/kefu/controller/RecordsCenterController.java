package com.xiaoma.kefu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.service.ChatRecordFieldService;
import com.xiaoma.kefu.service.WaitListService;
import com.xiaoma.kefu.util.database.DataBase;
import com.xiaoma.kefu.util.database.DataSet;

/**
 * *********************************
* @Description: 记录中心
* @author: wangxingfei
* @createdAt: 2015年4月3日上午9:57:51
**********************************
 */
@Controller
@RequestMapping(value = "recordsCenter")
public class RecordsCenterController {

	private Logger logger = Logger.getLogger(RecordsCenterController.class);
	
	@Autowired
	private ChatRecordFieldService chatRecordFieldService;//结果展示字段
	@Autowired
	private WaitListService waitListService;//等待菜单列表
	
	@RequestMapping(value = "queryTalk.action", method = RequestMethod.GET)
	public void queryTalkRecord(
				Integer deptId,//部门id
				String beginDate,//开始日期
				String endDate,//结束日期
				String cardName,//工号
				Integer isTalk,//客户是否说话
				String	customerId,//客户编码
				String ipInfo,//ip地址
				String consultPage,//咨询页面
				String talkContent,//聊天内容		需先处理
				String keywords,//关键字(访问来源)
				Integer totalNum,//总条数
				String numCondition,// 大于 等于 小于 等条件
				Integer styleId,//站点来源(风格id,页面用下拉列表)
//				String styleName,//风格名称(站点来源)		需先处理
				Integer openType,//开始方式
				Integer closeType,//结束方式
				Integer isWait,//是否进入等待队列
				String waitListName,//考试项目		需先处理
				Integer deviceType//设备类型
			){
		//需要使用缓存
		//获取需要展示的字段
		Map<String,String> recordFieldMap = chatRecordFieldService.getDisplayMap();
		
		//先对需要做处理的条件进行处理
		List<WaitList> waitList = waitListService.findByNameLike(waitListName);
		
		
		
		String sql = " SELECT IFNULL(t2.customerName,t1.customerId) customerId, t1.ipInfo, t1.consultPage, t1.keywords  "
				+ " , t1.styleId, t1.openType, t1.closeType, t1.isWait, t1.waitListId "
				+ " , t1.deviceType, t1.cardName, t1.maxSpace, t1.scoreType "
				+ " , t1.landingPage, t1.keywords, t1.durationTime, t1.btnCode "
				+ " , t1.waitTime, t1.firstTime, t1.beginDate, t1.totalNum  "
				+ " , t2.firstLandingPage, t2.firstVisitSource, t2.updateDate "
				+ " FROM dialogue t1 "
				+ " INNER JOIN customer t2 ON t1.customerId = t2.id " 
				+ " WHERE t1.isDel = 0 ";
		logger.debug(sql);
		DataSet ds = DataBase.Query(sql);
		List<List<String>> contentList = new ArrayList<List<String>>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			List<String> tempList = new ArrayList<String>(recordFieldMap.size());
			for(Map.Entry<String, String> entry:recordFieldMap.entrySet()){
				tempList.add(ds.getRow(i).getString(entry.getKey()));
			}
			contentList.add(tempList);
		}
		List<String> title = getDisplayTitle(recordFieldMap);
		//结果输出
		System.out.println(title);
		for(int i=0;i<contentList.size();i++){
			System.out.println(contentList.get(i));
		}
		
	}
	
	
	/**
	* @Description: 根据需要展示字段map,封装个list显示使用
	* @param recordFieldMap
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	private List<String> getDisplayTitle(Map<String, String> recordFieldMap) {
		List<String> list = new ArrayList<String>(recordFieldMap.size());
		for(Map.Entry<String, String> entry:recordFieldMap.entrySet()){
			list.add(entry.getValue());
		}   
		return list;
	}

	
}
