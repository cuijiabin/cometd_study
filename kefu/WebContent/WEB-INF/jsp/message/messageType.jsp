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
	<input type="hidden" name="typeId" id="typeId" value="${typeId}" />
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd f-txtr">
            <button type="button" class="btn btn-primary btn-small f-fl" onclick="javascript:addMessageType();">添加</button>
            <button type="button" class="btn btn btn-warning btn-small f-fl" onclick="javascript:updateMessageType();">编辑</button>
            <button type="button" class="btn btn btn-danger btn-small f-fl" onclick="javascript:deleteMessageType();">删除</button>
            <input class="c-wd150" type="text" />
            <button type="button" class="btn btn-primary btn-small">搜索</button>
        </div>
    </div>
    
	<div class="g-bd6 f-cb f-mar20">
		<div class="g-sd6 c-bor" >
		    <h3 class="u-tit c-bg">常用语分类设置</h3>
			<div id="tree_data">
				<jsp:include page="messageTree.jsp"></jsp:include>
			</div>
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
  function addMessageType(){

	var treeId = id;
	var tyd = typeId;
	var d = $.dialog({id:'addMessageType' ,title:"添加分类信息",content:'url:/messageType/new.action?treeId='+treeId+'&typeId='+tyd+' ',
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
 function updateMessageType(){

	var d = $.dialog({id:'updateMessageType' ,title:"编辑分类信息",content:'url:/messageType/toUpdate.action?treeId='+$("#messageTypeId").val(),
			lock:true, width:	600,height: 400});
 }
	/**
	 * 删除
	 */
   function deleteMessageType(){
	  var treeId = id;
 	  var url = "/messageType/delete.action";
	 var data = {
			 "treeId" :treeId
	 };
	 if(checkChild()){
		    alert("请先删除子节点信息！");
		   return false;
	   }
	 $.dialog.confirm('你确定要彻底删除吗？', function(){
	 $.ajax({
		 type :"get" ,
		 url : url,
		 data : data ,
		 contentType : "application/json; charset=utf-8",
	     dataType : "json",
	     success: function (data) {
	    	alert("删除成功 ！");
	    	var node;
	    	   var nodes = zTree.getSelectedNodes();
	    	   if (nodes.length > 0) {
			  	   node =nodes[0].getParentNode();
			  	   zTree.selectNode(node);
			   }
		  	   for (var i=0, l=nodes.length; i < l; i++) {
		  	   	  zTree.removeNode(nodes[i]);
		  	   }
		  	   changeDetail(node.id);
		    },
		    error: function (msg) {
		    alert("删除失败！");
		    }
	    });
	 })
 }
   /*
    * 删除前检查是否有子节点信息
    */
   function checkChild(){
   	var flag = false;
    var treeId = id;
   	var data = {
   			"treeId" : treeId
   	};
   	$.ajax({
   		type : "get",
   	     url : "/messageType/check.action",
   		data : data,
   		contentType : "application/json; charset=utf-8",
   		dataType : "json",
   		async:false,
   		success : function(data) {
   			if(data.code==0){
   				
   			}else{
   				flag = true;
   			}
   		},
   		error : function(msg){
   			alert(flag);
   			alert("删除失败！");
   			flag = true;
   		}
   	});
   	return flag;
   }

//更新树
//获取树选中节点，提交。查询出新的树，替换相应的div
function changeTree(){
	var url="/messageType/treeList.action";
	var data = {
			"typeId":$("#typeId").val(),
			"id": $("#messageTypeId").val()
	};
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    dataType: "html",
	    success: function (data) {
	       $("#tree_data").html(data);
	    },
	    error: function (msg) {
	        alert(msg);
	    }
	});
}
//更新右侧详细页 
//获取树选中节点，提交。查询出新的节点详细，替换相应的div
function changeDetail(id){
	var url="/messageType/detail.action";
	var data = {
			"typeId":$("#typeId").val(),
			"id": id==null?$("#messageTypeId").val():id
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
	$.dialog({id:'addMessageType'}).close();
	changeTree();
	changeDetail();
}

//编辑常用语分类
function editCallback(){
	$.dialog({id:'updateMessageType'}).close();
	changeTree();
	changeDetail();
	
}
</script>
</body>
</html>