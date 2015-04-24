<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>访问端广告设置--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li>首页</li>
        <li><i>&gt;</i>系统设置</li>
        <li><i>&gt;</i>风格管理</li>
        <li><i>&gt;</i>界面样式设置</li>
        <li><i>&gt;</i>对话邀请框设置</li>
        <li><i>&gt;</i>样式设置</li>
    </ul>
</div>

<div class="g-cnt">
    <div class="m-query f-mar10">
        <div class="m-query-hd">
            邀请框效果预览图
            <button type="button" class="btn btn-primary btn-small">刷新预览</button>
        </div>
        <div class="u-hr"></div>
        <div class="m-query-bd"><img src="http://oc2.xiaoma.com/img/upload/53lx/zdyivt/zdyivt_53kf_1420342163.png" alt="" /></div>
    </div>
    
    <!-- 查询条件 -->
    <div class="f-padd10">
    	<!-- 表格有边框 -->
        <table class="table m-table c-wdat f-mar0">
            <tbody>
                <tr>
                    <td class="c-wd150 f-vat">
                    	<ul class="e_tit c-bor">
                    		<c:forEach var="ele" items="${elementList }">
								<li class="on">
	                            	<a href="#" onclick="editDetail(${ele.id},${ele.sortId })">
	                            		<b class="c-colf00">（${ele.sortId }）</b> 
	                            		<span id="listName${ele.id }">${ele.name }</span>
	                            	</a>
                            		<span onclick="delDetail(${ele.id})" class="u-close f-fr"> &times;</span>
                            	</li>
							</c:forEach>
                        </ul>
                        <button type="button" class="btn btn-primary btn-small f-mtm" onclick="addEle()">添加新元素</button>
                    </td>
                    <td>
                    	<div id="table_data">
							<jsp:include page="elementDetailYD.jsp"></jsp:include>
						</div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

//编辑元素
function editDetail(id,sortId){
	var url = '/inviteElement/editDetailYD.action';
	$.ajax({
	    type: "get",
	    url: url,
	    data: {"id":id,"sortId":sortId},
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

//新增元素
function addEle(){
	var inviteId = '${inviteIcon.id}';
	$.dialog({content:'url:/inviteElement/editName.action?inviteId='+inviteId,
		id: 'addName',
		width: 400,height: 80,
		title:'添加元素'
	});
}

//新增元素回调
function addCallback(id,newName){
	$.dialog({id:'addName'}).close();
	var inviteId = '${inviteIcon.id}';
	var url = '/inviteElement/editYD.action?inviteId='+inviteId+'&id='+id;
	window.location = url;
}

function delDetail(id){
	$.dialog.confirm('你确定要删除吗？', function(){
		var url="/inviteElement/delete.action";
		$.ajax({
		    type: "get",
		    url: url,
		    data: {"id":id},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg,function(){
		    			var url = '/inviteElement/editYD.action?inviteId='+'${inviteIcon.id}';
			    		window.location = url;
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
</script>
</body>
</html>


