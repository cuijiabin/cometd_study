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
<!-- 面包屑 -->
<!-- <div class="m-crumb" style="margin-bottom: 20px"> -->
<!--     <ul class="f-cb"> -->
<!--         <li><b>位置：</b></li> -->
<!--         <li><a href="#">设置中心</a></li> -->
<!--         <li><i>&gt;</i><a href="#">管理设置</a></li> -->
<!--         <li><i>&gt;</i>工号管理</li> -->
<!--     </ul> -->
<!-- </div> -->

<!-- 表格有边框 -->
<a href="" style="font-size:18px">在职员工</a> <a href="" style="font-size:18px">离职员工</a><button style="float:right;margin-right:40px;" onclick="javascript:addUser();" class="btn" >添加工号</button>

<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
         
            <td>多选</td>
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
        <c:forEach var="user" items="${list}">
        <tr>
            <td><input type="checkbox" id="id" value="${user.id}"/></td>
            <td>${user.loginName}</td>
            <td>${user.userName}</td>
            <td>${user.cardName}</td>
            <td>${user.onLineStatus}</td>
            <td>${user.deptId}</td>
            <td>${user.maxListen}</td>
            <td><a href="/user/detail.action?id=${user.id}">查看<a/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/user/detail.action?id=${user.id}">编辑<a/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
 <c:if test=""> <input type="button" value="员工离职" id="leaves"/>  
  <select id="deptid">
      <option value="0">转移部门</option>
      <option value="1">转移至客服部</option>
      <option value="2">转移至流量部</option>
      <option value="3">转移至管理部</option>
      <option value="4">转移至好顾问</option>
      <option value="5">转移至随时学</option>
      <option value="6">转移至留学部</option>
  </select></c:if>





<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
function addUser(){
	$.dialog({content:'url:/user/add.action',
		width: 900,height: 500,
		button: [
			        {
			            name: '确认',
			            callback: function () {
			                 save(); 
			                return false;
			            },
			            focus: true
			        },
			        {
			            name: '关闭我'
			        }
			    ]
		});
}

</script>
</body>
</html>
