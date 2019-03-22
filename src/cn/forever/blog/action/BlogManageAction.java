package cn.forever.blog.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.forever.blog.model.Menu;
import cn.forever.blog.model.PageValue;
import cn.forever.blog.model.Reply;
import cn.forever.blog.model.Topic;
import cn.forever.blog.model.User;
import cn.forever.blog.service.BlogManageService;
import cn.forever.dao.ObjectDao;
import cn.forever.redis.CacheUtil;
import cn.forever.utils.CommonFunction;
import cn.forever.utils.Const;
import cn.forever.utils.Encrypt;
import cn.forever.utils.StrutsParamUtils;

import com.google.gson.Gson;
/**
 * 20170508
 * @author lwh
 * 用于博客管理,只有超级管理员才可以管理博客
 */
@ParentPackage(value = "struts-default")
@Namespace(value = "/blog")
@Action(value = "blogManageAction",results = {
		@Result(name = "menuManage",location = "/WEB-INF/blog/blogManage/menuManage/menuManage.jsp"),
		@Result(name = "topicManage",location = "/WEB-INF/blog/blogManage/topicManage/topicManage.jsp"),
		@Result(name = "replyManage",location = "/WEB-INF/blog/blogManage/replyManage/replyManage.jsp"),
		@Result(name = "login",location = "/WEB-INF/blog/blogManage/common/login.jsp")
})
public class BlogManageAction {
	private final static int NUM = 15;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger log = Logger.getLogger(BlogManageAction.class);
	@Resource(name="objectDao")
	private ObjectDao objectDao;
	//加入缓存
	@Resource(name="cacheUtil")
	private CacheUtil cacheUtil;
	
	
	@Resource(name="blogManageService")
	private BlogManageService blogManageService;
	//文件上传
	private File file;//对应的就是表单中文件上传的那个输入域的名称，Struts2框架会封装成File类型的
    private String fileFileName;//   上传输入域FileName  文件名
    private String fileContentType;// 上传文件的MIME类型
    
