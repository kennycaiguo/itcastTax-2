package cn.itcast.nsfw.info.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

import com.opensymphony.xwork2.ActionContext;
 
public class InfoAction extends BaseAction {
	@Resource
	private InfoService infoService;
	private List<Info> infoList;
	private Info info;
	
	//�б�ҳ��
	public String listUI() throws Exception{
		try {
			//���ط��༯��
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			infoList=infoService.findObjects();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "listUI";
	}
	//��ת������ҳ��
	public String addUI(){
		//���ط��༯��
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		//�ڽ���༭ҳ���ʱ���뵱ǰ����ʱ��
		info=new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//��������
	public String add(){
		if(info!=null ){
			infoService.save(info);
		}
		return "list";
	}
	//��ת���༭����
	public String editUI(){
		//���ط��༯��
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info!=null && info.getInfoId()!=null){
			info=infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}
	//����༭
	public String edit(){
		infoService.update(info);
		return "list";
	}
	//ɾ��
	public String delete(){
		if(info!=null && info.getInfoId()!=null){
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	//����ɾ��
	public String deleteSelected(){
		if(selectedRow!=null){
			for(String id:selectedRow){
				infoService.delete(id);
			}
		}
		return "list";
	}
	
	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> InfoList) {
		this.infoList = InfoList;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	//�첽������Ϣ
	public void publicInfo(){
		try {
			if (info != null) {
				//1.������Ϣ״̬
				Info tem = infoService.findObjectById(info.getInfoId());
				tem.setState(info.getState());
				infoService.update(tem);
	 
	 
				//2.������½��
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("����״̬�ɹ�".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}