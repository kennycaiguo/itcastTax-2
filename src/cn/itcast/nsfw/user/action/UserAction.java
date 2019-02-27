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
	//�û���ɫѡ���id��
	private String[] userRoleIds;
	
	private List<User> userList;
	private User user;
	
	//��ת������ҳ��
	public String addUI(){
		//���ؽ�ɫ�б�
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		return "addUI";
	}
	//�б�ҳ��
	public String listUI(){
		userList=userService.findObjects();
		return "listUI";
	}
	//��������
		public String add(){
			try {
				if(user!=null){
					//����ͷ��
					if(headImg!=null){
						//1������ͷ��upload/user
						//��ȡ����·���ľ��Ե�ַ
						String filePath=ServletActionContext.getServletContext().getRealPath("/upload/user");
						//���ɴ���ʽ������ļ�����
						String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
						//�����ļ�
						FileUtils.copyFile(headImg, new File(filePath,fileName));
						
						//2�������û�ͷ��·��
						user.setHeadImg("user/"+fileName);
					}
					
					//saveUserAndRole��������ͬʱ�����û����û��Ľ�ɫ
					userService.saveUserAndRole(user,userRoleIds);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "list";
		}
	//��ת���༭����
		//��ת���༭����
		public String editUI(){
			//���ؽ�ɫ�б�
			ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
			if(user!=null && user.getId()!=null){
				user=userService.findObjectById(user.getId());
				//�����ɫ����
				String[] ids=userService.getRoleIdByUserId(user.getId());
				if(ids!=null&&ids.length>0){
					userRoleIds=ids;
				}
			}
			return "editUI";
		}
	//����༭
		public String edit(){
			try {
				if(user!=null){
					//����ͷ��
					if(headImg!=null){
						
						//1������ͷ��upload/user
						//��ȡ����·���ľ��Ե�ַ
						String filePath=ServletActionContext.getServletContext().getRealPath("/upload/user");
						//���ɴ���ʽ������ļ�����
						String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
						//�����ļ�
						FileUtils.copyFile(headImg, new File(filePath,fileName));
						
						//�����ͷ����ڣ��Ѿ�ͷ��ɾ��
						if(user.getHeadImg()!=null){
							String oldfilename=filePath+"\\"+user.getHeadImg().substring(5);
							File file=new File(oldfilename);
							file.delete();
						}
						
						/**/
						
						//2�������û�ͷ��·��
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
	//ɾ��
	public String delete(){
		if(user!=null && user.getId()!=null){
			userService.delete(user.getId());
		}
		return  "list";
	}
	//����ɾ��
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
	//�����б�
	public void exportExcel(){
		try {
			//1.�����û��б�
			userList=userService.findObjects();
			//2.����
			HttpServletResponse response=ServletActionContext.getResponse();
			//���������Ҫ�������ĵ�����
			response.setContentType("application/x-execl");
			//�������������ĵ�Ҫ��Ϊ��������������(��ֹ����������ݣ��ļ���Ҫ����)
			response.setHeader("Content-Disposition", "attachment;filename="+new String("�û��б�.xls".getBytes(),"ISO-8859-1"));
			//��ȡ�����
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
	//ǰ��Ҫ�����������ԣ��Լ�get��set����(struts���Զ�ע��)
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
	//�����û��б�
	public String importExcel(){
		//1����ȡexcel�ļ�
		if(userExcel !=null){
			//�Ƿ���excel
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//2������
				userService.importExcel(userExcel,userExcelFileName);
			}
		}
		return "list";
	}
	//�����û��˺�Ψһ
	public void verifyAccount(){
		try {
			//1.��ȡ�˺�
			if (user != null && StringUtils.isNotBlank(user.getAccount())) {
				//2.�����˺ŵ����ݿ���У���Ƿ���ڸ��˺Ŷ�Ӧ���û�
				List<User> list = userService.findUserByAccountAndId(user.getId(), user.getAccount());
				String strResult = "true";
				if (list != null && list.size() > 0) {
					//˵�����˺��Ѿ�����
					strResult = "false";
				}
	 
	 
				//���
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
