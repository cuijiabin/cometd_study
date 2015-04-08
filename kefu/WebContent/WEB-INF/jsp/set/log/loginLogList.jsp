<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<!-- 表格有边框 -->
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td>工号</td>
            <td>姓名</td>
            <td>登录IP</td>
            <td>登录时间</td>
            <td>退出时间</td>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="log" items="${pageBean.objList}"> 
        <tr>
            <td>${log.loginName}</td>
            <td>${log.cardName}</td>
            <td>${log.ip}</td>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${log.createDate}"/></td>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${log.endDate}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>