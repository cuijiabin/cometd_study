/*
 * Created on 2006-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xiaoma.kefu.dict;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoma.kefu.cache.CacheMan;
import com.xiaoma.kefu.cache.CacheName;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.util.StringHelper;
import com.xiaoma.kefu.util.database.DataBase;
import com.xiaoma.kefu.util.database.DataSet;

/**
 * @author Administrator TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class DictMan {
	private static Log log = LogFactory.getLog(DictMan.class);
	public static String CahceNameList = "DictionaryCacheList";
	public static String CahceNameItem = "DictionaryCacheItem";
	public static void clearTableCache(String table) {
		table = table.toLowerCase();
		CacheMan.remove(CahceNameList, table);
	}
	public static void clearItemCache(String table, String key) {
		table = table.toLowerCase();
		CacheMan.remove(CahceNameItem + table, key);
	}
	public static void addItemCache(String cacheName,String table, Object obj) {
		table = table.toLowerCase();
		CacheMan.add(cacheName, table, obj);
	}
	/***
	 * 获取字典表list
	 * @param cacheName
	 * @param table
	 * @return
	 */
	public static List<DictItem> getDictList(String cacheName, String table) {
		table = table.toLowerCase();
		try {
			DataSet ds = DataBase.Query("select * from dict_item d where d.code='"+table +"' order by d.itemCode");
			List<DictItem> list = new ArrayList<DictItem>((int) ds.RowCount);
			for(int i=0;i<ds.RowCount;i++){
				DictItem d = new DictItem();
				d.setId(ds.getRow(i).getInt("id"));
				d.setCode(ds.getRow(i).getString("code"));
				d.setItemCode(ds.getRow(i).getString("itemCode"));
				d.setItemName(ds.getRow(i).getString("itemName"));
				d.setDescription(ds.getRow(i).getString("description"));
				addItemCache(CacheName.DICTITEM,table+d.getItemCode(), d);
				list.add(d);
			}
			addItemCache(CacheName.DICTLIST,table, list);
			return list;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return null;
		} 
	}
	public static List<DictItem> getDictList(String table) {
		if (StringHelper.isEmpty(table))
			return null;
		table = table.toLowerCase();
		try {
			List<DictItem> list = (List<DictItem>)CacheMan.getObject(CacheName.DICTLIST, table);
			if(list == null)
				list = getDictList(CacheName.DICTLIST,table);
			return list;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return null;
		}
	}
	public static List getDictItemId(String table) {
		List<DictItem> lst = DictMan.getDictList(table);
		List lstId = new ArrayList();
		for (DictItem dt : lst)
			lstId.add(dt.getId());
		return lstId;
	}
	public static List getDictListQuery(String table) {
		List dict = getDictList(table);
		List lstRes = new ArrayList();
		DictItem DictItem = new DictItem();
		lstRes.add(DictItem);
		lstRes.addAll(dict);
		return lstRes;
	}
	public static List getDictListSel(String table) {
		List dict = getDictList(table);
		List lstRes = new ArrayList();
		DictItem DictItem = new DictItem();
		DictItem.setItemName("请选择");
		DictItem.setDescription("请选择");
		lstRes.add(DictItem);
		lstRes.addAll(dict);
		return lstRes;
	}
	public static DictItem getDictItem(String table, int iValue) {
		table = table.toLowerCase();
		return getDictItem(table, String.valueOf(iValue));
	}
	public static DictItem getDictItem(String table, String value) {
		table = table.toLowerCase();
		DictItem dt = null;
		if (value != null) {
			dt = (DictItem) CacheMan.getObject(CacheName.DICTITEM , table+String.valueOf(value));
			if (dt == null) {
				getDictList(table);
				dt = (DictItem) CacheMan.getObject(CacheName.DICTITEM , table+String.valueOf(value));
			}
		}
		if (dt == null)
			dt = new DictItem();
		return dt;
	}
	public static void main(String[] args) {
		try {
			List l = DictMan.getDictList("d_sex");
			DictItem dt = DictMan.getDictItem("d_sex", "1");
			System.out.println(dt.getDescription());
			dt = DictMan.getDictItem("d_sex", "2");
			System.out.println(dt.getDescription());
			dt = DictMan.getDictItem("d_sex", "3");
			System.out.println(dt.getDescription());
			dt = DictMan.getDictItem("d_user_level", "1");
			System.out.println(dt.getDescription());
			System.out.println(dt);
		} catch (Exception e) {
			return;
		}
	}
}
