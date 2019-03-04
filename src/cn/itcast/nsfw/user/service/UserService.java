package cn.itcast.nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService {

	//����
		public void save(User user);
		//����
		public void update(User user);
		//����idɾ��
		public void delete(Serializable id);
		//����id����
		public User findObjectById(Serializable id);
		//�����б�
		public List<User> findObjects();
		//�����б�
		public void exportExcel(List<User> userList,ServletOutputStream outputStream);
		//�����б�
		void importExcel(File userExcel, String userExcelFileName);
		//
		List<User> findUserByAccountAndId(String id, String account);
		//�����û������Ӧ�Ľ�ɫ
		public void saveUserAndRole(User user, String... roleIds);
		//�����û������Ӧ�Ľ�ɫ
		public void updateUserAndRole(User user, String... roleIds);
		//�����û�id��ȡ���û���Ӧ�������û���ɫ
		public List<UserRole> getUserRolesByUserId(String id);
		//�����û����ʺź������ѯ�û��б�
		public List<User> findUserByAccountAndPass(String account, String password);
		
}
