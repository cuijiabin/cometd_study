<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 表格有边框 -->
<h3>查看工号</h3>
<table class="table table-bordered table-striped table-hover m-table">
        <tr>
            <td>工号</td>
            <td><input type="text" id="loginName" name="loginName" value="${user.loginName}" readonly="readonly"/></td>
            <td>姓名</td>
            <td><input type="text" id="userName" name="userName" value="${user.userName}" readonly="readonly"/></td>
        </tr>
        <tr>
         
            <td>密码</td>
            <td><input type="password" id="password" name="password" value="*********" readonly="readonly"/></td>
            <td>确认密码</td>
            <td><input type="password" id="password1" name="password1" value="*********" readonly="readonly"/></td>
        </tr>
        <tr>
         
            <td>部门</td>
            <td>
                <select id="deptId" name="deptId" disabled="disabled">
                    <c:forEach items="${deptList}" var="dept" >
                	<option value="${dept.id}" <c:if test="${user.deptId==dept.id}">selected</c:if>>${dept.name}</option>
                	</c:forEach>
                </select>
            </td>
             <td>角色</td>
            <td>
                <select id="roleId" name="roleId" disabled="disabled">
                	<c:forEach items="${roleList}" var="role">
                	<option value="${role.id}" <c:if test="${user.roleId==role.id}">selected</c:if>>${role.name}</option>
                	</c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>接听等级</td>
               <td>
                <select id="listenLevel" name="listenLevel" disabled="disabled">
                	<option <c:if test="${user.listenLevel==1}">selected</c:if>>1</option>
                	<option <c:if test="${user.listenLevel==2}">selected</c:if>>2</option>
                	<option <c:if test="${user.listenLevel==3}">selected</c:if>>3</option>
                	<option <c:if test="${user.listenLevel==4}">selected</c:if>>4</option>
                	<option <c:if test="${user.listenLevel==5}">selected</c:if>>5</option>
                </select>
            </td>
            <td>最大接听数</td>
            <td><input type="text" id="maxListen" name="maxListen" value="${user.maxListen}" readonly="readonly"/></td>
        </tr>
        <tr>       
            <td>工号名片</td>
            <td><input type="text" id="cardName" name="cardName" value="${user.cardName}" readonly="readonly"/></td>
            <td>入职日期</td>
            <td><input type="text" id="createDate" name="createDate" value="${user.createDate}" onClick="WdatePicker()" class="c-wd120 Wdate" readonly="readonly"/></td>
        </tr>
</table>
<button style="float:right;margin-right:40px;" onclick="javascript:cl();" class="btn" >关闭</button>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
var api = frameElement.api,W=api.opener;
function cl(){
	api.close();			
}
</script>

</body>
</html>
