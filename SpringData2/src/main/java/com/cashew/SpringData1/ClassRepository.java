package com.cashew.SpringData1;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;


/**
 * 支持级联查询，但需要注意的是，如果class里有teacheId属性，
 *
 */

public interface ClassRepository extends Repository<Class_, Integer>{
	//需要查出class的teacher_id是5的
	//WHERE teacher_id=5
	List<Class_>  getByTeacherId(Integer id);
	}