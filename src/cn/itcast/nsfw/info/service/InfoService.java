package cn.itcast.nsfw.info.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.nsfw.info.entity.Info;
 
 
 
public interface InfoService {
	
	//����
	public void save(Info entity);
	//����
	public void update(Info enetity);
	//����idɾ��
	public void delete(Serializable id);
	//����id����
	public Info findObjectById(Serializable id);
	//�����б�
	public List<Info> findObjects();
}
