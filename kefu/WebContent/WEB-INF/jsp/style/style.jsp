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
        <li><a href="#">系统设置</a></li>
        <li><i>&gt;</i><a href="#">风格管理</a></li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10" style="height: 30px">
    <div style="float:right;margin-right: 30px;height: 24px">
       <button type="button" class="btn btn-primary btn-small" onclick="add();">添加风格</button>
   	</div>
</div>

<div id="table_data">
	<jsp:include page="styleList.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">

function find(currentPage){
	var url="/style/find.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
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


//添加风格
function add(){
// 	var url = '/style/edit.action';
// 	window.location = url;
// 	return;
	$.dialog({content:'url:/style/edit.action',
		id: 'add',
		width: 400,height: 80,
		title:'添加风格'
	});
}

//添加风格回调
function editCallback(){
	$.dialog({id:'edit'}).close();
	$.dialog({id:'add'}).close();
// 	var pageNo = '${pageBean.currentPage}';
	find(1);
}

//添加风格
function rename(id){
	$.dialog({content:'url:/style/edit.action?styleId='+id,
		id: 'edit',
		width: 400,height: 80,
		title:'重命名风格'
	});
}

//查看明细
function showDetail(msgId){
// 	var url = '/messageRecords/view.action?msgId='+msgId;
// 	window.location = url;
// 	return;
	$.dialog({content:'url:/messageRecords/view.action?msgId='+msgId,
		id: 'viewMsg',
		width: 400,height: 500,
		title:'留言详情',
		cancel: true
	});
}
</script>
</body>
</html>
