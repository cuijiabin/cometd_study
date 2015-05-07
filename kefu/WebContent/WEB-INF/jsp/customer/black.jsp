<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>  
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
<div class="g-cnt">
<div class="m-query f-mar10">
    <div class="u-hr"></div>
    <div class="m-query-bd">${Session.user.id}
        <div class="f-mbm">
            <label>客户编号：</label><input id="customerId" name="customerId" value="${customerId }" class="c-wd150" type="text" maxlength="15" />
            <label>添加工号：</label><input  id="userName" name="userName" class="c-wd150" type="text" />
            <label>阻止原因：</label><input id="description" name="description" value="${description}" class="c-wd150" type="text" />
               <button type="button" class="btn btn-primary btn-small" onclick="javascript:find(1);">查询</button>
        </div>
        <div class="f-mbm">
        <%HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();%>
            <% if(CheckCodeUtil.isCheckFunc(userId,"f_bald_add")) {%>
           <button type="button" class="btn btn-primary btn-small" onclick="javascript:addBlacklist();">添加黑名单</button>
           <%} %>
            <label></label>
            <% if(CheckCodeUtil.isCheckFunc(userId,"f_bald_del")) {%>
            <button type="button" class="btn btn-primary btn-small" onclick="delTrue();">删除</button>
             <%} %>
        </div>
        <div class="m-query-hd">
    </div>
    </div>
</div>

<div id="table_data">
	<jsp:include page="blackList.jsp"></jsp:include>
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
/*
 * 条件查询
 */
  function find(currentPage){
	var url="/blacklist/find.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"map[customerId]":$("#customerId").val(),
			"map[description]":$("#description").val(),
			"map[userName]":$("#userName").val(),
			"map[typeId]":1
	};
	//校验搜索框中的参数
	if (!checkParam()) {
			return;
		}
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
  
  /**
   * js校验搜索参数
   */
   function checkParam() {
 	  var customerId = $("#customerId").val();
 		var chinesePatrn = /[\u4E00-\u9FA5]/g;
 		if(chinesePatrn.test(customerId)){
 			$.dialog.alert("客户编号不得是汉字！");
 			return false;
 		}
 		//只能为整数
 		 var customer = $("#customerId").val().trim();
 		var   customerIdParam =/^(-|\+)?\d+$/; 
 		if(!customer.replace(/^ +| +$/g,'')==''){
 			
 			if (!customerIdParam.test(customer)) {
 				$.dialog.alert("请输入有效的客户编号");
 				return false;
 			}
 			return true;
 		}
 		
 	
 		return true;
 }
	  /**
	  * 跳转新增前的页面
	  */
     function addBlacklist(){
    	var d = $.dialog({title:"添加黑名单",content:'url:/blacklist/new.action',lock:true, width:	600,height: 300,id:'addBlackList'});
 }

    /**
    *修改前的页面跳转
    */
    function toUpdate(blacklistId){
    	var c= $.dialog({content:'url:/blacklist/editBlack.action?blacklistId='+blacklistId,title:"修改黑名单",
    			width: 600,height: 300,id:'editBlackList'});
 }
  //全选/全不选
    function checkedAll(){
    	if(($("#titleCheckbox").is(":checked"))){
    		$("#table_data :checkbox").prop("checked", true);  
    	}else{
    		$("#table_data :checkbox").prop("checked", false);
    	}
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
	
	//编辑黑名单回调
	function editCallback(){
		$.dialog({id:'editBlackList'}).close();
		$("#customerId").val(''),  //为了清空之前不合法的数据
		$("#userName").val(''),
		$("#description").val('')
		var pageNo = '${pageBean.currentPage}';
		find(pageNo);
	}

	//新增黑名单回调
	function addCallback(){
		$.dialog({id:'addBlackList'}).close();
		$("#customerId").val('');  //为了清空之前不合法的数据
		$("#userName").val('');
		$("#description").val('');
		find(1);
  }
</script>
</body>
</html>
