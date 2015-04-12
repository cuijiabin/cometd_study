<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 查询条件 -->
<h3 class="u-tit c-bg">配置显示字段</h3>
<table class="table table-bordered table-striped table-hover m-table">
	<thead>
		<tr>
			<td style="width:30%; text-align: center;"><a href="#" onClick="toDef()">系统默认勾选</a> </td>
			<td>提示：最多可以最多勾选9项</td>
		</tr>
	</thead>
</table>

<div class="m-query f-mar10">
	<c:forEach var="crf" items="${list }" varStatus="status">
		<c:if test="${status.index % 5 == 0 }"> <br> </c:if>  
		<label><input type="checkbox" name="items" value="${crf.isDefault} ">${crf.name }
			<input type="hidden" name="codes" value="${crf.code} ">
		</label>
	</c:forEach>
	<br>
	<br>
	<button type="submit" class="btn btn-primary" id="btn_save">保存<i class="icon-ok icon-white"></i></button>
	<button type="reset" class="btn" id="btn_cancel">取消</button>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">

//默认勾选
function toDef(){
	$("input[name='items']").each(function(){
		if($(this).val()==1){
			this.checked = true;
		}else{
            this.checked = false;
		}
    });
}

//保存
$('#btn_save').on('click',function(){
	var items = $("input[name='items']");
	var codes = $("input[name='codes']");
	var str = '';
	for (var i=0; i<items.length; i++){
		var isCheck = 0 ;
		if(items[i].checked){
			isCheck = 1;
		};
		if(str!=''){
			str+=","
		}
		str+=codes[i].value+":"+isCheck;
	}
	$.ajax({
		type : 'post',
		url :  "/charRecordField/xx.action",
		dataType : 'json',
		data : str,
		async: false,
	 	success: function (data) {
	    	if(data.result==0){
	    		alert(11)
	    	}else{
	    		$.dialog.alert(data.msg);
	    	}
	    },
	    error: function (msg) {
	    	$.dialog.alert(msg);
	    }
	});
});


</script>
</body>
</html>
