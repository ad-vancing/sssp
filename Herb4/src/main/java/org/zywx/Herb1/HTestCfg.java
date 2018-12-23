package org.zywx.Herb1;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
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
	
	
	
	/**
	 * 测试c3p0是否设置成功
	 * 打印的类名是c3p0就代表设置成功
	 * com.mchange.v2.c3p0.impl.NewProxyConnection@52b6c3c1
	 */
	@Test
	public void testDoWork() {
		session.doWork(new Work() {
			
			public void execute(Connection connection) throws SQLException {
				System.out.println(connection);
				
			}
		});
	}
	
	
	/**
	 * hbm文件中class元素，属性dynamic-update动态更新
	 * 默认false的update语句：
	 *     update
        STUDENT 
    set
        NAME=?,
        MAJOR=?,
        TEL=?,
        BIRTHDAY=? 
    where
        ID=?
        设置该属性为true后的update语句：
        Hibernate: 
    update
        STUDENT 
    set
        NAME=? 
    where
        ID=?
	 */
	@Test
	public void testDynamicUpdate(){
		Student student = (Student) session.get(Student.class, 1);
		student.setName("cashew2");
	}
	
	
	/**
	 * 测试property的formula属性
	 * 有了个子查询
	 */
	@Test
	public void testFormula(){
		Student student = (Student) session.get(Student.class, 1);
		System.out.println(student.getDescript());
	}
	
	
	
	/**
	 * 测试property的时间相关属性设置
	 * 可能需要先删表，否则修改不起作用
	 * 若嫌弃每次都要删，可以修改配置文件：
	 * <property name="hbm2ddl.auto">create</property>
	 * 
	 * type="date" 字段是日期类型 yyyy-MM-dd
	 * type="time" 字段是时间类型 hh:mi:ss
	 * type="timestamp" 字段是日期时间类型 yyyymmddhhmiss
	 * java.util.Date都可以接受
	 * 因为了 java.util.Date 类的子类三个: java.sql.Date, java.sql.Time 和 java.sql.Timestamp, 这类分别和标准 SQL 类型中的 DATE, TIME 和 TIMESTAMP 类型对应
	 * 而它们都是java.util.Date 类的子类
	 */
	@Test
	public void testTimestamp(){
		Student student = new Student("aa2","fifa", "13445092488", new Date());
		session.save(student);
	}
	
	
	/**
	 * 测试大对象保存，如，存一张图片
	 * @throws IOException 
	 */
	@Test
	public void testBlob() throws IOException{
		Student student = new Student("aa2","fifa", "13445092488", new Date());
		
		InputStream iStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\4.jpg");
		Blob image = Hibernate.getLobCreator(session)
						      .createBlob(iStream, iStream.available());
		
		student.setImage(image);
		session.save(student);
	}
	
	/**
	 * 测试大对象获取，如，取一张图片
	 * 打印图片大小b
	 * 少用
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@Test
	public void testBlob2() throws IOException, SQLException{
		Student student = (Student) session.get(Student.class, 1);
		
		Blob image = student.getImage();
		InputStream iStream = image.getBinaryStream();
		System.out.println(iStream.available());
		
	}
}
