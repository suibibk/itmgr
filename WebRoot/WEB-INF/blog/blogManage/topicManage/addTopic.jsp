<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#addTop_content{
	width:95%;
	height:490px;
	margin:0px auto;
}
#topic_msg{
	width:100%;
	height:30px;
	line-height:30px;
}
#topic_msg div{
	float:left;
	height:100%;
	margin-right:80px;
}
#topic_menu select{
	width:150px;
	height:25px;
	border-radius:5px;	
}
#topic_visible select{
	width:50px;
	height:25px;
	border-radius:5px;	
}

#topic_img input{
	width:160px;
	height:20px;
	border-radius:5px;	
	background-color:#fff;
}
#topic_title{
	width:100%;
	height:45px;
	line-height:45px;
}
#topic_title input{
	height:25px;
	width:85%;
	border-radius:5px;	
	background-color:#fff;
}
#topic_cotent{
	width:100%;
	height:375px;
}
#topic_commit{
	width:100%;
	height:40px;
	text-align:center;
}
#topic_commit input{
	width:50px;
	height:30px;
	background-color:#fff;
	cursor:pointer;
}
</style>
<script type="text/javascript">
	var editor = null;
	$(function(){
		 editor = CKEDITOR.replace(
			    	'topic_content',
			    	{
				    	//resize_enabled:false,
				    	//height:230
			    		 customConfig:'${pageContext.request.contextPath }/ckeditor4.4.4/addTopicConfig.js',
			    		//开启工具栏“图像”中文件上传功能，后面的url为待会要上传action要指向的的action或servlet  
			    		filebrowserImageUploadUrl:"${pageContext.request.contextPath}/blog/blogManageAction!toUpload.action",
			    		//开启插入\编辑超链接中文件上传功能，后面的url为待会要上传action要指向的的action或servlet                                                                                                         
			    		filebrowserUploadUrl :"${pageContext.request.contextPath}/blog/blogManageAction!toUpload.action"

			    	}); //参数‘content’是textarea元素的name属性值，而非id属性值
	});

	//获取所有菜单
	function getAllMenu(){
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!getAllMenu.action",
			type: "POST",
			dataType:'json', 
			data : {},
			success: function (data) {
				if(data.state!=""){
					//用户可以建立比现在多一级的菜单:因为data中的数据就是从1开始的 所以可以直接用序号代替
					str='<option value="0" selected>请选择</option>';
					for(var i=0;i<data.length;i++){
						str+='<option value='+data[i].id+'>';
						str+=data[i].name;
						str+='</option>';
					}
					$("#topic_menu select").empty();
					$("#topic_menu select").append(str);
				}
			},
			error : function (data) {
				
			}
		});

	}
	var two = true;
	function saveTopic() {  
		if(!two){
			return;
		}
		two=false;

		//获取要创建主题所属的菜单
		var topic_menu = $("#topic_menu select").val();
		if(topic_menu==0){
			alert("请选择主题所属菜单");
			two=true;
			return;
		}
		
		//获取文件名
		var file = $("#topic_img input").val();
		if(file!=""){
			var type=file.substring(file.lastIndexOf(".")+1).toLowerCase();
			var flag=(type=="jpg"||type=="jpeg"||type=="png"||type=="gif");
			if(!flag){
				alert("请上传jpg|jpeg|png|gif等格式的图片");
				two=true;
				return;
			}
		}
		//获取菜主题标题
		var topic_title = $("#topic_title input").val();
		if(topic_title==""){
			alert("主题标题不能为空");
			two=true;
			return;
		}
		// 因为CKEditor编辑器取代了原来我们写的textarea元素，所以我们在编辑器里写的内容，其实都不在textarea中'
		// 因此，为了能在后台通过textarea获得值，必须用editor.updateElement()来更新textarea元素。
		editor.updateElement(); //非常重要的一句代码
		var topic_content = $("#topic_textarea").val();
		if(topic_content==""){
			alert("主题内容不能为空");
			two=true;
			return;
		}
	    var formData = new FormData($("#addTopicForm")[0]);
	    $.ajax({  
	          url: '${pageContext.request.contextPath }/blog/blogManageAction!addTopic.action' ,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (data) { 
		          if(data=="logon"){
						alert("请先登录");
						window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
						return;
			      }
			      if(data=="file_error"){
			    	  alert("上传图片失败，请重新上传");
			    	  two=true;
					  return;
				  }
		          if(data=="success"){
						//初始化div
						//初始化选项值
						alert("保存成功，继续添加");
						$("#topic_menu select").empty();
						$("#topic_title input").val("");
						$("#topic_img input").val("");
						editor.setData("");
						//初始化获取所有菜单
						getAllMenu();
						two=true;
						return;
			      } 
	          },  
	          error: function (data) {  
	              alert("系统异常,请重试"); 
	              two=true;
	              return; 
	          }  
	     });  
	 }  
	 //隐藏即可
	 function closeAddTopic(){
		 $("#addTopic").hide();
		 $("#topic").show();
		 $(".page").show();
	 }
</script>
<div id="addTopic" class="add">
	<div class="add_top">主题的创建(第一段用于列表显示，请换行)</div>
	<form id="addTopicForm">
		<div id="addTop_content">
			<div id="topic_msg">
				<div id="topic_menu">
					<span>所属菜单:</span>
					<span>
						<select name="topic_menu">
						</select>
					</span>
				</div>
				<div id="topic_img">
					<span>主题图片:</span>
					<span><input type="file" name="file"/></span>
				</div>
				<div id="topic_visible">
					<span>是否发布:</span>
					<span>
						<select name="visible">
							<option value="1" selected>是</option>
							<option value="0" >否</option>
						</select>
					</span>
				</div>
			</div>
			<div id="topic_title">
				<span>主题标题:</span>
				<span><input type="text" name="title"/></span>
			</div>
			<div id="topic_cotent">
				 <textarea id="topic_textarea" name="topic_content"></textarea>
			</div>
			<div id="topic_commit">
				<input type="button" value="创建" onclick="saveTopic();"/>&nbsp;&nbsp;
				<input type="button" value="返回" onclick="closeAddTopic();"/>
			</div>
		</div>
	</form>
</div>
