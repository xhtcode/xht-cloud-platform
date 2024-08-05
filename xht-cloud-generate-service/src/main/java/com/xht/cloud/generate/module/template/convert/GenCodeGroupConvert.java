package com.xht.cloud.generate.module.template.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupCreateRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupQueryRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeGroupUpdateRequest;
import com.xht.cloud.generate.module.template.domain.response.GenCodeGroupResponse;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeGroupDO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenCodeGroupConvert {

    /**
     * {@link GenCodeGroupCreateRequest} to {@link GenCodeGroupDO}
     */
    @Named(value = "addRequestToDo")
    GenCodeGroupDO toDO(GenCodeGroupCreateRequest createRequest);

    /**
     * {@link GenCodeGroupUpdateRequest} to {@link GenCodeGroupDO}
     */
    @Named(value = "updateRequestToDo")
    GenCodeGroupDO toDO(GenCodeGroupUpdateRequest updateRequest);

    /**
     * {@link GenCodeGroupQueryRequest} to {@link GenCodeGroupDO}
     */
    @Named(value = "queryRequestToDo")
    GenCodeGroupDO toDO(GenCodeGroupQueryRequest queryRequest);

    /**
     * {@link GenCodeGroupDO} to {@link GenCodeGroupResponse}
     */
    @Named(value = "DoToResponse")
    GenCodeGroupResponse toResponse(GenCodeGroupDO testDO);


    /**
     * list转换 {@link GenCodeGroupDO} to {@link GenCodeGroupResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenCodeGroupResponse> toResponse(List<GenCodeGroupDO> testDO);

    /**
     * 分页转换 {@link GenCodeGroupDO} to {@link GenCodeGroupResponse}
     */
    default PageResponse<GenCodeGroupResponse> toPageResponse(IPage<GenCodeGroupDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenCodeGroupResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
