<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>

</style>
<script type="text/javascript">
	function searchTopic(page,num){
		//获取名称
		var title = $("#topic_search_title").val();
		var menuId = $("#topic_serch_menu").val();
		var visible = $("#topic_search_visible").val();
		var orderBy = $("#topic_search_sort").val();
		getSearchTopic(title,menuId,orderBy,visible,page,num)
	}
	//名称，菜单级别，排序方式，是否可见,页数，每页多少
	function getSearchTopic(title,menuId,orderBy,visible,page,num){
		//根据条件取查询菜单
		$.ajax({ 
				 url: "${pageContext.request.contextPath }/blog/blogManageAction!getSearchTopic.action",
		         type: "POST", 
		         //async: false,
		         dataType:'json',
		         data : {"title" : title,"menuId":menuId,"orderBy":orderBy,"visible":visible,"page":page,"num":num},
		         success: function (data) {
		 	         if(data.state=="logon"){
					alert("用户已过期，请重新登录");
					window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
					return;
		 	 	     }
		        	 if(data.state!="error"){
		        		$("#all_count").html(data.count);
		        		$("#all_page").html(data.all_page);
		        		var index = page*num;
		        		$("#now_page").html(page+1);
		        		if(data.count==0){
		        			$("#now_page").html("0");
		 	        	}
		        		$("#topic .result table").html("");
		        		var pageValues = data.pageValues;
		        		str='<tr><th>序号</th><th>标题</th><th>所属菜单</th><th>创建时间</th><th>创建者</th><th>点赞数</th><th>浏览量</th><th>状态</th><th>操作</th></tr>';
		        		for(var i=0;i<pageValues.length;i++){
		 	        		str+='<tr>';
		 	        		str+='<td>'+(index+i+1)+'</td>';
					    	str+='<td>'+pageValues[i].valueA+'</td>';
					    	str+='<td>'+pageValues[i].valueB+'</td>';
					    	str+='<td>'+pageValues[i].valueC+'</td>';
					    	str+='<td>'+pageValues[i].valueD+'</td>';
					    	str+='<td>'+pageValues[i].valueE+'</td>';
					    	str+='<td>'+pageValues[i].valueF+'</td>';
					    	if(pageValues[i].valueG=='0'){
								str+='<td>未发布</td>';
						    }else{
								str+='<td>已发布</td>';
							}
					    	str+='<td class="tools">';
					    	if(pageValues[i].valueG=='0'){
					    		str+='<input type="submit" value_id="1" value="发布" onclick="visible(this,'+pageValues[i].id+',1);"/>';
					    	}else{
					    		str+='<input type="submit" value_id="0" value="取消" onclick="visible(this,'+pageValues[i].id+',1);"/>';
						    }
					    	//str+='<input type="submit" value="修改" onclick="edit('+pageValues[i].id+');"/>';
					    	str+='<input type="submit" value="删除" onclick="deleteObject(this,'+pageValues[i].id+',1);"/>';
					    	str+='</td></tr>';
		        		}
		        		$("#topic .result table").append(str);
		        	 }
		         },
		         error : function (data) {
		           	alert("网络异常");
		         }
		 	});
	}
	//获取所有菜单
	function getAllSearchMenu(){
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
					$("#topic_serch_menu").empty();
					$("#topic_serch_menu").append(str);
				}
			},
			error : function (data) {
				
			}
		});

	}
</script>
<div id="topic" class="content_menu">
			<div class="search">
					<div class="search1">
						<table>
							<tr>
								<td>
									<span>名称:</span>
									<span><input type="text" id="topic_search_title"/></span>
								</td>
								<td>
									<span>所属菜单:</span>
									<span>
										<select id="topic_serch_menu">
										</select>
									</span>
								</td>
								<td>
									<span>排序</span>
									<span>
										<select id="topic_search_sort">
											<option value="1" selected>时间逆序</option>
											<option value="2">时间正序</option>
										</select>
									</span>
								</td>
								<td>
									<span>状态</span>
									<span>
										<select id="topic_search_visible">
											<option value="" selected>全部</option>
											<option value="1">发布</option>
											<option value="0">未发布</option>
										</select>
									</span>
								</td>
							</tr>
						</table>
					</div>
					<div class="search2"><input type="submit" value="查询" onclick="searchTopic(0,15);"/>&nbsp;&nbsp;&nbsp;<input type="submit" value="新增" onclick="addTopic();"/></div>
				</div>
				<div class="result">
					<div class="div_table">
						<table>
						</table>
					</div>
				</div>
			</div>
			
