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
        <li><i>&gt;</i><a href="#">客户管理中心</a></li>
        <li><i>&gt;</i>客户管理</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	<div class="m-query-hd">
        <label>部门：</label><select class="c-wd80">
            <option selected="selected">全部部门</option>
            <option value="客服部">客服部</option>
            <option value="留学部">留学部</option>
            <option value="随时学">随时学</option>
            <option value="好顾问">好顾问</option>
        </select>
        <label>专题：</label><input class="c-wd80" type="text" />
        <label>关键词：</label><input class="c-wd80" type="text" />
        <label>对话时间：</label><input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" /> - <input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" />
        <div class="u-subsec">
           	<label><input type="checkbox">显示子栏目</label>
        </div>
    </div>
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
            <label>客户编号：</label><input class="c-wd150" type="text" />
            <label>IP地址：</label><input class="c-wd150" type="text" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>聊天内容：</label><input class="c-wd150" type="text" /><span class="help-inline c-clred">协议类型不能为空</span>
        </div>
        <div class="f-mbm">
            <label>客户编号：</label><input class="c-wd150" type="text" />
            <label>IP地址：</label><input class="c-wd150" type="text" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>聊天内容：</label><input class="c-wd150" type="text" /><span class="help-inline c-clred">协议类型不能为空</span>
        </div>
        <div class="f-mbm">
            <label>部门：</label><select class="c-wd80">
                <option selected="selected">全部部门</option>
                <option value="客服部">客服部</option>
                <option value="留学部">留学部</option>
                <option value="随时学">随时学</option>
                <option value="好顾问">好顾问</option>
            </select>
            <label>专题：</label><input class="c-wd150" type="text" /><span class="help-inline c-clred">协议类型不能为空</span>
            <label>关键词：</label><input class="c-wd150" type="text" />
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
        </div>
    </div>
    <div class="u-hr"></div>
    <div class="m-query-bd">
    	<label>对话日期：</label><input class="c-wd120 Wdate" type="text" onClick="WdatePicker()" />
        <button type="button" class="btn btn-primary btn-small">下载</button>
    </div>
</div>



<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
         
            <td>风格</td>
            <td>客户编码</td>
            <td>客户姓名</td>
            <td>联系方式</td>
             <td>咨询页面</td>
             <td>关键词</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="customer" items="${pageBean.objList}">
        <tr>
         
<%--             <td>${customer.id}</td> --%>
<%--             <td>${customer.id}</td> --%>
<%--             <td>${customer.id}</td> --%>
<%--            <td>${customer.id}</td> --%>
<%--            <td>${customer.id}</td> --%>
<%--            <td>${customer.id}</td> --%>
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
