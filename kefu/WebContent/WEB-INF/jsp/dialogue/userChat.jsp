<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="/jsplugin/exp/css/style.css" />
</head>

<body scroll="no" class="g-body">
	<div class="g-head fixed">
		<a class="logo f-fl" href="#"><img src="/img/logo.png" height="42" alt="" /></a>
	    <ul class="u-admin f-fr">
	        <li><a href="#"><i class="icon-user"></i>欢迎 ${user.loginName}</a>
	        	<select class="c-wdat">
	                 <option selected="selected">在线</option>
	                 <option value="离线">离线</option>
	             </select>
	        </li>
	        <li><a target="_blank" href="/user/main.action?typeId=2"><i class="icon-logout"></i>客服系统</a></li>
	        <li><a target="_blank" href="/user/main.action?typeId=6"><i class="icon-logout"></i>设置</a></li>
	        <li><a href="/user/logout.action"><i class="icon-logout"></i>退出</a></li>
	    </ul>
	</div>
	
	<div class="g-bd1 f-cb">
		<div class="g-bd1c f-cb c-bor">
	        <div class="g-mn1 f-fl"></div>
	        <div class="g-sd1 f-fr">
	         	<ul class="f-cb">
	                <li><i>|</i><a href="javascript:switchCustomer()">客服转接</a><i>|</i></li>
	            </ul>
	        </div>
	    </div>
	</div>
	
	<div class="clear"></div>
	<div class="g-bd2 f-cb">
	    <!-- 左侧对话列表 start -->
	    <div class="g-sd2l c-bor">
	        <h3 class="u-tit c-bg" id="currentDialogueNum">当前共有0个对话</h3>
	        <ul class="m-talk" id="customerList"></ul>
	    </div>
	    <!-- 左侧对话列表 end -->
	    
	    <div class="g-mn2">
	        <div class="g-mn2c">
	            <div class="g-mn2c-cnt c-bor">
	                <!-- 隐藏当前通信连接点 id -->
	                <input type="hidden" id="currentCcnId"/>
	                <!-- 隐藏当前客户 id -->
	                <input type="hidden" id="currentCustomerId"/>
	                
	        		<h3 class="u-tit c-bg" id="contentTitle">欢迎使用客服系统，与客服系统连接成功</h3>
	        		
	        		<!-- 对话框中客户信息 start -->
	        		<div class="u-userinfo f-padd10" id="dialugueCustomerInfo">
	                	<p class="f-mbn">编号：<span id="showDid"></span> 与您对话<a class="f-fr" href="javascript:forbidCuntomer()"><b>阻止访客</b></a></p>
	                    <p class="f-mbn">咨询页面：<span id="showDpage">http://wap.xiaoma.com/yasi/</span></p>
	                    <p class="f-mbm">IP地址：<span id="showDipInfo">北京市 [ 北京长城 ]</span></p>
	                </div>
	                <!-- 对话框中客户信息 end -->
	                
	                <div class="m-dialog2">
	                
	                    <!-- 对话内容 start -->
	                    <div class="u-record r-sms-manager" id="logbox"></div>
	                    <!-- 对话内容 end -->
	                    
	                    <div class="u-operate">
	                    
	                        <!-- 图标按钮 start -->
	                        <div class="u-operatebar c-bg">
	                        	<ul class="u-operatebar-btn">
	                            	<li><a class="exp-block-trigger" href="javascript:;"><img src="/img/icon_01.png" alt="" /></a></li>
	                            	<li><img src="/img/icon_02.png" alt="" /></li>
	                            	<li><img src="/img/icon_03.png" alt="" /></li>
	                            	<li><img src="/img/icon_04.png" alt="" /></li>
	                            	<li><img src="/img/icon_05.png" alt="" /></li>
	                            </ul>
	                        </div>
	                        <!-- 图标按钮 end -->
	                        
	                        <div class="u-input f-cb">
	                        
	                            <!-- 消息发送窗口 start -->
	                            <textarea class="u-txtarea" id="inputbox" onkeypress="return onSendBoxEnter(event);"></textarea>
	                            <!-- 消息发送窗口 end -->
	                            
	                            <div class="u-send">
	                                <div class="btn-group">
	                                    <a class="btn btn-primary" href="javascript:sendMessage(inputbox.value);">发送</a>
	                                    <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
	                                    	<span class="caret"></span>
	                                    </button>
	                                    
	                                    <ul class="dropdown-menu f-txtl">
	                                        <li><a><label><input type="radio" name="send" checked value="1">Enter 发送</label></a></li>
	                                        <li><a><label><input type="radio" name="send" value="2">Ctrl + Enter 发送</label></a></li>
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
	        	<div class="f-padd10 f-oh" id="customerInfo">
	            	<p class="f-mbn f-cb"><a class="f-fl"><span id="showname"></span></a><a class="f-fr" onclick="javascript:addCustomerInfo()">添加访客信息</a></p>
	            	<p class="f-mbm" name="customerIpInfo"><span id="showipInfo"></span></p>
	                <p class="m-from-sm"><button class="btn btn-small" type="button" onclick="showDialugueHistory()">查看聊天记录</button></p>
	            </div>
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
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/comet4j.js"></script>
<script type="text/javascript" src="/jsplugin/exp/exp.js"></script>
<script language="javascript" for="window" event="onload"> 
	
	// 引擎事件绑定
	JS.Engine.on({
		start : function(cId, aml, engine) {
			var style = engine.getConnector().workStyle;
			style = style === 'stream'?'长连接':'长轮询';
			console.log("style: "+style+" ,cId: "+cId);
			
			var str = ['<div class="r-offline"><span class="alert alert-success">恭喜你，连接成功</span></div>'];
			logbox.innerHTML += str.join('');
		},
		stop : function(cause, url, cId, engine) {
			var str = ['<div class="r-offline"><span class="alert alert-error">对不起，连接失败</span></div>'];
			logbox.innerHTML += str.join('');
		},
		dialogue : function(data, engine) {
			switch (data.type) {
			
				case 'on_message': 
					onMessage(data);//收到聊天消息
					break;
				case 'end_dialogue':
					updateCustomerList();//结束对话
					break;
				case 'on_switch_from':
					updateCustomerList();//转接给其他客服
					break;
				case 'on_switch_to':
					updateCustomerList();//被客服转接
					break;
				case 'on_open': 
					updateCustomerList();//上线 发送更新用户列表请求
					break;
				case 'on_close': 
					updateCustomerList();//下线 发送更新用户列表请求
					break;
			}
		}
	});
	
	
	// 开启连接
	function start(){
		JS.Engine.start('/conn');
		inputbox.focus();
	}
	
	// 更新用户列表请求 (**)
	function updateCustomerList() {
		if (!JS.Engine.running)
			return;
		var data = {"ccnId":JS.Engine.getId()};
		$.ajax({
		    type: "get",
		    url: "/dialogue/receiveList.action",
		    data: data,
		    contentType: "application/json; charset=utf-8",
		    dataType: "html",
		    success: function (data) {
		    	$("#customerList").html(data);
		    	setDefaultInfo();//初始值
		    },
		    error: function () {
		        console.log("updateCustomerList Error");
		    }
		});
	}

	start();
	$("#customerInfo").hide();
	$("#dialugueCustomerInfo").hide();
	
	// 检测并设置初始值  在updateCustomerList()中回调 (**)
	function setDefaultInfo(){
		var size = $("#customerList li").length;
		$("#currentDialogueNum").html("当前共有"+size+"个对话");
		if(size > 0){
			var ccnId = $("#customerList li:first").attr("id");
			var customerId = $("#customerList li:first").attr("customerId");
			$("#customerInfo").show();
			$("#dialugueCustomerInfo").show();
			changeTitle(customerId,ccnId);
		}else{
			$("#customerInfo").hide();
			$("#dialugueCustomerInfo").hide();
			$("#contentTitle").html("欢迎使用客服系统，与客服系统连接成功");
		}
	}
	
	// 切换对话客户 (**)
	function changeTitle(customerId,ccnId){
		
		$("#contentTitle").html("与客户"+customerId+"对话中");
		$("#currentCcnId").val(ccnId);
		$("#currentCustomerId").val(customerId);
		
		switchDialogueBox(ccnId);
		showCustomerInfo(customerId);
	}
	
	// 获取客户信息 (**)
	function showCustomerInfo(customerId){
		$.ajax({
		    type: "get",
		    url: "/customer/info.action",
		    data: {"customerId":customerId},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	var customerName = (data.customerName == null || data.customerName.length == 0) ? "--" : data.customerName;
		    	$("#showDid").html(customerId);
		    	$("#showname").html(customerName);
				$("#showDipInfo").html(data.ipInfo);
				$("#showipInfo").html(data.ipInfo);
		    },
		    error: function (msg) {
		        console.log("showCustomerInfo Error");
		    }
		});
	}
	
	// 发送聊天信息动作(**)
	function sendMessage(message) {
		if (!JS.Engine.running)
			return;
		message = message.trim();
		if (!message)
			return;
		var userCId = JS.Engine.getId();
		var cusCId = $("#currentCcnId").val();
		console.log("发送对象："+cusCId);
		var param = "userCId=" +userCId+'&cusCId='+cusCId+'&message='+ encodeURIComponent(message);
		JS.AJAX.post('/chat/toCustomer.action', param, function() {
			inputbox.value = '';
		});
	}
	// 回车事件 ctrl键暂时不太管用(**)
	function onSendBoxEnter(event) {
		var sendType="";
		$('input[name="send"]').each(function(){ if(this.checked){sendType=this.value;} });
		
		console.log("发送快捷方式："+sendType);
		if(sendType == '1'){
			if (event.keyCode == 13) {
				var message = inputbox.value;
				sendMessage(message);
				return false;
			}
		}else{
			if (event.ctrlKey && event.keyCode == 13) {
				var message = inputbox.value;
				sendMessage(message);
				return false;
			}
		}
	}
	
	// 用户聊天通知(**)
	function onMessage(data) {
		data = data.obj
		console.log("收到消息了！");
		var id = data.id;
		console.log(id);
		if(id == JS.Engine.getId()){
			id = $("#currentCcnId").val();
		}
		//创建隐藏div
		createHiddenDiv(id);
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
		$("#D"+id).append(str.join(''));
		switchDialogueBox(id);
		moveScroll();
	}
	//切换对话框窗口 (**)
	function switchDialogueBox(ccnId){
		logbox.innerHTML = $("#D"+ccnId).html();
		$("#currentCcnId").val(ccnId);
	}
	
	//创建隐藏div (**)
	function createHiddenDiv(ccnId){
		if ($("#D"+ccnId).length > 0){ 
			return;
		}else{
			$("#logbox").after("<div id='D"+ccnId+"' style='display: none;'></div>");
		}
	}

	//结束对话(**)
	function endDialogue(customerCnnId){
		customerCnnId = customerCnnId.replace("\"","").replace("\"","");
		alert("确定结束对话？");
		var ccnId = JS.Engine.getId();
		var param = "ccnId="+ccnId+'&type='+2+'&endCcnId='+customerCnnId;
		JS.AJAX.post('/chat/endDialogue.action', param, function() {
			$("#"+customerCnnId).remove();
			setDefaultInfo();
		});
		
	}
	
	function switchCustomer1(remark,toUserId){
		if (!JS.Engine.running)
			return;
		remark = remark.trim();
		if (!remark)
			return;
		var ccnId = JS.Engine.getId();
		var customerId = $("#currentCustomerId").val();
		var customerCcnId = $("#currentCcnId").val();
		var param = "ccnId="+ccnId+'&customerId='+customerId+'&customerCcnId='+customerCcnId+'&remark='+remark+'&toUserId='+toUserId;
		JS.AJAX.post('/chat/switchDialogue.action', param, function() {
		});
	}
	
	//添加访客信息 (**) 不是很全
	function addCustomerInfo(){
		var customerId = $("#currentCustomerId").val();
		var customerName = $("#showname").html();
		var content ='<table>'
		                  +'<tr>'
		                  +' <td>客服编号：'+customerId+'</td>'
		                  +'</tr>'
		                  +'<tr>'
		                  +'<td>姓名：<input id="updateName" type="text" value="'+customerName+'" /></td>'
		                  +'</tr>'
		                  +'</table>';
		
		var d = $.dialog({
		    id: 'addCustomerInfo',
		    title:"修改客户姓名",
		    content:content,
		    button: [
		        {
		            name: '确定',
		            callback: function () {
		            	name = $("#updateName").val();
		            	console.log("param: "+customerId+" ,name: "+name);
		            	updateName(customerId, name);
		            },
		            focus: true
		        },
		        {
		            name: '取消',
		            callback: function () {
		            }
		        }
		    ]
		});
	}
	
	function updateName(customerId,name){
		
		var url="/customer/upName.action";
		var data = {
				"customerId":customerId,
				"customerName":name
		};
		$.ajax({
		    type: "get",
		    url: url,
		    data: data,
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	showCustomerInfo(customerId);
		    	$.dialog.alert("修改成功");
		    },
		    error: function (msg) {
		    	$.dialog.alert("修改失败");
		    }
		});
	}
	
	
	// 移动滚动条
	function moveScroll() {
		logbox.scrollTop = logbox.scrollHeight;
		inputbox.focus();
	}
	
	//客服转接对话框
	function switchCustomer(){
		var customerId = $("#currentCustomerId").val();
		
		if(customerId == null || customerId.length ==0){
			$.dialog.alert("没有选中客户");
		}else{
			$.ajax({
			    type: "get",
			    url: "/dialogue/switchList.action?customerId="+customerId,
			    contentType: "application/json; charset=utf-8",
			    dataType: "html",
			    success: function (data) {
			    	$.dialog({
						content:data,
						title:"客服转接",
						width: 400,
						height: 300,
						id:'editBlackList',
						button: [
							        {
							            name: '确定',
							            callback: function () {
							            	var switchUserId = $("#switchUserId").val();
							        		var switchContent = $("#switchContent").val();
							        		alert(switchContent+" ,== "+switchUserId);
							        		switchCustomer1(switchContent,switchUserId);
							            },
							            focus: true
							        },
							        {
							            name: '取消',
							            callback: function () {
							            }
							        }
							    ]});
			    },
			    error: function () {
			    	$.dialog.alert("获取转接客户失败！");
			    }
			});
		}
	}
	
	//阻止访客提示(**)
	function forbidCuntomer(){
		var customerId = $("#currentCustomerId").val();
		if(customerId == null ||customerId.length == 0){
			alert("请选择需要阻止的客户！");
		}
		var content ='<table>'
            +'<tr>'
            +' <td>客服编号：'+customerId+'</td>'
            +'</tr>'
            +'<tr>'
            +'<td>阻止原因：<input id="forbidReason" type="text"/></td>'
            +'</tr>'
            +'</table>';
		var d = $.dialog({
		    id: 'forbidCuntomer',
		    title:"阻止访客",
		    content:content,
		    button: [
		        {
		            name: '确定',
		            callback: function () {
		            	var remark = $("#forbidReason").val();
		            	console.log("访客阻止param: "+customerId+" ,name: "+remark);
		            	realForbidCuntomer(remark);
		            },
		            focus: true
		        },
		        {
		            name: '取消',
		            callback: function () {
		            }
		        }
		    ]
		});
	}
	//确定阻止访客(**)
	function realForbidCuntomer(remark){
		var customerCcnId = $("#currentCcnId").val();
		var ccnId = JS.Engine.getId();
		var param = "ccnId="+ccnId+'&customerCcnId='+customerCcnId+'&remark='+remark;
		JS.AJAX.post('/chat/forbidCuntomer.action', param, function() {
			$("#"+customerCcnId).remove();
			setDefaultInfo();
		});
	}
	
	// 查看客户对话记录(**) 重复点击的话会变得重复！
	function showDialugueHistory(){
		var customerCcnId = $("#currentCcnId").val();
		var data= {"customerId":$("#currentCustomerId").val()};
		$.ajax({
		    type: "get",
		    url: "/dialogue/history.action",
		    data: data,
		    contentType: "application/json; charset=utf-8",
		    dataType: "html",
		    success: function (content) {
		    	createHiddenDiv(customerCcnId);
		    	content = content + $("#D"+customerCcnId).html();
		    	$("#D"+customerCcnId).html(content);
		    	switchDialogueBox(customerCcnId);
		    },
		    error: function () {
		        console.log("showDialugueHistory Error");
		    }
		});
	}
	
</script>
<script type="text/javascript">
	
    // 去空格操作
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	};
	
	// HTML编码
	String.prototype.HTMLEncode = function() {
		var temp = document.createElement("div");
		(temp.textContent != null) ? (temp.textContent = this): (temp.innerText = this);
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
	// 客服端访客对话框架-手风琴菜单
jQuery(".m-sidemenu").slide({titCell:"h3", targetCell:".m-sidemenu-cnt", defaultIndex:0, effect:"slideDown", delayTime:300, trigger:"click"});
</script>
</body>
</html>
