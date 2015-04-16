package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月2日上午11:21:05
 *    黑名单 Controller
 */
@Controller
@RequestMapping(value = "blacklist")
public class BlacklistController {
   
	private Logger logger = Logger.getLogger(BlacklistController.class);
	
	@Autowired
	private BlacklistService blacklistService;
	
	/**
	 * 查询所有、条件查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Blacklist> pageBean ){
		try {
			blacklistService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "customer/black";
			else
				return "customer/blackList";
		} catch (Exception e) {
			return "/error500";
		}
	}
	
	  /**
     * 保存前页面跳转
     * 
     * @return 返回值
     */
    @RequestMapping(value = "new.action",method=RequestMethod.GET)
    public String toSave(Model model,Blacklist blacklist) {

        return "customer/addBlacklist";
      }

	/**
	 * 保存黑名单实体
	 * @throws ParseException 
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(Blacklist blacklist,String enddate,Model model) throws ParseException {
		
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Date date = sdf.parse(enddate);
			 blacklist.setEndDate(date);
			boolean isSuccess = blacklistService.createNewBlacklist(blacklist);
			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "添加成功!"));
			  } else {
				model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
			  }
		   } catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "添加失败!"));
		 }
		return "resultjson";
	}
	
	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "update.action", method = RequestMethod.POST)
	public String updateBlacklist(Model model, @ModelAttribute("blacklist")Blacklist blacklist ,String enddate) {
    
		try {

			Blacklist toUpdateBlacklist = blacklistService.getBlacklistById(blacklist.getId());

			toUpdateBlacklist.setCustomerId(blacklist.getCustomerId());
			toUpdateBlacklist.setIp(blacklist.getIp());
//			toUpdateBlacklist.setEndDate(blacklist.getEndDate());;
            toUpdateBlacklist.setDescription(blacklist.getDescription());
			boolean isSuccess = blacklistService.updateBlacklist(toUpdateBlacklist);

			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}
		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}

		return "resultjson";

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deletBlacklist(Model model, Integer id) {

		boolean isSuccess = blacklistService.deleteBlacklistById(id);
		String message = "failure";
		Integer code = -1;

		if (isSuccess) {
			message = "success";
			code = 200;
		}
		model.addAttribute("message", message);
		model.addAttribute("code", code);

		return "iews/message";

	}
	/**
	 * 真删除
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "deleteBlackTrue.action", method = RequestMethod.GET)
	public String deleteMsgTrue(Model model,String ids){
		try {
			int num = blacklistService.delete(ids);
			if (num>0) {
				model.addAttribute("result", Ajax.JSONResult(0, "删除成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
			}
		} catch (Exception e) {
		
			model.addAttribute("result", Ajax.JSONResult(1, "删除失败!"));
		}
		return "resultjson";
	}
	
	/**
	 * 页面修改前跳转
	 * @param model
	 * @param blacklistId
	 * @return
	 */
	@RequestMapping(value = "editBlack.action", method = RequestMethod.GET)
	public String update(Model model,Integer blacklistId) {
		try {
			Blacklist blacklist = blacklistService.getBlacklistById(blacklistId);
			
			model.addAttribute("blacklist", blacklist);
		
			return "/customer/editBlacklist";
		} catch (Exception e) {
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}

	}
	
	/**
	 * 检查IP地址是否已存在
	 */
	@RequestMapping(value = "check.action", method = RequestMethod.GET)
	public String queryByCheck(Model model, Blacklist blacklist)
			throws UnsupportedEncodingException {
		try {
			if (blacklist == null || (StringHelper.isEmpty(blacklist.getIp()))) {
				model.addAttribute("result", Ajax.toJson(1, "缺少参数，请重新提交！"));
				return "resultjson";
			}
			Integer count = blacklistService.checkBlacklist(blacklist);
			if (count != null && count == 0) {
				model.addAttribute("result", Ajax.toJson(0, "该IP地址可以被添加！"));
			} else {
				model.addAttribute("result", Ajax.toJson(1, "该IP地址已存在！"));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			model.addAttribute("result", Ajax.toJson(1, "查询出错啦，请刷新后重试！"));
		}
		return "resultjson";
	}


}
