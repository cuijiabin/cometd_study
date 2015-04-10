<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

     $(function(){
    	 
    	  $("#savebtn").click(function(){
    		  
    		 document.myform.action="<%=request.getContextPath()%>/blacklist/add.action";
    		  document.myform.submit();
    	  });
     });
  
</script>
</head>

<body>
<spring:url value="/blacklist/add.action" var="formUrl" />

	<form:form name="myform" method="post" >
        <table >
            <tbody>
                <tr>
                    <td ><font color="red">*</font>客户编号：</td>
                    <td ><input name="customerId" required type="text"  value="10000000000"  nullmsg="请选填" errormsg="名称至少1个字符,最多20个字符！"sucmsg=" " ></td>
                    
                </tr>
                <tr>
                    <td ><font color="red">*</font>IP地址：</td>
                    <td ><input name="ip" value="192.168.1.101" required type="text"  nullmsg="" errormsg="名称至少1个字符,最多70个字符！"sucmsg=" "></td>
                </tr>
                <tr>
                  <td ><font color="red">*</font>失效时间：</td>
                    <td ><input name="endDate" value="2015-04-07 21:17:28" required type="text"  ></td>
                </tr>
                <tr>
                    <td ><font color="red">*</font>阻止原因：</td>
                    <td><textarea name="description"  errormsg="最多120个字符！"sucmsg=" "><c:out value="捣乱"></c:out></textarea></td>
                </tr>
            </tbody>
        </table>
        <div class="text-center">
    		<button type="button" id="savebtn">保存 </button>
        </div>
    </form:form>





</body>
</html>