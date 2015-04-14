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
            <td>角色名称</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="role" items="${pageBean.objList}">
        <tr>
            <td>${role.name}</td>
            <td><a href="">查看<a/>&nbsp;&nbsp;<a href="javascript:updateRole(${role.id})">编辑<a/>&nbsp;&nbsp;
            <a href="javascript:if(confirm('确实要删除吗?'))location='/role/delete.action?id=${role.id}'">删除<a/>&nbsp;&nbsp;<a href="/function/permit.action?id=${role.id}">权限配置<a/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>
