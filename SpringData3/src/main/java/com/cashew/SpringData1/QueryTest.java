package com.cashew.SpringData1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QueryTest {
	private ApplicationContext ctx = null;
	private ClassRepository cr = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		cr = ctx.getBean(ClassRepository.class);
	}

	/**
	 * 测试子查询
	 */
	@Test
	public void testQuery(){
		Class_ classM = cr.getMaxClassId();
		System.out.println(classM);
	}
	
	
	/**
	 * 测试占位符传参
	 */
	@Test
	public void testQueryWithParams1(){
		Class_ clas = cr.getQueryWithParams1("math",41);
		System.out.println(clas);
	}
	
	
	/**
	 * 测试命名参数传参
	 */
	@Test
	public void testQueryWithParams2(){
		Class_ clas = cr.getQueryWithParams2(41,"math");
		System.out.println(clas);
	}
	
	
	/**
	 * 测试原生sql查询
	 */
	@Test
	public void testQueryNative(){
		long count = cr.getCount();
		System.out.println(count);
	}
	
	

}
