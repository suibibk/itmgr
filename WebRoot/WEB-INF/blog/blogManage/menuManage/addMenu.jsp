<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#addMenu_content{
	width:50%;
	height:490px;
	margin:0px auto;
}
#menu_type{
	width:100%;
	height:81px;
	line-height:81px;
}
#menu_type1{
	float:left;
	width:50%;
	height:100%;
	text-align:left;
}
#menu_type2{
	float:right;
	width:50%;
	height:100%;
	text-align:left;
}
#menu_type select{
	width:100px;
	height:25px;
	border-radius:5px;	
}
#menu_type3{
	width:100%;
	height:81px;
	line-height:81px;
	text-align:left;
}
#menu_type3 select{
	width:100px;
	height:25px;
	border-radius:5px;	
}
#menu_content{
	width:100%;
	height:162px;
	text-align:center;
}
#menu_content div{
	width:100%;
	height:81px;
	line-height:81px;
	text-align:left;
}
#menu_content select{
	width:100px;
	height:25px;
	border-radius:5px;	
}
#menu_content div input{
	width:70%;
	height:25px;
	border-radius:5px;	
	background-color:#fff;
}
#menu_img_sort{
	width:100%;
	height:81px;
	line-height:81px;
}
#menu_img{
	float:left;
	width:50%;
	height:100%;
	text-align:left;
}
#menu_sort{
	float:right;
	width:50%;
	height:100%;
	text-align:left;
}
#menu_img_sort input{
	width:160px;
	height:20px;
	border-radius:5px;	
	background-color:#fff;
}
#menu_img_sort select{
	width:100px;
	height:25px;
	border-radius:5px;	
	background-color:#fff;
}
#commit{
	width:100%;
	height:81px;
	text-align:center;
}
#commit input{
	width:50px;
	height:30px;
	background-color:#fff;
	cursor:pointer;
}
</style>
<script type="text/javascript">
	$(function(){
		$("#menu_type2").hide();
		$("#menu_type1 select").change(function(){
			$("#menu_sort select").empty();
			var type = $("#menu_type1 select").val();
			if(type==0||type==1){
				$("#menu_type2").hide();
				if(type==1){
					getSort(type,0);
				}
				return;//不用处理
			}
			//去加载对应的父级菜单
			getAllParentMenu(type);
		});
		$("#menu_type2 select").change(function(){
			$("#menu_sort select").empty();
			var type2 = $("#menu_type2 select").val();
			if(type2!=0){
				getSort($("#menu_type1 select").val(),type2);
			}
		});
	});

	//根据菜单类型和父类的menuId获取用户可选排序值
	function getSort(type,menuId){
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!getSort.action",
			type: "POST",
			dataType:'json', 
			data : {"type":type,"menuId":menuId},
			success: function (data) {
				if(data.state!=""){
					//用户可以建立比现在多一级的菜单:因为data中的数据就是从1开始的 所以可以直接用序号代替
					str='<option value="0" selected>请选择</option>';
					if(data[0]==0){
						str+='<option value="1">1</option>';
					}else{
						for(var i=0;i<data.length+1;i++){
							var num = i+1;
							str+='<option value='+num+'>';
							str+=num;
							str+='</option>';
						}
					}
					$("#menu_sort select").empty();
					$("#menu_sort select").append(str);
				}
			},
			error : function (data) {
				
			}
		});
	}

	//根据菜单类型，获取所有父级类型
	function getAllParentMenu(type){
		//首先肯定要减去1，因为这才是上一级菜单
		var type=type-1;
		if(type<1){
			return;
		}
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!getAllParentMenu.action",
			type: "POST",
			dataType:'json', 
			data : {"type":type},
			success: function (data) {
				if(data.state!=""){
					//用户可以建立比现在多一级的菜单:因为data中的数据就是从1开始的 所以可以直接用序号代替
					str='<option value="0" selected>请选择</option>';
					for(var i=0;i<data.length;i++){
						var num = i+1;
						str+='<option value='+data[i].id+'>';
						str+=data[i].name;
						str+='</option>';
					}
					$("#menu_type2 select").empty();
					$("#menu_type2 select").append(str);
					$("#menu_type2").show();
				}
			},
			error : function (data) {
				
			}
		});
	}
	
	//获取菜单的种类
	function getMenuTypeNum(){
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!getMenuTypeNum.action",
			type: "POST",
			dataType:'json', 
			data : {},
			success: function (data) {
				if(data.state!=""){
					//用户可以建立比现在多一级的菜单:因为data中的数据就是从1开始的 所以可以直接用序号代替
					str='<option value="0" selected>请选择</option>';
					for(var i=0;i<(data.length+1);i++){
						var num = i+1;
						str+='<option value='+num+'>';
						str+=numToChina(num)+'级菜单';
						str+='</option>';
					}
					$("#menu_type1 select").empty();
					$("#menu_type1 select").append(str);
				}
			},
			error : function (data) {
				
			}
		});
	}

	//简单的1到十数字转中文的方法
	function numToChina(num){
		switch (num) {
		case 1:
			return "一";
			break;
		case 2:
			return "二";
			break;
		case 3:
			return "三";
			break;
		case 4:
			return "四";
			break;
		case 5:
			return "五";
			break;
		case 6:
			return "六";
			break;
		case 7:
			return "七";
			break;
		case 8:
			return "八";
			break;
		case 9:
			return "九";
			break;
		case 10:
			return "十";
			break;
		default:
			break;
		}
	}
	/**
	Ajax的processData设置为false。因为data值是FormData对象，不需要对数据做处理。
	 第二种方式中<form>标签加enctyp　　e="multipart/form-data"属性。
	cache设置为false，上传文件不需要缓存。
	contentType设置为false。因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="mutipart/form-data"，所以这里设置为false。
	*/
	var one = true;
	function saveMenu() {  
		if(!one){
			return;
		}
		one=false;

		//获取要创建的菜单类型
		var menu_type1 = $("#menu_type1 select").val();
		if(menu_type1==0){
			alert("请选择要创建的菜单类型");
			one=true;
			return;
		}
		//获取父级菜单类型
		var menu_type2 = $("#menu_type2 select").val();
		if(type!=1&&menu_type2==0){
			alert("请选择所属父级菜单");
			one=true;
			return;
		}
		//获取是否发布
		//获取菜单名称
		var name = $("#name").val();
		if(name==""){
			alert("菜单名称不能为空");
			one=true;
			return;
		}
		//获取菜单排序
		var sort = $("#menu_sort select").val();
		if(sort==0){
			alert("请选择排序");
			one=true;
			return;
		}
		//获取文件名
		var file = $("#file").val();
		if(file!=""){
			var type=file.substring(file.lastIndexOf(".")+1).toLowerCase();
			var flag=(type=="jpg"||type=="jpeg"||type=="png"||type=="gif");
			if(!flag){
				alert("请上传jpg|jpeg|png|gif等格式的图片");
				one=true;
				return;
			}
		}
	    var formData = new FormData($("#addMenuForm")[0]);
	    $.ajax({  
	          url: '${pageContext.request.contextPath }/blog/blogManageAction!addMenu.action' ,  
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
			    	  one=true;
					  return;
				  }
		          if(data=="success"){
						//初始化div
						alert("保存成功，继续添加");
						//2、初始化选项值
						$("#menu_type1 select").empty();
						$("#menu_type2 select").empty();
						$("#menu_sort select").empty();
						$("#name").val("");
						$("#remark").val("");
						$("#file").val("");
						//3、初始化addMenu div
						getMenuTypeNum();
						one=true;
						return;
			      } 
	          },  
	          error: function (data) {  
	              alert("系统异常,请重试"); 
	              one=true;
	              return; 
	          }  
	     });  
	 }  

	 //隐藏即可
	 function closeAddMenu(){
		 $("#addMenu").hide();
		 $("#menu").show();
		 $(".page").show();
	 }
