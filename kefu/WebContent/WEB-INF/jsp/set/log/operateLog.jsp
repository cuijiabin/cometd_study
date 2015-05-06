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
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">日志管理</a></li>
        <li><i>&gt;</i>操作日志</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="g-cnt">
<div class="m-query f-mar10">
	<div class="m-query-hd">
		<label>栏目：</label>
        <select class="c-wd80" name="funcId" id="funcId">
            <option value="0" selected="selected">全部栏目</option>
            <option value="1">客服部</option>
            <option value="2">留学部</option>
            <option value="3">随时学</option>
            <option value="4">好顾问</option>
        </select>
		<label>操作人：</label><input class="c-wd80" type="text" name="loginName" id="loginName" value="" />
        <label>部门：</label>
        <select class="c-wd80" name="deptId" id="deptId">
            <option value="0" selected="selected">全部部门</option>
            <option value="1">客服部</option>
            <option value="2">留学部</option>
            <option value="3">随时学</option>
            <option value="4">好顾问</option>
        </select>
        <label>人员：</label>
        <select class="c-wd80" name="userId" id="userId" >
            <option value="0" selected="selected"></option>
        </select>
        <label>日期：</label><input class="c-wd80 Wdate" name="startDate" id="startDate" type="text" onClick="WdatePicker()" /> - <input class="c-wd80 Wdate" name="endDate" id="endDate" type="text" onClick="WdatePicker()" />
        
        <div class="u-subsec">
           	<button class="btn btn-primary" type="button" onclick="javascript:find(1);"> 查 询  </button>
        </div>
    </div>
    <div class="u-hr"></div>
</div>

<div id="table_data">
	<jsp:include page="operateLogList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
//自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);
function find(currentPage){
	var url="/log/findLog.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[funcId]":$("#funcId").val(),
			"map[loginName]":$("#loginName").val(),
			"map[deptId]":$("#deptId").val(),
			"map[userId]":$("#userId").val(),
			"map[startDate]":$("#startDate").val(),
			"map[endDate]":$("#endDate").val(),
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
