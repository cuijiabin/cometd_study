<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>  
  
<json:object>  
  <json:array name="list" var="ccnId" items="${ccnIds}">  
    <json:object>  
      <json:property name="ccnId" value="${ccnId}"/>  
      <json:property name="customerId" value="${ccnIdMap[ccnId]}"/>  
      <json:property name="ip" value="${customerMap[ccnIdMap[ccnId]].ip}"/>  
    </json:object>  
  </json:array>  
</json:object>