</script>
<div id="addMenu" class="add">
	<div class="add_top">菜单的创建</div>
	<form id="addMenuForm">
		<div id="addMenu_content">
			<div id="menu_type">
				<div id="menu_type1">
					<span>菜单类型:</span>
					<span>
						<select name="type">
						</select>
					</span>
				</div>
				<div id="menu_type2">
					<span>父级类型:</span>
					<span>
						<select name="menuId">
						</select>
					</span>
				</div>
			</div>
			<div id="menu_type3">
					<span>是否发布:</span>
					<span>
						<select name="visible">
							<option value="1" selected>是</option>
							<option value="0" >否</option>
						</select>
					</span>
			</div>
			<div id="menu_content">
				<div>
					<span>菜单名称:</span>
					<span><input type="text" id="name" name="name"/></span>
				</div>
				<div>
					<span>菜单备注:</span>
					<span><input type="text" id="remark" name="remark"/></span>
				</div>
			</div>
			<div id="menu_img_sort">
				<div id="menu_img">
					<span>菜单图片:</span>
					<span><input type="file" id="file" name="file"/></span>
				</div>
				<div id="menu_sort">
					<span>菜单排序:</span>
					<span>
						<select name="sort">
						</select>
					</span>
				</div>
			</div>
			<div id="commit">
				<input type="button" value="创建" onclick="saveMenu();"/>&nbsp;&nbsp;
				<input type="button" value="返回" onclick="closeAddMenu();"/>
			</div>
		</div>
	</form>
</div>
