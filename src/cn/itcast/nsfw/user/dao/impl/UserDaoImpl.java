package cn.itcast.nsfw.user.dao.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public class UserDaoImpl extends BaseDaoImpl<User> 
implements UserDao{
	@Override
	public List<User> findUserByAccountAndId(String id, String account) {
		String hql="FROM User WHERE account = ?";
		if(StringUtils.isNoneBlank(id)){
			hql+=" AND id != ?";
		}
		Query query=getSession().createQuery(hql);
		query.setParameter(0, account);
		if(StringUtils.isNoneBlank(id)){
			query.setParameter(1, id);
		}
		return query.list();
	}
	@Override
	public void saveUserRole(UserRole userRole) {
		getHibernateTemplate().save(userRole);
	}
	 
	 
	@Override
	public void deleteUserRoleByUserId(String id) {
		 Query query=getSession().createQuery("DELETE FROM UserRole WHERE id.userId=?");
		 query.setParameter(0, id);
		 query.executeUpdate();
	}
	@Override
	public String[] getRoleIdByUserId(String id) {
		Query query=getSession().createQuery("FROM UserRole WHERE id.userId=?");
		query.setParameter(0, id);
		List<UserRole> list=query.list();
		String[] ids=null;
		if(list!=null&&list.size()>0){
			ids=new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i]=list.get(i).getId().getRole().getRoleId();
			}
		}
		return ids;
	}
}
