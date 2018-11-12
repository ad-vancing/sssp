package org.zywx.Herb1;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HTestEntityOperate {
	
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
	 * 1.1临时对象==>持久化对象
	 * 第一次打印，显示id 是0;第二次打印显示id是数据库里的id值
	 * 在flush()时发生insert语句
	 * 
	 * 对象的id属性，如果在save()方法之前设置是无效的，id的值最终会由数据库分配。
	 * 如果在save()方法后设置id，会报错，因为持久化对象的id是不能被修改的
	 */
	@Test
	public void testSave() {
		Student student = new Student("tom", "cs", "13001087677", new Date());
		System.out.println(student);
		session.save(student);
		System.out.println(student);
	}

	
	/**
	 * 1.2临时对象==>持久化对象
	 * 第一次打印，显示id 是0;第二次打印显示id是数据库里的id值
	 * 在flush()时发生insert语句
	 * 
	 * 对象的id属性，如果在persist()方法之前设置，执行会报错
	 */
	@Test
	public void testPersist() {
		Student student = new Student("jerry", "cs", "13001087677", new Date());
		System.out.println(student);
		session.persist(student);
		System.out.println(student);
	}
	
	
	/**
	 * 2.1获取持久化对象
	 * 即从数据库里读取得到一个对像（立即加载对象）
	 * 
	 * 查询之后关闭session，对象仍可使用
	 * 
	 * 如果数据库查询返回空，也将返回空
	 */
	@Test
	public void testGet() {
		Student student = (Student) session.get(Student.class, 3);
//		session.close();
		System.out.println(student);
	}
	
	
	/**
	 * 2.2获取持久化对象
	 * 即从数据库里读取得到一个对像（不使用就不立即执行查询操作，懒加载）
	 * 
	 * 但实际得到的是一个代理对象
	 * org.zywx.Herb1.Student_$$_javassist_0
	 * 使用时才执行select
	 * 
	 * 查询之后关闭session，对象将不能再使用，使用时抛出懒加载异常
	 * LazyInitializationException
	 * 
	 * 如果数据库查询返回空，使用实体类将报错
	 * 
	 */
	@Test
	public void testLoad() {
		Student student = (Student) session.load(Student.class, 30);
		System.out.println(student.getClass().getName());
		
//		session.close();
//		System.out.println(student);
	}
	
	
	/**
	 * 3.1游离对象==>持久化对象
	 * 若更新一个持久化对象，不需要显示地调用update()方法，因为事务提交方法执行时，会flush()
	 * 如果关了当前session，重启一个session，此时之前的持久化对象相对于重启的session是一个游离对象，
	 * 更新一个游离对象到数据库即变成持久化状态，需要显示地调用update()方法
	 * 
	 * 注意1：
	 * 无论游离对象与对应数据库的数据是否一致，都会发送update语句
	 * 持久化对象与对应数据库数据一致时，是不会发送的
	 * 解决办法（如果设置了绑定update的触发器，需要解决，没有设置触发器，就不需要解决，通常不需要）：
	 * 在hbm配置文件里的class元素里，设置属性
	 * select-before-update="true"
	 * 这样，在更新前先查询，就不会盲目触发update了
	 * 缺点：需要多发select语句
	 */
	@Test
	public void testUpdate() {
		Student student = (Student) session.get(Student.class, 3);
		System.out.println(student);
		
		transaction.commit();
		session.close();
		session = sessionFactory.openSession();
		transaction=session.beginTransaction();
		
		student.setMajor("cs");
		session.update(student);
		System.out.println(student);
		
	}
	
	/**
	 * 3.1游离对象==>持久化对象
	 * 注意2：
	 * 修改了id，如果使得对象不能在表里找到对应id的记录，再update()会报错
	 * 
	 * 
	 */
	@Test
	public void testUpdate1() {
		Student student = (Student) session.get(Student.class, 3);
		System.out.println(student);
		
		transaction.commit();
		session.close();
		session = sessionFactory.openSession();
		transaction=session.beginTransaction();
		
		student.setId(1);
		session.update(student);
		
	}
	
	/**
	 * 3.1游离对象==>持久化对象
	 * 注意3：
	 * 如果session缓存里已经有了与游离对象相同id的持久化对象了
	 * 再对游离对象进行update()会报错，因为在同一个session缓存里，不能有两个id相同的对象
	 * 
	 * 
	 */
	@Test
	public void testUpdate2() {
		Student student = (Student) session.get(Student.class, 3);
		System.out.println(student);
		
		transaction.commit();
		session.close();
		session = sessionFactory.openSession();
		transaction=session.beginTransaction();
		
		Student student2 = (Student) session.get(Student.class, 3);
		student.setMajor("cs");
		session.update(student);
		
	}
	
	
	/**
	 * 4.1
	 * 游离对象==>持久化对象（对象的oid不是空时(如果表里无该条记录，报错)，说明是游离对象，用update）
	 * 临时对象==>持久化对象（对象的oid是空时，说明是临时对象，用save）
	 * 都适用.
	 * 
	 * 判断是临时、游离对象的其他方法：(了解)
	 * hbm配置文件的id元素设置属性unsaved-value="10"
	 * 如果对象的id=10，则认为该对象是游离对象（会先执行insert）
	 */
	@Test
	public void testSaveOrUpdate() {
		Student student = new Student("aa","fifa", "13445092488", new Date());
//		session.saveOrUpdate(student);
		
		student.setId(1);
		session.saveOrUpdate(student);
		
	}
	
	
	
	/**
	 * 5
	 * 数据表有对象的id对应的记录时（只需要id对应上），该记录会被删除；
	 * 若没有对应记录，会报错(id为默认值0不会)
	 * 
	 * 是调用flush()方法时发送的delete语句
	 * 
	 * 删除之后不在缓存中了，但对象的id属性仍是原记录的id值
	 * 可以在cfg配置文件中设置:（用的不多）
	 * <property name="use_identifier_rollback">true</property>
	 * 使得删除对象的id为null
	 * 
	 */
	@Test
	public void testDelete() {
		Student student = new Student("ab","fifa", "13445092488", new Date());
		student.setId(8);//游离对象
		session.delete(student);
		System.out.println(student);
		
	}
	
	
	/**
	 * 6
	 * 从session缓存中把指定对应id的持久化对象移除
	 * 
	 * 只有student2在表中name被更新了
	 * (若更新一个持久化对象，不需要显示地调用update()方法，因为实务提交方法执行时，会flush())
	 * 
	 */
	@Test
	public void testEvict() {
		Student student1 = (Student) session.get(Student.class,1);
		Student student2 = (Student) session.get(Student.class,3);
		
		student1.setName("aa");
		student2.setName("bb");
		session.evict(student1);
	}
	
	
	/**
	 * 7
	 * 使用jdbc的一些操作，如存储过程、批量操作
	 */
	@Test
	public void testDoWork() {
		session.doWork(new Work() {
			
			public void execute(Connection connection) throws SQLException {
				System.out.println(connection);//这是jdbc原生connection
				
				//调用jdbc存储过程、或批量操作
			}
		});
	}
	
}
