package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.content.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{

	User user;
	@Resource
	private UserService userService;
	private String loginResult;
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	//��ת����¼ҳ��
	public String toLoginUI(){
		return "loginUI";
	}
	//��¼
	public String login(){
		if(user!=null){
			if(StringUtils.isNoneBlank(user.getAccount())
					&&StringUtils.isNoneBlank(user.getPassword())){
				//�����û����˺ź������ѯ�û��б�
				List<User> list=userService.findUserByAccountAndPassword(user.getAccount(),user.getPassword());
				if(list!=null&&list.size()>0){//˵����¼�ɹ�
					//1����¼�ɹ�
					User user=list.get(0);
					//1.1��***
					//1.2�����û���Ϣ���浽session��
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//1.3�����û���¼��¼����־�ļ�
					Log log=LogFactory.getLog(getClass());
					log.info("�û�����Ϊ:"+user.getName()+"���û���¼��ϵͳ");
					//1.4���ض�����ת����ҳ
					return "home";
				}else{
					loginResult="�˺Ż����벻��ȷ!";
				}
			}else{
				loginResult="�˺Ż����벻��Ϊ��!";
			}
		}else{
			loginResult="�������˺ź�����!";
		}
		return toLoginUI();
	}	
}
