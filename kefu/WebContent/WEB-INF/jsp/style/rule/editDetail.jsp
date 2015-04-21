<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/jsplugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">系统设置</a></li>
        <li><i>&gt;</i><a href="#">风格管理</a></li>
        <li><i>&gt;</i><a href="#">业务分流</a></li>
    </ul>
</div>

<div class="g-cnt">
   	<form id="mainForm" method="post" >
	    <div class="f-padd10">
	    <!-- 表格有边框 -->
	        <table class="table m-table m-table1 m-table2 c-wdat c-bor">
	      	    <c:forEach var="group" items="${groupList }">
	      	    	<thead>
		                <tr>
		                    <td class="f-db f-txtl c-bg">
		                    	<label>
		                    		<input type="checkbox" id="group${group.id }" name="group" onclick="javascript:checkedAll(${group.id});" >
		                    		${group.name }
		                    	</label>
		                   	</td>
		                </tr>
	            	</thead>
	            	<tbody>
		                <tr>
		                    <td class="f-db f-txtl">
		                    	<c:forEach var="detail" items="${detailMap[group.id] }" varStatus="status">
		                    		<c:if test="${status.index % 8 == 0 }"> <br /> </c:if>  
			        				<label class="f-mar20">
			        					<input type="hidden" name="ids" value="${detail.id }" />
			        					<input type="checkbox" name="detail${group.id }" <c:if test="${detail.isReception == 1 }"> checked="true" </c:if> />
			        					${detail.cardName }
			        				</label>
								</c:forEach>
		                    </td>
		                </tr>
	            	</tbody>
				</c:forEach>
			</table>
	    </div>
   </form>
    
    <div class="m-query f-mar10">
        <div class="m-query-hd">
      		<button type="button" class="btn btn-primary btn-small" onclick="save()">保存</button>
        </div>
    </div>
    
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

//全选/全不选
function checkedAll(groupId){
	var groupName = 'group'+groupId;
	var detaidName = 'detail'+groupId;
	if(($("#"+groupName+"").is(":checked"))){
		$("input[name='"+detaidName+"']").each(function() {  
            $(this).attr("checked", true); 
        }); 
	}else{
		$("input[name='"+detaidName+"']").each(function() {  
       		 $(this).attr("checked", false); 
   		 }); 
	}
}

//保存
function save(){
	var ids = $("input[name='ids']");
	var checks = $("input[name^='detail']");
	
	var str = '';
	for (var i=0; i<ids.length; i++){
		var isCheck = 0 ;
		if(checks[i].checked){
			isCheck = 1;
		};
		if(str!=''){
			str+=","
		}
		str+=ids[i].value+":"+isCheck;
	}
	$.ajax({
		type : 'post',
		url :  "/busiGroupDetail/saveDetail.action",
		dataType : 'json',
		data: {data:str} ,
		async: false,
	 	success: function (data) {
	    	if(data.result==0){
	    		$.dialog.alert('操作成功!',function(){
	    			window.location.reload();			
	    		});
	    	}else{
	    		$.dialog.alert(data.msg);
	    	}
	    },
	    error: function (msg) {
	    	$.dialog.alert(msg);
	    }
	});
}

</script>
</body>
</html>

