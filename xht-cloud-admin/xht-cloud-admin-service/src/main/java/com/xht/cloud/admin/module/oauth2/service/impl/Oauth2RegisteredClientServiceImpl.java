package com.xht.cloud.admin.module.oauth2.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.cloud.admin.module.oauth2.convert.Oauth2RegisteredClientConvert;
import com.xht.cloud.admin.module.oauth2.dao.Oauth2RegisteredClientDao;
import com.xht.cloud.admin.module.oauth2.domain.dataobject.Oauth2RegisteredClientDO;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientCreateRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientQueryRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientUpdateRequest;
import com.xht.cloud.admin.module.oauth2.domain.response.Oauth2RegisteredClientResponse;
import com.xht.cloud.admin.module.oauth2.service.IOauth2RegisteredClientService;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.exception.BizException;
import com.xht.cloud.framework.mybatis.enums.DelFlagEnum;
import com.xht.cloud.framework.security.constant.SuperClientAutoApproveEnums;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import com.xht.cloud.framework.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.xht.cloud.framework.security.constant.SecurityConstant.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.xht.cloud.framework.security.constant.SecurityConstant.REFRESH_TOKEN_VALIDITY_SECONDS;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2RegisteredClientServiceImpl implements IOauth2RegisteredClientService {

    private final Oauth2RegisteredClientDao oauth2RegisteredClientDao;

    private final RegisteredClientRepository registeredClientRepository;

    private final Oauth2RegisteredClientConvert oauth2RegisteredClientConvert;

    /**
     * 创建客户端信息
     *
     * @param createRequest {@link Oauth2RegisteredClientCreateRequest}
     * @return {@link String} 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(Oauth2RegisteredClientCreateRequest createRequest) {
        // @formatter:off
        if (oauth2RegisteredClientDao.selectCount(Oauth2RegisteredClientDO::getClientId, createRequest.getClientId()) > 0) {
            throw new BizException(String.format("clientId `%s` 重复", createRequest.getClientId()));
        }
        if (oauth2RegisteredClientDao.selectCount(Oauth2RegisteredClientDO::getClientSecret, createRequest.getClientSecret()) > 0) {
            throw new BizException(String.format("clientSecret `%s` 重复 ", createRequest.getClientSecret()));
        }
        RegisteredClient build = build(createRequest, null).build();
        try{
            registeredClientRepository.save(build);
        }catch (Exception e){
            log.error("[client 存储失败] ",e);
            throw new BizException("请检查clientId和clientSecret是否规范");
        }
        String id = build.getId();
        updateOauth2RegisteredClient(createRequest,id);
        // @formatter:on
        return id;
    }

    /**
     * 根据id修改客户端信息
     *
     * @param updateRequest Oauth2RegisteredClientUpdateRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Oauth2RegisteredClientUpdateRequest updateRequest) {
        Assert.notNull(updateRequest, "客户端修改信息不能为空");
        Assert.notNull(updateRequest.getId(), "客户端修改信息id不能为空");
        LambdaQueryWrapper<Oauth2RegisteredClientDO> queryWrapper = new LambdaQueryWrapper<Oauth2RegisteredClientDO>()
                .eq(Oauth2RegisteredClientDO::getClientId, updateRequest.getClientId())
                .ne(Oauth2RegisteredClientDO::getId, updateRequest.getId());
        if (oauth2RegisteredClientDao.exists(queryWrapper)) {
            throw new BizException("clientId `{}` 重复", updateRequest.getClientId());
        }
        LambdaQueryWrapper<Oauth2RegisteredClientDO> lambdaQueryWrapper = new LambdaQueryWrapper<Oauth2RegisteredClientDO>()
                .eq(Oauth2RegisteredClientDO::getClientSecret, updateRequest.getClientSecret())
                .ne(Oauth2RegisteredClientDO::getId, updateRequest.getId());
        if (oauth2RegisteredClientDao.exists(lambdaQueryWrapper)) {
            throw new BizException("clientSecret `{} 重复 ", updateRequest.getClientSecret());
        }
        RegisteredClient build = build(updateRequest, updateRequest.getId()).build();
        try{
            registeredClientRepository.save(build);
        }catch (Exception e){
            log.error("[client 存储失败] ",e);
            throw new BizException("请检查clientId和clientSecret是否规范");
        }
        updateOauth2RegisteredClient(updateRequest,updateRequest.getId());
    }

    /**
     * 删除客户端
     *
     * @param ids {@link List<String>} id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> ids) {
        Assert.notEmpty(ids, "客户端信息ids不能为空");
        oauth2RegisteredClientDao.removeBatchByIds(ids);
    }

    /**
     * 根据id查询客户端详细
     *
     * @param id {@link String} 数据库主键
     * @return {@link Oauth2RegisteredClientResponse}
     */
    @Override
    public Oauth2RegisteredClientResponse findById(String id) {
        Assert.hasText(id, "客户端信息id不能为空");
        return oauth2RegisteredClientConvert.toResponse(oauth2RegisteredClientDao.getById(id));
    }

    /**
     * 分页查询客户端
     *
     * @param queryRequest {@link Oauth2RegisteredClientQueryRequest}
     * @return {@link Oauth2RegisteredClientResponse} 分页详情
     */
    @Override
    public PageResponse<Oauth2RegisteredClientResponse> findPage(Oauth2RegisteredClientQueryRequest queryRequest) {
        return oauth2RegisteredClientConvert.toPageResponse(oauth2RegisteredClientDao.pageQueryRequest(queryRequest));
    }


    private RegisteredClient.Builder build(Oauth2RegisteredClientCreateRequest createRequest, String id) {
        // @formatter:off
        RegisteredClient.Builder builder = RegisteredClient
                .withId(StringUtils.emptyToDefault(id,IdUtil.getSnowflakeNextIdStr()))
                .clientName(createRequest.getClientId())
                .clientId(createRequest.getClientName())
                .clientSecret(createRequest.getClientSecret()).clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        Set<String> authorizationGrantTypes = createRequest.getAuthorizationGrantTypes();
        LocalDateTime clientSecretExpiresAt = createRequest.getClientSecretExpiresAt();
        if (Objects.nonNull(clientSecretExpiresAt)){
            builder.clientSecretExpiresAt(clientSecretExpiresAt.toInstant(ZoneOffset.UTC));
        }
        if (!CollectionUtils.isEmpty(authorizationGrantTypes)) {
            for (String item : authorizationGrantTypes) {
                builder.authorizationGrantType(new AuthorizationGrantType(item));
            }
        }
        Set<String> scopes = createRequest.getScopes();
        if (!CollectionUtils.isEmpty(scopes)) {
            builder.scopes(strings -> strings.addAll(scopes));
        }
        builder.tokenSettings(TokenSettings
                .builder()
                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                .accessTokenTimeToLive(Duration
                        .ofSeconds(Optional.ofNullable(createRequest.getAccessTokenValidity())
                                .orElse(ACCESS_TOKEN_VALIDITY_SECONDS)))
                .refreshTokenTimeToLive(Duration.ofSeconds(Optional
                        .ofNullable(createRequest.getRefreshTokenValidity())
                        .orElse(REFRESH_TOKEN_VALIDITY_SECONDS))).build());
        builder.clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(SuperClientAutoApproveEnums.of(createRequest.getAutoApprove()).isValue())
                .build());
        builder.redirectUri(createRequest.getRedirectUris());
        // @formatter:on
        return builder;
    }


    public void updateOauth2RegisteredClient(Oauth2RegisteredClientCreateRequest createRequest, String id) {
        // @formatter:off
        LambdaUpdateWrapper<Oauth2RegisteredClientDO> lambdaUpdateWrapper = new  LambdaUpdateWrapper<Oauth2RegisteredClientDO>()
                .set(Oauth2RegisteredClientDO::getAutoApprove, createRequest.getAutoApprove())
                .set(Oauth2RegisteredClientDO::getAccessTokenValidity, createRequest.getAccessTokenValidity())
                .set(Oauth2RegisteredClientDO::getRefreshTokenValidity, createRequest.getRefreshTokenValidity())
                .set(Oauth2RegisteredClientDO::getDelFlag, DelFlagEnum.NORMAL.getValue())
                .set(Oauth2RegisteredClientDO::getCreateBy, SecurityContextUtil.getUserName())
                .set(Oauth2RegisteredClientDO::getUpdateBy,  SecurityContextUtil.getUserName())
                .set(Oauth2RegisteredClientDO::getCreateTime, LocalDateTime.now())
                .set(Oauth2RegisteredClientDO::getUpdateTime, LocalDateTime.now())
                .eq(Oauth2RegisteredClientDO::getId, id)
                ;
        oauth2RegisteredClientDao.update(lambdaUpdateWrapper);
        // @formatter:on
    }
}
