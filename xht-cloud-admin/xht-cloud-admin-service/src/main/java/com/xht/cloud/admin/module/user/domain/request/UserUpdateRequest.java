package com.xht.cloud.admin.module.user.domain.request;

import com.xht.cloud.admin.api.user.enums.UserSexEnums;
import com.xht.cloud.admin.api.user.enums.UserStatusEnums;
import com.xht.cloud.framework.domain.request.IUpdateRequestFun;
import com.xht.cloud.framework.web.validation.TelephoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述 ：用户修改对象
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "用户修改对象", description = "用户中心")
public class UserUpdateRequest implements IUpdateRequestFun<String> {

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @TelephoneNumber
    private String contactPhone;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    @NotEmpty(message = "部门id不能为空")
    private String deptId;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态")
    @NotEmpty(message = "用户状态不能为空")
    private UserStatusEnums userStatus;

    /**
     * 身份证号码
     */
    @Schema(description = "身份证号码")
    @NotEmpty(message = "身份证号码不能为空")
    private String identityCard;

    /**
     * 性别，0：未知，1：男，2：女
     */
    @Schema(description = "性别")
    @NotEmpty(message = "性别不能为空")
    private UserSexEnums userSex;

    /**
     * 年龄
     */
    @Schema(description = "年龄")
    @NotEmpty(message = "年龄不能为空")
    private String userAge;

    /**
     * 联系手机号
     */
    @Schema(description = "联系手机号")
    @NotEmpty(message = "联系手机号不能为空")
    private String contactMobile;

    /**
     * 地址id
     */
    @Schema(description = "地址id")
    @NotEmpty(message = "地址id不能为空")
    private String addressId;

    /**
     * 地址名称
     */
    @Schema(description = "地址名称")
    @NotEmpty(message = "地址名称不能为空")
    private String addressName;

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return null;
    }
}
