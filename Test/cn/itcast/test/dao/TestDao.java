package cn.itcast.test.dao;

import java.io.Serializable;

import cn.itcast.test.entity.Person;

public interface TestDao {
	//保存用户
	public void save(Person person);
	
	//根据Id查询
	public Person findPerson(Serializable id); 
}
