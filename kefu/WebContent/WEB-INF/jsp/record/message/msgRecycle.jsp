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
        <li><i>&gt;</i><a href="#">留言记录</a></li>
        <li><i>&gt;</i><a href="#">回收站</a></li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	<div class="m-query-hd">
        <label>对话时间：</label><input class="c-wd80 Wdate" type="text" id="beginDate" onClick="WdatePicker()" /> - 
        <input class="c-wd80 Wdate" type="text" id="endDate" onClick="WdatePicker()" />
        <div class="u-subsec">
           	<button class="btn btn-primary" type="button" onclick="javascript:find(1);"> 查 询  </button>
        </div>
    </div>
    <div class="u-subsec">
        <button type="button" class="btn btn-primary btn-small" onclick="restore();">还原</button>
        <button type="button" class="btn btn-primary btn-small" onclick="delTrue();">彻底删除</button>
   	</div>
    <div class="u-hr"></div>
</div>

<div id="table_data">
	<jsp:include page="msgRecycleList.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">

function find(currentPage){
	var url="/recordsCenter/findMessageDel.action";
	var data = {
			"currentPage":currentPage,
			"typeId":1,
			"beginDate":$("#beginDate").val(),
			"endDate":$("#endDate").val()
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

//全选/全不选
function checkedAll(){
	if(($("#titleCheckbox").is(":checked"))){
		$("#table_data :checkbox").attr("checked", true);  
	}else{
		$("#table_data :checkbox").attr("checked", false);
	}
}

//还原
function restore(){
	var ids = "";
	$("input[type=checkbox][name=detailCheckbox]:checked").each(function(){ 
    	if(ids!=""){
    		ids+=",";
    	}
   		ids+=$(this).val();
    });
	if(ids==""){
		$.dialog.alert("请先选择数据!");
		return;
	}
	
	$.dialog.confirm('你确定要还原吗？', function(){
		var url="/recordsCenter/restoreMsg.action";
		$.ajax({
		    type: "GET",
		    url: url,
		    data: {"ids":ids},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg);
		    		find(1);
		    	}else{
		    		$.dialog.alert(data.msg);
		    	}
		    },
		    error: function (msg) {
		    	$.dialog.alert(msg);
		    }
		});
	});	
		
}

//彻底删除
function delTrue(){
	var ids = "";
	$("input[type=checkbox][name=detailCheckbox]:checked").each(function(){ 
    	if(ids!=""){
    		ids+=",";
    	}
   		ids+=$(this).val();
    });
	if(ids==""){
		$.dialog.alert("请先选择数据!");
		return;
	}
	$.dialog.confirm('你确定要彻底删除吗？', function(){
		var url="/recordsCenter/deleteMsgTrue.action";
		$.ajax({
		    type: "GET",
		    url: url,
		    data: {"ids":ids},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg);
		    		find(1);
		    	}else{
		    		$.dialog.alert(data.msg);
		    	}
		    },
		    error: function (msg) {
		    	$.dialog.alert(msg);
		    }
		});
	});
}

//创建客户名称
function updateCusl(customerId,msgId){
	$.dialog({content:'url:/customer/editCus4Msg.action?customerId='+customerId+'&msgId='+msgId,
		id: 'editCus',
		width: 400,height: 500,
		title:'添加访客信息'
	});
}

//创建客户回调
function editCallback(){
	$.dialog({id:'editCus'}).close();
	var pageNo = '${pageBean.currentPage}';
	find(pageNo);
}

//查看明细
function showDetail(msgId){
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
