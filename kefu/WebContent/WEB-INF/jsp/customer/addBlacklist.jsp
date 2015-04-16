<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>   
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
        <li><i>&gt;</i>添加黑名单</li>
    </ul>
</div>
<!-- 表格有边框 -->
<h2>添加黑名单</h2>
<table  border="1" aglin="centert" class="table">
        <tr>
          <td>客户编号</td>
           <td><input type ="text" id ="customerId" name="customerId" value=""/><span id="customerIdInfo" style = "color: red;">*</span><td>
        </tr>
         <tr>
           <td>IP地址</td>
           <td><input type ="text" id ="ip" name="ip" onblur="javascript:checkBlacklist();" /><span id="ipInfo" style = "color: red">*</span><td>
        </tr>
         <tr> 
           <td>失效时间</td>
           <td><input type ="text" id ="endDate" name="endDate" /><span id="endDateInfo" style = "color: red;">*</span><td>
        </tr>
         <tr>
           <td>阻止原因</td>
           <td><input type ="text" id ="description" name="description" /><span id="descriptionInfo" style = "color: red;">*</span><td>
        </tr>
   </table>
 <button style="float:right;margin-right:40px;" onclick="javascript:cl();" class="btn" >关闭</button>
<button style="float:right;margin-right:40px;" onclick="javascript:addBlacklist(${blacklist.id});"  class="btn" >保存</button>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
var api = frameElement.api,W=api.opener;

function addBlacklist(id){
	var url="";
	var data="";
  if(id!=undefined){
	   url="/blacklist/update.action";
	   data = {
			   "id":id,
				"name" : $("#name").val(),
				"sortNum": $("#sortNum").val()
			};
	
  }else{
	  var url = "/blacklist/save.action";
	  var data = {
	  	"customerId" : $("#customerId").val(),
	  	"ip" : $("#ip").val(),
	  	"description" : $("#description").val(),
	  	"enddate" : $("#endDate").val()
	  };
	  
		//新增时验证参数
		if (!verificationParam(data)) {
			return;
		}
	  
     }
    
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
 *  js 校验添加
 */
function verificationParam(userData) {
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
	
	if(checkBlacklist()){
	    alert("此IP地址已存在！");
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
/*
 * 校验黑名单的唯一性(添加)
 */
function checkBlacklist(){
	var flag = false;
	if($("#ip").val()==''){
		$("#ipInfo").html("IP地址不能为空!");
		return true;
	}else {
      	$("#ipInfo").html("*");
	}
	
	$.ajax({
		type : "get",
		url : "/blacklist/check.action",
		data : {'ip':$("#ip").val()},
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		async:false,
		success : function(data) {
			if(data.code==0){
				$("#ipInfo").html("*");
				
			}
			
			else{
				alert(flag);
				$("#ipInfo").html("该IP地址已存在!");
			 	flag = true;
			 
			}
		},
		error : function(msg){
			alert("查询失败!");
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
