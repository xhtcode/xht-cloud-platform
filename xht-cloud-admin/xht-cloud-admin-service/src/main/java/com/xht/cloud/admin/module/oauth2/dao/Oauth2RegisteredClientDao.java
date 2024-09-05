package com.xht.cloud.admin.module.oauth2.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.oauth2.domain.dataobject.Oauth2RegisteredClientDO;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientQueryRequest;
import com.xht.cloud.admin.module.oauth2.mapper.Oauth2RegisteredClientMapper;
import com.xht.cloud.framework.mybatis.dao.BaseDaoImpl;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author : 小糊涂
 **/
@Slf4j
@Repository
public class Oauth2RegisteredClientDao extends BaseDaoImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClientDO> {

    /**
     * 分页查询
     *
     * @param queryRequest 查询参数
     * @return 分页数据
     */
    public IPage<Oauth2RegisteredClientDO> pageQueryRequest(Oauth2RegisteredClientQueryRequest queryRequest) {
        LambdaQueryWrapper<Oauth2RegisteredClientDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(StringUtils.hasText(queryRequest.getClientId()), Oauth2RegisteredClientDO::getClientId, queryRequest.getClientId())
                .eq(StringUtils.hasText(queryRequest.getClientSecret()), Oauth2RegisteredClientDO::getClientSecret, queryRequest.getClientSecret())
                .eq(StringUtils.hasText(queryRequest.getAutoApprove()), Oauth2RegisteredClientDO::getAutoApprove, queryRequest.getAutoApprove())
                .eq(StringUtils.hasText(queryRequest.getClientName()), Oauth2RegisteredClientDO::getClientName, queryRequest.getClientName())
                .eq(StringUtils.hasText(queryRequest.getAuthorizationGrantTypes()), Oauth2RegisteredClientDO::getAuthorizationGrantTypes, queryRequest.getAuthorizationGrantTypes())
                .eq(StringUtils.hasText(queryRequest.getScopes()), Oauth2RegisteredClientDO::getScopes, queryRequest.getScopes());
        return page(PageTool.getPage(queryRequest), wrapper);
    }
}
