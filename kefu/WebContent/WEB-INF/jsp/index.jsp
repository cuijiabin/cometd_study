<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh" class="off">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服信息管理系统</title>
</head>
<body scroll="no" class="g-body">
<jsp:include page="top.jsp"/>
<div class="g-bd">
	<div class="g-sd f-fl">
    	<div id="leftMain" class="f-fl"></div>
        <a href="javascript:;" id="openClose" style="outline-style:none;outline-color:invert;outline-width:medium;" hideFocus="hidefocus" class="open" title="展开与关闭"><span class="f-dn">展开</span></a>
    </div>    
    <div class="g-mn">
    	<div class="g-mn-cnt">
            <div class="content" style="position:relative;overflow:hidden">
                <iframe name="right" id="rightMain" src="/iframe7.html" frameborder="false" scrolling="auto" style="border:none; margin-bottom:30px" width="100%" height="auto" allowtransparency="true"></iframe>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
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
	} else { 
	  window.onresize = resize;
	}
	function resize() {
		wSize();
		return false;
	}
}
function wSize(){
	//这是一字符串
	var str=getWindowSize();
	var strs= new Array(); //定义一数组
	strs=str.toString().split(","); //字符分割
	var heights = strs[0]-150,
		Body = $("body");
	$("#rightMain").height(heights-2);
	$("#rightMain").css("margin-bottom","-6px");
	//iframe.height = strs[0]-46;
	if(strs[1]<980){
		$(".g-head").css("width",980+"px");
		$(".g-bd").css("width",980+"px");
		Body.removeClass("g-body");
	}else{
		$(".g-head").css("width","auto");
		$(".g-bd").css("width","auto");
		Body.addClass("g-body");
	}
	var openClose = $("#rightMain").height()+25;
	$("#openClose").height(openClose+30);
	$("#leftMain").height(openClose-20);
}
wSize();
//左侧开关
$("#openClose").click(function(){
	if($(this).data("clicknum")==1) {
		$("html").removeClass("on");
		$(".g-sd").removeClass("left_menu_on");
		$(this).removeClass("close");
		$(this).data("clicknum", 0);
	} else {
		$(".g-sd").addClass("left_menu_on");
		$(this).addClass("close");
		$("html").addClass("on");
		$(this).data("clicknum", 1);
	}
	return false;
});

$(function(){
	$("#leftMain").load("/iframe3.html");
	$("#rightMain").attr("src", "/welcome.html");
})

function _M(menuid,targetUrl) {
	$("#leftMain").load(targetUrl);
	// $("#rightMain").attr("src", targetUrl);
	$(".nLi").removeClass("on");
	$("#_M"+menuid).addClass("on");
	
	// 显示左侧菜单，当点击顶部时，展开左侧
	$(".g-sd").removeClass("left_menu_on");
	$("#openClose").removeClass("close");
	$("html").removeClass("on");
	$("#openClose").data("clicknum", 0);
}
function _MP(menuid,targetUrl){
	$("#rightMain").attr("src", targetUrl);
	$(".submenu").removeClass("on");
	$("#_MP"+menuid).addClass("on");
}
</script>
</body>
</html>
    