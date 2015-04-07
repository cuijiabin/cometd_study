package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Dict;



/**
 * @author 冯榕基
 * @time 2015年1月13日下午5:31:32
 *
 */
public interface DictDao extends BaseDao<Dict>{
	
   /**
    * 获取全部字典表数据
    * @return
    */
	public Integer getAllDictCount();

	public List<Dict> getDictOrderById(Integer start, Integer offset);
	/**
	 * 添加一条
	 * @param dict
	 * @return
	 */


	boolean createNewDict(Dict dict);

	/**
	 * 修改
	 * @param dict
	 * @return
	 */
	boolean updateDict(Dict dict);
    /**
     * 删除
     * @param dict
     * @return
     */

	boolean deleteDictById(String id);
   /**
    * 根据id查询一条
    * @param id
    * @return
    */
	public Dict getDictByDictId(String id);
    /**
     * 条件查询 
     * @return
     */
    public List<Dict> getDictByCodeAndName(Integer start, Integer offset, String code, String name);
    
    public Integer getCountByCodeAndName(String code, String name);

	
	
}
