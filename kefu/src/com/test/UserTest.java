package com.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoma.kefu.dao.UserDao;
import com.xiaoma.kefu.model.User;
import com.xiaoma.kefu.redis.JedisTalkDao;
import com.xiaoma.kefu.service.UserService;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="file:src/application-context.xml")  
public class UserTest {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDaoImpl;
	@Test
	public void test(){
		
		List<User> users = userService.getAll();
		for(User user : users){
			System.out.println(user.getCardName());
			if(user.getMaxListen() != null){
				JedisTalkDao.setMaxReceiveCount(user.getId().toString(), user.getMaxListen());
			}
		}
	}
}
