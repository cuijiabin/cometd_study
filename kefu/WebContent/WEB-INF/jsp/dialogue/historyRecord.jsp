<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>  

<c:forEach var="dialogue" items="${dialogueList}">
     <c:choose>
         <c:when test="${dialogue.dialogueType == 1}">
           <p class="r-visitor">客户：${dialogue.customerId } <fmt:formatDate value="${dialogue.createDate}" type="time" /></p>
           <p class="r-visitor-txt">${dialogue.content }</p>
        </c:when>
         <c:otherwise>
            <p class="r-manager">${dialogue.cardName } <fmt:formatDate value="${dialogue.createDate}" type="time" /></p>
            <p class="r-manager-txt">${dialogue.content }</p>
        </c:otherwise>
     </c:choose>
</c:forEach>
<div class="r-history"><h3>历史记录</h3></div>