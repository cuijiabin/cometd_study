package com.xiaoma.tag;

import java.io.IOException;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class SelectTag extends BodyTagSupport {
	// 自定义标签支持带参数
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag() {
		try {
			pageContext.getOut().print("Hello " + name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		return EVAL_BODY_INCLUDE;
	}
}