<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xiaoma.kefu.util.CheckCodeUtil"  %>  
<%@ page import="javax.servlet.http.HttpSession"  %>  
<%@ page import="com.xiaoma.kefu.model.User"  %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<div class="m-crumb" >
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">常用语管理</a></li>
        <li><i>&gt;</i>常用语分类</li>
    </ul>
</div>
 <% HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();
    Integer typeId = Integer.parseInt(String.valueOf(request.getAttribute("typeId")));
    String adds="";
    String updates="";
    String dels="";
    if(typeId==1){
    	adds="f_calset_add";
    	updates="f_calset_update";
    	dels="f_calset_del";
    }else{
    	adds="f_perclass_add";
    	updates="f_perclass_update";
    	dels="f_perclass_del";
    }
 %>
<div style="margin:50px " >
<div class="g-cnt">
	<input type="hidden" readonly="readonly" name="typeId" id="typeId" value="${typeId}" />
	<input type="hidden" readonly="readonly" name="userId" id="userId" value="${userId }"/>
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd f-txtr">
           
            <% if(CheckCodeUtil.isCheckFunc(userId,adds)) {%>
            <button type="button" class="btn btn-primary btn-small f-fl" onclick="javascript:addMessageType();">添加</button>
            <%}%>
             <% if(CheckCodeUtil.isCheckFunc(userId,updates)) {%>
            <button type="button" class="btn btn btn-warning btn-small f-fl" onclick="javascript:updateMessageType();">编辑</button>
            <%}%>
             <% if(CheckCodeUtil.isCheckFunc(userId,dels)) {%>
            <button type="button" class="btn btn btn-danger btn-small f-fl" onclick="javascript:deleteMessageType();">删除</button>
            <%}%>
            <input class="c-wd150" type="text" id="searTitle" name="searTitle"/>
            <button type="button" class="btn btn-primary btn-small" onclick="javascript:find();">搜索</button>
        </div>
    </div>
	<div class="g-bd6 f-cb f-mar20">
		<div class="g-sd6 c-bor" >
		    <h3 class="u-tit c-bg">常用语分类设置</h3>
			<div id="tree_data">
				<jsp:include page="messageTree.jsp"></jsp:include>
			</div>
		</div >
		
		<div class="g-mn6">
			<div id="table_data" class="g-mn6c">
		     <jsp:include page="messageTypeDetail.jsp"></jsp:include>
		     </div>
		</div>
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
// //以下参数设置的是默认值
// 	var id =  1;   //设置树节点ID默认为1
// var title = '公共常用语分类设置';
// var sortId= 1;
// var pId= 0;
// var status=1;
// var typeId=1;

/**
 *查询
 */
function find(){
	var url="/messageType/search.action";
	var data = {
			"title" : $("#searTitle").val().trim(),
			"typeId" : $("#typeId").val().trim(),
			"userId" : $("#userId").val().trim()
	};
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    dataType: "html",
	    success: function (data) {
	       $("#table_data").html(data);
	     	var messageTypeId = $("#messageTypeId").val();
			  	//获取节点id 等于  messageTypeId 的节点数据
		   var node = zTree.getNodeByParam("id", messageTypeId,null);
	       zTree.selectNode(node);
	    },
	    error: function (msg) {
	        $.dialog.alert("没找到相应的分类 ！");
	    }
	});
}

	/**
	 * 跳转新增前的页面
	 */
   function addMessageType(){
       //跳转到添加的页面前进行判断该节点下是否有常用语（有，不执行添加；没有，执行添加）	
	   if(checkMessage_Daily()){
		    $.dialog.alert("该分类下已存在常用语，不可继续添加分类 ！");
		   return false;
	   }
	  //第一次添加的情况
	   var treeId = null;
	   if($("#messageTypeId").val() == ''){
		    treeId =0;
	    }else{
	    	treeId = $("#messageTypeId").val();
	    }
	var tyd = $("#typeId").val();
 	var d = $.dialog({id:'addMessageType' ,title:"添加分类信息",content:'url:/messageType/new.action?treeId='+treeId+'&typeId='+tyd+' ',
 			lock:true, width:	600,height: 400});
}
   /*
    *添加常用语分类时检查他下面是否有常用语
    */
  function checkMessage_Daily(){
	 var messageTypeId =   $("#messageTypeId").val();
	  //第一次添加的情况
	   if(messageTypeId == ''){
	    	return false;
	    }else{
	    	messageTypeId =   $("#messageTypeId").val();
	    }
   var flag = false;
   	var data = {
   			"messageTypeId" : messageTypeId
   	};
   	$.ajax({
   		type : "get",
   	     url : "/messageDaily/check.action",
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
   			$.dialog.alert(flag);
   			$.dialog.alert("添加失败！");
   			flag = true;
   		}
   	});
   	return flag;
}

	/**
	 * 跳转编辑前的页面
	 */
 function updateMessageType(){

	var d = $.dialog({id:'updateMessageType' ,title:"编辑分类信息",content:'url:/messageType/toUpdate.action?treeId='+$("#messageTypeId").val(),
			lock:true, width:	600,height: 360});
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
	 //检查是否有子节点
	 if(checkChild()){
		    $.dialog.alert("请先删除子节点信息！");
		   return false;
	   }
	 //检查该节点下是否有常用语
	 if(checkMessage_Daily()){
		    $.dialog.alert("请先删除该节点下的常用语 ！");
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
	    	$.dialog.alert("删除成功 ！");
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
		    $.dialog.alert("删除失败！");
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
   			"treeId" : $("#messageTypeId").val()
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
   			$.dialog.alert(flag);
   			$.dialog.alert("删除失败！");
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
	        $.dialog.alert(msg);
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
	        $.dialog.alert(msg);
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