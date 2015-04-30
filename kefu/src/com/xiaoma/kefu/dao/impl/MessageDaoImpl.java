package com.xiaoma.kefu.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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
				String title = conditions.get("title").trim();
				if (StringHelper.isNotEmpty(title)) {
					role.add(Restrictions.like("title",
							"%" + title + "%"));
				}
				if (StringHelper.isNotEmpty(conditions.get("type"))) { //获得常用语的类型
					 String type = conditions.get("type").trim();
					 int tpe=Integer.parseInt(type);
					 if (tpe==1) {
						role.add(Restrictions.eq("typeId", tpe));
					}else {
						role.add(Restrictions.eq("typeId", tpe));
						String userId = conditions.get("userId").trim();
						int user = Integer.parseInt(userId);
						role.add(Restrictions.eq("userId", user));
					}
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
		 * 查询是否有常用语
		 */
		@Override
		public Integer checkDaily(Integer messageTypeId ){
			Session session = getSession();
			StringBuffer hqlBuffer = new StringBuffer(
					"select count(m.messageTypeId) from Message m  where  m.messageTypeId="+messageTypeId+"  " );
		  Query query = session.createQuery(hqlBuffer.toString());
		  Object obj = query.uniqueResult();
			return Integer.parseInt(obj.toString());
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
		/***
		 * 根据typeId查询所有的分类
		 * @param typeId
		 * @param userId
		 * @return
		 */
		public List<Message> findAllByParam(Integer typeId,Integer userId){
			try {
				Session session = getSession();
				String sql = "from Message a where a.typeId="+typeId;
				if(typeId == 2){
					sql += " and a.userId="+userId;
				}
				Query query = session.createQuery(sql);
				List<Message> list = (List<Message>)query.list();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			return null;
		}
}
