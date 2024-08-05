package com.xht.cloud.admin.module.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.log.dto.OperationLogDTO;
import com.xht.cloud.admin.module.log.convert.SysOperationLogConvert;
import com.xht.cloud.admin.module.log.domain.dataobject.SysOperationLogDO;
import com.xht.cloud.admin.module.log.domain.request.SysOperationLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysOperationLogResponse;
import com.xht.cloud.admin.module.log.mapper.SysOperationLogMapper;
import com.xht.cloud.admin.module.log.service.ISysOperationLogService;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 描述 ：系统操作日志记录
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOperationLogServiceImpl implements ISysOperationLogService {

    private final SysOperationLogMapper sysOperationLogMapper;

    private final SysOperationLogConvert sysOperationLogConvert;

    /**
     * 根据id查询详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link SysOperationLogResponse}
     */
    @Override
    public SysOperationLogResponse findById(String id) {
        return sysOperationLogConvert.convert(sysOperationLogMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询
     *
     * @param queryRequest {@link SysOperationLogQueryRequest}
     * @return {@link PageResponse< SysOperationLogResponse >} 分页详情
     */
    @Override
    public PageResponse<SysOperationLogResponse> findPage(SysOperationLogQueryRequest queryRequest) {
        IPage<SysOperationLogDO> sysLogIPage = sysOperationLogMapper.selectPage(PageTool.getPage(queryRequest),
                sysOperationLogConvert.lambdaQuery(queryRequest));
        return sysOperationLogConvert.convert(sysLogIPage);
    }

    /**
     * 保存操作日志
     *
     * @param operationLogDTO {@link OperationLogDTO}
     */
    @Async
    @Override
    public void saveOperationLog(OperationLogDTO operationLogDTO) {
        try {
            SysOperationLogDO sysLogDO = sysOperationLogConvert.dtoToDo(operationLogDTO);
            sysLogDO.setOperateName(SecurityContextUtil.getUserAccount());
            sysOperationLogMapper.insert(sysLogDO);
        } catch (Exception e) {
            log.info("[操作日志]存储错误 {}", e.getMessage(), e);
        }
    }

}
