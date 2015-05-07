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
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li>记录中心</li>
        <li><i>&gt;</i>聊天记录</li>
        <li><i>&gt;</i>回收站</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="g-cnt">
<div class="m-query f-mar10">
	<div class="m-query-bd">
	    <div class="f-mbm">
            <label>部门：</label>
            	<select class="c-wd80" name="deptId" id="deptId" onchange="refreshUserList(this)">
            		<c:forEach var="dept" items="${deptList}">
						<option value="${dept.id }">${dept.name }</option>
					</c:forEach>
            	</select>
            <label>对话时间：</label><input class="c-wd80 Wdate" type="text" id="beginDate" value="${beginDate }" onClick="WdatePicker()" /> - 
            	<input class="c-wd80 Wdate" type="text" id="endDate" value="${endDate }" onClick="WdatePicker()" />
            <label>工号：</label>
            	<select class="c-wd80" name="userId" id="userId">
            	    <c:forEach var="user" items="${userList}">
						<option value="${user.id }">${user.cardName }</option>
					</c:forEach>
            	</select>
            <div class="u-subsec">
           		<label><input type="checkbox" id="isTalk" name="isTalk">仅显示客户有说话记录</label>
       		</div>
       		<div class="u-subsec">
           		<button class="btn btn-primary" type="button" onclick="javascript:find(1);"> 查 询  </button>
        	</div>
        </div>
	    <div class="u-hr"></div>
	    <div class="u-subsec">
	        <button type="button" class="btn btn-primary btn-small" onclick="restore();">还原</button>
	        <button type="button" class="btn btn-primary btn-small" onclick="delTrue();">彻底删除</button>
	        <button type="button" class="btn btn-primary btn-small" onclick="editDisplay();">配置显示字段</button>
     	</div>
    </div>
</div>

<div id="table_data">
	<jsp:include page="talkRecycleList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
//自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

function find(currentPage){
	var isTalk = 0 ;
	if(($("#isTalk").is(":checked"))){
		isTalk = 1;
	};
	var url="/recordsCenter/queryTalkDel.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"typeId":1,
			"deptId":$("#deptId").val(),
			"beginDate":$("#beginDate").val(),
			"endDate":$("#endDate").val(),
			"userId":$("#userId").val(),
			"isTalk":isTalk
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

//全选/全不选
function checkedAll(){
	if(($("#titleCheckbox").is(":checked"))){
		$("#table_data :checkbox").prop("checked", true);  
	}else{
		$("#table_data :checkbox").prop("checked", false);
	}
}

//还原
function restore(){
	var ids = "";
	$("input[type=checkbox][name=detailCheckbox]:checked").each(function(){ 
    	if(ids!=""){
    		ids+=",";
    	}
   		ids+=$(this).val();
    });
	if(ids==""){
		$.dialog.alert("请先选择数据!");
		return;
	}
	$.dialog.confirm('你确定要还原吗？', function(){
		var url="/recordsCenter/restoreTalk.action";
		$.ajax({
		    type: "GET",
		    url: url,
		    data: {"ids":ids},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg);
		    		find(1);
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

//彻底删除
function delTrue(){
	var ids = "";
	$("input[type=checkbox][name=detailCheckbox]:checked").each(function(){ 
    	if(ids!=""){
    		ids+=",";
    	}
   		ids+=$(this).val();
    });
	if(ids==""){
		$.dialog.alert("请先选择数据!");
		return;
	}
	$.dialog.confirm('你确定要彻底删除吗？', function(){
		var url="/recordsCenter/deleteTalkTrue.action";
		$.ajax({
		    type: "GET",
		    url: url,
		    data: {"ids":ids},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		$.dialog.alert(data.msg);
		    		find(1);
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

//查看明细
function showDetail(dialogueId){
	var isShowTel = 0 ;
	if(($("#isShowTel").is(":checked"))){
		isShowTel = 1;
	};
	var url="/recordsCenter/queryTalkList.action";
	url+="?dialogueId="+dialogueId+"&isShowTel="+isShowTel+"&isDel=1";
	window.location.href = url;
	return;
}

//创建客户名称
function updateCusl(customerId,dialogueId){
	
	$.dialog({content:'url:/customer/editCus.action?customerId='+customerId+'&dialogueId='+dialogueId,
		id: 'editCus',
		width: 400,height: 500,
		title:'添加访客信息'
	});
}

//配置显示字段
function editDisplay(){
	$.dialog({content:'url:/chatRecordField/edit.action',
		id: 'editDisplay',
		width: 800,height: 600,
		lock:true, 
		title:'配置显示字段'
	});
}

//创建客户回调
function editCallback(){
	$.dialog({id:'editCus'}).close();
	var pageNo = '${pageBean.currentPage}';
	find(pageNo);
}

//人员列表联动
function refreshUserList(obj){
    var deptId = obj.value;  
    var conKind = document.getElementById('userId');
    $.ajax({
	    type: "GET",
	    url: '/recordsCenter/getUserByDeptId.action',
	    data: {"deptId":deptId},
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function (data) {
	    	//清空原来的列表
	    	for (m = conKind.options.length - 1; m > 0; m--){
				conKind.options[m] = null;
	    	}
	    	
	    	//设置新的列表
	    	var list = data.msg;
	    	if (list.length>0) {
				for (i = 0; i < list.length; i++) {
					conKind.options[i] = new Option(list[i].cardName, list[i].id);
				}
			}else{
				conKind.options[0] = new Option('', 0);
			}
	    },
	    error: function (msg) {
	    	alert(msg);
	    }
	});
}
</script>
</body>
</html>

