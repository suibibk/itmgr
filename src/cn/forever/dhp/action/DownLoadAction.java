package cn.forever.dhp.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.forever.blog.model.User;
import cn.forever.blog.service.BlogManageService;
import cn.forever.dao.ObjectDao;
import cn.forever.dhp.model.DHP;
import cn.forever.dhp.service.DHPService;
import cn.forever.redis.CacheUtil;
import cn.forever.utils.CommonFunction;
import cn.forever.utils.StrutsParamUtils;
/**
 * 20170508
 * @author lwh
 * 用于博客管理
 */
@ParentPackage(value = "struts-default")
@Namespace(value = "/dhp")
@Action(value = "downLoadAction" ,results ={@Result(
				// result 名
				name = "success", 
				// result 类型
				type = "stream",
				params = {
				// 下载的文件格式
				"contentType", "application/octet-stream",   
				// 调用action对应的方法
				"inputName", "inputStream",   
				// HTTP协议，使浏览器弹出下载窗口
				"contentDisposition", "attachment;filename=\"${fileName}\"",   
				// 文件大小
				"bufferSize", "10240"}  
				)  ,
				@Result(name = "login",location = "/WEB-INF/blog/blogManage/common/login.jsp")}
)

public class DownLoadAction{
	private static final Logger log = Logger.getLogger(DownLoadAction.class);
	@Resource(name="objectDao")
	private ObjectDao objectDao;
	//加入缓存
	@Resource(name="cacheUtil")
	private CacheUtil cacheUtil;
	
	@Resource(name="dhpService")
	private DHPService dhpService;
	
	@Resource(name="blogManageService")
	private BlogManageService blogManageService;
	
	/**  
	* 下载文件名
	* 对应annotation注解里面的${fileName}，struts 会自动获取该fileName
	*/  
	private String fileName;   
	public String getFileName() {   
	        return fileName;   
	}   
	public void setFileName(String fileName) {   
            this.fileName = fileName;   
    }  
	private InputStream inputStream;
	public String downLoad(){
		//判断用户是否登录
		//只有后台管理的会调用这个（然后，注销的话肯定是）
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null){
			log.info("用户没有登录，去到登录页面");
			return "login";
		}
		String dhpId =  StrutsParamUtils.getPraramValue("dhpId", "");
		DHP dhp = dhpService.getDHP(dhpId, user.getUserId());
		if(dhp!=null&&"2".equals(dhp.getType())){//如果存在，且是文件
			try {
				String path =dhp.getPath();
				fileName=dhp.getName();
				fileName = new String(fileName.getBytes(), "ISO8859-1");
				//setFileName(fileName);
				String truePath = CommonFunction.getApplication().getRealPath(path);
				log.info("truePath:"+truePath);
				inputStream=FileUtils.openInputStream(new File(truePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";
	}
	 /**  
	* 获取下载流
	* 对应 annotation 注解里面的 "inputName", "inputStream"
	* 假如 annotation 注解改为 "inputName", "myStream"，则下面的方法则应改为：getMyStream
	* @return InputStream  
	*/  
	public InputStream getInputStream() {   
		return inputStream;
	}   
}
