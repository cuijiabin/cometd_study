package com.xiaoma.kefu.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import com.xiaoma.kefu.util.PageBean;

/**
 * @author hanyu
 * @time 2015年4月3日下午4:55:41
 *
 */
public interface BaseDao<T> {
    
	public void find(Class<T> clazz,List<String> relation,List<Criterion> role,ProjectionList projList,List<Order> orders,PageBean<T> pageBean);
	public List<T> findAll(Class<T> clazz);
	public int findRowCount(Class<T> clazz,List<String> relation,List<Criterion> role,ProjectionList projList);
	public T findById(Class<T> clazz,Integer id);
	public T findById(Class<T> clazz,Long id);
	public T findById(Class<T> clazz,String id);
	public Serializable add(T obj);
	public int update(T obj);
	public int delete(T obj);
}
