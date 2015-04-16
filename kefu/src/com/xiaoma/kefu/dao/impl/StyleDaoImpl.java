package com.xiaoma.kefu.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.StyleDao;
import com.xiaoma.kefu.model.Style;
import com.xiaoma.kefu.util.PageBean;

/**
 * *********************************
* @Description: 风格	daoImpl
* @author: wangxingfei
* @createdAt: 2015年4月7日上午9:29:12
**********************************
 */
@Repository("styleDaoImpl")
public class StyleDaoImpl extends BaseDaoImpl<Style> implements StyleDao {
	
	/**
	 * 根据名称模糊查询
	* @Description: TODO
	* @param styleName
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Style> findByNameLike(String styleName) {
		Session session = getSession();
		String hql = "from Style t where t.name LIKE :styleName ";
		Query query = session.createQuery(hql);
		query.setString("styleName", "%" + styleName + "%");
		return	query.list();
	}

	/**
	 * 查询所有风格
	* @Description: TODO
	* @param conditions	查询条件
	* @param pageBean	分页
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	@Override
	public void findByCondition(Map<String, String> conditions,
			PageBean<Style> pageBean) {
		List<String> relation = new ArrayList<String>();
		List<Criterion> role = new ArrayList<Criterion>();// 条件
		List<Order> orders = new ArrayList<Order>();// 排序
		if (conditions != null) {}
		orders.add(Order.desc("createDate"));
		find(Style.class, relation, role, null, orders, pageBean);
	}
	
	/**
	 * 校验风格名称
	* @Description: TODO
	* @param style
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月13日
	 */
	public Integer validateName(Style style){
		Integer result = 1 ;
		Session session = getSession();
		if(style==null) return result;
		if(style.getId()!=null){
			//更新校验
			String hql = " from Style t where t.id != :id and t.name = :name ";
			Query query = session.createQuery(hql);
			query.setInteger("id", style.getId());
			query.setString("name", style.getName());
			result = query.list().size();
		}else{
			//新增
			String hql = " from Style t where t.name = :name ";
			Query query = session.createQuery(hql);
			query.setString("name", style.getName());
			result = query.list().size();
		}
		return	result;
	}

}
