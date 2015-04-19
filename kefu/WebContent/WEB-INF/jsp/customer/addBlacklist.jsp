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
<!-- 表格有边框 -->
<div style="margin:30px;">
<table class="table table-bordered m-table">
    <tbody>
        <tr>
          <td>客户编号</td>
           <td><input type ="text" id ="customerId" name="customerId" value=""/><span id="customerIdInfo" style = "color: red;">*</span></td>
        </tr>
         <tr>
           <td>IP地址</td>
           <td><input type ="text" id ="ip" name="ip" onblur="javascript:checkBlacklist();" /><span id="ipInfo" style = "color: red">*</span></td>
        </tr>
         <tr> 
           <td>失效时间</td>
           <td><input id="endDate" name="endDate"  type="text"  readonly="readonly" value="${endDate }" /><span id="endDateInfo" style = "color: red;">*</span></td>
        </tr>
         <tr>
           <td>阻止原因</td>
           <td><input type ="text" id ="description" name="description" /><span id="descriptionInfo" style = "color: red;">*</span></td>
        </tr>
         <tr>
          
           <td colspan="2" align="right">
            <button  onclick="javascript:addBlacklist(${blacklist.id});"  class="btn btn-primary" >保存</button>
            <button  onclick="javascript:cl();" class="btn btn-primary" >取消</button>
            </td>
        </tr>
        </tbody>
   </table>
   </div>

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
    				W.$.dialog.alert('操作成功!',function(){
    					W.addCallback();		
    	    		});

    			} else {
    				W.$.dialog.alert(data.msg);
    			}
    		},
    		error : function(msg) {
    			W.$.dialog.alert(data.msg);
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
	
	var   customerIdParam =/^(-|\+)?\d+$/;
	if (!customerIdParam.test(customerId)) {
		alert("客户编号不得为中文");
		return false;
	}
	
//      //校验访客是否存在
// 	if(checkCustomer()){
// 	    alert("此IP地址已存在！");
// 	   return false;
//    }
	
	var ip = userData.ip;
	if (ip.replace("^[ ]+$", "").length == 0) {
		 alert("IP地址不得为空！");
		return false;
	}
 	  var ipParam =/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
		if (!ipParam.test(ip)) {
			alert("ip地址格式不正确");
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
 * 校验黑名单iP的唯一性(添加)
 */
function checkBlacklist(){
	var flag = false;
	if($("#ip").val()==''){
		$("#ipInfo").html("*");
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
				$("#ipInfo").html("*");
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
