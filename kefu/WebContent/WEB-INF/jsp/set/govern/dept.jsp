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
<button style="float:right;margin-right:5px;" onclick="javascript:addDept()" class="btn" >添加部门</button>
<div id="table_data" style="margin-top: 30px">
	<jsp:include page="deptList.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
function find(currentPage){
	var url="/dept/list.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
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
	        alert(msg);
	    }
	});
}

function addDept(){
	var d = $.dialog({content:'url:/dept/addDept.action',lock:true, width:	500,height: 300,});
}
function updateDept(id){

	var d = $.dialog({content:'url:/dept/detail.action?id='+id+'',lock:true, width: 

		500,height: 300,});

}
function findUser(id){

	var d = $.dialog({content:'url:/dept/findDeptUser.action?deptId='+id+'',lock:true, width: 

		600,height: 600,});

}

</script>
</body>
</html>