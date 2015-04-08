package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.service.MessageRecordsService;

/**
 * 留言信息测试
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月8日上午11:12:28
**********************************
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class MessageRecordsServiceTest {
	@Autowired
	private MessageRecordsService messageRecordsService;
	
//	@Test
	public void testDelete4Logic(){
		messageRecordsService.delete4Logic("1,2,4");
	}
	
	@Test
	public void testRestore(){
		messageRecordsService.restore("1,2,4");
	}
	
}
