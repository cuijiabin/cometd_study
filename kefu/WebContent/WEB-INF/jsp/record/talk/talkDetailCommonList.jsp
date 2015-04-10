<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript"
	src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/jsplugin/kkpager/src/kkpager.css" />

<h3 class="u-tit c-bg">对话内容</h3>
	<div class="u-record r-sms-visitor">
		<c:forEach var="detail" items="${pageBean.objList}">
			<c:choose>
				<c:when test="${detail.dialogueType == '1' }"> 
					<p class="r-visitor">${detail.customerId } &ensp; <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.createDate}"/></p>
                    <p class="r-visitor-txt">${detail.content }</p>
				</c:when>
				<c:when test="${detail.dialogueType == '2' }"> 
					<p class="r-manager">${detail.cardName } &ensp; <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.createDate}"/></p>
                    <p class="r-manager-txt">${detail.content }</p>
				</c:when>
				<c:otherwise> 
				    <p class="r-welcome"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.createDate}"/></p>
                    <p class="r-welcome">${detail.content }</p>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>                
<jsp:include page="../../page.jsp"></jsp:include>