    //下面的是ckediter上传的字段
    private File upload;  //文件  
    private String uploadContentType;  //文件类型  
    private String uploadFileName;   //文件名 
    

    
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public File getFile() {
		return file;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String blogManage(){
		HttpServletRequest request = StrutsParamUtils.getRequest();
		HttpSession session = request.getSession();
		String type = StrutsParamUtils.getPraramValue("type", "1");
		request.setAttribute("type", type);
		//判断用户是否登录
		//只有后台管理的会调用这个（然后，注销的话肯定是）
		User user = (User) session.getAttribute("user");
		if(user==null||!"0".equals(user.getType())){
			log.info("用户没有登录，去到登录页面");
			return "login";
		}else{
			//下面处理登录后的页面逻辑
			if("1".equals(type)){
				return "menuManage";
			}
			if("2".equals(type)){
				return "topicManage";
			}
			if("3".equals(type)){
				return "replyManage";
			}else{
				session.removeAttribute("user");
				return "login";
			}
		}
		
	}
	
	//博客管理登录 且用户有效
	public void login(){
		//获取页面传过来的参数
		String str = "{\"state\":\"error\"}";
		String username = StrutsParamUtils.getPraramValue("username", "");
		String password = StrutsParamUtils.getPraramValue("password", "");
		HttpSession session = StrutsParamUtils.getRequest().getSession();
		log.info("username="+username+"|password="+password);
		if("".equals(username)||"".equals(password)){
			StrutsParamUtils.writeStr(str); 
			log.info("用户名或密码为空");
			return;
		}
		//获取加密后的密码
		String encrypt_password = Encrypt.encrypt(password);
		//根据用户名和密码去数据库中查找是否有记录
		String hql = "from User u where u.username=? and u.password=? and u.visible='1'";
		Object[] args = new Object[]{username,encrypt_password};
		User user = (User) objectDao.findObjectByHqlAndArgs(hql, args);
		if(user!=null){
			//网盘登录
			if("0".equals(user.getType())||"1".equals(user.getType())){
				//用户所拥有的权限
				str= "{\"state\":\"success\"}";
				session.setAttribute("user", user);//博客管理
				StrutsParamUtils.writeStr(str); 
				log.info("用户登录成功");
			}else{
				//等于1的话是博客用户，不能使用管理系统
				StrutsParamUtils.writeStr(str); 
				log.info("用户名或密码错误");
			}
			return;
		}else{
			StrutsParamUtils.writeStr(str); 
			log.info("用户名或密码错误");
			return;
		}
		
	}
	
	/**
	 * 为了安全，这里注销所有
	 * @return
	 */
	public String logon(){
		HttpServletRequest request = StrutsParamUtils.getRequest();
		HttpSession session = request.getSession();
		//只有后台管理的会调用这个（然后，注销的话肯定是）
		User user = (User) session.getAttribute("user");
		if(user!=null){
			session.removeAttribute("user");
		}
		//CommonFunction.redirect(request.getContextPath()+"/blog/blogManageAction!blogManage.action");
		return "login";
		
	}
	
	public void visible(){
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null||!"0".equals(user.getType())){
			StrutsParamUtils.writeStr("logon");
			log.info("用户未登登录");
			return;
		}
		//获取页面传过来的参数
		String str_id = StrutsParamUtils.getPraramValue("id", "");
		String visible = StrutsParamUtils.getPraramValue("visible", "");//是否发布
		String type = StrutsParamUtils.getPraramValue("type", "");//处理类型0是菜单1是主题2是回复
		if("".equals(str_id)||"".equals(visible)||"".equals(type)){
			StrutsParamUtils.writeStr("error");
			log.info("参数错误");
			return;
		}
		Long id = Long.parseLong(str_id);
		Object[] args =new Object[]{id};
		//处理菜单
		if("0".equals(type)){
			String hql_menu = "from Menu u where u.id=?";
			Menu menu = (Menu) objectDao.findObjectByHqlAndArgs(hql_menu,args);
			if(menu!=null){
				menu.setVisible(visible);
				objectDao.saveOrUpdate(menu);
			}
		}
		//处理主题
		if("1".equals(type)){
			String hql_topic = "from Topic t where t.id=?";
			Topic topic = (Topic) objectDao.findObjectByHqlAndArgs(hql_topic,args);
			if(topic!=null){
				topic.setVisible(visible);
				objectDao.saveOrUpdate(topic);
			}
		}
		//处理回复
		if("2".equals(type)){
			String hql_reply = "from Reply r where r.id=?";
			Reply reply = (Reply) objectDao.findObjectByHqlAndArgs(hql_reply,args);
			if(reply!=null){
				reply.setVisible(visible);
				objectDao.saveOrUpdate(reply);
			}
		}
		StrutsParamUtils.writeStr("success");
		log.info("修改成功");
		return;
	}
	public void delete(){
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null||!"0".equals(user.getType())){
			StrutsParamUtils.writeStr("logon");
			log.info("用户未登登录");
			return;
		}
		//获取页面传过来的参数
		String str_id = StrutsParamUtils.getPraramValue("id", "");
		String type = StrutsParamUtils.getPraramValue("type", "");//处理类型0是菜单1是主题2是回复
		if("".equals(str_id)||"".equals(type)){
			StrutsParamUtils.writeStr("error");
			log.info("参数错误");
			return;
		}
		Long id = Long.parseLong(str_id);
		
