package com.cashew.SpringData1;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="class")
public class Class_ {

	private String className;
	private int classId;
	private Date classDate;
	private Teacher teacher;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Id
	@GeneratedValue
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public Date getClassDate() {
		return classDate;
	}
	public void setClassDate(Date classDate) {
		this.classDate = classDate;
	}
	
	@JoinColumn(name="teacher_id")//即该列列名
	@ManyToOne//多个class对应一个teacher
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Class_(String className,  Date classDate) {
		super();
		this.className = className;
		this.classDate = classDate;
	}
	public Class_() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Class_ [className=" + className + ", classId=" + classId + ", teacher=" + teacher + ", classDate="
				+ classDate + "]";
	}
	
}
