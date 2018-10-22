package org.cashew.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Table(name="Teachers")//对应数据库的表名
@Entity//表明这是一个实体类
public class Teacher {
     private int id;
     private String name;
     private String tel;
     private Date birthday;
     private Date createDate;
     
    @GeneratedValue(strategy=GenerationType.AUTO)//主键生成方式，AUTO是默认的
    @Id //标识列
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="Last_Name",length=10,nullable=false)//对应的列名
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	//生日只需到年月日，对应数据库的DATE类型
	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	//创建时间到年月日时分秒，对应数据库的TIMESTAMP类型，此外还有TIME
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Teacher(String name, String tel, Date birthday , Date createDate) {
		super();
		this.name = name;
		this.tel = tel;
		this.birthday = birthday;
		this.createDate = createDate;
	}
	
    public Teacher() {
		// TODO Auto-generated constructor stub
	} 
    
//    不需要映射为数据表的一列
    @Transient
    public String getInfo(){
    	return id + name + tel;
    }
    
    
    
}
