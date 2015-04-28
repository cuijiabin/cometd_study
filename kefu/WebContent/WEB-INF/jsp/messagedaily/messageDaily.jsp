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
        <li><i>&gt;</i>常用语设置</li>
    </ul>
</div>
<div class="g-cnt">
<input type="hidden" name="typeId" id="typeId" value="${typeId}" />
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd f-txtr">
            <button type="button" class="btn btn-primary btn-small f-fl" onclick="javascript:addMessageDaily();">添加</button>
            <input class="c-wd150" type="text" id="serchTitle" />
            <button type="button" class="btn btn-primary btn-small" onclick="javascript:find(1);">搜索</button>
        </div>
    </div>
    
	<div class="g-bd6 f-cb f-mar20">
		<div class="g-sd6 c-bor" >
		    <h3 class="u-tit c-bg">常用语分类设置</h3>
			<div id="tree_data">
				<jsp:include page="messageTree.jsp"/>
			</div>
		</div >
		<div id="table_data" class="g-mn6c">
		     <jsp:include page="messageDailyList.jsp"/>
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

/*
 * 条件查询
 */
  function find(currentPage){
	var url="/messageDaily/find.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[title]":$("#serchTitle").val(),
			"map[typeId]":1
	};
	
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    dataType: "html",
	    success: function (data) {
	       $("#table_data").html(data);
	    },
	    error: function (msg) {
	        alert(msg);
	    }
	});
  }

	/**
	 * 跳转新增前的页面
	 */
  function addMessageDaily(){

	var treeId = id;
 	var d = $.dialog({id:'addMessageDaily' ,title:"添加常用语信息",content:'url:/messageDaily/new.action?treeId='+treeId+' ',
 			lock:true, width:	600,height: 400});
}
	//以下参数设置的是默认值
  	var id =  1;   //设置树节点ID默认为1
	var title = '公共常用语分类设置';
	var sortId= 1;
	var pId= 0;
	var status=1;
	var typeId=1;
	/**
	 * 跳转编辑前的页面
	 */
 function toUpdate(messageDailyId){

	var d = $.dialog({id:'updateMessageDaily' ,title:"编辑常用语信息",content:'url:/messageDaily/toUpdate.action?messageDailyId='+messageDailyId,
			lock:true, width:	600,height: 400});
 }
	/**
	 * 删除
	 */
   function deleteMessageDaily(id){
	
 	  var url = "/messageDaily/delete.action";
	 var data = {
			 "id" :id
	 };

	 $.dialog.confirm('你确定要彻底删除吗？', function(){
	 $.ajax({
		 type :"get" ,
		 url : url,
		 data : data ,
		 contentType : "application/json; charset=utf-8",
	     dataType : "json",
	     success: function (data) {
	    	alert("删除成功 ！");
	    	changeTabal();
	    
		    },
		    error: function (msg) {
		    alert("删除失败！");
		    }
	    });
	 })
 }
  

//更新右侧表格数据
function changeTabal(){
	var url="/messageDaily/find.action";
	var data = {
			
			"id": $("#messageDaily").val(),
		
	};
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    dataType: "html",
	    success: function (data) {
	       $("#table_data").html(data);
	    },
	    error: function (msg) {
	        alert(msg);
	    }
	});
}
//新增常用语分类
function addCallback(){
	$.dialog({id:'addMessageDaily'}).close();
	
	changeTabal();
}

//编辑常用语分类
function editCallback(){
	$.dialog({id:'updateMessageDaily'}).close();

	changeTabal();
	
}
</script>
</body>
</html>