package com.xiaoma.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.util.StringHelper;

public class DictValueTag extends BodyTagSupport {

	private PageContext pageContext;
	private Tag tag;
	private String name;
	private String value;

	/**
	 * 标签初始化功能。
	 */
	public int doEndTag() throws JspException {
		try {
			if(StringHelper.isNotEmpty(name) && StringHelper.isNotEmpty(value)){
				DictItem d = DictMan.getDictItem(name, value);
				if(d != null)
					pageContext.getOut().print(d.getItemName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Tag.EVAL_PAGE;
	}

	/**
	 * 标签开始
	 */
	public int doStartTag() throws JspException {
		return Tag.SKIP_BODY;
	}

	public Tag getParent() {

		return null;
	}

	public void release() {

	}

	public void setPageContext(PageContext arg0) {
		this.pageContext = arg0;

	}

	public void setParent(Tag arg0) {
		this.tag = arg0;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
