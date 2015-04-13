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
<!-- <div class="m-crumb"> -->
<!--     <ul class="f-cb"> -->
<!--         <li><b>位置：</b></li> -->
<!--         <li><a href="#">设置中心</a></li> -->
<!--         <li><i>&gt;</i><a href="#">管理设置</a></li> -->
<!--         <li><i>&gt;</i>角色管理</li> -->
<!--     </ul> -->
<!-- </div> -->
<!-- 表格有边框 -->
<c:if test="${empty role.id}"><h2>添加角色</h2></c:if> <c:if test="${not empty role.id}"><h2>修改角色</h2></c:if>
<table  border="1" aglin="centert" class="table">
        <tr>
           <td>角色名称</td>
           <td><input type ="text" id ="name" name="name" value="${role.name}"/><td>
        </tr>
</table>
 <button style="float:right;margin-right:40px;" onclick="javascript:cl();" class="btn" >关闭</button>
 <button style="float:right;margin-right:40px;" onclick="javascript:addRole(${role.id});" class="btn" >确认</button>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
var api = frameElement.api,W=api.opener;
function addRole(id){
	var url="";
	var data="";
  if(id!=undefined){
	   url="/role/update.action";
	   data = {
			   "id":id,
				"name" : $("#name").val(),	    
			};
		//修改时验证参数
		if (!verificationParam1(data)) {
			
			return;
		}
  }else{
      url="/role/save.action";
      data = {
  			"name" : $("#name").val(),	    
  		};
      
		//新增时验证参数
		if (!verificationParam(data)) {
			
			return;
		}
   }
		
		$.ajax({
			type : "get",
			url : url,
			data : data,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
					alert(data.msg);
					w.callback();
			},
			error : function(msg) {
				alert(data.msg);
			}
		});
	}
/**
 *  js 校验添加
 */
function verificationParam(roleData) {
	var roleName = roleData.name;
	if (roleName.replace("^[ ]+$", "").length == 0) {
		
		alert("角色名不得为空！");

		return false;
	}
	if(checkRole()){
		
		   alert("角色名已存在！");
		   return false;
	 }
	return true;
}

function verificationParam1(roleData) {
	var roleName = roleData.name;
	if (roleName.replace("^[ ]+$", "").length == 0) {
		
		alert("角色名不得为空！");

		return false;
	}
	return true;
}
   /*
* 验证角色唯一性(添加时)
*/

function checkRole(){
	var flag = false;
if($("#name").val()==''){
	$("#info").html("角色名不能为空!");
	return true;
     }
  $.ajax({
	type : "get",
	url : "/role/check.action",
	data : {'name':$("#name").val()},
	contentType : "application/json; charset=utf-8",
	dataType : "json",
	async:false,
	success : function(data) {
		if(data.code==0)
			$("#info").html("");
		else{
			$("#info").html("该角色名已存在!");
		 	flag = true;
		}
	},
	error : function(msg){
		alert("查询失败!");
	}
   });
  return flag;
  }
   
function cl(){
		api.close();			
}
</script>
</body>
</html>
