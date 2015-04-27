<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib uri="/WEB-INF/xiaoma.tld" prefix="xiaoma" %> 
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>访问端广告设置--客服信息管理系统</title>
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
        <li>首页</li>
        <li><i>&gt;</i>系统设置</li>
        <li><i>&gt;</i>风格管理</li>
        <li><i>&gt;</i>界面样式设置</li>
        <li><i>&gt;</i>对话邀请框设置</li>
    </ul>
</div>
<div style="margin:50px">
<div class="g-cnt">
    
    <div class="f-padd10">
    	<!-- 表格有边框 -->
    	<form id="mainForm" enctype="multipart/form-data" method="post">
    		<input type="hidden" id="id" name="id" value="${inviteIcon.id }"/>
    		<input type="hidden" id="styleId" name="styleId" value="${inviteIcon.styleId }" />
    		<input type="hidden" id="deviceType" name="deviceType" value="${inviteIcon.deviceType }" />
        	<table class="table table-bordered m-table c-wdat">
	            <tbody>
	                <tr>
	                    <td class="c-wd100">位置定位</td>
	                    <td class="f-db f-txtl">
	                    	<div class="f-mbm">
	                    		<c:forEach var="d" items="${dict}">
	            					<label><input type="radio" name="locationMode" id="locationMode_${d.itemCode}" value="${d.itemCode }" <c:if test="${inviteIcon.locationMode == d.itemCode }"> checked </c:if> />${d.itemName }</label>
	            				</c:forEach>
	                        </div>
	                    	<div class="f-mbm">
	                        	水平定位 距离
	                            <xiaoma:select name="siteZy" dictName="d_site_level" value="${inviteIcon.siteZy }" />
	                            <input class="c-wd50" type="text"  id="siteZyPx" name="siteZyPx" value="${inviteIcon.siteZyPx }" maxlength="4" >
	                            像素
	                        </div>
	                    	<div>
	                        	垂直定位 距离
	                            <xiaoma:select name="siteDd" dictName="d_site_vertical" value="${inviteIcon.siteDd }" />
	                            <input class="c-wd50" type="text"  id="siteDdPx" name="siteDdPx" value="${inviteIcon.siteDdPx }" maxlength="4" >
	                            像素
	                        </div>
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">样式</td>
	                    <td class="f-txtl"><img src="http://oc2.xiaoma.com//img/upload/53kf/zdytb/off_53kf1407116979.png" alt="" /></td>
	                    <td class="c-wd100"><a href="#" onclick="editElement(${inviteIcon.id})">编辑</a></td>
	                </tr>
	            </tbody>
	        </table>
	    </form>
    </div>
    
    <div class="m-query f-mar10">
        <div class="m-query-hd">
        	<button type="button" class="btn btn-primary btn-small" id="btn_save">保存</button>
        </div>
    </div>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);


//保存
$('#btn_save').on('click',function(){
	if(!isNaturalNum($("#siteZyPx").val())){
		$.dialog.alert("水平定位像素请填写大于等于0的整数!");
		return;
	}
	if(!isNaturalNum($("#siteDdPx").val())){
		$.dialog.alert("垂直定位像素请填写大于等于0的整数!");
		return;
	}
	var path = "/inviteIcon/saveYD.action";  
    $('#mainForm').attr("action", path).submit();
});

//编辑元素
function editElement(id){
	var url = '/inviteElement/editYD.action?inviteId='+id;
	window.location = url;
	return;
}

</script>
</body>
</html>


