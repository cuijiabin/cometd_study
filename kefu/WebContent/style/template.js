
//是否PC
function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

var isPc = IsPC();

$(function() {
	
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
	     data: {"styleId":styleId,"isPC":isPc},
	     timeout:30000,
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
	     data: {"styleId":styleId,"isPC":isPc},
	     dataType: "jsonp",
	     timeout:30000,
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
function gotoKF(buttonId){
	var url = "http://localhost:9090/dialogue/customerChat.action";
	var refer = getCookie('KF_refer');
	var landingPage = getCookie('KF_landingPage');
	var consultPage = window.location.href;
	window.open (url+"?refer="+refer+"&landingPage="+landingPage+"&consultPage="+consultPage+"&btnCode="+buttonId+"&styleId="+styleId) ;
}


if(!Array.prototype.map){
	Array.prototype.map = function(fn,scope) {
  		var result = [],ri = 0;
  		for (var i = 0,n = this.length; i < n; i++){
			if(i in this){
	  			result[ri++]  = fn.call(scope ,this[i],i,this);
			}
  		}
		return result;
	};
}
var getWindowSize = function(){
	return ["Height","Width"].map(function(name){
  		return window["inner"+name] ||
		document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ]
	});
}
window.onload = function (){
	if(!+"\v1" && !document.querySelector) { // for IE6 IE7
	  document.body.onresize = resize;
	  document.body.onscroll = resize;
	} else { 
	  window.onresize = resize;
	  window.onscroll = resize;
	}
	function resize() {
		findDimensions();
		return false;
	}
	resize();
}
function findDimensions(){
	// 这是一字符串
	var winStr=getWindowSize();
	var winStrs= new Array();
	winStrs=winStr.toString().split(",");
	var winHeight = winStrs[0],
		winWidth = winStrs[1];
	
	if(isPc){
		// 获取邀请框自身的宽高
		var boxWidth = document.getElementById("w-kfcbox").offsetWidth,
			boxHeight = document.getElementById("w-kfcbox").offsetHeight;
		var sTop = document.documentElement.scrollTop || document.body.scrollTop;
		
		// 自动居中
		document.getElementById("w-kfcbox").style.left = (winWidth-boxWidth)/2 + "px";
		document.getElementById("w-kfcbox").style.top = ((winHeight-boxHeight)/2 + sTop) + "px";
	}else{
		// 获取图片的宽高
		var imgWidth = document.getElementById("w-mkfcbox-img").offsetWidth,
			imgHeight = document.getElementById("w-mkfcbox-img").offsetHeight;
		
		// 设置邀请框外层宽高
		var boxWidth = document.getElementById("w-mkfcbox").style.width = imgWidth,
			boxHeight = document.getElementById("w-mkfcbox").style.height = imgHeight,
			iconHeight = document.getElementById("w-mkfrbox").style.height;
			document.getElementById("w-mkfcbox").style.width = imgWidth + "px";
			document.getElementById("w-mkfcbox").style.height = imgHeight + "px";
			document.getElementById("w-mkfcbox-img").style.width = 100 + "%";
		var sTop = document.documentElement.scrollTop || document.body.scrollTop;
		
		// 自定居中
		document.getElementById("w-mkfcbox").style.left = (winWidth-boxWidth)/2 + "px";
		document.getElementById("w-mkfcbox").style.top = ((winHeight-boxHeight)/2 + sTop) + "px";
		document.getElementById("w-mkfrbox").style.top = ((winHeight-imgHeight)/2) + "px";
	}

}

//隐藏漂浮框
function hiddenKfbox(){
	if(iconType == 1){
		document.getElementById("w-kfcbox").style.display = "none";
	}else if(iconType == 2){
		document.getElementById("w-kfrbox").style.display = "none";
	}else if(iconType == 3){
		document.getElementById("w-mkfcbox").style.display = "none";
	}
	
}


