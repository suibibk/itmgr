<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>后台管理系统</title>
<script src="${pageContext.request.contextPath }/blog/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/blog/js/md5.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/common/mobile.js" type="text/javascript"></script>
<style type="text/css">
*{margin:0px;padding:0px;}
body{
	background-color:#000;
	font-family:"微软雅黑";
}
#login{
	width:400px;
	height:260px;
	margin:0px auto;
	background-color:#fff;
	margin-top:150px;
	border-radius:5px;
	box-shadow:#666 5px 5px 0px 0px;
}
#title{
	width:100%;
	height:50px;
	background-color:#DAF9CA;
	text-align:center;
	line-height:50px;
	font-size:25px;
	border-radius: 5px 5px 0px 0px;
}
#tip{
	width:100%;
	height:30px;
	text-align:center;
	line-height:30px;
	font-size:18px;
	color:red;
}
.username_password{
	width:100%;
	height:100px;
	background-color:#ccc;
	font-size:18px;
}	
.div0{
	width:100%;
	height:50px;
}
.div1{
	float:left;
	width:20%;
	height:100%;
	text-align:right;
	line-height:50px;
}
.div2{
	float:right;
	width:79%;
	height:100%;
	text-align:left;
	line-height:50px;
}
.div2 input{
	width:80%;
	height:40px;
	border-radius:20px;
	padding-left:10px;
	font-size:18px;
}
#submit{
	width:100%;
	height:50px;
	text-align:center;
	line-height:50px;
}
#submit input{
	width:70px;
	height:40px;
	border-radius:20px;
	background-color:#DAF9CA;
}
</style>
<script type="text/javascript">
var one =true;//防止二次提交
function login(){
	if(!one){
		return;
	}
	one=false;
	$("#tip").html("");
	//获取用户名
	var username = $("#username").val();
	var password = $("#password").val();
	if(username==""){
		$("#tip").html("用户名不能为空");
		one=true;
		return;
	}
	if(password==""){
		$("#tip").html("密码不能为空");
		one=true;
		return;
	}
	password=hex_md5(password);
	$.ajax({ 
		url: "${pageContext.request.contextPath }/blog/blogManageAction!login.action",
		type: "POST",
		dataType:'json', 
		data : {"username":username,"password":password},
		success: function (data) {
			if(data.state=="success"){
				window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
			    return;
			}else{
				$("#tip").html("用户名或密码错误");
				one=true;
				return;
			}
		},
		error : function (data) {
		    $("#tip").html("系统异常，请稍后重试");
		    one=true;
			return;
		}
	});
}
</script>
</head>

<body>
	<div id="login">
		<div id="title">后台管理系统</div>
		<div id="tip"></div>
		<div id="username_password">
			<div class="div0">
				<div class="div1">用户名:</div>
				<div class="div2"><input type="text" placeholder="请输入用户名" id="username" name="username"/></div>
			</div>
			<div class="div0">
				<div class="div1">密码:</div>
				<div class="div2"><input type="password"  placeholder="请输入密码" id="password" name="password"/></div>
			</div>
		</div>
		<div id="submit"><input type="button" value="登录" onclick="login();"/></div>
	</div>
</body>
</html>