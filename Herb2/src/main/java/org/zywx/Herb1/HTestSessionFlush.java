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


public class HTestSessionFlush  {
	
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
	
	/**
	 * 只发送了一次查询sql语句，而且结果是true
	 * 因为第一次查询得到的对象会被缓存
	 * 第二次查询会先去缓存查看
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWhatIsSessionFlush() throws Exception {
        System.out.println("test...");
		
		Student st1 = (Student) session.get(Student.class, 3);
		Student st2 = (Student) session.get(Student.class, 3);
		System.out.println(st1 == st2);
	}
	
	
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
	 * 1.先查询获取对象发送select查询，再设置对象属性与数据库的值不一致，最后提交事务时又会有一条update
	 * 在 Transaction 的 commit() 方法中会先调用 session 的 flush 方法, 再提交事务
	 * 
	 * 看源码：
	 * AbstractTrasaction的commit()方法有个beforeTransactionCommit()
	 * JdbcTrasaction的beforeTransactionCommit()里调用了managedFlush()
	 * SessionImpl的managedFlush()里调用了flush()
	 * 
	 */
	@Test
	public void testSessionFlush1() throws Exception {
        System.out.println("test...");
		
		Student st1 = (Student) session.get(Student.class, 3);
		System.out.println(st1);
		st1.setName("Aug");
		System.out.println(st1);
	}	
	
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
	 * 2. flush() 方法会可能会发送 update（获insert等） 语句, 但不会提交事务. 不提交事务就对数据库表不起作用
	 */
	@Test
	public void testSessionFlush2()  {
        System.out.println("test...");
		
		Student st1 = (Student) session.get(Student.class, 3);
		st1.setName("Aug5");
		session.flush();
		System.out.println(st1);
	}	
	
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
	 * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前, 也有可能会进行 flush() 操作.
	 * 1). 执行 HQL 或 QBC 查询, 会先进行 flush() 操作更新表, 以保证在该事务内得到数据表的最新的记录
	 * 
	 * 若该session范围内的缓存对象没有被修改，不会去flush操作缓存。
	 * 
	 * 如果注释掉事务提交，数据库表的对应值不会变化，但st查询结果会是stName之后的值
	 */
	@Test
	public void testSessionFlush3()  {
        System.out.println("test...");
		
        Student st1 = (Student) session.get(Student.class, 3);
        System.out.println(st1);
		st1.setName("Aug1");
		System.out.println(st1);
		Student st = (Student) session.createCriteria(Student.class).uniqueResult();//该方法执行需要表里只有一条记录
		System.out.println(st);
	}	
	
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
	 * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前, 也有可能会进行 flush() 操作.
	 * 2). 若记录的 ID 是由底层数据库使用自增的方式生成的, 则在调用 save() 方法时, 就会立即发送 INSERT 语句. 
	 * 而不是在事务提交方法里insert
	 * 因为 save 方法后, 必须保证对象的 ID 是存在的!
	 * 
	 * 先执行insert，以知道id的值。
	 * 
	 * 如果id是Hiernate生成的，则insert发生在事务提交方法里
	 * 把<generator class="native" />里native改hilo 
	 * 事务提交前先select、update确定id值，提交时insert
	 * 
	 */
	@Test
	public void testSessionFlush4() throws Exception {
        System.out.println("test...");
		
        Student st = new Student("tom", "ge", "1300107327",new java.util.Date());
		session.save(st);
	}	
	
	
	
	/**
	 * refresh(): 会强制发送 SELECT 语句, 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致!
	 * 
	 * 通过打断点测试发现st的属性打印出来并无变化，因为mysql默认事务隔离级别是“可重复读”
	 * 如果在cfg配置文件里加上如下，修改mysql事务隔离级别为“读已提交”则会变化
	 * <!-- 设置 Hibernate 的事务隔离级别 -->
     *	<property name="connection.isolation">2</property>
	 * 
	 * 
	 */
	@Test
	public void testRefresh(){
		Student st = (Student) session.get(Student.class, 3);
		System.out.println(st);
		
		session.refresh(st); 
		System.out.println(st); 
	}
	
	
	
	/**
	 * clear(): 清理缓存
	 * 注掉该方法会发两次select查询
	 */
	@Test
	public void testClear(){
		Student st1 = (Student) session.get(Student.class, 3);
		
		session.clear();
		
		Student st2 = (Student) session.get(Student.class, 3);
		
		System.out.println(st1 == st2);
	}
	
}
