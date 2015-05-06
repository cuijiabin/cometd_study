<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%> 

<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="blank" />
<meta name="format-detection" content="telephone=no" />
<meta name="format-detection" content="email=no" />
<meta name="author" content="dobao">
<title>访客手机端对话窗口--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body onload="init()" scroll="no" style="background:#f2f2f2">
<header class="m-mob-hd">
    <div class="m-mob-siteTit">
        <img src="/img/icon_worker.png" height="30" alt="" />
        留学考试全日制
    </div>
    <h3 class="m-mob-tit" id="dialogueTitle">等待咨询中</h3>
    <div class="f-fr m-mob-menu">
        <a class="m-mob-menuOn">人工服务</a>
        <ul class="m-mob-submenu">
            <li><a label="worker">人工客服</a></li>
            <li><a label="message">留言</a></li>
        </ul>
    </div>
</header>
<input type="hidden" id=isForbidden value="${isForbidden }"/>
<input type="hidden" id="currentCustomerId" value="${customer.id }"/>
<input type="hidden" id="currentUserCcnId"/>
<div id="chat">
    <div class="m-mob-bd">
        <div class="m-mobChat-cnt" id="logbox">
            
        </div>
    </div>
    
    <!-- 输入模块 -->
    <div class="m-mob-inputbar">
        <div class="m-mob-face">
            <ul id="faces_ul">
            </ul>
        </div>
        <div class="hd">
            <textarea class="input" id="inputbox"></textarea>
            <a class="icon-tools" href="#" title="评分"></a>
            <a class="icon-face" href="#" title="表情"></a>
            <a href="#" class="btn btn-primary btn-send" id="btn-send" onclick="javascript:sendMessage(inputbox.value);">发送</a>
        </div>
    </div>
</div>

<!-- 评分模块 -->
<div class="m-mob-score">
    <p>请为客服人员评分：</p>
    <ul>
        <li><span class="icon-star icon-star-0"></span>很满意<i class="icon-check"></i></li>
        <li class="selected"><span class="icon-star icon-star-1"></span>较好<i class="icon-check"></i></li>
        <li><span class="icon-star icon-star-2"></span>一般<i class="icon-check"></i></li>
        <li><span class="icon-star icon-star-3"></span>较差<i class="icon-check"></i></li>
        <li><span class="icon-star icon-star-4"></span>恶劣<i class="icon-check"></i></li>
    </ul>
    <div class="m-mob-btn">
        <a class="m-mob-btnNo" href="#">取消</a>
        <a class="m-mob-btnOk" href="#">确定</a>
    </div>
</div>
<div class="masker"></div>

