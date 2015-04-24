<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>    
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
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">管理设置</a></li>
        <li><i>&gt;</i>角色管理</li>
    </ul>
</div>
<div>${remind.lsoundEffect}
<form action="/function/saveRemind.action" enctype="multipart/form-data" method="post" id="fform">
<table border="1" style="width: 200px">
    <tr>
        <td>
    	   <input  type ="checkbox" id="lsoundEffect" name="lsoundEffect" value="1" <c:if test='${remind.lsoundEffect==1}'>checked</c:if>/>播音效<br>
    	        <input  type ="radio" id="lineEffect" name="lineEffect"  value="1" checked="checked" <c:if test='${remind.lineEffect==1}'>checked</c:if>/>默认音效<br>
    	   		<input  type ="radio" id="lineEffect" name="lineEffect"  value="2" <c:if test='${remind.lineEffect==2}'>checked</c:if>/>自定义</t>
    	   		<input type="file" id="lsound" name="lsound" size="12" onchange="checkfile()">
                <br>
    	   <input  type ="checkbox" id="bubble" name="bubble" value="1" <c:if test='${remind.bubble==1}'>checked</c:if>/>冒气泡框
        </td>
    </tr>
    <tr>
        <td>
    	   <input  type ="checkbox" id="jsoundEffect" name="jsoundEffect" value="1" <c:if test='${remind.jsoundEffect==1}'>checked</c:if>/>播音效<br>
    	   		<input  type ="radio" id="createEffect" name="createEffect" value="1"  checked="checked" <c:if test='${remind.createEffect==1}'>checked</c:if>/>默认音效<br>
    	   		<input  type ="radio" id="createEffect" name="createEffect" value="2" <c:if test='${remind.createEffect==2}'>checked</c:if>/>自定义  <input type="file" id="jsound" name="jsound"><br>
        </td>
     </tr>
     <tr>		
        <td>
    	   <input  type ="checkbox" id="reSoundEffect" name="reSoundEffect" value="1" <c:if test='${remind.reSoundEffect==1}'>checked</c:if>/>播音效<br>
    	   		<input  type ="radio" id="receiveEffect" name="receiveEffect" value="1" checked="checked" <c:if test='${remind.receiveEffect==1}'>checked</c:if>/>默认音效<br>
    	   		<input  type ="radio" id="receiveEffect" name="receiveEffect" value="2" <c:if test='${remind.receiveEffect==2}'>checked</c:if>/>自定义  <input type="file" id="resound" name="resound"><br>
    	   		
    	   <input  type ="checkbox" id="upHint" name="upHint" value="1" <c:if test='${remind.upHint==1}'>checked</c:if>/>弹出提醒<br>
	    	   <input  type ="radio" id="upWdInform" name="upWdInform" value="1"  checked="checked"<c:if test='${remind.upWdInform==1}'>checked</c:if>/>弹出窗口<br>
	    	   <input  type ="radio" id="upWdInform" name="upWdInform" value="2" <c:if test='${remind.upWdInform==2}'>checked</c:if>/>弹出通知<br>
        </td>
    </tr>
</table>
</form>
    <tr>
      	<td align="center">
        <button onclick="saveRemind()" class="btn btn-primary btn-small" >确认</button>
    	<button class="btn" onclick="removecheck()">取消</button>
    	</td>
    </tr>
</div>
<!-- 表格有边框 -->
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
function saveRemind(){
	var lsound=$("#lsound").val();
	if(lsound!=''){
		extStart=lsound.lastIndexOf(".");
	    ext=lsound.substring(extStart,lsound.length).toUpperCase();
	    if(ext!=".MP3"&&ext!=".WAV"){
	    	alert("请选格式为mp3,wav的音效文件");
	    	return;
	    }
	}
	var jsound=$("#jsound").val();
	if(jsound!=''){
		extStart=jsound.lastIndexOf(".");
	    ext=jsound.substring(extStart,jsound.length).toUpperCase();
	    if(ext!=".MP3"&&ext!=".WAV"){
	    	$.dialog.alert("请选格式为mp3,wav的音效文件");
	    	return;
	    }
	}
	var resound=$("#resound").val();
	if(resound!=''){
		extStart=resound.lastIndexOf(".");
	    ext=resound.substring(extStart,resound.length).toUpperCase();
	    if(ext!=".MP3"&&ext!=".WAV"){
	    	$.dialog.alert("请选格式为mp3,wav的音效文件");
	    	return;
	    }
	}
   $("#fform").submit();
}

function removecheck(){
    $(":checkbox[checked='checked']").attr("checked",false);
    $(":radio[checked='checked']").attr("checked",false);
}
</script>
</body>
</html>
