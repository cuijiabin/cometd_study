package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.comet4j.core.util.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoma.kefu.redis.JedisDao;


@Controller
@RequestMapping(value = "dialogue")
public class CometController {
	private static final CometContext context = CometContext.getInstance();
	
	@RequestMapping(value = "talk.action", method = RequestMethod.POST)
	public void talk(HttpServletRequest request,HttpServletResponse response, String cmd) throws IOException{
	    CometEngine engine = context.getEngine();
	    

		if ("talk".equals(cmd)) {
			String id = request.getParameter("id");
			String name = JedisDao.getJedis().get("1###"+id);
			if(StringUtils.isNotBlank(name)){
				name = JedisDao.getJedis().get("2###"+id);
			}
			
			String text = request.getParameter("text");
			TalkDTO dto = new TalkDTO(id, name, text);
			
			String cid = request.getParameter("cid");//engine.getConnectionId(request);
			System.out.println(cid);
			
			CometConnection ccn = engine.getConnection(id);
			
			engine.sendTo("talker", ccn, dto);
			return;
		}

	}
}
