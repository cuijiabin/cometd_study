<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>界面样式设置--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">系统设置</a></li>
        <li><i>&gt;</i><a href="#">风格管理</a></li>
        <li><i>&gt;</i>界面样式设置</li>
    </ul>
</div>

<div class="g-cnt">
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd">当前营销风格效果预览 <span class="c-colf00">[ <b>${style.name }</b> ]</span></div>
    </div>
    
    <div class="f-padd10">
    <!-- 表格有边框 -->
        <table class="table table-bordered m-table c-wdat">
            <tbody>
                <tr>
                    <td class="c-wd150">访问端界面</td>
                    <td class="f-txtl"><img src="http://oc2.xiaoma.com/style/setting/ver06/img/sreenshot/wxf_new/sreenshot_company_gray.gif" width="154" alt="" /></td>
                    <td class="c-wd120"><button type="button" class="btn btn-small" onclick="editClient(${style.id})" >设置</button></td>
                </tr>
                <tr>
                    <td class="c-wd150">客服图标</td>
                    <td class="f-txtl"><img src="http://oc2.xiaoma.com/img/upload/53lx/zdytb/on_53lx1406714337.png" alt="" /></td>
                    <td class="c-wd120"><button type="button" class="btn btn-small" onclick="editServicePC(${style.id})" >设置</button></td>
                </tr>
                <tr>
                    <td class="c-wd150">对话邀请框</td>
                    <td class="f-txtl"><img src="http://oc2.xiaoma.com/img/upload/53lx/zdyivt/zdyivt_53kf_1427443841.jpg" alt="" /></td>
                    <td class="c-wd120"><button type="button" class="btn btn-small" onclick="editInvitePC(${style.id})" >设置</button></td>
                </tr>
                <tr>
                    <td class="c-wd150">手机端客服图标</td>
                    <td class="f-txtl"><img src="" alt="" /></td>
                    <td class="c-wd120"><button type="button" class="btn btn-small" onclick="editServiceYD(${style.id})" >设置</button></td>
                </tr>
                <tr>
                    <td class="c-wd150">手机端对话邀请框</td>
                    <td class="f-txtl"><img src="" alt="" /></td>
                    <td class="c-wd120"><button type="button" class="btn btn-small" onclick="editInviteYD(${style.id})" >设置</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);

//访客端界面
function editClient(styleId){
	var url = '/clientStyle/edit.action?styleId='+styleId;
	window.location = url;
	return;
}

//客服图标 PC
function editServicePC(styleId){
	var url = '/serviceIcon/edit.action?styleId='+styleId+'&deviceTypeId=1';
	window.location = url;
	return;
}

//对话邀请框设置 PC
function editInvitePC(styleId){
	var url = '/inviteIcon/edit.action?styleId='+styleId+'&deviceTypeId=1';
	window.location = url;
	return;
}

//客服图标 移动
function editServiceYD(styleId){
	var url = '/serviceIcon/edit.action?styleId='+styleId+'&deviceTypeId=2';
	window.location = url;
	return;
}

//对话邀请框设置 移动
function editInviteYD(styleId){
	var url = '/inviteIcon/edit.action?styleId='+styleId+'&deviceTypeId=2';
	window.location = url;
	return;
}


</script>
</body>
</html>

