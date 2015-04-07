<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <SCRIPT type="text/javascript" >
var zTree;
	var demoIframe;

	var setting = {
		view: {
			dblClickExpand: false,
			showLine: false,
			selectedMulti: false
		},
		async:{
			enable:false,
			type:'get',
			url:""
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
			beforeClick: function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					return false;
				} else {
					demoIframe.attr("src",treeNode.file + ".html");
					return true;
				}
			}
		}
	};

	var zNodes =[
		{id:1, pId:0, name:"[core] 基本功能 演示", open:true},
		{id:101, pId:1, name:"最简单的树 --  标准 JSON 数据", file:"core/standardData"},
		{id:102, pId:1, name:"最简单的树 --  简单 JSON 数据", file:"core/simpleData"},
		{id:103, pId:1, name:"不显示 连接线", file:"core/noline"},
		{id:104, pId:1, name:"不显示 节点 图标", file:"core/noicon"},
		{id:105, pId:1, name:"自定义图标 --  icon 属性", file:"core/custom_icon"},
		{id:106, pId:1, name:"自定义图标 --  iconSkin 属性", file:"core/custom_iconSkin"},
		{id:107, pId:1, name:"自定义字体", file:"core/custom_font"},
		{id:115, pId:1, name:"超链接演示", file:"core/url"},
		{id:108, pId:1, name:"异步加载 节点数据", file:"core/async"},
		{id:109, pId:1, name:"用 zTree 方法 异步加载 节点数据", file:"core/async_fun"},
		{id:110, pId:1, name:"用 zTree 方法 更新 节点数据", file:"core/update_fun"},
		{id:111, pId:1, name:"单击 节点 控制", file:"core/click"},
		{id:112, pId:1, name:"展开 / 折叠 父节点 控制", file:"core/expand"},
		{id:113, pId:1, name:"根据 参数 查找 节点", file:"core/searchNodes"},
		{id:114, pId:1, name:"其他 鼠标 事件监听", file:"core/otherMouse"},

		{id:2, pId:0, name:"[excheck] 复/单选框功能 演示", open:false},
		{id:201, pId:2, name:"Checkbox 勾选操作", file:"excheck/checkbox"},
		{id:206, pId:2, name:"Checkbox nocheck 演示", file:"excheck/checkbox_nocheck"},
		{id:207, pId:2, name:"Checkbox chkDisabled 演示", file:"excheck/checkbox_chkDisabled"},
		{id:208, pId:2, name:"Checkbox halfCheck 演示", file:"excheck/checkbox_halfCheck"},
		{id:202, pId:2, name:"Checkbox 勾选统计", file:"excheck/checkbox_count"},
		{id:203, pId:2, name:"用 zTree 方法 勾选 Checkbox", file:"excheck/checkbox_fun"},
		{id:204, pId:2, name:"Radio 勾选操作", file:"excheck/radio"},
		{id:209, pId:2, name:"Radio nocheck 演示", file:"excheck/radio_nocheck"},
		{id:210, pId:2, name:"Radio chkDisabled 演示", file:"excheck/radio_chkDisabled"},
		{id:211, pId:2, name:"Radio halfCheck 演示", file:"excheck/radio_halfCheck"},
		{id:205, pId:2, name:"用 zTree 方法 勾选 Radio", file:"excheck/radio_fun"},

		{id:3, pId:0, name:"[exedit] 编辑功能 演示", open:false},
		{id:301, pId:3, name:"拖拽 节点 基本控制", file:"exedit/drag"},
		{id:303, pId:3, name:"用 zTree 方法 移动 / 复制 节点", file:"exedit/drag_fun"},
		{id:304, pId:3, name:"基本 增 / 删 / 改 节点", file:"exedit/edit"},
		{id:305, pId:3, name:"高级 增 / 删 / 改 节点", file:"exedit/edit_super"},
		{id:306, pId:3, name:"用 zTree 方法 增 / 删 / 改 节点", file:"exedit/edit_fun"},
		{id:307, pId:3, name:"异步加载 & 编辑功能 共存", file:"exedit/async_edit"},
		{id:308, pId:3, name:"多棵树之间 的 数据交互", file:"exedit/multiTree"},
	];

	$(document).ready(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		demoIframe = $("#testIframe");
		demoIframe.bind("load", loadReady);
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(zTree.getNodeByParam("id", 101));

	});

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
		h = demoIframe.height() >= maxH ? minH:maxH ;
		if (h < 530) h = 530;
		demoIframe.height(h);
	}

  //-->
  </SCRIPT>
 <TABLE border=0 height=600px align=left>
	<TR>
		<TD width=260px align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
		</TD>
		<TD width=770px align=left valign=top><IFRAME ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=600px SRC="core/standardData.html"></IFRAME></TD>
	</TR>
</TABLE>    