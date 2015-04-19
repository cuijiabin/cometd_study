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
        <li><i>&gt;</i>访客注册设置</li>
    </ul>
</div>
<div style="margin:50px;">
<table class="table table-bordered m-table">
    <tbody>
        <tr>
            <td width="200px" class="f-txtr tdbg">客户注册：</td>
            <td class="f-txtl">
            		<label><input type="checkbox" name="message" id="message" <c:if test="${message.itemName == '1' }"> checked </c:if> />${message.description }</label>
            		<label><input type="checkbox" name="dialog" id="dialog" <c:if test="${dialog.itemName == '1'}"> checked </c:if>  />${dialog.description }</label>
            </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">客户信息：</td>
            <td class="f-txtl">
            	<c:forEach var="d" items="${dict}">
            		<label><input type="checkbox" name="info" id="info_${d.itemCode}" value="${d.itemCode }" <c:if test="${(info.itemName).indexOf(d.itemCode)>=0 }"> checked </c:if> />${d.itemName }</label>
            	</c:forEach>
            </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">是否必填：</td>
            <td class="f-txtl">
            	<c:forEach var="d" items="${dict}">
            		<label><input type="checkbox" name="check" id="info_${d.itemCode}" value="${d.itemCode }" <c:if test="${(check.itemName).indexOf(d.itemCode)>=0 }"> checked </c:if> />${d.itemName }</label>
            	</c:forEach>
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
	var info = ",";
	$('input[name="info"]:checked').each(function(){ 
	   	info += $(this).val()+ ",";  
	  });
	var check = ",";
	$('input[name="check"]:checked').each(function(){    
	   	check += $(this).val() + ",";    
	  });
	var data = {
			"message":$("#message").attr("checked")=="checked"?1:0,
			"dialog":$("#dialog").attr("checked")=="checked"?1:0,
			"info": info,
			"check": check
	};
	$.ajax({
	    type: "post",
	    url: "/sys/updateVisitRegister.action",
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
