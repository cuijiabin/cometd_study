<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xiaoma.kefu.util.CheckCodeUtil"  %>  
<%@ page import="javax.servlet.http.HttpSession"  %>  
<%@ page import="com.xiaoma.kefu.model.User"  %>  
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
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
 <% HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();
    Integer typeId = Integer.parseInt(String.valueOf(request.getAttribute("typeId")));
    String adds="";
    String updates="";
    String dels="";
    if(typeId==1){
    	adds="f_usesay_add";
    	updates="f_usesay_update";
    	dels="f_usesay_del";
    }else{
    	adds="f_persay_add";
    	updates="f_persay_update";
    	dels="f_persay_del";
    }
 %>
<div class="g-cnt">
<input type="hidden" readonly="readonly" name="userId" id="userId" value="${userId}" />
<input type="hidden" readonly="readonly" name="typeId" id="typeId" value="${typeId}" />
<input type="hidden" readonly="readonly" name="messageDailyId" id="messageDailyId" value="${messageType.id}" />
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd f-txtr">
           <% if(CheckCodeUtil.isCheckFunc(userId,adds)) {%>
            <button type="button" class="btn btn-primary btn-small f-fl" onclick="javascript:addMessageDaily();">添加</button>
            <%} %>
            <input class="c-wd150" type="text" id="serchTitle" name="serchTitle"/>
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
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);
//以下参数设置的是默认值
// var id =  1;   //设置树节点ID默认为1
// var title = '公共常用语分类设置';
// var sortId= 1;
var pId= 0;
// var status=1;
// var typeId=1;

/*
 * 条件查询
 */
  function find(currentPage){
	var url="/messageDaily/find.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[type]" : $("#typeId").val(),
			"typeId" : $("#typeId").val(), //仅供做权限时使用
			"map[title]":$("#serchTitle").val(),
			"map[userId]" : $("#userId").val(),
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
	        $.dialog.alert("出现异常！");
	    }
	});
  }

	/**
	 * 跳转新增前的页面
	 */
  function addMessageDaily(){
	  //初始判断，有无节点	
	  var messageDailyId=  $("#messageDailyId").val();
      if(messageDailyId ==''){
    	$.dialog.alert("没有找到有效的节点 ！");
   	   return false;
      }
    
       //不让在pId=0的节点（初级节点）下添加常用语
       var p = pId;
       if(p==0){
    		$.dialog.alert("不能在该节点下添加常用语  ！");
    	   return false;
       }
 	  
     //检查是否有子节点
	 if(checkChild()){
		    $.dialog.alert("请在子节点下添加常用语 ！");
		   return false;
	  }
     //获取树的id
	 var nodes = zTree.getSelectedNodes();
	 for (var i=0, l=nodes.length; i < l; i++) {
	   var tid = nodes[i].id;  //得到选中的树的id 
	  }
	var treeId = tid;
	var typeId = $("#typeId").val();
 	var d = $.dialog({id:'addMessageDaily' ,title:"添加常用语信息",content:'url:/messageDaily/new.action?treeId='+treeId+'&typeId='+typeId+' ',
 			lock:true, width:	600,height: 400});
 }
  /*
   * 添加前前检查是否有子节点信息
   */
  function checkChild(){
  	   var flag = false;
  	   //获取节点id
 	   var nodes = zTree.getSelectedNodes();
	   for (var i=0, l=nodes.length; i < l; i++) {
	   var   tid=	  nodes[i].id;  //得到选中的树的id 
	   }
  	var data = {
  			"treeId" : tid
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
  			$.dialog.alert("添加失败！");
  			flag = true;
  		}
  	});
  	return flag;
 }

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
	    	$.dialog.alert("删除成功 ！");
	    	changeTabal();
	    
		    },
		    error: function (msg) {
		    $.dialog.alert("删除失败！");
		    }
	    });
	 })
 }
  

//更新右侧表格数据
function changeTabal(){
	var url="/messageDaily/find.action";
	var data = {
			"userId": $("#userId").val(),
			"id": $("#messageDaily").val(),
			"typeId":$("#typeId").val()
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