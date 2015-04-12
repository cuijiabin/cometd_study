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

<body onload="init()" scroll="no" class="g-body">
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
                            <div class="u-record r-sms-visitor" id="logbox">
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
                                    <textarea class="u-txtarea" id="inputbox"></textarea>
                                    <div class="u-send">
                                        <div class="btn-group">
                                            <a class="btn btn-primary" href="javascript:send(inputbox.value);">发送</a>
                                            <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown" onclick="send(inputbox.value);">
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

<script type="text/javascript" src="/js/comet4j.js"></script>

<script language="javascript" for="window" event="onload"> 

   var maxLogCount = 100;
			console.log("init");
			// 引擎事件绑定
			JS.Engine.on({
				start : function(cId, aml, engine) {
					var style = engine.getConnector().workStyle;
					style = style === 'stream'?'长连接':'长轮询';
					console.log("style: "+ style);
					var str = ['<p class="r-welcome">※ 已建立链接</p>'];
					logbox.innerHTML += str.join('');
				},
				stop : function(cause, url, cId, engine) {
					var str = ['<p class="r-welcome">※连接中断</p>'];
					logbox.innerHTML += str.join('');
				},
				talker : function(data, timespan, engine) {
					switch (data.type) {
					case 'talk': // 收到聊天消息
						onMessage(data, timespan);
						break;
					case 'up': // 上线
						onJoin(data, timespan);
						break;
					case 'down': // 下线
						onLeft(data, timespan);
						break;
					default:
					}
				}
			});
			
			var userName = getCookie('KF_CUSTOMER_ID') || '';
			userName = userName ? userName.trim() : '' ;
			
			start();
			
		//}
		//开启连接
		function start(){
			JS.Engine.start('/conn');
			inputbox.focus();
		}
		
		
		// 用户聊天通知
		function onMessage(data, timespan) {
			
			console.log("收到消息了！");
			var id = data.id;
			var name = data.name || '';
			name = name.HTMLEncode();
			var text = data.text || '';
			text = text.HTMLEncode();
			var t = data.transtime;
			var str;
			if (lastTalkId == id) {
				str = [ '<p class="r-visitor-txt">',text,'</p>' ];
			} else {
				str = [ '<p class="r-visitor">',name,'&nbsp;', t,
				           '</p><p class="r-visitor-txt">',text,'</p>' ];
			}
			checkLogCount();
			logbox.innerHTML += str.join('');
			lastTalkId = id;
			moveScroll();
		}
		// 用户上线通知
		function onJoin(data, timespan) {
			var id = data.id;
			var name = data.name || '';
			name = name.HTMLEncode();
			var t = data.transtime;
			var str = ['<p class="r-visitor">',name,'&nbsp;',t,'&nbsp;上线了</p>'];
			checkLogCount();
			logbox.innerHTML += str.join('');
			lastTalkId = null;
			moveScroll();
		}
		// 用户下线通知
		function onLeft(data, timespan) {
			var id = data.id;
			var name = data.name || '';
			name = name.HTMLEncode();
			var t = data.transtime;
			var str = ['<p class="r-visitor">',name,'&nbsp;',t,'&nbsp;离开了</p>'];
			checkLogCount();
			logbox.innerHTML += str.join('');
			lastTalkId = null;
			moveScroll();
		}
		
		// 检测输出长度
		function checkLogCount() {
			var count = logbox.childNodes.length;
			if (count > maxLogCount) {
				var c = count - maxLogCount;
				for ( var i = 0; i < c; i++) {
					// logbox.removeChild(logbox.children[0]);
					logbox.removeChild(logbox.firstChild);
				}
		
			}
		}
		// 移动滚动条
		function moveScroll() {
			logbox.scrollTop = logbox.scrollHeight;
			inputbox.focus();
		}
		// 回车事件
		function onSendBoxEnter(event) {
			if (event.keyCode == 13) {
				var text = inputbox.value;
				send(text);
				return false;
			}
		}
		// 发送聊天信息动作
		function send(text) {
			if (!JS.Engine.running)
				return;
			text = text.trim();
			if (!text)
				return;
			var id = JS.Engine.getId();
			var param = "id=" + id + '&text=' + encodeURIComponent(text);
			JS.AJAX.post('/chat/talk.action?cmd=talk', param, function() {
				inputbox.value = '';
			});
		}
		// 改名动作
		function rename() {
			if (!JS.Engine.running)
				return;
			var oldName = getCookie('userName') || '';
			oldName = oldName.trim();
			var userName = prompt("请输入姓名", oldName);
			userName = userName ? userName.trim() : '';
			var id = JS.Engine.getId();
			if (!id || !userName || oldName == userName)
				return;
			var param = "id=" + id + '&newName=' + encodeURIComponent(userName)
					+ '&oldName=' + encodeURIComponent(oldName);
			setCookie('userName', userName, 365);
			JS.AJAX.post('/chat/talk.action?cmd=rename', param);
		}
		
		// 设置Cookie
		function setCookie(name, value, expireDay) {
			var exp = new Date();
			exp.setTime(exp.getTime() + expireDay * 24 * 60 * 60 * 1000);
			document.cookie = name + "=" + encodeURIComponent(value) + ";expires="
					+ exp.toGMTString();
		}
		// 获得Cookie
		function getCookie(name) {
			var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
			if (arr != null)
				return decodeURIComponent(arr[2]);
			return null;
		}
		// 删除Cookie
		function delCookie(name) {
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval = getCookie(name);
			if (cval != null)
				document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
		}
		// HTML编码
		String.prototype.HTMLEncode = function() {
			var temp = document.createElement("div");
			(temp.textContent != null) ? (temp.textContent = this)
					: (temp.innerText = this);
			var output = temp.innerHTML;
			temp = null;
			return output;
		};
		// HTML解码
		String.prototype.HTMLDecode = function() {
			var temp = document.createElement("div");
			temp.innerHTML = this;
			var output = temp.innerText || temp.textContent;
			temp = null;
			return output;
		};
		String.prototype.trim = function() {
			return this.replace(/^\s+|\s+$/g, '');
		};
</script> 

</body>
</html>