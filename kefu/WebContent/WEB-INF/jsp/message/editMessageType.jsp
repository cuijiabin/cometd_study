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
      <table class="table table-bordered table-striped table-hover m-table">
          <tbody>
        
                 <tr>
                  <td class="c-wd50">分类编号</td>
                  <td><input type="text" id="typeId" name="typeId" value="${typeId }"/></td>
                 </tr>
              
                 <tr>
                    <td class="c-wd50">分类名称</td>
                     <td><input type="text" type="text" id="title" name="title" value="${messageType.title} " /></td>
                </tr>
             <tr>
                  <td class="c-wd50">排序</td>
                  <td><input type="text" type="text" id="sortId" name="sortId" value="${messageType.sortId }"/></td>
              </tr>
               <tr>
                 <td class="c-wd50">展示</td>
                  <td class="c-wd300"><input type="checkbox" id="replyWay"  value="${messageType.status }" <c:if test="${messageType.status==1 }"> checked </c:if>  /></td>
              </tr>
              
              <tr>
          
           <td colspan="2" align="right">
            <button  onclick="javascript:editMessageType();"  class="btn btn-primary" >保存</button>
            <button  onclick="javascript:cl();" class="btn btn-primary" >取消</button>
            </td>
        </tr>
              
          </tbody>
      </table>
 
 

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
var api = frameElement.api,W=api.opener;

function editMessageType(){
	 
	
	  var isSelect = document.getElementById("replyWay_${messageType.status }").checked;
	  var status = isSelect ? 1:2;
      alert(status);
//	  var url = "/messageType/save.action";
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
	
	return true;
}


function cl(){
	api.close();			
}

</script>
</body>
</html>
