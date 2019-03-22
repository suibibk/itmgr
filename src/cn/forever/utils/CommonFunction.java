package cn.forever.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import cn.forever.blog.model.Memory;
import cn.forever.blog.model.Menu;
import cn.forever.dao.ObjectDao;
import cn.forever.redis.CacheUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 20170421 lwh
 * 公共方法 获取access_token 获取openId等等的
 */
public class CommonFunction {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	private static ServletContext application;
	public static void setApplication(ServletContext application){
		CommonFunction.application=application;
	}
	public static ServletContext getApplication(){
		return application;
	}
	/**
	 * 刷入内存，刷Memory表和菜单
	 * @param objectDao
	 */
	public static void flushApplication(){
		CacheUtil cacheUtil=(CacheUtil) SpringBeanManager.getBean("cacheUtil");
		ObjectDao objectDao=(ObjectDao) SpringBeanManager.getBean("objectDao");
		loadingMemoryToApplication(cacheUtil,objectDao);
		loadingMenuToApplication(cacheUtil,objectDao);
	}
	
	/**
	 * 将Memory中的记录装载进application中
	 */
	@SuppressWarnings("unchecked")
	private static void loadingMemoryToApplication(CacheUtil cacheUtil,ObjectDao objectDao){
		//获取memory表中所有有效的记录
		String hql = "select key_name from Memory m where m.visible='1' group by key_name";
		List list = objectDao.findByHqlAndArgs(hql, null);
		if(list!=null){
			for (int i = 0; i <list.size(); i++) {
				Object object = list.get(i);
				//更加key_name去或去记录按序号排序
				String key_name=(String)object;
				String hql_memory = "from Memory m where m.visible='1' and m.key_name=? order by m.sort+0";
				Object[] args_memory = new Object[]{key_name};
				List<Memory> memorys = objectDao.findByHqlAndArgs(hql_memory, args_memory);
				if(memorys!=null){
					//将这个Key对应的值刷入内存
					cacheUtil.put(key_name, memorys);
					System.out.println("key_name:"+key_name+"的值："+memorys.toString()+"刷入REDIS成功");
				}
			}
		}
	}
	/**
	 * 将博客的菜单按等级加载到内存中去
	 * @param objectDao
	 */
	@SuppressWarnings("unchecked")
	private static void loadingMenuToApplication(CacheUtil cacheUtil,ObjectDao objectDao){
		String type="1";
		String menuId="0";
		String hql = "from Menu m where m.type=? and m.menuId=? and m.visible='1' order by m.sort+0,m.create_datetime";
		Object[] args = new Object[]{type,Long.parseLong(menuId)};
		List<Menu>menus = objectDao.findByHqlAndArgs(hql, args);
		if(menus!=null){
			cacheUtil.put(Const.MENUS_KEY, menus);
		}
		System.out.println("加载菜单进入内存完成");
	}
	public static Object openConnection(String link){
		if(link==null||"".equals(link)){
			System.out.println("链接不存在");
			return null;
		}
		HttpURLConnection conn=null;
		InputStream is=null;
		BufferedInputStream bis=null;
		try {
			URL url=new URL(link);
			conn=(HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
            conn.setRequestMethod("GET");
			is=conn.getInputStream();
			bis = new BufferedInputStream(is);
			byte[] bytes = new byte[1024];
			int len = -1;
			StringBuilder sb = new StringBuilder();
			while((len=bis.read(bytes))!=-1){
				sb.append(new String(bytes,0,len));
			}
			String json = sb.toString();
			JsonParser parser = new JsonParser();
			JsonObject object=(JsonObject)parser.parse(json);
            bis.close();
            is.close();
            conn.disconnect();
			System.out.println("链接返回的值："+object);
			return object;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getDateTime(){
		return format.format(new Date());
	}
	public static String getUrl(){
		HttpServletRequest request=StrutsParamUtils.getRequest();
		String q = request.getQueryString();
		String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
		if(q!=null){
			url=url+"?"+q;
		}
		return url;
	}
	//重定向到一个链接
	public static void redirect(String url){
		try {
 			if(url != null && !url.equals("")){
 				System.out.println("要重定向的链接："+url);
 				StrutsParamUtils.getResponse().sendRedirect(url);
 			}
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 		return ;
	}
	
	
	public static String getUUID(){
		return (UUID.randomUUID()+"").replace("-", "");
	}
	/**
	 * 根据类型，返回对应的编号
	 * @param fileType exe  js  jsp  txt...
	 * @return 对应的Const的DHP_SUBTYPE...
	 */
	public static String getSubTypeByFileType(String fileType){
		String txt ="txt";
		String exe ="exe";
		String code="js,jsp,java,css,properties,xml";
		String html="html";
		String img="jpg,png,gif,jpeg";
		String music="mp3,wma,rm,wav,midi,ape,flac";
		String video="wmv,asf,asx,rm,rmvb,mp4,3gp,mov,m4vavi,dat,mkv,flv,vob";
		String pdf="pdf";
		String word="doc,docx";
		String zip="zip,rar,jar,7z";
		//转换成小写
		fileType=fileType.toLowerCase();
		if(txt.contains(fileType)){
			return Const.DHP_SUBTYPE_TXT;
		}else if(exe.contains(fileType)){
			return Const.DHP_SUBTYPE_EXE;
		}else if(code.contains(fileType)){
			return Const.DHP_SUBTYPE_CODE;
		}else if(html.contains(fileType)){
			return Const.DHP_SUBTYPE_HTML;
		}else if(img.contains(fileType)){
			return Const.DHP_SUBTYPE_IMG;
		}else if(music.contains(fileType)){
			return Const.DHP_SUBTYPE_MUSIC;
		}else if(video.contains(fileType)){
			return Const.DHP_SUBTYPE_VIDEO;
		}else if(pdf.contains(fileType)){
			return Const.DHP_SUBTYPE_PDF;
		}else if(word.contains(fileType)){
			return Const.DHP_SUBTYPE_WORD;
		}else if(zip.contains(fileType)){
			return Const.DHP_SUBTYPE_ZIP;
		}else{
			return Const.DHP_SUBTYPE_OTHER;
		}
	}
	
	/**
	 * 根据类型，找寻有的功能
	 * @param fileType
	 * @return
	 */
	public static String getOperationBysubType(String subType){
		 String[] DHP_OPERATION_DOWN = new String[]{"2","3","4","5","6","7","8","9","10","11","0"};
		 String[] DHP_OPERATION_DELETE = new String[]{"1","2","3","4","5","6","7","8","9","10","11","0"};
		 String[] DHP_OPERATION_CHECK = new String[]{"2","3","5","6","7","8","9"};
		 String[] DHP_OPERATION_VIDEO = new String[]{"4"};
		 String[] DHP_OPERATION_LISTEN = new String[]{"5"};
		 String OPERATION="";
		 for (String string : DHP_OPERATION_DOWN) {
			if(subType.equals(string)){
				OPERATION+=Const.DHP_OPERATION_DOWN;
				break;
			}
		 }
		 for (String string : DHP_OPERATION_DELETE) {
			 if(subType.equals(string)){
					OPERATION+=Const.DHP_OPERATION_DELETE;
					break;
				}
		 }
		 for (String string : DHP_OPERATION_CHECK) {
			 if(subType.equals(string)){
					OPERATION+=Const.DHP_OPERATION_CHECK;
					break;
				}
		 }
		 for (String string : DHP_OPERATION_VIDEO) {
			 if(subType.equals(string)){
					OPERATION+=Const.DHP_OPERATION_VIDEO;
					break;
				}
		 }
		 for (String string : DHP_OPERATION_LISTEN) {
			 if(subType.equals(string)){
				 OPERATION+=Const.DHP_OPERATION_LISTEN;
				 break;
			 }
		 }
		 return OPERATION;
	}
}





