package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	Class<T> clazz;
	
	//拿到泛型实体类T
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		//BaseDaoImpl<User>获取此类泛型框中的第一个泛型
		ParameterizedType pt=(ParameterizedType)this.getClass().getGenericSuperclass();
		clazz=(Class<T>)pt.getActualTypeArguments()[0];
	}
	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}
	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(findObjectById(id));
	}

	@Override
	public T findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects() {
		Query query=getSession()
				.createQuery("FROM "+clazz.getSimpleName());
			return query.list();
	}


}
