<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript"
	src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>	
<link rel="stylesheet" type="text/css"
	href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
	<thead>
		<tr>
			<td style="width: 30px; text-align: center;"><input type="checkbox" id="titleCheckbox" name="titleCheckbox"  onclick="javascript:checkedAll();" value="全选"> </td>
			<td>客户名称</td>
            <td>IP地址</td>
            <td>咨询页面</td>
            <td>访问来源</td>
            <td>创建时间</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="contentMap" items="${pageBean.objList}">
			<tr>
				<td><input type="checkbox" name="detailCheckbox" value="${contentMap.id}" >
					<input type="hidden" id="id" value="${contentMap.id}">
					<input type="hidden" id="hasName" value="${contentMap.hasName}">
					<input type="hidden" id="customerId" value="${contentMap.customerId}">
				</td>
				<td>
					<c:choose>
						<c:when test="${showDetail == 1 and contentMap.hasName == 1 }"> 
							<a href="#" onClick="showDetail(${contentMap.id})">${contentMap.customerName}</a>
						</c:when>
						<c:when test="${showDetail == 1 and contentMap.hasName != 1 }"> 
							<a href="#" onClick="showDetail(${contentMap.id})">${contentMap.customerName}</a>
								[<a href="#" onClick="updateCusl(${contentMap.customerId},${contentMap.id})">创建</a>]
						</c:when>
						<c:otherwise> 
							${contentMap.customerName}
						</c:otherwise>
					</c:choose>
				</td>
	            <td>${contentMap.ipInfo}</td>
	            <td title="${contentMap.consultPage}">${contentMap.consultPage}</td>
	            <td>${contentMap.keywords}</td>
	            <td>${contentMap.createDate}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>