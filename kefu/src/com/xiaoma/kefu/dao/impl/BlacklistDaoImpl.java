package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.BlacklistDao;
import com.xiaoma.kefu.dao.HibBaseDao;
import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.util.StringHelper;

/**
 * @author frongji
 * @time 2015年4月3日上午10:30:18
 *   黑名单的接口实现类
 */
@Repository("blacklistDaoImpl")
public class BlacklistDaoImpl extends HibBaseDao<Blacklist> implements BlacklistDao{

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
		String hql = "select count(1) from Blacklist  ";
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
			if(userId!=null){
				hql += " and b.userId like '"+"%"+userId+"%"+"'";
			}
			
			if(StringHelper.isNotEmpty(description)){
				hql += " and b.description like '"+"%"+description+"%"+"'";
			}
			Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
			return (List<Blacklist>) query.list();
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

	
}
