<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#top{
	width:100%;
	height:75px;
}
#title{
	width:100%;
	heigth:50px;
	text-align:center;
	line-height:50px;
	font-size:20px;
}
#choice{
	width:100%;
	height:25px;
	lind-height:25px;
}
#type{
	float:left;
	width:90%;
	height:100%;
	text-align:center;
}
#login{
	float:right;
	width:10%;
	height:100%;
	text-align:center;
}
.choice_table{
	width:100%;
	height:100%;
}
.choice_table td{
	cursor:pointer;
	text-align:center;
	background-color:#ccc;
	border-radius:5px;
}

</style>
<script type="text/javascript">
	$(function(){
		$("#type table td").click(function(){
			$(this).css("background-color","#DAF9CA");
			$(this).siblings("td").css("background-color","#ccc");
		});
	});
function logon(){
		window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!logon.action';
}
function go(type){
		window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action?type='+type;
}

//因为发布是全部都一样的只要有ID即可，所以调用同一个方法
//type:0发布菜单 1发布主题 2发布回复
var three = true;
function visible(obj,id,type){
	var visible = $(obj).attr("value_id");
	if(!three){
		return;
	}
	three = false;
	$.ajax({ 
		 url: "${pageContext.request.contextPath }/blog/blogManageAction!visible.action",
         type: "POST", 
         data : {"id":id,"visible":visible,"type":type},
         success: function (data) {
        	 if(data=="logon"){
					alert("请先登录");
					window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
					return;
		      }else if(data=="error"){
		    	  alert("参数错误");
		    	  three=true;
		    	  return;
			  }else if(data=="success"){
		    	  var td = $(obj).parent().prev();
		    	  if(visible==1){
					  $(obj).attr("value_id","0");
					  $(obj).attr("value","取消");
					  td.html("已发布");
			      }else{
			    	  $(obj).attr("value_id","1");
			    	  $(obj).attr("value","发布");
			    	  td.html("未发布");
				   }
		    	  three=true;
			  }else{
				  alert("系统异常,请重试"); 
		          three=true;
		          return; 
			  }
         },
         error : function (data) {
        	 alert("系统异常,请重试"); 
             three=true;
             return; 
         }
 });
}

var four = true;
function deleteObject(obj,id,type){
	if(!window.confirm("删除后将不可恢复？")){
		return;
	}
	if(!four){
		return;
	}
	four = false;
	$.ajax({ 
		 url: "${pageContext.request.contextPath }/blog/blogManageAction!delete.action",
         type: "POST", 
         data : {"id":id,"type":type},
         success: function (data) {
        	 if(data=="logon"){
					alert("请先登录");
					window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
					return;
		      }else if(data=="error"){
		    	  alert("参数错误");
		    	  three=true;
		    	  return;
			  }else if(data=="success"){
		    	  var tr = $(obj).parent().parent();
		    	  tr.hide();
		    	  four=true;
			  }else{
				  alert("系统异常,请重试"); 
		          four=true;
		          return; 
			  }
         },
         error : function (data) {
        	 alert("系统异常,请重试"); 
             four=true;
             return; 
         }
 });
}
</script>
<div id="top">
	<div id="title">${sessionScope.user.nickname}的博客管理--》<a style="text-decoration: none;" href="${pageContext.request.contextPath}/dhp/dhpAction!dhp.action">【蛋黄盘】</a></div>
	<div id="choice">
		<div id="type">
				<table class="choice_table">
					<tr>
						<td id="type1" onclick="go(1)">菜单管理</td>
						<td id="type2" onclick="go(2)">主题管理</td>
						<td id="type3" onclick="go(3)">回复管理</td>
					</tr>
				</table>
		</div>
		<div id="login">
			<table class="choice_table">
				<tr>
					<td id="login1">${sessionScope.user.nickname}</td>
					<td id="login2" onclick="logon();">注销</td>
				</tr>
			</table>
		</div>
	</div>
</div>

