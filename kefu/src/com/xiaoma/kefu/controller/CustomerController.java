package com.xiaoma.kefu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.model.MessageRecords;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.service.DialogueService;
import com.xiaoma.kefu.service.MessageRecordsService;
import com.xiaoma.kefu.util.Ajax;
import com.xiaoma.kefu.util.MapEntity;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月2日上午9:54:59
 *
 */
@Controller
@RequestMapping(value = "customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DialogueService dialogueService;//对话信息
	@Autowired
	private MessageRecordsService messageRecordsService;//留言信息
	
	/**
	 * 查询所有、条件查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(MapEntity conditions, @ModelAttribute("pageBean") PageBean<Customer> pageBean) {

		try {
			customerService.getResult(conditions.getMap(), pageBean);
			if (conditions == null || conditions.getMap() == null
					|| conditions.getMap().get("typeId") == null)
				return "customer/customer";
			else
				return "customer/customerList";
		} catch (Exception e) {
			return "/error500";
		}

	}
	/**
	 * 查询所有
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "getLeft.action", method = RequestMethod.GET)
	public String getLeft(Model model,Integer funcId) {
		
		return "customer/left";
	}
//	 /**
//     * 保存前页面跳转
//     * 
//     * @return 返回值
//     */
//    @RequestMapping(value = "/new.action")
//    public String toSave() {
//
//        return "customer/addBlacklist";
//    }

	/**
	 * 添加
	 */

	@RequestMapping(value = "add.action", method = RequestMethod.GET)
	public String addCustomer(HttpSession session, Model model, Customer customer) {

		try {
		
			boolean isSuccess = customerService.createNewCustomer(customer);
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
	public String updateCustomer(Model model, Customer customer) {

		try {
			
			Customer toUpdate = customerService.getCustomerById(customer.getId());
			toUpdate.setPhone(customer.getCustomerName());
			
			
			boolean isSuccess = customerService.updateCustomer(toUpdate);

			if (isSuccess) {
				model.addAttribute("result", Ajax.JSONResult(0, "修改成功!"));
			} else {
				model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
			}

		} catch (Exception e) {
			model.addAttribute("result", Ajax.JSONResult(1, "修改失败!"));
		}
		return "iews/message";

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.action", method = RequestMethod.GET)
	public String deleteCustomer(Model model, Long id) {

		boolean isSuccess = customerService.deleteCustomerById(id);
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
	 * 编辑用户信息页面(用于记录中心-聊天记录)
	* @Description: TODO
	* @param model
	* @param customerId
	* @param dialogueId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "editCus.action", method = RequestMethod.GET)
	public String editCus(Model model,String customerId,String dialogueId) {
		try {
			Customer customer = customerService.getCustomerById(Long.valueOf(customerId));
			Dialogue dialogue = dialogueService.findById(Long.valueOf(dialogueId));
			model.addAttribute("customer", customer);
			model.addAttribute("dialogue", dialogue);
			return "/customer/editCus";
		} catch (Exception e) {
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	
	/**
	 * 更新用户信息,用于记录中心
	* @Description: TODO
	* @param model
	* @param customer
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月12日
	 */
	@RequestMapping(value = "updateCus.action", method = RequestMethod.POST)
	public String updateCus(Model model, Customer customer) {
		try {
			
			Customer toUpdate = customerService.getCustomerById(customer.getId());
			toUpdate.setCustomerName(customer.getCustomerName());
			toUpdate.setPhone(customer.getCustomerName());
			toUpdate.setEmail(customer.getEmail());
			toUpdate.setRemark(customer.getRemark());
			boolean isSuccess = customerService.updateCustomer(customer);
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
	 * 编辑用户信息页面(用于记录中心留言记录)
	* @Description: TODO
	* @param model
	* @param customerId
	* @param dialogueId
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	@RequestMapping(value = "editCus4Msg.action", method = RequestMethod.GET)
	public String editCus4Msg(Model model,String customerId,Integer msgId) {
		try {
			Customer customer = customerService.getCustomerById(Long.valueOf(customerId));
			MessageRecords msg = messageRecordsService.findById(Integer.valueOf(msgId));
			model.addAttribute("customer", customer);
			model.addAttribute("dialogue", msg);//和聊天记录用一个
			return "/customer/editCus";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "对不起出错了");
			return "error500";
		}
	}
	



}
