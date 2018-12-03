package com.anjoy.frozen.seller.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 买家信息表.(俗称小B)
 * </p>
 *
 * @author 
 * @since 2018-08-15
 */
public class Buyer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 来源于user表中的ID
     */
    @TableId("buyer_id")
    private Long buyerId;
    /**
     * 买家名称(也就是餐馆名称)
     */
    @TableField("buyer_name")
    private String buyerName;
    /**
     * 餐馆地址
     */
    @TableField("buyer_address")
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
    @TableField("province_id")
    private Integer provinceId;
    /**
     * 所属市
     */
    @TableField("city_id")
    private Integer cityId;
    /**
     * 所属区
     */
    @TableField("region_id")
    private Integer regionId;
    /**
     * 负责人
     */
    @TableField("boss_name")
    private String bossName;
    /**
     * 负责人的电话号码
     */
    @TableField("boss_tel")
    private String bossTel;
    /**
     * 联系人
     */
    @TableField("contact_name")
    private String contactName;
    /**
     * 联系人电话号码
     */
    @TableField("contact_tel")
    private String contactTel;
    /**
     * 账号余额
     */
    @TableField("balance_money")
    private BigDecimal balanceMoney;
    /**
     * 客户的类型，1为火锅店，2为小餐馆，3为中餐馆，4,为烧烤
     */
    @TableField("buyer_type")
    private Integer buyerType;
    /**
     * 所属销售人员
     */
    @TableField("sale_id")
    private Long saleId;
    /**
     * 所属卖家
     */
    @TableField("seller_id")
    private Long sellerId;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 买家logo图片
     */
    @TableField("buyer_logo")
    private String buyerLogo;
    /**
     * 方便配送人员容易找到位置，所拍摄的多张图片
     */
    @TableField("buyer_images")
    private String buyerImages;
    /**
     * 餐馆，早上开门时间
     */
    @TableField("open_time")
    private String openTime;
    /**
     * 餐厅晚上关门时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 可以允许的物流最晚送达时间
     */
    @TableField("delivery_time")
    private String deliveryTime;
    @TableField("delivery_area_id")
    private Long deliveryAreaId;
    /**
     * 买家菜单
     */
    @TableField("buyer_menu")
    private String buyerMenu;
    /**
     * 客户级别，1为A类客户,2为B类客户,3为C类客户
     */
    @TableField("buyer_level")
    private Integer buyerLevel;


    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossTel() {
        return bossTel;
    }

    public void setBossTel(String bossTel) {
        this.bossTel = bossTel;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public Integer getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(Integer buyerType) {
        this.buyerType = buyerType;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBuyerLogo() {
        return buyerLogo;
    }

    public void setBuyerLogo(String buyerLogo) {
        this.buyerLogo = buyerLogo;
    }

    public String getBuyerImages() {
        return buyerImages;
    }

    public void setBuyerImages(String buyerImages) {
        this.buyerImages = buyerImages;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Long getDeliveryAreaId() {
        return deliveryAreaId;
    }

    public void setDeliveryAreaId(Long deliveryAreaId) {
        this.deliveryAreaId = deliveryAreaId;
    }

    public String getBuyerMenu() {
        return buyerMenu;
    }

    public void setBuyerMenu(String buyerMenu) {
        this.buyerMenu = buyerMenu;
    }

    public Integer getBuyerLevel() {
        return buyerLevel;
    }

    public void setBuyerLevel(Integer buyerLevel) {
        this.buyerLevel = buyerLevel;
    }

    @Override
    public String toString() {
        return "Buyer{" +
        ", buyerId=" + buyerId +
        ", buyerName=" + buyerName +
        ", buyerAddress=" + buyerAddress +
        ", lng=" + lng +
        ", lat=" + lat +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", regionId=" + regionId +
        ", bossName=" + bossName +
        ", bossTel=" + bossTel +
        ", contactName=" + contactName +
        ", contactTel=" + contactTel +
        ", balanceMoney=" + balanceMoney +
        ", buyerType=" + buyerType +
        ", saleId=" + saleId +
        ", sellerId=" + sellerId +
        ", remark=" + remark +
        ", buyerLogo=" + buyerLogo +
        ", buyerImages=" + buyerImages +
        ", openTime=" + openTime +
        ", endTime=" + endTime +
        ", deliveryTime=" + deliveryTime +
        ", deliveryAreaId=" + deliveryAreaId +
        ", buyerMenu=" + buyerMenu +
        ", buyerLevel=" + buyerLevel +
        "}";
    }
}
