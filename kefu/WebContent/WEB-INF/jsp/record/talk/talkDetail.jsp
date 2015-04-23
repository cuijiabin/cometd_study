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
        <li>记录中心</li>
        <li><i>&gt;</i>聊天记录</li>
        <li><i>&gt;</i>详情</li>
    </ul>
</div>

<div class="g-bd6 f-cb f-mar20">
    <div class="g-sd6 c-bor">
        <h3 class="u-tit c-bg">聊天记录时间线</h3>
        <input type="hidden" id="isShowTel" value="${isShowTel }" />        	
        <ul class="m-recordtime">
        	<c:forEach var="timeDia" items="${timeList }">
        		<li><a href="#" onClick="showDetail(${timeDia.id})"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${timeDia.beginDate}"/></a></li>
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


//查看明细,切换右侧明细
function showDetail(dialogueId){
	var isShowTel = $("#isShowTel").val() ;
	var url="/recordsCenter/queryTalkDetail.action";
	var data = {
			"dialogueId":dialogueId,
			"isShowTel":isShowTel
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

//分页, 切换右下聊天内容
function find(currentPage){
	var isShowTel = $("#isShowTel").val() ;
	var dialogueId = $("#dialogueId").val() ;
	var url="/recordsCenter/queryTalkDetail.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"typeId":1,
			"dialogueId":dialogueId,
			"isShowTel":isShowTel
	};
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    dataType: "html",
	    success: function (data) {
	       $("#table_detail_data").html(data);
	    },
	    error: function (msg) {
	        alert(msg);
	    }
	});
}
</script>
</body>
</html>

