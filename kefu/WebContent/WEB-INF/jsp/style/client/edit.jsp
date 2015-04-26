<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>    
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
        <li><i>&gt;</i>访问端广告设置</li>
    </ul>
</div>
<div style="margin:50px">
<div class="g-cnt">
    
    <div class="f-padd10">
    	<!-- 表格有边框 -->
    	<form id="mainForm" enctype="multipart/form-data" method="post" >
    		<input type="hidden" id="id" name="id" value="${clientStyle.id }"/>
    		<input type="hidden" id="styleId" name="styleId" value="${clientStyle.styleId }" />
	        <table class="table table-bordered m-table c-wdat">
	            <tbody>
	                <tr>
	                    <td class="c-wd100">头部</td>
	                    <td class="f-txtl">
	                    	<label><input type="checkbox" id="isHeadAd" name="isHeadAd" value="1" <c:if test="${clientStyle.isHeadAd == 1}">checked</c:if> />文字广告语</label>
	                    	<input type="text" id="headAd" name="headAd" value="${clientStyle.headAd }" maxlength="50" />
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">欢迎语</td>
	                    <td class="f-txtl">
	                    	<label><input type="checkbox" id="isWelcomeAd" name="isWelcomeAd" value="1" <c:if test="${clientStyle.isWelcomeAd == 1}">checked</c:if> />文字欢迎语</label>
	                    	<input type="text" id="welcomeAd" name="welcomeAd" value="${clientStyle.welcomeAd }" maxlength="50"/>
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">工具栏</td>
	                    <td class="f-txtl">
	                    	<label><input type="checkbox" id="isPhizBtn" name="isPhizBtn" value="1" <c:if test="${clientStyle.isPhizBtn == 1}">checked</c:if> />表情按钮功能</label>
	                    	<label><input type="checkbox" id="isUserGrade" name="isUserGrade" value="1" <c:if test="${clientStyle.isUserGrade == 1}">checked</c:if> />客服评分功能</label>
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">右上侧</td>
	                    <td class="f-txtl">
	                    	<label><input type="checkbox" id="isYsAd" name="isYsAd" value="1" <c:if test="${clientStyle.isYsAd == 1}">checked</c:if> />广告图</label>
	                        <input type="file" style="width:170px;height:initial;line-height:normal;" id="fileYs" name="fileYs" />
	                    </td>
	                    <td class="f-txtl"><img src="${ysUrl }" alt=""></td>
	                    
	                </tr>
	                <tr>
	                    <td class="c-wd100">右下侧</td>
	                    <td class="f-txtl">
	                    	<label><input type="checkbox" id="isYxAd" name="isYxAd" value="1" <c:if test="${clientStyle.isYxAd == 1}">checked</c:if> />广告图</label>
	                        <input type="file" id="fileYx" name="fileYx" style="width:170px;height:initial;line-height:normal;" />
	                    </td>
	                    <td class="f-txtl"><img src="${yxUrl }" alt="" /></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">底部</td>
	                    <td class="f-txtl">
	                    	<label><input type="checkbox" id="isButtomAd" name="isButtomAd" value="1" <c:if test="${clientStyle.isButtomAd == 1}">checked</c:if> />文字广告语</label>
	                    	<input type="text" id="buttomAd" name="buttomAd" value="${clientStyle.buttomAd }" maxlength="50"/>
	                    </td>
	                    <td class="f-txtl"></td>
	                </tr>
	                <tr>
	                    <td class="c-wd100">等待列表</td>
	                    <td class="f-txtl"><a href="#" onclick="editWait(${clientStyle.styleId})">配置</a></td>
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


//保存
$('#btn_save').on('click',function(){
	//校验文件类型
	var filepath=$("input[name='fileYs']").val();
	if(filepath!=''){
		var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
	    	$.dialog.alert("图片限于bmp,png,gif,jpeg,jpg格式!");
	    	return;
	    }
	}
	filepath=$("input[name='fileYx']").val();
	if(filepath!=''){
		extStart=filepath.lastIndexOf(".");
	    ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
	    	$.dialog.alert("图片限于bmp,png,gif,jpeg,jpg格式!!");
	    	return;
	    }
	}
	
	var path = "/clientStyle/save.action";  
    $('#mainForm').attr("action", path).submit();
});

//等待列表
function editWait(styleId){
//  	var url = '/waitList/edit.action?styleId='+styleId;
//  	window.location = url;
//  	return;
	$.dialog({content:'url:/waitList/edit.action?styleId='+styleId,
		id: 'wait',
		width: 900,height: 700,
		title:'等待列表配置',
		lock:true
	});
}


</script>
</body>
</html>


