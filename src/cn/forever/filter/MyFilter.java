package cn.forever.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.forever.utils.CommonFunction;
import cn.forever.utils.Const;

public class MyFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request1 = (HttpServletRequest) request;
		//HttpServletResponse response1 = (HttpServletResponse)response;
		
		String url =FilterUtil.getUrl(request1);
		//判断链接是否包含蛋黄盘的内容，包含的话要判断是否登录
		if(url.contains(Const.DAN_HUANG)){
			//判断用户是否登录，没有登录的话将会重订向没有权限
			//if(!FilterUtil.isLogin(request1)){
				//跳转到登录界面(不可以直接访问)
				request1.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			//}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
}
