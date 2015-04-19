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
        <li><i>&gt;</i><a href="#">个人设置</a></li>
        <li><i>&gt;</i>个人信息</li>
    </ul>
</div>
<table border=1 style="width: 300px;height: 200px;margin: 50px 0 10px 100px" >
      <tr>
      <td width=30%>工号：</td><td>${user.loginName}</td>
      </tr>
      <tr>
      <td>姓名：</td><td>${user.userName}</td>
      </tr>
      <tr>
      <td>生日：</td><td>${user.birthday}</td>
      </tr>
      <tr>
      <td>身份：</td><td>${user.cardName}</td>
      </tr>
      <tr>
      <td>部门：</td><td>${user.deptId}</td>
      </tr>
      
</table>
<button style="margin-left:320px;" onclick="javascript:repass();" class="btn btn-primary btn-small" >重置密码</button>
<!-- 表格有边框 -->
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
function repass(){
	var d = $.dialog({id:'pass',title:"重置密码",content:'url:/user/password.action',lock:true, width:	500,height: 300,});
}

function callback(){
	$.dialog({id:'pass'}).close();
	find();
}
</script>
</body>
</html>
