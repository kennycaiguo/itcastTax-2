package cn.itcast.test.dao;

import java.io.Serializable;

import cn.itcast.test.entity.Person;

public interface TestDao {
	//�����û�
	public void save(Person person);
	
	//����Id��ѯ
	public Person findPerson(Serializable id); 
}
