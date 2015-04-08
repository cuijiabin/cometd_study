<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!-- 面包屑 -->
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">首页</a></li>
        <li><i>&gt;</i><a href="#">客户管理中心</a></li>
        <li><i>&gt;</i>客户管理</li>
    </ul>
</div>
<!-- 查询条件 -->
<div class="m-query f-mar10">
	<div class="m-query-hd">
        <label>部门：</label><select class="c-wd80">
            <option selected="selected">全部部门</option>
            <option value="客服部">客服部</option>
            <option value="留学部">留学部</option>
            <option value="随时学">随时学</option>
            <option value="好顾问">好顾问</option>
        </select>
        <label>专题：</label><input class="c-wd80" type="text" />
        <label>关键词：</label><input class="c-wd80" type="text" />
        <label>对话时间：</label><input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" /> - <input class="c-wd80 Wdate" type="text" onClick="WdatePicker()" />
        <div class="u-subsec">
           	<label><input type="checkbox">显示子栏目</label>
        </div>
    </div>
    <div class="u-hr"></div>
    <div class="m-query-bd">
        <div class="f-mbm">
            <label>客户编号：</label><input class="c-wd150" type="text" />
            <label>IP地址：</label><input class="c-wd150" type="text" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>聊天内容：</label><input class="c-wd150" type="text" /><span class="help-inline c-clred">协议类型不能为空</span>
        </div>
        <div class="f-mbm">
            <label>客户编号：</label><input class="c-wd150" type="text" />
            <label>IP地址：</label><input class="c-wd150" type="text" />
            <label>咨询页面：</label><input class="c-wd150" type="text" />
            <label>聊天内容：</label><input class="c-wd150" type="text" /><span class="help-inline c-clred">协议类型不能为空</span>
        </div>
        <div class="f-mbm">
            <label>部门：</label><select class="c-wd80">
                <option selected="selected">全部部门</option>
                <option value="客服部">客服部</option>
                <option value="留学部">留学部</option>
                <option value="随时学">随时学</option>
                <option value="好顾问">好顾问</option>
            </select>
            <label>专题：</label><input class="c-wd150" type="text" /><span class="help-inline c-clred">协议类型不能为空</span>
            <label>关键词：</label><input class="c-wd150" type="text" />
            <label>交谈条数：</label>
            <div class="u-subsec">
                <label><input type="radio" name="1" />小于</label>
                <label><input type="radio" name="1" />等于</label>
                <label><input type="radio" name="1" />大于</label>
            </div>
            <div class="input-append">
              	<input class="c-wd30" type="text">
              	<button class="btn btn-small" type="button">条</button>
            </div>
        </div>
    </div>
    <div class="u-hr"></div>
    <div class="m-query-bd">
    	<label>对话日期：</label><input class="c-wd120 Wdate" type="text" onClick="WdatePicker()" />
        <button type="button" class="btn btn-primary btn-small">下载</button>
    </div>
</div>

<!-- 按钮组 -->
<div class="btn-group f-mar10" data-toggle="buttons-radio">
    <button class="btn">文档模型</button>
    <button class="btn">栏目模型</button>
    <button class="btn">专题模型</button>
</div>
<div class="btn-group" data-toggle="buttons-radio">
    <button type="button" class="btn btn-primary">文档模型</button>
    <button type="button" class="btn btn-primary">栏目模型</button>
    <button type="button" class="btn btn-primary">专题模型</button>
</div>

<!-- 操作按钮 -->
<div class="m-btn f-mar10">
   	<div class="u-subsec pull-left">
        <label><input type="checkbox" />显示子栏目</label>
    </div>
    <div class="u-upload pull-left">
    	<button class="btn" type="button">上传文件</button>
        <input type="file"/>
    </div>
    <button type="button" class="btn">新增</button>
    <button type="button" class="btn btn-primary">保存</button>
    <button type="button" class="btn btn-info">字段列表</button>
    <button type="button" class="btn btn-success">复制</button>
    <button type="button" class="btn btn-warning">修改</button>
    <button type="button" class="btn btn-danger">删除</button>
    <button type="button" class="btn btn-inverse">上移</button>
    <button type="button" class="btn btn-link">下移</button>
    <button type="button" class="btn">置顶</button>
    <button type="button" class="btn">置底</button>
  	<button type="button" class="btn disabled">禁用</button>
