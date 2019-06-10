package com.anjoy.cloud.component.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2019-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_EAM_SBTYPENAME")
@ApiModel(value="TEamSbtypename对象", description="")
public class TEamSbtypename implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fbillnumber;

    private String fsbcode1;

    private String fsbcode2;

    private String fsbcode3;

    private String fsbname2;

    private String fsbname3;

    private String fsbtype;

    private String fflag;

    private Date fsystime;


}
