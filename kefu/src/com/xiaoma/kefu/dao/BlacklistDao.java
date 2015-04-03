package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Blacklist;

/**
 * @author frongji
 * @time 2015年4月2日下午8:50:34
 *  
 */
public interface BlacklistDao {
    
	/**
	 * 添加一条
	 * @param blacklist
	 * @return
	 */
	public boolean createNewBlacklist(Blacklist blacklist);
    
	/**
	 * 查询所有的条数
	 * @return
	 */
	public Integer getAllBlacklistCount();
    /**
     * 查询所有
     * @param start
     * @param offset
     * @return
     */
	public List<Blacklist> getBlacklistOrderById(Integer start, Integer offset);
   
	/**
	 * 条件查询
	 * @param start
	 * @param offset
	 * @param customerId
	 * @param userId
	 * @param description
	 * @return
	 */
	public List<Blacklist> getBlacklistByConditions(Integer start,
			Integer offset, Long customerId, Integer userId, String description);
    /**
     * 查询一条
     * @param id
     * @return
     */
	public Blacklist getBlacklistByBlacklistId(Integer id);
    
	/**
	 * 修改一条
	 * @param Blacklist
	 * @return
	 */
	public boolean updateBlacklist(Blacklist Blacklist);
    /**
     * 删除
     * @param id
     * @return
     */
	public boolean deleteBlacklistById(Integer id);

}
