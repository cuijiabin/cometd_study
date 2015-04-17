package com.xiaoma.kefu.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DictitemDao;
import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author 冯榕基
 * @time 2015年1月19日上午10:59:33
 * 
 */
@Service
public class DictitemService {
	private Logger logger = Logger.getLogger(DictitemService.class);
	@Autowired
	private DictitemDao dictitemDaoImpl;

	/**
	 * 查询所有
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public PageBean<DictItem> getResult(Integer currentPage,
			Integer pageRecorders, String code) {

		Integer totalCount = dictitemDaoImpl.getCountByCode(code);
		PageBean<DictItem> result = new PageBean<DictItem>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<DictItem> list = dictitemDaoImpl.getDictitemByCode(start,
				pageRecorders, code);
		result.setObjList(list);

		return result;
	}

	/**
	 * 查询所有
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public List<DictItem> getAll(String code) {
		List<DictItem> list = dictitemDaoImpl.getDictitemByCode(0, 10000, code);
		return list;
	}

	/**
	 * 在弹出的对话框显示详细信息
	 */
	public DictItem getDictitemById(Integer id) {
		return dictitemDaoImpl.findById(DictItem.class, id);
	}

	/**
	 * 添加
	 */
	public Integer add(DictItem dictitem) {
		try {
			return (Integer) dictitemDaoImpl.add(dictitem);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 修改
	 */
	public Integer update(DictItem dictItem) {
		try {
			int flag = dictitemDaoImpl.update(dictItem);
			DictMan.clearTableCache(dictItem.getCode());
			DictMan.clearItemCache(dictItem.getCode(), dictItem.getItemCode());
			return flag;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;

	}

	/**
	 * 删除
	 */
	public Integer delete(DictItem dictItem) {
		try {
			int flag = dictitemDaoImpl.delete(dictItem);
			DictMan.clearTableCache(dictItem.getCode());
			DictMan.clearItemCache(dictItem.getCode(), dictItem.getItemCode());
			return flag;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 根据dictCode获取二级字典条数
	 * 
	 * @param dictCode
	 * @return
	 */
	public Integer getCountByCode(String dictCode) {

		return dictitemDaoImpl.getCountByCode(dictCode);
	}

	/**
	 * 根据code与itemCode 查询id列表
	 * 
	 * @param code
	 * @param itemCode
	 * @return
	 */
	public List<Integer> getIdsByCodeAndItemCode(String code, String itemCode) {

		return dictitemDaoImpl.getIdsByCodeAndItemCode(code, itemCode);
	}
}
