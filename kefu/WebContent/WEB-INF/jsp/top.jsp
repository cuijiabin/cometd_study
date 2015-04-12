<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/jsplugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>

<div class="g-head fixed">
	<a class="logo f-fl" href="#"><img src="/img/logo.png" height="42" alt="" /></a>
    <ul class="u-admin f-fr">
        <li><a href="#"><i class="icon-user"></i>欢迎 Dobao</a></li>
        <li><a href="#"><i class="icon-home"></i>首页</a></li>
        <li><a href="#"><i class="icon-lock"></i>锁屏</a></li>
        <li><a href="#"><i class="icon-logout"></i>退出</a></li>
        <li><a target="_blank" href="/dialogue/user.action"><i class="icon-logout"></i>对话</a></li>
    </ul>
</div>

<div class="m-nav">
    <ul>
        <c:forEach items="${topList}" var="top" varStatus="i">
        <li id="_M${i.index+1}" class="nLi on">
            <h3><a href="javascript:_M(${i.index+1},'${top.url}')">${top.name}</a></h3>
        </li>

       </c:forEach>
    </ul>
</div>
