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
<form>
<table class="table table-bordered table-striped table-hover m-table">
        <tr>
            <td>工号</td>
            <td><input type="text" id="loginName" name="loginName" value="${user.loginName}"/></td>
            <td>姓名</td>
            <td><input type="text" id="userName" name="userName" value="${user.userName}"/></td>
        </tr>
        <tr>
         
            <td>密码</td>
            <td><input type="password" id="password" name="password" value="${user.password}"/></td>
            <td>确认密码</td>
            <td><input type="password" id="password1" name="password1" value="${user.password}"/></td>
        </tr>
        <tr>
         
            <td>部门</td>
            <td>
                <select id="deptId" name="deptId" >
                    <c:forEach items="${deptList}" var="dept">
                	<option value="${dept.id}" <c:if test="${user.deptId==dept.id}">selected</c:if>>${dept.name}</option>
                	</c:forEach>
                </select>
            </td>
             <td>角色</td>
            <td>
                <select id="roleId" name="roleId">
                	<c:forEach items="${roleList}" var="role">
                	<option value="${role.id}" <c:if test="${user.roleId==role.id}">selected</c:if>>${role.name}</option>
                	</c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>接听等级</td>
               <td>
                <select id="listenLevel" name="listenLevel">
                	<option <c:if test="${user.listenLevel==1}">selected</c:if>>1</option>
                	<option <c:if test="${user.listenLevel==2}">selected</c:if>>2</option>
                	<option <c:if test="${user.listenLevel==3}">selected</c:if>>3</option>
                	<option <c:if test="${user.listenLevel==4}">selected</c:if>>4</option>
                	<option <c:if test="${user.listenLevel==5}">selected</c:if>>5</option>
                </select>
            </td>
            <td>最大接听数</td>
            <td><input type="text" id="maxListen" name="maxListen" value="${user.maxListen}"/></td>
        </tr>
        <tr>       
            <td>工号名片</td>
            <td><input type="text" id="cardName" name="cardName" value="${user.maxListen}"/></td>
            <td>入职日期</td>
            <td><input type="text" id="createDate" name="createDate" value="${user.createDate}" onClick="WdatePicker()" class="c-wd120 Wdate" /></td>
        </tr>
</table>
                                     <input type="button" value="保存" onclick="save(${user.id})"/>  <input type="button" value="关闭" />
</form>




<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
function save(id){
	alert(id);
	if(id!=null){
		var url = "/user/save.action";
		
	}
	var data = {
		"loginName" : $("#loginName").val(),
		"userName" : $("#userName").val(),
		"password" : $("#password").val(),
		"password1" : $("#password1").val(),
		"listenLevel" : $("#listenLevel option:selected").val(),
		"deptId" : $("#deptId option:selected").val(),
		"roleId" : $("#roleId option:selected").val(),
		"maxListen" : $("#maxListen").val(),
		"cardName" : $("#cardName").val(),
		"createDate" : $("#createDate").val(),		    
	};
	//新增时验证参数
	if (!verificationParam(data)) {
		alert(data);
		return;
	}
	$.ajax({
		type : "get",
		url : url,
		data : data,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
				alert(data.msg);
		},
		error : function(msg) {
			alert(data.msg);
		}
	});
}
/**
 *  js 校验添加
 */
function verificationParam(userData) {
	var loginName = userData.loginName;
	if (loginName.replace("^[ ]+$", "").length == 0) {
		alert("登录名不得为空！");
		return false;
	}
	
	var chinesePatrn = /^[\da-zA-Z]{6,10}$/;
	if(chinesePatrn.test(loginName)){
		alert("工号输入规则不对");
		return false;
	}
	
	 if(checkUser()){
		   alert("登录名已存在！");
		   return false;
	   }
	
	var userName = userData.userName;
	if (userName.replace("^[ ]+$", "").length == 0) {
		alert("用户名不得为空！");
		return false;
	}
	
	var password = userData.password;
	var patrn = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/;
	if (!patrn.test(password)) {
		alert("密码格式不正确");
		return false;
	}
	
	var password1 = userData.password1;
	if (password !=password1) {
		alert("两次输入密码不一致！");
		return false;
	}
	
	var maxListen = userData.maxListen;
	if (maxListen.replace("^[ ]+$", "").length == 0) {
		alert("请填写最大接听数！");
		return false;
	}
	
	var cardName = userData.cardName;
	if (cardName.replace("^[ ]+$", "").length == 0) {
		alert("请填写名片！");
		return false;
	}
	
	var createDate = userData.createDate;
	if (createDate.replace("^[ ]+$", "").length == 0) {
		alert("请填写入职时间！");
		return false;
	}
	
	return true;
}
function checkUser(){
	var flag = false;
	if($("#1oginName").val()==''){
		$("#info").html("登录名不能为空!");
		return true;
	}
	$.ajax({
		type : "get",
		url : "/user/check.action",
		data : {'loginName':$("#loginName").val()},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async:false,
		success : function(data) {
			if(data.code==0)
				$("#info").html("*");
			else{
				$("#info").html("该登录名已存在!");
			 	flag = true;
			}
		},
		error : function(msg){
			alert("查询失败!");
		}
	});
	return flag;
}
</script>
</body>
</html>
