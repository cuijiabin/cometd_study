package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.Dialogue;
import com.xiaoma.kefu.service.DialogueService;

/**
 * 对话信息测试
 * *********************************
* @Description: TODO
* @author: wangxingfei
* @createdAt: 2015年4月3日下午3:45:02
**********************************
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class DialogueServiceTest {
	@Autowired
	private DialogueService dialogueService;
	
//	@Test
	public void testDelete4Logic(){
		List<Dialogue> list = new ArrayList<Dialogue>();
		for(int i=0;i<5;i++){
			Dialogue model = new Dialogue();
			model.setId((long) (i+1));
			list.add(model);
		}
		dialogueService.delete4Logic(list);
	}
	
//	@Test
	public void testRestore(){
		List<Dialogue> list = new ArrayList<Dialogue>();
		for(int i=0;i<5;i++){
			Dialogue model = new Dialogue();
			model.setId((long) (i+1));
			list.add(model);
		}
		dialogueService.restore(list);
	}
	
	@Test
	public void testDelete(){
		List<Dialogue> list = new ArrayList<Dialogue>();
		for(int i=0;i<5;i++){
			Dialogue model = new Dialogue();
			model.setId((long) (i+3));
			list.add(model);
		}
		dialogueService.delete(list);
	}
	
	
}