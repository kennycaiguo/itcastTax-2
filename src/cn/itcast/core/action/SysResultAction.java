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
		//�õ�����ʱ���ϲ��action��
		BaseAction action=(BaseAction)arg1.getAction();
		
		//request.response,action���㶼�õ��ˣ����������������κβ�����
		System.out.println("������SysResultAction...");
	}

}
