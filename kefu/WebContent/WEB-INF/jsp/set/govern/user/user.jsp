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
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- 面包屑 -->
<div class="m-crumb" style="margin-bottom: 20px">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">管理设置</a></li>
        <li><i>&gt;</i>工号管理</li>
    </ul>
</div>

<!-- 表格有边框 -->
<div class="g-cnt">
<div class="m-query f-mar10">
	<button class="btn btn-primary" type="button" onclick="javascript:changeUserType('1');"> 在职员工  </button>
	<button class="btn btn-primary" type="button" onclick="javascript:changeUserType('2')"> 离职员工  </button>
	<button style="float:right;margin-right:5px;" onclick="javascript:addUser()" class="btn btn-primary btn-small" >添加工号</button>
</div>
<div id="table_data" style="margin-top: 10px">
	<jsp:include page="userList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
//自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);
function changeUserType(typeId){
	$("#status").val(typeId);
	find(1);
}
function find(currentPage){
	var url="/user/find.action?map[status]="+$("#status").val();
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[typeId]":1,
			"map[id]":1
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
	    	$.dialog.alert(msg);
	    }
	});
}

function addUser(){
	var d = $.dialog({id:'user',title:"添加工号",content:'url:/user/add.action',lock:true, width: 560,height: 350,});
}
function updateUser(id){

	var d = $.dialog({id:'user',title:"修改工号",content:'url:/user/detail.action?id='+id+'',lock:true, width: 

		560,height: 350,});

}
function findUser(id){

	var d = $.dialog({id:'user',title:"查看工号",content:'url:/user/detail.action?id='+id+'&type='+5+'',lock:true, width: 

		560,height: 350,});

}
function deptUser(dId){

	var d = $.dialog({id:'dept',title:"查看部门成员",content:'url:/dept/findDeptUser.action?deptId='+dId+'',lock:true, width: 

		800,height: 600,});

}

function userLeave(status){
	var ids =[]; 
	$('input[name="userId"]:checked').each(function(){ 
		ids.push($(this).val()); 
	}); 
	if(ids==""||ids==null||ids==0){
		$.dialog.alert("请选择人员!");
		return;
	}
	$.ajax({
		url:"/user/leave.action?status="+status+"",
		type:"post",
		data:"ids="+ids,
		dataType:"json",
		success:function(data) {
			$.dialog.alert(data.msg,function(){
				find(1);
    		});		
		},
		error : function(data) {
			$.dialog.alert("出现错误,请重试！");
		}
	});
	
}

function deleteAll(){
	var ids =[]; 
	$('input[name="userId"]:checked').each(function(){ 
		ids.push($(this).val()); 
	});
	if(ids==""||ids==null||ids==0){
		$.dialog.alert("请选择人员!");
		return;
	}
	$.ajax({
		url:"/user/delete.action",
		type:"post",
		data:"ids="+ids,
		dataType:"json",
		success:function(data) {
			$.dialog.alert(data.msg,function(){
				find(1);
    		});		
		},
		error : function(data) {
			$.dialog.alert("出现错误,请重试！");
		}
	});
}
function changeDept(){
	var ids =[]; 
	$('input[name="userId"]:checked').each(function(){ 
		ids.push($(this).val()); 
	});
	var deptId=$("#dept option:selected").val()
	if(deptId==0){
		$.dialog.alert("请选择转移部门");
		return;
	}
	if(ids==""||ids==null||ids==0){
		$.dialog.alert("请选择人员!");
		return;
	}
	$.ajax({
		url:"/user/tradept.action?deptId="+deptId,
		type:"post",
		data:"ids="+ids,
		dataType:"json",
		success:function(data) {
			$.dialog.alert(data.msg,function(){
				find(1);
    		});		
			
		},
		error : function(data) {
			$.dialog.alert("出现错误,请重试！");
		}
	});
}
function callback(){
	$.dialog({id:'user'}).close();
	location.href="/user/find.action?map[status]=1&map[id]=1";
}

function checkAll() {
	var value=$('[name=all]:checked').val();
	if(value==0){
          $("input:checkbox").prop("checked",true);
	}else{
		  $("input:checkbox").each(function(){
		        $(this).prop("checked",false);
		   });
	}
}
</script>
</body>
</html>
