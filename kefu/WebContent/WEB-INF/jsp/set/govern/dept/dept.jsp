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
        <li><i>&gt;</i>部门管理</li>
    </ul>
</div>

<!-- 表格有边框 -->
<div style="margin:50px">
<div style="width: 100%;height: 30px"><button style="float:right;margin-right:5px;" onclick="javascript:addDept()" class="btn btn-primary btn-small" >添加部门</button></div>
<div id="table_data" style="margin-top: 3px">
	<jsp:include page="deptList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
var api = frameElement.api,W=api.opener;
function find(currentPage){
	var url="/dept/list.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[typeId]":1,
			"map[id]":1
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
	    	W.$.dialog.alert("出现错误,请重试!");
	    }
	});
}

function addDept(){
	var d = $.dialog({id:'dept',title:"添加部门",content:'url:/dept/addDept.action',lock:true, width:	400,height: 240,});
}
function updateDept(id){

	var d = $.dialog({id:'dept',title:"修改部门",content:'url:/dept/detail.action?id='+id+'',lock:true, width: 

		400,height: 240,});

}
function findUser(id){

	var d = $.dialog({id:'dept',title:"查看部门成员",content:'url:/dept/findDeptUser.action?deptId='+id+'',lock:true, width: 

		900,height: 500,});

}
function deleteDept(id){
	if(confirm('确实要删除吗?')){
	var url="/dept/delete.action";
	$.ajax({
		type : "get",
		url : url,
		data : {"id":id},
		dataType : "json",
		success : function(data) {
			W.$.dialog.alert(data.msg);
			location.reload();
		},
		error : function(msg) {
			W.$.dialog.alert("出现错误,请重试!");
		}
	});
	}
}
function callback(){
	$.dialog({id:'dept'}).close();
	find();
}
</script>
</body>
</html>
