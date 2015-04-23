<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <c:forEach var="wait" varStatus="status"  items="${waitList}"> 
    	<c:if test="${status.index%4==0 }">
        	<tr>
        </c:if>
            <td>${wait.name}</td>
       	<c:if test="${status.index%4==3 }">
        	</tr>
        </c:if>
    </c:forEach>
</table>