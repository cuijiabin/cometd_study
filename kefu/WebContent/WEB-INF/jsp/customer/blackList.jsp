<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<table id="blacklisttable" class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
             <td><input type="checkbox" id="titleCheckbox" name="titleCheckbox" onclick="javascript:checkedAll();" value="全选"></td>
             <td>客户编号</td>
             <td>IP地址</td>
             <td>地理位置</td>
             <td>失效时间</td>
             <td>添加工号</td>
             <td>阻止原因</td>
             <td>操作</td>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="blacklist" items="${pageBean.objList}"> 
        <tr>
         <td><input type="checkbox" name="ck" value="${blacklist.id }" value=""></td>
            <td>${blacklist.customerId}</td>
            <td>${blacklist.ip}</td>
            <td>${blacklist.ipInfo}</td>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${blacklist.endDate}"/></td>
            <td>${blacklist.userName}</td>
            <td>${blacklist.description}</td>
            <td><a href="javascript:toUpdate('${blacklist.id}')">编辑</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../page.jsp"></jsp:include>