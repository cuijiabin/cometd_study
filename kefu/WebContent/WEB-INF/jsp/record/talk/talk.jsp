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
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">记录中心</a></li>
        <li><i>&gt;</i><a href="#">聊天记录</a></li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	<div class="m-query-bd">
	    <div class="f-mbm">
            <label>部门：</label>
            	<select class="c-wd80">
	                <option selected="selected">全部部门</option>
	                <option value="客服部">客服部</option>
	                <option value="留学部">留学部</option>
	                <option value="随时学">随时学</option>
	                <option value="好顾问">好顾问</option>
            	</select>
            <label>对话时间：</label><input class="c-wd80 Wdate" type="text" id="beginDate" onClick="WdatePicker()" /> - 
            				<input class="c-wd80 Wdate" type="text" id="endDate" onClick="WdatePicker()" />
            <label>工号：</label>
            	<select class="c-wd80">
	                <option selected="selected">全部工号</option>
	                <option value="李老师">李老师</option>
	                <option value="王老师">王老师</option>
	                <option value="张老师">张老师</option>
            	</select>
            <div class="u-subsec">
           		<label><input type="checkbox">仅显示客户有说话记录</label>
       		</div>
        </div>
        <div class="f-mbm">
            <label>客户编号：</label><input class="c-wd150" type="text" />
            <label>IP地址：</label><input class="c-wd150" type="text" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>聊天内容：</label><input class="c-wd150" type="text" />
        </div>
        <div class="f-mbm">
            <label>访问来源：</label><input class="c-wd150" type="text" />
            <label>交谈条数：</label>
            <div class="u-subsec">
                <label><input type="radio" name="1" />小于</label>
                <label><input type="radio" name="1" />等于</label>
                <label><input type="radio" name="1" />大于</label>
            </div>
            <div class="input-append">
              	<input class="c-wd30" type="text">
              	<button class="btn btn-small" type="button">条</button>
            </div>
            <label>站点来源：</label><input class="c-wd150" type="text" />
        </div>
        <div class="f-mbm">
            <label>开始方式：</label><select class="c-wd80">
                <option selected="selected">全部</option>
                <option value="图标">图标</option>
                <option value="邀请框">邀请框</option>
            </select>
            <label>对话结束方式：</label><select class="c-wd80">
                <option selected="selected">全部</option>
                <option value="图标">客服结束对话</option>
                <option value="邀请框">访客结束对话</option>
                <option value="邀请框">关闭网页</option>
            </select>
            <label>是否进入等待队列：</label><select class="c-wd80">
                <option selected="selected">全部</option>
                <option value="图标">是</option>
                <option value="邀请框">否</option>
            </select>
		</div> 
		<div class="f-mbm">
            <label>考试项目：</label><input class="c-wd150" type="text" />
            <label>设备：</label><select class="c-wd80">
                <option selected="selected">全部</option>
                <option value="图标">PC</option>
                <option value="邀请框">移动</option>
            </select>
            <div class="u-subsec">
           		<button class="btn btn-primary" type="button" onclick="javascript:find(1);"> 查 询  </button>
        	</div>
		</div> 
	    <div class="u-hr"></div>
	    <div class="m-query-bd">
	    	<label>对话日期：</label><input class="c-wd120 Wdate" type="text" id="expDate" onClick="WdatePicker()" />
	        <button type="button" class="btn btn-primary btn-small" onclick="expExcel();">下载</button>
	    </div>
	    <div class="u-hr"></div>
	    <div class="u-subsec">
        	<label><input type="checkbox" id="isShowTel">显式查看聊天记录中的电话号码</label>
	        <button type="button" class="btn btn-primary btn-small" onclick="del();">删除</button>
	        <button type="button" class="btn btn-primary btn-small" onclick="toRecycle();">回收站</button>
	        <button type="button" class="btn btn-primary btn-small">配置显示字段</button>
     	</div>
    </div>
</div>

<div id="table_data">
	<jsp:include page="talkList.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">

function find(currentPage){
	var url="/recordsCenter/find.action";
	var data = {
			"currentPage":currentPage,
			"typeId":1,
			"beginDate":$("#beginDate").val(),
			"endDate":$("#endDate").val()
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
		width: 400,height: 500,
		button: [
			        {
			            name: '确认',
			            callback: function () {
			                 save(); 
			                return false;
			            },
			            focus: true
			        },
			        {
			            name: '关闭'
			        }
			    ]
		});
}

function save(){
	alert('talk.Save');
	var email = $("#email").val();
	alert(email);
	return;
	var data = {
		"loginName" : $("#loginName").val(),
		"userName" : $("#userName").val(),
		"password" : $("#password").val(),
		"password1" : $("#password1").val(),
		"listenLevel" : $("#listenLevel option:selected").val(),
		"deptId" : $("#deptId option:selected").val(),
		"roleId" : $("#roleId option:selected").val(),
		"maxListen" : $("#maxListen").val(),
		"cardName" : $("#cardName").val(),
		"createDate" : $("#createDate").val(),		    
	};
}
</script>
</body>
</html>

