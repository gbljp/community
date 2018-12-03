package com.anjoy.cloud.component.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 买家信息表.(俗称小B)
 * </p>
 *
 * @author 
 * @since 2018-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Buyer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 来源于user表中的ID
     */
    private Long buyerId;

    /**
     * 买家名称(也就是餐馆名称)
     */
    private String buyerName;

    /**
     * 餐馆地址
     */
    private String buyerAddress;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 所属省
     */
    private Integer provinceId;

    /**
     * 所属市
     */
    private Integer cityId;

    /**
     * 所属区
     */
    private Integer regionId;

    /**
     * 负责人
     */
    private String bossName;

    /**
     * 负责人的电话号码
     */
    private String bossTel;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系人电话号码
     */
    private String contactTel;

    /**
     * 账号余额
     */
    private BigDecimal balanceMoney;

    /**
     * 客户的类型，1为火锅店，2为小餐馆，3为中餐馆，4,为烧烤
     */
    private Integer buyerType;

    /**
     * 所属销售人员
     */
    private Long saleId;

    /**
     * 所属卖家
     */
    private Long sellerId;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 买家logo图片
     */
    private String buyerLogo;

    /**
     * 方便配送人员容易找到位置，所拍摄的多张图片
     */
    private String buyerImages;

    /**
     * 餐馆，早上开门时间
     */
    private String openTime;

    /**
     * 餐厅晚上关门时间
     */
    private String endTime;

    /**
     * 可以允许的物流最晚送达时间
     */
    private String deliveryTime;

    private Long deliveryAreaId;

    /**
     * 买家菜单
     */
    private String buyerMenu;

    /**
     * 客户级别，1为A类客户,2为B类客户,3为C类客户
     */
    private Integer buyerLevel;


}
