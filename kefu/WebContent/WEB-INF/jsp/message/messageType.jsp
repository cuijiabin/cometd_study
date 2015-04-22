<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/jsplugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
</head>
<body>
<!--  面包屑  -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">常用语管理</a></li>
        <li><i>&gt;</i>公共常用语分类</li>
    </ul>
</div>
<div class="g-cnt">
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd f-txtr">
            <button type="button" class="btn btn-primary btn-small f-fl" onclick="javascript:addMessageType();">添加</button>
            <button type="button" class="btn btn btn-warning btn-small f-fl" onclick="javascript:updateMessageType();">编辑</button>
            <button type="button" class="btn btn btn-danger btn-small f-fl">删除</button>
            <input class="c-wd150" type="text" />
            <button type="button" class="btn btn-primary btn-small">搜索</button>
        </div>
    </div>
    
	<div class="g-bd6 f-cb f-mar20">
		<div class="g-sd6 c-bor" >
		    <h3 class="u-tit c-bg">常用语分类设置</h3>
			<jsp:include page="messageTree.jsp"></jsp:include>
		</div >
		
		 <div id="table_data" class="g-mn6">
		     <jsp:include page="messageTypeDetail.jsp"></jsp:include>
		</div>
	</div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

/**
 * 跳转新增前的页面
 */
function addMessageType(event, treeId, treeNode){

	var treeId = id;
	var tyd = typeId;
	var d = $.dialog({id:'addMessageType' ,title:"添加分类信息",content:'url:/messageType/new.action?treeId='+treeId+'&typeId='+tyd+' ',
			lock:true, width:	600,height: 400});
}

/**
 * 跳转编辑前的页面
 */
function updateMessageType(){

	var treeId = id;
	var ti = title;
	var so= sortId;
	var pI= pId;
	var st=status;
	var tyd = typeId;
	var d = $.dialog({id:'updateMessageType' ,title:"编辑分类信息",content:'url:/messageType/toUpdate.action?treeId='+treeId+'&typeId='+tyd+'&title='+ti+'&sortId='+so+'&pId='+pI+'&status='+st+' ',
			lock:true, width:	600,height: 400});
}

//编辑常用语分类
function editCallback(){
	$.dialog({id:'editBlackList'}).close();
	var pageNo = '${pageBean.currentPage}';
	find(pageNo);
}


//新增常用语分类
function addCallback(){
	$.dialog({id:'addMessageType'}).close();
	find(1);
}
</script>
</body>
</html>