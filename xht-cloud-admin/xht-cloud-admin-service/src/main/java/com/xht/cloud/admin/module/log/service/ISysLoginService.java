package com.xht.cloud.admin.module.log.service;

import com.xht.cloud.admin.api.log.dto.SysLoginLogDTO;
import com.xht.cloud.admin.module.log.domain.request.SysLoginLogQueryRequest;
import com.xht.cloud.admin.module.log.domain.response.SysLoginLogResponse;
import com.xht.cloud.framework.domain.response.PageResponse;

/**
 * 描述 ：系统登录日志记录
 *
 * @author : 小糊涂
 **/
public interface ISysLoginService {

    /**
     * 根据id查询详细登录日志
     *
     * @param id {@link Long} 数据库主键
     * @return {@link SysLoginLogResponse}
     */
    SysLoginLogResponse findById(Long id);

    /**
     * 分页查询登录日志
     *
     * @param queryRequest {@link SysLoginLogQueryRequest}
     * @return {@link SysLoginLogResponse} 分页详情
     */
    PageResponse<SysLoginLogResponse> findPage(SysLoginLogQueryRequest queryRequest);

    /**
     * 保存操作登录日志
     *
     * @param loginLogDTO {@link SysLoginLogDTO}
     */
    void saveOperationLog(SysLoginLogDTO loginLogDTO);
}
