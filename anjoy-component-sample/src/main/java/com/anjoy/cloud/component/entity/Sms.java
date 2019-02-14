package com.anjoy.cloud.component.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 手机短信验证码
 * </p>
 *
 * @author 
 * @since 2019-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Sms对象", description="手机短信验证码")
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自动增加ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "内容")
    private String message;

    @ApiModelProperty(value = "备注短信的作用或者说明")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
