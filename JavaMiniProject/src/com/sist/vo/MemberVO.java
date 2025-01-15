package com.sist.vo;
/*
 * ID       NOT NULL VARCHAR2(50)  
PWD      NOT NULL VARCHAR2(50)  
NAME     NOT NULL VARCHAR2(51)  
NICKNAME NOT NULL VARCHAR2(51)  
BIRTHDAY          DATE          
SEX               VARCHAR2(10)  
POST              VARCHAR2(7)   
ADDR1             VARCHAR2(100) 
ADDR2             VARCHAR2(100) 
PHONE             VARCHAR2(14)  
EMAIL    NOT NULL VARCHAR2(100) 
JOIN              CLOB 
 */

import java.util.Date;

public class MemberVO {
	private String id, pwd, name, nickname, sex, post, addr1, addr2, phone, email, join;
	private Date birthday;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoin() {
		return join;
	}
	public void setJoin(String join) {
		this.join = join;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
}
