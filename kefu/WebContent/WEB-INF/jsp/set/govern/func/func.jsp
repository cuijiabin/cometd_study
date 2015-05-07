<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>  
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="/css/app.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/jsplugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="m-crumb">
    <ul class="f-cb">
        <li><b>位置：</b></li>
        <li><a href="#">设置中心</a></li>
        <li><i>&gt;</i><a href="#">管理设置</a></li>
        <li><i>&gt;</i><a href="#">角色管理</a></li>
        <li><i>&gt;</i>权限配置</li>
    </ul>
</div>
<div style="margin:50px">
<h4>角色： ${role.name}</h4><input type="hidden" id="roleId" name="roleId" value="${role.id}"/><input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
<input type="hidden" id="strings" name="strings" value="${strs}"/>
    	<!-- 表格有边框 -->
        <table class="table m-table c-wdat f-mar0">
            <tbody>
                <tr>
                   <td class="c-wd150 f-vat">
					 
                    	<ul class="e_tit c-bor">
                            <c:forEach items="${list}" var="dept">
                        	     <li id="dept${dept.id}" name="deptcss" class="" value="${dept.id}" onclick="findPermit(this)">
                            	    <a><b class="c-colf00"></b>${dept.name}</a>
                                 </li>
					        </c:forEach>
                        </ul>
                    </td>
                    <td>
                    <div class="g-cnt">
                    <table class="table table-bordered m-table c-wdat f-mar0">
							<tr>
								<td width=260px align=left valign=top>
									<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
								</td>
								<TD width=0px align=left valign=top></td>
							</tr>
					</table>
					<c:if test="${status==3}"><button class="btn btn-primary btn-small" onclick="saveFunc()">保存</button></c:if>
					</div>
                    </td>
                    <td><src href="/tree.jsp"/></td>
                </tr>
            </tbody>
        </table>
   </div>	
</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<script type="text/javascript" src="/jsplugin/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
var zTree;
var demoIframe;
var setting = {
	view:{
		dblClickExpand: false,
		showLine: false,
		selectedMulti: false,
		open:true
	},
	async:{
		enable:false,
	},
	check:{
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "s" }
	},
	data: {
		key: {
			url: "xUrl"
		},
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: ""
		}
	},
	callback: {
		beforeClick: function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);
				return false;
			} 
		}
	}
};

var str ='${json}';
var zNodes = eval('('+ str +')');
var strs = $("#strings").val();
var ids=strs.split(",");
for (var i = 0; i < zNodes.length; i++) {
	for (var t = 0; t < ids.length; t++) {
		var yy=parseInt(ids[t]);
		if(zNodes[i].id==yy){
			zNodes[i].checked = true;
			break;
		}
	}
}
$(document).ready(function(){
	var t = $("#tree");
	t = $.fn.zTree.init(t, setting, zNodes);
	demoIframe = $("#testIframe");
	demoIframe.bind("load", loadReady);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.expandAll(true);
});

function loadReady() {
	var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
	htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
	maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
	h = demoIframe.height() >= maxH ? minH:maxH ;
	if (h < 530) h = 530;
	demoIframe.height(h);
}

function saveFunc(){
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var nodes = treeObj.getCheckedNodes(true);
	var ids="";
	for (var i = 0; i < nodes.length; i++) {
		 ids+=nodes[i].id+",";
	    }
	  var data = {
		    "ids":ids,
		    "roleId":$("#roleId").val(),
		    "deptId":$("#deptId").val()
	  };
	$.ajax({
		url:"/function/saveFunc.action",
		type:"post",
		data:data,
		dataType:"json",
		success:function(data) {
			alert(data.msg);
		},
		error : function(data) {
			alert("出现错误,请重试！");
		}
	});
	
}
if($("#deptId").val()!=undefined){
	var deptId=$("#deptId").val();
	$("li").removeClass();
	$("#dept"+deptId+"").addClass("on");
}
function findPermit(obj){
	var val=obj.value;
     location.href="/function/permit.action?deptId="+val+"&roleId="+$("#roleId").val()+"&status=3";
}
</script>

</html>
