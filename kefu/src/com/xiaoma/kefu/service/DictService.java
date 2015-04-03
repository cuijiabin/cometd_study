package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DictDao;
import com.xiaoma.kefu.model.Dict;
import com.xiaoma.kefu.util.PageBean;



/**
 * @author 冯榕基
 * @time 2015年1月13日下午3:26:29
 *
 */
@Service
public class DictService {

	@Autowired
	private DictDao dictDaoImpl;

	/**
	 * 查询所有
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public PageBean<Dict> getResult(Integer currentPage, Integer pageRecorders) {

		Integer totalCount = dictDaoImpl.getAllDictCount();
		PageBean<Dict> result = new PageBean<Dict>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<Dict> list = dictDaoImpl.getDictOrderById(start, pageRecorders);
		result.setObjList(list);

		return result;
	}

	/**
	 * 条件查询
	 */
	public PageBean<Dict> getResultByInfo(Integer currentPage, Integer pageRecorders, String code, String name) {

		Integer totalCount = dictDaoImpl.getCountByCodeAndName(code, name);
		PageBean<Dict> result = new PageBean<Dict>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<Dict> list = dictDaoImpl.getDictByCodeAndName(start,
				pageRecorders, code, name);

		result.setObjList(list);

		return result;
	}

	/**
	 * 删除
	 */
	public boolean deleteDictById(String id) {
		return dictDaoImpl.deleteDictById(id);
	}

	/**
	 * 添加
	 */
	public boolean createNewDict(Dict dict) {
		return dictDaoImpl.createNewDict(dict);
	}

	/**
	 * 修改
	 */
	public boolean updateDict(Dict dict) {
		return dictDaoImpl.updateDict(dict);
	}

	/**
	 * 在弹出的对话框显示详细信息
	 */
	public Dict getDictById(String id) {
		return dictDaoImpl.getDictByDictId(id);
	}

}
