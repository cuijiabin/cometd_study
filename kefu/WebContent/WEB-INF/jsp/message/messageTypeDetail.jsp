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
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>
<body>
  	<h3  class="u-tit c-bg c-bortit">分类信息</h3>
      <table class="table table-bordered table-striped table-hover m-table">
        <tr>
                <td class="c-wd50">父ID<input type="text" id="id" name="id"/></td>
               </tr>
                <tr>
                <td class="c-wd50">父ID<input type="text" id="typeId" name="typeId"/></td>
               </tr>
               <tr>
                <td class="c-wd50">分类编号<input type="text" id="typeId" name="typeId"/></td>
               </tr>
               <tr>
                  <td class="c-wd50">分类名称<input type="text" type="text" id="title" name="title" /></td>
              </tr>
           <tr>
                <td class="c-wd50">排序<input type="text" type="text" id="sortId" name="sortId" /></td>
            </tr>
             <tr>
               <td class="c-wd50">展示<input type="checkbox"/></td>
            </tr>
   
      
     </table>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">


</script>
</body>
</html>
