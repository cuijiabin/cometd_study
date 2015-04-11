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
        <li><a href="#">访客管理</a></li>
        <li><i>&gt;</i><a href="#">黑名单</a></li>
        <li><i>&gt;</i>添加黑名单</li>
    </ul>
</div>
<!-- 表格有边框 -->
<h2>添加黑名单</h2>
<form name="form1" method="post" id="form1">
<table  border="1" aglin="centert" class="table">
        <tr>
           <td>客户编号</td>
           <td><input type ="text" id ="id" name="id" value="1000000001"/><td>
        </tr>
         <tr>
           <td>IP地址</td>
           <td><input type ="text" id ="ip" name="ip" value="192.168.1.102"/><td>
        </tr>
         <tr>
           <td>失效时间</td>
           <td><input type ="text" id ="endDate" name="endDate" value="2015-01-28 23:58:29"/><td>
        </tr>
         <tr>
           <td>阻止原因</td>
           <td><input type ="text" id ="description" name="description" value="骂人"/><td>
        </tr>
</table>
 <button style="float:right;margin-right:40px;" onclick="javascript:save();"  class="btn" >保存</button>
</form>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>

<script type="text/javascript">

</script>
</body>
</html>
