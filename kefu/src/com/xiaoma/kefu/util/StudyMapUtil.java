package com.xiaoma.kefu.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudyMapUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K,V> convertList2Map(List list,Field key,Field value){
		Map<K,V> resultMap = new HashMap<K,V>();
		for(Object obj : list){
			try {
				
				key.setAccessible(true);
				value.setAccessible(true);
				key.get(obj);
				value.get(obj);
				
				resultMap.put((K)key.get(obj), (V)value.get(obj));
				
			} catch (Exception e) {
			} 
		}
		return resultMap;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K,V> convertList2Map(List<V> list,Field key){
		Map<K,V> resultMap = new HashMap<K,V>();
		for(Object obj : list){
			try {
				
				key.setAccessible(true);
				key.get(obj);
				
				resultMap.put((K)key.get(obj), (V)(obj));
				
			} catch (Exception e) {
			} 
		}
		return resultMap;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K> List<K> getValuesFromList(List list,Field key){
		
		List<K> valueList = new ArrayList<K>();
		for(Object obj : list){
			try {
				
				key.setAccessible(true);
				key.get(obj);
				
				valueList.add((K)key.get(obj));
				
			} catch (Exception e) {
			} 
		}
		return valueList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K> Set<K> getDistinctValueFromList(List list,Field key){
		
		Set<K> valueSet = new HashSet<K>();
		for(Object obj : list){
			try {
				
				key.setAccessible(true);
				key.get(obj);
				
				valueSet.add((K)key.get(obj));
				
			} catch (Exception e) {
			} 
		}
		return valueSet;
	}
	
//	public static void main(String[] args) {
//		Test test0 = new Test();
//		test0.setName("test0");
//		test0.setId(0);
//		
//		Test test1 = new Test();
//		test1.setName("test1");
//		test1.setId(1);
//		
//		Test test2 = new Test();
//		test2.setName("test2");
//		test2.setId(2);
//		
//		Test test3 = new Test();
//		test3.setName("test3");
//		test3.setId(3);
//		
//		List<Test> list = Arrays.asList(test0,test1,test2,test3);
//		
//		Map<Integer, String> m = new HashMap<Integer, String>();
//		try {
//			m = convertList2Map(list,Test.class.getDeclaredField("id"), Test.class.getDeclaredField("name"));
//		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(m.toString());
//		
//	}
}

//class Test{
//	private String name;
//	private Integer id;
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	
//	
//}
