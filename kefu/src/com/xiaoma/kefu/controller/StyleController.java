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
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.BusiGroup;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.service.BusiGroupService;
import com.xiaoma.kefu.service.ServiceIconService;
import com.xiaoma.kefu.service.StyleService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.SysConst;

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
			e.printStackTrace();
			logger.error("StyleController.queryAll ERROR");
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
			String picUrl1 = DictMan.getDictItem("d_sys_param", 2).getItemName()
					+ "/" + SysConst.STYLE_PATH //风格主目录
					+ "/"+"serviceIocn_min.jpg";	//类别
			
			
			model.addAttribute("picUrl1", picUrl1);
			model.addAttribute("style", style);
			return "/style/editCommon";
		} catch (Exception e) {
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
			model.addAttribute("result", Ajax.JSONResult(1, "失败!"));
		}
		return "resultjson";
	}
	
	
	/**
	 * 获取 客服图标的 div	PC
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
//	    String strDiv = serviceIconService.getDivByStyleId(Integer.valueOf(styleId),DeviceType.PC);
	    try {
	    	String strDiv = (String) CacheMan.getObject(CacheName.DIVICONPC,styleId);
	        res.getWriter().write(callbackFunName + "([ { name:\""+strDiv+"\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	    	logger.error("获取图标div出错,风格id="+styleId,e);
	    }
	}
	
	@RequestMapping(value = "getDialogueDiv.action", method = RequestMethod.GET)
	public void getDialogueDiv(Model model,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/plain");
	    String callbackFunName =req.getParameter("callbackDialogue");//得到js函数名称
	    
	    String str = " <div id='w-kfcbox' style='position:fixed;top:300px;left:611px;display:block;overflow:hidden;width:680px;height:380px;z-index:7998;'> "
	    		+ " <div id='w-kfbox-cnt' style='position:relative;overflow:hidden;width:680px;height:380px;background-image:url(http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1429507915.png);background-repeat:no-repeat;z-index:0;'>  "
	    		+ " <div style='position:absolute;left:669px;top:0px;overflow:hidden;width:11px;height:11px;background-image:url(http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1414986002.gif);background-repeat:no-repeat;z-index:2;cursor:pointer;'></div>  "
	    		+ "     	<div style='position:absolute;left:0px;top:0px;overflow:hidden;width:680px;height:380px;z-index:1;cursor:pointer;'></div> "
	    		+ " </div> </div>"
	    		;
	    
	    try {
	        res.getWriter().write(callbackFunName + "([ { name:\""+str+"\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}
