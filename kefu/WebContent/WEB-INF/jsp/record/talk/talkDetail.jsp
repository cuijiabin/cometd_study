<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
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
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">记录中心</a></li>
        <li><i>&gt;</i><a href="#">聊天记录</a></li>
        <li><i>&gt;</i><a href="#">详情</a></li>
    </ul>
</div>

<div class="g-bd6 f-cb f-mar20">
    <div class="g-sd6 c-bor">
        <h3 class="u-tit c-bg">聊天记录时间线</h3>
        <input type="hidden" id="isShowTel" value="${isShowTel }" />        	
        <ul class="m-recordtime">
        	<c:forEach var="timeDia" items="${timeList }">
        		<li><a href="#"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${timeDia.beginDate}"/></a></li>
			</c:forEach>
        </ul>
    </div>
    
    <div class="g-mn6">
        <div class="g-mn6c">
        	<div id="table_data">
				<jsp:include page="talkDetailCommon.jsp"></jsp:include>
			</div>
        </div>
    </div>
    
    
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">

$(function(){
	var isShowTel = $("#isShowTel").val() ;
});

</script>
</body>
</html>

