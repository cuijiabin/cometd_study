<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/WEB-INF/xiaoma.tld" prefix="xiaoma" %>
<!-- 翻页 -->
<div id="kkpager" >每页
	<xiaoma:select name="pageRecorders" dictName="d_pagesize" value="${pageBean.pageRecorders}" changeName="find(1)" />
	条
</div>
<script type="text/javascript">
$(function(){
	var totalPage = '${pageBean.totalPages}';
	var totalRecords = '${pageBean.totalRows}';
	var pageNo = '${pageBean.currentPage}';
	if(!pageNo){
		pageNo = 1;
	}
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		mode: 'click',
		pno : pageNo,
		//总页码
		total : totalPage,
		//总数据条数
		totalRecords : totalRecords,
		//链接前部
		hrefFormer : '',
		//链接尾部
		hrefLatter : '',
		//getLink : function(n){
		//	return this.hrefFormer + this.hrefLatter + "?pno="+n;
		//},
		lang : {
			firstPageText : '|<',
			lastPageText : '>|',
			prePageText : '<',
			nextPageText : '>',
			totalPageBeforeText : '共',
			totalPageAfterText : '页',
			totalRecordsAfterText : '条数据',
			gopageBeforeText : '转到',
			gopageButtonOkText : '确定',
			gopageAfterText : '页',
			buttonTipBeforeText : '第',
			buttonTipAfterText : '页'
		},
		//,
		//mode : 'click',//默认值是link，可选link或者click
		click : function(n){
			find(n);
		  	return false;
		}
	});
});
</script>