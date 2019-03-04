package cn.itcast.nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService {

	//新增
		public void save(User user);
		//更新
		public void update(User user);
		//根据id删除
		public void delete(Serializable id);
		//根据id查找
		public User findObjectById(Serializable id);
		//查找列表
		public List<User> findObjects();
		//导出列表
		public void exportExcel(List<User> userList,ServletOutputStream outputStream);
		//导入列表
		void importExcel(File userExcel, String userExcelFileName);
		//
		List<User> findUserByAccountAndId(String id, String account);
		//保存用户及其对应的角色
		public void saveUserAndRole(User user, String... roleIds);
		//保存用户及其对应的角色
		public void updateUserAndRole(User user, String... roleIds);
		//根据用户id获取该用户对应的所有用户角色
		public List<UserRole> getUserRolesByUserId(String id);
		//根据用户的帐号和密码查询用户列表
		public List<User> findUserByAccountAndPass(String account, String password);
		
}
