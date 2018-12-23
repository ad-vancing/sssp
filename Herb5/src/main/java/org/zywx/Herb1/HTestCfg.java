package org.zywx.Herb1;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HTestCfg {
	
	private SessionFactory sessionFactory;
	//生产环境session、transaction作为成员变量会有并发问题。
	private Session session;
	private Transaction transaction;
	
	
	@Before
	public void init(){
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				                            .buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testComponent(){
		Worker worker = new Worker();
		Pay pay = new Pay(15, 300, 7);
		worker.setName("cyan");
		worker.setPay(pay);
		
		session.save(worker);
		
	}

}
