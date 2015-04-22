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
<div>
<form action="/function/saveRemind.action" enctype="multipart/form-data" method="post" id="fform">
<table border="1" style="width: 200px">
    <tr>
        <td>
    	   <input  type ="checkbox" id="lsoundEffect" name="lsoundEffect" value="1"/>播音效<br>
    	        <input  type ="radio" id="lineEffect" name="lineEffect"  value="1"/>默认音效<br>
    	   		<input  type ="radio" id="lineEffect" name="lineEffect"  value="2"/>自定义</t>
    	   		<input type="file" id="lsound" name="lsound">
                <br>
    	   <input  type ="checkbox" id="bubble" name="bubble" value="1"/>冒气泡框
        </td>
    </tr>
    <tr>
        <td>
    	   <input  type ="checkbox" id="jsoundEffect" name="jsoundEffect" value="1"/>播音效<br>
    	   		<input  type ="radio" id="createEffect" name="createEffect" value="1" />默认音效<br>
    	   		<input  type ="radio" id="createEffect" name="createEffect" value="2"/>自定义  <input type="file" id="jsound" name="jsound"><br>
        </td>
     </tr>
     <tr>		
        <td>
    	   <input  type ="checkbox" id="reSoundEffect" name="reSoundEffect" value="1"/>播音效<br>
    	   		<input  type ="radio" id="receiveEffect" name="receiveEffect" value="1"/>默认音效<br>
    	   		<input  type ="radio" id="receiveEffect" name="receiveEffect" value="2"/>自定义  <input type="file" id="resound" name="resound"><br>
    	   		
    	   <input  type ="checkbox" id="upHint" name="upHint" value="1"/>弹出提醒<br>
	    	   <input  type ="radio" id="upWdInform" name="upWdInform" value="1"/>弹出窗口<br>
	    	   <input  type ="radio" id="upWdInform" name="upWdInform" value="2"/>弹出通知<br>
        </td>
    </tr>
    <tr>
    	<td align="center">
        <button  onclick="javascript:saveRemind();" class="btn btn-primary btn-small" >确认</button>
    	<button onclick="javascript:;abolist" class="btn" >取消</button>
    	</td>
    </tr>
</table>
</form>
</div>
<!-- 表格有边框 -->
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
function saveRemind(){
// 	alert($("#lsound").val());
// 	   url="/function/saveRemind.action";
// 	   data = {
// 			   "lsoundEffect":$("#lsoundEffect").attr("checked")?1:0,
// 			   "lsound":
// 			    "lineEffect":$('input:radio[name=lineEffect]:checked').val(),
// 			    "bubble":$("#bubble").attr("checked")?1:0,
// 			    "jsoundEffect":$("#jsoundEffect").attr("checked")?1:0,
// 			    "createEffect":$('input:radio[name=createEffect]:checked').val(), 
// 			    "reSoundEffect":$("#reSoundEffect").attr("checked")?1:0,
// 				"receiveEffect" : $('input:radio[name=receiveEffect]:checked').val(),
// 				"upHint":$("#upHint").attr("checked")?1:0,
// 				"upWdInform" : $('input:radio[name=upWdInform]:checked').val()    
// 			};
// 		$.ajax({
// 			type : "get",
// 			url : url,
// 			data : data,
// 			dataType : "json",
// 			success : function(data) {
// 					alert(data.msg);
// 			},
// 			error : function(msg) {
// 				alert("出现错误,请重试!");
// 			}
// 		});
   $("#fform").submit();
}

</script>
</body>
</html>
