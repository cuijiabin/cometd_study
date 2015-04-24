<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">管理设置</a></li>
        <li><i>&gt;</i>角色管理</li>
    </ul>
</div>

<!-- 表格有边框 -->
<div style="margin:50px">

<div><button style="float:right;margin:5px;" onclick="javascript:addRole()" class="btn btn-primary btn-small" >添加角色</button></div>
<div id="table_data" style="margin-top: 10px">
	<jsp:include page="roleList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
function find(currentPage){
	var url="/role/list.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[typeId]":1
	};
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    dataType: "html",
	    success: function (data) {
	       $("#table_data").html(data);
	    },
	    error: function (msg) {
	    	alert("出现错误,请重试!");
	    }
	});
}

function addRole(){
	var d = $.dialog({id:'role',title:"添加角色",content:'url:/role/addRole.action',lock:true, width:	370,height: 210,});
}
function updateRole(id){

	var d = $.dialog({id:'role',title:"修改角色",content:'url:/role/detail.action?id='+id+'',lock:true, width: 

		370,height: 210,});

}
//javascript:if(confirm('确实要删除吗?'))location='/role/delete.action?id=${role.id}
function deleteRole(id){
	if(confirm('确实要删除吗?')){
	var url="/role/delete.action";
	$.ajax({
		type : "post",
		url : url,
		data : {"id":id},
		dataType : "json",
		success : function(data) {
			alert(data.msg);
			location.reload();
		},
		error : function(msg) {
			alert("出现错误,请重试!");
		}
	});
	}
}
function callback(){
	$.dialog({id:'role'}).close();
	find();
}
</script>
</body>
</html>
