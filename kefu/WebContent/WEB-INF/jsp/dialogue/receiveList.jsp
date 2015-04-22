<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<c:forEach var="dq" items="${list}">
	<li id="${dq.ccnId }" class="on" onclick="javascript:changeTitle(${dq.customer.id },'${dq.ccnId }')">
	   <p>客户：${dq.customer.id }</p>
	   <p>${dq.customer.ipInfo }</p>
	   <p>上线时间：${dq.enterTime }</p>
	   <span class="u-close" onclick="javascript:endDialogue('${dq.ccnId }')">x</span>
	</li>
</c:forEach>