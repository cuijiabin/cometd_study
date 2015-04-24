package com.xiaoma.kefu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.WaitListDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.util.StringHelper;

/**
 * 等待菜单列表 业务处理类 *********************************
 * 
 * @Description: TODO
 * @author: wangxingfei
 * @createdAt: 2015年4月3日下午3:27:45
 ********************************** 
 */
@Service
public class WaitListService {

	@Autowired
	private WaitListDao waitListDaoImpl;

	/**
	 * 根据名称 模糊查询
	 * 
	 * @Description: TODO
	 * @param waitListName
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月3日
	 */
	public List<WaitList> findByNameLike(String waitListName) {
		return waitListDaoImpl.findByNameLike(waitListName);
	}

	/**
	 * 根据主键id查询
	 * 
	 * @Description: TODO
	 * @param id
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月14日
	 */
	public WaitList get(Integer id) {
		return waitListDaoImpl.findById(WaitList.class, id);
	}

	/**
	 * 查找风格下的 一级菜单
	 * 
	 * @Description: TODO
	 * @param styleId
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月15日
	 */
	public List<WaitList> getOneLev(Integer styleId) {
		return waitListDaoImpl.findOneLev(styleId);
	}

	/**
	 * 根据pid,查找二级菜单
	 * 
	 * @Description: TODO
	 * @param pId
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月15日
	 */
	public List<WaitList> getByPid(Integer pId) {
		return waitListDaoImpl.findByPid(pId);
	}

	/**
	 * * 校验名称是否存在 一级根据风格id校验, 二级根据pid校验 (如果pid为空或者=0, 则为一级菜单)
	 * 
	 * @Description: TODO
	 * @param waitList
	 * @return 0 表示OK
	 * @Author: wangxingfei
	 * @Date: 2015年4月16日
	 */
	public Integer validateName(WaitList waitList) {
		if (waitList == null)
			return 1;
		if (waitList.getpId() == null || waitList.getpId() == 0) {
			return waitListDaoImpl.validateName(waitList);
		} else {
			return waitListDaoImpl.validateName2(waitList);
		}
	}

	/**
	 * 校验数量是否超出限制
	 * 
	 * @Description: TODO
	 * @param waitList
	 * @return true 表示OK
	 * @Author: wangxingfei
	 * @Date: 2015年4月16日
	 */
	public boolean validateNum(WaitList waitList) {
		boolean flag = false;
		if (waitList == null)
			return flag;
		List<WaitList> list = new ArrayList<WaitList>();
		if (waitList.getpId() == null || waitList.getpId() == 0) {
			// 一级
			list = getOneLev(waitList.getStyleId());
			Integer maxSize = Integer.valueOf(DictMan.getDictItem(
					"d_sys_param", 3).getItemName());
			if (waitList.getId() != null) {// 更新操作
				maxSize = maxSize + 1;
			}
			if (list != null && list.size() < maxSize) {
				flag = true;
			}
		} else {
			// 二级
			list = getByPid(waitList.getpId());
			Integer maxSize = Integer.valueOf(DictMan.getDictItem(
					"d_sys_param", 4).getItemName());
			if (waitList.getId() != null) {// 更新操作
				maxSize = maxSize + 1;
			}
			if (list != null && list.size() < maxSize) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 创建
	 * 
	 * @param waitList
	 * @return
	 */
	public Integer create(WaitList waitList) {
		waitList.setCreateDate(new Date());
		return (Integer) waitListDaoImpl.add(waitList);
	}

	/**
	 * 删除 删除一级菜单时,将二级菜单也删除
	 * 
	 * @Description: TODO
	 * @param id
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月16日
	 */
	public int delete(Integer id) {
		WaitList waitList = get(id);
		int num = 0;
		if (waitList.getpId() != null && waitList.getpId() != 0) {
			num += waitListDaoImpl.deleteByPid(id);
		}
		num += waitListDaoImpl.delete(waitList);
		return num;
	}

	/**
	 * 更新
	 * 
	 * @Description: TODO
	 * @param toUpdate
	 * @return
	 * @Author: wangxingfei
	 * @Date: 2015年4月16日
	 */
	public int update(WaitList toUpdate) {
		return waitListDaoImpl.update(toUpdate);
	}
	/***
	 * 
	 * @param styleId,id
	 * @return
	 */
	public List<WaitList> findListById(String id) {
		String [] str ;
		if(StringHelper.isEmpty(id) || (str = id.split("_")).length<2){
			return null;
		}
		return waitListDaoImpl.findListById(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
	}

}
