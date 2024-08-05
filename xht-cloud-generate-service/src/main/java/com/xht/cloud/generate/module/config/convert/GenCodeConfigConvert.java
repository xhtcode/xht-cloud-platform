package com.xht.cloud.generate.module.config.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigCreateRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigQueryRequest;
import com.xht.cloud.generate.module.config.domain.request.GenCodeConfigUpdateRequest;
import com.xht.cloud.generate.module.config.domain.response.GenCodeConfigResponse;
import com.xht.cloud.generate.module.config.domain.dataobject.GenCodeConfigDO;
import java.util.List;
import java.util.Objects;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：代码生成器-配置中心
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenCodeConfigConvert {

    /**
     * {@link GenCodeConfigCreateRequest} to {@link GenCodeConfigDO}
     */
    @Named(value = "addRequestToDo")
    GenCodeConfigDO toDO(GenCodeConfigCreateRequest createRequest);

    /**
     * {@link GenCodeConfigUpdateRequest} to {@link GenCodeConfigDO}
     */
    @Named(value = "updateRequestToDo")
    GenCodeConfigDO toDO(GenCodeConfigUpdateRequest updateRequest);

    /**
     * {@link GenCodeConfigQueryRequest} to {@link GenCodeConfigDO}
     */
    @Named(value = "queryRequestToDo")
    GenCodeConfigDO toDO(GenCodeConfigQueryRequest queryRequest);

    /**
     * {@link GenCodeConfigDO} to {@link GenCodeConfigResponse}
     */
    @Named(value = "DoToResponse")
    GenCodeConfigResponse toResponse(GenCodeConfigDO testDO);


    /**
     * list转换 {@link GenCodeConfigDO} to {@link GenCodeConfigResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenCodeConfigResponse> toResponse(List<GenCodeConfigDO> testDO);

    /**
     * 分页转换 {@link GenCodeConfigDO} to {@link GenCodeConfigResponse}
     */
    default PageResponse<GenCodeConfigResponse> toPageResponse(IPage<GenCodeConfigDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenCodeConfigResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
