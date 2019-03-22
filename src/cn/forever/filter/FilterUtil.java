package cn.forever.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * @author forever
 *
 */
public class FilterUtil {
	/**
	 * 获取URL
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request){
		String q = request.getQueryString();
		String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
		if(q!=null){
			url=url+"?"+q;
		}
		System.out.println("用户访问的URL是："+url);
		return url;
	}
	
	/**
	 * 判断用户是否已经登录
	 */
	public static Boolean isLogin(HttpServletRequest request){
		HttpSession session  =request.getSession();
		Object object = session.getAttribute("user");
		if(object==null){
			System.out.println("用户没有登录");
			return false;
		}else{
			System.out.println("用户已经登录");
			return true;
		}
	}
}
