<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh" class="off">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服信息管理系统</title>
</head>
<body scroll="no" class="objbody">
<jsp:include page="top.jsp"/>
<div id="content">
	<div class="left_menu pull-left">
    	<iframe name="lfetTreeBody" id="leftTreeBody" src="/customer/getLeft.action" frameborder="false" scrolling="auto" style="border:none;" width="100%" height="auto" allowtransparency="true"></iframe>
        <a href="javascript:;" id="openClose" hidefocus="hidefocus" class="open" title="展开与关闭"><span class="f-dn">展开</span></a>
    </div>
    <div class="col-auto">
    	<div class="col-1">
        	<div class="content" style="position:relative;overflow:hidden;">
            	<iframe name="rightBody" id="rightBody" src="/user/demo.action" frameborder="false" scrolling="auto" style="border: none;" width="100%" height="auto" allowtransparency="true"></iframe>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
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
	var heights = strs[0]-145,Body = $('body');$('#rightBody').height(heights-2);
	$('#rightBody').css("margin-bottom","-6px");
	//iframe.height = strs[0]-46;
	if(strs[1]<980){
		$('#content').css('width',980+'px');
		Body.attr('scroll','');
		Body.removeClass('objbody');
	}else{
		$('#content').css('width','auto');
		Body.attr('scroll','no');
		Body.addClass('objbody');
	}
	
	var openClose = $("#rightBody").height() + 25;
	$("#openClose").height(openClose);	
	$("#leftTreeBody").height(openClose);
}
wSize();
//左侧开关
$("#openClose").click(function(){
	if($(this).data('clicknum')==1) {
		$("html").removeClass("on");
		$(".left_menu").removeClass("left_menu_on");
		$(this).removeClass("close");
		$(this).data('clicknum', 0);
	} else {
		$(".left_menu").addClass("left_menu_on");
		$(this).addClass("close");
		$("html").addClass("on");
		$(this).data('clicknum', 1);
	}
	return false;
});
jQuery(".navcen").slide({ type:"menu", titCell:".nLi", targetCell:".sub", effect:"slideDown", delayTime:300, triggerTime:0, defaultPlay:false, returnDefault:true });
</script>
</body>
</html>
    