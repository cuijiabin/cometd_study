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
<input type="button" value="添加部门" style="margin-left:1608px"/>
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td>部门名称</td>
            <td>在职人数</td>
            <td>排序</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="dept" items="${list}">
        <tr>

            <td>${dept.name}</td>
            <td>${dept.userCount}</td>
            <td>${dept.sortNum}</td>
            <td><a href="">查看<a/>&nbsp;&nbsp;<a href="">编辑<a/>&nbsp;&nbsp;<a href="">删除<a/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
</script>
</body>
</html>
