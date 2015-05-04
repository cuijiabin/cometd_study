<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-cn">
<head>
<link rel="stylesheet" href="/jsplugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/jsplugin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
 <SCRIPT >

var zTree;
	var demoIframe;
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: false,
			selectedMulti: false,
			open:true
		},
		async:{
			enable:false,  //是否启用异步加载
			type:'get',
			url:"/messageDaily/main.action"
		},
		data: {
			simpleData: {
				enable:true,  
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
		
		 onClick:zTreeOnClick
		}
	};

	var str ='${json}';
	var zNodes = eval('('+ str +')');
	$(document).ready(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		demoIframe = $("#testIframe");
		demoIframe.bind("load", loadReady);
		zTree = $.fn.zTree.getZTreeObj("tree");
		var node = zTree.getNodeByParam("id", "${messageType.id}");
		zTree.selectNode(node,false);

		zTree.expandAll(true);
		//zTree.selectNode(zTree.getNodeByParam("id", 101));
		//alert(3);
	});

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
		h = demoIframe.height() >= maxH ? minH:maxH ;
		if (h < 530) h = 530;
		demoIframe.height(h);
	}
	
	 //树单击事件显示详细信息
	function zTreeOnClick(event, treeId, treeNode) {
		var url = "/messageDaily/find.action";
		var data = {
			"id" : treeNode.id,
			"typeId" :treeNode.typeId,
		};
		id=treeNode.id;    
		title = treeNode.name;
		sortId = treeNode.sortId;
		pId = treeNode.pId;
		status = treeNode.status;
		typeId  =treeNode.typeId;
		$.ajax({
			type : "get",
			url : url,
			data : data,
			contentType : "application/json; charset=utf-8",
			dataType : "html",
			success : function(data) {
				 $("#table_data").html(data);
			},
			error : function(msg) {
				alert("error");
			}
		});

	}


  </SCRIPT>
 </head>
<body style="background-color:transparent">
 <TABLE border=0 height=600px align=left>
	<TR>
		<TD width=260px align=left valign=top >
			<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
		</TD>
		<TD width=770px align=left valign=top><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=600px SRC=""></IFRAME></TD>
	</TR>
</TABLE>  
</body>
</html> 