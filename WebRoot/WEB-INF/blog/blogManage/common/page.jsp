<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.page{
     width:100%; 
	 height:35px;
	 text-align:center;
	 line-height:35px;
	 font-size:15px;
}
.page span input{
	height:23px;
	width:50px;
	font-size:15px;
	font-family:"微软雅黑";
	cursor:pointer;
	border-radius:5px;
	background-color:#fff;
}
.page #now_page{
	font-weight:bold;
}
.page span #page_num{
     cursor:auto;
}
</style>
<script type="text/javascript">
var type="${requestScope.type}";
function pageUp(){
	var page = parseInt($("#now_page").html());
	if(page==1){
		return;
	}else{
		if(type=="1"){
			searchMenu(page-2,15);
		}
		if(type=="2"){
			searchTopic(page-2,15);
		}
		if(type=="3"){
			searchReply(page-2,15);
		}
		window.scrollTo( 0, 0 );
	}
	
}
function pageDown(){
	var page = parseInt($("#now_page").html());
	var all_page = parseInt($("#all_page").html());
	if(page==parseInt(all_page)){
		return;
	}else{
		if(type=="1"){
			searchMenu(page,15);
		}
		if(type=="2"){
			searchTopic(page,15);
		}
		if(type=="3"){
			searchReply(page,15)
		}
		
		window.scrollTo( 0, 0 );
	}
}
function toPage(){
	var page = parseInt($("#now_page").html());
	var all_page = parseInt($("#all_page").html());
	if($("#page_num").val()==""){
		return;
	}
	var page_num = parseInt($("#page_num").val());
	if(page_num<1||page_num==page||page_num>parseInt(all_page)){
		return;
	}else{
		if(type=="1"){
			searchMenu(page-1,15);
		}
		if(type=="2"){
			searchTopic(page-1,15)
		}
		if(type=="3"){
			searchReply(page-1,15)
		}
		window.scrollTo( 0, 0 );
	}
}
</script>
<div class="page">
	<span>总数:</span><span id="all_count">0</span>
	<span id="now_page">0</span>/<span id="all_page">0</span><span>页</span>
	<span>
		<input type="button" value="上一页" onclick="pageUp();"/>
		<input type="button" value="下一页" onclick="pageDown();"/>
		<input type="text" value="" id="page_num"/>
		<input type="button" value="跳转" onclick="toPage();"/>
	</span>
</div>
