package com.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.ChatRecordField;
import com.xiaoma.kefu.service.ChatRecordFieldService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class ChatRecordFieldServiceTest {
	@Autowired
	private ChatRecordFieldService chatRecordFieldService;//结果展示字段
	
	@Test
	public void testUpdateList(){
		List<ChatRecordField> list = new ArrayList<ChatRecordField>();
		ChatRecordField crf = new ChatRecordField();
		crf.setId(13);
		crf.setCode("deviceType");
		crf.setName("设备");
		crf.setIsDisplay(1);
		list.add(crf);
		chatRecordFieldService.updateIsDisplay(list);
		crf = chatRecordFieldService.findById(13);
		System.out.println(crf);
	}
	
	@Test
	public void testFindAll(){
		List<ChatRecordField> list = chatRecordFieldService.findAll();
		System.out.println(list.size());
		Assert.assertEquals(list.size(), 25);
	}
	
}
