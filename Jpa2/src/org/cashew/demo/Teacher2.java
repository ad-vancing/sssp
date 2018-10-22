package org.cashew.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 测试Table主键生成策略 
 * 需要先创建一张存储主键信息的表JPA_ID_GENERATOR,有：id，PK_NAME，PK_VALUE(第一个记录是增了PK_VALUE个增量)三个字段
 */
@Table(name="Teachers2")//对应数据库的表名
@Entity//表明这是一个实体类
public class Teacher2 {
     private int id;
     private String name;
     private String tel;
     private Date birthday;
     private Date createDate;
     
    @TableGenerator(name="id-generator",
    		table="JPA_ID_GENERATOR",
    		pkColumnName="PK_NAME",
    		pkColumnValue="TEACHER2_ID",
    		valueColumnName="PK_VALUE",
    		allocationSize=100)//表示每次增量 
    @GeneratedValue(strategy=GenerationType.TABLE,generator="id-generator")
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
	public Teacher2(String name, String tel, Date birthday , Date createDate) {
		super();
		this.name = name;
		this.tel = tel;
		this.birthday = birthday;
		this.createDate = createDate;
	}
	
    public Teacher2() {
		// TODO Auto-generated constructor stub
	} 
    
//    不需要映射为数据表的一列
    @Transient
    public String getInfo(){
    	return id + name + tel;
    }
    
    
    
}
