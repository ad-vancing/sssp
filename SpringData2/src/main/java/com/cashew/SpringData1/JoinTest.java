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

public class JoinTest {
	private ApplicationContext ctx = null;
	private ClassRepository cr = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		cr = ctx.getBean(ClassRepository.class);
	}

	
	/**
	 * 生成表
	 */
	@Test
	public void testJpa(){
		
	}
	
	/**
	 * 测试级联查询，
	 * sql打印：
	 * Hibernate: 
    select
        class_0_.class_id as class_id1_0_,
        class_0_.class_date as class_da2_0_,
        class_0_.class_name as class_na3_0_,
        class_0_.teacher_id as teacher_4_0_ 
    from
        class class_0_ 
    left outer join
        teacher teacher1_ 
            on class_0_.teacher_id=teacher1_.id 
    where
        teacher1_.id=?
	 */
	@Test
	public void testSpringData(){
		List<Class_> list = cr.getByTeacherId(1);
		System.out.println(list.size());
		
		
	}
	
	
	/**
	 * 测试级联查询，而class里有teacheId属性，则优先使用该属性对应的列
	 * Hibernate: 
    select
        class_2x0_.class_id as class_id1_1_,
        class_2x0_.class_date as class_da2_1_,
        class_2x0_.class_name as class_na3_1_,
        class_2x0_.teacher_id as teacher_5_1_,
        class_2x0_.tt_id as tt_id4_1_ 
    from
        class2 class_2x0_ 
    where
        class_2x0_.tt_id=?
	 */
	@Test
	public void test2SpringData(){
		 Class2Repository cr2 = ctx.getBean(Class2Repository.class);
		List<Class_2> list = cr2.getByTeacherId(1);
		System.out.println(list.size());
		
		
	}
	
	
	/**
	 * 表示"Teacher_Id"并不是指属性，而是列,从而使用级联属性
	 * 打印sql:
	 * Hibernate: 
    select
        class_2x0_.class_id as class_id1_1_,
        class_2x0_.class_date as class_da2_1_,
        class_2x0_.class_name as class_na3_1_,
        class_2x0_.teacher_id as teacher_5_1_,
        class_2x0_.tt_id as tt_id4_1_ 
    from
        class2 class_2x0_ 
    left outer join
        teacher teacher1_ 
            on class_2x0_.teacher_id=teacher1_.id 
    where
        teacher1_.id=?
	 */
	@Test
	public void test3SpringData(){
		 Class2Repository cr2 = ctx.getBean(Class2Repository.class);
		List<Class_2> list = cr2.getByTeacher_Id(1);
		System.out.println(list.size());
		
		
	}
	

}
