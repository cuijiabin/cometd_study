package com.xiaoma.kefu.dao.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.DictDao;
import com.xiaoma.kefu.model.Dict;




@Repository("dictDaoImpl")
public class DictDaoImpl extends BaseDaoImpl<Dict> implements DictDao {
	
	/**
	 * 查询所有字典表数据
	 */
	@Override
     public Integer getAllDictCount() {
		
		Session session = getSession();
		String hql = "select count(1) from Dict";
		Query query = session.createSQLQuery(hql);
		
		return ((Number)query.uniqueResult()).intValue();
		
	}
	@SuppressWarnings("unchecked")
	@Override	
    public List<Dict> getDictOrderById(Integer start, Integer offset) {
		
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		String hql = "from Dict limit order by code asc";
		Query query = session.createQuery(hql).setFirstResult(start).setMaxResults(offset);
		
		return (List<Dict>) query.list();
	}
	
	
   /**
    * 条件查询
    */
	
	@SuppressWarnings("unchecked")
	@Override	
    public List<Dict> getDictByCodeAndName(Integer start, Integer offset ,String code,String name) {
		
		//参数检查
		start = (start == null)? 0 :start;
		offset = (offset == null)? 20 :offset;
		
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer("from Dict where 1 = 1");
		if(StringUtils.isNotBlank(code)){
			hqlBf.append(" and code  like '%"+code+"%'");
		}
		if(StringUtils.isNotBlank(name)){
			hqlBf.append(" and name like '%"+name+"%'");
		}
        
		Query query = session.createQuery(hqlBf.toString()).setFirstResult(start).setMaxResults(offset);
		
		return (List<Dict>) query.list();
	}

 /**
  * 添加一条
  * 
  */
	
	@Override
	public boolean createNewDict(Dict dict) {
		try {
			add(dict);
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
	public boolean updateDict(Dict dict) {
		try {
			update(dict);
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
	public boolean deleteDictById(String id){
		Dict dict = this.getDictByDictId(id);
		try {	
		delete(dict);
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
	public Dict getDictByDictId(String id) {
		if(id == null){
			return null;
		}
		return findById(Dict.class,id);
		
	}
	@Override
	public Integer getCountByCodeAndName(String code, String name) {
		Session session = getSession();
		StringBuffer hqlBf = new StringBuffer("select count(1) from Dict where 1 = 1");
		if(StringUtils.isNotBlank(code)){
			hqlBf.append(" and code  like '%"+code+"%'");
		}
		if(StringUtils.isNotBlank(name)){
			hqlBf.append(" and name like '%"+name+"%'");
		}
		Query query = session.createSQLQuery(hqlBf.toString());
		
		return ((Number)query.uniqueResult()).intValue();
	}

	
}
