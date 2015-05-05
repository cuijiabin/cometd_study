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
</head>
<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">访客管理</a></li>
        <li><i>&gt;</i>访客信息</li>
    </ul>
</div>
<!-- 查询条件 -->
<div style="margin:50px">
<div class="m-query f-mar10">
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
            <label>客户姓名：</label><input id="customerName" name="customerName"class="c-wd150" type="text" />
            <label>客户编号：</label><input id="customerId" name="customerId" maxlength="15" class="c-wd150" type="text" />
            <label>联系方式：</label><input id="phone" name="phone"  class="c-wd150" type="text" />
            <label>风格：</label><input id="customerStyle" name="customerStyle" class="c-wd150" type="text" />
        </div>
        <div class="f-mbm">
           <label>添加时间：</label><input readonly="readonly" id="beginDate" name="beginDate" class="c-wd80 Wdate" type="text" value="${beginDate }" onClick="WdatePicker()" /> - <input id="endDate" name="endDate" class="c-wd80 Wdate" type="text" readonly="readonly" value="${endDate }" onClick="WdatePicker()" />
            <label>咨询页面：</label><input class="c-wd150" type="text" id="consultPage" name="consultPage"/>
            <label>网站关键词：</label><input class="c-wd150" type="text" id="keywords" name="keywords"/>
             <label></label>
              <label></label>
               <label></label>
            <button type="button" class="btn btn-primary btn-small" onclick="javascript:find(1);">查询</button>
            <label></label>
            <%HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();%>
                 <% if(CheckCodeUtil.isCheckFunc(userId,"f_mess_out")) {%>
                 <button type="button" target="_blank"  class="btn btn-primary btn-small" onclick="javascript:exportExcel();" >导出</button>
                 <%} %>
        </div>
        <div class="m-query-hd">
    </div>
    </div>
</div>
<div id="table_data">
	<jsp:include page="customerList.jsp"></jsp:include>
</div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
/**
 *查询
 */
function find(currentPage){
	var url="/customer/find.action";
	var data = {
			"currentPage":currentPage,
			"pageRecorders" : $("#pageRecorders").val(),
			"beginDate" :$("#beginDate").val(),
			"endDate" :$("#endDate").val(),
			"map[customerName]":$("#customerName").val(),
			"map[id]":$("#customerId").val(),
			"map[phone]":$("#phone").val(),
			"map[styleName]":$("#customerStyle").val(),
			"map[consultPage]":$("#consultPage").val(),
			"map[keywords]":$("#keywords").val(),
			"map[typeId]":1
	};
	//检验日期
	if (!checkdate(data)) {
		return;
	}
	//校验搜索框中的参数
	if (!checkParam()) {
			return;
		}
	
	$.ajax({
	    type: "get",
	    url: url,
	    data: data,
	    contentType: "application/json; charset=utf-8",
	    async:false,
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
  *检验日期
  */
  function checkdate(userData){
	  var beginDate = userData.beginDate;
	  var endDate = userData.endDate;
	  var pageRecorders = userData.pageRecorders;
	  console.log(beginDate);
	  console.log(endDate);
	  console.log(pageRecorders);
	  beginDate = beginDate.replace("-","/").replace("-","/");
	  endDate = endDate.replace("-","/").replace("-","/");
	  if(Date.parse(endDate) < Date.parse(beginDate))   {                           
           $.dialog.alert('结束日期不能小于开始日期',this);
	  	   return false;
	  }else{
		  return true;
	  }
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
		 var customer_trim = $("#customerId").val().trim();
		var customer = customer_trim;
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
   *导出满足查询条件的所有数据
   */
  function exportExcel(){
	  //用来解决没有数据不让导出的问题
	  find(1);
	  var rowNum = $("#customerTable tbody tr").length;
	  console.log("总行数试试"+rowNum);
	  if(rowNum <=0){
		  $.dialog.alert("该范围内没有数据可供导出 ！");
		  return;
	  }
	    //校验搜索框中的参数
	    var customerId = $("#customerId").val();
        var chinesePatrn = /[\u4E00-\u9FA5]/g;
		if(chinesePatrn.test(customerId)){
			$.dialog.alert("客户编号不得是汉字！");
			return false;
	  }
	     //校验日期
	     var beginDate =$("#beginDate").val();
		 var endDate=$("#endDate").val();
		  beginDate = beginDate.replace("-","/").replace("-","/");
		  endDate = endDate.replace("-","/").replace("-","/");
		  if(Date.parse(endDate) < Date.parse(beginDate))   {                           
	           $.dialog.alert('结束日期不能小于开始日期',this);
		  	   return false;
	   }else{
			  console.log(beginDate);
			  console.log(endDate);
	   }
		  
 	 window.open("/customer/exportExcel.action?beginDate="+$("#beginDate").val()+"&endDate="+$("#endDate").val()
 			       +"&customerName="+$("#customerName").val()
 			       +"&id="+$("#customerId").val()
 			       +"&phone="+$("#phone").val()
 			       +"&styleName="+$("#customerStyle").val()
 			       +"&consultPage="+$("#consultPage").val()
 			       +"&keywords="+$("#keywords").val()+" ");
 }
</script>
</body>
</html>
