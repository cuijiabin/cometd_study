package com.xiaoma.kefu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.service.CustomerService;
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
	 * 查询所有
	 * 
	 * @param conditions
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "find.action", method = RequestMethod.GET)
	public String queryAll(Model model, String customerName, String phone,
			Integer currentPage, Integer pageRecorders) {

		currentPage = (currentPage == null) ? 1 : currentPage;
		pageRecorders = (pageRecorders == null) ? 10 : pageRecorders;
		PageBean<Customer> pageBean = customerService.getResultNameOrPhone(currentPage, pageRecorders,
				customerName, phone);

		model.addAttribute("list", pageBean.getObjList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("customerName", customerName);
		model.addAttribute("phone", phone);

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


}
