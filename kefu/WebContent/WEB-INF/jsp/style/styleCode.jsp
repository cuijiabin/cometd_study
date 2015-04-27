<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>    
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="g-cnt">    
    <div class="f-padd10">
    	<div class="m-tit">
            <h3>获取网页代码</h3>
        </div>
        <h3 class="u-tit1">获取浮动客服图标代码：（网站必备）</h3>
        <p class="u-txt">将以下代码插入到您网站的相关页面的 &lt;/html&gt; 的前一行！</p>
        <textarea style="min-width:600px;height:100px;" readonly="readonly" id="code1">${code }</textarea>
        <div class="clear"></div>
        <button class="btn btn-primary" type="button" id="copyBtn1">复制</button>
        <div class="u-hr"></div>
        <h3 class="u-tit1">获取固定论坛签名代码</h3>
        <p class="u-txt">将以下代码插入到您网站的相关页面的 &lt;/html&gt; 的前一行！</p>
        <textarea style="min-width:600px;height:100px;" readonly="readonly" id="code2">${code2 }</textarea>
        <div class="clear"></div>
        <button class="btn btn-primary" type="button" id="copyBtn2">复制</button>
    </div>
    
	<div class="f-txtr f-mtw">
		<button type="submit" class="btn" onclick="cancel();">关闭</button>
	</div>
</div>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/jsplugin/zclip/jquery.zclip.min.js"></script>
<script type="text/javascript">
var api = frameElement.api;//调用父页面数据  
var W = api.opener;//获取父页面对象      

//关闭
function cancel(){
	api.close();
}

$('#copyBtn1').zclip({ 
    path: "/jsplugin/zclip/ZeroClipboard.swf", 
    copy: function(){ 
        return $('#code1').val(); 
	},
	afterCopy:function(){/* 复制成功后的操作 */
		W.$.dialog.alert("复制成功!");
	}
}); 

$('#copyBtn2').zclip({ 
    path: "/jsplugin/zclip/ZeroClipboard.swf", 
    copy: function(){ 
        return $('#code2').val(); 
	},
	afterCopy:function(){/* 复制成功后的操作 */
		W.$.dialog.alert("复制成功!");
	}
}); 

</script>

</body>
</html>
