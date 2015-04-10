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
<!-- <div class="m-crumb"> -->
<!--     <ul class="f-cb"> -->
<!--         <li><b>位置：</b></li> -->
<!--         <li><a href="#">设置中心</a></li> -->
<!--         <li><i>&gt;</i><a href="#">管理设置</a></li> -->
<!--         <li><i>&gt;</i>角色管理</li> -->
<!--     </ul> -->
<!-- </div> -->
<!-- 表格有边框 -->
<h2>添加角色</h2>
<form action="/role/add.action" method="post" id="form">
<table  border="1" aglin="centert" class="table">
        <tr>
           <td>角色名称</td>
           <td><input type ="text" id ="name" name="name"/><td>
        </tr>
</table>
 <button style="float:right;margin-right:40px;" onclick="javascript:addRole();" class="btn" >添加</button>
</form>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
function addRole(){
  var name=	$("#name").val();
	alert(name);
	if(name!==""||name==null){
		alert("名称不能为空");
	}else{
		$("#form").submit();
	}
}
</script>
</body>
</html>
