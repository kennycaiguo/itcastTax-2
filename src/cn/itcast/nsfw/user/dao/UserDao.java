package cn.itcast.nsfw.user.dao;

import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {
	/**
	 * �����ʺź��û�id��ѯ�û�
	 * @param id �û�ID
	 * @param account �û��ʺ�
	 * @return �û��б�
	 */
	public List<User> findUserByAccountAndId(String id, String account);

	void deleteUserRoleByUserId(String id);

	void saveUserRole(UserRole userRole);

	String[] getRoleIdByUserId(String id);

	List<User> findUserByAccountAndPassword(String account, String password);
	
}
