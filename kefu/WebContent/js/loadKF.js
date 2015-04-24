$(function() {
	alert('loadKF.js onload');
	//set refer
	var oldRefer = getCookie("KF_refer");
	if(oldRefer==''){
		setCookie("KF_refer", document.referrer);
	}
	
	//set landing page
	var oldLanding = getCookie("KF_landingPage");
	if(oldLanding==''){
		var local = window.location.href;
		var end = local.indexOf('?');
		if(end!=-1){
			local = local.substring(0, local.indexOf('?'));
		}
		setCookie("KF_landingPage", local);
	}
	
	//load div
	addDialogueDiv();
	addIconDiv();
})

//设置cookies
function setCookie(name, value) {
	// var Days = 30;
	// var exp = new Date();
	// exp.setTime(exp.getTime() + Days*24*60*60*1000);
	// document.cookie = name + "="+ escape (value) + ";expires=" +
	// exp.toGMTString();
	document.cookie = name + "=" + escape(value) + ";path=/";
}

//读取cookies
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg)) {
		return unescape(arr[2]);
	} else {
		return "";
	}
}

//创建邀请框div
function addDialogueDiv(){
	//创建一个div
	var div = document.createElement('div');
	$.ajax({
	     type: "get",
	     async: true,
	     url: "http://localhost:9090/style/getDialogueDiv.action",
	     dataType: "jsonp",
	     jsonp: "callbackDialogue", //服务端用于接收callback调用的function名的参数  
	     jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来 ,success_jsonpCallback是jQuery的默认实现,可以自定义
	     success: function(json) {
    		div.innerHTML = json[0].name;
    		var bo = document.body;//获取body对象.
    		//动态插入到body中
    		bo.insertBefore(div,bo.lastChild);
	     },
		 error: function (msg) {
	    	alert('action error1!');
	    }
	 });
}

//创建邀请框div
function addIconDiv(){
	//创建一个div
	var div = document.createElement('div');
	$.ajax({
	     type: "get",
	     async: true,
	     url: "http://localhost:9090/style/getIconDiv.action",
	     data: {"styleId":1},
	     dataType: "jsonp",
	     jsonp: "callbackIcon", //服务端用于接收callback调用的function名的参数  
	     jsonpCallback: "success_jsonpCallback1", //callback的function名称,服务端会把名称和data一起传递回来  
	     success: function(json) {
	    	div.innerHTML = json[0].name;
    		var bo = document.body;//获取body对象.
    		//动态插入到body中
    		bo.insertBefore(div,bo.lastChild);
	     },
		 error: function (msg) {
	    	alert('action icon error!');
	    }
	 });
}

//跳转到客服窗口
function gotoKF(){
	var url = "http://localhost:9090/dialogue/customerChat.action";
	var refer = getCookie('KF_refer');
	var landingPage = getCookie('KF_landingPage');
	var consultPage = window.location.href;
	window.open (url+"?refer="+refer+"&landingPage="+landingPage+"&consultPage="+consultPage) ;
}
