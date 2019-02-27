package cn.itcast.nsfw.role.action;


import java.util.HashSet;
import java.util.List;
 






import javax.annotation.Resource;
 
 






import cn.itcast.core.action.BaseAction;
import cn.itcast.core.content.Constant;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.role.entity.RolePrivilegeId;
import cn.itcast.nsfw.role.service.RoleService;

import com.opensymphony.xwork2.ActionContext;
 
public class RoleAction extends BaseAction{
	@Resource
	private RoleService roleService;
	private List<Role> roleList;
	private Role role;
	private String[] privilegeIds;
	
	//�б�ҳ��
	public String listUI() throws Exception{
		try {
			//����Ȩ�޼���
			ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
			roleList=roleService.findObjects();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "listUI";
	}
	//��ת������ҳ��
	public String addUI(){
		//����Ȩ�޼���
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}
	//��������
	public String add(){
		if(role!=null ){
			//����Ȩ�ޱ���
			if(privilegeIds!=null){
				HashSet<RolePrivilege> set=new HashSet<RolePrivilege>();
				for (int i = 0; i < privilegeIds.length; i++) {
					set.add(new RolePrivilege(new RolePrivilegeId(role,privilegeIds[i])));
				}
				role.setRolePrivileges(set);
			}
			roleService.save(role);
		}
		return "list";
	}
	//��ת���༭����
	public String editUI(){
		//����Ȩ�޼���
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		if(role!=null && role.getRoleId()!=null){
			role=roleService.findObjectById(role.getRoleId());
			//����Ȩ�޻���
			if(role.getRolePrivileges()!=null){
				privilegeIds=new String[role.getRolePrivileges().size()];
				int i=0;
				for(RolePrivilege r:role.getRolePrivileges()){
					privilegeIds[i++]=r.getId().getCode();
				}
			}
		}
		return "editUI";
	}
	//����༭
	public String edit(){
		
		//����Ȩ�ޱ���
		if(privilegeIds!=null){
			HashSet<RolePrivilege> set=new HashSet<RolePrivilege>();
			for (int i = 0; i < privilegeIds.length; i++) {
				set.add(new RolePrivilege(new RolePrivilegeId(role,privilegeIds[i])));
			}
			role.setRolePrivileges(set);
		}	
		roleService.update(role);
	
		return "list";
	}
	//ɾ��
	public String delete(){
		if(role!=null && role.getRoleId()!=null){
			roleService.delete(role.getRoleId());
		}
		return "list";
	}
	//����ɾ��
	public String deleteSelected(){
		if(selectedRow!=null){
			for(String id:selectedRow){
				roleService.delete(id);
			}
		}
		return "list";
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	 
}
