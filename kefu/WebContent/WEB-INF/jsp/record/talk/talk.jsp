<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<%@ taglib uri="/WEB-INF/xiaoma.tld" prefix="xiaoma" %>
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
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li>记录中心</li>
        <li><i>&gt;</i>聊天记录</li>
    </ul>
</div>
<!-- 查询条件 -->
<div style="margin:50px">
<div class="m-query f-mar10">
	<div class="m-query-bd">
	    <div class="f-mbm">
            <label>部门：</label>
            	<select class="c-wd80" name="deptId" id="deptId" onchange="refreshUserList(this)">
            		<c:forEach var="dept" items="${deptList}">
						<option value="${dept.id }">${dept.name }</option>
					</c:forEach>
<!-- 	                <option selected="selected" value="">全部部门</option> -->
<!-- 	                <option value="1">客服部</option> -->
<!-- 	                <option value="2">留学部</option> -->
<!-- 	                <option value="3">随时学</option> -->
<!-- 	                <option value="4">好顾问</option> -->
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
        </div>
        <div class="f-mbm">
            <label>客户编号：</label><input class="c-wd150" type="text" id="customerId" name="customerId" />
            <label>IP地址：</label><input class="c-wd150" type="text"  id="ipInfo" name="ipInfo" />
            <label>咨询页面：</label><input class="c-wd150" type="text" id="consultPage" name="consultPage" />
            <label>聊天内容：</label><input class="c-wd150" type="text" id="talkContent" name="talkContent" />
        </div>
        <div class="f-mbm">
            <label>访问来源：</label><input class="c-wd150" type="text" id="keywords" name="keywords" />
            <label>交谈条数：</label>
            <div class="u-subsec">
                <label><input type="radio" name="numCondition" value='DY' />大于</label>
                <label><input type="radio" name="numCondition" value='EQ' />等于</label>
                <label><input type="radio" name="numCondition" value='XY' />小于</label>
            </div>
            <div class="input-append">
              	<input class="c-wd30" type="text" id="totalNum" name="totalNum" maxlength="4">
              	<button class="btn btn-small" type="button">条</button>
            </div>
            <label>站点来源：</label><input class="c-wd150" type="text" id="styleName" name="styleName" />
        </div>
        <div class="f-mbm">
            <label>开始方式：</label>
            <xiaoma:select name="openType" dictName="d_open_type" all="0" allName="全部" />
            <label>对话结束方式：</label>
            <xiaoma:select name="closeType" dictName="d_close_type" all="0" allName="全部" />
            <label>是否进入等待队列：</label>
            <select class="c-wd80" name="isWait" id="isWait">
                <option selected="selected" value="">全部</option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
		</div> 
		<div class="f-mbm">
            <label>考试项目：</label><input class="c-wd150" type="text" id="waitListName" name="waitListName" />
            <label>设备：</label>
            <xiaoma:select name="deviceType" dictName="d_device_type" all="0" allName="全部" />
            <div class="u-subsec">
           		<button class="btn btn-primary" type="button" onclick="javascript:find(1);"> 查 询  </button>
        	</div>
		</div> 
	    <div class="u-hr"></div>
	    <div class="m-query-bd">
	     <%HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();%>
	    	<label>对话日期：</label><input class="c-wd120 Wdate" type="text" id="expDate" onClick="WdatePicker({minDate:'2015-04-01',maxDate:'%y-%M-{%d-1}}'})" />
	    	<% if(CheckCodeUtil.isCheckFunc(userId,"f_down_load")) {%>
	        <button type="button" class="btn btn-primary btn-small" onclick="expExcel();">下载</button>
	        <%} %>
	    </div>
	    <div class="u-hr"></div>
	    <div class="u-subsec">
	       <% if(CheckCodeUtil.isCheckFunc(userId,"f_oncheck_log")) {%>
        	<label><input type="checkbox" id="isShowTel">显式查看聊天记录中的电话号码</label>
        	 <%} %>
        	<% if(CheckCodeUtil.isCheckFunc(userId,"f_fieldon_del")) {%>
	        <button type="button" class="btn btn-primary btn-small" onclick="del();">删除</button>
	         <%} %>
	        <% if(CheckCodeUtil.isCheckFunc(userId,"f_recycel_up")) {%>
	        <button type="button" class="btn btn-primary btn-small" onclick="toRecycle();">回收站</button>
	         <%} %>
	        <% if(CheckCodeUtil.isCheckFunc(userId,"f_set_fieldon")) {%>
	        <button type="button" class="btn btn-primary btn-small" onclick="editDisplay();">配置显示字段</button>
	         <%} %>
     	</div>
    </div>
</div>

<div id="table_data">
	<jsp:include page="talkList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">

function find(currentPage){
	
	var totalNum = $("#totalNum").val();
	if(!isInteger(totalNum)){
		$.dialog.alert("交谈条数请填写正整数!");
		return;
	}
	var isTalk = 0 ;
	if(($("#isTalk").is(":checked"))){
		isTalk = 1;
	};
	var url="/recordsCenter/find.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"typeId":1,
			"deptId":$("#deptId").val(),
			"beginDate":$("#beginDate").val(),
			"endDate":$("#endDate").val(),
			"userId":$("#userId").val(),
			"isTalk":isTalk,
			"customerId":$("#customerId").val(),
			"ipInfo":$("#ipInfo").val(),
			"consultPage":$("#consultPage").val(),
			"talkContent":$("#talkContent").val(),
			"keywords":$("#keywords").val(),
			"numCondition":$("input[name='numCondition']:checked").val(),
			"totalNum":totalNum,
			"styleName":$("#styleName").val(),
			"openType":$("#openType").val(),
			"closeType":$("#closeType").val(),
			"isWait":$("#isWait").val(),
			"waitListName":$("#waitListName").val(),
			"deviceType":$("#deviceType").val()
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

//回收站
function toRecycle(){
	var url="/recordsCenter/queryTalkDel.action";
	window.location.href = url;
	return;
}

//全选/全不选
function checkedAll(){
	if(($("#titleCheckbox").is(":checked"))){
		$("#table_data :checkbox").attr("checked", true);  
	}else{
		$("#table_data :checkbox").attr("checked", false);
	}
}

//下载
function expExcel(){
	var expDate = $("#expDate").val()
	if(expDate==null || expDate==""){
		$.dialog.alert("请先选择日期!");
		return;
	}
	var url = "/recordsCenter/expTalk.action?date="+expDate;
	window.open(url);  
}

//删除
function del(){
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
	$.dialog.confirm('你确定要删除吗？', function(){
		var url="/recordsCenter/deleteTalk4Logic.action";
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
	url+="?dialogueId="+dialogueId+"&isShowTel="+isShowTel;
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
// 	var url="/chatRecordField/edit.action";
// // 	url+="?dialogueId="+dialogueId+"&isShowTel="+isShowTel;
// 	window.location.href = url;
// 	return;
	
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

