<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<%@ taglib uri="/WEB-INF/xiaoma.tld" prefix="xiaoma" %>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li>系统设置</li>
        <li><i>&gt;</i>风格管理</li>
        <li><i>&gt;</i>分配机制</li>
    </ul>
</div>

<div class="g-cnt" style="width:900px">
    <div class="f-padd10">
    	<form id="mainForm" method="post">
    		<input type="hidden" id="id" name="id" value="${rule.id}"/>
            <input type="hidden" id="styleId" name="styleId" value="${rule.styleId}" />
	    	<div class="m-tit">
	            <h3>第一规则</h3>
	        </div>
	        <p class="f-padd20">
	        	<c:forEach var="first" items="${rule1}">
		           	 <label>
		           	 	<input type="checkbox" name="firstRule" value="${first.itemCode }" 
		           	 		<c:if test="${rule.firstRule == first.itemCode }"> checked </c:if>  />${first.itemName }
		           	 </label>
		        </c:forEach>
	        </p>
	    	<div class="m-tit">
	            <h3>第二规则</h3>
	        </div>
	        <p class="f-padd20">
				<c:forEach var="sec" items="${rule2}">
		           	 <label>
		           	 	<input type="radio" name="secondRule" value="${sec.itemCode }" 
		           	 		<c:if test="${rule.secondRule == sec.itemCode }"> checked </c:if>  />${sec.itemName }
		           	 </label>
		        </c:forEach>
	        </p>
	    	<div class="m-tit">
	            <h3>第三规则</h3>
	        </div>
	        <p class="f-padd20">
				<c:forEach var="third" items="${rule3}">
		           	 <label>
		           	 	<input type="radio" name="thirdRule" value="${third.itemCode }" 
		           	 		<c:if test="${rule.thirdRule == third.itemCode }"> checked </c:if>  />${third.itemName }
		           	 </label>
		        </c:forEach>	
	        </p>
	    </form>
    </div>
    
     <div class="m-query f-mar10">
        <div class="m-query-hd">
      		<button type="button" class="btn btn-primary btn-small" id="btn_save">保存</button>
        </div>
    </div>  
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
//自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

//保存
$('#btn_save').on('click',function(){
	$.ajax({
	    type: "post",
	    url: "/allotRule/save.action",
	    dataType: "json",
	    data : $('#mainForm').serialize(),
// 	    contentType: "application/json; charset=utf-8",
	    async:false,
	    success: function (data) {
	    	if(data.result==0){
	    		$.dialog.alert(data.msg,function(){
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
    return false;
});


</script>
</body>
</html>

