package cn.itcast.nsfw.user.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	//用户角色选择的id们
	private String[] userRoleIds;
	
	private List<User> userList;
	private User user;
	
	//跳转到新增页面
	public String addUI(){
		//加载角色列表
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		return "addUI";
	}
	//列表页面
	public String listUI(){
		userList=userService.findObjects();
		return "listUI";
	}
	//保存新增
		public String add(){
			try {
				if(user!=null){
					//处理头像
					if(headImg!=null){
						//1、保存头像到upload/user
						//获取保存路径的绝对地址
						String filePath=ServletActionContext.getServletContext().getRealPath("/upload/user");
						//生成带格式的随机文件名称
						String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
						//复制文件
						FileUtils.copyFile(headImg, new File(filePath,fileName));
						
						//2、设置用户头像路径
						user.setHeadImg("user/"+fileName);
					}
					
					//saveUserAndRole方法用来同时保存用户和用户的角色
					userService.saveUserAndRole(user,userRoleIds);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "list";
		}
	//跳转到编辑界面
		//跳转到编辑界面
		public String editUI(){
			//加载角色列表
			ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
			if(user!=null && user.getId()!=null){
				user=userService.findObjectById(user.getId());
				//处理角色回显
				String[] ids=userService.getRoleIdByUserId(user.getId());
				if(ids!=null&&ids.length>0){
					userRoleIds=ids;
				}
			}
			return "editUI";
		}
	//保存编辑
		public String edit(){
			try {
				if(user!=null){
					//处理头像
					if(headImg!=null){
						
						//1、保存头像到upload/user
						//获取保存路径的绝对地址
						String filePath=ServletActionContext.getServletContext().getRealPath("/upload/user");
						//生成带格式的随机文件名称
						String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
						//复制文件
						FileUtils.copyFile(headImg, new File(filePath,fileName));
						
						//如果旧头像存在，把旧头像删除
						if(user.getHeadImg()!=null){
							String oldfilename=filePath+"\\"+user.getHeadImg().substring(5);
							File file=new File(oldfilename);
							file.delete();
						}
						
						/**/
						
						//2、设置用户头像路径
						user.setHeadImg("user/"+fileName);
					}
					
					userService.updateUserAndRole(user,userRoleIds);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "list";
		}
	//删除
	public String delete(){
		if(user!=null && user.getId()!=null){
			userService.delete(user.getId());
		}
		return  "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow!=null){
			for(String id:selectedRow){
				userService.delete(id);
			}
		}
		return  "list";
	}
	private String[] selectedRow;
 
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private File headImg;
	private String headImgContantType;
	private String headImgFileName;

	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgContantType() {
		return headImgContantType;
	}
	public void setHeadImgContantType(String headImgContantType) {
		this.headImgContantType = headImgContantType;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	//导出列表
	public void exportExcel(){
		try {
			//1.查找用户列表
			userList=userService.findObjects();
			//2.导出
			HttpServletResponse response=ServletActionContext.getResponse();
			//告诉浏览器要弹出的文档类型
			response.setContentType("application/x-execl");
			//告诉浏览器这个文档要作为附件给别人下载(防止浏览器不兼容，文件名要编码)
			response.setHeader("Content-Disposition", "attachment;filename="+new String("用户列表.xls".getBytes(),"ISO-8859-1"));
			//获取输出流
			ServletOutputStream outputStream= response.getOutputStream();
			userService.exportExcel(userList,outputStream);
			if(outputStream !=null){
				outputStream.close();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//前面要价这三个属性，以及get和set方法(struts的自动注入)
	private File userExcel;
	private String userExcelContantType;
	private String userExcelFileName;
	 
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public String getUserExcelContantType() {
		return userExcelContantType;
	}
	public void setUserExcelContantType(String userExcelContantType) {
		this.userExcelContantType = userExcelContantType;
	}
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	//导入用户列表
	public String importExcel(){
		//1、获取excel文件
		if(userExcel !=null){
			//是否是excel
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//2、导入
				userService.importExcel(userExcel,userExcelFileName);
			}
		}
		return "list";
	}
	//检验用户账号唯一
	public void verifyAccount(){
		try {
			//1.获取账号
			if (user != null && StringUtils.isNotBlank(user.getAccount())) {
				//2.根据账号到数据库中校验是否存在该账号对应的用户
				List<User> list = userService.findUserByAccountAndId(user.getId(), user.getAccount());
				String strResult = "true";
				if (list != null && list.size() > 0) {
					//说明该账号已经存在
					strResult = "false";
				}
	 
	 
				//输出
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(strResult.getBytes());
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String[] getUserRoleIds() {
		return userRoleIds;
	}
	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}
}
