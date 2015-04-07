package com.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.WaitList;
import com.xiaoma.kefu.service.WaitListService;

/**
 * 等待菜单列表	测试
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午3:45:02
**********************************
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class WaitListServiceTest {
	@Autowired
	private WaitListService waitListService;
	
	@Test
	public void testFindLike(){
		List<WaitList> list = waitListService.findByNameLike("托福");
		System.out.println(list.size());
		Assert.assertEquals(list.size(), 2);
	}
	
	
}
