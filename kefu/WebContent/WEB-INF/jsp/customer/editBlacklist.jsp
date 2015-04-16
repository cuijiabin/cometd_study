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
        <li><a href="#">访客管理</a></li>
        <li><i>&gt;</i><a href="#">黑名单</a></li>
        <li><i>&gt;</i>修改黑名单</li>
    </ul>
</div>
<!-- 表格有边框 -->
<h2>修改黑名单</h2>

<table  border="1" aglin="centert" class="table">

        <tr>
           <td></td>
           <td><input type ="hidden" id ="blacklistId" name="blacklistId" value="${blacklist.id}"/><span id="blacklistIdInfo" style = "color: red;">*</span><td>
        </tr>
        <tr>
           <td>客户编号</td>
           <td><input type ="text" readonly="readonly" id ="customerId" name="customerId" value="${blacklist.customerId}"/><span id="customerIdInfo" style = "color: red">*</span><td>
        </tr>
         <tr>
           <td>IP地址</td>
           <td><input type ="text" id ="ip" readonly="readonly" name="ip" value="${blacklist.ip}"/><span id="ipInfo" style = "color: red;">*</span><td>
        </tr>
         <tr>
           <td>失效时间</td>
           <td><input type ="text" id ="endDate" name="endDate" value="${blacklist.endDate}"/><span id="endDateInfo" style = "color: red;">*</span><td>
        </tr>
         <tr>
           <td>阻止原因</td>
           <td><input type ="text" id ="description" name="description" value="${blacklist.description}"/><span id="descriptionInfo" style = "color: red;">*</span><td>
        </tr>
   </table>
   <button type="submit" class="btn btn-primary" id="btn_save">保存<i class="icon-ok icon-white"></i></button>
<!--        <button type="submit" class="btn btn-primary" onclick="update()">保存<i class="icon-ok icon-white"></i></button> -->
                    <button type="reset" class="btn" id="btn_cancel">取消</button>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">


var api = frameElement.api;//调用父页面数据  
var W = api.opener;//获取父页面对象  

$('#btn_cancel').on('click',function(){
	 api.close();

});

//保存
$('#btn_save').on('click',function(){
	alert("修改确定");
	var data = {
		"id"  : $("#blacklistId").val(),
		"customerId" : $("#customerId").val(),
		"ip" : $("#ip").val(),
		"enddate" : $("#endDate").val(),
		"description" : $("#description").val()
	};
	//修改时参数验证
	if (!updateParam(data)) {
		return;
	}
	$.ajax({
		type : 'post',
		url : "/blacklist/update.action",
		dataType : 'json',
		data : data,
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


function update(){
	url : "/blacklist/update.action";
	var data = {
			"id"  : $("#blacklistId").val(),
		"customerId" : $("#customerId").val(),
		"ip" : $("#ip").val(),
		"description" : $("#description").val(),
		"enddate" : $("#endDate").val()
	};
	alert("修改确定hou");
	$.ajax({
		type : "post",
		url : url,
		data : data,
		dataType : "json",
		async:false,
		success : function(data) {
			if (data.result == 0) {
				alert(data.msg);
				$("#customerId").val('');
				$("#ip").val('');
				$("#endDate").val('');
				$("#description").val('');
				location.reload();
			} else {
				alert(data.msg);
			}
		},
		error : function(msg) {
			alert(data.msg);
		}
	});
  }
  
/**
 *  js 校验
 */
function updateParam(userData) {
	var customerId = userData.customerId;
	if (customerId.replace("^[ ]+$", "").length == 0) {
		 alert("客户编号不得为空！");
		return false;
	}
	
	var ip = userData.ip;
	if (ip.replace("^[ ]+$", "").length == 0) {
		 alert("IP地址不得为空！");
		return false;
	}
	


	var enddate = userData.enddate;
	if (enddate.replace("^[ ]+$", "").length == 0) {
		 alert("失效时间不得为空！");
		return false;
	}

	var description	 = userData.description;
	if (description.replace("^[ ]+$", "").length == 0) {
		 alert("阻止原因不得为空！");
		return false;
	}

	return true;
}




</script>
</body>
</html>
