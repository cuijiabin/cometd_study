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
  	  <input type="text" readonly="readonly" id="pId" name="pId" value="${treeId}"/>
  	   <div style="margin:50px;">
  <table class="table table-bordered m-table">
          <tbody>
           <tr>
	          <td class="f-txtr tdbg" width="150px">分类编号</td>
	          <td class="f-txtl"><input type="text" readonly="readonly" id="typeId" name="typeId" value="${typeId }"/></td>
           </tr>
            <tr>
               <td class="f-txtr tdbg">分类名称</td>
                <td class="f-txtl"><input type="text" type="text" maxlength="20" id="title" name="title" /></td>
           </tr>
           <tr>
                <td class="f-txtr tdbg">排序</td>
                <td class="f-txtl"><input type="text" type="text" readonly="readonly"  id="sortId" name="sortId" value="${sortId} "/></td>
            </tr>
            <tr>
              <td class="f-txtr tdbg">展示</td>
               <td class="f-txtl"><input style="margin-left: 90px" id="statusCk" name="statusCk" type="checkbox"  /></td>
           </tr>
         <tr>
            <td colspan="2" align="right">
            <button  onclick="javascript:addMessageType();"  class="btn btn-primary" >保存</button>
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

function addMessageType(){
	  var isSelect = document.getElementById("statusCk").checked;
	  var status = isSelect ? 1:2;

	  var url = "/messageType/save.action";
	  var data = {
	    "pId"   : $("#pId").val(),  
	  	"typeId" : $("#typeId").val(),
	  	"title" : $("#title").val(),
	  	"sortId" : $("#sortId").val(),
	  	"status" : status
	   };
	  
	//新增时验证参数
		if (!verificationParam(data)) {
			return;
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

	var title = userData.title;
	if (title.replace(/^ +| +$/g,'')=='') {
		 alert("分类名称不得为空 ！");
		return false;
	}
	
	var sortId = userData.sortId;
	if (sortId.replace(/^ +| +$/g,'')=='') {
		 alert("排序不得为空 ！");
		return false;
	}
	
	return true;
}

function cl(){
	api.close();			
}
</script>
</body>
</html>
