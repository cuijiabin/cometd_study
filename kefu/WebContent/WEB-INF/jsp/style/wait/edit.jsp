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

<div class="g-bd6 f-cb f-mar20">
    <div class="g-sd6 c-bor">
        <h3 class="u-tit c-bg">等待列表配置</h3>
        <div style="float:right;margin-right: 30px;height: 24px">
       		<button type="button" class="btn btn-primary btn-small" onclick="add();">添加</button>
   		</div>
        <!-- 表格有边框 -->
		<table class="table table-bordered table-striped table-hover m-table">
			<thead>
				<tr>
					<td>一级菜单</td>
		            <td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${pList}">
					<tr>
			            <td id="pname${p.id }">${p.name}</td>
			            <td>
			            	<a href="#" onClick="show(${p.id})">查看</a>
			            	<a href="#" onClick="toUpdate(${p.id})">修改</a>
			            	<a href="#" onClick="del(${p.id})">删除</a>
			            </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    </div>
    
    <div class="g-mn6">
        <div class="g-mn6c">
        	<div id="table_data">
				<jsp:include page="editTwo.jsp"></jsp:include>
			</div>
        </div>
    </div>
    
</div>

<div style="float:right;margin-right: 30px;height: 24px">
    <button type="button" class="btn btn-primary btn-small" onclick="cancel();">关闭</button>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">

var api = frameElement.api;//调用父页面数据  
var W = api.opener;//获取父页面对象  
var cDG;

//查看明细,切换右侧明细
function show(id){
	var url="/waitList/editTwo.action";
	var data = {
			"pId":id
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

//添加
function add(){
	cDG = W.$.dialog({
		id:'add',
		content:'url:/waitList/add.action?styleId='+'${styleId}',
		lock:true,
		parent:api,
		width: 400,height: 100,
		title:'添加一级菜单'
	});
}

//添加回调
function addCallback(){
	cDG.close();
	api.reload(window);
}

//删除
function del(id){
	W.$.dialog.confirm('删除会将此菜单下的二级菜单一起删除,你确定要删除吗？', function(){
		var url="/waitList/delete.action";
		$.ajax({
		    type: "post",
		    url: url,
		    data: {"id":id},
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		W.$.dialog.alert('操作成功!',function(){
		    			window.location = '/waitList/edit.action?styleId='+'${styleId}'
		    		});
		    	}else{
		    		W.$.dialog.alert(data.msg);
		    	}
		    },
		    error: function (msg) {
		    	W.$.dialog.alert(msg);
		    }
		});
	});
}

//更新
function toUpdate(id){
	var td = $("#pname"+id); //获取td
	var txt = td.text(); //td内容
	var input = $("<input maxlength='20' type='text' value='" + txt + "'/>"); 
	td.html(input); //td修改为input 
	input.focus();//获取焦点 
	//文本框失去焦点后提交内容，重新变为文本 
	input.blur(function(){ 
		var newtxt = input.val(); 
		//判断文本有没有修改 
		if (newtxt != txt) {
			var postData = {"id":id,"name":newtxt,"styleId":'${styleId}'};
			$.ajax({
			    type: "post",
			    url: "/waitList/validate.action",
			    dataType: "json",
			    data : postData,
			    async:false,
			    success: function (data) {
			    	if(data.result==0){
			    		save(postData,td,txt);
			    		td.html(newtxt);
			    	}else{
			    		W.$.dialog.alert(data.msg,function(){
// 			    			input.focus();
			    			td.html(txt);
			    		});
			    	}
			    },
			    error: function (msg) {
			    	W.$.dialog.alert(data.msg,function(){
//			    		input.focus();
		    			td.html(txt);
		    		});
			    }
			});
		}else{
			td.html(txt);
		}
	});
}

//保存
function save(postData,td,txt){
	$.ajax({
	    type: "post",
	    url: '/waitList/save.action',
	    data: postData,
	    dataType: "json",
	    success: function (data) {
	    	if(data.result==0){
	    	}else{
		    	W.$.dialog.alert(data.msg,function(){
//		    		input.focus();
	    			td.html(txt);
	    		});
	    	}
	    },
	    error: function (msg) {
	    	W.$.dialog.alert(data.msg,function(){
//	    		input.focus();
    			td.html(txt);
    		});
	    }
	});
}

//关闭
function cancel(){
	api.close();
}

</script>
</body>
</html>
