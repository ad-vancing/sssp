package com.cashew.SpringData1;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;


/**
 * 支持级联查询，但需要注意的是，如果class里有teacheId属性，则优先使用该属性对应的列
 *	如果这种情况下还是需要使用级联查询，可以使用下划线getByTeacherId改为getByTeacher_Id
 *	表示"Teacher_Id"并不是指属性，而是列
 */

public interface Class2Repository extends Repository<Class_2, Integer>{
	//需要查出class的teacher_id是5的
	//WHERE teacher_id=5
	List<Class_2>  getByTeacherId(Integer id);
	List<Class_2>  getByTeacher_Id(Integer id);
	}