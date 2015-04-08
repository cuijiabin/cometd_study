<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
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
        <li><i>&gt;</i>客户信息</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
            <label>客户姓名：</label><input id="customerName" name="customerName" value="${customerName}" class="c-wd150" type="text" />
            <label>客户编号：</label><input id="customerId" name="customerId" value="${customerId} " class="c-wd150" type="text" />
            <label>联系方式：</label><input id="customerPhone" name="customerPhone" value="${phone }" class="c-wd150" type="text" />
            <label>风格：</label><input id="customerStyle" name="customerStyle" class="c-wd150" type="text" />
        </div>
        <div class="f-mbm">
           <label>添加时间：</label><input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" /> - <input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>网站关键词：</label><input class="c-wd150" type="text" />
             <label></label>
              <label></label>
               <label></label>
            <button type="button" class="btn btn-primary btn-small" onclick="page()">查询</button>
            <label></label>
            <button type="button" class="btn btn-primary btn-small" >导出</button>
        </div>
        <div class="m-query-hd">
    </div>
      
    </div>

 
</div>



<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
         
            <td>风格</td>
            <td>客户编号</td>
            <td>客户姓名</td>
            <td>联系方式</td>
             <td>咨询页面</td>
             <td>关键词</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="customer" items="${pageBean.objList}">
        <tr>
            <td>风格</td>
            <td>${customer.id}</td>
            <td>${customer.customerName}</td>
            <td>${customer.phone}</td>
            <td>咨询页面</td>
            <td>${customer.createDate}</td>

        </tr>
        </c:forEach>
    </tbody>
</table>





<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">


/*
 * 条件查询
 */
function page(currentPage, pageRecorders) {
		pageRecorders = (pageRecorders == null) ? "${pageBean.pageRecorders}"
				: pageRecorders;
		currentPage = (currentPage == null) ? "${pageBean.currentPage}"
				: currentPage;

		window.location.href = '/customer/find.action?currentPage=' + currentPage
				+ '&pageRecorders=' + pageRecorders + '&phone='
				+ $("#customerPhone").val() + '&customerName='
				+ $("#customerName").val() + '&customerId=' 
				+$("#customerId").val();
	}
</script>
</body>
</html>
