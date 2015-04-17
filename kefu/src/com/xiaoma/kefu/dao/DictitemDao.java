package com.xiaoma.kefu.dao;

import java.util.List;

import com.xiaoma.kefu.model.Dict;
import com.xiaoma.kefu.model.DictItem;



/**
 * @author 冯榕基
 * @time 2015年1月19日上午11:00:46
 *
 */
public interface DictitemDao extends BaseDao<DictItem>{

	/**
	 * 查询所有
	 * @return
	 */
	public Integer getAllDictitemCount();

	public List<DictItem> getDictitemByCode(Integer start, Integer offset,String code);
	
    /**
	 * 根据dictCode获取二级字典条数
	 * @param dictCode
	 * @return
	 */
    public Integer getCountByCode(String dictCode);
    
    /**
	 * 根据code与itemCode 查询id列表
	 * 
	 * @param code
	 * @param itemCode
	 * @return
	 */
    public List<Integer> getIdsByCodeAndItemCode(String code,String itemCode);

}
