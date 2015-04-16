package com.xiaoma.kefu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.BlacklistDao;
import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月3日上午10:30:18
 *   黑名单的接口实现类
 */
@Repository("blacklistDaoImpl")
public class BlacklistDaoImpl extends BaseDaoImpl<Blacklist> implements BlacklistDao{

	 private static Logger logger   = Logger.getLogger(BlacklistDaoImpl.class);
	
	/**
	 * 添加一条
	 */
	@Override
	public boolean createNewBlacklist(Blacklist blacklist) {
		try {
			add(blacklist);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * 查询所有用户
	 */
	@Override
     public Integer getAllBlacklistCount() {
		
		Session session = getSession();
		String hql = "select count(1) from black_list  ";
		Query query = session.createSQLQuery(hql);
		
		return ((Number)query.uniqueResult()).intValue();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override	
    public List<Blacklist> getBlacklistOrderById(Integer start, Integer offset) {
		
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		String hql = "from Blacklist  limit order by id asc";
		Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
		
		return (List<Blacklist>) query.list();
	}
	
	   /**
	    * 条件查询
	    */
		
		@SuppressWarnings("unchecked")
		@Override	
	    public List<Blacklist> getBlacklistByConditions(Integer start, Integer offset ,Long customerId,Integer userId,String description) {
			
			//参数检查
			start = (start == null)? 0 :start;
			offset = (offset == null)? 20 :offset;
			
			Session session = getSession();
			
			String hql = "from Blacklist b where 1=1  ";
			if(customerId!=null){
				hql += " and b.customerId like '"+"%"+customerId+"%"+"'";
			}
			
			
			if(StringHelper.isNotEmpty(description)){
				hql += " and b.description like '"+"%"+description+"%"+"'";
			}
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			return (List<Blacklist>) query.list();
		}
		

		/**
         * 查询
         * @param conditions
         * @param pageBean
         */
		@Override
		public void findByCondition(Map<String, String> conditions,
				PageBean<Blacklist> pageBean) {
		     try{ 
			List<String> relation = new ArrayList<String>();
			List<Criterion> role = new ArrayList<Criterion>();// 条件
			List<Order> orders = new ArrayList<Order>();// 排序
			if (conditions != null) {
				
				if (StringHelper.isNotEmpty(conditions.get("description"))) {
					role.add(Restrictions.like("description",
							"%" + conditions.get("description").trim() + "%"));
				}
				String customerId= conditions.get("customerId").trim();//去掉输入的访客编号左右的空格
				if (StringHelper.isNotEmpty(customerId) ){   
						role.add(Restrictions.eq("customerId", Long.parseLong(customerId))  );
				 }
				
                 String userName= conditions.get("userName").trim();
				if (StringHelper.isNotEmpty(userName)) {
					role.add(Restrictions.like("userName",
							"%" + conditions.get("userName").trim() + "%"));
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
			find(Blacklist.class, relation, role, null, orders, pageBean);
			logger.info("search Blacklist by conditions!");
		     }catch(Exception e){
		    	 e.printStackTrace();
		     }
		} 

		/**
		 * 查询一条
		 */
		@Override
		public Blacklist getBlacklistByBlacklistId(Integer id) {
			
			if(id == null){
				return null;
			}
			return findById(Blacklist.class,id);
			
		}
		
		/**
		 * 修改
		 * @param 
		 * @return
		 */
		@Override
		public boolean updateBlacklist(Blacklist Blacklist) {
			try {
				update(Blacklist);
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
		public boolean deleteBlacklistById(Integer id){
			Blacklist Blacklist = this.getBlacklistByBlacklistId(id);
			try {	
			delete(Blacklist);
			return true;
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			
		}
		
		/**
		 * 精确查询数据库中的信息
		 */
		@Override
		public Integer checkBlacklist(Blacklist blacklist) {
			Session session = getSession();
			StringBuffer hqlBf = new StringBuffer(
					"select count(a.id) from Blacklist a where 1 = 1");
			if (StringUtils.isNotBlank(blacklist.getIp())) {
				hqlBf.append(" and a.ip  = '" + blacklist.getIp() + "' ");
			}
			
			if(blacklist.getId()!=null && blacklist.getId()>0){
				hqlBf.append(" and a.id  != " + blacklist.getId() + "  ");
			}
		
			Query query = session.createQuery(hqlBf.toString());
			Object obj = query.uniqueResult();
			return Integer.parseInt(obj.toString());
		}

	
}
