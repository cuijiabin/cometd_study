package com.xiaoma.kefu.dao;

import java.util.Map;

import com.xiaoma.kefu.model.Message;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月7日下午5:18:15
 *
 */
public interface MessageDao extends BaseDao<Message> {
 

    /**
     * 查询、条件查询
     * @param conditions
     * @param pageBean
     * @param id
     */
	public void findByCondition(Map<String, String> conditions,
			PageBean<Message> pageBean, Integer id);
    /**
     * 查询最大的id值
     * @return
     */
	public  Integer getMaxId();
	/**
	 * 根据ID查询一条
	 * @param id
	 * @return
	 */
	public Message getMessageById(Integer id);
	/**
	 * 添加一条
	 * @param message
	 * @return
	 */
 	public boolean createNewMessage(Message message);
 	/**
 	 * 修改一条
 	 * @param message
 	 * @return
 	 */
	public boolean updateMessage(Message message);
	/**
	 * 根据id删除一条
	 * @param id
	 * @return
	 */
	public boolean deleteMessageById(Integer id);

}
