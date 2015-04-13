<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<script type="text/javascript"
	src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/jsplugin/kkpager/src/kkpager.css" />
<!-- 表格有边框 -->
<table class="table table-bordered m-table c-bgfff f-mbn">
                <tbody>
                    <tr>
                        <td colspan="2">客户编码：${dialogue.customerName } <input type="hidden" id="dialogueId" value="${dialogue.id }" /> </td>
                        <td colspan="2">接待工号：${dialogue.cardName }</td>
                        <td colspan="2">IP地址：${dialogue.ipInfo }</td>
                    </tr>
                    <tr>
                        <td colspan="3">资讯页面：${dialogue.consultPage }</td>
                        <td colspan="3">访问来源：${dialogue.keywords }</td>
                    </tr>
                    <tr>
                        <td colspan="2">开始时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dialogue.beginDate}"/></td>
                        <td colspan="2">结束时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dialogue.endDate}"/></td>
                        <td colspan="2">对话时长：${dialogue.durationTimeFM }</td>
                    </tr>
                    <tr>
                        <td colspan="2">开始方式：${dialogue.openType }</td>
                        <td colspan="2">结束方式：${dialogue.closeType }</td>
                        <td colspan="2">工号评分：${dialogue.scoreType }</td>
                    </tr>
                </tbody>
            </table>
            
