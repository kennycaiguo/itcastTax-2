package cn.itcast.nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.nsfw.user.entity.User;

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
		void updateUserAndRole(User user, String[] userRoleIds);
		void saveUserAndRole(User user, String[] userRoleIds);
		String[] getRoleIdByUserId(String id);
		
}
