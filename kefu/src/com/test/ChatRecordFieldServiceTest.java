package com.test;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.service.ChatRecordFieldService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class ChatRecordFieldServiceTest {
	@Autowired
	private ChatRecordFieldService chatRecordFieldService;//结果展示字段
	
//	@Test
	public void testSaveRecord(){
		String date = "customerId:1,cardName:0";
		chatRecordFieldService.saveRecord(1, date);
		
		chatRecordFieldService.saveRecord(2, date);
	}
	
	@Test
	public void testFindByUserId(){
		Map<String,String> recordFieldMap = null;
		recordFieldMap = chatRecordFieldService.findDisplayMapByUserId(3);
		Assert.assertEquals(recordFieldMap.size(), 0);
	}
}
