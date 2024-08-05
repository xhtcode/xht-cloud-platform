package com.xht.cloud.admin.module.log.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.cloud.admin.api.log.dto.OperationLogDTO;
import com.xht.cloud.admin.module.log.domain.dataobject.SysOperationLogDO;
import com.xht.cloud.admin.module.log.domain.request.SysOperationLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysOperationLogResponse;
import com.xht.cloud.framework.mybatis.convert.PageConvert;
import com.xht.cloud.framework.utils.support.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 描述 ：系统操作日志记录
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface SysOperationLogConvert extends PageConvert<SysOperationLogDO, SysOperationLogResponse> {

    SysOperationLogDO dtoToDo(OperationLogDTO operationLogDTO);

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<SysOperationLogDO> lambdaQuery(SysOperationLogQueryRequest entity) {
        if (Objects.isNull(entity)) {
            return new LambdaQueryWrapper<>();
        }
        LambdaQueryWrapper<SysOperationLogDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getServerName()), SysOperationLogDO::getServerName, entity.getServerName())
                .like(StringUtils.hasText(entity.getBusName()), SysOperationLogDO::getBusName, entity.getBusName())
                .eq(!ObjectUtils.isEmpty(entity.getStatus()), SysOperationLogDO::getStatus, entity.getStatus())
                ;
    }

}
