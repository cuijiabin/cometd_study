package com.xiaoma.kefu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.BlacklistDao;
import com.xiaoma.kefu.model.Blacklist;
import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.util.PageBean;

/**
 * @author frongji
 * @time 2015年4月2日上午11:23:15
 *
 */
@Service
public class BlacklistService {
   
	  @Autowired
	  private BlacklistDao blacklistDaoImpl;
	  
		/**
		 * 条件查询
		 */
	   public PageBean<Blacklist> getResultByConditions(Integer currentPage,Integer pageRecorders,
			Long customerId,Integer userId,String description){
		
		Integer totalCount = blacklistDaoImpl.getAllBlacklistCount();
		PageBean<Blacklist> result = new PageBean<Blacklist>();
		
		result.setCurrentPage(currentPage);
		result.setPageRecorders(pageRecorders);
		result.setTotalRows(totalCount);
		
		Integer start = result.getStartRow();
		
		  List<Blacklist> list =blacklistDaoImpl.getBlacklistByConditions(start,pageRecorders,customerId ,userId,description);		
		  result.setObjList(list);
		
		return result;
	    }
	   /**
		 * 分页查询
		 * 
		 * @param map
		 * @param pageBean
		 * @return
		 */
		public void getResult(Map<String, String> conditions,PageBean<Blacklist> pageBean){
			blacklistDaoImpl.findByCondition(conditions,pageBean);

		 }

			/**
			 * 添加
			 */
	       public boolean createNewBlacklist(Blacklist blacklist){
	          return blacklistDaoImpl.createNewBlacklist(blacklist);
	       }
	      
	    
		   
		   /**
		    * 修改
		    */
		   public boolean updateBlacklist(Blacklist blacklist){
			  return blacklistDaoImpl.updateBlacklist(blacklist); 
		   }
		   
		    /**
			 * 删除
			 */
		public boolean deleteBlacklistById(Integer id){
			
			return blacklistDaoImpl.deleteBlacklistById(id);
		}
		
		/**
		 * 查询一条
		 */
			public Blacklist getBlacklistById(Integer id){
				return blacklistDaoImpl.getBlacklistByBlacklistId(id);
			}
			

}
