<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>博客管理</title>
<link href="${pageContext.request.contextPath }/blog/css/blogManage.css?time=201705249" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/blog/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/ckeditor4.4.4/ckeditor.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/common/mobile.js" type="text/javascript"></script>
<style type="text/css">
#type1{
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
		$("#menu").show();
		getMenuTypeNumSearch();
		getSearchMenu("",0,1,"",0,15);
	}
	
	/*****添加菜单******/
	function addMenu(){
		//1、隐藏当前menu,显示添加菜单div
		$("#menu").hide();
		$(".page").hide();
		$("#editMenu").hide();
		$("#addMenu").show();
		//2、初始化选项值
		$("#menu_type1 select").empty();
		$("#menu_type2 select").empty();
		$("#menu_sort select").empty();
		$("#name").val("");
		$("#remark").val("");
		$("#file").val("");
		//3、初始化addMenu div
		getMenuTypeNum();
	}
</script>
</head>
<body>
	<div id="blogManage">
		<%@include file="/WEB-INF/blog/blogManage/common/top.jsp"%>
		<div id="content">
			<!----------------------查询显示------------------------>
						<!--------菜单------->
			<%@include file="/WEB-INF/blog/blogManage/menuManage/menu.jsp"%>

			<!----------------------添加显示------------------------>
						<!--------添加菜单------->
			<%@include file="/WEB-INF/blog/blogManage/menuManage/addMenu.jsp"%>

			
			<!----------------------修改显示------------------------>
						<!--------修改菜单------->
			<%@include file="/WEB-INF/blog/blogManage/menuManage/editMenu.jsp"%>
		</div>
		<%@include file="/WEB-INF/blog/blogManage/common/page.jsp"%>
	</div>
</body>
</html>