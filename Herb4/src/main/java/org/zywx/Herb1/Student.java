package org.zywx.Herb1;

import java.sql.Blob;
import java.util.Date;

public class Student {

	private int id; //最好都用包装类，不然查询时是null不能赋给对象的int属性
	private String name;
	private String major;
	private String tel;
	private Date birthday;
	
	//该属性为 name: tel（表里没有）
	private String descript;
	
	//大文本
	private String selfDesc;
	
	//Blob二进制类型
	private Blob image;
	
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public String getSelfDesc() {
		return selfDesc;
	}
	public void setSelfDesc(String selfDesc) {
		this.selfDesc = selfDesc;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", major=" + major + ", tel=" + tel + ", birthday=" + birthday
				+ "]";
	}
	public Student( String name, String major, String tel, Date birthday) {
		super();
		this.name = name;
		this.major = major;
		this.tel = tel;
		this.birthday = birthday;
	}
	
	public Student() {
		// TODO Auto-generated constructor stub
	}
}
