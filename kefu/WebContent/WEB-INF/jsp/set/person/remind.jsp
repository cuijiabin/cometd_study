<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
<%
	    String path = request.getContextPath();
	    String basePath = request.getScheme() + "://"
	            + request.getServerName() + ":" + request.getServerPort()
	            + path + "/";
	%> 
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>提醒方式--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link href="/jsplugin/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">个人设置</a></li>
        <li><i>&gt;</i>提醒方式</li>
    </ul>
</div>
<div class="g-cnt">
<div class="g-cnt" style="width: 1000px"> 
    <div class="f-padd10">
    <form action="/function/saveRemind.action" enctype="multipart/form-data" method="post" id="fform">
    	<div class="m-tit">
            <h3>访客上线提醒</h3>
        </div>
        <div class="f-padd20">
        	<label><input  type ="checkbox" id="lsoundEffect" name="lsoundEffect" value="1" <c:if test='${remind.lsoundEffect==1}'>checked</c:if>/> 播音效</label>
            <div style="padding-left:30px;line-height:30px;">
                <label><input type ="radio" id="lineEffect" name="lineEffect"  value="1" checked="checked" <c:if test='${remind.lineEffect==1}'>checked</c:if>/> 默认音效</label>
                <p class="u-subsec f-fl">
                    <label><input type ="radio" id="lineEffect" name="lineEffect"  value="2" <c:if test='${remind.lineEffect==2}'>checked</c:if>/> 自定义</label>
                </p>
                <p class="u-upload f-fl">
                    <button class="btn" type="button">上传文件</button>
                    <input type="file" id="lsound" name="lsound"/>
                </p>
                <span class="help-inline c-clred">请上传小于200KB，格式为mp3,wav的音效文件</span>
            </div>
            <div class="clear"></div>
        	<label><input type ="checkbox" id="bubble" name="bubble" value="1" <c:if test='${remind.bubble==1}'>checked</c:if>/> 冒气泡框</label>
        </div>
    	<div class="m-tit">
            <h3>访客建立对话</h3>
        </div>
        <div class="f-padd20">
        	<label><input  type ="checkbox" id="jsoundEffect" name="jsoundEffect" value="1" <c:if test='${remind.jsoundEffect==1}'>checked</c:if>/> 播音效</label>
            <div style="padding-left:30px;line-height:30px;">
                <label><input  type ="radio" id="createEffect" name="createEffect" value="1"  checked="checked" <c:if test='${remind.createEffect==1}'>checked</c:if>/> 默认音效</label>
                <p class="u-subsec f-fl">
                    <label><input  type ="radio" id="createEffect" name="createEffect" value="2" <c:if test='${remind.createEffect==2}'>checked</c:if>/> 自定义</label>
                </p>
                <p class="u-upload f-fl">
                    <button class="btn" type="button">上传文件</button>
                    <input type="file" id="jsound" name="jsound">
                </p>
                <span class="help-inline c-clred">请上传小于200KB，格式为mp3,wav的音效文件</span>
            </div>
        </div>
    	<div class="m-tit">
            <h3>收到访客消息</h3>
        </div>
        <div class="f-padd20">
        	<label><input  type ="checkbox" id="reSoundEffect" name="reSoundEffect" value="1" <c:if test='${remind.reSoundEffect==1}'>checked</c:if>/>播音效</label>
            <div style="padding-left:30px;line-height:30px;">
                <label><input  type ="radio" id="receiveEffect" name="receiveEffect" value="1" checked="checked" <c:if test='${remind.receiveEffect==1}'>checked</c:if>/> 默认音效</label>
                <p class="u-subsec f-fl">
                    <label><input  type ="radio" id="receiveEffect" name="receiveEffect" value="2" <c:if test='${remind.receiveEffect==2}'>checked</c:if>/>自定义</label>
                </p>
                <p class="u-upload f-fl">
                
                    <button class="btn" type="button">上传文件</button>
                    <input type="file" id="resound" name="resound">
                </p>
                <span class="help-inline c-clred">请上传小于200KB，格式为mp3,wav的音效文件</span>
            </div>
            <div class="clear"></div>
        	<label><input  type ="checkbox" id="upHint" name="upHint" value="1" <c:if test='${remind.upHint==1}'>checked</c:if>/> 弹出提示</label>
            <div style="padding-left:30px;line-height:30px;">
                <label><input  type ="radio" id="upWdInform" name="upWdInform" value="1"  checked="checked"<c:if test='${remind.upWdInform==1}'>checked</c:if>/> 弹出窗口</label>
                <label><input  type ="radio" id="upWdInform" name="upWdInform" value="2" <c:if test='${remind.upWdInform==2}'>checked</c:if>/> 弹出通知</label>
            </div>
        </div>
        </form>
        <div class="form-actions f-txtc mar0">
            <button type="submit" class="btn btn-primary" onclick="saveRemind()">确定 <i class="icon-ok icon-white"></i></button>
            <button type="reset" class="btn" onclick="removecheck()">取消</button>
        </div>
    </div>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/jsplugin/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

function saveRemind(){
	var lsound=$("#lsound").val();
	if(lsound!=''){
		extStart=lsound.lastIndexOf(".");
	    ext=lsound.substring(extStart,lsound.length).toUpperCase();
	    if(ext!=".MP3"&&ext!=".WAV"){
	    	$.dialog.alert("上线提醒请选格式为mp3,wav的音效文件");
	    	return;
	    }
	   var obj_file = document.getElementById("lsound");
	    if(obj_file.files[0].size>204800){
	    	$.dialog.alert("上线提醒的文件过大，请按要求操作");
	    	return;
	    }
	    
	}
	var jsound=$("#jsound").val();
	if(jsound!=''){
		extStart=jsound.lastIndexOf(".");
	    ext=jsound.substring(extStart,jsound.length).toUpperCase();
	    if(ext!=".MP3"&&ext!=".WAV"){
	    	$.dialog.alert("建立对话请选格式为mp3,wav的音效文件");
	    	return;
	    }
	    var obj_file = document.getElementById("jsound");
	    if(obj_file.files[0].size>204800){
	    	$.dialog.alert("建立对话的文件过大，请按要求操作");
	    	return;
	    }
	}
	var resound=$("#resound").val();
	if(resound!=''){
		extStart=resound.lastIndexOf(".");
	    ext=resound.substring(extStart,resound.length).toUpperCase();
	    if(ext!=".MP3"&&ext!=".WAV"){
	    	$.dialog.alert("收到信息请选格式为mp3,wav的音效文件");
	    	return;
	    }
	    var obj_file = document.getElementById("resound");
	    if(obj_file.files[0].size>204800){
	    	$.dialog.alert("收到信息的文件过大，请按要求操作");
	    	return;
	    }
	}

   $("#fform").submit();
}


function removecheck(){
    $(":checkbox[checked='checked']").prop("checked",false);
    $(":radio[checked='checked']").prop("checked",false);
}

</script>
</body>
</html>

