<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript"
	src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
	<thead>
		<tr>
			<td style="width: 30px; text-align: center;"><input type="checkbox" id="inlineCheckbox" value="全选"> </td>
			<c:forEach var="title" items="${title }">
				<td>${title}</td>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="content" items="${pageBean.objList}">
			<tr>
				<td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
				<c:forEach var="log" items="${content }">
					<td>${log}</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>