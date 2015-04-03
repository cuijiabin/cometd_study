<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天</title>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">	
var ws = null;
function startWebSocket() {
	if (!window.WebSocket) alert("本浏览器不支持WebSocket!");
	//if (window.WebSocket) alert("本浏览器支持WebSocket!");
	
	// 创建WebSocket
	ws = new WebSocket("ws://192.168.1.97:8080/message/1");
	
	// 收到消息时在消息框内显示
	ws.onmessage = function(evt) {   
		$('#msgBox').append(evt.data);
		$('#msgBox').append('</br>');
	};
	// 断开时会走这个方法
	ws.onclose = function() { 
		alert('对话关闭！~~~~~~~');
	};
	// 连接上时走这个方法
	ws.onopen = function() {
		console.log('open~~~~~~~~'); 
		//ws.send();
	};
	
}

  
// 发送消息
function sendMsg() {
    //var message = null;
	var data = document.getElementById('msgSendBox').value;
	//message.data = data;
	
	ws.send(data);
	document.getElementById('msgSendBox').value = '';
	
}
</script>
</head>
<body onload="startWebSocket();">

<div id="msgBox" style="width:400px;height:300px;border:1px solid #000000">
</div>
<textarea id="msgSendBox" rows="5" cols="32"></textarea>
<input type="button" value="send" onclick="sendMsg()"></input>
</body>
</html>