package com.cashew.SpringData1;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="class")
public class Class_ {

	private String className;
	private int classId;
	private String teacher;
	private Date classDate;
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
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public Date getClassDate() {
		return classDate;
	}
	public void setClassDate(Date classDate) {
		this.classDate = classDate;
	}
	public Class_(String className, String teacher, Date classDate) {
		super();
		this.className = className;
		this.teacher = teacher;
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
