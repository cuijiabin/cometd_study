<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<c:if test="${id==0}">
<h4>请选择您要咨询的考试</h4>
</c:if>
<c:if test="${id!=0}">
<h4>请选择您需要的服务</h4>
</c:if>
<br/>
<table style="width:300px;" class="table table-bordered ">
    <c:forEach var="wait" varStatus="status"  items="${waitList}"> 
    	<c:if test="${status.index%3==0 }">
        	<tr>
        </c:if>
            <td><a <c:if test="${wait.pId!=0}"> target="_blank" href="${wait.linkUrl }" </c:if> <c:if test="${wait.pId==0}"> href="javascript:findWaitList(${wait.id});" </c:if>>
            		${wait.name}
            	</a>
            </td>
       	<c:if test="${status.index%3==2 }">
        	</tr>
        </c:if>
    </c:forEach>
</table>