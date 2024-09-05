package com.xht.cloud.generate.module.database.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.module.database.domain.dataobject.GenDatabaseDO;
import com.xht.cloud.generate.module.database.domain.request.GenDatabaseQueryRequest;
import com.xht.cloud.generate.module.database.mapper.GenDatabaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class GenDatabaseDao extends BaseDaoImpl<GenDatabaseMapper, GenDatabaseDO> {

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<GenDatabaseDO> pageQueryRequest(GenDatabaseQueryRequest queryRequest) {
        LambdaQueryWrapper<GenDatabaseDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(StringUtils.hasText(queryRequest.getConnName()), GenDatabaseDO::getConnName, queryRequest.getConnName())
                .eq(StringUtils.hasText(queryRequest.getDbUrl()), GenDatabaseDO::getDbUrl, queryRequest.getDbUrl())
                .eq(StringUtils.hasText(queryRequest.getDbType()), GenDatabaseDO::getDbType, queryRequest.getDbType())
                .eq(StringUtils.hasText(queryRequest.getDbName()), GenDatabaseDO::getDbName, queryRequest.getDbName())
                .eq(StringUtils.hasText(queryRequest.getDbDescribe()), GenDatabaseDO::getDbDescribe, queryRequest.getDbDescribe())
                .eq(StringUtils.hasText(queryRequest.getHost()), GenDatabaseDO::getHost, queryRequest.getHost())
                .eq(Objects.nonNull(queryRequest.getPort()), GenDatabaseDO::getPort, queryRequest.getPort())
                .eq(StringUtils.hasText(queryRequest.getUserName()), GenDatabaseDO::getUserName, queryRequest.getUserName())
                .eq(StringUtils.hasText(queryRequest.getPassWord()), GenDatabaseDO::getPassWord, queryRequest.getPassWord())
                .eq(!ObjectUtils.isEmpty(queryRequest.getSort()), GenDatabaseDO::getSort, queryRequest.getSort());
        return page(PageTool.getPage(queryRequest), wrapper);
    }

    /**
     * 查询全部
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public List<GenDatabaseDO> listQueryRequest(GenDatabaseQueryRequest queryRequest) {
        LambdaQueryWrapper<GenDatabaseDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(StringUtils.hasText(queryRequest.getConnName()), GenDatabaseDO::getConnName, queryRequest.getConnName())
                .eq(StringUtils.hasText(queryRequest.getDbUrl()), GenDatabaseDO::getDbUrl, queryRequest.getDbUrl())
                .eq(StringUtils.hasText(queryRequest.getDbType()), GenDatabaseDO::getDbType, queryRequest.getDbType())
                .eq(StringUtils.hasText(queryRequest.getDbName()), GenDatabaseDO::getDbName, queryRequest.getDbName())
                .eq(StringUtils.hasText(queryRequest.getDbDescribe()), GenDatabaseDO::getDbDescribe, queryRequest.getDbDescribe())
                .eq(StringUtils.hasText(queryRequest.getHost()), GenDatabaseDO::getHost, queryRequest.getHost())
                .eq(Objects.nonNull(queryRequest.getPort()), GenDatabaseDO::getPort, queryRequest.getPort())
                .eq(StringUtils.hasText(queryRequest.getUserName()), GenDatabaseDO::getUserName, queryRequest.getUserName())
                .eq(StringUtils.hasText(queryRequest.getPassWord()), GenDatabaseDO::getPassWord, queryRequest.getPassWord())
                .eq(!ObjectUtils.isEmpty(queryRequest.getSort()), GenDatabaseDO::getSort, queryRequest.getSort());
        return list(wrapper);
    }
}
