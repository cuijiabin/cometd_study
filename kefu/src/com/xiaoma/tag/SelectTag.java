package com.xiaoma.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.xiaoma.kefu.dict.DictMan;
import com.xiaoma.kefu.model.DictItem;
import com.xiaoma.kefu.util.StringHelper;

public class SelectTag extends BodyTagSupport {

	private PageContext pageContext;
	private Tag tag;
	private String name;
	private String dictName;
	private String all;
	private String allName;
	private String value;
	private String changeName;
	
	/**
	 * 标签初始化功能。
	 */
	public int doEndTag() throws JspException {
		try {
			StringBuffer tags= new StringBuffer();
			 //如果name和dictName为空，则什么也不输出
			if(StringHelper.isNotEmpty(name) || StringHelper.isNotEmpty(dictName)){
				//如果typeId为空或者为1，即为字典表获取
				List<DictItem> list = DictMan.getDictList(dictName);
				tags.append("<select name='").append(name).append("' id='").append(name).append("' class='c-wdat' ");
				if(StringHelper.isNotEmpty(changeName))
					tags.append("onchange='").append(changeName).append("' ");
				tags.append(">");
				if(StringHelper.isNotEmpty(all))
					tags.append("<option value=''>").append(allName).append("</option>");
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						DictItem d = list.get(i);
						if(StringHelper.isNotEmpty(value) && StringHelper.isNotEmpty(d.getItemCode())&&value.equals(d.getItemCode()))
							tags.append("<option selected value='").append(d.getItemCode()).append("'>").append(d.getItemName()).append("</option>");
						else
							tags.append("<option value='").append(d.getItemCode()).append("'>").append(d.getItemName()).append("</option>");
					}
				}
				tags.append("</select>");
			}
			pageContext.getOut().print(tags);

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

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

}
