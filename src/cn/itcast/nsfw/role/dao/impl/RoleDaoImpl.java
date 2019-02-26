package cn.itcast.nsfw.role.dao.impl;


import org.hibernate.Query;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
 
 
 
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{
 
 
	@Override
	public void deletePrivilegeByRoleId(String roleId) {
		//hql语句是按照类来的，不是按照数据库表来写语句的
		 Query query=getSession().createQuery("DELETE FROM RolePrivilege WHERE id.role.roleId=?");
		 query.setParameter(0, roleId);
		 query.executeUpdate();
	}
}
