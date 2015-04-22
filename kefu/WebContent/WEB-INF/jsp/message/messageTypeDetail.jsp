<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>   
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服信息管理系统</title>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>
<body>
  	<h3  class="u-tit c-bg c-bortit">分类信息</h3>
      <table class="table table-bordered table-striped table-hover m-table">
        <tr>
                <td class="c-wd50">ID<input type="text" id="id" name="id" value="${messageType.id }"/></td>
               </tr>
                <tr>
                <td class="c-wd50">父ID<input type="text" id="typeId" name="typeId" value="${messageType.pId }"/></td>
               </tr>
               <tr>
                <td class="c-wd50">分类编号<input type="text" id="typeId" name="typeId" value="${messageType.typeId }"/></td>
               </tr>
               <tr>
                  <td class="c-wd50">分类名称<input type="text" type="text" id="title" name="title" value="${messageType.title }" /></td>
              </tr>
           <tr>
                <td class="c-wd50">排序<input type="text" type="text" id="sortId" name="sortId" value="${messageType.sortId }" /></td>
            </tr>
             <tr>
               <td class="c-wd50">展示
               <c:if test="${messageType.status==1 }">
               <input type="checkbox"  checked="checked" />
               </c:if>
               <c:if test="${messageType.status==2 }">
               <input type="checkbox"  />
               </c:if>
              </td>
            </tr>
   
     </table>

<script type="text/javascript">


</script>
</body>
</html>
