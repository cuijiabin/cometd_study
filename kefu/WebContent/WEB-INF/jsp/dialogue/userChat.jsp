<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服端访客对话框架--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body scroll="no" class="g-body">
<div class="g-head fixed">
	<a class="logo f-fl" href="#"><img src="/img/logo.png" height="42" alt="" /></a>
    <ul class="u-admin f-fr">
        <li><a href="#"><i class="icon-user"></i>欢迎 Dobao</a></li>
        <li><a href="#"><i class="icon-home"></i>首页</a></li>
        <li><a href="#"><i class="icon-lock"></i>锁屏</a></li>
        <li><a href="#"><i class="icon-logout"></i>退出</a></li>
    </ul>
</div>
<div class="m-nav">
    <ul>
        <li id="_M1" class="nLi on">
            <h3><a href="javascript:_M(1,'iframe7.html')">首页</a></h3>
        </li>
        <li id="_M2" class="nLi">
            <h3><a href="javascript:_M(2,'')">产品价格</a></h3>
        </li>
        <li id="_M3" class="nLi">
            <h3><a href="javascript:_M(3,'')">产品特性</a></h3>
        </li>
        <li id="_M4" class="nLi">
            <h3><a href="javascript:_M(4,'')">客户案例</a></h3>
        </li>
        <li id="_M5" class="nLi">
            <h3><a href="javascript:_M(5,'">文档中心</a></h3>
        </li>
        <li id="_M6" class="nLi">
            <h3><a href="javascript:_M(6,'')">关于我们</a></h3>
        </li>
    </ul>
</div>
<div class="g-bd1 f-cb">
	<div class="g-bd1c f-cb c-bor">
        <div class="g-sd1 f-fl">
            <ul class="f-cb">
                <li><a href="#">添加工单</a></li>
                <li><i>|</i><a href="#">访客阻止</a></li>
                <li><i>|</i><a href="#"> 客服转接</a></li>
            </ul>
        </div>
        <div class="g-mn1 f-fr">
            <div class="g-mn1c">
                <a class="btn btn-primary btn-small" href="#">客服管理</a>
                <a class="btn btn-primary btn-small" href="#"><i class="icon-cog icon-white"></i> 设置</a>
                <select class="c-wd60">
                    <option selected="selected">在线</option>
                    <option value="离线">离线</option>
                </select>
                <a class="btn btn-danger btn-small" href="#">退出登录</a>
            </div>
        </div>
    </div>
