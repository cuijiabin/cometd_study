<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客户端访客对话框架--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body class="g-body">
<div class="m-chat">

	<table>
	  <thead>
	    <tr>
	      <th width="10">客服转接</th>
	      <th width="30">您确定要转接客户${customerId }吗？</th>
	    </tr>
	  </thead>
	  
	  <tbody>
	    <tr>
	      <td>
	                        选择客服：<select>
						  <c:forEach var="user" items="${list}">
						  <option value ="${user.id }" id="switchUserId">${user.cardName}</option>
						  </c:forEach>
					  </select> 
		  </td>
	      <td>备注：<textarea rows="3" cols="20" id="switchContent"></textarea></td>
	    </tr>
	    <tr>
	  </tbody>
    </table>
  
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>

</body>
</html>