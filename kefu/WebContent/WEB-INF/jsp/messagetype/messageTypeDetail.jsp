<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>   
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>   
<h3  class="u-tit c-bg c-bortit">分类信息</h3>
<div style="margin:50px;">
<input type="hidden" name="messageTypeId" id="messageTypeId" value="${messageType.id }" />
<table class="table table-bordered m-table">
        <tr>
         	<td class="f-txtr tdbg">分类编号</td><td class="f-txtl"><input type="text" id="typeId" name="typeId" value="${messageType.typeId }"/></td>
        </tr>
        <tr>
           	<td class="f-txtr tdbg">分类名称</td><td class="f-txtl"><input type="text" type="text" id="title" name="title" value="${messageType.title }" /></td>
       	</tr>
        <tr>
            <td class="f-txtr tdbg">排序</td><td class="f-txtl"><input type="text" type="text" id="sortId" name="sortId" value="${messageType.sortId }" /></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">展示</td>
            <td class="f-txtl" >
               <input type="checkbox"  <c:if test="${messageType.status==1 }"> checked="checked" </c:if> />
            </td>
         </tr>
   
     </table>
</div>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>