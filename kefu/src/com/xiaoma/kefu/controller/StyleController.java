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

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.BusiGroup;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.service.BusiGroupService;
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
	
	@RequestMapping(value = "getStyleDiv.action", method = RequestMethod.GET)
	public String getStyleDiv(HttpServletRequest req,HttpServletResponse res){
//		res.setContentType("text/plain");
//	    String callbackFunName =req.getParameter("callbackparam");//得到js函数名称
//	    try {
//	        res.getWriter().write(callbackFunName + "([ { name:\"John\"}])"); //返回jsonp数据
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
		return "/styleDiv/divOne";
	}
	
	@RequestMapping(value = "getIconDiv.action", method = RequestMethod.GET)
	public void getIconDiv(Model model,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/plain");
	    String callbackFunName =req.getParameter("callbackIcon");//得到js函数名称
	    
//	    单引号,双引号 都 \\\	OK
	    String str = " <div id=\\\"w-kfrbox\\\" style=\\\"position:fixed;top:200px;right:10px;\\\"> "
		+ " <div style=\\\"position:absolute;top:0px;left:0;overflow:hidden;width:11px;height:11px;background-image:url(http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1414986002.gif);background-repeat:no-repeat;z-index:2;cursor:pointer;\\\"></div>  "
		+ " <img src=\\\"http://pics2.xiaoma.com/xiaoma/sem/float/kc_rtel_05.png\\\" onclick=\\\"window.open(\\\'http://oc2.xiaoma.com/new/client.php?arg=53kf&amp;style=3&amp;l=zh-cn&amp;charset=utf-8&amp;lytype=0&amp;referer=http%3A%2F%2Fkecheng.xiaoma.com%2F&amp;isvip=bcf14bbb85a346c2fb52e8cea8822cce&amp;identifier=&amp;keyword=http%3A//kecheng.xiaoma.com/&amp;tfrom=1&amp;tpl=crystal_blue\\\',\\\'_blank\\\',\\\'height=573,width=803,top=200,left=200,status=yes,toolbar=no,menubar=no,resizable=yes,scrollbars=no,location=no,titlebar=no\\\')\\\" style=\\\"cursor:pointer\\\"> "
		+ " </div> "
		;
	    
//	    //单引号不管, 双引号 \\\	ok
//	    String str = " <div id=\\\"w-kfrbox\\\" style=\\\"position:fixed;top:200px;right:10px;\\\"> "
//		+ " <div style=\\\"position:absolute;top:0px;left:0;overflow:hidden;width:11px;height:11px;background-image:url(http://oc2.xiaoma.com/img/upload/53kf/zdyivt/zdyivt_53kf_1414986002.gif);background-repeat:no-repeat;z-index:2;cursor:pointer;\\\"></div>  "
//		+ " <img src=\\\"http://pics2.xiaoma.com/xiaoma/sem/float/kc_rtel_05.png\\\" onclick=\\\"gotoKF()\\\" style=\\\"cursor:pointer\\\"> "
//		+ " </div> "
//		;
	    
	    
	    try {
	        res.getWriter().write(callbackFunName + "([ { name:\""+str+"\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	        e.printStackTrace();
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
//	        res.getWriter().write(callbackFunName + "([ { name:\"John\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}
