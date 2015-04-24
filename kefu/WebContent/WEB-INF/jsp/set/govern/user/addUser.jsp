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
<style type="text/css">
	input{
		width:135px;
	}
</style>
</head>

<body>
<!-- 表格有边框 -->
<div style="margin:30px;">
<table class="table table-bordered m-table">
        <tr>
            <td class="f-txtr tdbg" width="80px">工号</td>
            <td class="f-txtl"><input type="text" id="loginName" name="loginName" value="${user.loginName}"/></td>
            <td class="f-txtr tdbg" width="80px">姓名</td>
            <td class="f-txtl"><input type="text" id="userName" name="userName" value="${user.userName}" maxlength="16"/></td>
        </tr>
        <tr>
         
           <td class="f-txtr tdbg">密码</td>
            <td class="f-txtl"><input type="password" id="password" name="password"/></td>
            <td class="f-txtr tdbg">确认密码</td>
            <td class="f-txtl"><input type="password" id="password1" name="password1"/></td>
        </tr>
        <tr>
         
            <td class="f-txtr tdbg">部门</td>
            <td class="f-txtl">
                <select id="deptId" name="deptId" class='c-wdat' >
                    <c:forEach items="${deptList}" var="dept">
                	<option value="${dept.id}" <c:if test="${user.deptId==dept.id}">selected</c:if>>${dept.name}</option>
                	</c:forEach>
                </select>
            </td>
            <td class="f-txtr tdbg">角色</td>
            <td class="f-txtl">
                <select id="roleId" name="roleId" class='c-wdat'>
                	<c:forEach items="${roleList}" var="role">
                	<option value="${role.id}" <c:if test="${user.roleId==role.id}">selected</c:if>>${role.name}</option>
                	</c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">接听等级</td>
               <td class="f-txtl">
                <select id="listenLevel" name="listenLevel" class='c-wdat'>
                	<option <c:if test="${user.listenLevel==1}">selected</c:if>>1</option>
                	<option <c:if test="${user.listenLevel==2}">selected</c:if>>2</option>
                	<option <c:if test="${user.listenLevel==3}">selected</c:if>>3</option>
                	<option <c:if test="${user.listenLevel==4}">selected</c:if>>4</option>
                	<option <c:if test="${user.listenLevel==5}">selected</c:if>>5</option>
                </select>
            </td>
            <td class="f-txtr tdbg">最大接听数</td>
            <td class="f-txtl"><input type="text" id="maxListen" name="maxListen" value="${user.maxListen}" maxlength="6"/></td>
        </tr>
        <tr>       
            <td class="f-txtr tdbg">工号名片</td>
            <td class="f-txtl"><input type="text" id="cardName" name="cardName" value="${user.maxListen}" maxlength="10"/></td>
            <td class="f-txtr tdbg">入职日期</td>
            <td class="f-txtl"><input type="text" id="createDate" name="createDate" value="${user.createDate}" onClick="WdatePicker({maxDate:'%y-%M-%d'})" class="c-wd120 Wdate" /></td>
        </tr>
</table>

<button style="float:right;margin-right:40px;" onclick="javascript:cl();" class="btn" >关闭</button>
 <button style="float:right;margin-right:40px;" onclick="javascript:saveUser();" class="btn btn-primary btn-small" >确认</button>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
var api = frameElement.api,W=api.opener;
function saveUser(id){
		var url = "/user/save.action";
		var data = {
			"loginName" : $("#loginName").val(),
			"userName" : $("#userName").val(),
			"password" : $("#password").val(),
			"password1" : $("#password1").val(),
			"listenLevel" : $("#listenLevel option:selected").val(),
			"deptId" : $("#deptId option:selected").val(),
			"roleId" : $("#roleId option:selected").val(),
			"deptName" : $("#deptId option:selected").text(),
			"roleName" : $("#roleId option:selected").text(),
			"maxListen" : $("#maxListen").val(),
			"cardName" : $("#cardName").val(),
			"createDate" : $("#createDate").val(),		    
		};
			//新增时验证参数
			if (!verificationParam(data)) {
				return;
			}
	$.ajax({
		type : "post",
		url : url,
		data : data,
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
function verificationParam(userData) {
	var loginName = userData.loginName;
	if (loginName.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
		 alert("工号不得为空！");
		return false;
	}
	var chinesePatrn = /^[\da-zA-Z]{6,15}$/;
	if(!chinesePatrn.test(loginName)){
		 alert("工号输入规则不对");
		return false;
	}
	
	 if(checkUser()){
		    alert("工号已存在！");
		   return false;
	   }
	
	var userName = userData.userName;
	if (userName.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
		 alert("姓名不得为空！");
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
	if (maxListen.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
		 alert("请填写最大接听数！");
		return false;
	}
	var checkListen = /^\d+$/;
	if(!checkListen.test(maxListen)||maxListen==0){
		alert("请输入大于0的正整数！");
		return false;
	}
	var cardName = userData.cardName;
	if (cardName.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
		 alert("请填写名片！");
		return false;
	}
	
	var createDate = userData.createDate;
	if (createDate.replace(/(^\s*)|(\s*$)/g, "").length == 0) {
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
