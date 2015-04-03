package com.xiaoma.kefu.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoma.kefu.dao.BaseDao;
import com.xiaoma.kefu.util.PageBean;
/**
 * 公用Dao类，封装基本的增删改查和条件查询
 * @author Administrator
 *
 * @param <T>
 */
@Repository
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T> {
	//由Spring注入SessionFactory
	
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取Session
	 * @return
	 */
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 对任意类型的实体类进行条件查询（PageBean）
	 * @param clazz
	 * @param relation：关系映射
	 * @param role:条件
	 * @param projList：是否使用分组、聚合函数等
	 * @param pageBean
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public void find(Class<T> clazz,List<String> relation,List<Criterion> role,ProjectionList projList,List<Order> orders,PageBean<T> pageBean){
		Session session = getSession();
		//创建对应的criteria
		Criteria criteria = session.createCriteria(clazz);
//		//添加关系映射
		if(relation != null){
			for(String str : relation){
				if(str.contains(".")){
					CriteriaUtil.addAlias((CriteriaImpl)criteria,str,str.substring(str.indexOf(".") + 1));
					continue;
				}
				CriteriaUtil.addAlias((CriteriaImpl)criteria,str,str);
			}
		}
		//添加查询条件
		if(role != null){
			for(Criterion criterion : role){
				criteria.add(criterion);
			}
		}
		
		//判断是否分组、使用聚合函数等
		if(projList != null){
			criteria.setProjection(projList);
		}
		//排序
		if(orders != null){
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
		//去除由于表连接带来的重复数据
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//将查询结果保存在二级缓冲中
//		criteria.setCacheable(true);
//		criteria.setCacheRegion("myCacheRegion");
		//将总行数和结果集存入PageBean当中
		pageBean.setTotalRows(this.findRowCount(clazz, relation, role, projList));
		//分页处理
		if(!(pageBean.getStartRow() == null || pageBean.getPageRecorders() == null)){
			criteria.setFirstResult(pageBean.getStartRow());
			criteria.setMaxResults(pageBean.getPageRecorders());
		}
		pageBean.setObjList(criteria.list());
	}
	/**
	 * 查询所有
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> clazz){
		Session session = getSession();
		//创建对应的criteria
		Criteria criteria = session.createCriteria(clazz);
		//去除由于表连接带来的重复数据
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//将查询结果保存在二级缓冲中
		criteria.setCacheable(true);
		criteria.setCacheRegion("myCacheRegion");
		//将总行数和结果集存入PageBean当中
		return criteria.list();
	}
	/**
	 * 查询符合条件的记录行数
	 * @param clazz
	 * @param relation
	 * @param role
	 * @return
	 */
	public int findRowCount(Class<T> clazz,List<String> relation,List<Criterion> role,ProjectionList projList){
		Session session = getSession();
		//创建对应的criteria
		Criteria criteria = session.createCriteria(clazz);
		//添加关系映射
		if(relation != null){
			for(String str : relation){
				if(str.contains(".")){
					CriteriaUtil.addAlias((CriteriaImpl)criteria,str,str.substring(str.indexOf(".") + 1));
					continue;
				}
				CriteriaUtil.addAlias((CriteriaImpl)criteria,  str ,  str );
			}
		}
		//添加查询条件
		if(role != null){
			for(Criterion criterion : role){
				criteria.add(criterion);
			}
		}
		//判断是否分组、使用聚合函数等
		if(projList != null){
			criteria.setProjection(projList);
		}
		//去除由于表连接带来的重复数据
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
	}
	
	/**
	 * 根据id查找实体对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(Class<T> clazz,Integer id){
		Session session = getSession();
		return (T) session.get(clazz, id);
	}
	
	/**
	 * 根据id查找实体对象
	 * @param clazz
	 * @param id Long
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(Class<T> clazz,Long id){
		Session session = getSession();
		return (T) session.get(clazz, id);
	}
	
	/**
	 * 根据ID查找实体对象(冯榕基)
	 */
	@SuppressWarnings("unchecked")
	public T findById(Class<T> clazz,String id){
		Session session = getSession();
		return (T) session.get(clazz, id);
	}
	
	/**
	 * 添加
	 * @param obj
	 * 
	 */
	public Serializable add(T obj){
		try{
			Session session = getSession();
			return session.save(obj);
		}catch(Exception ex){
			return 0;
		}
	}
	/**
	 * 更新
	 * @param obj
	 * 
	 */
	public int update(T obj){
		try{
			Session session = getSession();
			session.update(obj);
			return 1;
		}catch(Exception ex){
			return 0;
		}
	}
	/**
	 * 删除
	 * @param obj
	 * 
	 */
	public int delete(T obj){
		try{
			Session session = getSession();
			session.delete(obj);
			return 1;
		}catch(Exception ex){
			return 0;
		}
	}
}
/**
 * 创建criteria访问实体间的关系，如果重复则不添加，可以进行多层访问并访问任何属性
 * @author lenovo
 *
 */
class CriteriaUtil {
	@SuppressWarnings("rawtypes")
	public static Criteria addAlias(CriteriaImpl criteriaImpl, String path,
			String name) {
		if (path == null)
			return criteriaImpl;
		for (Iterator iter = criteriaImpl.iterateSubcriteria(); iter.hasNext();) {
			Subcriteria subCriteria = (Subcriteria) iter.next();
			if (path.equals(subCriteria.getPath()))
				return criteriaImpl;
		}
		return criteriaImpl.createAlias(path, name,JoinType.LEFT_OUTER_JOIN);
	}
}