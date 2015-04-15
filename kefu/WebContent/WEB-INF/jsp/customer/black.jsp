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
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">访客管理</a></li>
        <li><i>&gt;</i>黑名单</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
            <label>客户编号：</label><input id="customerId" name="customerId" value="${customerId }" class="c-wd150" type="text" />
            <label>添加工号：</label><input  id="userName" name="userName" class="c-wd150" type="text" />
            <label>阻止原因：</label><input id="description" name="description" value="${description}" class="c-wd150" type="text" />
               <button type="button" class="btn btn-primary btn-small" onclick="javascript:find(1);">查询</button>
        </div>
        <div class="f-mbm">
         
        <button type="button" class="btn btn-primary btn-small" onclick="javascript:addBlacklist();">添加黑名单</button>
            <label></label>
            <button type="button" class="btn btn-primary btn-small" onclick="delTrue();">删除</button>
        </div>
        <div class="m-query-hd">
    </div>
      
    </div>
</div>

<div id="table_data">
	<jsp:include page="blackList.jsp"></jsp:include>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
/*
 * 条件查询
 */
function find(currentPage){
	var url="/blacklist/find.action";
	var data = {
			"currentPage":currentPage,
			"map[customerId]":$("#customerId").val(),
			"map[description]":$("#description").val(),
			"map[userName]":$("#userName").val(),
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
     function addBlacklist(){
    	var d = $.dialog({id:'blacklist',content:'url:/blacklist/new.action',lock:true, width:	500,height: 300,});
    }
    /**
    *新增的方法
    */
    function save(){
	var url = "/blacklist/save.action";
	var data = {
		"customerId" : $("#customerId").val(),
		"ip" : $("#ip").val(),
		"description" : $("#description").val(),
		"enddate" : $("#endDate").val()
	};
	$.ajax({
		type : "post",
		url : url,
		data : data,
		dataType : "json",
		async:false,
		success : function(data) {
			if (data.result == 0) {
				alert(data.msg);
				$("#customerId").val('');
				$("#ip").val('');
				$("#endDate").val('');
				$("#description").val('');
				location.reload();
			} else {
				alert(data.msg);
			}
		},
		error : function(msg) {
			alert(data.msg);
		}
	});
  }
    /**
    *修改前的页面跳转
    */
    function toUpdate(blacklistId){
    	
    		$.dialog({content:'url:/blacklist/editBlack.action?blacklistId='+blacklistId,
    			width: 400,height: 500,
    			button: [
    				        {
    				            name: '确认',
    				            callback: function () {
    				                 update(); 
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


	
	/*
	* 彻底删除
	*/
	function delTrue(){
		var ids = "";
		$("input[type=checkbox][name='ck']:checked").each(function(){ 
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
			var url="/blacklist/deleteBlackTrue.action";
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
</script>
</body>
</html>
