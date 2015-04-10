<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客户端访客对话框架--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body scroll="no" class="g-body">
<div class="m-chat">
	<div class="m-chat-hd f-cb">
        <div class="m-chat-hdc f-cb c-bg">
            <div class="u-teach f-fl">
                <img src="/img/pic_02.png" alt="" />
                <p>艾丽娅老师 <span>托福口语</span></p>
            </div>
        </div>
    </div>
	<div class="m-chat-mn f-cb">
        <div class="g-mn5">
        	<div class="slideTab2">
            	<div class="hd">
                	<ul class="f-cb"><li class="u-borl">咨询</li><li class="u-borl">留言</li><li>帮助</li></ul>
                </div>
                <div class="bd">
                    <div class="g-mn5c c-bor">
                        <h3 class="u-tit c-bg f-txtl">等待咨询...</h3>
                        <div class="m-dialog">
                            <div class="u-record r-sms-visitor" id="msgBox">
                            	<p class="r-welcome">※ 已建立链接</p>
                                <p class="r-manager">管理员 15:30:59</p>
                                <p class="r-manager-txt">请问有什么需要帮助你的吗？<br />请问有什么需要帮助你的吗？</p>
                            	<p class="r-visitor">访问者 15:31:30</p>
                                <p class="r-visitor-txt">谢谢，没有！</p>
                                <p class="r-manager">管理员 15:30:59</p>
                                <p class="r-manager-txt">请问有什么需要帮助你的吗？<br />请问有什么需要帮助你的吗？</p>
                            	<p class="r-visitor">访问者 15:31:30</p>
                                <p class="r-visitor-txt">谢谢，没有！</p>
                                <p class="r-manager">管理员 15:30:59</p>
                                <p class="r-manager-txt">请问有什么需要帮助你的吗？<br />请问有什么需要帮助你的吗？</p>
                            	<p class="r-visitor">访问者 15:31:30</p>
                                <p class="r-visitor-txt">谢谢，没有！</p>
                                <p class="r-visitor">访问者 15:31:30</p>
                                <p class="r-visitor-txt"><a href="http://www.xiaoma.com/" target="_blank">http://www.xiaoma.com/</a></p>
                            </div>
                            <div class="u-operate">
                                <div class="u-operatebar c-bg">
                                    <ul class="u-operatebar-btn">
                                        <li><img src="/img/icon_01.png" alt="" /></li>
                                        <li><img src="/img/icon_02.png" alt="" /></li>
                                        <li><img src="/img/icon_03.png" alt="" /></li>
                                        <li><img src="/img/icon_04.png" alt="" /></li>
                                        <li><img src="/img/icon_05.png" alt="" /></li>
                                    </ul>
                                </div>
                                <div class="u-input f-cb">
                                    <textarea class="u-txtarea" id="msgSendBox"></textarea>
                                    <div class="u-send">
                                        <div class="btn-group">
                                            <a class="btn btn-primary" href="javascript:sendMsg()">发送</a>
                                            <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" onclick="sendMsg()">
                                            <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu f-txtl">
                                                <li><a href="#"><label><input type="radio" name="2" checked>Enter 发送</label></a></li>
                                                <li><a href="#"><label><input type="radio" name="2">Ctrl + Enter 发送</label></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>                
                        </div>
                    </div>
                    <div class="g-mn5c c-bor">
                        <h3 class="u-tit c-bg f-txtl">请填写留言信息</h3>
                        <div class="m-message">
                            <div class="m-query f-mar10">
                                <div class="m-query-hd">
                                    <p class="u-txt">您好，现在是非咨询时段。如需帮助请留言。我们的留学顾问会第一时间与您联系。</p>
                                </div>
                                <div class="u-hr"></div>
                                <div class="m-query-bd">
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">回复方式：</label>
                                        <div class="u-subsec">
                                            <label><input type="radio" name="mode" />网站</label>
                                            <label><input type="radio" name="mode" />邮箱</label>
                                            <label><input type="radio" name="mode" />短信</label>
                                        </div>
                                    </div>
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">回复对象：</label>
                                        <div class="u-subsec">
                                            <label><input type="radio" name="object" />公司</label>
                                            <label><input type="radio" name="object" />部门/员工</label>
                                        </div>
                                        <select class="c-wd80">
                                            <option selected="selected">全部部门</option>
                                            <option value="客服部">客服部</option>
                                            <option value="留学部">留学部</option>
                                            <option value="随时学">随时学</option>
                                            <option value="好顾问">好顾问</option>
                                        </select>
                                    </div>
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">姓名：</label><input class="c-wd150" type="text" /> <span class="help-inline c-clred">* 必填</span>
                                    </div>
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">Email：</label><input class="c-wd150" type="text" />
                                    </div>
                                    <div class="f-mbm">
                                    	<label class="c-wd80 f-txtr">电话：</label><input class="c-wd150" type="text" /> <span class="help-inline c-clred">* 必填</span>
                                    </div>
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">留言内容：</label><textarea style="width:300px;height:130px;height:128px\9;*height:118px;height:133px\9\0;resize:none;"></textarea> <span class="help-inline c-clred">* 必填</span>
                                    </div>
                                    <div class="u-hr"></div>
                                    <div class="m-query-bd">
                                        <button type="button" class="btn btn-primary">提交</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="g-sd5 c-bor">
            <div class="slideTab">
                <div class="hd c-bg">
                    <ul><li class="u-borl">公司简介</li><li>客服信息</li></ul>
                </div>
                <div class="bd">
                	<div class="tabBox"></div>
                	<div class="tabBox"></div>
                </div>
            </div>
        </div>
    </div>
	<div class="m-chat-sd"></div>
    <div class="clear"></div>
    <div class="g-bd3 f-cb c-bor">
        <div class="g-bd3c f-cb">
            <div class="g-sd3 f-fl">
                <ul class="f-cb u-txt">
                    <li><a href="#" target="_blank">小马过河留学考试全日制</a></li>
                    <li><a href="#" target="_blank">小马过河国际教育</a></li>
                </ul>
            </div>
            <div class="g-mn34 f-fr">
                <div class="g-mn3c">
                    <ul class="f-cb">
                        <li>网址：<a href="#" target="_blank">www.xiaoma.com</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/app.js"></script>

<script language="javascript" for="window" event="onload"> 
	var ws = null;
	function startWebSocket() {
		if (!window.WebSocket) alert("本浏览器不支持WebSocket!");
		
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
		};
	}
	
	startWebSocket();
	
	// 发送消息
	function sendMsg() {
		
		//console.log('发送消息'); 
		var data = document.getElementById('msgSendBox').value;
		//console.log('发送消息'+data); 
		ws.send(data);
		document.getElementById('msgSendBox').value = '';
		
	}
	
	function setCookie(name,value){
	    var Days = 30;
	    var exp = new Date();
	    exp.setTime(exp.getTime() + Days*24*60*60*1000);
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	    
	}
</script> 
<script type="text/javascript">	

</script>
</body>
</html>