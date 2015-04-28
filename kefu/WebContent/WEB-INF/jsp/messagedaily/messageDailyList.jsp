<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>  
<%@ page import="javax.servlet.http.HttpSession"  %>  
<%@ page import="com.xiaoma.kefu.model.User"  %> 
<%@ page import="com.xiaoma.kefu.util.CheckCodeUtil"  %>  
<link rel="stylesheet" type="text/css" href="/jsplugin/kkpager/src/kkpager.css" />
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<h3  class="u-tit c-bg c-bortit">常用语信息</h3>
<input type="hidden" name="messageDaily" id="messageDaily" value="${messageDailyId}" />
 <% HttpSession session1 = request.getSession(); User user = (User)session1.getAttribute("user"); Integer userId = user.getId();
    Integer typeId = Integer.parseInt(String.valueOf(request.getAttribute("typeId")));
    String adds="";
    String updates="";
    String dels="";
    if(typeId==1){
    	adds="f_usesay_add";
    	updates="f_usesay_update";
    	dels="f_usesay_del";
    }else{
    	adds="f_persay_add";
    	updates="f_persay_update";
    	dels="f_persay_del";
    }
    boolean checkupdate=false;
    if(CheckCodeUtil.isCheckFunc(userId,updates)){
    	checkupdate=true;
    }
    boolean checkdel=false;
    if(CheckCodeUtil.isCheckFunc(userId,dels)){
    	checkdel=true;
    }
 %>
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
               <% if(checkupdate) {%>
               <a class="f-mar5" href="javascript:toUpdate('${message.id}')" title="编辑"><i class="icon-edit"></i></a>
               <%} %>
               <% if(checkdel) {%>
               <a class="f-mar5" href="javascript:deleteMessageDaily('${message.id}')" title="删除"><i class="icon-trash"></i></a>
                <%} %>
            </td>
        </tr>
        </c:forEach>
        <tr>
        	<td  colspan="5"> <jsp:include page="../page.jsp"></jsp:include></td>
        </tr>
        </tbody>
     </table>
