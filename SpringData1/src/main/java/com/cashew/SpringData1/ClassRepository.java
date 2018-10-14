package com.cashew.SpringData1;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;


/**
 * 关于Repository接口，它是一个空接口（标记接口）
 * 如果定义一个接口继承该空接口，则定义的接口会被IOC容器识别为一个Repository Bean纳入到IOC容器中，
 *进而可以在定义的接口里定义满足一定规范的方法
 * 
 * 也可以通过注解@RepositoryDefinition(domainClass=Class_.class,idClass=Integer.class)来替代“继承Repository”
 * 
 * Repository的子接口CrudRepository:实现了一组CRUD相关的方法
 * PagingAndSortingRepository继承了CrudRepository，实现了一组分页排序相关的方法
 * JpaRepository继承了PagingAndSortingRepository，实现了一组JPA规范相关的方法
 * 
 * 关于在接口里声明的方法：
 * 如果是查询的方法，需要以find、read、get开头；
 * 条件查询的话，条件的属性用条件关键字By连接，且首字母大写；
 * 有多个条件属性时，条件属性之间用关键字And或Or或Between等连接
 * 复杂条件查询，参考更多的关键字使用
 * 
 * 
 * 
 * 
 * @author issuser
 *
 */

public interface ClassRepository extends Repository<Class_, Integer>{
	//根据className获取对应的class完整信息
	Class_ getByClassName(String classNAme);
	
	//根据className、teacher获取对应的class完整信息
	Class_ getByClassNameAndTeacher(String classNAme, String teacher);
	
	//复杂查询语句 WHERE ClassName LIKE ?% AND classId < ?
	//也有EndingWith、Containing
	List<Class_> getByClassNameStartingWithAndClassIdLessThan(String className, Integer id);
	
	
	//复杂查询语句 WHERE ClassName IN (?, ?, ?) OR classDate > ?
	//也有EndingWith、ContainingWith
	List<Class_> getByClassNameInOrClassDateGreaterThan(List<String> classNames, Date dateMax);
	}