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
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">访客管理</a></li>
        <li><i>&gt;</i>访客信息</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
            <label>客户姓名：</label><input id="customerName" name="customerName" value="${customerName}" class="c-wd150" type="text" />
            <label>客户编号：</label><input id="customerId" name="customerId" value="${customerId} " class="c-wd150" type="text" />
            <label>联系方式：</label><input id="customerPhone" name="customerPhone" value="${phone }" class="c-wd150" type="text" />
            <label>风格：</label><input id="customerStyle" name="customerStyle" class="c-wd150" type="text" />
        </div>
        <div class="f-mbm">
           <label>添加时间：</label><input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" /> - <input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>网站关键词：</label><input class="c-wd150" type="text" />
             <label></label>
              <label></label>
               <label></label>
            <button type="button" class="btn btn-primary btn-small" onclick="javascript:find(1);">查询</button>
            <label></label>
            <button type="button" class="btn btn-primary btn-small" >导出</button>
        </div>
        <div class="m-query-hd">
    </div>
    </div>
</div>

<div id="table_data">
	<jsp:include page="customerList.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">

function find(currentPage){
	var url="/customer/find.action";
	var data = {
			"currentPage":currentPage,
			"map[customerName]":$("#customerName").val(),
			"map[customerId]":$("#customerId").val(),
			"map[customerPhone]":$("#customerPhone").val(),
			"map[typeId]":1
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
</script>
</body>
</html>
