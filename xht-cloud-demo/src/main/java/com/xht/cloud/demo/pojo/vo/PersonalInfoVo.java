package com.xht.cloud.demo.pojo.vo;


import com.xht.cloud.framework.web.desensitization.Desensitization;
import com.xht.cloud.framework.web.desensitization.DesensitizationStrategyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：人员信息
 *
 * @author 小糊涂
 **/
@Data
@Builder
@Schema(title = "PersonalInfoVo(人员信息)")
public class PersonalInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Desensitization(DesensitizationStrategyEnum.PHONE)
    @Schema(description = "手机号")
    private String phone;

    @Desensitization(DesensitizationStrategyEnum.FIXED_PHONE)
    @Schema(description = "手机号")
    private String fixedPhone;

    @Desensitization(DesensitizationStrategyEnum.EMAIL)
    @Schema(description = "邮箱")
    private String email;

    @Desensitization(DesensitizationStrategyEnum.ID_CARD)
    @Schema(description = "身份证号")
    private String idCard;

    @Desensitization(DesensitizationStrategyEnum.ADDRESS)
    @Schema(description = "地址")
    private String address;

    @Desensitization(DesensitizationStrategyEnum.BANK_CARD)
    @Schema(description = "银行卡号")
    private String bankCard;

    @Desensitization(DesensitizationStrategyEnum.NAME)
    @Schema(description = "姓名")
    private String name;

    @Desensitization(DesensitizationStrategyEnum.PASSWORD)
    @Schema(description = "密码")
    private String password; // 密码

}