</div>
<!-- 表单提交 -->
<div class="f-txtc">
    <div class="m-form f-mar10">
        <div class="form_box">
            <form class="form-horizontal error" novalidate>
                <div class="control-group">
                    <label class="control-label">Email address</label>
                    <div class="controls">
                        <input type="text" id="inputError" aria-invalid="false">
                        <span class="help-inline">Please correct the error</span>
                    </div>
                </div><br>
                <div class="control-group">
                    <label class="control-label">Email again</label>
                    <div class="controls">
                        <input type="text" id="inputError" aria-invalid="false">
                        <span class="help-inline">Please correct the error</span>
                    </div>
                </div><br>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save changes <i class="icon-ok icon-white"></i></button>
                    <button type="reset" class="btn">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<a data-toggle="modal" id="onshowdiv" href="#sitechange">弹窗</a>
<div id="sitechange" class="modal hide fade" aria-hidden="true" style="display: none; height:auto;">
    <div class="modal-header">
    	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    	<h4>请选择站点</h4>
    </div>
    <div class="modal-body" id="siteDiv">
    </div>
    
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" aria-hidden="true" onclick="okClosed()">取消</a>
        <a href="#" class="btn btn-primary" onclick="okSite()">确认</a>
    </div>
</div>

<!-- 表单组合 -->
<div class="m-tabbox f-mar10 fixed">
	<div class="tabbox pull-left">
        <div>
        	<p><input type="text" id="smallImage" readonly name="smallImage" onchange="fn_smallImage(this.value);" value=""></p>
        	<p>
            	<input class="valid" id="f_smallImage" name="imgFile" size="23" style="width:170px;" type="file">
    			<button type="button" class="btn" onclick="uploadImg('smallImage',this);">上传</button>
            </p>
        </div>
        <p>
        	宽：<input class="valid" id="w_smallImage" name="smallImageWidth" value="100" default="100" style="width:70px;" type="text" errormsg="只能为数字！" sucmsg=" " datatype="n"><span class="Validform_checktip"></span>
        	<button type="button" class="btn" onclick="imgCrop('smallImage');">裁剪</button>
        </p>
        <div>
        	高：<input class="valid" id="h_smallImage" name="smallImageHigth" value="100" style="width:70px;" type="text" errormsg="只能为数字！" sucmsg=" " datatype="n"><span class="Validform_checktip"></span>
            <div class="u-subsec">
                <label><input type="checkbox">压缩</label>
            </div>
        </div>
    </div>
    <div class="tabbox-img pull-left">
        <input id="t_smallImage" value="false" type="hidden">
        <input id="tw_smallImage" value="116" type="hidden">
        <input id="th_smallImage" value="86" type="hidden">
        <img id="img_smallImage" style="/*display:none;*/ width:300px; height:151px;">
    </div>
    
    <div class="m-btn m-btn2 pull-right">
        <button type="button" class="btn btn2">删除</button>
        <button type="button" class="btn btn2">上移</button>
        <button type="button" class="btn btn2">下移</button>
    </div>
</div>

<!-- 表格有边框 -->
<table class="table table-bordered table-striped table-hover m-table">
    <thead>
        <tr>
            <td style="width:30px;text-align:center;"><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td style="width:50px;">序号</td>
            <td style="width:657px;">公司名</td>
            <td style="width:657px;">地址</td>
            <td style="width:300px;">日期</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td><input type="text" name="modelName" value="新闻"></td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td><button type="button" class="btn btn-small">新增</button></td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td><select name="attrId">
                    <option value="">--属性--</option>
                 	<option>属性值</option>
                </select></td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>
            	<span class="label label-sm label-success">正常</span>
            	<span class="label label-sm label-important">正常</span>
            	<span class="label label-sm label-warning">正常</span>
            	<span class="label label-sm label-info">正常</span>
            	<span class="label label-sm label-inverse">正常</span>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>
                <span><a id="delete" href="/cms-core/user/delete/1?page=1">删除</a></span>
                <span><a id="disable" href="/cms-core/user/updateEnabled/1?page=1">停用</a></span>
                <span><a id="lock" href="/cms-core/user/updateLock/1?page=1">锁定</a></span>
                <span><a id="edit" href="/cms-core/user/toUpdate/1?page=1">编辑</a></span>
                <span><a id="rolesm" href="/cms-core/user/roles/1?page=1">角色管理</a></span>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td class="f-txtl">
                <span class="file-dir">directory</span>
                <span class="file-file">file</span>
                <span class="file-zip">zip</span>
                <span class="file-img">img</span>
                <span class="file-txt">txt</span>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>北京市海淀区中关村海淀北一街2号鸿城拓展大厦8层</td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>北京市海淀区中关村海淀北一街2号鸿城拓展大厦8层</td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td class="u-subsec">
                <label><input type="radio" name="2" />1</label>
                <label><input type="radio" name="2" />2</label>
                <label><input type="radio" name="2" />3</label>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>
            	<label><input type="checkbox" />1</label>
                <label><input type="checkbox" />2</label>
                <label><input type="checkbox" />3</label>
            </td>
            <td>2014-07-25</td>
        </tr>
    </tbody>
