<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>常用语管理--客服信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="favicon.ico">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="css/app.css" rel="stylesheet" type="text/css">
<link href="css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>
</head>
<body>
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">客户管理中心</a></li>
        <li><i>&gt;</i>客户管理</li>
    </ul>
</div>
<div class="g-cnt">
    <!-- 查询条件 -->
    <div class="m-query f-mar10">
        <div class="m-query-hd f-txtr">
            <button type="button" class="btn btn-primary btn-small f-fl">添加</button>
            <button type="button" class="btn btn btn-warning btn-small f-fl">编辑</button>
            <button type="button" class="btn btn btn-danger btn-small f-fl">删除</button>
            <input class="c-wd150" type="text" />
            <button type="button" class="btn btn-primary btn-small">搜索</button>
        </div>
    </div>
    
    <div class="g-bd6 f-cb f-mar20">
        <div class="g-sd6 c-bor">
            <h3 class="u-tit c-bg">常用语分类设置</h3>
            这里放树状菜单哦~
        </div>
        <div class="g-mn6">
            <div class="g-mn6c">
            	<h3 class="u-tit c-bg c-bortit">分类信息</h3>
                <table class="table table-bordered table-striped table-hover m-table">
                    <thead>
                        <tr>
                            <td class="c-wd50">序号</td>
                            <td class="c-wd300">常用语标题</td>
                            <td>内容</td>
                            <td class="c-wd50">展示</td>
                            <td class="c-wd80">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td title="我是标题">我是标题</td>
                            <td title="我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容">我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容我是内容</td>
                            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
                            <td>
                                <a class="f-mar5" href="#" title="编辑"><i class="icon-edit"></i></a>
                                <a class="f-mar5" href="#" title="删除"><i class="icon-trash"></i></a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="m-page1 m-page-rt">
                    第
                    <input type="text" value="1">
                    页，共2页 | 每页显示
                    <select class="c-wd60">
                        <option value="10" selected="selected">10</option>
                        <option value="20">20</option>
                        <option value="40">40</option>
                        <option value="100">100</option>
                        <option value="200">200</option>
                        <option value="200">1000</option>
                    </select>
                    条，显示第1条到第10条，共60条
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript">
// 自定义滚动条--左右布局右侧
(function($){
	$(window).load(function(){
		$(".g-cnt").mCustomScrollbar({theme:"minimal-dark"});
	});
})(jQuery);
</script>
</body>
</html>