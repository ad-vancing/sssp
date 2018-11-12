package org.cashew.demo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JpaEMApiTest {
	
	private EntityManagerFactory fac;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@Before
	public void init() {
		String pN = "Jpa1";
		fac = Persistence.createEntityManagerFactory(pN);
		em = fac.createEntityManager();
		transaction = em.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destroy() {
		transaction.commit();
		em.close();
		fac.close();
	}

	/**
	 * 相当于hibernate里session的get()
	 */
	@Test
	public void testFind() {
		Teacher t1 = em.find(Teacher.class, 4);
		System.out.println(t1);
		
	}
	
	
	/**
	 * 相当于hibernate里session的load()
	 * 用到查询所得对象时，才会发送查询语句
	 * 先打印"============="
	 * 再打印sql
	 * 最后打印对象信息
	 */
	@Test
	public void testGetReference() {
		Teacher t1 = em.getReference(Teacher.class, 4);
		System.out.println("================");
		System.out.println(t1);
		
	}
	
	
	/**
	 * 类似于hibernate里session的save()
	 * 不同之处：
	 * 在持久化对象之前设置对象的id是会报错的，对于save()方法是无效不会报错
	 * 
	 */
	@Test
	public void testPersist() {
		Teacher th = new Teacher("cyan","13001073288", new  java.util.Date(),new  java.util.Date());
		em.persist(th);
		System.out.println(th.getId());
	}
	
	
	/**
	 * 类似于hibernate里session的delete()
	 * 不同之处：
	 * session的delete()还能删除游离对象，而该方法只能删除持久化对象
	 * 
	 */
	@Test
	public void testRemove() {
//		Teacher th = new Teacher("cyan11","13001073288", new  java.util.Date(),new  java.util.Date());
//		th.setId(1);
//		em.remove(th);删除游离对象报错
		
		Teacher t1 = em.find(Teacher.class, 4);
		em.remove(t1);//删除持久化对象
	}
	
	
	/**
	 * 类似于hibernate里session的saveOrUpdate()
	 * 1.若传人一个临时对象
	 * 会先创建一个新 对象，再把临时对象的属性复制到新对象中，最后对新对象insert持久化
	 */
	@Test
	public void testMerge1() {
		Teacher t1 = new Teacher("cyan11","13001073288", new  java.util.Date(),new  java.util.Date());
		Teacher t2 = em.merge(t1);
		System.out.println(t1.getId());//0
		System.out.println(t2.getId());//数据库的id值
	}

	
	/**
	 * 类似于hibernate里session的saveOrUpdate()
	 * 2.若传人一个游离对象（有id）
	 * 先查询oid对应记录
	 * 
	 * 若数据库里有对应OID的记录
	 * 会返回该记录对应的对象，再把游离对象的属性复制到该对象中，最后对该对象执行update
	 * 数据库里没有对应OID的记录
	 * 会先创建一个新对象，再把游离对象的属性复制到新对象中，最后对新对象insert持久化
	 */
	@Test
	public void testMerge2() {
		Teacher t1 = new Teacher("cyanss","13001073288", new  java.util.Date(),new  java.util.Date());
		t1.setId(7);
		Teacher t2 = em.merge(t1);
		System.out.println(t1.getId());//7
		System.out.println(t2.getId());//数据库的id值7
	}
	
	
	/**
	 * 类似于hibernate里session的saveOrUpdate()
	 * 3.若传人一个已存在于entityManager缓存的对象 
	 * 会把该对象的属性复杂到缓存的对象中，并对缓存对象update
	 */
	@Test
	public void testMerge3() {
		Teacher t1 = em.find(Teacher.class, 5);
		Teacher t2 = new Teacher("cyanhh","13001073288", new  java.util.Date(),new  java.util.Date());
		t2.setId(5);
		em.merge(t2);
		System.out.println(t1);
		System.out.println(t2);
	}
}
