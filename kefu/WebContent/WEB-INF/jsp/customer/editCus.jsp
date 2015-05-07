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

<!-- 表格有边框 -->
<div style="margin:50px">
<form id="mainForm">
<table class="table table-bordered m-table">
    <tbody>
        <tr>
            <td class="f-txtl tdbg">风格：${styleName }</td>
        </tr>
        <tr>
            <td class="f-txtl tdbg">网站关键词：${dialogue.keywords }</td>
        </tr>
        <tr>
            <td class="f-txtl tdbg">咨询页面：${dialogue.consultPage }</td>
        </tr>
        <tr>
            <td class="f-txtl tdbg">客户编号：${customer.id }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">客户名称：<input type="text" id="customerName" name="customerName" value="${customer.customerName}"/>
            	<input type="hidden" id="id" name="id" value="${customer.id}"/>
            </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">手机：<input type="text" id="phone" name="phone" value="${customer.phone}"/> </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">邮箱：<input type="text" id="email" name="email" value="${customer.email}"/> </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">备注：<textarea id="remark" name="remark">${customer.remark}</textarea></td>
        </tr>
    </tbody>
</table>
                    <button type="button" class="btn btn-primary" id="btn_save">保存<i class="icon-ok icon-white"></i></button>
                    <button type="button" class="btn" id="btn_cancel">取消</button>
</form>

</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>

<script type="text/javascript">


var api = frameElement.api;//调用父页面数据  
var W = api.opener;//获取父页面对象  

//取消
$('#btn_cancel').on('click',function(){
	 api.close();
// 	 frameElement.lhgDG.cancel();
});


//保存
$('#btn_save').on('click',function(){
	var mob=$.trim($("#phone").val()); 
	if($.trim($("#phone").val())==""){
		W.$.dialog.alert("手机号码不能为空！");
		  return false;
	}else{
		var reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[01678]|18[0-9]|14[57])[0-9]{8}$/;
		if(!reg.test($.trim($('#phone').val()))){
			W.$.dialog.alert("手机号码格式不对！");
			return false;
		}
	}
	$.ajax({
		type : 'post',
		url :  "/customer/updateCus.action",
		dataType : 'json',
		data : $('#mainForm').serialize(),
	 	success: function (data) {
	    	if(data.result==0){
	    		W.$.dialog.alert('操作成功!',function(){
	    			W.editCallback(data.customer.customerName);
	    		});
	    	}else{
	    		W.$.dialog.alert(data.msg);
	    	}
	    },
	    error: function (msg) {
	    	$.dialog.alert(msg);
	    }
	});
});


</script>

</body>
</html>
