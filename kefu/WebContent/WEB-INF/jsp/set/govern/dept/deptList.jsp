<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td>部门名称</td>
            <td>在职人数</td>
            <td>排序</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="dept" items="${pageBean.objList}">
        <tr>
            <td>${dept.name}</td>
            <td>${dept.userCount}</td>
            <td>${dept.sortNum}</td>
            <td><a href="javascript:findUser(${dept.id})">查看<a/>&nbsp;&nbsp;<a href="javascript:updateDept(${dept.id})">编辑<a/>&nbsp;&nbsp;<a href="javascript:deleteDept(${dept.id})">删除<a/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../../../page.jsp"></jsp:include>
