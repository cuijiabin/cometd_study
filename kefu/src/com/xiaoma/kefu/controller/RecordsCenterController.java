package com.xiaoma.kefu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.service.ChatRecordFieldService;
import com.xiaoma.kefu.service.StyleService;
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
	@Autowired
	private StyleService styleService;//风格
	
	@RequestMapping(value = "queryTalk.action", method = RequestMethod.GET)
	public void queryTalkRecord(
				Integer deptId,//部门id	
				String beginDate,//开始日期
				String endDate,//结束日期
				Integer userId,//客服id
//				String cardName,//工号
				Integer isTalk,//客户是否说话
				String	customerId,//客户编码
				String ipInfo,//ip地址
				String consultPage,//咨询页面
				String talkContent,//聊天内容		需先处理
				String keywords,//关键字(访问来源)
				Integer totalNum,//总条数
				String numCondition,// 大于 等于 小于 等条件
//				Integer styleId,//站点来源(风格id,页面用下拉列表)
				String styleName,//风格名称(站点来源)		需先处理
				Integer openType,//开始方式
				Integer closeType,//结束方式
				Integer isWait,//是否进入等待队列
				String waitListName,//考试项目		需先处理
				Integer deviceType//设备类型
			){
		
		StringBuilder condition = new StringBuilder();
		if(deptId!=null && deptId>0){
			condition.append(" and t1.deptId = " + deptId );
		}
		if(beginDate!=null){
			condition.append(" and t1.beginDate >= '" + beginDate + " 00:00:00'");
		}
		if(endDate!=null){
			condition.append(" and t1.endDate <= '" + endDate + " 23:59:59'");
		}
		if(userId!=null && userId>0){
			condition.append(" and t1.userId = " + userId );
		}
		if(isTalk!=null && isTalk==1){
			condition.append(" and t1.isTalk = " + 1 );
		}
		if(StringUtils.isNotBlank(customerId)){
			condition.append(" and t1.customerId like '%" + customerId + "%'" );
		}
		if(StringUtils.isNotBlank(ipInfo)){
			condition.append(" and ( t1.ipInfo like '%" + ipInfo + "%' " );
			condition.append(" 		or t1.ip like '%" + ipInfo + "%' )" );
		}
		if(StringUtils.isNotBlank(consultPage)){
			condition.append(" and t1.consultPage like '%" + consultPage + "%'" );
		}
		if(StringUtils.isNotBlank(keywords)){
			condition.append(" and t1.keywords like '%" + keywords + "%'" );
		}
		if(StringUtils.isNotBlank(numCondition) && totalNum != null){
			condition.append(" and t1.totalNum " + numCondition + totalNum  );
		}
		if(openType!=null){
			condition.append(" and t1.openType = " + openType );
		}
		if(closeType!=null){
			condition.append(" and t1.closeType = " + closeType );
		}
		if(isWait!=null){
			condition.append(" and t1.isWait = " + isWait );
		}
		if(deviceType!=null){
			condition.append(" and t1.deviceType = " + deviceType );
		}
		if(StringUtils.isNotBlank(waitListName)){
			String waitIds = getWaitIds(waitListName);
			condition.append(" and t1.waitListId in (" + waitIds + " ) " );
		}
		if(StringUtils.isNotBlank(styleName)){
			String styleIds = getStyleIds(styleName);
			condition.append(" and t1.styleId in (" + styleIds + " ) " );
		}
		
		//需要使用缓存
		//获取需要展示的字段
		Map<String,String> recordFieldMap = getRecordFieldMap(1);
		
		//针对查询条件包含聊天记录的处理
		if(StringUtils.isNotBlank(talkContent)){
			condition.append(" and exists ( select 1 from dialogue_detail dd where t1.id = dd.dialogueId ");
			condition.append(" 	and dd.content like '%" + talkContent + "%' ");
			if(beginDate!=null){
				condition.append(" and dd.createDate >= '" + beginDate + " 00:00:00'");
			}
			if(endDate!=null){
				condition.append(" and dd.createDate <= '" + endDate + " 23:59:59'");
			}
			condition.append(" ) ");
		}
		
		String sql = " SELECT t1.id dialogueId,IFNULL(t2.customerName,t1.customerId) customerId "
				+ " , t1.ipInfo, t1.consultPage, t1.keywords  "
				+ " , t1.styleId, t1.openType, t1.closeType, t1.isWait, t1.waitListId "
				+ " , t1.deviceType, t1.cardName, t1.maxSpace, t1.scoreType "
				+ " , t1.landingPage, t1.keywords, t1.durationTime, t1.btnCode "
				+ " , t1.waitTime, t1.firstTime, t1.beginDate, t1.totalNum  "
				+ " , t2.firstLandingPage, t2.firstVisitSource, t2.updateDate "
				+ " FROM dialogue t1 "
				+ " INNER JOIN customer t2 ON t1.customerId = t2.id " 
				+ " WHERE t1.isDel = 0 "
				+ condition.toString();
		logger.debug(sql);
		System.out.println(sql);
		DataSet ds = DataBase.Query(sql);
		List<List<String>> contentList = new ArrayList<List<String>>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			List<String> tempList = new ArrayList<String>(recordFieldMap.size()+1);
			tempList.add(ds.getRow(i).getString("dialogueId"));//对话id,固定第一位
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
	 * 
	* @Description: 聊天记录回收站查询
	* @param deptId
	* @param beginDate
	* @param endDate
	* @param userId
	* @param isTalk
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	@RequestMapping(value = "queryTalkDel.action", method = RequestMethod.GET)
	public void queryTalkRecordDel(
				Integer deptId,//部门id	
				String beginDate,//开始日期
				String endDate,//结束日期
				Integer userId,//客服id
//				String cardName,//工号
				Integer isTalk//客户是否说话
			){
		
		StringBuilder condition = new StringBuilder();
		if(deptId!=null && deptId>0){
			condition.append(" and t1.deptId = " + deptId );
		}
		if(beginDate!=null){
			condition.append(" and t1.beginDate >= '" + beginDate + " 00:00:00'");
		}
		if(endDate!=null){
			condition.append(" and t1.endDate <= '" + endDate + " 23:59:59'");
		}
		if(userId!=null && userId>0){
			condition.append(" and t1.userId = " + userId );
		}
		if(isTalk!=null && isTalk==1){
			condition.append(" and t1.isTalk = " + 1 );
		}
		
		//需要使用缓存
		//获取需要展示的字段
		Map<String,String> recordFieldMap = getRecordFieldMap(1);
		
		String sql = " SELECT t1.id dialogueId,IFNULL(t2.customerName,t1.customerId) customerId "
				+ " , t1.ipInfo, t1.consultPage, t1.keywords  "
				+ " , t1.styleId, t1.openType, t1.closeType, t1.isWait, t1.waitListId "
				+ " , t1.deviceType, t1.cardName, t1.maxSpace, t1.scoreType "
				+ " , t1.landingPage, t1.keywords, t1.durationTime, t1.btnCode "
				+ " , t1.waitTime, t1.firstTime, t1.beginDate, t1.totalNum  "
				+ " , t2.firstLandingPage, t2.firstVisitSource, t2.updateDate "
				+ " FROM dialogue t1 "
				+ " INNER JOIN customer t2 ON t1.customerId = t2.id " 
				+ " WHERE t1.isDel = 1 "
				+ condition.toString();
		logger.debug(sql);
		System.out.println(sql);
		DataSet ds = DataBase.Query(sql);
		List<List<String>> contentList = new ArrayList<List<String>>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			List<String> tempList = new ArrayList<String>(recordFieldMap.size()+1);
			tempList.add(ds.getRow(i).getString("dialogueId"));//对话id,固定第一位
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
	 * 
	* @Description: 根据用户,获取显示字段
	* @param userId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	private Map<String, String> getRecordFieldMap(Integer userId) {
		Map<String,String> recordFieldMap = null;
		//进行权限判断,如果有权限配置字段,则去查询配置结果,否则取默认
		if(userId!=1){
			recordFieldMap = chatRecordFieldService.findDisplayMapByUserId(userId);
		}
		if(recordFieldMap==null || recordFieldMap.size()==0){//如果没权限或者没设置,则用默认
			recordFieldMap = chatRecordFieldService.findDefaultMap();
		}
		return recordFieldMap;
	}

	/**
	 * 
	* @Description: 根据风格名称,模糊查询风格id
	* @param styleName
	* @return	1,2,3
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	private String getStyleIds(String styleName) {
		StringBuilder sbd = new StringBuilder();
		if(StringUtils.isNotBlank(styleName)){
			List<WaitList> list = styleService.findByNameLike(styleName);
			for(int i=0;i<list.size();i++){
				if(i>0){
					sbd.append(",");
				}
				sbd.append(list.get(i).getId());
			}
		}
		return sbd.toString();
	}

	/**
	 * 
	* @Description: 根据等待菜单名称 模糊查询等待菜单id
	* @param waitListName
	* @return	1,2,3
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	private String getWaitIds(String waitListName) {
		StringBuilder sbd = new StringBuilder();
		if(StringUtils.isNotBlank(waitListName)){
			List<WaitList> list = waitListService.findByNameLike(waitListName);
			for(int i=0;i<list.size();i++){
				if(i>0){
					sbd.append(",");
				}
				sbd.append(list.get(i).getId());
			}
		}
		return sbd.toString();
	}


	/**
	* @Description: 根据需要展示字段map,封装个list显示使用
	* @param recordFieldMap
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月3日
	 */
	private List<String> getDisplayTitle(Map<String, String> recordFieldMap) {
		List<String> list = new ArrayList<String>(recordFieldMap.size()+1);
		list.add("dialogueId");//对话id,固定第一位
		for(Map.Entry<String, String> entry:recordFieldMap.entrySet()){
			list.add(entry.getValue());
		}   
		return list;
	}
}
