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
  	<h3  class="u-tit c-bg c-bortit">分类信息</h3>
  	<input type="hidden" id="treeId" name="treeId" value="${messageType.id }"/>
    <input type="hidden" id="pId" name="pId" value="${messageType.pId }"/>
    <div style="margin:50px;">
		<table class="table table-bordered m-table">
          <tbody>
                 <tr>
                 <td class="f-txtr tdbg" width="150px">分类编号</td>
                  <td class="f-txtl"><input type="text" readonly="readonly" id="typeId" name="typeId" value="${messageType.typeId }"/></td>
                 </tr>
              
                 <tr>
                    <td class="f-txtr tdbg">分类名称</td>
                     <td class="f-txtl"><input type="text" type="text" maxlength="20" id="title" name="title" value="${messageType.title} " /></td>
                </tr>
             <tr>
                  <td class="f-txtr tdbg">排序</td>
                  <td class="f-txtl"><input type="text" type="text" readonly="readonly" id="sortId" name="sortId" value="${messageType.sortId }"/></td>
              </tr>
               <tr>
                 <td class="f-txtr tdbg">展示</td>
                  <td class="f-txtl"><input style="margin-left: 90px" type="checkbox" id="statusCk"  value="${messageType.status }" <c:if test="${messageType.status==1 }"> checked </c:if>  /></td>
              </tr>
              <tr>
		           <td colspan="2" align="right">
		            <button  onclick="javascript:editMessageType();"  class="btn btn-primary" >保存</button>
		            <button  onclick="javascript:cl();" class="btn btn-primary" >取消</button>
		            </td>
		        </tr>
              
          </tbody>
      </table>
 </div>
 

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
var api = frameElement.api,W=api.opener;

function editMessageType(){
	  var isSelect = document.getElementById("statusCk").checked;
	  var status = isSelect ? 1:2;
    
	  var url = "/messageType/update.action";
	  var data = {
	   "id"   : $("#treeId").val(),  
	    "pId"   : $("#pId").val(),  
	  	"typeId" : $("#typeId").val(),
	  	"title" : $("#title").val(),
	  	"sortId" : $("#sortId").val(),
	  	"status" : status
	   };
	  //修改时验证参数
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
    					W.editCallback();	
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
	
	return true;
}


function cl(){
	api.close();			
}

</script>
</body>
</html>
