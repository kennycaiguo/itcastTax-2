package cn.itcast.core.filter;

import java.io.IOException;
 




import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.core.content.Constant;
import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.core.permission.impl.PermissionCheckImpl;
import cn.itcast.nsfw.user.entity.User;
 
 
 
 
public class LoginFilter implements Filter {
 
 
	@Override
	public void destroy() {
		 
	}
 
 
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		String uri=request.getRequestURI();
		//判断当前请求地址是否是登录地址
		if(!uri.contains("sys/login_")){
			//非登录请求
			if(request.getSession().getAttribute(Constant.USER)!=null){
				//说明已经登录过
				//判断是否访问纳税服务子系统
				if(uri.contains("/nsfw/")){
					//说明访问纳税服务子系统
					User user=(User)request.getSession().getAttribute(Constant.USER);
					PermissionCheck pc=new PermissionCheckImpl();
					if(pc.isAccessible(user,"nsfw")){
						//说明有权限，放行
						chain.doFilter(request, response);
					}else{
						//没有权限，跳转到没有权限提示界面
						response.sendRedirect(request.getContextPath()+"/sys/login_toNoPermissionUI.action");
					}
						
				}else{
					//非访问纳税服务子系统，直接放行
					chain.doFilter(request, response);
				}
				
			}else{
				//没有登录,跳转到登录界面
				response.sendRedirect(request.getContextPath()+"/sys/login_toLoginUI.action");
			}
		}else{
			//登录请求，直接放行
			chain.doFilter(request, response);
		}
	}
 
 
	@Override
	public void init(FilterConfig chain) throws ServletException {
		 
	}
}