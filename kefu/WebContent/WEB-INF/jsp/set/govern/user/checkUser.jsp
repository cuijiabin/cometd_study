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
<style type="text/css">
	input{
		width:135px;
	}
</style>
<body>
<!-- 表格有边框 -->
<div style="margin:30px;">
<table class="table table-bordered m-table">
        <tr>
            <td class="f-txtr tdbg" width="80px">工号</td>
            <td class="f-txtl"><input type="text" id="loginName" name="loginName" value="${user.loginName}" readonly="readonly"/></td>
            <td class="f-txtr tdbg" width="80px">姓名</td>
            <td class="f-txtl"><input type="text" id="userName" name="userName" value="${user.userName}" maxlength="16" readonly="readonly"/></td>
        </tr>
        <tr>
         
           <td class="f-txtr tdbg">密码</td>
            <td class="f-txtl"><input type="password" id="password" name="password" value="********" readonly="readonly"/></td>
            <td class="f-txtr tdbg">确认密码</td>
            <td class="f-txtl"><input type="password" id="password1" name="password1" value="********" readonly="readonly"/></td>
        </tr>
        <tr>
         
            <td class="f-txtr tdbg">部门</td>
            <td class="f-txtl">
                <input type="text" id="deptName" name="deptName" value="${user.deptName}" maxlength="6" readonly="readonly"/>
            </td>
            <td class="f-txtr tdbg">角色</td>
            <td class="f-txtl">
                <input type="text" id="roleName" name="roleName" value="${user.roleName}" maxlength="6" readonly="readonly"/>
            </td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">接听等级</td>
               <td class="f-txtl">
                  <input type="text" id="maxListen" name="maxListen" value="${user.listenLevel}" maxlength="6" readonly="readonly"/>
            </td>
            <td class="f-txtr tdbg">最大接听数</td>
            <td class="f-txtl"><input type="text" id="maxListen" name="maxListen" value="${user.maxListen}" maxlength="6" readonly="readonly"/></td>
        </tr>
        <tr>       
            <td class="f-txtr tdbg">工号名片</td>
            <td class="f-txtl"><input type="text" id="cardName" name="cardName" value="${user.cardName}" maxlength="10" readonly="readonly"/></td>
            <td class="f-txtr tdbg">入职日期</td>
            <td class="f-txtl"><input type="text" id="createDate" name="createDate" value="${user.createDate}"  readonly="readonly"/></td>
        </tr>
</table>

<button style="float:right;margin-right:40px;" onclick="javascript:cl();" class="btn" >关闭</button>
</div>

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
