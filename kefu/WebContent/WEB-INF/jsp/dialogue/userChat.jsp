<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服端访客对话框架--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body scroll="no" class="g-body">
<jsp:include page="../top.jsp"></jsp:include>
<div class="g-bd1 f-cb">
	<div class="g-bd1c f-cb c-bor">
        <div class="g-sd1 f-fl">
            <ul class="f-cb">
                <li><a href="#">添加工单</a></li>
                <li><i>|</i><a href="#">访客阻止</a></li>
                <li><i>|</i><a href="#"> 客服转接</a></li>
            </ul>
        </div>
        <div class="g-mn1 f-fr">
            <div class="g-mn1c">
                <a class="btn btn-primary btn-small" href="#">客服管理</a>
                <a class="btn btn-primary btn-small" href="#"><i class="icon-cog icon-white"></i> 设置</a>
                <select class="c-wd60">
                    <option selected="selected">在线</option>
                    <option value="离线">离线</option>
                </select>
                <a class="btn btn-danger btn-small" href="#">退出登录</a>
            </div>
        </div>
    </div>
</div>
<div class="clear"></div>
<div class="g-bd2 f-cb">
    <div class="g-sd2l c-bor">
        <h3 class="u-tit c-bg">当前共有0个对话</h3>
        <ul class="m-talk" id="customerList">
        </ul>
    </div>
    <div class="g-mn2">
        <div class="g-mn2c">
            <div class="u-state c-bor">等待咨询...</div>
            <div class="g-mn2c-cnt c-bor">
                <input type="hidden" id="currentCcnId"/>
        		<h3 class="u-tit c-bg" id="contentTitle">欢迎 xxx 使用客服系统，与客服系统连接成功</h3>
                <div class="m-dialog2">
                    <div class="u-record r-sms-manager" >
                        <p class="r-welcome">欢迎使用客服系统</p>
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
                            <textarea class="u-txtarea" id="inputbox" ></textarea>
                            <div class="u-send">
                                <div class="btn-group">
                                    <a class="btn btn-primary" href="javascript:sendMessage(inputbox.value);">发送</a>
                                    <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
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
        </div>
    </div>
    <div class="g-sd2r">
    	<div class="m-visitor c-bor">
        	<h3 class="u-tit c-bg">访客信息</h3>
        </div>
        <div class="m-sidemenu">
        	<div class="c-bor">
                <h3 class="u-tit c-bg">
                	<a class="f-fr" href="#">添加</a>
                    <a class="f-fl" href="#">常用语管理</a>
                </h3>
                <div class="m-sidemenu-cnt">1</div>
            </div>
        	<div class="c-bor">
                <h3 class="u-tit c-bg">
                	<a class="f-fr" href="#">添加</a>
                    <a class="f-fl" href="#">知识库</a>
                </h3>
                <div class="m-sidemenu-cnt">2</div>
            </div>
        	<div class="c-bor">
                <h3 class="u-tit c-bg">
                	<a class="f-fr" href="#">添加</a>
                    <a class="f-fl" href="#">产品信息</a>
                </h3>
                <div class="m-sidemenu-cnt">3</div>
            </div>
		</div>
    </div>
</div>
<div class="clear"></div>
<div class="g-bd3 f-cb c-bor">
	<div class="g-bd3c f-cb">
        <div class="g-sd3 f-fl">
            <ul class="f-cb">
                <li><span>在线访客：100</span></li>
                <li><i>|</i><span>等待咨询：10</span></li>
            </ul>
        </div>
        <div class="g-mn3 f-fr">
            <div class="g-mn3c">
                <ul class="f-cb">
                    <li><span>工号：cc</span></li>
                    <li><i>|</i><span>身份：一般员工</span></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/app.js"></script>

<script type="text/javascript" src="/js/comet4j.js"></script>
<script language="javascript" for="window" event="onload"> 
	console.log("init");
	// 引擎事件绑定
	JS.Engine.on({
		start : function(cId, aml, engine) {
			var style = engine.getConnector().workStyle;
			style = style === 'stream'?'长连接':'长轮询';
			console.log("style: "+ style);
		},
		stop : function(cause, url, cId, engine) {
		},
		dialogue : function(data, timespan, engine) {
			switch (data.type) {
			case 'on_message': // 收到聊天消息
				break;
			case 'update_list': //
				userList(data);
				break;
			case 'on_open': // 上线
			    //更新用户列表
				updateCustomerList();
				break;
			case 'on_close': // 下线
				updateCustomerList();
				break;
			}
		}
	});
	
	start();
	updateCustomerList();
	
	//开启连接
	function start(){
		JS.Engine.start('/conn');
		inputbox.focus();
		
		
	}
	
	
	//更新用户列表
	function updateCustomerList() {
		if (!JS.Engine.running)
			return;
		var id = JS.Engine.getId();
		var param = "ccnId=" + id ;
		JS.AJAX.post('/chat/receiveList.action', param, function() {
			inputbox.value = '';
		});
	}
	// 用户下线通知
	function userList(data) {
		var list = data.obj
		console.log(list.length);
		var html = "";
		for(var i=0; i<list.length; i++){
			var dQuene = list[i];
			var ccnId = '"'+dQuene.ccnId+'"';
			html += "<li class='on'><p>"+dQuene.customerName
			+"</p><p><a href='javascript:changeTitle("+dQuene.customerId+", "+ccnId+");'>"
			+dQuene.ip+"</a></p><span class='u-close'>x</span></li>"
		}
		console.log(html);
		 $("#customerList").html(html);
	}
	function changeTitle(customerId,ccnId){
		
		console.log("与客户"+customerId+"对话中");
		$("#contentTitle").html("与客户"+customerId+"对话中");
		$("#currentCcnId").val(ccnId);
	}
	
	// 发送聊天信息动作
	function sendMessage(message) {
		if (!JS.Engine.running)
			return;
		message = message.trim();
		if (!message)
			return;
		var userCId = JS.Engine.getId();
		var cusCId = $("#currentCcnId").val();
		alert(cusCId);
		var param = "userCId=" + userCId + '&cusCId='+cusCId+'&message=' + encodeURIComponent(message);
		JS.AJAX.post('/chat/toCustomer.action', param, function() {
			inputbox.value = '';
		});
	}
	
</script>
</body>
</html>