		Object[] args =new Object[]{id};
		//处理菜单
		if("0".equals(type)){
			String hql_menu = "from Menu u where u.id=?";
			Menu menu = (Menu) objectDao.findObjectByHqlAndArgs(hql_menu,args);
			if(menu!=null){
				objectDao.delete(menu);
			}
		}
		//处理主题
		if("1".equals(type)){
			String hql_topic = "from Topic t where t.id=?";
			Topic topic = (Topic) objectDao.findObjectByHqlAndArgs(hql_topic,args);
			if(topic!=null){
				String key = "menu_topic_count:"+topic.getMenuId();
				objectDao.delete(topic);
				//同时，redis中的次数也要减去
				String menu_topic_count=cacheUtil.get(key);
				if(menu_topic_count!=null){
					//这里才去处理
					cacheUtil.put(key,(Integer.parseInt(menu_topic_count)-1)+"");
				}
			}
		}
		//处理回复
		if("2".equals(type)){
			String hql_reply = "from Reply r where r.id=?";
			Reply reply = (Reply) objectDao.findObjectByHqlAndArgs(hql_reply,args);
			if(reply!=null){
				objectDao.delete(reply);
				
			}
		}
		StrutsParamUtils.writeStr("success");
		log.info("删除成功");
		return;
	}
	
	/***************************菜单列表开始******************************/
	public void getSearchMenu(){
		String str = "{\"state\":\"error\"}";
			//获取页面传过来的参数
			User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
			if(user==null||!"0".equals(user.getType())){
				str= "{\"state\":\"logon\"}";
				StrutsParamUtils.writeStr(str);
				log.info("用户未登登录");
				return;
			}
			String name = StrutsParamUtils.getPraramValue("name", "");
			String type = StrutsParamUtils.getPraramValue("type", "0");//0就查询全部
			String orderBy = StrutsParamUtils.getPraramValue("orderBy", "1");//1就是按时间逆序
			String visible = StrutsParamUtils.getPraramValue("visible", "");// 是否发布 
			String page = StrutsParamUtils.getPraramValue("page", "0");//默认查询第一页
			String num = StrutsParamUtils.getPraramValue("num", "15");//默认查询15条数据
			StringBuffer sb =new StringBuffer();
			Map<String,Object> map = new HashMap<String, Object>();
			if(!"".equals(name)){
				sb.append("from Menu u where u.name like :name");
				map.put("name", "%"+name+"%");
			}else{
				sb.append("from Menu u where 1=1");
			}
			if(!"0".equals(type)){
				sb.append(" and u.type=:type");
				map.put("type", type);
			}
			if(!"".equals(visible)){
				sb.append(" and u.visible=:visible");
				map.put("visible", visible);
			}
			if("1".equals(orderBy)){
				sb.append(" order by u.create_datetime desc");
			}
			if("2".equals(orderBy)){
				sb.append(" order by u.create_datetime asc");
			}
			if("3".equals(orderBy)){
				sb.append(" order by u.sort+0 asc");
			}
			if("4".equals(orderBy)){
				sb.append(" order by u.sort+0 desc");
			}
			log.info("sql:"+sb.toString());
			Map<String,Object> strMap = new HashMap<String, Object>();
			String getNum = "select count(*) "+sb.toString();
			int count =objectDao.getCount(getNum, map);//总数
			int all_page=count/NUM+1;//总页数
			if(count%NUM==0){
				all_page--;//相当于刚刚够页数 不用加下一页
			}
			strMap.put("count", count);
			strMap.put("all_page", all_page);
			//分页查询
			//定位初始位置:start_page 为0,15,30
			int search_num=Integer.parseInt(num);
			int start_page = Integer.parseInt(page)*search_num;
			
			List<Menu> menus = objectDao.findPageByHqlAndMap(sb.toString(), map,start_page,search_num);
			//log.info(menus.toString());
			List<PageValue> pageValues = getMenuListPageValue(menus);
			if(pageValues!=null){
				strMap.put("pageValues", pageValues);
			}
			Gson gosn = new Gson();
			str = gosn.toJson(strMap);
			log.info("获取的菜单是："+str);
			StrutsParamUtils.writeStr(str);
			return;
		}
		
		private List<PageValue> getMenuListPageValue(List<Menu> menus){
			if(menus==null){
				return null;
			}
			List<PageValue> pageValues = new ArrayList<PageValue>();
			for (Menu menu : menus) {
				PageValue pageValue = new PageValue();
				//获取该主题的作者
				Long id=menu.getId();
				String hql_user =  "from User u where u.userId=?";
				Object[] args_user = new Object[]{menu.getUserId()};
				String nickname="匿名";
				User user = (User) objectDao.findObjectByHqlAndArgs(hql_user, args_user);
				if(user!=null){
					nickname=user.getNickname();
				}
				log.info("菜单："+id+"的作者是："+nickname);
				
				pageValue.setId(id);//菜单的ID
				pageValue.setValueA(menu.getName());//菜单的标题
				pageValue.setValueB(menu.getRemark());//菜单的备注
				pageValue.setValueC(menu.getCreate_datetime());//菜单的时间
				pageValue.setValueD(menu.getType());//菜单的类型
				pageValue.setValueE(menu.getSort());//菜单的排序
				pageValue.setValueF(menu.getVisible());//菜单是否发布
				pageValue.setValueG(nickname);//菜单的作者
				pageValues.add(pageValue);
			}
			return pageValues;
		}
	/***************************菜单列表结束******************************/
	/***************************主题列表开始******************************/
		public void getSearchTopic(){
			String str = "{\"state\":\"error\"}";
				//获取页面传过来的参数
				User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
				if(user==null||!"0".equals(user.getType())){
					str= "{\"state\":\"logon\"}";
					StrutsParamUtils.writeStr(str);
					log.info("用户未登登录");
					return;
				}
				String title = StrutsParamUtils.getPraramValue("title", "");
				String menuId = StrutsParamUtils.getPraramValue("menuId", "0");//0就是查询全部
				String orderBy = StrutsParamUtils.getPraramValue("orderBy", "1");//1就是按时间逆序，2是按时间正序
				String visible = StrutsParamUtils.getPraramValue("visible", "");// 是否发布 
				String page = StrutsParamUtils.getPraramValue("page", "0");//默认查询第一页
				String num = StrutsParamUtils.getPraramValue("num", "15");//默认查询15条数据
				log.info("page:"+page);
				StringBuffer sb =new StringBuffer();
				Map<String,Object> map = new HashMap<String, Object>();
				if(!"".equals(title)){
					sb.append("from Topic t where t.title like :title");
					map.put("title", "%"+title+"%");
				}else{
					sb.append("from Topic t where 1=1");
				}
				if(!"0".equals(menuId)){
					sb.append(" and t.menuId=:menuId");
					map.put("menuId",Long.parseLong(menuId));
				}
				if(!"".equals(visible)){
					sb.append(" and t.visible=:visible");
					map.put("visible", visible);
				}
				if("1".equals(orderBy)){
					sb.append(" order by t.create_datetime desc");
				}
				if("2".equals(orderBy)){
					sb.append(" order by t.create_datetime asc");
				}
				log.info("sql:"+sb.toString());
				Map<String,Object> strMap = new HashMap<String, Object>();
				String getNum = "select count(*) "+sb.toString();
				int count =objectDao.getCount(getNum, map);//总数
				int all_page=count/NUM+1;//总页数
				if(count%NUM==0){
					all_page--;//相当于刚刚够页数 不用加下一页
				}
				strMap.put("count", count);
				strMap.put("all_page", all_page);
				//分页查询
				//定位初始位置:start_page 为0,15,30
				int search_num=Integer.parseInt(num);
				int start_page = Integer.parseInt(page)*search_num;
				List<Topic> topics = objectDao.findPageByHqlAndMap(sb.toString(), map, start_page, search_num);
				//log.info(menus.toString());
				List<PageValue> pageValues = getTopicListPageValue(topics);
				if(pageValues!=null){
					strMap.put("pageValues", pageValues);
				}
				Gson gosn = new Gson();
				str = gosn.toJson(strMap);
				log.info("获取的主题是："+str);
				StrutsParamUtils.writeStr(str);
				return;
			}
			
			private List<PageValue> getTopicListPageValue(List<Topic> topics){
				if(topics==null){
					return null;
				}
				List<PageValue> pageValues = new ArrayList<PageValue>();
				for (Topic topic : topics) {
					PageValue pageValue = new PageValue();
					
					//获取该主题的作者
					Long id=topic.getId();
					//获取该帖子所属菜单
					String hql_menu = "from Menu u where u.id=?";
					Menu menu = (Menu) objectDao.findObjectByHqlAndArgs(hql_menu,new Object[]{topic.getMenuId()});
					String menu_name = "未知";
					if(menu!=null){
						menu_name=menu.getName();
					}
					
					String hql_user =  "from User u where u.userId=?";
					Object[] args_user = new Object[]{topic.getUserId()};
					String nickname="匿名";
					User user = (User) objectDao.findObjectByHqlAndArgs(hql_user, args_user);
					if(user!=null){
						nickname=user.getNickname();
					}
					log.info("主题："+id+"的作者是："+nickname);
					//获取该帖子的热度
					String hql_hot = "select count(*) from RTMsg r where r.type1='1' and r.type2='1' and r.msgId=?";
					Object[] args_hot =  new Object[]{id};
					int hot = objectDao.getCount(hql_hot, args_hot);
					log.info("主题："+id+"的热度是："+hot);
					
					//获取该帖子的浏览量
					String hql_page_view = "select count(*) from RTMsg r where r.type1='2' and r.type2='1' and r.msgId=?";
					Object[] args_page_view =  new Object[]{id};
					int page_view = objectDao.getCount(hql_page_view, args_page_view);
					log.info("主题："+id+"的浏览量是："+page_view);
					pageValue.setId(id);//主题ID
					pageValue.setValueA(topic.getTitle());//主题的标题
					pageValue.setValueB(menu_name);//所属菜单
					pageValue.setValueC(topic.getCreate_datetime());//创建的时间
					pageValue.setValueD(nickname);//创建者是谁
					pageValue.setValueE(hot+"");//点赞数
					pageValue.setValueF(page_view+"");//浏览量
					pageValue.setValueG(topic.getVisible());//状态
					pageValues.add(pageValue);
				}
				return pageValues;
			}
	
	/***************************主题列表结束******************************/
			
			/***************************回复列表开始******************************/
			public void getSearchReply(){
				String str = "{\"state\":\"error\"}";
					//获取页面传过来的参数
					User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
					if(user==null||!"0".equals(user.getType())){
						str= "{\"state\":\"logon\"}";
						StrutsParamUtils.writeStr(str);
						log.info("用户未登登录");
						return;
					}
					String content = StrutsParamUtils.getPraramValue("content", "");
					String orderBy = StrutsParamUtils.getPraramValue("orderBy", "1");//1就是按时间逆序，2是按时间正序
					String visible = StrutsParamUtils.getPraramValue("visible", "");// 是否发布 
					String page = StrutsParamUtils.getPraramValue("page", "0");//默认查询第一页
					String num = StrutsParamUtils.getPraramValue("num", "15");//默认查询15条数据
					StringBuffer sb =new StringBuffer();
					Map<String,Object> map = new HashMap<String, Object>();
					if(!"".equals(content)){
						sb.append("from Reply r where r.content like :content");
						map.put("content", "%"+content+"%");
					}else{
						sb.append("from Reply r where 1=1");
					}
					if(!"".equals(visible)){
						sb.append(" and r.visible=:visible");
						map.put("visible", visible);
					}
					if("1".equals(orderBy)){
						sb.append(" order by r.create_datetime desc");
					}
					if("2".equals(orderBy)){
						sb.append(" order by r.create_datetime asc");
					}
					log.info("sql:"+sb.toString());
					Map<String,Object> strMap = new HashMap<String, Object>();
					String getNum = "select count(*) "+sb.toString();
					int count =objectDao.getCount(getNum, map);//总数
					int all_page=count/NUM+1;//总页数
					if(count%NUM==0){
						all_page--;//相当于刚刚够页数 不用加下一页
					}
					strMap.put("count", count);
					strMap.put("all_page", all_page);
					//分页查询
					//定位初始位置:start_page 为0,15,30
					int search_num=Integer.parseInt(num);
					int start_page = Integer.parseInt(page)*search_num;
					List<Reply> replys = objectDao.findPageByHqlAndMap(sb.toString(), map, start_page, search_num);
					//log.info(replys.toString());
					List<PageValue> pageValues = getReplyListPageValue(replys);
					if(pageValues!=null){
						strMap.put("pageValues", pageValues);
					}
					Gson gosn = new Gson();
					str = gosn.toJson(strMap);
					log.info("获取的回复是："+str);
					StrutsParamUtils.writeStr(str);
					return;
				}
				
				private List<PageValue> getReplyListPageValue(List<Reply> replys){
					if(replys==null){
						return null;
					}
					List<PageValue> pageValues = new ArrayList<PageValue>();
					for (Reply reply : replys) {
						PageValue pageValue = new PageValue();
						
						//获取该主题的作者
						Long id=reply.getId();
						//获取该帖子所属主题
						String hql_topic = "from Topic t where t.id=?";
						Topic topic = (Topic) objectDao.findObjectByHqlAndArgs(hql_topic,new Object[]{reply.getTopicId()});
						String topic_name = "未知";
						if(topic!=null){
							topic_name=topic.getTitle();
						}
						
						String hql_user =  "from User u where u.id=?";
						Object[] args_user = new Object[]{reply.getUserId()};
						String nickname="匿名";
						User user = (User) objectDao.findObjectByHqlAndArgs(hql_user, args_user);
						if(user!=null){
							nickname=user.getNickname();
						}
						log.info("回复："+id+"的作者是："+nickname);
						String hql_to_user =  "from User u where u.userId=?";
						Object[] args_to_user = new Object[]{reply.getToUserId()};
						String to_nickname="匿名";
						User to_user = (User) objectDao.findObjectByHqlAndArgs(hql_to_user, args_to_user);
						if(to_user!=null){
							to_nickname=to_user.getNickname();
						}
						log.info("回复："+id+"的回复对象是："+nickname);
						//获取该回复的热度
						String hql_hot = "select count(*) from RTMsg r where r.type1='1' and r.type2='2' and r.msgId=?";
						Object[] args_hot =  new Object[]{id};
						int hot = objectDao.getCount(hql_hot, args_hot);
						log.info("回复："+id+"的热度是："+hot);
						
						pageValue.setId(id);//回复ID
						pageValue.setValueA(reply.getContent());//回复的内容
						pageValue.setValueB(topic_name);//所属主题
						pageValue.setValueC(reply.getCreate_datetime());//创建的时间
						pageValue.setValueD(nickname);//创建者是谁
						pageValue.setValueE(to_nickname);//回复谁
						pageValue.setValueF(hot+"");//点赞数
						pageValue.setValueG(reply.getVisible());//状态
						pageValues.add(pageValue);
					}
					return pageValues;
				}
		
		/***************************回复列表结束******************************/		
	
	/***************************添加菜单开始******************************/
	/**
	 * 获取有多少个等级
	 */
	public void getMenuTypeNum(){
		String str = "{\"state\":\"error\"}";
		//按时间排序获取最新的9条或者多少条记录
		String hql = "select type from Menu group by type order by type";
		List list = objectDao.findByHqlAndArgs(hql, null);
		if(list!=null){
			Gson gosn = new Gson();
			str = gosn.toJson(list);
		}
		log.info("srt:"+str);
		StrutsParamUtils.writeStr(str);
		//获取最新的帖子
		return;
	}
	//获取序号
	public void getSort(){
		String type = StrutsParamUtils.getPraramValue("type", "1");
		String menuId = StrutsParamUtils.getPraramValue("menuId", "0");
		String str = "{\"state\":\"error\"}";
		//按时间排序获取最新的9条或者多少条记录
		String hql = "select sort from Menu where type=? and menuId=? group by sort order by sort";
		List list = objectDao.findByHqlAndArgs(hql, new Object[]{type,Long.parseLong(menuId)});
		if(list!=null){
			Gson gosn = new Gson();
			str = gosn.toJson(list);
		}else{
			str="[\"0\"]";
		}
		log.info("srt:"+str);
		StrutsParamUtils.writeStr(str);
		//获取最新的帖子
		return;
	}
	
	/**
	 * 获取某一等级的所有菜单
	 */
	@SuppressWarnings("unchecked")
	public void getAllParentMenu(){
		String type = StrutsParamUtils.getPraramValue("type", "1");
		log.info("要获取的菜单等级："+type);
		String str = "{\"state\":\"error\"}";
		//按时间排序获取最新的9条或者多少条记录
		String hql = "from Menu u where  u.type=? order by u.sort,u.create_datetime";
		List<Menu> menus = objectDao.findByHqlAndArgs(hql, new Object[]{type});
		if(menus!=null){
			Gson gosn = new Gson();
			str = gosn.toJson(menus);
		}
		log.info("要获取的菜单等级:"+str);
		StrutsParamUtils.writeStr(str);
		//获取最新的帖子
		return;
	}
	
	/**
	 * 保存菜单
	 */
	public void addMenu(){
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null||!"0".equals(user.getType())){
			StrutsParamUtils.writeStr("logon");
			log.info("用户未登登录");
			return;
		}
		HttpServletRequest request = StrutsParamUtils.getRequest();  
		//获取页面传过来的参数
		String name = StrutsParamUtils.getPraramValue("name", "");//菜单名称
		String remark = StrutsParamUtils.getPraramValue("remark", "");//菜单备注
		String type = StrutsParamUtils.getPraramValue("type", "1");//菜单级别
		String sort = StrutsParamUtils.getPraramValue("sort", "1");//菜单排序
		String menuId = StrutsParamUtils.getPraramValue("menuId", "0");//父级菜单
		String visible = StrutsParamUtils.getPraramValue("visible", "0");//是否发布
		String   userId = user.getUserId();//作者
		String create_datetime = format.format(new Date());
		String imgUrl="";
		if(fileFileName!=null&&!"".equals(fileFileName)){
			String imgType = fileFileName.substring(fileFileName.lastIndexOf(".")+1).toLowerCase();
			String path = "/uploadFile/blog/menu/";
			String uuid = UUID.randomUUID().toString();
			imgUrl = path+uuid+".jpg";
			String truePath = CommonFunction.getApplication().getRealPath(path)+"/"+uuid+".jpg";
			imgUrl=request.getRequestURI().replace(request.getServletPath(), imgUrl);
			log.info("imgUrl:"+imgUrl);
			log.info("truePath:"+truePath);
			if(!"jpg".equals(imgType)
				&&!"jpeg".equals(imgType)
				&&!"png".equals(imgType)
				&&!"gif".equals(imgType)){
				StrutsParamUtils.writeStr("img_error");
				log.info("用户上传图片格式不对");
				return;
			}
			//获取页面传过来的参数
			if(file!=null){
				log.info("有文件上传");
				File destFile = new File(truePath);
				try {
					FileUtils.copyFile(file, destFile);
				} catch (IOException e) {
					StrutsParamUtils.writeStr("file_error");
					log.info("上传文件失败");
					return;
				}
			}
		}
		
		Menu menu = new Menu();
		menu.setCreate_datetime(create_datetime);
		menu.setImgUrl(imgUrl);
		menu.setMenuId(Long.parseLong(menuId));
		menu.setName(name);
		menu.setSort(sort);
		menu.setType(type);
		menu.setUserId(userId);
		menu.setVisible(visible);
		menu.setRemark(remark);
		objectDao.saveOrUpdate(menu);
		StrutsParamUtils.writeStr("success");
		log.info("保存菜单成功");
		//修改父级菜单的value字段为1（这里用作是否有子菜单）
		return;
	}
	/***************************添加菜单结束******************************/
	/***************************添加主题开始******************************/
	//获取发布的所有菜单
	public void getAllMenu(){
		String str = "{\"state\":\"error\"}";
		//获取所有可见的菜单，先按菜单类型排序，相同类型就按序号排序，再按时间排序
		String hql = "from Menu u where u.visible='1' order by u.type,u.sort,u.create_datetime";
		List<Menu> menus = objectDao.findByHqlAndArgs(hql,null);
		if(menus!=null){
			Gson gosn = new Gson();
			str = gosn.toJson(menus);
		}
		log.info("要获菜单:"+str);
		StrutsParamUtils.writeStr(str);
		//获取最新的帖子
		return;
		
	}
	//添加主题
	public void addTopic(){
		
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null||!"0".equals(user.getType())){
			StrutsParamUtils.writeStr("logon");
			log.info("用户未登登录");
			return;
		}
		HttpServletRequest request = StrutsParamUtils.getRequest();  
		//获取页面传过来的参数
		String topic_menu = StrutsParamUtils.getPraramValue("topic_menu", "");//主题所属菜单
		String title = StrutsParamUtils.getPraramValue("title", "");//主题的标题
		String topic_content = StrutsParamUtils.getPraramValue("topic_content", "0");//主题内容
		String visible = StrutsParamUtils.getPraramValue("visible", "0");//是否发布
		
		String   userId = user.getUserId();//作者
		String create_datetime = format.format(new Date());
		String imgUrl="";
		if(fileFileName!=null&&!"".equals(fileFileName)){
			String imgType = fileFileName.substring(fileFileName.lastIndexOf(".")+1).toLowerCase();
			String path = "/uploadFile/blog/topic/";
			String uuid = UUID.randomUUID().toString();
			imgUrl = path+uuid+".jpg";
			String truePath = CommonFunction.getApplication().getRealPath(path)+"/"+uuid+".jpg";
			imgUrl=request.getRequestURI().replace(request.getServletPath(), imgUrl);
			log.info("imgUrl:"+imgUrl);
			log.info("truePath:"+truePath);
			if(!"jpg".equals(imgType)
				&&!"jpeg".equals(imgType)
				&&!"png".equals(imgType)
				&&!"gif".equals(imgType)){
				StrutsParamUtils.writeStr("img_error");
				log.info("用户上传图片格式不对");
				return;
			}
			//获取页面传过来的参数
			if(file!=null){
				log.info("有文件上传");
				File destFile = new File(truePath);
				try {
					FileUtils.copyFile(file, destFile);
				} catch (IOException e) {
					StrutsParamUtils.writeStr("file_error");
					log.info("上传文件失败");
					return;
				}
			}
		}
		
		Topic topic = new Topic();
		topic.setTitle(title);
		//这里要将内容保存为静态
		//获取第一段保存为摘要
		String content =topic_content.substring(0, topic_content.indexOf("<br />"));
		topic.setContent(content);
		//保存为静态页面
		String path = "/uploadFile/blog/topics/"+topic_menu+"/";
		String uuid = UUID.randomUUID().toString();
		String contentUrl = path+uuid+".html";
		String truePath = CommonFunction.getApplication().getRealPath(path)+"/"+uuid+".html";
		System.out.println("truePath:"+truePath);
		try {
			FileUtils.writeStringToFile(new File(truePath), topic_content, "UTF-8");
			contentUrl=request.getRequestURI().replace(request.getServletPath(), contentUrl);
			topic.setValue(contentUrl);
		} catch (IOException e) {
			//若是保存不成功，则，将内容保存在content
			topic.setContent(topic_content);
			e.printStackTrace();
		}
		
		topic.setImgUrl(imgUrl);
		topic.setMenuId(Long.parseLong(topic_menu));
		topic.setCreate_datetime(create_datetime);
		topic.setUserId(userId);
		topic.setVisible(visible);
		topic.setHot(0l);//默认为0
		objectDao.saveOrUpdate(topic);
		//这里还需要将这个版块下面的主题数目加一，。如果redis中有的话
		String key = "menu_topic_count:"+topic_menu;
		String menu_topic_count=cacheUtil.get(key);
		if(menu_topic_count!=null){
			//这里才去处理
			cacheUtil.put(key,(Integer.parseInt(menu_topic_count)+1)+"");
		}
		StrutsParamUtils.writeStr("success");
		log.info("保存主题成功");
		return;
	}
	/***************************添加主题结束
	 * @throws IOException ******************************/
	//文件上传 这个是自己上传不用限定格式
	public void toUpload() throws IOException{
		log.info("进来上传图片");
			User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
			if(user==null||!"0".equals(user.getType())){
				StrutsParamUtils.writeStr("logon");
				StrutsParamUtils.writeStr("用户未登登录");
				return;
			}
			String fileUrl="";
			log.info("uploadFileName:"+uploadFileName);
			log.info("uploadContentType:"+uploadContentType);
			if(uploadFileName!=null&&!"".equals(uploadFileName)){
				String imgType = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1).toLowerCase();
				String path = "/uploadFile/blog/imges/"+CommonFunction.getDateTime()+"/";
				String uuid = UUID.randomUUID().toString();
				fileUrl = path+uuid+"."+imgType;
				String truePath = CommonFunction.getApplication().getRealPath(path)+"/"+uuid+"."+imgType;
				//获取页面传过来的参数
				if(upload!=null){
					log.info("有文件上传");
					File destFile = new File(truePath);
					try {
						FileUtils.copyFile(upload, destFile);
					} catch (IOException e) {
						StrutsParamUtils.writeStr("file_error");
						StrutsParamUtils.writeStr("上传文件失败");
						return;
					}
				}
			}
			String callback =StrutsParamUtils.getPraramValue("CKEditorFuncNum", "");
			//StrutsParamUtils.writeStr("<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+ callback + ",'" + fileUrl + "','')</script>");
			HttpServletRequest request = StrutsParamUtils.getRequest();  
			HttpServletResponse response = StrutsParamUtils.getResponse();  
	        response.setCharacterEncoding("GBK");  
	        PrintWriter out = response.getWriter(); 
			fileUrl=request.getRequestURI().replace(request.getServletPath(), fileUrl);
			log.info("fileUrl:"+fileUrl);
			out.println("<script type=\"text/javascript\">");    
	        out.println("window.parent.CKEDITOR.tools.callFunction("+ callback + ",'" + fileUrl + "','')");    
	        out.println("</script>");  
			log.info("上传图片成功");
			return;
	}
	//修改菜单修改功能没不写先
	/*public void getMenuFromId(){
		String str = "{\"state\":\"error\"}";
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null){
			StrutsParamUtils.writeStr("{\"state\":\"error\"}");
			log.info("用户未登录");
			return;
		}
		String id = StrutsParamUtils.getPraramValue("id", "");//要修改的主题ID

	}*/
	
	/**
	 * 刷新内存，用户必须已经登录才可以刷，用户修改完菜单后都需要刷新内存
	 */
	public void flushApplication(){
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null||!"0".equals(user.getType())){
			StrutsParamUtils.writeStr("logon");
			log.info("用户未登录");
			return;
		}
		//刷memory
		CommonFunction.flushApplication();
		//用户刷内存成功
		log.info("用户刷新内存成功");
		StrutsParamUtils.writeStr("success");
	}
}
