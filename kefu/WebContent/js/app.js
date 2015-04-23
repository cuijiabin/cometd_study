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
	var heights = strs[0]-98,
		heights2 = strs[0]-179,
		heights3 = strs[0]-99,
		widths = strs[1],
		Body = $("body");
	// 后台两列布局框架
	$(".g-bd").height(heights);
	$("#rightMain").height(heights-20);
	$("#rightMain").css("margin-bottom","-6px");
	$(".g-cnt").height(heights+57);
	// 客服端访客对话框架
	$(".g-bd2").height(heights2-5);
	$(".g-sd2l").height(heights2-7);
	$(".m-talk").height(heights2-33);
	$(".g-mn2c-cnt").height(heights2-39);
	$(".g-sd2r").height(heights2-5);
	$(".m-dialog2").height(heights3-246);
	$(".m-dialog2 .u-record").height(heights2-291);
	var heights5 = $(".m-dialog2").height()-$(".m-dialog2 .u-record").height();
	$(".m-dialog2 .u-operate").height(heights5);
	$(".m-dialog2 .u-txtarea").height(heights5-35);
	$(".m-dialog2  .u-txtarea").width($(".g-mn2c-cnt").width()-114);
	$(".m-sidemenu .m-sidemenu-cnt").height(heights2-229);
	$(".m-group-menu,.m-group-member,.m-group-tree").height(heights2-8);
	// 客户端访客对话框架
	$(".m-dialog").height(heights3-28);
	$(".m-message").height(heights3-28);
	$(".m-dialog .u-record").height(heights3-153);
	var heights4 = $(".m-dialog").height()-$(".m-dialog .u-record").height();
	$(".m-dialog .u-operate").height(heights4);
	$(".m-dialog .u-txtarea").height(heights4-35);
	$(".g-mn5c").width(widths-177);
	$(".m-dialog  .u-txtarea").width($(".g-mn5c").width()-112);
	$(".g-sd5").height(heights3-2);
	$(".slideTab .bd .tabBox").height(heights3-28);
	$(".g-mn6c .m-dialog").height(heights3-137);// 聊天记录详情
	$(".g-mn6c .m-dialog .u-record").height(heights3-163);// 聊天记录详情
	$(".m-recordtime").height(heights3-21);// 聊天记录详情
	
	if(strs[1]<1000){
		$(".g-head").css("width",1000+"px");
		$(".g-bd").css("width",1000+"px");
		Body.removeClass("g-body");
		// Body.css("background","#f1f1f1");
	}else{
		$(".g-head").css("width","auto");
		$(".g-bd").css("width","auto");
		Body.addClass("g-body");
	}
	// var openClose = $("#rightMain").height();
	$("#openClose").height(heights);
	$("#Scroll").height(heights-20);
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

/*
 * 是否正整数
 * 如果为空,则返回true
 * 如果是 返回true,如果不是,返回false
 */
function isInteger(value){
	if(value!=''){
		if(!(/^(\+|-)?\d+$/.test( value ))||value<=0){  
			return false;
	    }
	}
	return true;
}
