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
<h3>	留言详情	</h3>
<table class="table table-bordered m-table">
    <tbody>
        <tr>
            <td class="f-txtr tdbg">回复方式:</td>
            <td class="f-txtr tdbg">网站</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">回复对象:</td>
            <td class="f-txtr tdbg">公司</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">姓名:</td>
            <td class="f-txtr tdbg">${msg.name }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">Email:</td>
            <td class="f-txtr tdbg">${msg.email }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">电话:</td>
            <td class="f-txtr tdbg">${msg.phone }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">QQ:</td>
            <td class="f-txtr tdbg">${msg.qq }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">MSN:</td>
            <td class="f-txtr tdbg">${msg.msn }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">单位:</td>
            <td class="f-txtr tdbg">${msg.company }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">留言内容:</td>
            <td class="f-txtr tdbg">${msg.messageContent }</td>
        </tr>
    </tbody>
</table>
</body>
</html>
