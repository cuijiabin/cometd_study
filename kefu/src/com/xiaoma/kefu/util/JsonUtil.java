package com.xiaoma.kefu.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @author cuijiabin
 *
 */
public class JsonUtil {

	/**
	 * 把对象转化成json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {

		JSONObject jsonObj = JSONObject.fromObject(obj);

		return jsonObj.toString();

	}

	/**
	 * 把json转化成对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> Object getObjFromJson(String json, Class<T> clazz) {
		JSONObject jsonObj = JSONObject.fromObject(json);

		Object obj = JSONObject.toBean(jsonObj, clazz);

		return obj;
	}

	public static List<Long> convertSerializable2Long(List<Serializable> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Long> result = new ArrayList<Long>();
		for (Serializable ser : list) {
			result.add((Long) ser);
		}

		return result;
	}
	
	public static List<Serializable> convertLong2Serializable(List<Long> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Serializable> result = new ArrayList<Serializable>();
		for (Long i : list) {
			result.add(i);
		}
		return result;
	}
	
	public static List<Integer> convertSerializable2Integer(List<Serializable> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Integer> result = new ArrayList<Integer>();
		for (Serializable ser : list) {
			result.add((Integer) ser);
		}

		return result;
	}
	
	public static List<Serializable> convertInteger2Serializable(List<Integer> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Serializable> result = new ArrayList<Serializable>();
		for (Integer i : list) {
			result.add(i);
		}
		return result;
	}
	
}