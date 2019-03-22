<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
#editMenu_content{
	width:50%;
	height:490px;
	margin:0px auto;
}
#edit_menu_type{
	width:100%;
	height:81px;
	line-height:81px;
}
#edit_menu_type1{
	float:left;
	width:50%;
	height:100%;
	text-align:left;
}
#edit_menu_type2{
	float:right;
	width:50%;
	height:100%;
	text-align:left;
}
#edit_menu_type select{
	width:100px;
	height:25px;
	border-radius:5px;	
}
#edit_menu_type3{
	width:100%;
	height:81px;
	line-height:81px;
	text-align:left;
}
#edit_menu_type3 select{
	width:100px;
	height:25px;
	border-radius:5px;	
}
#edit_menu_content{
	width:100%;
	height:162px;
	text-align:center;
}
#edit_menu_content div{
	width:100%;
	height:81px;
	line-height:81px;
	text-align:left;
}
#edit_menu_content select{
	width:100px;
	height:25px;
	border-radius:5px;	
}
#edit_menu_content div input{
	width:70%;
	height:25px;
	border-radius:5px;	
	background-color:#fff;
}
#edit_menu_img_sort{
	width:100%;
	height:81px;
	line-height:81px;
}
#edit_menu_img{
	float:left;
	width:50%;
	height:100%;
	text-align:left;
}
#edit_menu_sort{
	float:right;
	width:50%;
	height:100%;
	text-align:left;
}
#edit_menu_img_sort input{
	width:160px;
	height:20px;
	border-radius:5px;	
	background-color:#fff;
}
#edit_menu_img_sort select{
	width:100px;
	height:25px;
	border-radius:5px;	
	background-color:#fff;
}
#edit_commit{
	width:100%;
	height:81px;
	text-align:center;
}
#edit_commit input{
	width:50px;
	height:30px;
	background-color:#fff;
	cursor:pointer;
}
</style>
<script type="text/javascript">
	function getMenuFromId(id){
		alert("功能正在完善中");
		return;
		//更具ID获取菜单的信息
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!getMenuFromId.action",
			type: "POST",
			dataType:'json', 
			data : {"id":id},
			success: function (data) {
				if(data.state!=""){
				}
			},
			error : function (data) {
				
			}
		});
	}
</script>
<div id="editMenu" class="edit">
	<div class="add_top">菜单的修改</div>
	<form id="editMenuForm">
		<div id="editMenu_content">
			<div id="edit_menu_type">
				<div id="edit_menu_type1">
					<span>菜单类型:</span>
					<span>
						<select name="type">
						</select>
					</span>
				</div>
				<div id="edit_menu_type2">
					<span>父级类型:</span>
					<span>
						<select name="menuId">
						</select>
					</span>
				</div>
			</div>
			<div id="edit_menu_type3">
					<span>是否发布:</span>
					<span>
						<select name="visible">
							<option value="1" selected>是</option>
							<option value="0" >否</option>
						</select>
					</span>
			</div>
			<div id="edit_menu_content">
				<div>
					<span>菜单名称:</span>
					<span><input type="text" id="edit_name" name="name"/></span>
				</div>
				<div>
					<span>菜单备注:</span>
					<span><input type="text" id="edit_remark" name="remark"/></span>
				</div>
			</div>
			<div id="edit_menu_img_sort">
				<div id="edit_menu_img">
					<span>菜单图片:</span>
					<span><input type="file" id="edit_file" name="file"/></span>
				</div>
				<div id="edit_menu_sort">
					<span>菜单排序:</span>
					<span>
						<select name="sort">
						</select>
					</span>
				</div>
			</div>
			<div id="edit_commit">
				<input type="button" value="创建" onclick="saveMenu();"/>&nbsp;&nbsp;
				<input type="button" value="返回" onclick="closeAddMenu();"/>
			</div>
		</div>
	</form>
</div>