</div>
<div class="clear"></div>
<div class="g-bd2 f-cb">
    <div class="g-sd2l c-bor">
        <h3 class="u-tit c-bg">当前共有0个对话</h3>
        <ul class="m-talk">
        	<li class="on">
            	<p>在线客户1</p>
                <p>北京市 [ 北京联通 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li class="on">
            	<p class="">在线客户2</p>
                <p class="">北京市 [ 北京长城 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li>
            	<p class="">离线客户3</p>
                <p class="">北京市 [ 北京电信 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li class="on">
            	<p class="">在线客户2</p>
                <p class="">北京市 [ 北京长城 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li class="on">
            	<p class="">在线客户2</p>
                <p class="">北京市 [ 北京长城 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li class="on">
            	<p class="">在线客户2</p>
                <p class="">北京市 [ 北京长城 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li>
            	<p class="">离线客户3</p>
                <p class="">北京市 [ 北京电信 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li>
            	<p class="">离线客户3</p>
                <p class="">北京市 [ 北京电信 ]</p>
                <span class="u-close">x</span>
            </li>
        	<li>
            	<p class="">离线客户3</p>
                <p class="">北京市 [ 北京电信 ]</p>
                <span class="u-close">x</span>
            </li>
        </ul>
    </div>
    <div class="g-mn2">
        <div class="g-mn2c">
            <div class="u-state c-bor">等待咨询...</div>
            <div class="g-mn2c-cnt c-bor">
        		<h3 class="u-tit c-bg">欢迎 xxx 使用客服系统，与客服系统连接成功</h3>
                <div class="m-dialog2">
                    <div class="u-record r-sms-manager">
                        <p class="r-welcome">欢迎使用客服系统</p>
                        <p class="r-welcome">※ 已建立链接</p>
                        <p class="r-manager">管理员 15:30:59</p>
                        <p class="r-manager-txt">请问有什么需要帮助你的吗？<br />请问有什么需要帮助你的吗？</p>
                        <p class="r-visitor">访问者 15:31:30</p>
                        <p class="r-visitor-txt">谢谢，没有！</p>
                        <p class="r-manager">管理员 15:30:59</p>
                        <p class="r-manager-txt">请问有什么需要帮助你的吗？<br />请问有什么需要帮助你的吗？</p>
                        <p class="r-visitor">访问者 15:31:30</p>
                        <p class="r-visitor-txt">谢谢，没有！</p>
                        <p class="r-manager">管理员 15:30:59</p>
                        <p class="r-manager-txt">请问有什么需要帮助你的吗？<br />请问有什么需要帮助你的吗？</p>
                        <p class="r-visitor">访问者 15:31:30</p>
                        <p class="r-visitor-txt">谢谢，没有！</p>
                        <p class="r-visitor">访问者 15:31:30</p>
                        <p class="r-visitor-txt"><a href="http://www.xiaoma.com/" target="_blank">http://www.xiaoma.com/</a></p>
                    </div>
                    <div class="u-operate">
                        <div class="u-operatebar c-bg">
                        	<ul class="u-operatebar-btn">
                            	<li><img src="/img/icon_01.png" alt="" /></li>
                            	<li><img src="/img/icon_02.png" alt="" /></li>
                            	<li><img src="/img/icon_03.png" alt="" /></li>
                            	<li><img src="/img/icon_04.png" alt="" /></li>
                            	<li><img src="/img/icon_05.png" alt="" /></li>
                            </ul>
                        </div>
                        <div class="u-input f-cb">
                            <textarea class="u-txtarea"></textarea>
                            <div class="u-send">
                                <div class="btn-group">
                                    <a class="btn btn-primary" href="#">发送</a>
                                    <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                    <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu f-txtl">
                                        <li><a href="#"><label><input type="radio" name="2" checked>Enter 发送</label></a></li>
                                        <li><a href="#"><label><input type="radio" name="2">Ctrl + Enter 发送</label></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>                
                </div>
            </div>
        </div>
    </div>
    <div class="g-sd2r">
    	<div class="m-visitor c-bor">
        	<h3 class="u-tit c-bg">访客信息</h3>
        </div>
        <div class="m-sidemenu">
        	<div class="c-bor">
                <h3 class="u-tit c-bg">
                	<a class="f-fr" href="#">添加</a>
                    <a class="f-fl" href="#">常用语管理</a>
                </h3>
                <div class="m-sidemenu-cnt">1</div>
            </div>
        	<div class="c-bor">
                <h3 class="u-tit c-bg">
                	<a class="f-fr" href="#">添加</a>
                    <a class="f-fl" href="#">知识库</a>
                </h3>
                <div class="m-sidemenu-cnt">2</div>
            </div>
        	<div class="c-bor">
                <h3 class="u-tit c-bg">
                	<a class="f-fr" href="#">添加</a>
                    <a class="f-fl" href="#">产品信息</a>
                </h3>
                <div class="m-sidemenu-cnt">3</div>
            </div>
		</div>
    </div>
</div>
<div class="clear"></div>
<div class="g-bd3 f-cb c-bor">
	<div class="g-bd3c f-cb">
        <div class="g-sd3 f-fl">
            <ul class="f-cb">
                <li><span>在线访客：100</span></li>
                <li><i>|</i><span>等待咨询：10</span></li>
            </ul>
        </div>
        <div class="g-mn3 f-fr">
            <div class="g-mn3c">
                <ul class="f-cb">
                    <li><span>工号：cc</span></li>
                    <li><i>|</i><span>身份：一般员工</span></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
</body>
</html>
