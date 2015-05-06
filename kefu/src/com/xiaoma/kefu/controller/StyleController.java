package com.xiaoma.kefu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.BusiGroup;
import com.xiaoma.kefu.model.InviteIcon;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.service.BusiGroupService;
import com.xiaoma.kefu.service.InviteIconService;
import com.xiaoma.kefu.service.ServiceIconService;
import com.xiaoma.kefu.service.StyleService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.FileUtil;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.SysConst;
import com.xiaoma.kefu.util.SysConst.DeviceType;
import com.xiaoma.kefu.util.SysConst.StylePicName;

/**
 * *********************************
* @Description: 风格	controller
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:20:30
**********************************
 */
@Controller
@RequestMapping(value = "style")
public class StyleController {

	private Logger logger = Logger.getLogger(StyleController.class);

	@Autowired
	private StyleService styleService;
	@Autowired
	private BusiGroupService busiGroupService;//业务分组
	@Autowired
	private ServiceIconService serviceIconService;//客服图标
	@Autowired
	private InviteIconService inviteIconService;//邀请框
	
	
	/**
	 * 查询所有风格
	* @Description: TODO
	* @param conditions
	* @param pageBean
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Style> pageBean) {
		try {
			styleService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "style/style";
			else
				return "style/styleList";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "/error500";
		}
	}
	
	
	/**
	 * 编辑
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "edit.action", method = RequestMethod.GET)
	public String edit(Model model,Integer styleId) {
		try {
			Style style;
			if(styleId!=null && styleId>0){
				style = styleService.get(styleId);
			}else{
				style = new Style();
			}
			model.addAttribute("style", style);
			return "/style/editStyle";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 校验风格名称是否存在
	* @Description: TODO
	* @param model
	* @param customer
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "validate.action", method = RequestMethod.POST)
	public String validate(Model model, Style style) {
		try {
			Integer isSuccess = styleService.validateName(style);
			if (isSuccess == 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "名称已存在!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 保存风格
	* @Description: TODO
	* @param model
	* @param style
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("style") Style style) {
		try {
			Integer id = style.getId();
			int isSuccess = 0;
			if(id==null){
				isSuccess = styleService.initStyle(style);//初始化风格
			}else{
				Style toUpdate = styleService.get(id);
				toUpdate.setName(style.getName());
				isSuccess = styleService.update(toUpdate);
			}
			if (isSuccess > 0) {
				model.addAttribute("result", Ajax.JSONResult(0, "操作成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("result", Ajax.JSONResult(1, "操作失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 跳转到	界面样式设置 界面
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月14日
	 */
	@RequestMapping(value = "editCommon.action", method = RequestMethod.GET)
	public String editCommon(Model model,Integer styleId) {
		try {
			Style style = styleService.get(styleId);
			
			//客服图片路径 写死. 暂时只有这一个图片,所有风格公用
			String picUrl1 = "/" + SysConst.TEMPLATE_PATH + "/" + SysConst.PIC_TEMPLATE_CLIENT;//类别
			
			InviteIcon icon = inviteIconService.getByStyleId(styleId, DeviceType.PC);
			String picUrl3 = inviteIconService.getPvwDiv(icon,null,true,DeviceType.PC);
			icon = inviteIconService.getByStyleId(styleId, DeviceType.移动);
			String picUrl5 = inviteIconService.getPvwDiv(icon,null,true,DeviceType.移动);
			
			model.addAttribute("picUrl1", picUrl1);
			model.addAttribute("picUrl2", styleService.getMinPicPath(styleId, StylePicName.客服图标PC在线));
			model.addAttribute("picUrl3", picUrl3);
			model.addAttribute("picUrl4", styleService.getMinPicPath(styleId, StylePicName.客服图标移动在线));
			model.addAttribute("picUrl5", picUrl5);
			
			
			model.addAttribute("style", style);
			return "/style/editCommon";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	
	/**
	 * 获取代码
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月27日
	 */
	@RequestMapping(value = "getCode.action", method = RequestMethod.GET)
	public String getCode(Model model,Integer styleId) {
		try {
			Style style = styleService.get(styleId);
			
			String code = "<script type='text/javascript' src='"+ 
					FileUtil.getSiteUrl()
					+"/" + SysConst.JS_DIV_PATH
					+"/" + SysConst.JS_NAME + styleId + ".js"
					+"'></script>";
			String code2 = " [url= " + FileUtil.getSiteUrl() + "/dialogue/customerChat.action?styleId="+styleId
					+ "][img]" + "http://oc1.xiaoma.com/kfimg.php?arg=53kf&style=1" +" [/img]超酷签名，点击这里和我直接沟通[/url]";
			
			model.addAttribute("style", style);
			model.addAttribute("code", code);
			model.addAttribute("code2", code2);
			return "/style/styleCode";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 校验风格下 是否存在 业务分组
	* @Description: TODO
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@RequestMapping(value = "checkHasGroup.action", method = RequestMethod.GET)
	public String checkHasGroup(Model model, Integer styleId) {
		try {
			List<BusiGroup> groupList = busiGroupService.findByStyleId(styleId);
			if (groupList!=null && groupList.size()>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "请先创建业务分组!"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
	
	
	/**
	 * 获取 客服图标的 div	
	* @param req
	* @param res
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	@RequestMapping(value = "getIconDiv.action", method = RequestMethod.GET)
	public void getIconDiv(HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/plain");
		String callbackFunName =req.getParameter("callbackIcon");//得到js函数名称
		String styleId = req.getParameter("styleId");
		String isPC = req.getParameter("isPC");
	    try {
	    	List<Integer> userList = CacheMan.getOnlineUserIdsByStyleId(Integer.valueOf(styleId));
	    	boolean isOnline = true; //在线
	    	if(userList!=null && userList.size()==0){
	    		isOnline = false;
	    	}
	    	String strDiv = null;
	    	if(isPC!=null && isPC.equals("false")){
	    		if(isOnline){
	    			strDiv = (String) CacheMan.getObject(CacheName.DIVICONYDON,styleId);
	    		}else{
	    			strDiv = (String) CacheMan.getObject(CacheName.DIVICONYDOFF,styleId);
	    		}
	    	}else{
	    		if(isOnline){
	    			strDiv = (String) CacheMan.getObject(CacheName.DIVICONPCON,styleId);
	    		}else{
	    			strDiv = (String) CacheMan.getObject(CacheName.DIVICONPCOFF,styleId);
	    		}
	    	}
	    	logger.info("图标div="+strDiv);
	        res.getWriter().write(callbackFunName + "([ { name:\""+strDiv+"\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	    	logger.error("获取图标div出错,风格id="+styleId,e);
	    }
	}
	
	/**
	 * 获取 邀请框的 div 
	* @param model
	* @param req
	* @param res
	* @Author: wangxingfei
	* @Date: 2015年4月24日
	 */
	@RequestMapping(value = "getDialogueDiv.action", method = RequestMethod.GET)
	public void getDialogueDiv(Model model,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/plain");
	    String callbackFunName =req.getParameter("callbackDialogue");//得到js函数名称
	    String styleId = req.getParameter("styleId");
	    String isPC = req.getParameter("isPC");
	    try {
	    	String strDiv = null;
	    	if(isPC!=null && isPC.equals("false")){
	    		strDiv = (String) CacheMan.getObject(CacheName.DIVINVITEYD,styleId);
	    	}else{
	    		strDiv = (String) CacheMan.getObject(CacheName.DIVINVITEPC,styleId);
	    	}
	    	logger.info("邀请框div="+strDiv);
	        res.getWriter().write(callbackFunName + "([ { name:\""+strDiv+"\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	    	logger.error(e.getMessage(),e);
	    }
	}
	
	/**
	 * 重新生成 风格的 js文件,用于 模板变更后,重新生成js文件
	* @param model
	* @param styleId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年5月6日
	 */
	@RequestMapping(value = "afreshStyleJs.action", method = RequestMethod.GET)
	public String afreshStyleJs(Model model,Integer styleId){
		try {
			//创建js文件
			FileUtil.createStyleJsFile(styleId);
			model.addAttribute("result", Ajax.JSONResult(0, "成功!"));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
}
