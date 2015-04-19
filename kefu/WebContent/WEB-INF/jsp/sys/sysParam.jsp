<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js"></script>
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">系统设置</a></li>
        <li><i>&gt;</i>全局设置</li>
    </ul>
</div>
<div style="margin:50px;">
<table class="table table-bordered m-table">
    <tbody>
        <tr>
            <td width="200px" class="f-txtr tdbg">显示上次聊天记录：</td>
            <td class="f-txtl">
            	<select name="message" id="message"  class="c-wdat"  >
            		<option value="1" <c:if test="${message.itemName==1 }">selected</c:if>>打开 </ption>
            		<option value="2" <c:if test="${message.itemName==2 }">selected</c:if>>关闭</option>
            	</select>
            </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">留言Email通知：</td>
            <td class="f-txtl">
            	<select name="email" id="email"  class="c-wdat" >
            		<option value="1" <c:if test="${email.itemName==1 }">selected</c:if>>打开 </ption>
            		<option value="2" <c:if test="${email.itemName==2 }">selected</c:if>>关闭</option>
            	</select>
            </td>
        </tr>
        <tr>
        	<td colspan="2" class="f-txtl">
        		<div style="width:98%;text-align: center">
            		<button type="button" class="btn btn-primary" onclick="update();" id="btn_save">保存</button>
            	</div>
			</td>
        </tr>
    </tbody>
</table>
	
</div>
<script type="text/javascript">
function update(){
	var data = {
			"message":$("#message").val(),
			"email":$("#email").val()
	};
	$.ajax({
	    type: "post",
	    url: "/sys/updateSysParam.action",
	    dataType: "json",
	    data : data,
	    success: function (data) {
    		$.dialog.alert(data.msg);
	    },
	    error: function (msg) {
	    	$.dialog.alert(msg);
	    }
	});
}
</script>
</body>
</html>
