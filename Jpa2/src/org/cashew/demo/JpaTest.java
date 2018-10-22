package org.cashew.demo;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

public class JpaTest {

	@Test
	public void test() {
		String pN = "Jpa1";
		EntityManagerFactory fac = Persistence.createEntityManagerFactory(pN);
		
		EntityManager em = fac.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		Teacher th = new Teacher("jerry","13001073288", new  java.util.Date(),new  java.util.Date());
		em.persist(th);
		
		transaction.commit();
		
		em.close();
		
		fac.close();
		
		
	}
	
	
	@Test
	public void testIdGeneratorByTable() {
		String pN = "Jpa1";
		EntityManagerFactory fac = Persistence.createEntityManagerFactory(pN);
		
		EntityManager em = fac.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		Teacher2 th = new Teacher2("jerry","13001073288", new  java.util.Date(),new  java.util.Date());
		em.persist(th);
		
		transaction.commit();
		
		em.close();
		
		fac.close();
		
		
	}

}
