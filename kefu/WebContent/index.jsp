<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="author" content="dobao">
<title>登录--客服信息管理系统</title>
<link rel="icon" href="favicon.ico">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.google.v2.3.2.css" rel="stylesheet" type="text/css">
<link href="css/app.css" rel="stylesheet" type="text/css">
</head>

<body style="background:#dfdfdf;">
<div class="m-login">
		<div class="login_form">
			<div class="m-login-info" id="err"></div>
			<div class="form_info">
				<div class="field">
					<label>用户名：</label>
					<input type="text" name="loginName" id="loginName" class="text" size="20">
				</div>
				<div class="field">
					<label>密　码：</label>
					<input type="password" name="password" id="password" class="text" size="20">
				</div>
				<div class="field">
					<label>验证码：</label>
					<input type="text" class="text text1" name="yzm" id="yzm"size="10">
                    <cite class="yzm"><img src="image.jsp" height="15" id="pic" name="pic"/></cite>  <a href="javascript:reloadImage('image.jsp')">看不清</a><br/>
				</div>
				<div class="field">
					<label></label>
					<button class="button" style="margin-left:50px;_margin-left:48px" onclick="javascript:login();"></button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript">
	function login(){
		var data = {
			"loginName":$("#loginName").val(),
			"password":$("#password").val(),
			"yzm":$("#yzm").val()
		};
		//alert(111);
		$.ajax({
			type: "post",
		    url: "/user/login.action",
		    data: data,
		    dataType: "json",
		    success: function (data) {
		    	if(data.result==0){
		    		window.location="/user/main.action";
		    	}else{
		    		$("#err").text(data.msg);
		    	}
		    },
		    error: function (data) {
		    	$("#err").text("出现错误,请重新登录");
		    }
		});
	}
	
	  function reloadImage(imgurl){
          var getimagecode=document.getElementById("pic");
          getimagecode.src= imgurl + "?id=" + Math.random();
      }
</script>
</body>
</html>

    