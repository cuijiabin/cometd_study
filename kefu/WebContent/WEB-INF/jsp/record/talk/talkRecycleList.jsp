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
			<td style="width: 30px; text-align: center;"><input type="checkbox" id="titleCheckbox" name="titleCheckbox"  onclick="javascript:checkedAll();"  value="全选"> </td>
			<c:forEach var="title" items="${title }">
				<td>${title}</td>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="contentMap" items="${pageBean.objList}">
			<tr>
				<td><input type="checkbox" name="detailCheckbox" value="${contentMap.dialogueId}" >
					<input type="hidden" id="dialogueId" value="${contentMap.dialogueId}">
					<input type="hidden" id="hasName" value="${contentMap.hasName}">
					<input type="hidden" id="customerId" value="${contentMap.customerId}">
				</td>
				
				<c:forEach var="record" items="${recordFieldMap }">
					<c:forEach var="mapItem" items="${contentMap }">
						<c:if test="${mapItem.key ==record.key}"> 
							<c:choose>
								<c:when test="${mapItem.key == 'customerName' and showDetail == 1 and contentMap.hasName == 1 }"> 
									<td><a href="#" onClick="showDetail(${contentMap.dialogueId})">${mapItem.value}</a>
										<img src="/img/u168.png"/>
									</td>
								</c:when>
								<c:when test="${mapItem.key == 'customerName' and showDetail == 1 and contentMap.hasName != 1 }"> 
									<td><a href="#" onClick="showDetail(${contentMap.dialogueId})">${mapItem.value}</a>
										[<a href="#" onClick="updateCusl(${contentMap.customerId},${contentMap.dialogueId})">创建</a>]
									</td>
								</c:when>
								<c:otherwise> 
									<td title="${mapItem.value}">${mapItem.value}</td>
								</c:otherwise>
							</c:choose>
						</c:if> 
					</c:forEach>
				</c:forEach>
				
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="../../page.jsp"></jsp:include>