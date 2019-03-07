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
	
	//列表页面
	public String listUI() throws Exception{
		try {
			//加载分类集合
			ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
			infoList=infoService.findObjects();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		//在进入编辑页面的时候传入当前创建时间
		info=new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//保存新增
	public String add(){
		if(info!=null ){
			infoService.save(info);
		}
		return "list";
	}
	//跳转到编辑界面
	public String editUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info!=null && info.getInfoId()!=null){
			info=infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		infoService.update(info);
		return "list";
	}
	//删除
	public String delete(){
		if(info!=null && info.getInfoId()!=null){
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	//批量删除
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
	//异步发布信息
	public void publicInfo(){
		try {
			if (info != null) {
				//1.更新信息状态
				Info tem = infoService.findObjectById(info.getInfoId());
				tem.setState(info.getState());
				infoService.update(tem);
	 
	 
				//2.输出更新结果
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("更新状态成功".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}