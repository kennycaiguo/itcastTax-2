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
		//�жϵ�ǰ�����ַ�Ƿ��ǵ�¼��ַ
		if(!uri.contains("sys/login_")){
			//�ǵ�¼����
			if(request.getSession().getAttribute(Constant.USER)!=null){
				//˵���Ѿ���¼��
				//�ж��Ƿ������˰������ϵͳ
				if(uri.contains("/nsfw/")){
					//˵��������˰������ϵͳ
					User user=(User)request.getSession().getAttribute(Constant.USER);
					PermissionCheck pc=new PermissionCheckImpl();
					if(pc.isAccessible(user,"nsfw")){
						//˵����Ȩ�ޣ�����
						chain.doFilter(request, response);
					}else{
						//û��Ȩ�ޣ���ת��û��Ȩ����ʾ����
						response.sendRedirect(request.getContextPath()+"/sys/login_toNoPermissionUI.action");
					}
						
				}else{
					//�Ƿ�����˰������ϵͳ��ֱ�ӷ���
					chain.doFilter(request, response);
				}
				
			}else{
				//û�е�¼,��ת����¼����
				response.sendRedirect(request.getContextPath()+"/sys/login_toLoginUI.action");
			}
		}else{
			//��¼����ֱ�ӷ���
			chain.doFilter(request, response);
		}
	}
 
 
	@Override
	public void init(FilterConfig chain) throws ServletException {
		 
	}
}