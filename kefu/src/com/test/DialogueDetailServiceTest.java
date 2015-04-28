package com.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.DialogueDetail;
import com.xiaoma.kefu.service.BusiGroupDetailService;
import com.xiaoma.kefu.service.DialogueDetailService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class DialogueDetailServiceTest {

	@Autowired
	private DialogueDetailService dialogueDetailService;
	
	@Autowired
	private BusiGroupDetailService busiGroupDetailService;
	
	@Test
	public void batchAddTest() throws Exception{
		
		DialogueDetail d1 = new DialogueDetail(); 
		d1.setContent("吃饭");
		DialogueDetail d2 = new DialogueDetail(); 
		d2.setContent("吃饭");
		DialogueDetail d3 = new DialogueDetail(); 
		d3.setContent("吃饭");
		
		List<DialogueDetail> list = Arrays.asList(d1,d2,d3);
		dialogueDetailService.batchAdd(list);
		List<Long> ids = Arrays.asList(23L,24L,25L,26L,27L);
		dialogueDetailService.batchDelete(ids);
	}
	
	@Test
	public void batchDelTest() throws Exception{
		List<Long> ids = Arrays.asList(23L,24L,25L,26L,27L);
		dialogueDetailService.batchDelete(ids);
		
	}
	
	@Test
	public void getLastRecordsByCustomerId() throws Exception{
		
		List<DialogueDetail> list = dialogueDetailService.getLastRecordsByCustomerId(100000030L);
		
		System.out.println(list.toString());
	}
	
	@Test
	public void testBusiGroupDetailService() throws Exception{
		
		List<Integer> list = busiGroupDetailService.findUserIdsByStyleId(1);
		for(Integer userId : list){
			System.out.println(userId);
		}
		
		
	}
	
}
