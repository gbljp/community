package com.anjoy.cloud.component.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 卖家信息表.(俗称经销商)
 * </p>
 *
 * @author 
 * @since 2018-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 来源于users的ID
     */
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
    private Integer sellerRank;

    /**
     * 评分
     */
    private Double sellerGrade;

    /**
     * 店铺地址
     */
    private String sellerAddress;

    /**
     * 关键字
     */
    private String sellerKeyword;

    /**
     * 手机号码
     */
    private String sellerTel;

    /**
     * 余额
     */
    private BigDecimal balanceMoney;

    /**
     * 账单金额,用于提现的计算
     */
    private BigDecimal billMoney;

    /**
     * 备注
     */
    private String remark;

    /**
     * 类型 1自营 2加盟 3合作
     */
    private Integer sellerType;

    /**
     * 所属区域,多个区域用逗号进行相隔
     */
    private String regions;


}
