<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<%@ taglib uri="/WEB-INF/xiaoma.tld" prefix="xiaoma" %>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<input type="hidden" name="status" id="status" value="${status}"/>
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
         
            <td><input type="checkbox" id="all" name="all" value="0" onclick="checkAll()"/></td>
            <td>工号</td>
            <td>姓名</td>
            <td>身份</td>
            <td>状态</td>
            <td>部门</td>
            <td>接听数</td>
            <td>操作</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${pageBean.objList}">
        <tr>
            <td><input type="checkbox" id="userId" name="userId" value="${user.id}"/></td>
            <td>${user.loginName}</td>
            <td>${user.userName}</td>
            <td>${user.roleName}</td>
            <td><xiaoma:dictValue name="d_user_onLineStatus"  value="${user.onLineStatus}"/></td>
            <td><a href="javascript:deptUser(${user.deptId})">${user.deptName}<a></a></td>
            <td>${user.maxListen}</td>
            <td><a href="javascript:findUser(${user.id})">查看<a/>&nbsp;&nbsp;&nbsp;&nbsp;
                   <c:if test="${status==1}"><a href="javascript:updateUser(${user.id})">编辑<a/></c:if>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="../../../page.jsp"></jsp:include>
<c:if test="${status==1}"> <button class="btn btn-primary btn-small" id="leaves" onclick="userLeave(2)">员工离职</button>  
  <select id="dept" name="dept" onchange="changeDept()">
      <option value="0">转移部门</option>
      <c:forEach items="${deptList}" var="dept">
     		 <option value="${dept.id}">转移至${dept.name}</option>
      </c:forEach>
  </select>
  </c:if>
  <c:if test="${status==2}">
  <button class="btn btn-primary btn-small" onclick="userLeave(1)">员工复职</button> <button class="btn" onclick="deleteAll()">删除</button>
  </c:if>
