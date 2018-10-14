package com.cashew.SpringData1;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDataTest {
	private ApplicationContext ctx = null;
	private ClassRepository cr = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		cr = ctx.getBean(ClassRepository.class);
	}

	/**
	 * 测试数据库配置
	 * @throws SQLException
	 */
	@Test
	public void test1() throws SQLException {
		DataSource ds = ctx.getBean(DataSource.class);
		System.out.println(ds.getConnection());
	
	}
	
	/**
	 * 测试jpa的EntityManagerFactory 配置
	 */
	@Test
	public void testJpa(){
		
	}
	
	/**
	 * 测试SpringData
	 */
	@Test
	public void testSpringData(){
	
		System.out.println(cr.getClass().getName());
		
		Class_ clas = cr.getByClassName("PC");
		System.out.println(clas);
		
		Class_ clas2 = cr.getByClassNameAndTeacher("CS", "jerry");
		System.out.println(clas2);
		
	}
	
	/**
	 * 测试SpringData复杂查询
	 * @throws ParseException 
	 */
	@Test
	public void testSpringData2() throws ParseException{
		System.out.println(cr.getClass().getName());
		
		List<Class_> clasList = cr.getByClassNameStartingWithAndClassIdLessThan("C", 3);
		
		for(Class_ cl : clasList){
			System.out.println(cl);
		}
		
//		List<String> classNames = new ArrayList();
//		classNames.add("PC");
//		classNames.add("CS");
		List<String> classNames =Arrays.asList("PC","CS");
		List<Class_> clasList2 = 
				cr.getByClassNameInOrClassDateGreaterThan(classNames,
						new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-2"));
		
		for(Class_ cl : clasList2){
			System.out.println(cl);
		}
		
	}

}
