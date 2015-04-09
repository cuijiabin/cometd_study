<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<!-- 表格有边框 -->
<h3 class="u-tit c-bg">对话内容</h3>
	<div class="u-record r-sms-visitor">
		<c:forEach var="detail" items="${pageBean.objList}">
			<p class="r-manager">${detail.content }</p>
		</c:forEach>
	</div>                
<jsp:include page="../../page.jsp"></jsp:include>