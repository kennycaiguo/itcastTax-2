package cn.itcast.core.permission;

import cn.itcast.nsfw.user.entity.User;

public interface PermissionCheck {

	/**
	 *�ж��û��Ƿ���code��Ӧ��Ȩ��
	 * @param user �û�
	 * @param code ��ϵͳ��Ȩ�ޱ�ʶ
	 * @return true or false
	 */
	public boolean isAccessible(User user,String code);
}
