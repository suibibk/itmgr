package cn.forever.dhp.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.forever.blog.model.User;
import cn.forever.dao.ObjectDao;
import cn.forever.dhp.model.DHP;
import cn.forever.dhp.service.DHPService;
import cn.forever.utils.CommonFunction;
import cn.forever.utils.Const;
import cn.forever.utils.StrutsParamUtils;
/**
 * 20170508
 * @author lwh
 * 用于博客展示
 */
@ParentPackage(value = "struts-default")
@Namespace(value = "/file")
@Action(value = "fileAction",results = {
		@Result(name = "music",location = "/WEB-INF/file/music/music.jsp"),
		@Result(name = "fail",location = "/index.jsp")
})
public class FileAction {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger log = Logger.getLogger(FileAction.class);
	@Resource(name="objectDao")
	private ObjectDao objectDao;
	
	@Resource(name="dhpService")
	private DHPService dhpService;
	//文件上传
	private File file;//对应的就是表单中文件上传的那个输入域的名称，Struts2框架会封装成File类型的
    private String fileFileName;//   上传输入域FileName  文件名
    private String fileContentType;// 上传文件的MIME类型
    
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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

	//文件上传
	public void uploadFile(){
		User user = (User) StrutsParamUtils.getRequest().getSession().getAttribute("user");
		if(user==null){
			StrutsParamUtils.writeStr("logon");
			log.info("用户未登登录");
			return;
		}
		//文件类型（1是音乐，2是视频，3是文档，4，图片，5是压缩文件）
		String dhpId = StrutsParamUtils.getPraramValue("dhpId", "0");//上级目录
		String   userId = user.getUserId();//作者
		DHP dhp =dhpService.getDHP(dhpId, userId);
		if(dhp==null||!Const.DHP_TYPE_WJJ.equals(dhp.getType())){//必须是文件夹
			StrutsParamUtils.writeStr("file_error");
			log.info("上传文件失败");
			return ;
		}
		//文件保存路径
		String filePath="";
		String subType="0";//默认是未识别的类型
		Long size =0L;
		if(fileFileName!=null&&!"".equals(fileFileName)){
			//文件的后缀
			String fileType = fileFileName.substring(fileFileName.lastIndexOf(".")+1);
			//String fileName = fileFileName.substring(0,fileFileName.lastIndexOf(".")+1);
			size=file.length();
			if(size>1610612736){
				StrutsParamUtils.writeStr("file_size");
				log.info("上传文件失败,文件超过1.5G");
				return ;
			}
			subType =CommonFunction.getSubTypeByFileType(fileType);
			//用户的内容放置路径，按用户和文件类型划分
			String path= "/uploadFile/dhp/"+userId+"/"+subType+"/";
			String uuid=CommonFunction.getUUID();
			filePath = path+uuid+"."+fileType;
			String truePath = CommonFunction.getApplication().getRealPath(path)+"/"+uuid+"."+fileType;
			log.info("size:"+size);//这个是保存在数据库的路径
			log.info("fileFileName:"+fileFileName);//这个是保存在数据库的路径
			log.info("fileType:"+fileType);//这个是保存在数据库的路径
			log.info("subType:"+subType);//这个是保存在数据库的路径
			log.info("filePath:"+filePath);//这个是保存在数据库的路径
			log.info("truePath:"+truePath);//这个是保存在磁盘上的真实路径
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
			//这里就可以去新建文件夹啦
			DHP newdhp = new DHP();
			newdhp.setCreate_datetime(format.format(new Date()));
			newdhp.setDhpId(CommonFunction.getUUID());
			newdhp.setName(fileFileName);
			newdhp.setType(Const.DHP_TYPE_WJ);
			newdhp.setSubType(subType);
			newdhp.setSupId(dhpId);
			newdhp.setUserId(userId);
			newdhp.setVisible("1");
			newdhp.setSize(size+"");
			newdhp.setPath(filePath);
			newdhp.setOperation(CommonFunction.getOperationBysubType(subType));
			objectDao.saveOrUpdate(newdhp);
			StrutsParamUtils.writeStr("success");
			log.info("保存文件成功");
		}else{
			StrutsParamUtils.writeStr("noFile");
			log.info("文件不存在");
			return;
		}
		
	}
}
