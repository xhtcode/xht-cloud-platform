package com.xht.cloud.demo.pojo.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.cloud.framework.core.enums.SuperHashChildEnums;
import com.xht.cloud.framework.jackson.desensitization.annotation.SensitiveField;
import com.xht.cloud.framework.jackson.desensitization.constant.SensitiveFieldConstant;
import com.xht.cloud.framework.jackson.enums.annotation.TransEnum;
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

    @Serial private static final long serialVersionUID = 1L;

    @TransEnum(suffix = "Text")
    private SuperHashChildEnums a;

    @TransEnum
    private SuperHashChildEnums b;

    @SensitiveField(type = SensitiveFieldConstant.PHONE)
    @Schema(description = "手机号")private String phone;

    @JsonSerialize()
    @SensitiveField(type = SensitiveFieldConstant.FIXED_PHONE)
    @Schema(description = "手机号")private String fixedPhone;

    @SensitiveField(type = SensitiveFieldConstant.EMAIL)
    @Schema(description = "邮箱")private String email;

    @SensitiveField(type = SensitiveFieldConstant.ID_CARD)
    @Schema(description = "身份证号")private String idCard;

    @SensitiveField(type = SensitiveFieldConstant.ADDRESS)
    @Schema(description = "地址")private String address;

    @SensitiveField(type = SensitiveFieldConstant.BANK_CARD)
    @Schema(description = "银行卡号")private String bankCard;

    @SensitiveField(type = SensitiveFieldConstant.NAME)
    @Schema(description = "姓名")private String name;

    @SensitiveField(type = SensitiveFieldConstant.PASSWORD)
    @Schema(description = "密码")private String password; // 密码

}