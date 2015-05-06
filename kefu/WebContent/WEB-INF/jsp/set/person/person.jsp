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
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">个人设置</a></li>
        <li><i>&gt;</i>个人信息</li>
    </ul>
</div>
<div class="g-cnt">
<table class="table table-bordered m-table" style="width: 360px">
      <tr>
      <td class="f-txtr tdbg" width="150px">工号：</td><td class="f-txtl">${user.loginName}</td>
      </tr>
      <tr>
      <td class="f-txtr tdbg" width="150px">姓名：</td><td class="f-txtl">${user.userName}</td>
      </tr>
      <tr>
      <td class="f-txtr tdbg" width="150px">身份：</td><td class="f-txtl">${user.roleName}</td>
      </tr>
      <tr>
      <td class="f-txtr tdbg" width="150px">部门：</td><td class="f-txtl">${user.deptName}</td>
      </tr>
      
</table>
<button style="margin-left:280px;" onclick="javascript:repass();" class="btn btn-primary btn-small" >重置密码</button>
</div>
<!-- 表格有边框 -->
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
function repass(){
	var d = $.dialog({id:'pass',title:"重置密码",content:'url:/user/password.action',lock:true, width:	460,height: 280,});
}

function callback(){
	$.dialog({id:'pass'}).close();
}

</script>
</body>
</html>
