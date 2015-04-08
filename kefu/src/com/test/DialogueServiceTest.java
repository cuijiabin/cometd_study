package com.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
//		List<Dialogue> list = new ArrayList<Dialogue>();
//		for(int i=0;i<5;i++){
//			Dialogue model = new Dialogue();
//			model.setId((long) (i+1));
//			list.add(model);
//		}
		dialogueService.delete4Logic("1,2,4");
	}
	
//	@Test
	public void testRestore(){
//		List<Dialogue> list = new ArrayList<Dialogue>();
//		for(int i=0;i<5;i++){
//			Dialogue model = new Dialogue();
//			model.setId((long) (i+1));
//			list.add(model);
//		}
		dialogueService.restore("1,2,4");
	}
	
//	@Test
	public void testDelete(){
//		List<Dialogue> list = new ArrayList<Dialogue>();
//		for(int i=0;i<5;i++){
//			Dialogue model = new Dialogue();
//			model.setId((long) (i+3));
//			list.add(model);
//		}
		dialogueService.delete("1,2,4");
	}
	
	@Test
	public void testExp() throws Exception{
		dialogueService.writeExcelByTime();
	}
	
	public static void main(String[] args) throws Exception{
//		testExp();
		System.out.println(getFileName());
	}
	
	private static String getFileName() {
		Calendar c = Calendar.getInstance();  
        Date date = new Date();  
        c.setTime(date);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day - 1);  
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c  
                .getTime());  
        return dayBefore;  
	}
	
	
}
