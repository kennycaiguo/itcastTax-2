package cn.itcast.nsfw.info.service.impl;


import java.io.Serializable;
import java.util.List;
 
 






import javax.annotation.Resource;
 
 






import org.springframework.stereotype.Service;

import cn.itcast.nsfw.info.dao.InfoDao;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;
 
 
 
@Service("infoService")
public class InfoServiceImpl implements InfoService {
 
 
	@Resource
	private InfoDao infoDao;
	
	@Override
	public void delete(Serializable id) {
		infoDao.delete(id);
	}
 
 
	@Override
	public Info findObjectById(Serializable id) {
		return infoDao.findObjectById(id);
	}
 
 
	@Override
	public List<Info> findObjects() {
		return infoDao.findObjects();
	}
 
 
	@Override
	public void save(Info entity) {
		infoDao.save(entity);
	}
 
 
	@Override
	public void update(Info enetity) {
		infoDao.update(enetity);
	}
 
 
}
