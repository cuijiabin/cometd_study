<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%> 

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
<link rel="stylesheet" type="text/css" href="/jsplugin/exp/css/style.css" />
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
                	<ul class="f-cb"><li class="u-borl" id="advisory">咨询</li><li class="u-borl" id="leaveMessage">留言</li><li>帮助</li></ul>
                </div>
                <div class="bd">
                    <div class="g-mn5c c-bor">
                        <h3 class="u-tit c-bg f-txtl"><span id="dialogueTitle">等待咨询...</span>  <a class="f-fr c-colf00" href="javascript:endDialogue()"><b>X 结束对话</b></a></h3>
                        
                        <input type="hidden" id=isForbidden value="${isForbidden }"/>
                        <input type="hidden" id="currentCustomerId" value="${customer.id }"/>
                        <div class="m-dialog">
                            <div class="u-record r-sms-visitor" id="logbox">
                            <c:if test="${dialogueList != null}">
	                            <c:forEach var="dialogue" items="${dialogueList}">
	                              <p class="r-visitor">${dialogue.customerId}&nbsp;${dialogue.createDate}</p>
	                              <p class="r-visitor-txt">${dialogue.content}</p>
	                            </c:forEach>
	                            <div class="r-history"><h3>历史记录</h3></div>
	                        </c:if>
                            </div>
                            <div class="u-operate">
                                <div class="u-operatebar c-bg">
                                    <ul class="u-operatebar-btn">
                                        <li><a class="exp-block-trigger" href="javascript:;"><img src="/img/icon_01.png" alt="" /></a></li>
