package com.xiaoma.kefu.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.redis.JedisDao;
import com.xiaoma.kefu.redis.SystemConfiguration;
import com.xiaoma.kefu.util.CookieUtil;
import com.xiaoma.kefu.util.DesUtil;
import com.xiaoma.kefu.util.JsonUtil;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月1日下午5:33:36
 * 
 */

@Service("customerService")
public class CustomerService {

	@Autowired
	private CustomerDao customerDaoImpl;

	private Jedis jedis = JedisDao.getJedis();

	/**
	 * 条件查询
	 */
	public PageBean getResultByCon(Map<String, String> conditions ,Integer currentPage,
			Integer pageRecorders) {
		PageBean pageBean = new PageBean();
		try {
			Integer totalCount = customerDaoImpl.getAllCustomerCount();
			pageBean.setCurrentPage(currentPage);
			pageBean.setPageRecorders(pageRecorders);
			pageBean.setTotalRows(totalCount);
			Integer start = pageBean.getStartRow();
			List list = customerDaoImpl.getCustomerByCon(conditions, start, pageRecorders);
			pageBean.setObjList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}

	/**
	 * 添加
	 */
	public boolean createNewCustomer(Customer customer) {
		return customerDaoImpl.createNewCustomer(customer);
	}

	/**
	 * 添加
	 */
	public Long insert(Customer customer) {

		customer.setCreateDate(new Date());
		return customerDaoImpl.insert(customer);
	}

	/**
	 * 修改
	 */
	public boolean updateCustomer(Customer customer) {
		return customerDaoImpl.updateCustomer(customer);
	}

	/**
	 * 删除
	 */
	public boolean deleteCustomerById(Long id) {
		boolean flag = true;
		Customer customer = customerDaoImpl.getCustomerById(id);
		if (customer != null) {
			customer.setStatus(1);
			flag = customerDaoImpl.updateCustomer(customer);
		}
		return flag;
	}

	/**
	 * 查询一条
	 */
	public Customer getCustomerById(long id) {
		return customerDaoImpl.getCustomerById(id);
	}

	public Long getMaxCustomerId() {
		String maxCustomerId = jedis.get("MaxCustomerId");

		// 如果缓存中不空，直接取
		if (StringUtils.isNotBlank(maxCustomerId)) {
			return Long.valueOf(maxCustomerId);
		}

		return customerDaoImpl.getMaxCustomerId();
	}

	/**
	 * 根据请求获取或者创建用户
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Customer genCustomer(HttpServletRequest request) throws Exception {
		Cookie cookie = CookieUtil.getCustomerCookie(request);
		String ip = CookieUtil.getIpAddr(request);
		Customer customer = customerDaoImpl.getByIp(ip);

		Long customerId = null;
		if (customer != null) {
			customerId = customer.getId();
		} else if (customer == null && cookie != null) {
			String id = DesUtil.decrypt(cookie.getValue(), SystemConfiguration
					.getInstance().getSecretKey());
			customerId = Long.valueOf(id);

		} else if (customer == null && cookie == null) {
			// 创建一个新的Customer
			customer = new Customer();
			customer.setIp(ip);
			customerId = this.insert(customer);
		}

		customer = this.getCustomerById(customerId);
		return customer;
	}


		
		

		
		public List<Customer> findByIds(List<Long> ids){
			
			List<Serializable> idList = JsonUtil.convertLong2Serializable(ids);
			
			return customerDaoImpl.findByIds(Customer.class, idList);
			
		}
}
