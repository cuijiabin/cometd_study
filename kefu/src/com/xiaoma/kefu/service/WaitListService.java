package com.xiaoma.kefu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.dao.WaitListDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
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
	private Logger logger = Logger.getLogger(WaitListService.class);
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
		List<WaitList> list;
		try {
			String [] str ;
			if(StringHelper.isEmpty(id) || (str = id.split("_")).length<2){
				return null;
			}
			list = waitListDaoImpl.findListById(
					Integer.parseInt(str[0]), Integer.parseInt(str[1]));
			if(list == null || list.size() < 12){
				List<WaitList> l = new ArrayList<WaitList>();
				int n =  Integer.parseInt(str[1])==0?12:9;
				int num = list == null?n:n-list.size();
				for(int i=0;i<num;i++){
					WaitList w = new WaitList();
					w.setId(99999);
					l.add(w);
				}
				list.addAll(l);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String [] args){
		List<DictItem> list = DictMan.getDictList("d_dialog_android");
		for (DictItem dictItem : list){  
			System.out.println(dictItem.getItemName());  
		} 
		Queue<String> q = new LinkedList<String>();
		q.offer("1");
		q.offer("2");
		q.offer("3");
		q.offer("4");
		q.offer("5");
		CacheMan.add("asdasd",1, q);
		Queue<String> qq = (Queue<String>)CacheMan.getObject("asdasd", 1,Queue.class);
		for (String string : qq){  
            System.out.println(string);  
        } 
		System.out.println("==========删除一个==========");
		String s = q.poll();
		System.out.println("==========取出的一个=========="+s);
		for (String string : q){  
			System.out.println(string);  
		} 
		CacheMan.update("asdasd",1, qq);
		qq = (Queue<String>)CacheMan.getObject("asdasd", 1,Queue.class);
		System.out.println("==========又删除一个==========");
		q.poll();
		for (String string : qq){  
			System.out.println(string);  
		} 
		System.out.println("==========增加一个1==========");
		q.offer("1");
		for (String string : q){  
			System.out.println(string);  
		} 
		System.out.println("==========删除一个5==========");
		for (String string : q){  
			System.out.println(string);  
		} 
	}

}
