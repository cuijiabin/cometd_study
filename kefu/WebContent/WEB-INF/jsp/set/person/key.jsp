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
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">个人设置</a></li>
        <li><i>&gt;</i>快捷键</li>
    </ul>
</div>
<div class="g-cnt">
	<table class="table table-bordered m-table" style="width: 350px">
		<tr>
			<td class="f-txtr tdbg" width="150px">客户端截图热键:</td> <td class="f-txtl">Shift+Alt+<input type="text" id="picKey" name="picKey" style="width: 20px" value="${key.picKey}" ></td>
		</tr>
		<tr>
			<td class="f-txtr tdbg" width="150px">显示客户端热键:</td> <td class="f-txtl">Shift+Alt+<input type="text" id="showKey" name="showKey" style="width: 20px" value="${key.showKey}"></td>
		</tr>
		<tr>
			<td class="f-txtr tdbg" width="150px">上一个对话热键:</td> <td class="f-txtl">Shift+Alt+<input type="text" id="lastKey" name="lastKey" style="width: 20px" value="${key.lastKey}"></td>
		</tr>
		<tr>
			<td class="f-txtr tdbg" width="150px">下一个对话热键:</td> <td class="f-txtl">Shift+Alt+<input type="text" id="nextKey" name="nextKey" style="width: 20px" value="${key.nextKey}"></td>
		</tr>
		<tr>
			<td class="f-txtr tdbg" width="150px">访客对话发送热键:</td> <td class="f-txtl"><input type="radio" id="sendKey" name="sendKey" value="1" <c:if test='${key.sendKey==1}'>checked</c:if>/>Enter &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" id="sendKey" name="sendKey" value="2" <c:if test='${key.sendKey==2}'>checked</c:if>/>Ctrl+Enter	</td>
		</tr>
	</table>
	 <button style="margin-left:230px;" onclick="javascript:saveKey();" class="btn btn-primary btn-small" >&nbsp;&nbsp;保存&nbsp;&nbsp;</button>
</div>
<!-- 表格有边框 -->
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
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
function saveKey(){
	   url="/function/saveKeyboard.action";
	   data = {
			    "picKey":$("#picKey").val(),
				"showKey" : $("#showKey").val(),	    
				"lastKey" : $("#lastKey").val(),	    
				"nextKey" : $("#nextKey").val(),	    
				"sendKey" : $('input:radio[name=sendKey]:checked').val()	    
			};
		$.ajax({
			type : "get",
			url : url,
			data : data,
			dataType : "json",
			success : function(data) {
				$.dialog.alert(data.msg);
			},
			error : function(msg) {
				$.dialog.alert("出现错误,请重试!");
			}
		});
}
</script>
</body>
</html>
