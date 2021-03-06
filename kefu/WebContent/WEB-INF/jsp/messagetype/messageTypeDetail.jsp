<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>   
<h3  class="u-tit c-bg c-bortit">分类信息</h3>
<div style="margin:50px;">
<input type="hidden" readonly="readonly" name="messageTypeId" id="messageTypeId" value="${messageType.id }" />
<table class="table table-bordered m-table">
   <c:if test="${messageType.id !=null}">
        <tr>
         	<td class="f-txtr tdbg">分类编号</td><td class="f-txtl">${messageType.typeId }</td>
        </tr>
        <tr>
           	<td class="f-txtr tdbg">分类名称</td><td class="f-txtl">${messageType.title }</td>
       	</tr>
        <tr>
            <td class="f-txtr tdbg">排序</td><td class="f-txtl">${messageType.sortId }</td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">展示</td>
            <td class="f-txtl" >
               <input type="checkbox"  <c:if test="${messageType.status==1 }"> checked="checked" </c:if> />
            </td>
         </tr>
   </c:if>
    <c:if test="${messageType.id ==null}"> 
         <tr>
            <td colspan="2"> 没有找到相应的分类 ！</td>
         </tr>
    </c:if>
     </table>
</div>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>