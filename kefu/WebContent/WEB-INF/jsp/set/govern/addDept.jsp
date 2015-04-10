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
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">管理设置</a></li>
        <li><i>&gt;</i>部门管理</li>
    </ul>
</div>
<h2>添加部门</h2>
<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
        <tr>
            <td>部门名称</td>
            <td><input type="text" id="name" name="name" value="${dept.name}"/></td>
        </tr>
            <tr>
            <td>排序</td>
            <td><input type ="text" id="sortNum" name="sortNum" value="${dept.sortNum}" readonly="readonly"/></td>
        </tr>
</table>
 <button style="float:right;margin-right:40px;" onclick="javascript:addDept(${dept.id});" class="btn" >确认</button>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
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
  }else{
      url="/dept/save.action";
      data = {
  			"name" : $("#name").val(),
  			"sortNum": $("#sortNum").val()
  		};
   }
	
		//新增时验证参数
		if (!verificationParam(data)) {		
			return;
		}
		$.ajax({
			type : "get",
			url : url,
			data : data,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
					$.dialog.alert(data.msg);
			},
			error : function(msg) {
				$.dialog.alert(data.msg);
			}
		});
	}
/**
 *  js 校验添加
 */
function verificationParam(deptData) {
	var deptName = deptData.name;
	if (deptName.replace("^[ ]+$", "").length == 0) {
		$.dialog.alert("部们名不得为空！");

		return false;
	}
	if(checkDept()){
		   $.dialog.alert("部门已存在！");
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
		$.dialog.alert("查询失败!");
	}
   });
  return flag;
  }
</script>
</body>
</html>
