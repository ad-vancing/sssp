package org.cashew.demo;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

public class JpaTest {

	/**
	 * 测试Persistence的另一个构建EntityManagerFactory的方法
	 * 
	 */
	@Test
	public void test() {
		String pN = "Jpa1";
		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		//设置该属性后，控制台就不会再打印sql了，不常用
		propertiesMap.put("hibernate.show_sql", false);
		
//		EntityManagerFactory fac = Persistence.createEntityManagerFactory(pN);
		EntityManagerFactory fac = Persistence.createEntityManagerFactory(pN,propertiesMap);
		
		EntityManager em = fac.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		Teacher th = new Teacher("jerry","13001073288", new  java.util.Date(),new  java.util.Date());
		em.persist(th);
		
		transaction.commit();
		
		em.close();
		
		fac.close();
		
		
	}
	
	

}
