<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>

</style>
<script type="text/javascript">
	function searchReply(page,num){
		//获取名称
		var content = $("#reply_search_content").val();
		var visible = $("#reply_search_visible").val();
		var orderBy = $("#reply_search_sort").val();
		getSearchReply(content,orderBy,visible,page,num);
	}
	//名称，菜单级别，排序方式，是否可见,页数，每页多少
	function getSearchReply(content,orderBy,visible,page,num){
		//根据条件取查询菜单
		$.ajax({ 
				 url: "${pageContext.request.contextPath }/blog/blogManageAction!getSearchReply.action",
		         type: "POST", 
		         //async: false,
		         dataType:'json',
		         data : {"content" : content,"orderBy":orderBy,"visible":visible,"page":page,"num":num},
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
		        		$("#reply .result table").html("");
		        		var pageValues = data.pageValues;
		        		str='<tr><th>序号</th><th>内容</th><th>所属主题</th><th>时间</th> <th>创建者</th><th>回复谁</th><th>点赞数</th><th>状态</th><th>操作</th></tr>';
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
					    		str+='<input type="submit" value_id="1" value="发布" onclick="visible(this,'+pageValues[i].id+',2);"/>';
					    	}else{
					    		str+='<input type="submit" value_id="0" value="取消" onclick="visible(this,'+pageValues[i].id+',2);"/>';
						    }
					    	//str+='<input type="submit" value="修改" onclick="edit('+pageValues[i].id+');"/>';
					    	str+='<input type="submit" value="删除" onclick="deleteObject(this,'+pageValues[i].id+',2);"/>';
					    	str+='</td></tr>';
		        		}
		        		$("#reply .result table").append(str);
		        	 }
		         },
		         error : function (data) {
		           	alert("网络异常");
		         }
		 	});
	}
</script>
<div id="reply" class="content_menu">
	<div class="search">
		<div class="search1">
			<table>
				<tr>
					<td>
						<span>内容:</span>
						<span><input type="text" id="reply_search_content"/></span>
					</td>
					<td>
						<span>排序</span>
						<span>
							<select id="reply_search_sort">
											<option value="1" selected>时间逆序</option>
											<option value="2">时间正序</option>
							</select>
						</span>
					</td>
					<td>
						<span>状态</span>
						<span>
							<select id="reply_search_visible">
											<option value="" selected>全部</option>
											<option value="1">发布</option>
											<option value="0">未发布</option>
										</select>
						</span>
					</td>
				</tr>
			</table>
		</div>
		<div class="search2"><input type="submit" value="查询" onclick="searchReply(0,15);"/></div>
	</div>
	<div class="result">
		<div class="div_table">
			<table>
				
			</table>
		</div>
	</div>
</div>