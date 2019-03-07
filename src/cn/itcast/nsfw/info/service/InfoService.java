package cn.itcast.nsfw.info.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.nsfw.info.entity.Info;
 
 
 
public interface InfoService {
	
	//新增
	public void save(Info entity);
	//更新
	public void update(Info enetity);
	//根据id删除
	public void delete(Serializable id);
	//根据id查找
	public Info findObjectById(Serializable id);
	//查找列表
	public List<Info> findObjects();
}
