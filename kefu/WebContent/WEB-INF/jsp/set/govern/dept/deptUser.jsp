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
<h3>查看部门成员</h3>
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td>工号</td>
            <td>姓名</td>
            <td>身份</td>
            <td>状态</td>
            <td>部门</td>
            <td>接听数</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${list}">
        <tr>
            <td>${user.loginName}</td>
            <td>${user.userName}</td>
            <td>${user.cardName}</td>
            <td>${user.onLineStatus}</td>
            <td>${user.deptId}</td>
            <td>${user.maxListen}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">

</script>
</body>
</html>
