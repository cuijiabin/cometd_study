package com.xiaoma.kefu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.MessageDao;
import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月7日下午5:20:21
 *   常用语 Dao 的实现类
 */
@Repository("messageDaoImpl")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
	
	 private static Logger logger   = Logger.getLogger(MessageDaoImpl.class);
      
	  /**
	   * 获得常用语总条数
	   */
	   @Override
	   public Integer getAllMessageCount(){
		   Session session = getSession();
		   String hql = "select count(1) from Message";
		   
		Query query  = session.createSQLQuery(hql);//createSQLQuery用的是sql语句查询的
		   
		   return ((Number)query.uniqueResult()).intValue();
	   }
	   
	   /**
	    * 条件查询
	    */
		
		@SuppressWarnings("unchecked")
		@Override	
	    public List<Message> getMessageByConditions(Integer start, Integer offset ,String customerName,String phone) {
			
			//参数检查
			start = (start == null)? 0 :start;
			offset = (offset == null)? 20 :offset;
			
			Session session = getSession();
			
			String hql = "from Customer c where 1=1 and c.status<>1";
			if(StringHelper.isNotEmpty(customerName)){
				hql += " and c.customerName like '"+"%"+customerName+"%"+"'";
			}
			if(StringHelper.isNotEmpty(phone)){
				hql += " and c.phone like '"+"%"+phone+"%"+"'";
			}
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			return (List<Message>) query.list();
		}
		
		  /**
         * 查询
         * @param conditions
         * @param pageBean
         */
		@Override
		public void findByCondition(Map<String, String> conditions,
				PageBean<Message> pageBean) {
		      
			List<String> relation = new ArrayList<String>();
			List<Criterion> role = new ArrayList<Criterion>();// 条件
			List<Order> orders = new ArrayList<Order>();// 排序
			if (conditions != null) {
				String customerId= conditions.get("id").trim();//去掉输入的访客编号左右的空格
				if (StringHelper.isNotEmpty(conditions.get("customerName"))) {
					role.add(Restrictions.like("customerName",
							"%" + conditions.get("customerName").trim() + "%"));
				}
			
				if (StringHelper.isNotEmpty(customerId) ){   
					role.add(Restrictions.eq("id", Long.parseLong(customerId))  );
			 }
				
				if (StringHelper.isNotEmpty(conditions.get("phone"))) {
					
					role.add(Restrictions.eq("phone", conditions.get("phone").trim()));
				}
				if (conditions.get("startDate") != null
						&& !conditions.get("startDate").isEmpty()
						&& conditions.get("endDate") != null
						&& !conditions.get("endDate").isEmpty()) {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						role.add(Restrictions.between(
								"createDate",
								format.parse(conditions.get("startDate")
										+ " 0:00:00"),
								format.parse(conditions.get("endDate")
										+ " 23:59:59")));
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}
				}
			}
			orders.add(Order.asc("createDate"));
			find(Message.class, relation, role, null, orders, pageBean);
			logger.info("search Message by conditions!");
		}
}
