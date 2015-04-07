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
        <li><i>&gt;</i>黑名单</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
           
            <label>客户编号：</label><input class="c-wd150" type="text" />
            <label>添加工号：</label><input class="c-wd150" type="text" />
            <label>组织原因：</label><input class="c-wd150" type="text" />
               <button type="button" class="btn btn-primary btn-small">查询</button>
        </div>
        <div class="f-mbm">
         
        <button type="button" class="btn btn-primary btn-small">添加黑名单</button>
            <label></label>
            <button type="button" class="btn btn-primary btn-small">删除</button>
        </div>
        <div class="m-query-hd">
    </div>
      
    </div>

 
</div>



<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
         
            
            <td>客户编码</td>
            <td>IP地址</td>
            <td>地理位置</td>
             <td>失效时间</td>
             <td>添加工号</td>
             <td>阻止原因</td>
             <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="blacklist" items="${pageBean.objList}">
        <tr>
         <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
       
            <td>${blacklist.customerId}</td>
            <td>${blacklist.ip}</td>
            <td>${blacklist.ipInfo}</td>
            <td>${blacklist.endDate}</td>
           <td>添加工号</td>
           <td>${blacklist.description}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>





<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
</script>
</body>
</html>
