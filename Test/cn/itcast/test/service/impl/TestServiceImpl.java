package cn.itcast.test.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	@Autowired
	TestDao testDao;
	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("Hellow Service");
	}

	@Override
	public void save(Person person) {
		// TODO Auto-generated method stub
		testDao.save(person);
	}

	@Override
	public Person findPerson(Serializable id) {
		save(new Person("jack"));
		return testDao.findPerson(id);
	}

}
