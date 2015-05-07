package com.xiaoma.kefu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.CustomerDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月1日下午4:57:03
 * 
 */
@Repository("customerDaoImpl")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements
		CustomerDao {

	private static Logger logger = Logger.getLogger(CustomerDaoImpl.class);

//	@Override
//	public Integer getAllCustomerCount(Map<String, String> conditions,String beginDate,String endDate, PageBean pageBean) {
//		Session session = getSession();
//		String hql = "select count(1) from Customer  ";
//
//		Query query = session.createSQLQuery(hql);
//
//		return ((Number) query.uniqueResult()).intValue();
//
//	}
	/**
	 * 查询总条数（测试）
	 */
	@Override
	public Integer getAllCustomerCount(Map<String, String> conditions,String beginDate,String endDate, PageBean pageBean) {
		Session session = getSession();

		String hql = "select  COUNT(DISTINCT(a.id)) FROM customer a LEFT JOIN dialogue b on a.id=b.customerId  WHERE 1=1 and a.status<>1  ";
		if(beginDate!=null && endDate!=null){
			hql +="and  a.createDate BETWEEN '"+ beginDate+" "+"00:00:00"+"' " +"AND"+" '" +endDate+" "+"23:59:59"+"' ";
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sdf.format(new Date());
			hql +="and  a.createDate BETWEEN '"+ today+" "+"00:00:00"+"' " +"AND"+" '" +today+" "+"23:59:59"+"' ";
		}
		if (conditions !=null) {
			   String customerName =  conditions.get("customerName").trim();
			if(StringHelper.isNotEmpty(customerName)){
				hql += " and a.customerName like '"+"%"+customerName+"%"+"'";
			}	
		         String customerId =  conditions.get("id").trim();
			if(StringHelper.isNotEmpty(customerId)){
				hql += " and a.id = "+customerId+" " ;
			}	
			    String phone = conditions.get("phone").trim();
			if(StringHelper.isNotEmpty(phone)){
				hql += " and a.phone like '"+"%"+phone+"%"+"'" ;
			}
			   String styleName =  conditions.get("styleName").trim();
			if(StringHelper.isNotEmpty(styleName)){
				hql += " and a.styleName like '"+"%"+styleName+"%"+"'";
			}
			  String consultPage =  conditions.get("consultPage").trim();
			if(StringHelper.isNotEmpty(consultPage)){
				hql += " and b.consultPage like '"+"%"+consultPage+"%"+"'";
			}
				
			 String keywords =  conditions.get("keywords").trim();
			if(StringHelper.isNotEmpty(keywords)){
				hql += " and b.keywords like '"+"%"+keywords+"%"+"'";
			}
		}
		Query query = session.createSQLQuery(hql);
		return ((Number) query.uniqueResult()).intValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerOrderById(Integer start, Integer offset) {

		// 参数检查
		start = (start == null) ? 0 : start;
		offset = (offset == null) ? 20 : offset;

		Session session = getSession();
		String hql = "from Customer limit order by id asc";
		Query query = session.createQuery(hql).setFirstResult(start)
				.setMaxResults(offset);

		return (List<Customer>) query.list();
	}

	/**
	 * 条件查询(测试)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List getCustomerByCon(Map<String, String> conditions,String beginDate,String endDate, PageBean pageBean) {
		
	
		Session session = getSession();
		String hql = "select a.*,b.* FROM customer a LEFT JOIN dialogue b on a.id=b.customerId  WHERE 1=1 and a.status<>1  ";
		if(beginDate!=null && endDate!=null){
			hql +="and  a.createDate BETWEEN '"+ beginDate+" "+"00:00:00"+"' " +"AND"+" '" +endDate+" "+"23:59:59"+"' ";
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String today = sdf.format(new Date());
			hql +="and  a.createDate BETWEEN '"+ today+" "+"00:00:00"+"' " +"AND"+" '" +today+" "+"23:59:59"+"' ";
		}
		if (conditions !=null) {
			   String customerName =  conditions.get("customerName").trim();
			if(StringHelper.isNotEmpty(customerName)){
				hql += " and a.customerName like '"+"%"+customerName+"%"+"'";
			}	
		         String customerId =  conditions.get("id").trim();
			if(StringHelper.isNotEmpty(customerId)){
				hql += " and a.id = "+customerId+" " ;
			}	
			    String phone = conditions.get("phone").trim();
			if(StringHelper.isNotEmpty(phone)){
				hql += " and a.phone like '"+"%"+phone+"%"+"'" ;
			}
			   String styleName =  conditions.get("styleName").trim();
			if(StringHelper.isNotEmpty(styleName)){
				hql += " and a.styleName like '"+"%"+styleName+"%"+"'";
			}
			  String consultPage =  conditions.get("consultPage").trim();
			if(StringHelper.isNotEmpty(consultPage)){
				hql += " and b.consultPage like '"+"%"+consultPage+"%"+"'";
			}
				
			 String keywords =  conditions.get("keywords").trim();
			if(StringHelper.isNotEmpty(keywords)){
				hql += " and b.keywords like '"+"%"+keywords+"%"+"'";
			}
		}
		 hql += "GROUP BY a.id ORDER BY createDate desc";
		Query query = session.createSQLQuery(hql).addEntity("a", Customer.class).addEntity("b", Dialogue.class).setFirstResult(pageBean.getStartRow()).setMaxResults(pageBean.getPageRecorders());
		List list = query.list();
		return list;
	}


	/**
	 * 条件查询(测试导出报表)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List  getCustomerByConExl(String beginDate,String endDate,String customerName,String id,String phone, String styleName,String consultPage,
			   String keywords) {
		
		Session session = getSession();
		String hql = "select a.*,b.* FROM customer a LEFT JOIN dialogue b on a.id=b.customerId  WHERE 1=1 and a.status<>1  ";
		if(beginDate!=null && endDate!=null){
			hql +="and  a.createDate BETWEEN '"+ beginDate+" "+"00:00:00"+"' " +"AND"+" '" +endDate+" "+"23:59:59"+"' ";
		}
		   String name = customerName.trim();
		if(StringHelper.isNotEmpty(name)){
			hql += " and a.customerName like '"+"%"+name+"%"+"'";
		}	
	         String customerId =  id.trim();
		if(StringHelper.isNotEmpty(customerId)){
			hql += " and a.id = "+customerId+" " ;
		}	
		    String pho = phone.trim();
		if(StringHelper.isNotEmpty(pho)){
			hql += " and a.phone = "+pho+" " ;
		}
		   String styleNa =  styleName.trim();
		if(StringHelper.isNotEmpty(styleNa)){
			hql += " and a.styleName like '"+"%"+styleNa+"%"+"'";
		}
		  String consultPa =  consultPage.trim();
		if(StringHelper.isNotEmpty(consultPa)){
			hql += " and b.consultPage like '"+"%"+consultPa+"%"+"'";
		}
		 String keywo =  keywords.trim();
		if(StringHelper.isNotEmpty(keywo)){
			hql += " and b.keywords like '"+"%"+keywo+"%"+"'";
		}
		
		 hql += "GROUP BY a.id ORDER BY createDate desc";
	
		Query query = session.createSQLQuery(hql).addEntity("a", Customer.class).addEntity("b", Dialogue.class);
		List list = query.list();
		return list;
	}
	
	/**
	 * 查询是否存在该访客
	 */
	@Override
	public Integer checkCus(Long id ){
		Session session = getSession();
		StringBuffer hqlBuffer = new StringBuffer(
				"select count(m.id) from Customer m  where  m.id="+id+" " );
	  Query query = session.createQuery(hqlBuffer.toString());
	  Object obj = query.uniqueResult();
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 添加一条
	 */
	@Override
	public boolean createNewCustomer(Customer customer) {

		try {
			add(customer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @return
	 */
	@Override
	public boolean updateCustomer(Customer customer) {
		try {
			update(customer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询一条
	 */
	@Override
	public Customer getCustomerById(Long id) {
		if (id == null) {
			return null;
		}
		return findById(Customer.class, id);
	}

	@Override
	public Long getMaxCustomerId() {
		Session session = getSession();
		String hql = "select max(id) from Customer  ";

		Query query = session.createSQLQuery(hql);

		return ((Number) query.uniqueResult()).longValue();
	}

	@Override
	public Long insert(Customer customer) {
		try {
			Long id = (Long) add(customer);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer getByIp(String ip) {
		Session session = getSession();
		String hql = "from Customer c where c.ip= '" + ip + "'";
		Query query = session.createQuery(hql);
		return (Customer) query.uniqueResult();
	}

}