<!--                                         <li><img src="/img/icon_02.png" alt="" /></li> -->
<!--                                         <li><img src="/img/icon_03.png" alt="" /></li> -->
                                        <li><img src="/img/icon_04.png" alt="" /></li>
                                        <li><img src="/img/icon_05.png" alt="" /></li>
                                        <a class="f-fr" href="javascript:socreUserNotice()">客服评分</a>
                                    </ul>
                                </div>
                                <div class="u-input f-cb">
                                    <textarea class="u-txtarea" id="inputbox" onchange="javascript:changeTxt(this);" onkeypress="return onSendBoxEnter(event);"></textarea>
                                    <div class="u-send">
                                        <div class="btn-group">
                                            <a class="btn btn-primary" href="javascript:sendMessage(inputbox.value);">发送</a>
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
                                        	<c:forEach varStatus="status" items="${replyWay}" var="d">
                                        		<label><input type="radio" <c:if test="${status.first}" >  checked="true" </c:if> onclick="changeRadio(this)" name="replyWay" value="${d.itemCode}" />${ d.itemName }</label>
                                       		</c:forEach>
                                        </div>
                                    </div>
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">回复对象：</label>
                                        <div class="u-subsec">
                                        	<c:forEach varStatus="status" items="${replyType}" var="d">
                                        		<label><input type="radio" <c:if test="${status.first}" > id="messageRadio" checked="true" </c:if> name="replyType" value="${d.itemCode}" onclick="changeRadio(this)" onchange="changeMessage(this);" />${ d.itemName }</label>
                                       		</c:forEach>
                                        </div>
										<select id="teacher" name="teacher" style="display:none;" class="c-wd80">
                                        	<c:forEach varStatus="status" items="${userList}" var="user">
                                        		<option  value="${user.id }" <c:if test="${status.first}" > selected="selected"</c:if>>${user.cardName}</option>
                                        	</c:forEach>
                                        </select>                                    
                                    </div>
                                    <c:forEach varStatus="status" items="${infoList}" var="d">
                                    <div class="f-mbm">
                                    	<label class="c-wd80 f-txtr">${d.itemName }：</label><input class="c-wd150" name="cust${d.description }" id="cust${d.description }" type="text" /><c:if test="${checkInfo.itemName.indexOf(d.itemCode)>=0 }"><span class="help-inline c-clred">* 必填</span></c:if> 
                                    </div>
                                    </c:forEach>
                                    <div class="f-mbm">
                                        <label class="c-wd80 f-txtr">留言内容：</label><textarea name="custContent" id="custContent" style="width:300px;height:130px;height:128px\9;*height:118px;height:133px\9\0;resize:none;"></textarea> <span class="help-inline c-clred">* 必填</span>
                                    </div>
                                    <div class="u-hr"></div>
                                    <div class="m-query-bd">
                                        <button type="button" onclick="javascript:addMessage();" class="btn btn-primary">提交</button>
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
<script type="text/javascript" src="/jsplugin/exp/exp.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=idialog"></script>
<script language="javascript" for="window" event="onload"> 

			console.log("init");
			// 引擎事件绑定
			JS.Engine.on({
				start : function(cId, aml, engine) {
					var style = engine.getConnector().workStyle;
					style = style === 'stream'?'长连接':'长轮询';
					console.log("style: "+ style+"cId: "+cId);
					var str = ['<div class="r-offline"><span class="alert alert-success">恭喜你，连接成功</span></div>'];
					logbox.innerHTML += str.join('');
				},
				stop : function(cause, url, cId, engine) {
					//var str = ['<div class="r-offline"><span class="alert alert-error">对不起，连接失败</span></div>'];
					//logbox.innerHTML += str.join('');
					//findWaitList(0);//获取等待列表
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
					var str = ['<div class="r-offline"><span class="alert alert-error">对不起，您已经被禁用</span></div>'];
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
					str = [ '<p class="r-manager">',name,'&nbsp;', t,'</p><p class="r-manager-txt">',$.expBlock.textFormat(text),'</p>' ];
				}else if(who == "2"){
					str = [ '<p class="r-visitor">',name,'&nbsp;', t,'</p><p class="r-visitor-txt">',$.expBlock.textFormat(text),'</p>' ];
				}
				console.log(str);
				logbox.innerHTML += str.join('');
				moveScroll();
			}
			
		// 用户上线通知(**)
		function onOpen(data) {
			var message = data.obj;
			var name = message.name || '';
			name = name.HTMLEncode();
			var html='与'+name+'对话中...';
			$("#dialogueTitle").html(html);
			$("#currentUserCcnId").val(message.id);
			
		}
		
		function switchCustomer(data){
			//alert("后台用户切换！");
		}
		
		// 用户下线通知(**)
		function onLeft(data) {
			
			var str = '<div class="r-offline"><span class="alert alert-block">对不起，客服已离开，对话结束！</span></div>';
			logbox.innerHTML += str;
			moveScroll();
			//弹出评分对话框
			socreUserNotice();
			
		}
		
		function noUser(data){
			var str = '<div class="r-offline"><span class="alert alert-block">对不起，客服不在线，请留言</span></div>';
			logbox.innerHTML += str;
			$("#leaveMessage").click();
		}
		
		function userBusy(data){
			var str = '<div class="r-offline"><span class="alert alert-info">客服正忙，请您耐心等待</span></div>';
			logbox.innerHTML += str;
			//$("#leaveMessage").click();
		}
		
		//通过按钮结束对话(**)
		function endDialogue(){
			var ccnId = JS.Engine.getId();
			var endCcnId = $("#currentUserCcnId").val();
			console.log("结束对话参数 ccnId："+ccnId+" ,endCcnId: "+endCcnId);
			
			var param = "ccnId="+ccnId+'&type='+1+'&endCcnId='+endCcnId;
			JS.AJAX.post('/chat/endDialogue.action', param, function() {
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
			JS.Engine.stop();
			
		    //弹出评分对话框
		    socreUserNotice();
		}
		
		// 客服评分弹出窗
		function socreUserNotice(){
			
			var content ='<table>'
                +'<tr>'
                +'<td>评分：<input type="radio" name="scoreType" value="1"/>非常好'
                +'<input type="radio" name="scoreType" value="2"/>好'
                +'<input type="radio" name="scoreType" value="3"/>一般'
                +'<input type="radio" name="scoreType" value="4"/>差'
                +'<input type="radio" name="scoreType" value="5"/>非常差</td>'
                +'</tr>'
                +'<tr>'
                +'<td>备注：<input id="scoreRemark" type="textarea"/></td>'
                +'</tr>'
                +'</table>';
                
			$.dialog({
			    id: 'socreUserNotice',
			    title:"客服评分",
			    content:content,
			    button: [
			        {
			            name: '提交',
			            callback: function () {
			            	var scoreType="";
			        		$('input[name="scoreType"]').each(function(){ if(this.checked){scoreType=this.value;} });
			        		var remark = $("#scoreRemark").val();
			        		console.log("评分参数：scoreType= "+scoreType+" ,remark= "+remark);
			        		socreDialogue(scoreType,remark);
			            },
			            focus: true
			        }
			    ]
			});
		}
		
		//向后台提交评分
		function socreDialogue(scoreType,remark){
			var customerCcnId = JS.Engine.getId();
			var userCcnId = $("#currentUserCcnId").val();
			
			var param = "customerCcnId="+customerCcnId+'&userCcnId='+userCcnId+'&scoreType='+scoreType+'&remark='+remark;
			JS.AJAX.post('/chat/socreDialogue.action', param, function() {
				alert("多谢评价！");
			});
		}
		
		// 移动滚动条
		function moveScroll() {
			logbox.scrollTop = logbox.scrollHeight;
			inputbox.focus();
		}
		// 回车事件
		function onSendBoxEnter(event) {
			console.log("回车发送！");
			if (event.keyCode == 13) {
				var message = inputbox.value;
				sendMessage(message);
				return false;
			}
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
		
		/***
		 *加载表情
		 */
		$(document).ready(function(){
			$.expBlock.initExp({
				/*
				//用户表情结构数据
				expData: [{name: '默认',icons:[{url:"../resources/js/plugins/exp/img/zz2_thumb.gif",title:"织"},{url:"../resources/js/plugins/exp/img/horse2_thumb.gif",title:"神马"}]}]
				//包含textarea和表情触发的exp-holder
				holder: '.exp-holder',
				//exp-holder中的textarea输入dom，默认为textarea,
				textarea : 'textarea',
				//触发dom
				trigger : '.exp-block-trigger',
				//每页显示表情的组数
				grpNum : 5,
				//位置相对页面固定(absolute)||窗口固定(fixed)
				posType : 'absolute',
				//表情层数
				zIndex : '100'
				*/
			});
			
			//使表情失效
			$.expBlock.disableExp();
			//使表情重新启动
			$.expBlock.enableExp();
			
			$('#J_sbt').click(function(){
				var s, ta = $('#inputbox'), val = ta.val();
				//将字符串中如"[微笑]"类的表情代号替换为<img/>标签
				s = $.expBlock.textFormat(val);
				alert(s);
				//console.log(s);
				$('#J_resulte').val(s);
			})
			
			/*
			 * ajax远程获取表情,注意同源策略
			 * 要求返回的数据格式如:[{name: groupname,icons:[{url:'imgurl',title:"iconname"},{url:'imgurl',title:"iconname"}]},{name: groupname,icons:[{url:'imgurl',title:"iconname"},{url:'imgurl',title:"iconname"}]},...]
			 */
			//$.expBlock.getRemoteExp(url);
			
		})
		
		function changeMessage(obj){
			if(obj.id == 'messageRadio')
				$("#teacher").hide();
			else
				$("#teacher").show();
		}
		function checkMessage(){
			//姓名
			var custName = $("#custname").val();
			if(!custName || custName.length>20){
				$.dialog.alert("姓名不能为空或超过20个字符!");
				return true;
			}
			//邮箱
			var email = $("#custemail").val();
			var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			if(email && (email.length>20 || !pattern.test(email))){
				$.dialog.alert("请输入正确的邮箱!");
				return true;
			}
			//电话
			var custPhone = $("#custphone").val();
			if (custPhone.replace("^[ ]+$", "").length != 0) {
				var pattenPhone = /^(0|86|17951)?(13[0-9]|15[012356789]|17[01678]|18[0-9]|14[57])[0-9]{8}$/;
				if (!pattenPhone.test(custPhone)) {
					$.dialog.alert("手机号码为空或者格式不正确!");
					return true;
				}
			}else{
				$.dialog.alert("手机号码为空或者格式不正确!");
				return true;
			}
			//QQ
			var qq = $("#custqq").val();
			var pattenQQ = /^[d]{20}$/;
			if(qq && !pattenQQ.test(qq)){
				$.dialog.alert("QQ号码格式不正确!");
				return true;
			}
			//MSN
			var msn = $("#custmsn").val();
			if(msn && msn.length>40){
				$.dialog.alert("MSN超长!");
				return true;
			}
			//单位
			var company = $("#custcompany").val();
			if(company && company.length>100){
				$.dialog.alert("单位超长!");
				return true;
			}
			//地址
			var address = $("#custaddress").val();
			if(address && address.length>100){
				$.dialog.alert("地址超长!");
				return true;
			}
			//留言内容
			var custContent = $("#custContent").val();
			if (custContent.replace("^[ ]+$", "").length <= 10 ||custContent.replace("^[ ]+$", "").length>200) {
				$.dialog.alert("留言内容应在10-200个字符之间!");
				return true;
			}
			
			return false;
		}
		function addMessage(){
			if(checkMessage()){
				return;
			}
			var replyWay="";
			$('input[name="replyWay"]').each(function(){    
				if(this.checked){
					replyWay=this.value;
				}
				//message += $(this).val() + ",";    
			  });
			var replyType="";
			$('input[name="replyType"]').each(function(){    
				if(this.checked){
					replyType=this.value;
				}
				//message += $(this).val() + ",";    
			  });
			var data = {
					   "customerId":$("#currentCustomerId").val(),
						"userId" : $("#teacher").val(),
						"replyWay" : replyWay,
						"replyType" :replyType,
						"name": $("#custname").val()?$("#custname").val():"",
						"email": $("#custemail").val()?$("#custemail").val():"",
						"phone": $("#custphone").val()?$("#custphone").val():"",
						"qq": $("#custqq").val()?$("#custqq").val():"",
						"msn": $("#custmsn").val()?$("#custmsn").val():"",
						"company": $("#custcompany").val()?$("#custcompany").val():"",
						"address": $("#custaddress").val()?$("#custaddress").val():"",
						"messageContent": $("#custContent").val().replace("^[ ]+$", "")
					};
			 $.ajax({
		    		type : "post",
		    		url : "/messageRecords/add.action",
		    		data : data,
		    		dataType : "json",
		    		async:false,
		    		success : function(data) {
		    			if (data.result == 0) {
		    				$.dialog.alert('留言成功!');
		    			} else {
		    				$.dialog.alert(data.msg);
		    			}
		    		},
		    		error : function(msg) {
		    			$.dialog.alert(data.msg);
		    		}
		    	});
		}
		//findWaitList(0);//获取等待列表
		function findWaitList(id){
			var data = {
					   "styleId":$("#styleId").val(),
						"id" : id
					};
			 $.ajax({
		    		type : "get",
		    		url : "/waitList/findById.action",
		    		data : data,
		    		dataType : "html",
		    		contentType : "application/json; charset=utf-8",
		    		async:false,
		    		success : function(data) {
		    			if(id == 0){
		    				logbox.innerHTML += "<div id='waitList'>"+data+"</div>";
		    			}else{
		    				$("#waitList").html(data);
		    			}
		    		},
		    		error : function(msg) {
		    			$.dialog.alert(data.msg);
		    		}
		    	});
			
		}
	
		// 客户端访客对话框架
		jQuery(".slideTab").slide({trigger:"click"});
		jQuery(".slideTab2").slide({trigger:"click"});
		jQuery(".g-sd3").slide({mainCell:"ul",autoPage:true,effect:"topLoop",autoPlay:true,vis:1});
</script> 

</body>
</html>