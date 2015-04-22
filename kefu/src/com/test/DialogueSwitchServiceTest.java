package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.model.DialogueSwitch;
import com.xiaoma.kefu.service.DialogueSwitchService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class DialogueSwitchServiceTest {

	@Autowired
	private DialogueSwitchService dialogueSwitchService;
	
	@Test
	public void crudTest(){
		DialogueSwitch dialogueSwitch = new DialogueSwitch();
		
		dialogueSwitch.setFromUserId(1);
		dialogueSwitch.setToUserId(2);
		dialogueSwitch.setRemark("我下班了！");
		
		Integer id = dialogueSwitchService.addDialogueSwitch(dialogueSwitch);
		
		System.out.println("插入结果："+id);
	}
}
