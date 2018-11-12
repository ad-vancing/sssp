package com.cashew.SpringData1;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


/**
 *Query注解使用
 *自定义JPQL语句，以实现更灵活的sql
 */

public interface ClassRepository extends Repository<Class_, Integer>{
	/**
	 * 子查询不是梦
	 * 需要查出class_id最大的class信息
	 * 注意不是用表名、字段名，而是实体类名和属性名
	 */
	@Query("SELECT c1 FROM Class_ c1 WHERE c1.id = (SELECT max(c2.id) from Class_ c2)")
	Class_  getMaxClassId();
	
	
	
	/**
	 * 往注解sql里传参数
	 * 1.使用占位符
	 * 如果= 换成LIKE，传的参数可以选择手动加%，也可以选择在sql里加上，如 LIKE %?1%
	 * ?1 换成命名参数形式也可以
	 */
	@Query("SELECT c1 FROM Class_ c1 WHERE c1.className= ?1 AND c1.classId= ?2")
	Class_  getQueryWithParams1(String name, int id);
	
	/**
	 * 往注解sql里传参数
	 * 2.使用命名参数，不用再考虑顺序
	 */
	@Query("SELECT c1 FROM Class_ c1 WHERE c1.className= :classN AND c1.classId= :id")
	Class_  getQueryWithParams2(@Param("id") int id, @Param("classN")  String name);
	
	
	
	/**
	 * 执行本地（原生）sql查询
	 * nativeQuery=true
	 */
	@Query(value="SELECT count(class_id) FROM class", nativeQuery=true)
	long  getCount();
	
	
	
	
	
	
	}