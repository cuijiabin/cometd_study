<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
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
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li>系统设置</li>
        <li><i>&gt;</i>风格管理</li>
        <li><i>&gt;</i>业务分组</li>
    </ul>
</div>
<div class="g-cnt f-padd20">
    <div class="g-bd7 f-cb c-bor">
        <div class="g-sd71">
            <h3 class="u-tit c-bg">已有分组</h3>
            <div class="m-group-menu f-txtc">
                <ul id="myul" class="e_tit">
                	<c:forEach var="group" items="${groupList }">
        				<li id="li${group.id }"><a class="f-txtc" href="#" onClick="showDetail(${group.id})">${group.name }</a> 
        					<span class="u-close f-fr" onClick="delGroup(${group.id})">&times;</span>
        				</li>
					</c:forEach>
                </ul>
                <button type="button" class="btn btn-primary btn-small f-mtm" onclick="addGroup();">添加分组</button>
            </div>
        </div>
       	<input type="hidden" id="currentGroupId" value="${currentGroupId }" />
        <div class="g-mn7" id="table_data">
			<jsp:include page="groupDetail.jsp"></jsp:include>
        </div>
        <div class="g-sd72">
            <h3 class="u-tit c-bg">添加客服到分组</h3>
            <div class="m-group-tree">
            	<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
            </div>
        </div>
    </div>
</div>

<div class="g-bd6 f-cb f-mar20">
    <div class="g-mn6">
        <div class="g-mn6c">
        	<div id="table_data">
				<jsp:include page="groupDetail.jsp"></jsp:include>
			</div>
        </div>
    </div>
    
    
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
	switchClass('${currentGroupId }');
})(jQuery);

//切换选中效果
function switchClass(id){
	var liobj=$("#myul li");
    liobj.each(function(){
    	if($(this).attr("id")==('li'+id)){
    		$(this).addClass("on");
    	}else{
    		$(this).removeClass("on");
    	}
    });
}

var zTree;
var demoIframe;
var setting = {
	view:{
		dblClickExpand: false,//双击节点时，是否自动展开父节点的标识 默认值: true
		showLine: false,//设置 zTree 是否显示节点之间的连线。默认值: true
		selectedMulti: false,//设置是否允许同时选中多个节点。默认值: true
		open:true
	},
	async:{
		enable:false,//设置 zTree 是否开启异步加载模式 默认false
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: ""
		}
	},
	callback: {
		onClick:zTreeOnClick
	}
};

var str ='${jsonTree}';
var zNodes = eval('('+ str +')');
$(document).ready(function(){
	var t = $("#tree");
	t = $.fn.zTree.init(t, setting, zNodes);
	demoIframe = $("#testIframe");
	demoIframe.bind("load", loadReady);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.expandAll(true);
});

function loadReady() {
	var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
	htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
	maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
	h = demoIframe.height() >= maxH ? minH:maxH ;
	if (h < 530) h = 530;
	demoIframe.height(h);
}

//树单击事件,添加人员或部门
function zTreeOnClick(event, treeId, treeNode) {
	var currentGroupId = $("#currentGroupId").val();//当前分组id
	if(currentGroupId==''){
		$.dialog.alert('请先选择分组!');
		return;
	}
	var postData = {
		"groupId":currentGroupId,
		"styleId" : '${styleId }',
		"userId":treeNode.id,
		"userType":treeNode.type,
		"cardName":treeNode.name
	};
	$.ajax({
		type: "post",
	    url: "/busiGroupDetail/save.action",
	    dataType: "html",
	    data : postData,
	    async:false,
	    success: function (data) {
	    	$("#table_data").html(data);
	    },
	    error: function (msg) {
	    	$.dialog.alert(msg);
	    }
	});
    
};

//删除分组
function delGroup(groupId){
	$.dialog.confirm('你确定要删除吗？', function(){
		var url="/busiGroup/delete.action";
		$.ajax({
		    type: "get",
		    url: url,
		    data: {"id":groupId},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg,function(){
			    		window.location.reload();
		    		});
		    	}else{
		    		$.dialog.alert(data.msg);
		    	}
		    },
		    error: function (msg) {
		    	$.dialog.alert(msg);
		    }
		});
	});
}

//删除用户
function delUser(userId){
	$.dialog.confirm('你确定要删除吗？', function(){
		$.ajax({
		    type: "post",
		    url: "/busiGroupDetail/delete.action",
		    data: {"id":userId},
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg,function(){
		    			showDetail($("#currentGroupId").val());
		    		});
		    	}else{
		    		$.dialog.alert(data.msg);
		    	}
		    },
		    error: function (msg) {
		    	$.dialog.alert(msg);
		    }
		});
	});
}

//添加
function addGroup(){
	var styleId = '${styleId }';
	$.dialog({content:'url:/busiGroup/addGroup.action?styleId='+styleId,
		id: 'addGroup',
		width: 400,height: 160,
		title:'添加分组'
	});
}

//添加回调
function addCallback(){
	$.dialog({id:'addGroup'}).close();
	window.location.reload();
}

//查看分组明细,切换中间明细
function showDetail(groupId){
	switchClass(groupId);
	$("#currentGroupId").val(groupId);
	var url="/busiGroupDetail/viewDetail.action";
	var data = {
			"groupId":groupId
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

</script>
</body>
</html>

