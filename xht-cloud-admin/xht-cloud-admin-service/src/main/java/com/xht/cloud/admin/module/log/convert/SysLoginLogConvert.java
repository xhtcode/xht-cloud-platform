package com.xht.cloud.admin.module.log.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.admin.api.user.enums.UserTypeEnums;
import com.xht.cloud.admin.module.log.domain.dataobject.SysLoginLogDO;
import com.xht.cloud.admin.module.log.domain.request.SysLoginLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.framework.mybatis.convert.PageConvert;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysLoginLogConvert extends PageConvert<SysLoginLogDO, SysLoginLogResponse> {

    @Mapping(target = "userType", expression = "java(com.xht.cloud.admin.api.user.enums.UserTypeEnums.of(operationLogDTO.getUserType()))")
    SysLoginLogDO dtoToDo(SysLoginLogDTO operationLogDTO);

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<SysLoginLogDO> lambdaQuery(SysLoginLogQueryRequest entity) {
        LambdaQueryWrapper<SysLoginLogDO> wrapper = new LambdaQueryWrapper<>();
        if (!SecurityContextUtil.isAdmin()) {
            SecurityContextUtil.user().ifPresent(userDetailsBO -> wrapper.eq(SysLoginLogDO::getUserId, userDetailsBO.getId()));
            return wrapper;
        }
        if (Objects.isNull(entity)) {
            return wrapper;
        }
        UserTypeEnums userType = entity.getUserType();
        if (Objects.nonNull(userType)) {
            wrapper.eq(SysLoginLogDO::getUserType,userType.getValue());
        }
        return wrapper
                .eq(!Objects.isNull(entity.getLoginStatus()), SysLoginLogDO::getLoginStatus, entity.getLoginStatus())
                .like(!StringUtils.isEmpty(entity.getUserName()), SysLoginLogDO::getUserName, entity.getUserName())
                ;
    }
}
