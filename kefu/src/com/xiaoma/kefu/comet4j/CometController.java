package com.xiaoma.kefu.comet4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.comet4j.core.util.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "dialogue")
public class CometController {
	private static final CometContext context = CometContext.getInstance();
	
	
	
	private static final AppStore appStore = AppStore.getInstance();
	
	@RequestMapping(value = "talk.action", method = RequestMethod.POST)
	public void talk(HttpServletRequest request,HttpServletResponse response, String cmd) throws IOException{
	    CometEngine engine = context.getEngine();
	    
		if ("rename".equals(cmd)) {
			String id = request.getParameter("id");
			if (id == null)
				return;
			String newName = request.getParameter("newName");
			String oldName = request.getParameter("oldName");
			appStore.put(id, newName);
			RenameDTO dto = new RenameDTO(id, oldName, newName);
			engine.sendToAll("talker", dto);
			return;
		}

		if ("talk".equals(cmd)) {
			String id = request.getParameter("id");
			String name = appStore.get(id);
			String text = request.getParameter("text");
			TalkDTO dto = new TalkDTO(id, name, text);
			engine.sendToAll("talker", dto);
			return;
		}

		if ("list".equals(cmd)) {
			List<UserDTO> userList = new ArrayList<UserDTO>();
			Map<String, String> map = AppStore.getInstance().getMap();
			Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				String id = (String) entry.getKey();
				String name = (String) entry.getValue();
				userList.add(new UserDTO(id, name));
			}
			String json = JSONUtil.convertToJson(userList);
			response.getWriter().print(json);
		}
	}
}
