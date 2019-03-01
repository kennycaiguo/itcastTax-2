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
	//跳转到登录页面
	public String toLoginUI(){
		return "loginUI";
	}
	//登录
	public String login(){
		if(user!=null){
			if(StringUtils.isNoneBlank(user.getAccount())
					&&StringUtils.isNoneBlank(user.getPassword())){
				//根据用户的账号和密码查询用户列表
				List<User> list=userService.findUserByAccountAndPassword(user.getAccount(),user.getPassword());
				if(list!=null&&list.size()>0){//说明登录成功
					//1、登录成功
					User user=list.get(0);
					//1.1、***
					//1.2、将用户信息保存到session中
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//1.3、将用户登录记录到日志文件
					Log log=LogFactory.getLog(getClass());
					log.info("用户名称为:"+user.getName()+"的用户登录了系统");
					//1.4、重定向跳转到首页
					return "home";
				}else{
					loginResult="账号或密码不正确!";
				}
			}else{
				loginResult="账号或密码不能为空!";
			}
		}else{
			loginResult="请输入账号和密码!";
		}
		return toLoginUI();
	}	
}
