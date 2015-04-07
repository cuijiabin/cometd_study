package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.MessageDao;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月7日下午5:20:21
 *   常用语 Dao 的实现类
 */
@Repository("messageDaoImpl")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
      
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
}
