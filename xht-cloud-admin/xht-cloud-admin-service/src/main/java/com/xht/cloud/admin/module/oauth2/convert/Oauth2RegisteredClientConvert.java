package com.xht.cloud.admin.module.oauth2.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.admin.module.oauth2.domain.dataobject.Oauth2RegisteredClientDO;
import com.xht.cloud.admin.module.oauth2.domain.request.Oauth2RegisteredClientCreateRequest;
import com.xht.cloud.admin.module.oauth2.domain.response.Oauth2RegisteredClientResponse;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.convert.IBaseConvert;
import com.xht.cloud.framework.mybatis.tool.PageTool;
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
        IBaseConvert<Oauth2RegisteredClientCreateRequest, Oauth2RegisteredClientResponse, Oauth2RegisteredClientDO> {

    /**
     * {@link Oauth2RegisteredClientCreateRequest} to {@link Oauth2RegisteredClientDO}
     */
    @Override
    @Named(value = "addRequestToDo")
    @Mapping(target = "scopes", expression = "java(convert(createRequest.getScopes()))")
    @Mapping(target = "authorizationGrantTypes", expression = "java(convert(createRequest.getAuthorizationGrantTypes()))")
    Oauth2RegisteredClientDO toDO(Oauth2RegisteredClientCreateRequest createRequest);

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

}
