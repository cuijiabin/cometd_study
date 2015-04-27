<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>   
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>

<h3  class="u-tit c-bg c-bortit">常用语信息</h3>

<table class="table table-bordered m-table">
        <thead>
              <tr>
                  <td class="c-wd50">编号</td>
                  <td class="c-wd300">常用语标题</td>
                  <td>内容</td>
                  <td class="c-wd50">展示</td>
                  <td class="c-wd80">操作</td>
              </tr>
        </thead>
        <tbody>
        <c:forEach var="message" items="${pageBean.objList }">
        <tr>
            <td>${message.id}</td>
            <td>${message.title}</td>
            <td>${message.content}</td>
            <td><input type="checkbox" <c:if test="${message.status==1 }"> checked="checked" </c:if>></td>
            <td>
               <a class="f-mar5" href="javascript:toUpdate('${message.id}')" title="编辑"><i class="icon-edit"></i></a>
               <a class="f-mar5" href="javascript:deleteMessageDaily('${message.id}')" title="删除"><i class="icon-trash"></i></a>
            </td>
        </tr>
        </c:forEach>
        <tr>
        	<td  colspan="5"> <jsp:include page="../page.jsp"></jsp:include></td>
        </tr>
        </tbody>
     </table>
