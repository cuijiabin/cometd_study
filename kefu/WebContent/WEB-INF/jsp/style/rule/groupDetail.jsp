<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<script type="text/javascript"
	src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/jsplugin/kkpager/src/kkpager.css" />

<div class="g-mn7c">
 	<h3 class="u-tit c-bg">组内成员</h3>
     <div class="m-group-member">
     	<ul class="m-tag">
	     	<c:forEach var="detail" items="${detailList }" varStatus="status">
				<c:if test="${status.index % 5 == 0 }"> <br /> </c:if>  
					<input type="hidden" name="id" value="${detail.id }">
					<li>
		             	<span>${detail.cardName }</span>
		                 <span class="u-close" onclick="delUser(${detail.id})">&times;</span>
		             </li>
			</c:forEach>
         </ul>
     </div>
 </div>