<!-- 留言模块 -->
<div id="message" style="display:none;">
    <div class="m-mob-message">
        <form autocomplete="off">
            <p>对不起，当前客服人员不在线，请留下您的问题和联系方式，客服会在上线后第一时间回复您！</p>
            <input class="tbox" type="text" placeholder="联系电话或手机号码（必填）" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
            <p>请留言：</p>
            <textarea class="abox" placeholder="我有问题想要咨询，请与我联系！"></textarea>
            <p class="m-mob-btn"><a href="#" class="m-mob-btnOk">提交留言</a></p>
        </form>
        <small>Powered by <a href="http://www.xiaoma.com/">XiaoMa</a></small>
    </div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/comet4j.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script language="javascript" for="window" event="onload"> 

			console.log("init");
			// 引擎事件绑定
			JS.Engine.on({
				start : function(cId, aml, engine) {
					var style = engine.getConnector().workStyle;
					style = style === 'stream'?'长连接':'长轮询';
					console.log("style: "+ style+"cId: "+cId);
					var str = ['<div class="msg-offline"><span class="alert alert-success">恭喜你，连接成功</span></div>'];
					logbox.innerHTML += str.join('');
				},
				stop : function(cause, url, cId, engine) {
					var str = ['<div class="msg-offline"><span class="alert alert-error">对不起，连接失败</span></div>'];
					logbox.innerHTML += str.join('');
					findWaitList(0);//获取等待列表
					$("#guanbiduihua").hide();
				},
				dialogue : function(data, engine) {
					switch (data.type) {
					case 'on_message': 
						onMessage(data);// 收到聊天消息
						break;
					case 'on_switch_customer': 
						switchCustomer(data); //客服切换
						break;
					case 'on_open': 
						onOpen(data); // 上线
						break;
					case 'on_close': 
						onLeft(data); // 下线
						break;
					case 'end_dialogue':
						endDialogueNotice(data); // 结束对话
						break;
					case 'no_user': 
						noUser(data);// 客服不在
						break;
					case 'user_busy': 
						userBusy(data);// 客服正忙
						break;
					default:
					}
				}
			});
			
		    start();
			
			//开启连接 (**)
			function start(){
				var isForbidden =  $("#isForbidden").val();
				if(isForbidden == 'false'){
					JS.Engine.start('/conn');
					inputbox.focus();
				}else{
					var str = ['<div class="msg-offline"><span class="alert alert-error">对不起，您已经被禁用</span></div>'];
					logbox.innerHTML += str.join('');
				}
			}
		
			// 用户聊天通知(**)
			function onMessage(data) {
				data = data.obj
				console.log("收到消息了！");
				var name = data.name || '';
				name = name.HTMLEncode();
				var text = data.text || '';
				text = text.HTMLEncode();
				var t = data.transtime;
				var who = data.who;
				var str = "";
				if(who == "1"){
					str = [ '<div class="msg-time">',t,'</div>','<div class="msg-visitor"><span class="avatar"></span><p class="msg-box"><i></i>',text,'</p></div>'];
				}else if(who == "2"){
					str = [ '<div class="msg-time">',t,'</div>','<div class="msg-worker"><span class="avatar"></span><p class="msg-box"><i></i>',text,'</p></div>'];
				}
				console.log(str);
				logbox.innerHTML += str.join('');
				moveScroll();
				$("#advisory").click();
				$("#guanbiduihua").show();
			}
			
		// 用户上线通知(**)
		function onOpen(data) {
			var message = data.obj;
			var name = message.name || '';
			name = name.HTMLEncode();
			var html='正与<span>【  '+name+'】<span/>聊天';
			$("#dialogueTitle").html(html);
			$("#currentUserCcnId").val(message.id);
			
			$("#guanbiduihua").show();
			$("#advisory").click();
			
		}
		
		function switchCustomer(data){
			
			var user = data.obj;
			var html='正与<span>【  '+user.cardName+'】<span/>聊天';
			console.log(html);
			$("#dialogueTitle").html(html);
		}
		
		// 用户下线通知(**)
		function onLeft(data) {
			
			var str = '<div class="msg-offline"><span class="alert alert-block">对不起，客服已离开，对话结束！</span></div>';
			logbox.innerHTML += str;
			moveScroll();
			//弹出评分对话框
			$("#guanbiduihua").hide();
			socreUserNotice();
			
		}
		
		function noUser(data){
			var str = '<div class="msg-offline"><span class="alert alert-block">对不起，客服不在线，请留言</span></div>';
			logbox.innerHTML += str;
			$("#leaveMessage").click();
			$("#guanbiduihua").hide();
		}
		
		function userBusy(data){
			var str = '<div class="msg-offline"><span class="alert alert-info">客服正忙，请您耐心等待</span></div>';
			logbox.innerHTML += str;
			$("#guanbiduihua").hide();
		}
		
		//通过按钮结束对话(**)
		function endDialogue(){
			var ccnId = JS.Engine.getId();
			var endCcnId = $("#currentUserCcnId").val();
			console.log("结束对话参数 ccnId："+ccnId+" ,endCcnId: "+endCcnId);
			
			var param = "ccnId="+ccnId+'&type='+1+'&endCcnId='+endCcnId;
			JS.AJAX.post('/chat/endDialogue.action', param, function() {
				JS.Engine.stop();
			});
		}
		// 对话结束提示！(**)
		function endDialogueNotice(data){
			console.log("对话结束类型："+data.obj);
			var str = "";
			if(data.obj == 1){
				 str = '<div class="r-offline"><span class="alert alert-block">您结束了与客服对话！</span></div>';
			}else{
				 str = '<div class="r-offline"><span class="alert alert-block">客服结束了与您的对话！</span></div>';
			}
			logbox.innerHTML += str;
			moveScroll();
			
			$("#guanbiduihua").hide();
			
		    //弹出评分对话框
		}
		
		// 移动滚动条
		function moveScroll() {
			logbox.scrollTop = logbox.scrollHeight;
			inputbox.focus();
		}
		
		// 发送聊天信息动作
		function sendMessage(message) {
			if (!JS.Engine.running)
				return;
			message = message.trim();
			if (!message)
				return;
			var cusCId = JS.Engine.getId();
			var param = "cusCId=" + cusCId + '&message=' + encodeURIComponent(message);
			JS.AJAX.post('/chat/toUser.action', param, function() {
				inputbox.value = '';
			});
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
		
		addListener = function(){
			// 点击表情区域以外地方自动关闭表情框或者右上角切换标签以外的地方自动隐藏标签
			$(document).bind('click', function(e){
				if(!$(e.target).closest("a").is(".icon-face") && $('.m-mob-face').hasClass('m-mob-faceOn')){
					$('.m-mob-face').removeClass('m-mob-faceOn');
				}
				if(!$(e.target).closest("div").is(".m-mob-menu") && !$('.m-mob-submenu').is(':hidden')){
					$('.m-mob-submenu').slideToggle('slow');
				}
			});
			$(window).resize(function (){
				if ($('.m-mob-score').hasClass('m-mob-scoreOn')){
					scoreResize();
				}
			});
		}
		addListener();
		function scoreResize(){
			var top = window.innerHeight - $('.m-mob-score').outerHeight(),
				left = window.innerWidth - $('.m-mob-score').outerWidth();
			top = top < 0 ? 0 : top / 2;
			left = left < 0 ? 0 : left / 2;
			$('.m-mob-score').css({'top': top, 'left': left});
		}
		// 表情框的显示和隐藏
		$('.icon-face').click(function () {
			if ($('.m-mob-face').hasClass('m-mob-faceOn')){
				$('.m-mob-face').removeClass('m-mob-faceOn');
			}else {
				$('.m-mob-face').addClass('m-mob-faceOn');
			}
		});
		// 评分功能
		$('.icon-tools').click(function(){
			$('.m-mob-score').addClass('m-mob-scoreOn');
			$('.masker').addClass('masker-show');
			scoreResize();
		});
		// 评分框取消按钮
		$('.m-mob-score .m-mob-btnNo,.masker').click(function(){
			$('.m-mob-score').removeClass('m-mob-scoreOn');
			$('.masker').removeClass('masker-show');
		});
		// 评分框确定按钮
		$('.m-mob-score .m-mob-btnOk').click(function () {
			$('.m-mob-score').removeClass('m-mob-scoreOn');
			$('.masker').removeClass('masker-show');
		});
		// 评分选择时
		$('.m-mob-score ul li').click(function () {
			$('.m-mob-score ul li').removeClass('selected');
			$(this).addClass('selected');
		});
		// 下拉菜单
		$(".m-mob-menuOn").click(function(){
			$('.m-mob-submenu').slideToggle('slow');
		})
		// 菜单切换
		var nowLabel = 'worker'; // 当前标签(默认人工客服)
		$('.m-mob-menu a').click(function(){
			var label = $(this).attr('label');
			if (label == nowLabel) {
				return ;
			}
			nowLabel = label;
			switch (label) {
				case 'worker':
					window.location.reload();
					break;
				case 'message':
					$('#chat').hide();
					$('#message').show();
					$(".m-mob-menuOn").text("留言");
					$(".m-mob-tit").text("请填写留言信息");
					$(".m-mob-submenu").hide();
					break;
				default:
					break;
			}
		});
</script> 
<script type="text/javascript">

</script>
</body>
</html>
