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
<div class="m-crumb" style="margin-bottom: 20px">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">管理设置</a></li>
        <li><i>&gt;</i>工号管理</li>
    </ul>
</div>

<!-- 表格有边框 -->
<a href="/user/find.action?map[status]=1" style="font-size:18px">在职员工</a> <a href="/user/find.action?map[status]=2"style="font-size:18px">离职员工</a>
<button style="float:right;margin-right:5px;" onclick="javascript:addUser()" class="btn btn-primary btn-small" >添加工号</button>

<div id="table_data" style="margin-top: 10px">
	<jsp:include page="userList.jsp"></jsp:include>
</div>
 <c:if test="${status==1}"> <button class="btn btn-primary btn-small" id="leaves" onclick="userLeave(2)">员工离职</button>  
  <select id="dept" name="dept" onchange="changeDept()">
      <option value="0">转移部门</option>
      <c:forEach items="${deptList}" var="dept">
     		 <option value="${dept.id}">转移至${dept.name}</option>
      </c:forEach>
  </select>
  </c:if>
  <c:if test="${status==2}">
  <button class="btn btn-primary btn-small" onclick="userLeave(1)">员工复职</button> <button class="btn" onclick="deleteAll()">删除</button>
  </c:if>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
function find(currentPage){
	var url="/user/find.action?map[status]=1";
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

function addUser(){
	var d = $.dialog({id:'user',title:"添加工号",content:'url:/user/add.action',lock:true, width:	1000,height: 600,});
}
function updateUser(id){

	var d = $.dialog({id:'user',title:"修改工号",content:'url:/user/detail.action?id='+id+'',lock:true, width: 

		1000,height: 600,});

}
function findUser(id){

	var d = $.dialog({id:'user',title:"查看工号",content:'url:/user/detail.action?id='+id+'&type='+5+'',lock:true, width: 

		1000,height: 600,});

}
function deptUser(dId){

	var d = $.dialog({id:'dept',title:"查看部门成员",content:'url:/dept/findDeptUser.action?deptId='+dId+'',lock:true, width: 

		800,height: 600,});

}

function userLeave(status){
	var ids= $(":checkbox[checked='checked']").map(function(){
		return $(this).val();
	}).get();
	if(ids==""||ids==null||ids==0){
		alert("请选择人员!");
		return;
	}
	$.ajax({
		url:"/user/leave.action?status="+status+"",
		type:"post",
		data:"ids="+ids,
		dataType:"json",
		success:function(data) {
			alert(data.msg);
			location.reload();
		},
		error : function(data) {
			alert("出现错误,请重试！");
		}
	});
	
}

function deleteAll(){
	var ids= $(":checkbox[checked='checked']").map(function(){
		return $(this).val();
	}).get();
	if(ids==""||ids==null||ids==0){
		alert("请选择人员!");
		return;
	}
	$.ajax({
		url:"/user/delete.action",
		type:"post",
		data:"ids="+ids,
		dataType:"json",
		success:function(data) {
			alert(data.msg);
			location.reload();
		},
		error : function(data) {
			alert("出现错误,请重试！");
		}
	});
}
function changeDept(){
	var ids= $(":checkbox[checked='checked']").map(function(){
		return $(this).val();
	}).get();
	var deptId=$("#dept option:selected").val()
	if(deptId==0){
		alert("请选择转移部门");
		return;
	}
	if(ids==""||ids==null||ids==0){
		alert("请选择人员!");
		return;
	}
	$.ajax({
		url:"/user/tradept.action?deptId="+deptId,
		type:"post",
		data:"ids="+ids,
		dataType:"json",
		success:function(data) {
			alert(data.msg);
			find();
		},
		error : function(data) {
			alert("出现错误,请重试！");
		}
	});
}
function callback(){
	$.dialog({id:'user'}).close();
	location.href="/user/find.action?map[status]=1"
}

function checkAll() {
	var value=$('[name=all]:checked').val();
	if(value==0){
          $("input:checkbox").attr("checked","true");
	}else{
		  $("input:checkbox").each(function(){
		        this.checked=false;
		   });
	}
}
</script>
</body>
</html>
