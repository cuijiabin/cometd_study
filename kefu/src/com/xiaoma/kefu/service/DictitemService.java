package com.xiaoma.kefu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DictitemDao;
import com.xiaoma.kefu.model.Dictitem;
import com.xiaoma.kefu.util.PageBean;



/**
 * @author 冯榕基
 * @time 2015年1月19日上午10:59:33
 *
 */
@Service
public class DictitemService {

	@Autowired
	private DictitemDao dictitemDaoImpl;

	/**
	 * 查询所有
	 * 
	 * @param map
	 * @param pageBean
	 * @return
	 */
	public PageBean<Dictitem> getResult(Integer currentPage,Integer pageRecorders, String code) {

		Integer totalCount = dictitemDaoImpl.getCountByCode(code);
		PageBean<Dictitem> result = new PageBean<Dictitem>();

		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);

		Integer start = result.getStartRow();

		List<Dictitem> list = dictitemDaoImpl.getDictitemByCode(start, pageRecorders, code);
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
	public List<Dictitem> getAll(String code) {
		List<Dictitem> list = dictitemDaoImpl.getDictitemByCode(0, 10000, code);
		return list;
	}

	/**
	 * 在弹出的对话框显示详细信息
	 */
	public Dictitem getDictitemById(Integer id) {
		return dictitemDaoImpl.getDictitemByDictitemId(id);
	}

	/**
	 * 添加
	 */
	public boolean createNewDictitem(Dictitem dictitem) {
		return dictitemDaoImpl.createNewDictitem(dictitem);
	}

	/**
	 * 修改
	 */
	public boolean updateDictitem(Dictitem dictitem) {
		return dictitemDaoImpl.updateDictitem(dictitem);
	}

	/**
	 * 删除
	 */
	public boolean deleteDictitemById(Integer id) {
		return dictitemDaoImpl.deleteDictitemById(id);
	}
	
	/**
	 * 根据dictCode获取二级字典条数
	 * @param dictCode
	 * @return
	 */
	public Integer getCountByCode(String dictCode){
		
		return dictitemDaoImpl.getCountByCode(dictCode);
	}
	
	/**
	 * 根据code与itemCode 查询id列表
	 * 
	 * @param code
	 * @param itemCode
	 * @return
	 */
	public List<Integer> getIdsByCodeAndItemCode(String code,String itemCode){
		
		return dictitemDaoImpl.getIdsByCodeAndItemCode(code, itemCode);
	}
}
