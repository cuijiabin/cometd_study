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
			<td>留言ID</td>
			<td>客户名称</td>
            <td>IP地址</td>
            <td>咨询页面</td>
            <td>访问来源</td>
            <td>创建时间</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="mr" items="${pageBean.objList}">
			<tr>
				<td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
	            <td>${mr.id}</td>
	            <td>${mr.customerName}</td>
	            <td>${mr.ipInfo}</td>
	            <td>${mr.consultPage}</td>
	            <td>${mr.keywords}</td>
	            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${mr.createDate}"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>