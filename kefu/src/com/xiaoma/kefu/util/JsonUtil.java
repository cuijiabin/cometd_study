package com.xiaoma.kefu.util;

import net.sf.json.JSONObject;

/**
 * 
 * @author cuijiabin
 *
 */
public class JsonUtil {

	/**
	 * 把对象转化成json
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		
		JSONObject jsonObj = JSONObject.fromObject(obj);
		
		return jsonObj.toString();
		
	}
	
	/**
	 * 把json转化成对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> Object getObjFromJson(String json,Class<T> clazz){
		JSONObject jsonObj = JSONObject.fromObject(json);
		
		Object obj =  JSONObject.toBean(jsonObj,clazz);
		
		return obj;
	}
}
