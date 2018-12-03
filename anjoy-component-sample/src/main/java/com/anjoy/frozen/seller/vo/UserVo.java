package com.anjoy.frozen.seller.vo;

import java.io.Serializable;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long sellerId;

	/**
	 * 账号,目前用手机号码作为账号
	 */
	private String sellerAccount;

	/**
	 * 店铺名称
	 */
	private String sellerName;

	/**
	 * 卖家真实姓名
	 */
	private String realName;
	/**
	 * 店铺别名,可以理解为简称
	 */
	private String sellerAlias;

	/**
	 * 店铺logo
	 */
	private String sellerLogo;
	/**
	 * 营业执照
	 */
	private String sellerLicence;
	/**
	 * 店铺评级,默认为0
	 */
	private int sellerRank;
	/**
	 * 评分
	 */
	private Double grade;

	/**
	 * 店铺地址
	 */
	private String sellerAddress;

	/**
	 * 关键字
	 */
	private String sellerKeyword;

	/**
	 * 余额
	 */
	private int balanceMoney;

	/**
	 * 账单金额,用于提现的计算
	 */
	private int billMoney;

	/**
	 * 所属区域,多个区域用逗号进行相隔
	 */
	private String regions;

	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 类型 1自营 2加盟 3合作
	 */
	private Integer sellerType;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerAccount() {
		return sellerAccount;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSellerAlias() {
		return sellerAlias;
	}

	public void setSellerAlias(String sellerAlias) {
		this.sellerAlias = sellerAlias;
	}

	public String getSellerLogo() {
		return sellerLogo;
	}

	public void setSellerLogo(String sellerLogo) {
		this.sellerLogo = sellerLogo;
	}

	public String getSellerLicence() {
		return sellerLicence;
	}

	public void setSellerLicence(String sellerLicence) {
		this.sellerLicence = sellerLicence;
	}

	public int getSellerRank() {
		return sellerRank;
	}

	public void setSellerRank(int sellerRank) {
		this.sellerRank = sellerRank;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getSellerKeyword() {
		return sellerKeyword;
	}

	public void setSellerKeyword(String sellerKeyword) {
		this.sellerKeyword = sellerKeyword;
	}

	public int getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(int balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public int getBillMoney() {
		return billMoney;
	}

	public void setBillMoney(int billMoney) {
		this.billMoney = billMoney;
	}

	public String getRegions() {
		return regions;
	}

	public void setRegions(String regions) {
		this.regions = regions;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSellerType() {
		return sellerType;
	}

	public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}
}
