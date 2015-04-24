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
<style type="text/css">
	input{
		width:150px;
	}
</style>
<body>
<div style="margin:50px">
<!-- 表格有边框 -->
<table class="table table-bordered m-table">
        <tr>
            <td class="f-txtr tdbg" width="80px">部门名称</td>
            <td class="f-txtl"><input type="text" id="name" name="name" value="${dept.name}" maxlength="10"/></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg" width="80px">排序</td>
            <td class="f-txtl"><input type ="text" id="sortNum" name="sortNum" value="${dept.sortNum}" readonly="readonly"/></td>
        </tr>
</table>
<button style="float:right;margin-right:40px;" onclick="javascript:cl();" class="btn" >关闭</button>
 <button style="float:right;margin-right:40px;" onclick="javascript:addDept(${dept.id});" class="btn btn-primary btn-small" >确认</button>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
var api = frameElement.api,W=api.opener;

function addDept(id){
	var url="";
	var data="";
  if(id!=undefined){
	   url="/dept/update.action";
	   data = {
			   "id":id,
				"name" : $("#name").val(),
				"sortNum": $("#sortNum").val()
			};
		//新增时验证参数
		if (!verificationParam1(data)) {		
			return;
		}
  }else{
      url="/dept/save.action";
      data = {
  			"name" : $("#name").val(),
  			"sortNum": $("#sortNum").val()
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
					W.callback();
			},
			error : function(msg) {
				alert("出现错误,请重试!");
			}
		});
	}
/**
 *  js 校验添加
 */
function verificationParam(deptData) {
	var deptName = deptData.name;
	if (deptName.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
		
		alert("部门名不得为空！");

		return false;
	}
	if(checkDept()){
		   alert("部门已存在！");
		   return false;
	 }
	return true;
}
//修改时验证
function verificationParam1(deptData) {
	var deptName = deptData.name;
	if (deptName.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
		
		alert("部门名不得为空！");

		return false;
	}
	if(checkDept()){
		   alert("部门已存在！");
		   return false;
	 }
	return true;
}
   /*
* 验证角色唯一性(添加时)
*/

function checkDept(){
	var flag = false;
if($("#name").val()==''){
	$("#info").html("部门名不能为空!");
	return true;
     }
  $.ajax({
	type : "get",
	url : "/dept/check.action",
	data : {'name':$("#name").val()},
	contentType : "application/json; charset=utf-8",
	dataType : "json",
	async:false,
	success : function(data) {
		if(data.code==0)
			$("#info").html("");
		else{
			$("#info").html("该部门名已存在!");
		 	flag = true;
		}
	},
	error : function(msg){
		alert("出现错误,请重试!");
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
