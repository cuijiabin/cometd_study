<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td>操作人</td>
            <td>操作栏目</td>
            <td>内容</td>
            <td>操作时间</td>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="log" items="${pageBean.objList}"> 
        <tr>
            <td>${log.loginName}</td>
            <td>${log.title}</td>
            <td>${log.content}</td>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${log.createDate}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>