</table>
<!-- 表格无边框 -->
<table class="table table-striped table-hover m-table">
    <thead>
        <tr>
            <td style="width:30px;text-align:center;"><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td style="width:50px;">序号</td>
            <td style="width:657px;">公司名</td>
            <td style="width:657px;">地址</td>
            <td style="width:300px;">日期</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td><input type="text" name="modelName" value="新闻"></td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td><button type="button" class="btn btn-small">新增</button></td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td><select name="attrId">
                    <option value="">--属性--</option>
                 	<option>属性值</option>
                </select></td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>
            	<span class="label label-sm label-success">正常</span>
            	<span class="label label-sm label-important">正常</span>
            	<span class="label label-sm label-warning">正常</span>
            	<span class="label label-sm label-info">正常</span>
            	<span class="label label-sm label-inverse">正常</span>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>
                <span><a id="delete" href="/cms-core/user/delete/1?page=1">删除</a></span>
                <span><a id="disable" href="/cms-core/user/updateEnabled/1?page=1">停用</a></span>
                <span><a id="lock" href="/cms-core/user/updateLock/1?page=1">锁定</a></span>
                <span><a id="edit" href="/cms-core/user/toUpdate/1?page=1">编辑</a></span>
                <span><a id="rolesm" href="/cms-core/user/roles/1?page=1">角色管理</a></span>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td class="f-txtl">
                <span class="file-dir">directory</span>
                <span class="file-file">file</span>
                <span class="file-zip">zip</span>
                <span class="file-img">img</span>
                <span class="file-txt">txt</span>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>北京市海淀区中关村海淀北一街2号鸿城拓展大厦8层</td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>北京市海淀区中关村海淀北一街2号鸿城拓展大厦8层</td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td class="u-subsec">
                <label><input type="radio" name="3" />1</label>
                <label><input type="radio" name="3" />2</label>
                <label><input type="radio" name="3" />3</label>
            </td>
            <td>2014-07-25</td>
        </tr>
        <tr>
            <td><input type="checkbox" id="inlineCheckbox" value="全选"></td>
            <td>1</td>
            <td>北京小马过河互联网科技有限公司</td>
            <td>
            	<label><input type="checkbox" />1</label>
                <label><input type="checkbox"  />2</label>
                <label><input type="checkbox" />3</label>
            </td>
            <td>2014-07-25</td>
        </tr>
    </tbody>
</table>
<!-- 生成 -->
<div class="form-actions f-txtc mar0">
    <button type="submit" class="btn btn-primary">生成首页HTML <i class="icon-ok icon-white"></i></button>
    <button type="reset" class="btn">删除首页HTML</button>
</div>
<!-- 提交按钮 -->
<div class="f-txtc">
    <button type="submit" class="btn btn-primary">提交 <i class="icon-ok icon-white"></i></button>
    <button type="reset" class="btn">重置</button>
</div>
<!-- 翻页 -->
<div class="m-page m-page-sr m-page-sm">
    <a href="#" class="first pageprv z-dis"><span class="pagearr">&lt;</span>上一页</a>
    <a href="#">1</a>
    <a href="#" class="z-crt">2</a>
    <a href="#">3</a>
    <a href="#">4</a>
    <i>...</i>
    <a href="#">10</a>
    <a href="#" class="last pagenxt">下一页<span class="pagearr">&gt;</span></a>
</div>

<table class="table table-bordered m-table">
    <tbody>
        <tr>
            <td class="f-txtr tdbg">协议类型：</td>
            <td class="f-txtl" colspan="3"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">部署路径：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
            <td class="f-txtr tdbg">端口号：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">部署路径：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
            <td class="f-txtr tdbg">端口号：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">部署路径：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
            <td class="f-txtr tdbg">端口号：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">协议类型：</td>
            <td class="f-txtl" colspan="3"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">协议类型：</td>
            <td class="f-txtl" colspan="3"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">部署路径：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
            <td class="f-txtr tdbg">端口号：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">部署路径：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
            <td class="f-txtr tdbg">端口号：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
        <tr>
            <td class="f-txtr tdbg">部署路径：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
            <td class="f-txtr tdbg">端口号：</td>
            <td class="f-txtl"><input type="text" /><span class="help-inline">协议类型不能为空</span></td>
        </tr>
    </tbody>
</table>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
//$('.btn-group .btn').click(function(){
//	$(this).addClass("active").siblings().removeClass("active");
//})
</script>
</body>
</html>
