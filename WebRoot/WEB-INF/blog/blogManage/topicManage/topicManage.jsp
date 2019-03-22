<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>博客管理</title>
<link href="${pageContext.request.contextPath }/blog/css/blogManage.css?time=201705247" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/blog/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/ckeditor4.4.4/ckeditor.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/common/mobile.js" type="text/javascript"></script>
<style type="text/css">
#type2{
	background-color:#DAF9CA;
}
</style>
<script type="text/javascript">
	$(function(){
		init();
	});
	function init(){
		$(".add").hide();
		$(".edit").hide();
		$(".page").show();
		$("#topic").show();
		getAllSearchMenu();
		getSearchTopic("",0,1,"",0,15);
	}

	/*****添加主题******/
	function addTopic(){
		$("#topic").hide();
		$(".page").hide();
		$(".add").show();
		//初始化选项值
		$("#topic_menu select").empty();
		$("#topic_title input").val("");
		$("#topic_img input").val("");
		editor.setData("");
		//初始化获取所有菜单
		getAllMenu();
		
	}

</script>
</head>
<body>
	<div id="blogManage">
		<%@include file="/WEB-INF/blog/blogManage/common/top.jsp"%>
		<div id="content">
			<!----------------------查询显示------------------------>
						<!--------主题------->
			<%@include file="/WEB-INF/blog/blogManage/topicManage/topic.jsp"%>
			
			<!----------------------添加显示------------------------>
						<!--------添加主题------->
			<%@include file="/WEB-INF/blog/blogManage/topicManage/addTopic.jsp"%>
			
			<!----------------------修改显示------------------------>
						<!--------修改主题------->
			<%@include file="/WEB-INF/blog/blogManage/topicManage/editTopic.jsp"%>
		</div>
		<%@include file="/WEB-INF/blog/blogManage/common/page.jsp"%>
	</div>
</body>
</html>