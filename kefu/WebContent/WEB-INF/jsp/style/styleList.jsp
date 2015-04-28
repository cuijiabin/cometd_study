<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ page import="com.xiaoma.kefu.util.CheckCodeUtil"  %>  
<%@ page import="javax.servlet.http.HttpSession"  %>  
<%@ page import="com.xiaoma.kefu.model.User"  %>  
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript"src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>	
<link rel="stylesheet" type="text/css"
	href="/jsplugin/kkpager/src/kkpager.css" />
<%
HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();
    boolean rename=false;
    if(CheckCodeUtil.isCheckFunc(userId,"f_style_rename")){
    	rename=true;
    }
    boolean getcode=false;
    if(CheckCodeUtil.isCheckFunc(userId,"f_style_code")){
    	getcode=true;
    }
    boolean faceset=false;
    if(CheckCodeUtil.isCheckFunc(userId,"f_styface_set")){
    	faceset=true;
    }
    boolean receiveset=false;
    if(CheckCodeUtil.isCheckFunc(userId,"f_receive_set")){
    	receiveset=true;
    }
%>
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
				<td>
				<% if(rename) {%>
				<a href="#" onClick="rename(${style.id})">重命名</a>
				<%}else{%>
					重命名
				<%}%>
				</td>
				<td>
				<% if(getcode) {%>
				<a href="#" onClick="getCode(${style.id})">获取代码</a>
				<%}else{%>
					获取代码
				<%}%>
				</td>
				<td>
				<% if(faceset) {%>
				<a href="#" onClick="editCommon(${style.id})">设置</a>
				<%}else{%>
					设置
				<%}%>
				</td>
				<td>
				<% if(receiveset) {%>
					<a href="#" onClick="fenzu(${style.id})">业务分组</a>
					<a href="#" onClick="fenliu(${style.id})">业务分流</a>
					<a href="#" onClick="editAllot(${style.id})">分配机制</a>
				<%}else{%>
					业务分组  业务分流  分配机制
				<%}%>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="../page.jsp"></jsp:include>