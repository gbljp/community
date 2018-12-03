package com.anjoy.cloud.component.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基本信息
 * @author wangfucai
 *
 */
public class Users implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2869034349247740496L;
	/**
	 * 自动增加ID
	 */
	private Long id;
	/**
	 * 账号，目前采用手机号码为账号
	 */
	private String account;

	/**
	 * 别名,可以理解为简称
	 */
	private String password;
	
	/**
	 * 状态，(-1.表示强制下架,坚决不可用;0.刚注册，未完善资料;1.完成了资料，未审核;2.完成了资料，审核未通过;3.审核通过，可以用;)
	 * 
	 */
	private int status;
	
	/**
	 * 真实姓名
	 */
	private String trueName;
	
	/**
	 * 注册时间
	 */
	private Date createTime;
	
	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 1为买家，2为卖家
	 */
	private int type;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}