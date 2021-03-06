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
<div style="margin:50px">
<table class="table table-bordered m-table" style="width:350px">
      <tr>
      <td class="f-txtr tdbg" width="100px">旧密码</td><td class="f-txtl"><input type="password" id="oldpass" name="oldpass"/></td>
      </tr>
      <tr>
      <td class="f-txtr tdbg" width="100px">新密码</td><td class="f-txtl"><input type="password" id="password" name="password"/></td>
      </tr>
      <tr>
      <td class="f-txtr tdbg" width="100px">重复密码</td><td class="f-txtl"><input type="password" id="password1" name="password1"/></td>
      </tr>    
</table>
 <button style="float:left; margin-left:210px;" onclick="javascript:repass();" class="btn btn-primary btn-small" >确认</button>
 <button style="float:left; margin-left:10px;" onclick="javascript:cl();" class="btn" >关闭</button>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
var api = frameElement.api,W=api.opener;
function repass(){
	   url="/user/repass.action";
	   data = {
			    "oldpass":$("#oldpass").val(),
				"password" : $("#password").val(),	    
				"password1" : $("#password1").val()	    
			};
		//修改时验证参数
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
				W.$.dialog.alert(data.msg);
					W.callback();
			},
			error : function(msg) {
				W.$.dialog.alert("出现错误,请重试!");
			}
		});
	}
function verificationParam(userData) {
       var oldpass = userData.oldpass;
	   if(oldpass.replace(/(^\s*)|(\s*$)/g, "").length ==0){
		   W.$.dialog.alert("旧密码不能为空");
			return false;
		}
	   if(checkPass()){
		   W.$.dialog.alert("旧密码输入错误！");
		   return false;
	   }
		var patrn = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/;
		if (!patrn.test(oldpass)) {
			W.$.dialog.alert("旧密码格式不正确");
			return false;
		}
		
       var password = userData.password;
	   if(password.replace(/(^\s*)|(\s*$)/g, "").length ==0){
		   W.$.dialog.alert("新密码不能为空");
			return false;
		}
		
		var patrn = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/;
		if (!patrn.test(password)) {
			W.$.dialog.alert("新密码格式不正确");
			return false;
		}
		
		var password1 = userData.password1;
		if (password !=password1) {
			W.$.dialog.alert("两次输入密码不一致！");
			return false;
		}
		return true;
}

function checkPass(){
    var flag=false;
	$.ajax({
		type : "get",
		url : "/user/checkPass.action",
		data : {'oldpass':$("#oldpass").val()},
		dataType : "json",
		async:false,
		success : function(data) {
			if(data.code==0)
				$("#info").html("*");
			else{
				$("#info").html("旧密码输入不正确!");
			 	flag = true;
			}
		},
		error : function(msg){
			W.$.dialog.alert("出现错误,请重试!");
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
