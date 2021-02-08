package com.tianruoxi.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author：Tianruoxi
 * @date：2021/2/5 17:58
 */

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "AdminLogin对象", description = "")
public class AdminLoginParam {
  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  @ApiModelProperty(value = "密码", required = true)
  private String password;
  @ApiModelProperty(value = "验证码", required = true)
  private String Code;

}
