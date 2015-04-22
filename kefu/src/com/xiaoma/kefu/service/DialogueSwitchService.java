package com.xiaoma.kefu.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoma.kefu.dao.DialogueSwitchDao;
import com.xiaoma.kefu.model.DialogueSwitch;

@Service("dialogueSwitchService")
public class DialogueSwitchService {

	@Resource()
	private DialogueSwitchDao dialogueSwitchDao;

	public Integer addDialogueSwitch(DialogueSwitch dialogueSwitch) {
		if (dialogueSwitch == null) {
			return -1;
		}
		dialogueSwitch.setCreateDate(new Date());
		Integer id = (Integer) dialogueSwitchDao.add(dialogueSwitch);
		return id;
	}
}
