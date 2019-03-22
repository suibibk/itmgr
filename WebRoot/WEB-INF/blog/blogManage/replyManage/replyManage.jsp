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
#type3{
	background-color:#DAF9CA;
}
</style>
<script type="text/javascript">
	$(function(){
		init();
	});
	function init(){
		$(".page").show();
		$(".edit").hide();
		$(".add").hide();
		$("#reply").show();
		//查询回复
		getSearchReply("",1,"",0,15);
	}


</script>
</head>
<body>
	<div id="blogManage">
		<%@include file="/WEB-INF/blog/blogManage/common/top.jsp"%>
		<div id="content">
			<!----------------------查询显示------------------------>
						<!--------回复------->
			<%@include file="/WEB-INF/blog/blogManage/replyManage/reply.jsp"%>
			
			<!----------------------添加显示------------------------>
			<!----------------------添加显示------------------------>
						<!--------添加主题------->
			<%@include file="/WEB-INF/blog/blogManage/replyManage/addReply.jsp"%>
						<!--------修改回复------->
			<%@include file="/WEB-INF/blog/blogManage/replyManage/editReply.jsp"%>
		</div>
		<%@include file="/WEB-INF/blog/blogManage/common/page.jsp"%>
	</div>
</body>
</html>