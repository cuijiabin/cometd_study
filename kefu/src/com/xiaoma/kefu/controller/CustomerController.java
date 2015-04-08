package com.xiaoma.kefu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.service.CustomerService;
import com.xiaoma.kefu.util.Ajax;
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
	
	/**
	 * 查询所有、条件查询
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(Model model, String customerName, String phone,Long customerId,
			Integer currentPage, Integer pageRecorders) {

		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
		PageBean<Customer> pageBean = customerService.getResultNameOrPhone(currentPage, pageRecorders,
				customerName, phone,customerId);

		model.addAttribute("list", pageBean.getObjList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("customerName", customerName);
		model.addAttribute("phone", phone);
		model.addAttribute("customerId", customerId);

		return "customer/customerList";
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
	@RequestMapping(value = "update.action", method = RequestMethod.GET)
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




}
