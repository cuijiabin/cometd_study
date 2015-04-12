package com.xiaoma.kefu.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.DictitemDao;
import com.xiaoma.kefu.model.DictItem;



/**
 * @author 冯榕基
 * @time 2015年1月19日上午11:00:22
 *
 */
@Repository("dictitemDaoImpl")
public class DictitemDaoImpl extends BaseDaoImpl<DictItem> implements DictitemDao{
	
	/**
	 * 查询所有字典详细表数据
	 */
	@Override
     public Integer getAllDictitemCount() {
		
		Session session = getSession();
		String hql = "select count(1) from Dictitem";
		Query query = session.createSQLQuery(hql);
		
		return ((Number)query.uniqueResult()).intValue();
		
	}
	@SuppressWarnings("unchecked")
	@Override	
    public List<DictItem> getDictitemByCode(Integer start, Integer offset,String code) {
		
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer("from Dictitem where 1 = 1");
		if(StringUtils.isNotBlank(code)){
			hqlBf.append(" and code = '"+code+"'");
		}
		
		Query query = session.createQuery(hqlBf.toString()).setFirstResult(start).setMaxResults(offset);
		
		return (List<DictItem>) query.list();
	}
	
	/**
	 * 查询一条
	 */
	@Override
	public DictItem getDictitemByDictitemId(Integer id) {
		
		if(id == null){
			return null;
		}
		return findById(DictItem.class,id);
		
	}
	/**
	 * 查询dict表
	 */
	
	/**
	  * 添加一条
	  * 
	  */
		
		@Override
		public boolean createNewDictitem(DictItem dictitem) {
			try {
				add(dictitem);
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
		public boolean updateDictitem(DictItem dictitem) {
			try {
				update(dictitem);
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
		public boolean deleteDictitemById(Integer id){
			DictItem dictitem = this.getDictitemByDictitemId(id);
			try {	
			delete(dictitem);
			return true;
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
			
		}
		
		@Override
		public Integer getCountByCode(String dictCode) {
			
			//参数检查
			if(StringUtils.isBlank(dictCode)){
				return getAllDictitemCount();
			}
			Session session = getSession();
			String hql = "select count(1) from Dictitem  where code = '"+dictCode+"'";
			Query query = session.createSQLQuery(hql);
			
			return ((Number)query.uniqueResult()).intValue();
		}
		
		@Override
		public List<Integer> getIdsByCodeAndItemCode(String code, String itemCode) {
			//参数检查
			if(StringUtils.isBlank(code) || StringUtils.isBlank(itemCode)){
				return null;
			}
			Session session = getSession();
			String hql = "select id from Dictitem  where code = '"+code+"' and itemCode= '"+itemCode+"'";
			Query query = session.createSQLQuery(hql);
			
			return (List<Integer>) query.list();
		}
		
}
