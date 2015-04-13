package com.xiaoma.kefu.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

/**
 * @author frongji
 * @time 2015年4月2日上午11:21:05
 *    黑名单 Controller
 */
@Controller
@RequestMapping(value = "blacklist")
public class BlacklistController {
   
	@Autowired
	private BlacklistService blacklistService;
	
//	@Autowired
//	private User user;
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
    @RequestMapping(value = "new.action")
    public String toSave() {

    	
        return "customer/addBlacklist";
      }

	/**
	 * 保存黑名单实体
	 * @throws ParseException 
	 */
  
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save( @ModelAttribute("blacklist")Blacklist blacklist,String enddate,Model model) throws ParseException {
		

		System.out.println("888888888888888888888888888888888888888888888888");
		
		 try {
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  Date date = sdf.parse(enddate);
			 blacklist.setEndDate(date);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			     
			 
		         
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
      System.out.println("========进入修改方法==========");
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


}
