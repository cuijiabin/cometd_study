package com.xiaoma.kefu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.service.ChatRecordFieldService;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.service.MessageRecordsService;
import com.xiaoma.kefu.service.StyleService;
import com.xiaoma.kefu.service.UserService;
import com.xiaoma.kefu.service.WaitListService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.CheckCodeUtil;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.CompareEnum;
import com.xiaoma.kefu.util.SysConst.RoleNameId;
import com.xiaoma.kefu.util.TimeHelper;
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
	@Autowired
	private DialogueService dialogueService;//对话信息
	@Autowired
	private CustomerService customerService;//客户信息
	@Autowired
	private UserService userService;//用户
	@Autowired
	private MessageRecordsService messageRecordsService;//留言记录
	
	/**
	 * 聊天记录查询
	* @Description: TODO
	* @param deptId	部门id	
	* @param beginDate	开始日期
	* @param endDate	结束日期
	* @param userId		客服id
	* @param isTalk		客户是否说话
	* @param customerId	客户编码
	* @param ipInfo		ip地址
	* @param consultPage	咨询页面
	* @param talkContent	聊天内容
	* @param keywords		关键字(访问来源)
	* @param totalNum		总条数
	* @param numCondition	大于 等于 小于 等条件
	* @param styleName		风格名称(站点来源)
	* @param openType		开始方式
	* @param closeType		结束方式
	* @param isWait			是否进入等待队列
	* @param waitListName	考试项目
	* @param deviceType		设备类型
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryTalkRecord(HttpSession session,Model model,
				Integer deptId,String beginDate,String endDate,
				Integer userId,Integer isTalk,String customerId,
				String ipInfo,String consultPage,String talkContent,
				String keywords,Integer totalNum,String numCondition,
				String styleName,Integer openType,Integer closeType,
				Integer isWait,String waitListName,Integer deviceType,
				Integer typeId,
				@ModelAttribute("pageBean") PageBean<Map<String,String>> pageBean
			){
		//封装条件
		StringBuilder condition = getTalkCondition(deptId, beginDate, endDate, userId,
				isTalk, customerId, ipInfo, consultPage,talkContent, keywords, totalNum,
				numCondition, styleName, openType, closeType, isWait,
				waitListName, deviceType);
		
		//判断用户角色
		User user = (User) session.getAttribute(CacheName.USER);
		if (user == null) return "login";
			
		//主管看所有部门,所有员工
		//员工看自己部门,自己的记录
		List<User> userList = new ArrayList<User>();
		if(user.getRoleId().equals(RoleNameId.员工.getCode())){
			condition.append(" and t1.userId = " + userId );//员工只能查自己
			userList.add(user);
		}else{
//			userList.addAll(userService.findAll()); //等提供
			userList.add(user);//test
		}
		
		//需要使用缓存
		//获取需要展示的字段
		Map<String,String> recordFieldMap = getRecordFieldMap(user);
		List<String> title = getDisplayTitle(recordFieldMap);//title
		getTalkContentList(pageBean,condition,recordFieldMap,0);//content
		
		//是否能查看聊天明细
		int showDetail = 0;
		if(CheckCodeUtil.isCheckFunc(user.getId(), "f_check_dialog")){
			showDetail = 1;
		}
		
		model.addAttribute("userList", userList);
		model.addAttribute("title", title);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("showDetail", showDetail);
		model.addAttribute("beginDate", initDate(beginDate));
		model.addAttribute("endDate", initDate(endDate));
		model.addAttribute("recordFieldMap", recordFieldMap);
		if(typeId==null || typeId !=1){
			return "/record/talk/talk";
		}else{
			return "/record/talk/talkList";
		}
	}
	
	/**
	 * 逻辑删除对话信息
	* @Description: TODO
	* @param model
	* @param ids	1,2,3
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	@RequestMapping(value = "deleteTalk4Logic.action", method = RequestMethod.GET)
	public String deleteTalk4Logic(Model model,String ids){
		try {
			int num = dialogueService.delete4Logic(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("deleteTalk4Logic失败!"+ids);
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 还原对话信息
	* @Description: TODO
	* @param model
	* @param ids
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "restoreTalk.action", method = RequestMethod.GET)
	public String restoreTalk(Model model,String ids){
		try {
			int num = dialogueService.restore(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "还原成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "还原失败!"));
			}
		} catch (Exception e) {
			logger.error("restoreTalk失败!"+ids);
			model.addAttribute("result", Ajax.JSONResult(1, "还原失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 彻底删除 对话信息
	* @Description: TODO
	* @param model
	* @param ids
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "deleteTalkTrue.action", method = RequestMethod.GET)
	public String deleteTalkTrue(Model model,String ids){
		try {
			int num = dialogueService.delete(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("deleteTalkTrue失败!"+ids);
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}


	/**
	 * 聊天记录回收站查询
	* @Description: TODO
	* @param session
	* @param model
	* @param deptId		部门id
	* @param beginDate	开始日期
	* @param endDate	结束日期
	* @param userId		客服id
	* @param isTalk		客户是否说话
	* @param typeId		首次查询	1否   null 是
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	@RequestMapping(value = "queryTalkDel.action", method = RequestMethod.GET)
	public String queryTalkDel(HttpSession session,Model model,
				Integer deptId,String beginDate,String endDate,
				Integer userId,Integer isTalk,Integer typeId,
				@ModelAttribute("pageBean") PageBean<Map<String,String>> pageBean
			){
		
		StringBuilder condition = getTalkDelCondition(deptId, beginDate, endDate, userId,isTalk);
		//判断用户角色
		User user = (User) session.getAttribute(CacheName.USER);
		if (user == null) return "login";
		
		List<User> userList = new ArrayList<User>();
		if(user.getRoleId().equals(RoleNameId.员工.getCode())){
			condition.append(" and t1.userId = " + userId );//员工只能查自己
			userList.add(user);
		}else{
//			userList.addAll(userService.findAll()); //等提供
			userList.add(user);//test
		}
		
		Map<String,String> recordFieldMap = getRecordFieldMap(user);
		List<String> title = getDisplayTitle(recordFieldMap);//title
		getTalkContentList(pageBean,condition,recordFieldMap,1);//content
		
		//是否能查看聊天明细
		int showDetail = 0;
		if(CheckCodeUtil.isCheckFunc(user.getId(), "f_check_dialog")){
			showDetail = 1;
		}
		
		model.addAttribute("userList", userList);
		model.addAttribute("title", title);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("showDetail", showDetail);//是否能查看聊天明细
		model.addAttribute("recordFieldMap", recordFieldMap);
		model.addAttribute("beginDate", initDate(beginDate));
		model.addAttribute("endDate", initDate(endDate));
		model.addAttribute("recordFieldMap", recordFieldMap);
		
		if(typeId==null || typeId !=1){
			return "/record/talk/talkRecycle";
		}else{
			return "/record/talk/talkRecycleList";
		}
		
	}
	

	/**
	 * 查询聊天 详情	(聊天详情整个页面)
	 * (根据当前对话id,获取客户,然后获取客户所有聊天记录)
	* @Description: TODO
	* @param dialogueId	对话id
	* @param isShowTel	是否显示显示电话号码  1是 0否
	* @param typeId	查询类别
	* @param isDel	是否是回收站查询
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	@RequestMapping(value = "queryTalkList.action", method = RequestMethod.GET)
	public String queryTalkList(Model model,Long dialogueId,Integer isShowTel,Integer typeId,
			Integer isDel,
			@ModelAttribute("pageBean") PageBean<DialogueDetail> pageBean
		){
		//获取对话信息
		Dialogue dialogue = dialogueService.findById(dialogueId);
		//获取聊天记录列表
		List<Dialogue> timeList = findDialogueByCId(dialogue.getCustomerId(),isDel);
		//封装聊天记录
		packDialogue(dialogue);
		
		List<DialogueDetail> detailList = queryTalkContent(dialogueId,isShowTel,pageBean);
		pageBean.setObjList(detailList);
		
		model.addAttribute("dialogue", dialogue);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("timeList", timeList);
		model.addAttribute("isShowTel", isShowTel);
		return "/record/talk/talkDetail"; 
	}
	
	/**
	 * 聊天明细查询	(聊天详情右侧页面)
	* @Description: TODO
	* @param model
	* @param dialogueId	对话id
	* @param isShowTel	是否显示电话号码
	* @param typeId	查询类别(返回大页面还是小页面)
	* @param pageBean
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "queryTalkDetail.action", method = RequestMethod.GET)
	public String queryTalkDetail(Model model,Long dialogueId,Integer isShowTel,Integer typeId,
			@ModelAttribute("pageBean") PageBean<DialogueDetail> pageBean
		){
		if(dialogueId==null){
			return "/error500";
		}
		try {
			//获取对话信息
			Dialogue dialogue = dialogueService.findById(dialogueId);
			//封装对话信息
			packDialogue(dialogue);
			
			List<DialogueDetail> detailList = queryTalkContent(dialogueId,isShowTel,pageBean);
			pageBean.setObjList(detailList);
			
			model.addAttribute("dialogue", dialogue);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("isShowTel", isShowTel);
			
			if(typeId==null || typeId!=1){
				return "/record/talk/talkDetailCommon";
			}
			return "/record/talk/talkDetailCommonList"; 
		} catch (Exception e) {
			return "/error500";
		}
		
	}
	
	
	/**
	 * 留言记录 查询
	* @Description: TODO
	* @param beginDate	开始日期
	* @param endDate	结束日期
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	@RequestMapping(value = "findMessage.action", method = RequestMethod.GET)
	public String findMessage(HttpSession session,Model model,String beginDate,String endDate,
			Integer typeId,
			@ModelAttribute("pageBean") PageBean<Map<String,String>> pageBean){
		//判断用户角色
		User user = (User) session.getAttribute(CacheName.USER);
		if (user == null) return "login";
		
		StringBuilder condition = getMsgCondition(beginDate,endDate);
		
		List<Map<String,String>> list = getMsgList(pageBean,condition,0);//content
		pageBean.setObjList(list);
		
		//是否有权查看明细
		int showDetail = 0;
		if(CheckCodeUtil.isCheckFunc(user.getId(), "f_check_dialog")){
			showDetail = 1;
		}
		
		model.addAttribute("beginDate", initDate(beginDate));
		model.addAttribute("endDate", initDate(endDate));
		model.addAttribute("showDetail", showDetail);
		if(typeId==null){
			return "/record/message/message";
		}else{
			return "/record/message/messageList";
		}
	}
	
	/**
	 * 留言记录 查询(回收站)
	* @Description: TODO
	* @param model
	* @param beginDate
	* @param endDate
	* @param typeId
	* @param pageBean
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "findMessageDel.action", method = RequestMethod.GET)
	public String findMessageDel(HttpSession session,Model model,String beginDate,String endDate,
			Integer typeId,
			@ModelAttribute("pageBean") PageBean<Map<String,String>> pageBean){
		//判断用户角色
		User user = (User) session.getAttribute(CacheName.USER);
		if (user == null) return "login";
		StringBuilder condition = getMsgCondition(beginDate,endDate);
		
		List<Map<String,String>> list = getMsgList(pageBean,condition,1);//content
		pageBean.setObjList(list);
		
		//是否有权查看明细
		int showDetail = 0;
		if(CheckCodeUtil.isCheckFunc(user.getId(), "f_check_dialog")){
			showDetail = 1;
		}
		
		model.addAttribute("beginDate", initDate(beginDate));
		model.addAttribute("endDate", initDate(endDate));
		model.addAttribute("showDetail", showDetail);//是否有权查看明细
		if(typeId==null){
			return "/record/message/msgRecycle";
		}else{
			return "/record/message/msgRecycleList";
		}
	}
	
	
	/**
	 * 逻辑删除留言信息
	* @Description: TODO
	* @param model
	* @param ids	1,2,3
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	@RequestMapping(value = "deleteMsg4Logic.action", method = RequestMethod.GET)
	public String deleteMsg4Logic(Model model,String ids){
		try {
			int num = messageRecordsService.delete4Logic(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("deleteMsg4Logic失败!"+ids);
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 还原留言信息
	* @Description: TODO
	* @param model
	* @param ids
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "restoreMsg.action", method = RequestMethod.GET)
	public String restoreMsg(Model model,String ids){
		try {
			int num = messageRecordsService.restore(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "还原成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "还原失败!"));
			}
		} catch (Exception e) {
			logger.error("restoreMsg失败!"+ids);
			model.addAttribute("result", Ajax.JSONResult(1, "还原失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 彻底删除 留言信息
	* @Description: TODO
	* @param model
	* @param ids
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "deleteMsgTrue.action", method = RequestMethod.GET)
	public String deleteMsgTrue(Model model,String ids){
		try {
			int num = messageRecordsService.delete(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
			logger.error("deleteMsgTrue失败!"+ids);
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	

	/**
	 * 导出对话信息
	* @Description: TODO
	* @param date	yyyy-MM-dd	(时间)
	* @param response
	* @throws IOException
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	@RequestMapping(value = "/expTalk", method = RequestMethod.GET)
	public void expTalk(String date,HttpServletResponse response) throws IOException {
		String basePath = FileUtil.getExpTalkRootPath(date) ;
		String path = basePath + "/" + date + ".xlsx";
		response.setContentType("application/octet-stream");// 二进制流
		response.setHeader("Content-Disposition", "attachment;filename=\"" + date + ".xlsx\"");
		OutputStream out = response.getOutputStream();
		try {
			if(new File(path).exists()){
				FileInputStream in = new FileInputStream(path);
				byte[] buf = new byte[1024];
				int b;
				while ((b = in.read(buf)) != -1) {
					out.write(buf, 0, b);
				}
				out.flush();
				out.close();
				in.close();
			}else{
				Log.error("file does not exist!" + date);
				throw new Exception("file does not exist!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	/**
	 * 查询对话内容
	* @Description: TODO
	* @param dialogueId
	* @param isShowTel
	* @param pageBean
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	public List<DialogueDetail> queryTalkContent(Long dialogueId,Integer isShowTel,
			PageBean<DialogueDetail> pageBean){
		String sql = " SELECT dd.id, dd.dialogueId, dd.dialogueType,dd.customerId, "
				+ " dd.userId, dd.cardName, dd.content,dd.createDate "
//				+ " DATE_FORMAT(dd.createDate,'%Y-%m-%d %H:%i:%S') createDate  "
				+ " FROM dialogue_detail AS dd "
				+ " WHERE dd.dialogueId = " + dialogueId
				+ " order by dd.createDate " ;
		DataSet ds = DataBase.Query(sql,pageBean);
		List<DialogueDetail> list = new ArrayList<DialogueDetail>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			DialogueDetail dd = new DialogueDetail();
			dd.setId(ds.getRow(i).getLong("id"));
			dd.setDialogueType(ds.getRow(i).getInt("dialogueType"));
			dd.setCustomerId(ds.getRow(i).getLong("customerId"));
			dd.setUserId(ds.getRow(i).getInt("userId"));
			dd.setCardName(ds.getRow(i).getString("cardName"));
			dd.setCreateDate(ds.getRow(i).getDate("createDate"));
			
			String content = ds.getRow(i).getString("content");
//			if(dd.getDialogueType().equals(1)){//客户
//				content = ds.getRow(i).getString("customerId") + " " 
//						+ ds.getRow(i).getString("createDate")
//						+ "<br> &ensp;&ensp;&ensp;&ensp;"
//						+ ds.getRow(i).getString("content");
//			}else{//客服+机器人
//				content = ds.getRow(i).getString("cardName") + " " 
//						+ ds.getRow(i).getString("createDate")
//						+ "<br> &ensp;&ensp;&ensp;&ensp;"
//						+ ds.getRow(i).getString("content");
//			}
			if(isShowTel==null || isShowTel!=1){
				content = replaceTel(content);
			}
			dd.setContent(content);
			list.add(dd);
			
		}
		return list;
	}
	
	/**
	 * 封装回收站查询条件
	* @Description: TODO
	* @param deptId
	* @param beginDate
	* @param endDate
	* @param userId
	* @param isTalk
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	private StringBuilder getTalkDelCondition(Integer deptId, String beginDate,
			String endDate, Integer userId, Integer isTalk) {
		StringBuilder condition = new StringBuilder();
		if(deptId!=null && deptId>0){
			condition.append(" and t1.deptId = " + deptId );
		}
		if(StringUtils.isBlank(beginDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginDate = sdf.format(new Date());
		}
		condition.append(" and t1.beginDate >= '" + beginDate + " 00:00:00'");
		if(StringUtils.isBlank(endDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endDate = sdf.format(new Date());
		}
		condition.append(" and t1.endDate <= '" + endDate + " 23:59:59'");
		if(userId!=null && userId>0){
			condition.append(" and t1.userId = " + userId );
		}
		if(isTalk!=null && isTalk==1){
			condition.append(" and t1.isTalk = " + 1 );
		}
		return condition;
	}
	
	/**
	 * 封装查询条件
	* @Description: TODO
	* @param deptId
	* @param beginDate
	* @param endDate
	* @param userId
	* @param isTalk
	* @param customerId
	* @param ipInfo
	* @param consultPage
	* @param talkContent
	* @param keywords
	* @param totalNum
	* @param numCondition
	* @param styleName
	* @param openType
	* @param closeType
	* @param isWait
	* @param waitListName
	* @param deviceType
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	private StringBuilder getTalkCondition(Integer deptId, String beginDate,
			String endDate, Integer userId, Integer isTalk, String customerId,
			String ipInfo, String consultPage, String talkContent, String keywords,
			Integer totalNum, String numCondition, String styleName,
			Integer openType, Integer closeType, Integer isWait,
			String waitListName, Integer deviceType) {
		StringBuilder condition = new StringBuilder();
		if(deptId!=null && deptId>0){
			condition.append(" and t1.deptId = " + deptId );
		}
		if(StringUtils.isBlank(beginDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginDate = sdf.format(new Date());
		}
		condition.append(" and t1.beginDate >= '" + beginDate + " 00:00:00'");
		if(StringUtils.isBlank(endDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endDate = sdf.format(new Date());
		}
		condition.append(" and t1.endDate <= '" + endDate + " 23:59:59'");
		if(userId!=null && userId>0){//后面再根据权限判断一次
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
			condition.append(" and t1.totalNum " + CompareEnum.valueOf(numCondition).getCode() + " " +  totalNum  );
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
		return condition;
	}
	
	/**
	 * 获取对话信息 内容列表
	* @Description: TODO
	* @param pageBean	分页信息
	* @param condition	查询条件
	* @param recordFieldMap	需要展示字段
	* @param isDel	是否是回收站查询
	* @Author: wangxingfei
	* @Date: 2015年4月9日
	 */
	private void getTalkContentList(PageBean<Map<String,String>> pageBean,
			StringBuilder condition, Map<String, String> recordFieldMap, int isDel) {
		String sql = " SELECT t1.id dialogueId,IFNULL(t2.customerName,t1.customerId) customerName "
				+ " , CASE WHEN t2.customerName IS NULL THEN 0 ELSE 1 END hasName,t1.customerId "
				+ " , t1.ipInfo, t1.consultPage, t1.keywords  "
				+ " , case when t1.isWait=1 then '是' else '否' end isWait "
				+ " , IFNULL(t1.styleId,0) styleId, t1.openType, t1.closeType, IFNULL(t1.waitListId,0) waitListId "
				+ " , t1.deviceType, t1.cardName, t1.maxSpace, t1.scoreType "
				+ " , t1.landingPage, t1.durationTime, t1.btnCode "
				+ " , t1.waitTime, t1.firstTime, t1.beginDate, t1.totalNum  "
				+ " , t2.firstLandingPage, t2.firstVisitSource, t2.updateDate "
				+ " FROM dialogue t1 "
				+ " INNER JOIN customer t2 ON t1.customerId = t2.id " 
				+ " WHERE t1.isDel = " + isDel 
				+ condition.toString();
		DataSet ds = DataBase.Query(sql,pageBean);
//		List<List<String>> contentList = new ArrayList<List<String>>((int) ds.RowCount);
		List<Map<String,String>> contentList = new ArrayList<Map<String,String>>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			Map<String,String> hm = new HashMap<String,String>();
			hm.put("dialogueId", ds.getRow(i).getString("dialogueId"));//对话id
			hm.put("hasName", ds.getRow(i).getString("hasName"));//客户是否有名称
			hm.put("customerId", ds.getRow(i).getString("customerId"));//客户id
			
			for(Map.Entry<String, String> entry:recordFieldMap.entrySet()){
				String key = entry.getKey();
				if(key.equals(SysConst.CHAT_CONTENT)){//如果需要展示聊天记录
					hm.put(key, getChatContent(ds.getRow(i).getString("dialogueId")));
				}else if(key.equals("styleId")){//风格
					hm.put(key, "");
					String temp = ds.getRow(i).getString("styleId");
					if(!temp.equals("0")){
						Style style = styleService.get(Integer.valueOf(temp));
						if(style!=null){
							hm.put(key, style.getName());
						}
					}
				}else if(key.equals("openType")){//开始方式
					hm.put(key, DictMan.getDictItem("d_open_type", ds.getRow(i).getString("openType")).getItemName());
				}else if(key.equals("closeType")){//结束方式
					hm.put(key, DictMan.getDictItem("d_close_type", ds.getRow(i).getString("closeType")).getItemName());
				}else if(key.equals("waitListId")){//考试项目
					hm.put(key, "");
					String temp = ds.getRow(i).getString("waitListId");
					if(!temp.equals("0")){
						WaitList waitList = waitListService.get(Integer.valueOf(temp));
						if(waitList!=null){
							if(waitList.getpId()!=null){//如果有父级,则取父级
								waitList = waitListService.get(waitList.getpId());
							}
							hm.put(key, waitList.getName());
						}
					}
				}else if(key.equals("waitListId2")){//需求
					hm.put(key, "");
					String temp = ds.getRow(i).getString("waitListId");
					if(!temp.equals("0")){
						WaitList waitList = waitListService.get(Integer.valueOf(temp));
						if(waitList!=null && waitList.getpId()!=null){//如果没有父级,则只有考试,没有需求
							hm.put(key, waitList.getName());
						}
					}
				}else {
					hm.put(key, ds.getRow(i).getString(key));
				}
			}
			contentList.add(hm);
		}
		pageBean.setObjList(contentList);
	}
	
	/**
	 * 根据对话id,获取聊天记录(目前只取第一条聊天记录)
	* @Description: TODO
	* @param string
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	private String getChatContent(String dialogueId) {
		String sql = " select content,min(createDate) from dialogue_detail "
				+ " where dialogueId =  " + dialogueId ;
		return DataBase.getSingleResult(sql);
	}
	
	/**
	 * 替换聊天内容中的电话号码为*
	* @Description: TODO
	* @param content
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月8日
	 */
	private String replaceTel(String content) {
		String regex = "(?<!\\d)(?:(?:1[3578]\\d{9})|(?:861[3578]\\d{9}))(?!\\d)";
		Matcher matcher = Pattern.compile(regex).matcher(content); 
		while (matcher.find()) {
			content = content.replaceAll(matcher.group(), "*");
	    }  
		return content;
	}

	/**
	 * 根据客户id,获取对话列表
	* @Description: 根据
	* @param customerId
	* @param isDel
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	private List<Dialogue> findDialogueByCId(Long customerId,Integer isDel) {
		if(isDel==null || isDel!=1){
			isDel = 0;
		}
		String sql = " SELECT t1.id dialogueId,t1.beginDate "
				+ " FROM dialogue t1 "
				+ " WHERE t1.isDel = " + isDel
				+ " and t1.customerId = " + customerId ;
		DataSet ds = DataBase.Query(sql);
		List<Dialogue> list = new ArrayList<Dialogue>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			Dialogue dia = new Dialogue();
			dia.setId(ds.getRow(i).getLong("dialogueId"));
			dia.setBeginDate(ds.getRow(i).getDate("beginDate"));
			list.add(dia);
			
		}
		return list;
	}

	/**
	 * 
	* @Description: 根据用户,获取显示字段
	* @param userId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月7日
	 */
	private Map<String, String> getRecordFieldMap(User user) {
		Map<String,String> recordFieldMap = null;
		//进行权限判断,如果有权限配置字段,则去查询配置结果,否则取默认
		if(user!=null && user.getId()!=null ){
			recordFieldMap = chatRecordFieldService.findDisplayMapByUserId(user.getId());
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
		StringBuilder sbd = new StringBuilder("0");
		if(StringUtils.isNotBlank(styleName)){
			List<Style> list = styleService.findByNameLike(styleName);
			for(int i=0;i<list.size();i++){
				sbd.append(",");
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
		StringBuilder sbd = new StringBuilder("0");
		if(StringUtils.isNotBlank(waitListName)){
			List<WaitList> list = waitListService.findByNameLike(waitListName);
			for(int i=0;i<list.size();i++){
				sbd.append(",");
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
//		list.add("dialogueId");//对话id,固定第一位
		for(Map.Entry<String, String> entry:recordFieldMap.entrySet()){
			list.add(entry.getValue());
		}   
		return list;
	}
	
	/**
	 * 封装对话信息	客户名称,对话时长格式化,枚举项转换等
	* @Description: TODO
	* @param dialogue
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	private void packDialogue(Dialogue dialogue) {
		//获取用户名称
		Customer customer = customerService.getCustomerById(dialogue.getCustomerId());
		if(StringUtils.isNotBlank(customer.getCustomerName())){
			dialogue.setCustomerName(dialogue.getCustomerId()+"("+customer.getCustomerName()+")");
		}else{
			dialogue.setCustomerName(dialogue.getCustomerId().toString());
		}
		//格式化时间
		dialogue.setDurationTimeFM(TimeHelper.secToTime(dialogue.getDurationTime()));
		
		//开始,结束方式,评分
		
	}
	
	/**
	 * 封装留言查询条件
	* @Description: TODO
	* @param beginDate
	* @param endDate
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	private StringBuilder getMsgCondition(String beginDate, String endDate) {
		StringBuilder condition = new StringBuilder();
		if(StringUtils.isBlank(beginDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginDate = sdf.format(new Date());
		}
		condition.append(" and t1.createDate >= '" + beginDate + " 00:00:00'");
		if(StringUtils.isBlank(endDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endDate = sdf.format(new Date());
		}
		condition.append(" and t1.createDate <= '" + endDate + " 23:59:59'");
		return condition;
	}
	
	/**
	 * 查询留言记录的List
	* @Description: TODO
	* @param pageBean
	* @param condition
	* @param isDel
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	private List<Map<String,String>> getMsgList(PageBean<Map<String,String>> pageBean,
			StringBuilder condition, Integer isDel) {
		if(isDel==null || isDel!=1){
			isDel = 0;
		}
		String sql = " select t1.id,IFNULL(t2.customerName,t1.customerId) customerName "
				+ " , CASE WHEN t2.customerName IS NULL THEN 0 ELSE 1 END hasName,t1.customerId "
				+ " ,t1.ipInfo,t1.consultPage,t1.keywords,  "
				+ " DATE_FORMAT(t1.createDate,'%Y-%m-%d %H:%i') createDate  "
				+ " from message_records t1 "
				+ " inner join customer t2 on t1.customerId = t2.id "
				+ " where t1.isDel =  " + isDel
				+ condition.toString();
		DataSet ds = DataBase.Query(sql,pageBean);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>((int) ds.RowCount);
		for(int i=0;i<ds.RowCount;i++){
			Map<String,String> hm = new HashMap<String,String>();
			hm.put("id", ds.getRow(i).getString("id"));
			hm.put("customerName", ds.getRow(i).getString("customerName"));
			hm.put("hasName", ds.getRow(i).getString("hasName"));
			hm.put("customerId", ds.getRow(i).getString("customerId"));
			hm.put("ipInfo", ds.getRow(i).getString("ipInfo"));
			hm.put("consultPage", ds.getRow(i).getString("consultPage"));
			hm.put("keywords", ds.getRow(i).getString("keywords"));
			hm.put("createDate", ds.getRow(i).getString("createDate"));
			list.add(hm);
		}
		return list;
	}
	
	/**
	 * 初始化查询日期, 如果为空,则默认当天
	* @Description: TODO
	* @param beginDate
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	private String initDate(String beginDate) {
		String result = beginDate;
		if(StringUtils.isBlank(beginDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(new Date());
		}
		return result;
	}
}
