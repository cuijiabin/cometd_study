<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!--  面包屑  -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">常用语管理</a></li>
        <li><i>&gt;</i>公共常用语设置</li>
    </ul>
</div>
<!-- <div id="table_data"> -->
<%-- 	<jsp:include page="messageTree.jsp"></jsp:include> --%>
<!-- </div> -->

<div id="table_data">
	<jsp:include page="messageList.jsp"></jsp:include>
</div>
</body>
</html>