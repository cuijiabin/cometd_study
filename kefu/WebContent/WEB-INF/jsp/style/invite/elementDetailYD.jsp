<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="/WEB-INF/xiaoma.tld" prefix="xiaoma" %> 
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript"
	src="/jsplugin/lhgdialog/lhgdialog.min.js?skin=iblue"></script>
<script type="text/javascript"	src="/jsplugin/form/jquery-form.js"></script>	
<script type="text/javascript" src="/js/app.js"></script>	
<!-- 表格有边框 -->
<form id="mainForm" enctype="multipart/form-data" method="post">
	<input type="hidden" id="id" name="id" value="${inviteElement.id }"/>
	<input type="hidden" id="inviteId" name="inviteId" value="${inviteElement.inviteId }" />
	<table class="table table-bordered m-table c-wdat f-mar0">
		<tbody>
			<tr>
				<td class="f-txtl" colspan="2"><b class="c-colf00">（${inviteElement.sortId }）</b> <span id="spanName">${inviteElement.name }</span>
					<c:if test="${inviteElement.id != null && inviteElement.sortId !=1 }">
						<button type="button" class="btn btn-primary btn-small" onclick="updateName(${inviteElement.id })">修改名称</button>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="c-wd100">大小</td>
				<td class="f-txtl"> 
					宽<input class="c-wd50" type="text" id="width" name="width" value="${inviteElement.width }" maxlength="4" />%
					高 <input class="c-wd50" type="text" id="height" name="height" value="${inviteElement.height }" maxlength="4" />px
					<span class="help-inline c-clred">注：如为空,则宽为80%,高为背景图高度</span>
				</td>
			</tr>
			<tr>
				<td class="c-wd100">位置</td>
				<td class="f-txtl">
					距左 <input class="c-wd50" type="text" id="siteLeft" name="siteLeft" value="${inviteElement.siteLeft }" maxlength="4" 
						<c:if test="${inviteElement.sortId ==1}"> readonly="readonly" </c:if> />px
					距顶<input class="c-wd50" type="text" id="siteTop" name="siteTop" value="${inviteElement.siteTop }" maxlength="4" 
						<c:if test="${inviteElement.sortId ==1}"> readonly="readonly" </c:if> />px
				</td>
			</tr>
			<tr>
				<td class="c-wd100">背景图片</td>
				<td class="f-txtl">
					<input type="file" style="width:170px;height:initial;line-height:normal;" id="groupFile" name="groupFile" />
				</td>
			</tr>
			<tr>
				<td class="c-wd100">层叠顺序</td>
				<td class="f-txtl">
					<input class="c-wd50" type="text" id="level" name="level" value="${inviteElement.level }" maxlength="4" /> 
					<span class="help-inline c-clred">注：数值大的覆盖数值小的</span>
				</td>
			</tr>
			<tr>
				<td class="c-wd100">操作行为</td>
				<td class="f-txtl">
					<xiaoma:select name="operationType" dictName="d_ele_operation" value="${inviteElement.operationType }" />
				</td>
			</tr>
			<tr>
				<td class="c-wd100">自定义链接</td>
				<td class="f-txtl">
					<xiaoma:select name="openType" dictName="d_ele_openType" value="${inviteElement.openType }" />
					<input class="c-wd300" type="text" id="openUrl" name="openUrl" value="${inviteElement.openUrl }" maxlength="100" placeholder="http://" />
				</td>
			</tr>
		</tbody>
	</table>
</form>

<div class="m-query-hd">
	<c:if test="${inviteElement.id != null }">
		<button type="button" class="btn btn-primary btn-small" onclick="saveDetail()">保存</button>
    	<button type="button" class="btn btn-primary btn-small" onclick="refreshDiv()">刷新预览图</button>
	</c:if>
</div>

<script type="text/javascript">

//保存
function saveDetail(){
	var msg = validate();
	if(msg!=''){
		$.dialog.alert(msg);
		return;
	}
	var path = "/inviteElement/saveYD.action";  
    $('#mainForm').attr("action", path).submit();
}

//校验
function validate(){
	var msg = '';
	if(!isNaturalNum($("#width").val())){
		msg += '宽度请填写大于等于0的整数! </br>';
	}
	if(!isNaturalNum($("#height").val())){
		msg += '高度请填写大于等于0的整数! </br>'
	}
	if(!isNaturalNum($("#siteLeft").val())){
		msg += '距左距离请填写大于等于0的整数! </br>'
	}
	if(!isNaturalNum($("#siteTop").val())){
		msg += '距顶距离请填写大于等于0的整数! </br>'
	}
	if(!isNaturalNum($("#level").val())){
		msg += '层叠顺序请填写大于等于0的整数! </br>'
	}
	
	//校验文件类型
	var filepath=$("input[name='groupFile']").val();
	if(filepath!=''){
	    var extStart=filepath.lastIndexOf(".");
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
	    	msg += '图片限于bmp,png,gif,jpeg,jpg格式! </br>'
	    }
	}
	return msg;
}

//修改元素名称
function updateName(id){
	$.dialog({content:'url:/inviteElement/editName.action?id='+id,
		id: 'editName',
		width: 400,height: 180,
		title:'修改名称'
	});
}

//修改元素名称回调
function editCallback(id,newName){
	$.dialog({id:'editName'}).close();
	$("#spanName").html(newName);
	$("#listName"+id).html(newName);
}

//刷新div
function refreshDiv(){
	var msg = validate();
	if(msg!=''){
		$.dialog.alert(msg);
		return;
	}
    $("#mainForm").ajaxSubmit({
        type:'post',
        url:'/inviteElement/refreshPvwDiv.action',
        dataType : 'json',
        success:function(data){
        	if(data.result==0){
        		$("#divView").html(data.msg);
	    	}else{
	    		$.dialog.alert(data.msg);
	    	}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        	$.dialog.alert('error');	
        }
    });
}

</script>