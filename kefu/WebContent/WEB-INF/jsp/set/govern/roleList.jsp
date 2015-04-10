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
<button style="float:right;margin-right:5px;" onclick="javascript:addRole();" class="btn" >添加角色</button>
<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td>角色名称</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="role" items="${list}">
        <tr>
            <td>${role.name}</td>
            <td><a href="">查看<a/>&nbsp;&nbsp;<a href="">编辑<a/>&nbsp;&nbsp;<a href="">删除<a/>&nbsp;&nbsp;<a href="">权限配置<a/></td>
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
function addRole(){
	$.dialog({content:'url:/role/addRole.action',
		width: 900,height: 500,
		button: [
			        {
			            name: '确认',
			            callback: function () {
			                 save(); 
			                return false;
			            },
			            focus: true
			        },
			        {
			            name: '关闭我'
			        }
			    ]
		});
}
</script>
</body>
</html>
