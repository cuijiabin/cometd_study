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
<form id="mainForm">
<table class="table table-bordered m-table">
    <tbody>
        <tr>
            <td class="f-txtr tdbg">风格：${dialogue.styleId }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">网站关键词：${dialogue.keywords }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">咨询页面：${dialogue.consultPage }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">客户编号：${customer.id }</td>
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
            <td class="f-txtr tdbg">备注：<input type="text" id="remark" name="remark" value="${customer.remark}"/> </td>
        </tr>
    </tbody>
</table>
                    <button type="submit" class="btn btn-primary" id="btn_save">保存<i class="icon-ok icon-white"></i></button>
                    <button type="reset" class="btn" id="btn_cancel">取消</button>
</form>


<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>

<script type="text/javascript">


var api = frameElement.api;//调用父页面数据  
var W = api.opener;//获取父页面对象  
// //以下代码为弹出层页面添加按钮  
// api.button({  
//     id:'valueOk',  
//     name:'确定'    
// });  
// api.button({  
//     id:'cancel',  
//     name:'关闭'  
// }); 

//取消
$('#btn_cancel').on('click',function(){
	 api.close();
// 	 frameElement.lhgDG.cancel();
});

//保存
$('#btn_save').on('click',function(){
	$.ajax({
		type : 'post',
		url :  "/customer/updateCus.action",
		dataType : 'json',
		data : $('#mainForm').serialize(),
		async:false,
	 	success: function (data) {
	    	if(data.result==0){
	    		W.$.dialog.alert('操作成功!',function(){
	    			W.editCallback();
	    		});
	    	}else{
	    		$.dialog.alert(data.msg);
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
