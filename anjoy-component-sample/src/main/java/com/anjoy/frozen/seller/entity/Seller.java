package com.anjoy.frozen.seller.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 卖家信息表.(俗称经销商)
 * </p>
 *
 * @author 
 * @since 2018-08-14
 */
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 来源于users的ID
     */
    @TableId("seller_id")
    private Long sellerId;
    /**
     * 账号,目前用手机号码作为账号
     */
    @TableField("seller_account")
    private String sellerAccount;
    /**
     * 店铺名称
     */
    @TableField("seller_name")
    private String sellerName;
    /**
     * 店铺别名,可以理解为简称
     */
    @TableField("seller_alias")
    private String sellerAlias;
    /**
     * 店铺logo
     */
    @TableField("seller_logo")
    private String sellerLogo;
    /**
     * 营业执照
     */
    @TableField("seller_licence")
    private String sellerLicence;
    /**
     * 店铺评级,默认为0
     */
    @TableField("seller_rank")
    private Integer sellerRank;
    /**
     * 评分
     */
    @TableField("seller_grade")
    private Double sellerGrade;
    /**
     * 店铺地址
     */
    @TableField("seller_address")
    private String sellerAddress;
    /**
     * 关键字
     */
    @TableField("seller_keyword")
    private String sellerKeyword;
    /**
     * 手机号码
     */
    @TableField("seller_tel")
    private String sellerTel;
    /**
     * 余额
     */
    @TableField("balance_money")
    private BigDecimal balanceMoney;
    /**
     * 账单金额,用于提现的计算
     */
    @TableField("bill_money")
    private BigDecimal billMoney;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型 1自营 2加盟 3合作
     */
    @TableField("seller_type")
    private Integer sellerType;
    /**
     * 所属区域,多个区域用逗号进行相隔
     */
    private String regions;


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

    public Integer getSellerRank() {
        return sellerRank;
    }

    public void setSellerRank(Integer sellerRank) {
        this.sellerRank = sellerRank;
    }

    public Double getSellerGrade() {
        return sellerGrade;
    }

    public void setSellerGrade(Double sellerGrade) {
        this.sellerGrade = sellerGrade;
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

    public String getSellerTel() {
        return sellerTel;
    }

    public void setSellerTel(String sellerTel) {
        this.sellerTel = sellerTel;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSellerType() {
        return sellerType;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "Seller{" +
        ", sellerId=" + sellerId +
        ", sellerAccount=" + sellerAccount +
        ", sellerName=" + sellerName +
        ", sellerAlias=" + sellerAlias +
        ", sellerLogo=" + sellerLogo +
        ", sellerLicence=" + sellerLicence +
        ", sellerRank=" + sellerRank +
        ", sellerGrade=" + sellerGrade +
        ", sellerAddress=" + sellerAddress +
        ", sellerKeyword=" + sellerKeyword +
        ", sellerTel=" + sellerTel +
        ", balanceMoney=" + balanceMoney +
        ", billMoney=" + billMoney +
        ", remark=" + remark +
        ", sellerType=" + sellerType +
        ", regions=" + regions +
        "}";
    }
}
