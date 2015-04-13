<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
         
            <td>多选</td>
            <td>工号</td>
            <td>姓名</td>
            <td>身份</td>
            <td>状态</td>
            <td>部门</td>
            <td>接听数</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${pageBean.objList}">
        <tr>
            <td><input type="checkbox" id="id" name="id" value="${user.id}"/></td>
            <td>${user.loginName}</td>
            <td>${user.userName}</td>
            <td>${user.cardName}</td>
            <td>${user.onLineStatus}</td>
            <td>${user.deptId}</td>
            <td>${user.maxListen}</td>
            <td><a href="javascript:findUser(${user.id})">查看<a/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:updateUser(${user.id})">编辑<a/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>
