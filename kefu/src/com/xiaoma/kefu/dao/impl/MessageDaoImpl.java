package com.xiaoma.kefu.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
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
      * 查询
      * @param conditions
      * @param pageBean
      */
		@Override
		public void findByCondition(Map<String, String> conditions,
				PageBean<Message> pageBean,Integer id) {
		     try{ 
			List<String> relation = new ArrayList<String>();
			List<Criterion> role = new ArrayList<Criterion>();// 条件
			List<Order> orders = new ArrayList<Order>();// 排序
			if(id!=null){
		      role.add(Restrictions.eq("messageTypeId",id ));
			}
			if (conditions != null) {
				if (StringHelper.isNotEmpty(conditions.get("title"))) {
					role.add(Restrictions.like("title",
							"%" + conditions.get("title").trim() + "%"));
				}
			}
			orders.add(Order.desc("createDate"));
			find(Message.class, relation, role, null, orders, pageBean);
			logger.info("search Message by conditions!");
		     }catch(Exception e){
		    	 e.printStackTrace();
		     }
		}
		
		/**
		 * 查询子节点排序值为最大的 序号
		 */
		@Override
		public Integer getMaxId(){
			Session session = getSession();

			String hql="SELECT MAX(m.id)  FROM message m ";
			SQLQuery sqlQuery = session.createSQLQuery(hql);

			Integer count = (Integer)sqlQuery.uniqueResult();
			return count;

		}
		
	    /**
	     * 查询一条
	     */
		@Override
		public Message getMessageById(Integer id) {
			if(id==null)
			{
				return null;
			}
			return findById(Message.class,id);
		}
		
		/**
		 * 添加一条
		 */
		@Override
		public boolean createNewMessage(Message message){
			
			try {
				 add(message);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		}
		
		/**
		 * 修改
		 * @param 
		 * @return
		 */
		@Override
		public boolean updateMessage(Message message) {
			try {
				update(message);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		/**
		 * 删除
		 */
		@Override
		public Integer deleteMessageById(Integer id){
			Message message = this.getMessageById(id);
			
			try {
			 delete(message);
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			return id;
		
		}
}
