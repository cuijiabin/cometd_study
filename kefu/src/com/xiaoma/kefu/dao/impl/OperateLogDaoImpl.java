package com.xiaoma.kefu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiaoma.kefu.dao.OperateLogDao;
import com.xiaoma.kefu.model.OperateLog;
import com.xiaoma.kefu.util.PageBean;
import com.xiaoma.kefu.util.StringHelper;

@Repository("operateLogDaoImpl")
public class OperateLogDaoImpl extends BaseDaoImpl<OperateLog> implements
		OperateLogDao {
	private static Logger logger = Logger.getLogger(LoginLogDaoImpl.class);

	public void findByCondition(Map<String, String> conditions,
			PageBean<OperateLog> pageBean) {
		List<String> relation = new ArrayList<String>();
		List<Criterion> role = new ArrayList<Criterion>();// 条件
		List<Order> orders = new ArrayList<Order>();// 排序
		try {
			if (conditions != null) {
				if (StringHelper.isNotEmpty(conditions.get("loginName"))) {
					role.add(Restrictions.like("loginName", "%"
							+ conditions.get("loginName").trim() + "%"));
				}
				if (StringHelper.isNotEmpty(conditions.get("deptId"))
						&& !"0".equals(conditions.get("deptId"))) {
					role.add(Restrictions.eq("deptId",
							Integer.parseInt(conditions.get("deptId"))));
				}
				if (StringHelper.isNotEmpty(conditions.get("userId"))
						&& !"0".equals(conditions.get("deptId"))) {
					role.add(Restrictions.eq("userId",
							Integer.parseInt(conditions.get("userId"))));
				}
				if (conditions.get("startDate") != null
						&& !conditions.get("startDate").isEmpty()) {
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");

					role.add(Restrictions.between(
							"createDate",
							format.parse(conditions.get("startDate")
									+ " 0:00:00"),
							(StringHelper.isEmpty(conditions.get("endDate")) ? format.parse(conditions.get("endDate"))
									: format.parse(conditions.get("endDate"))
											+ " 23:59:59")));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		orders.add(Order.asc("createDate"));
		find(OperateLog.class, relation, role, null, orders, pageBean);
	}
}
