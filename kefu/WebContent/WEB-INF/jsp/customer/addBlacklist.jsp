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
        <li><i>&gt;</i>添加黑名单</li>
    </ul>
</div>
<!-- 表格有边框 -->
<h2>添加黑名单</h2>
<table  border="1" aglin="centert" class="table">
        <tr>
           <td>客户编号</td>
           <td><input type ="text" id ="customerId" name="customerId" value="1000000001"/><td>
        </tr>
         <tr>
           <td>IP地址</td>
           <td><input type ="text" id ="ip" name="ip" value="192.168.1.102"/><td>
        </tr>
         <tr>
           <td>失效时间</td>
           <td><input type ="text" id ="endDate" name="endDate" value="2015-01-28 23:58:29"/><td>
        </tr>
         <tr>
           <td>阻止原因</td>
           <td><input type ="text" id ="description" name="description" value="骂人"/><td>
        </tr>
   </table>
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
	
	



</script>
</body>
</html>
