<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Language" content="zh-cn" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Comet4J Demo</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<script type="text/javascript" src="/js/comet4j.js"></script>
<script type="text/javascript" src="/js/talk.js"></script>
</head>
<body onload="init()">
<div id="statebar">
连接状态：<span id="workStyle"></span>；
连接数量：<span id="connectorCount"></span>；
已用内存：<span id="usedMemory"></span>；
可用内存：<span id="freeMemory"></span>；
内存容量：<span id="totalMemory"></span>；
最大容量：<span id="maxMemory"></span>；
系统已运行：<span id="startup"></span>
</div>

<div id="logbox">
</div>

<div id="toolbar" >
请输入：<input maxlength="200" id="inputbox" class="inputbox" onkeypress="return onSendBoxEnter(event);" type="text" ></input>
<input type="button" class="button" onclick="send(inputbox.value);" value="回车发送"></input>
<input type="button" class="button" onclick="rename();" value="改名"></input>
</div>

<div id="login">
	请输入昵称：<input type="text" class="inputbox" maxlength="50" id="loginName" onkeypress="return loginEnter(event);"></input>
	<input type="button" class="button" onclick="login();" value="确定"></input>
</div>

</body>
</html>