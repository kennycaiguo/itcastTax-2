package cn.itcast.core.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

public class SysResultAction extends StrutsResultSupport{

	@Override
	protected void doExecute(String arg0, ActionInvocation arg1)
			throws Exception {
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response= ServletActionContext.getResponse();
		//得到报错时候上层的action类
		BaseAction action=(BaseAction)arg1.getAction();
		
		//request.response,action类你都得到了，可以做你想做的任何操作了
		System.out.println("进入了SysResultAction...");
	}

}
