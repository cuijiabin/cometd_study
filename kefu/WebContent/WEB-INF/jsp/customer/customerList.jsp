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
<jsp:include page="../page.jsp"></jsp:include>