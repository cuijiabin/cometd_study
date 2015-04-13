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
			<td>风格名称</td>
            <td>操作</td>
            <td>获取代码</td>
            <td>页面样式设置</td>
            <td>接待规则设置</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="style" items="${pageBean.objList}">
			<tr>
				<td>${style.name}</td>
				<td><a href="#" onClick="rename(${style.id})">重命名</a></td>
				<td><a href="#" onClick="showDetail(${style.id})">获取代码</a></td>
				<td><a href="#" onClick="showDetail(${style.id})">设置</a></td>
				<td>
					<a href="#" onClick="showDetail(${style.id})">业务分组</a>
					<a href="#" onClick="showDetail(${style.id})">业务分流</a>
					<a href="#" onClick="showDetail(${style.id})">分配机制</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="../page.jsp"></jsp:include>