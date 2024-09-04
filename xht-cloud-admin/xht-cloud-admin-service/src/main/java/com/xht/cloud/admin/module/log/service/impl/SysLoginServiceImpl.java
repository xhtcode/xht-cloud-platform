package com.xht.cloud.admin.module.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.admin.module.log.convert.SysLoginLogConvert;
import com.xht.cloud.admin.module.log.domain.dataobject.SysLoginLogDO;
import com.xht.cloud.admin.module.log.domain.request.SysLoginLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.admin.module.log.mapper.SysLoginLogMapper;
import com.xht.cloud.admin.module.log.service.ISysLoginService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLoginServiceImpl implements ISysLoginService {

    private final SysLoginLogMapper sysLoginLogMapper;

    private final SysLoginLogConvert sysLoginLogConvert;

    /**
     * 根据id查询详细登录日志
     *
     * @param id {@link Long} 数据库主键
     * @return {@link SysLoginLogResponse}
     */
    @Override
    public SysLoginLogResponse findById(Long id) {
        return sysLoginLogConvert.convert(sysLoginLogMapper.findById(id).orElse(null));
    }

    /**
     * 分页查询登录日志
     *
     * @param queryRequest {@link SysLoginLogQueryRequest}
     * @return {@link SysLoginLogResponse} 分页详情
     */
    @Override
    public PageResponse<SysLoginLogResponse> findPage(SysLoginLogQueryRequest queryRequest) {
        LambdaQueryWrapper<SysLoginLogDO> queryWrapper = sysLoginLogConvert.lambdaQuery(queryRequest);
        IPage<SysLoginLogDO> sysLogIPage = sysLoginLogMapper.selectPage(PageTool.getPage(queryRequest), queryWrapper);
        return sysLoginLogConvert.convert(sysLogIPage);
    }

    /**
     * 保存操作登录日志
     *
     * @param loginLogDTO {@link SysLoginLogDTO}
     */
    @Async
    @Override
    public void saveOperationLog(SysLoginLogDTO loginLogDTO) {
        try {
            sysLoginLogMapper.insert(sysLoginLogConvert.dtoToDo(loginLogDTO));
        } catch (Exception e) {
            log.info("[登录日志]存储错误 {}", e.getMessage(), e);
        }
    }
}
