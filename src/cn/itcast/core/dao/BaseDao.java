package cn.itcast.core.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	//����
	public void save(T entity);
	//����
	public void update(T entity);
	//ɾ��
	public void delete(Serializable id);
	//����id����
	public T findObjectById(Serializable id);
	//�����б�
	public List<T> findObjects();
}
