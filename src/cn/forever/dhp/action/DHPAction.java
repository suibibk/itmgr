package cn.forever.dhp.action;

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
import org.apache.log4j.chainsaw.Main;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.forever.blog.action.BlogManageAction;
import cn.forever.blog.model.Menu;
import cn.forever.blog.model.PageValue;
import cn.forever.blog.model.Reply;
import cn.forever.blog.model.Topic;
import cn.forever.blog.model.User;
import cn.forever.blog.service.BlogManageService;
import cn.forever.dao.ObjectDao;
import cn.forever.dhp.model.DHP;
import cn.forever.dhp.service.DHPService;
import cn.forever.redis.CacheUtil;
import cn.forever.utils.CommonFunction;
import cn.forever.utils.Const;
import cn.forever.utils.Encrypt;
import cn.forever.utils.StrutsParamUtils;

import com.google.gson.Gson;
/**
 * 20170508
 * @author lwh
 * 用于博客管理
 */
@ParentPackage(value = "struts-default")
@Namespace(value = "/dhp")
@Action(value = "dhpAction",results = {
		@Result(name = "dhp",location = "/WEB-INF/dhp/dhp.jsp"),
		@Result(name = "login",location = "/WEB-INF/dhp/login.jsp")
})
public class DHPAction {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger log = Logger.getLogger(DHPAction.class);

	@Resource(name="objectDao")
	private ObjectDao objectDao;
	
	@Resource(name="dhpService")
	private DHPService dhpService;
	
	//加入缓存
	@Resource(name="cacheUtil")
	private CacheUtil cacheUtil;
	
	/**
	 * 去到蛋黄盘
	 * @return
	 */
	public String dhp(){
		HttpServletRequest request = StrutsParamUtils.getRequest();
		HttpSession session = request.getSession();
		//只有后台管理的会调用这个（然后，注销的话肯定是）
		User user = (User) session.getAttribute("user");
		if(user==null){
			log.info("用户没有登录，去到登录页面");
			return "login";
		}else{
			List<DHP> dhps=dhpService.getDHPs("0", user.getUserId());
			if(dhps==null){
				log.info("用户没有注册，网盘没有初始化");
				return "login";
			}
			request.setAttribute("dhpId", dhps.get(0).getDhpId());
			return "dhp";
		}
	}
	
	public void getDHPs(){
		String str = "{\"state\":\"error\"}";
		//先判断用户有没有登陆
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null){
			str = "{\"state\":\"logon\"}";
			StrutsParamUtils.writeStr(str);
			log.info("用户未登登录");
			return;
		}
		//否则用户以及登陆，获取用户id
		String   userId = user.getUserId();//作者
		//获取父级ID，默认是0
		String supId = StrutsParamUtils.getPraramValue("supId", "");
		if(!"".equals(supId)){
			List<DHP> dhps =dhpService.getDHPs(supId, userId);
			if(dhps!=null){
				Gson gosn = new Gson();
				str = gosn.toJson(dhps);
			}
		}
		log.info("要获的蛋黄盘内容:"+str);
		StrutsParamUtils.writeStr(str);
		//获取最新的帖子
		return;
	}
	
	public void createWjj(){
		String str = "{\"state\":\"error\"}";
		//先判断用户有没有登陆
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null){
			str = "{\"state\":\"logon\"}";
			StrutsParamUtils.writeStr(str);
			log.info("用户未登登录");
			return;
		}
		//否则用户以及登陆，获取用户id
		String   userId = user.getUserId();//作者
		//获取父级ID，默认是0
		String dhpId = StrutsParamUtils.getPraramValue("dhpId", "0");
		DHP dhp =dhpService.getDHP(dhpId, userId);
		if(dhp==null||!Const.DHP_TYPE_WJJ.equals(dhp.getType())){//必须是文件夹
			str = "{\"state\":\"noWjj\"}";
			StrutsParamUtils.writeStr(str);
			log.info("找不到该文件夹");
			return;
		}
		//这里就表示可以新建文件夹
		String wjj = StrutsParamUtils.getPraramValue("wjj", "");
		if("".equals(wjj)){
			str = "{\"state\":\"noName\"}";
			StrutsParamUtils.writeStr(str);
			log.info("文件夹名称必须传");
			return;
		}
		//这里就可以去新建文件夹啦
		DHP newdhp = new DHP();
		newdhp.setCreate_datetime(format.format(new Date()));
		newdhp.setDhpId(CommonFunction.getUUID());
		newdhp.setName(wjj);
		newdhp.setType(Const.DHP_TYPE_WJJ);
		newdhp.setSubType(Const.DHP_SUBTYPE_WJJ);
		newdhp.setSupId(dhpId);
		newdhp.setUserId(userId);
		newdhp.setVisible("1");
		newdhp.setOperation(CommonFunction.getSubTypeByFileType(Const.DHP_SUBTYPE_WJJ));
		objectDao.saveOrUpdate(newdhp);
		str = "{\"state\":\"success\"}";
		StrutsParamUtils.writeStr(str);
		log.info("创建文件夹成功");
		return;
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
	/**
	 * 用户去登录
	 */
	public void register(){
		//获取页面传过来的参数
		String username2 = StrutsParamUtils.getPraramValue("username2", "");
		String password2 = StrutsParamUtils.getPraramValue("password2", "");
		String password3 = StrutsParamUtils.getPraramValue("password3", "");
		log.info("username2="+username2+"|password2="+password2+"|password3="+password3);
		if("".equals(username2)||"".equals(password2)||"".equals(password3)){
			StrutsParamUtils.writeStr("error");
			log.info("用户名或密码为空");
			return;
		}
		if(!password2.equals(password3)){
			StrutsParamUtils.writeStr("no_same");
			log.info("两次输入的密码不同");
			return;
		}
		//根据用户名和密码去数据库中查找是否有记录（这里不需要限定用户的类型）
		String hql = "from User u where u.username=?";
		Object[] args = new Object[]{username2};
		User user1 = (User) objectDao.findObjectByHqlAndArgs(hql, args);
		if(user1!=null){
			//将用户放到session中
			StrutsParamUtils.writeStr("isHave");
			log.info("用户名已经存在");
			return;
		}else{
			String encrypt_password = Encrypt.encrypt(password2);
			String userId =CommonFunction.getUUID();
			User user= new User();
			user.setUserId(userId);
			user.setCreate_datetime(format.format(new Date()));
			user.setAge("");
			user.setNickname(username2);
			user.setUsername(username2);
			user.setPassword(encrypt_password);
			user.setRemark("蛋黄盘用户");
			user.setType("1");//网盘用户
			user.setVisible("0");//不允许，需要管理员审核
			objectDao.saveOrUpdate(user);
			//这里就可以去新建文件夹啦
			DHP newdhp = new DHP();
			newdhp.setCreate_datetime(format.format(new Date()));
			newdhp.setDhpId(CommonFunction.getUUID());
			newdhp.setName("蛋黄盘");
			newdhp.setType(Const.DHP_TYPE_WJJ);
			newdhp.setSubType(Const.DHP_SUBTYPE_WJJ);
			newdhp.setSupId("0");
			newdhp.setUserId(userId);
			newdhp.setVisible("1");
			objectDao.saveOrUpdate(newdhp);
			//初始化蛋黄盘
			log.info("用户注册成功");
			StrutsParamUtils.writeStr("success");
			return;
		}
	}
	public static void main(String[] args) {
		System.out.println(CommonFunction.getUUID());
	}
}
