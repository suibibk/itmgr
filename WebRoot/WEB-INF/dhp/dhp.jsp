<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <title>蛋黄盘</title>
	<link href="" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath }/blog/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/common/mobile.js" type="text/javascript"></script>
	<style type="text/css">
	*{margin:0px;padding:0px;font-size: 15px;}
	body{
		background-color:#000;
		font-family:"微软雅黑";
	}
	.operation{
		width:100%;
		height:40px;
		line-height:40px;
		text-align: center;
		border-bottom: 1px solid #333;
		position: fixed;
		/* top:40px;
		left:5%;
		right:5%; */
		background-color:#fff;
	}
	.operation span{
		display: inline-block;
		color: #3b8cff;
		height:25px;
		line-height:25px;
		border: 1px solid #c0d9fe;
   		border-radius: 5px;
    	width:100px;
    	text-align: center;
	}
	.operation span:hover{cursor: pointer;}
	.path{
		width:100%;
		word-wrap:break-word;
		position: fixed;
		top:40px;
		/* left:5%;
		right:5%; */
		background-color:#fff;
		line-height: 40px;
		height: 40px;
		font-size:14px;
		text-align:center;
		/* padding-left: 40px;
		box-sizing:border-box; */
		overflow:hidden;
		text-overflow:ellipsis;
		white-space:nowrap;
	}
	.path span{
		display: inline-block;
		color: #3b8cff;
		height:25px;
		line-height:25px;
	}
	.path span:hover{cursor: pointer;}
	.dhp{
		width:100%;
		/* left:5%;
		right:5%; */
		background-color:#fff;
		top:80px;
		bottom:0px;
		position: fixed;
	}
	.content{
		width:100%;
		height:100%;
		text-align: center;
		overflow: auto;
		/* padding-left: 20px; */
		box-sizing:border-box;
		-webkit-overflow-scrolling : touch;
	}
	.files{
		display:inline-block;
		margin-right: 5px;
		margin-left: 5px; 
	}
	.file{
		width:100%;
		height:100%;
		text-align: center;
	}
	 .file:HOVER{background:#eee; cursor: pointer;} 
	/*图片大小是56px*/
	.file img{
		 display: inline-block; 
		 margin-top: 10px;
	}
	.file_name{
		height:30px;
		width:115px;
		text-align: center;
		font-size: 14px;
		overflow:hidden;
		text-overflow:ellipsis;
		white-space:nowrap;
	}
	
	/*****弹出窗*****/
	.cover{
		 background: rgba(0, 0, 0, 0.5);
  		 position: fixed;
  		 z-index: 1;
  	     left: 0;
         bottom: 0;
         width: 100%;
         height: 100%;
         display: none;
	}
	/*******上传文件*******/
	.upload{
		position: fixed;
		top:40%;
		background: #fff;
		height:100px;
		z-index: 2;
		border-radius: 5px;
		display:none;
		left: 0px;
    	right: 0px;
    	margin: auto;
    	text-align: center;
    	width: 200px;
	}
	.file_choice{
		width:100%;
		height:50px;
		line-height: 50px;
		text-align:center;
		border-bottom: 1px solid #eee;
	}
	.file_choice input{
		width:90%;
	}
	.file_button{
		width:100%;
		height:49px;
		line-height: 49px;
		text-align:center;
	}
	.file_button span{
		display: inline-block;
		color: #3b8cff;
		height:25px;
		line-height:25px;
		border: 1px solid #c0d9fe;
   		border-radius: 5px;
   		padding-left: 5px;
   		padding-right: 5px;
   		margin-right: 5px;
   		margin-left: 5px;
   		box-sizing:border-box;
	}
	.msg{
	    	height:30px;
	    	width:100%;
	    	color: #3b8cff;
	    	display:none;
	    	text-align: center;
	    }
	/*******新建文件夹*******/
	.wjj{
		position: fixed;
		top:40%;
		background: #fff;
		height:100px;
		z-index: 2;
		border-radius: 5px;
		display: none;
		left: 0px;
    	right: 0px;
   	 	margin: auto;
    	text-align: center;
   		width: 200px;
	}
	.wjj_choice{
		width:100%;
		height:50px;
		line-height: 50px;
		text-align:center;
		border-bottom: 1px solid #eee;
	}
	.wjj_choice input{
		width:90%;
		height:30px;
		border-radius: 1px;
		text-indent: 3px;
	}
	.wjj_button{
		width:100%;
		height:49px;
		line-height: 49px;
		text-align:center;
	}
	.wjj_button span{
		display: inline-block;
		color: #3b8cff;
		height:25px;
		line-height:25px;
		border: 1px solid #c0d9fe;
   		border-radius: 5px;
   		padding-left: 5px;
   		padding-right: 5px;
   		margin-right: 5px;
   		margin-left: 5px;
   		box-sizing:border-box;
	}
	
	.wjj_button span:hover{cursor: pointer;}
	</style>
	<script type="text/javascript">
	$(function(){
		getDHPs("蛋黄盘",'${requestScope.dhpId}',2);
	});
	//去上传文件
	function toUpload(){
		$(".cover").show();
		$(".upload").show();
	}
	//去创建文件夹
	function toCreate(){
		$(".cover").show();
		$(".wjj").show();
	}
	//去注销
	function toLogon(){
		window.location.href='${pageContext.request.contextPath}/dhp/dhpAction!logon.action';
	}
	//去关闭
	function closeAll(){
		//关闭
		$(".cover").hide();
		$(".upload").hide();
		$(".wjj").hide();
		$("#file").val("");
		$("#wjj").val("");
		
	}
	function uploadFile(){
		alert("上传文件成功");
		//执行刷新当前目录
		closeAll();
		
	}
	
	function toGetDHPs(obj,dhpId){
		//将这个同级下面的目录全部删除
		$(obj).nextAll().remove();
		getDHPs('',dhpId,0);
	}
	var one=true;
	function createWjj(){
		if(!one){
			return ;
		}
		one=false;
		var dhpId =$("#dhpId").val();
		var wjj= $("#wjj").val();
		$.ajax({ 
 			 url: "${pageContext.request.contextPath }/dhp/dhpAction!createWjj.action",
 	         type: "POST", 
 	         //async: false,
 	         dataType:'json',
 	         data : {"dhpId":dhpId,"wjj":wjj},
 	         success: function (data) {
 	        	 if(data.state=="logon"){
 	        		window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
 	        	 }else if(data.state=="error"){
 	        		 alert("网络异常");
 	        	 }else if(data.state=="noWjj"){
 	        		alert("没有权限");
 	        	 }else if(data.state=="noName"){
 	        		alert("请输入文件夹的名称");
  	        	 }else if(data.state=="success"){
  	        		getDHPs('',dhpId,0);
  	        		closeAll();
  	        	 }
 	        	one=true;
 	         },
 	         error : function (data) {
 	           	alert("网络异常");
 	           one=true;
 	         }
 	 	});
		//执行刷新当前目录
		
	}
	var two = true;
	function uploadFile() {  
		//if(!two){
		//	return;
		//}
		//two=false;
	    var formData = new FormData($("#uploadForm")[0]);
	    $("#uploadFile").html("正在上传...");
	    closeAll();
	    $.ajax({  
	          url: '${pageContext.request.contextPath }/file/fileAction!uploadFile.action' ,  
	          type: 'POST',  
	          data: formData,  
	          async: true,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (data) { 
	        	  $("#uploadFile").html("上传文件");
		          if(data=="logon"){
						alert("请先登录");
						window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
						return;
			      }
			      if(data=="file_error"){
			    	  alert("上传失败");
			    	  $("#uploadFile").html("上传文件");
			    	//  two=true;
					  return;
				  }
			      if(data=="file_size"){
			    	  alert("上传文件过大，文件超过1.5G");
			    	  $("#uploadFile").html("上传文件");
			    	//  two=true;
					  return;
				  }
		          if(data=="success"){
						//初始化获取所有菜单
						getDHPs('',$("#dhpId").val(),0);
						$("#uploadFile").html("上传文件");
						//two=true;
						return;
			      } 
	          },  
	          error: function (data) { 
	        	  $("#uploadFile").html("上传文件");
	        	  alert("上传失败");
	              //two=true;
	              return; 
	          }  
	     });  
	 } 
	function toDown(dhpId){
		window.location.href='${pageContext.request.contextPath}/dhp/downLoadAction!downLoad.action?dhpId='+dhpId;
	}
	  /**
	 *获取快速通道
     */
     function getDHPs(name,supId,status){
    	$(".msg").show();
    	$(".msg").html("正在查询中...");
    	$(".content").html("");
 		$.ajax({ 
 			 url: "${pageContext.request.contextPath }/dhp/dhpAction!getDHPs.action",
 	         type: "POST", 
 	         //async: false,
 	         dataType:'json',
 	         data : {"supId":supId},
 	         success: function (data) {
 	        	 if(data.state=="logon"){
 	        		window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
 	        		return;
 	        	 }
 	        	//将supId放在input中
 	        	$("#dhpId").val(supId);
 	        	 if(status==1){
 	 	        	var path = '<span onclick="toGetDHPs(this,\''+supId+'\',0)">>>'+name+'</span>';
 	 	        	 $(".path").append(path);
 	        	 }
 	        	 if(status==2){
 	 	        	var path = '<span onclick="toGetDHPs(this,\''+supId+'\',0)">'+name+'</span>';
 	 	        	 $(".path").append(path);
 	        	 }
 	        	 if(data.state=="error"){
 	        		$(".msg").html("未查询到到记录");
 	        		return;
 	        	 }
 	        	 var str='';
 	        	 for(var i=0;i<data.length;i++){
 	        		str+='<div class="files">';
 	        		str+='<div class="file"';
 	        		if(data[i].type=="1"){//普通文件夹
 	        			str+=' onclick="getDHPs(\''+data[i].name+'\',\''+data[i].dhpId+'\',1)"';
 	        		}
 	        		if(data[i].type=="2"){
 	        			str+=' onclick="toDown(\''+data[i].dhpId+'\')"';
 	        		}
 	        		str+='>';
 	        		var type=data[i].subType;
 	        		str+='<img src="${pageContext.request.contextPath}/dhp/images/'+data[i].subType+'.png">';
 	        		str+='<div class="file_name" title="'+data[i].name+'">'+data[i].name+'</div>';
 	        		str+='</div>';
 	        		str+='</div>';
	 	 	     }
 	        	 $(".content").html(str);
 	        	 $(".msg").hide();
 	         },
 	         error : function (data) {
 	           	//alert("网络异常");
 	        	$(".msg").html("未查询到到记录");
 	         }
 	 	});
 	}
	</script>
</head>
<body>
	<!--头部-->
	<%-- 欢迎来到蛋黄盘
	<div><a href="${pageContext.request.contextPath}/dhp/downLoadAction!downLoad.action">下载</a></div> --%>
	<!--底部-->
	<div class="operation"><span id="uploadFile" onclick="toUpload()" title="最大1.5G">上传文件</span><span onclick="toCreate()">新建文件夹</span><span onclick="toLogon()">注销</span></div>
	<div class="path"></div>
	<div class="dhp">
		<div class="msg">正在查询中...</div>
		<div class="content">
			<%-- <div class="files">
				<div class="file">
					<img src="${pageContext.request.contextPath}/dhp/images/wjj.png">
					<div class="file_name">哈哈哈</div>
				</div>
			</div>
			
			<div class="files">
				<div class="file">
					<img src="${pageContext.request.contextPath}/dhp/images/video.png">
					<div class="file_name">哈哈哈</div>
				</div>
			</div> --%>
		</div>
	</div>
	<!-- 弹出框 -->
	<div class="cover"></div>
	<div class="upload">
		<div class="file_choice">
		<form id="uploadForm">
		<!-- 当前目录的级别 -->
			<input type="hidden" id="dhpId" name="dhpId" value="0">
			<input type="file" name="file" id="file"/>
		</form>
		</div>
		<div class="file_button">
			<span onclick="uploadFile();">确定</span>
			<span onclick="closeAll();">取消</span>
		</div>
	</div>
	
	<div class="wjj">
		<div class="wjj_choice">
			<input type="text" placeholder="请输入名称" name="wjj" id="wjj"/>
		</div>
		<div class="wjj_button">
			<span onclick="createWjj();">确定</span>
			<span onclick="closeAll();">取消</span>
		</div>
	</div>
	
	
</body>
</html>