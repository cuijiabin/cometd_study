package com.xiaoma.kefu.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.service.BlacklistService;
import com.xiaoma.kefu.util.AddressUtil;
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
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Blacklist> pageBean ,String createDate,String endDate){
		try {
			blacklistService.getResult(conditions.getMap(), pageBean,createDate);
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
    	
    	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date sub = DateUtils.addHours(now, 8);//设置失效时间为8个小时
	    String	endDate = sdf.format(sub);  //失效时间
        model.addAttribute("endDate", endDate);
        return "customer/addBlacklist";
      }

	/**
	 * 保存黑名单实体
	 * @throws ParseException 
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public String save(HttpSession session ,Blacklist blacklist,String enddate,Model model) throws ParseException {
		
		try {
			User user = (User) session.getAttribute(CacheName.USER);
			if (user == null)
				return "login";
			 String loginName = user.getLoginName();//获得工号
			 Integer userId = user.getId();
			 blacklist.setUserName(loginName);
			 blacklist.setUserId(userId);
			
			 SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date date = sdf.parse(enddate); //String型 时间转换为 Date型
			 blacklist.setEndDate(date);
			 
			 Date now =  new Date();
			 String nowString =  sdf.format(now); //Date型 时间 转换为 String型
			 Date nowDate = sdf.parse(nowString);
			 blacklist.setStartDate(nowDate);
			 blacklist.setCreateDate(nowDate);
			 
		    String ip = blacklist.getIp();
			String address = AddressUtil.getAddresses("ip=" + ip, "utf-8");//调取分析出IP地址的方法
			blacklist.setIpInfo(address);
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
