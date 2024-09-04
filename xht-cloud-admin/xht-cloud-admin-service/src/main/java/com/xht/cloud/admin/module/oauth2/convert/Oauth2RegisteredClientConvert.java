package com.xht.cloud.admin.module.oauth2.convert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.oauth2.domain.dataobject.Oauth2RegisteredClientDO;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientCreateRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientQueryRequest;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientUpdateRequest;
import com.xht.cloud.admin.module.oauth2.domain.response.Oauth2RegisteredClientResponse;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.framework.mybatis.wrapper.EntityWrapper;
import com.xht.cloud.framework.utils.ObjectUtils;
import com.xht.cloud.framework.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述 ：oauth2 客户端信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface Oauth2RegisteredClientConvert extends
        IBaseConvert<Oauth2RegisteredClientCreateRequest, Oauth2RegisteredClientUpdateRequest, Oauth2RegisteredClientQueryRequest, Oauth2RegisteredClientResponse, Oauth2RegisteredClientDO>,
        EntityWrapper<Oauth2RegisteredClientDO> {

    /**
     * {@link Oauth2RegisteredClientCreateRequest} to {@link Oauth2RegisteredClientDO}
     */
    @Override
    @Named(value = "addRequestToDo")
    @Mapping(target = "scopes", expression = "java(convert(createRequest.getScopes()))")
    @Mapping(target = "authorizationGrantTypes", expression = "java(convert(createRequest.getAuthorizationGrantTypes()))")
    Oauth2RegisteredClientDO toDO(Oauth2RegisteredClientCreateRequest createRequest);

    /**
     * {@link Oauth2RegisteredClientUpdateRequest} to {@link Oauth2RegisteredClientDO}
     */
    @Override
    @Named(value = "updateRequestToDo")
    @Mapping(target = "scopes", expression = "java(convert(updateRequest.getScopes()))")
    @Mapping(target = "authorizationGrantTypes", expression = "java(convert(updateRequest.getAuthorizationGrantTypes()))")
    Oauth2RegisteredClientDO toDO(Oauth2RegisteredClientUpdateRequest updateRequest);

    /**
     * {@link Oauth2RegisteredClientDO} to {@link Oauth2RegisteredClientResponse}
     */
    @Override
    @Named(value = "DoToResponse")
    @Mapping(target = "scopes", expression = "java(convert(clientDO.getScopes()))")
    @Mapping(target = "authorizationGrantTypes", expression = "java(convert(clientDO.getAuthorizationGrantTypes()))")
    Oauth2RegisteredClientResponse toResponse(Oauth2RegisteredClientDO clientDO);

    /**
     * 分页转换 {@link Oauth2RegisteredClientDO} to {@link Oauth2RegisteredClientResponse}
     */
    default PageResponse<Oauth2RegisteredClientResponse> toPageResponse(IPage<Oauth2RegisteredClientDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<Oauth2RegisteredClientResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

    default Set<String> convert(String string) {
        if (!StringUtils.hasText(string)) return Collections.emptySet();
        String[] split = StringUtils.delimitedListToStringArray(string, StringConstant.DEFAULT_DELIMITER);
        return Arrays.stream(split).collect(Collectors.toSet());
    }

    default String convert(Set<String> strings) {
        if (CollectionUtils.isEmpty(strings)) return StringConstant.EMPTY_STR;
        return StringUtils.collectionToDelimitedString(strings, StringConstant.DEFAULT_DELIMITER);
    }

    /**
     * 获取 {@link LambdaQueryWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper}
     */
    @Override
    default LambdaQueryWrapper<Oauth2RegisteredClientDO> lambdaQuery(Oauth2RegisteredClientDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaQuery();
        }
        LambdaQueryWrapper<Oauth2RegisteredClientDO> wrapper = new LambdaQueryWrapper<>();
        return wrapper
                .eq(StringUtils.hasText(entity.getId()), Oauth2RegisteredClientDO::getId, entity.getId())
                .eq(StringUtils.hasText(entity.getClientId()), Oauth2RegisteredClientDO::getClientId, entity.getClientId())
                .eq(!ObjectUtils.isEmpty(entity.getClientIdIssuedAt()), Oauth2RegisteredClientDO::getClientIdIssuedAt, entity.getClientIdIssuedAt())
                .eq(StringUtils.hasText(entity.getClientSecret()), Oauth2RegisteredClientDO::getClientSecret, entity.getClientSecret())
                .eq(!ObjectUtils.isEmpty(entity.getClientSecretExpiresAt()), Oauth2RegisteredClientDO::getClientSecretExpiresAt, entity.getClientSecretExpiresAt())
                .eq(StringUtils.hasText(entity.getAutoApprove()), Oauth2RegisteredClientDO::getAutoApprove, entity.getAutoApprove())
                .eq(StringUtils.hasText(entity.getClientName()), Oauth2RegisteredClientDO::getClientName, entity.getClientName())
                .eq(StringUtils.hasText(entity.getClientAuthenticationMethods()), Oauth2RegisteredClientDO::getClientAuthenticationMethods, entity.getClientAuthenticationMethods())
                .eq(StringUtils.hasText(entity.getAuthorizationGrantTypes()), Oauth2RegisteredClientDO::getAuthorizationGrantTypes, entity.getAuthorizationGrantTypes())
                .eq(StringUtils.hasText(entity.getRedirectUris()), Oauth2RegisteredClientDO::getRedirectUris, entity.getRedirectUris())
                .eq(StringUtils.hasText(entity.getPostLogoutRedirectUris()), Oauth2RegisteredClientDO::getPostLogoutRedirectUris, entity.getPostLogoutRedirectUris())
                .eq(StringUtils.hasText(entity.getScopes()), Oauth2RegisteredClientDO::getScopes, entity.getScopes())
                .eq(!ObjectUtils.isEmpty(entity.getAccessTokenValidity()), Oauth2RegisteredClientDO::getAccessTokenValidity, entity.getAccessTokenValidity())
                .eq(!ObjectUtils.isEmpty(entity.getRefreshTokenValidity()), Oauth2RegisteredClientDO::getRefreshTokenValidity, entity.getRefreshTokenValidity())
                ;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper}
     */
    @Override
    default LambdaUpdateWrapper<Oauth2RegisteredClientDO> lambdaUpdate(Oauth2RegisteredClientDO entity) {
        if (Objects.isNull(entity)) {
            return lambdaUpdate();
        }
        LambdaUpdateWrapper<Oauth2RegisteredClientDO> wrapper = new LambdaUpdateWrapper<>();
        return wrapper
                .set(Oauth2RegisteredClientDO::getClientId, entity.getClientId())
                .set(Oauth2RegisteredClientDO::getClientIdIssuedAt, entity.getClientIdIssuedAt())
                .set(Oauth2RegisteredClientDO::getClientSecret, entity.getClientSecret())
                .set(Oauth2RegisteredClientDO::getClientSecretExpiresAt, entity.getClientSecretExpiresAt())
                .set(Oauth2RegisteredClientDO::getAutoApprove, entity.getAutoApprove())
                .set(Oauth2RegisteredClientDO::getClientName, entity.getClientName())
                .set(Oauth2RegisteredClientDO::getClientAuthenticationMethods, entity.getClientAuthenticationMethods())
                .set(Oauth2RegisteredClientDO::getAuthorizationGrantTypes, entity.getAuthorizationGrantTypes())
                .set(Oauth2RegisteredClientDO::getRedirectUris, entity.getRedirectUris())
                .set(Oauth2RegisteredClientDO::getPostLogoutRedirectUris, entity.getPostLogoutRedirectUris())
                .set(Oauth2RegisteredClientDO::getScopes, entity.getScopes())
                .set(Oauth2RegisteredClientDO::getAccessTokenValidity, entity.getAccessTokenValidity())
                .set(Oauth2RegisteredClientDO::getRefreshTokenValidity, entity.getRefreshTokenValidity())
                ;
    }

}
