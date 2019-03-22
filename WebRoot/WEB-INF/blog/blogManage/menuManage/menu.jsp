<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>

</style>
<script type="text/javascript">
    var ten =true;
	function flushApplication(){
		if(!ten){
			return;
		}
		ten=false;
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!flushApplication.action",
			type: "POST",
			//dataType:'json', 
			data : {},
			success: function (data) {
				if(data=="logon"){
					window.location.href='${pageContext.request.contextPath}/blog/blogManageAction!blogManage.action';
					ten=true;
				}
				if(data=="success"){
					alert("刷新成功");
					ten=true;
				}
			},
			error : function (data) {
				alert("系统错误");
				ten=true;
			}
		});
	}
	function searchMenu(page,num){
		//获取名称
		var name = $("#menu_search_name").val();
		var type = $("#menu_type_search").val();
		var visible = $("#menu_search_visible").val();
		var orderBy = $("#menu_search_sort").val();
		getSearchMenu(name,type,orderBy,visible,page,num);
	}
	//名称，菜单级别，排序方式，是否可见,页数，每页多少
	function getSearchMenu(name,type,orderBy,visible,page,num){
		//根据条件取查询菜单
		$.ajax({ 
 			 url: "${pageContext.request.contextPath }/blog/blogManageAction!getSearchMenu.action",
 	         type: "POST", 
 	         //async: false,
 	         dataType:'json',
 	         data : {"name" : name,"type":type,"orderBy":orderBy,"visible":visible,"page":page,"num":num},
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
 	        		$("#menu .result table").html("");
 	        		var pageValues = data.pageValues;
 	        		str='<tr><th>序号</th><th>名称</th><th>菜单备注</th><th>菜单级别</th><th>创建时间</th><th>创建者</th><th>排序</th><th>状态</th><th>操作</th></tr>';
 	        		for(var i=0;i<pageValues.length;i++){
 	 	        		str+='<tr>';
 	 	        		str+='<td>'+(index+i+1)+'</td>';
				    	str+='<td>'+pageValues[i].valueA+'</td>';
				    	str+='<td>'+pageValues[i].valueB+'</td>';
				    	str+='<td>'+numToChina(parseInt(pageValues[i].valueD))+'级菜单</td>';
				    	str+='<td>'+pageValues[i].valueC+'</td>';
				    	str+='<td>'+pageValues[i].valueG+'</td>';
				    	str+='<td>'+pageValues[i].valueE+'</td>';
				    	if(pageValues[i].valueF=='0'){
							str+='<td>未发布</td>';
					    }else{
							str+='<td>已发布</td>';
						}
				    	str+='<td class="tools">';
				    	if(pageValues[i].valueF=='0'){
				    		str+='<input type="submit" value_id="1" value="发布" onclick="visible(this,'+pageValues[i].id+',0);"/>';
				    	}else{
				    		str+='<input type="submit" value_id="0" value="取消" onclick="visible(this,'+pageValues[i].id+',0);"/>';
					    }
				    	//不提供修改功能先：要改就直接改数据库str+='<input type="submit" value="修改" onclick="editMenu('+pageValues[i].id+');"/>';
				    	str+='<input type="submit" value="删除" onclick="deleteObject(this,'+pageValues[i].id+',0);"/>';
				    	str+='</td></tr>';
 	        		}
 	        		$("#menu .result table").append(str);
 	        	 }
 	         },
 	         error : function (data) {
 	           	alert("网络异常");
 	         }
 	 	});
	}
	//获取菜单的种类这个是查询的种
	function getMenuTypeNumSearch(){
		$.ajax({ 
			url: "${pageContext.request.contextPath }/blog/blogManageAction!getMenuTypeNum.action",
			type: "POST",
			dataType:'json', 
			data : {},
			success: function (data) {
				if(data.state!=""){
					//用户可以建立比现在多一级的菜单:因为data中的数据就是从1开始的 所以可以直接用序号代替
					str='<option value="0" selected>请选择</option>';
					for(var i=0;i<data.length;i++){
						var num = i+1;
						str+='<option value='+num+'>';
						str+=numToChina(num)+'级菜单';
						str+='</option>';
					}
					$("#menu_type_search").empty();
					$("#menu_type_search").append(str);
				}
			},
			error : function (data) {
				
			}
		});
	}
</script>
<div id="menu" class="content_menu">
				<div class="search">
					<div class="search1">
						<table>
							<tr>
								<td>
									<span>名称:</span>
									<span><input type="text" id="menu_search_name"/></span>
								</td>
								<td>
									<span>等级:</span>
									<span>
										<select id="menu_type_search">
										</select>
									</span>
								</td>
								<td>
									<span>排序</span>
									<span>
										<select id="menu_search_sort">
											<option value="1" selected>时间逆序</option>
											<option value="2">时间正序</option>
											<option value="3">顺序正序</option>
											<option value="4">顺序逆序</option>
										</select>
									</span>
								</td>
								<td>
									<span>状态</span>
									<span>
										<select id="menu_search_visible">
											<option value="" selected>全部</option>
											<option value="1">发布</option>
											<option value="0">未发布</option>
										</select>
									</span>
								</td>
							</tr>
						</table>
					</div>
					<div class="search2"><input type="submit" value="查询" onclick="searchMenu(0,15);"/>&nbsp;&nbsp;&nbsp;<input type="submit" value="新增" onclick="addMenu();"/>&nbsp;&nbsp;&nbsp;<input type="submit" value="刷新缓存" onclick="flushApplication();"/></div>
				</div>
				<div class="result">
					<div class="div_table">
						<table>
							
						</table>
					</div>
				</div>
			</div>
