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
        <li><i>&gt;</i>客服图标设置</li>
    </ul>
</div>

<div class="g-cnt">
    
    <div class="f-padd10">
    	<!-- 表格有边框 -->
    	<form id="mainForm" enctype="multipart/form-data" method="post">
    		<input type="hidden" id="id" name="id" value="${serviceIcon.id }"/>
    		<input type="hidden" id="styleId" name="styleId" value="${serviceIcon.styleId }" />
    		<input type="hidden" id="deviceType" name="deviceType" value="${serviceIcon.deviceType }" />
	        <table class="table table-bordered m-table c-wdat">
	            <tbody>
	                <tr>
	                    <td class="c-wd100">隐藏图标</td>
	                    <td class="f-db f-txtl">
	                        <label><input type="radio" name="isDisplay" value="1" <c:if test="${serviceIcon.isDisplay==1 }"> checked </c:if> />是</label>
	                        <label><input type="radio" name="isDisplay" value="0" <c:if test="${serviceIcon.isDisplay==0 }"> checked </c:if> />否</label>
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">显示方式</td>
	                    <td class="f-txtl">
	                    	<c:forEach var="d" items="${dict}">
            					<label><input type="radio" name="displayMode" id="displayMode_${d.itemCode}" value="${d.itemCode }" <c:if test="${serviceIcon.displayMode == d.itemCode }"> checked </c:if> />${d.itemName }</label>
            				</c:forEach>
<!-- 	                        <div> -->
<!-- 	                        	<label><input type="radio" name="1" />浮动图标</label> -->
<!-- 	                        	<label><input type="radio" name="1" />浮动固定模式</label> -->
<!-- 	                        </div> -->
<!-- 	                        <div> -->
<!-- 	                        	<label><input type="radio" name="1" />嵌入固定位置</label> -->
<!-- 	                        	<label><input type="radio" name="1" />浮动伸缩模式</label> -->
<!-- 	                        </div> -->
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">浮动位置</td>
	                    <td class="f-txtl">
	                    	<div class="f-mbm">
	                        	水平定位 距离 
	                        	<xiaoma:select name="siteZy" dictName="d_site_level" value="${serviceIcon.siteZy }" />
<!-- 	                            <select class="c-wdat"> -->
<!-- 	                                <option>右侧</option> -->
<!-- 	                                <option>左侧</option> -->
<!-- 	                            </select> -->
	                            <input class="c-wd50" type="text" id="siteZyPx" name="siteZyPx" value="${serviceIcon.siteZyPx }" maxlength="4" >
	                            像素
	                        </div>
	                    	<div>
	                        	垂直定位 距离 
	                        	<xiaoma:select name="siteDd" dictName="d_site_vertical" value="${serviceIcon.siteDd }" />
<!-- 	                            <select class="c-wdat"> -->
<!-- 	                                <option>顶部</option> -->
<!-- 	                                <option>底部</option> -->
<!-- 	                            </select> -->
	                            <input class="c-wd50" type="text" id="siteDdPx" name="siteDdPx" value="${serviceIcon.siteDdPx }" maxlength="4" >
	                            像素
	                        </div>
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">在线图标</td>
	                    <td class="f-txtl"><input type="file" style="width:170px;height:initial;line-height:normal;" id="fileOn" name="fileOn" /></td>
	                    <td class="f-txtl"><img src="${onUrl }" alt="" /></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">离线图标</td>
	                    <td class="f-txtl"><input type="file" style="width:170px;height:initial;line-height:normal;" id="fileOff" name="fileOff" /></td>
	                    <td class="f-txtl"><img src="${offUrl }" alt="" /></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">图标尺寸</td>
	                    <td class="f-txtl">
	                    	<div class="f-mbm">
	                        	宽度
	                            <input class="c-wd50" type="text" id="width" name="width" value="${serviceIcon.width }" maxlength="4" >
	                            像素
	                        </div>
	                    	<div>
	                        	高度
	                            <input class="c-wd50" type="text" id="height" name="height" value="${serviceIcon.height }" maxlength="4" >
	                            像素
	                        </div>
	                    </td>
	                    <td class="f-txtl"></td>
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
	
	if(!isInteger($("#siteZyPx").val())){
		$.dialog.alert("水平定位像素请填写正整数!");
		return;
	}
	if(!isInteger($("#siteDdPx").val())){
		$.dialog.alert("垂直定位像素请填写正整数!");
		return;
	}
	if(!isInteger($("#width").val())){
		$.dialog.alert("宽度请填写正整数!");
		return;
	}
	if(!isInteger($("#height").val())){
		$.dialog.alert("高度请填写正整数!");
		return;
	}
	
	//校验文件类型
	var filepath=$("input[name='fileOn']").val();
	if(filepath!=''){
		var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
	    	$.dialog.alert("图片限于bmp,png,gif,jpeg,jpg格式!");
	    	return;
	    }
	}
    
    
	filepath=$("input[name='fileOff']").val();
	if(filepath!=''){
		extStart=filepath.lastIndexOf(".");
	    ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
	    	$.dialog.alert("图片限于bmp,png,gif,jpeg,jpg格式!!");
	    	return;
	    }
	}
	
	var path = "/serviceIcon/savePC.action";  
    $('#mainForm').attr("action", path).submit();
});


</script>
</body>
</html>


