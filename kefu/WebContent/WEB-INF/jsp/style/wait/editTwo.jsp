<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript"
	src="/jsplugin/kkpager/src/kkpager.min.js"></script>
<script type="text/javascript" src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>	

 <div class="f-txtr">
     <button type="submit" class="btn btn-primary" onclick="addTwo();">添加</button>
 </div>
 <table class="table table-bordered table-striped table-hover m-table c-wdat f-mar0 f-mtm">
     <thead>
         <tr>
             <th>二级菜单</th>
             <th>链接地址</th>
             <th>操作</th>
         </tr>
     </thead>
     <tbody>
		<c:forEach var="z" items="${zList}">
			<tr>
	            <td id="zname${z.id }">${z.name}</td>
	            <td id="zurl${z.id }">${z.linkUrl}</td>
	            <td>
	            	<a href="#" onClick="toUpdateTwo(${z.id})">修改</a>&nbsp;
	            	<a href="#" onClick="delTwo(${z.id},${pId })">删除</a>
	            </td>
			</tr>
		</c:forEach>
     </tbody>
 </table>
 <div class="f-txtr f-mtw">
     <button type="submit" class="btn" onclick="cancel();">关闭</button>
 </div>

<script type="text/javascript">

var api = frameElement.api;//调用父页面数据  
var W = api.opener;//获取父页面对象      
var pId = '${pId }';
var cDG2;

//添加二级菜单
function addTwo(){
	if(pId==''){
		W.$.dialog.alert('请先选择一级菜单!',function(){
			return;
		});
	}else{
		cDG2 = W.$.dialog({
			id:'addTwo',
			content:'url:/waitList/addTwo.action?pId='+pId+'&styleId='+'${styleId}',
			lock:true,
			parent:api,
			width: 400,height: 220,
			title:'添加二级菜单'
		});
	}
	
	
}

//添加回调
function addTwoCallback(pId){
	cDG2.close();//关闭弹出窗
	show(pId);//刷新
}

//删除二级菜单
function delTwo(id,pId){
	W.$.dialog.confirm('你确定要删除吗？', function(){
		var url="/waitList/delete.action";
		$.ajax({
		    type: "post",
		    url: url,
		    data: {"id":id},
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		W.$.dialog.alert('操作成功!',function(){
		    			show(pId);
		    		});
		    	}else{
		    		W.$.dialog.alert(data.msg);
		    	}
		    },
		    error: function (msg) {
		    	W.$.dialog.alert(msg);
		    }
		});
	});
}

//更新
function toUpdateTwo(id){
	var td1 = $("#zname"+id); //获取td
	var td2 = $("#zurl"+id); //获取td
	
	var txtOldName = td1.text(); //td内容
	var txtOldUrl = td2.text(); //td内容
	
	var input1 = $("<input maxlength='20' type='text' value='" + txtOldName + "'/>");
	var input2 = $("<input maxlength='100' type='text' value='" + txtOldUrl + "'/>");
	td1.html(input1); //td修改为input
	td2.html(input2); //td修改为input 
	input1.focus();//获取焦点 
	//文本框失去焦点后提交内容，重新变为文本 
	input1.blur(function(){ //名称
		var newtxt = input1.val(); 
		if($.trim(newtxt) == ''){
			W.$.dialog.alert("名称不能为空!");
			td1.html(txtOldName);
			input2.focus();//url获取焦点
   			return false;
		}
		//判断文本有没有修改 
		if (newtxt != txtOldName) {
			var postData = {"id":id,"name":newtxt,"pId":pId,"linkUrl":txtOldUrl};
			$.ajax({
			    type: "post",
			    url: "/waitList/validate.action",
			    dataType: "json",
			    data : postData,
			    async:false,
			    success: function (data) {
			    	if(data.result==0){
			    		saveTwoName(postData,td1,txtOldName);
			    		td1.html(newtxt);
			    		txtOldName = newtxt;//更新
			    	}else{
			    		W.$.dialog.alert(data.msg,function(){
			    			td1.html(txtOldName);
			    		});
			    	}
			    },
			    error: function (msg) {
			    	W.$.dialog.alert(data.msg,function(){
		    			td1.html(txtOldName);
		    		});
			    }
			});
		}else{
			td1.html(txtOldName);
		}
		input2.focus();//url获取焦点
	});
	
	input2.blur(function(){ //url失去焦点
		var newtxt = input2.val(); 
		//判断文本有没有修改 
		if (newtxt != txtOldUrl) {
			var data = {"id":id,"linkUrl":newtxt,"pId":pId,"name":txtOldName};
			$.ajax({
			    type: "post",
			    url: "/waitList/save.action",
			    dataType: "json",
			    data : data,
			    async:false,
			    success: function (data) {
			    	if(data.result==0){
			    		td2.html(newtxt);
			    	}else{
			    		W.$.dialog.alert(data.msg,function(){
			    			td2.html(txtOldUrl);
			    		});
			    	}
			    },
			    error: function (msg) {
			    	W.$.dialog.alert(data.msg,function(){
		    			td2.html(txtOldUrl);
		    		});
			    }
			});
		}else{
			td2.html(txtOldUrl);
		}
	});
}

//保存
function saveTwoName(postData,td,txt){
	$.ajax({
	    type: "post",
	    url: '/waitList/save.action',
	    data: postData,
	    dataType: "json",
	    success: function (data) {
	    	if(data.result==0){
	    	}else{
		    	W.$.dialog.alert(data.msg,function(){
//		    		input.focus();
	    			td.html(txt);
	    		});
	    	}
	    },
	    error: function (msg) {
	    	W.$.dialog.alert(data.msg,function(){
//	    		input.focus();
    			td.html(txt);
    		});
	    }
	});
}

//关闭
function cancel(){
	api.close();
}

</script>
