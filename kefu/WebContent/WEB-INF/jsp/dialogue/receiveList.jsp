<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>  
 
<c:forEach var="dq" items="${list}">
	<li id="${dq.ccnId }" class="on" onclick="javascript:changeTitle(${dq.customer.id },'${dq.ccnId }')" customerId="${dq.customer.id }" >
	   <p>客户：${dq.customer.id }</p>
	   
	   <c:if test="${dq.customer.ipInfo == null }"><p>IP:--</p></c:if>
	   <c:if test="${dq.customer.ipInfo != null }"><p>IP:${dq.customer.ipInfo}</p></c:if>
	   <p>上线时间：<fmt:formatDate value="${dq.enterTime}" type="time" /></p>
	   
	   <span class="u-close" onclick="javascript:endDialogue('${dq.ccnId }')">x</span>
	</li>
</c:forEach>

<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>