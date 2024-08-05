package com.xht.cloud.generate.module.template.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateCreateRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateQueryRequest;
import com.xht.cloud.generate.module.template.domain.request.GenCodeTemplateUpdateRequest;
import com.xht.cloud.generate.module.template.domain.response.GenCodeTemplateResponse;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeTemplateDO;
import java.util.List;
import java.util.Objects;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：代码生成器-代码模板
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenCodeTemplateConvert {

    /**
     * {@link GenCodeTemplateCreateRequest} to {@link GenCodeTemplateDO}
     */
    @Named(value = "addRequestToDo")
    GenCodeTemplateDO toDO(GenCodeTemplateCreateRequest createRequest);

    /**
     * {@link GenCodeTemplateUpdateRequest} to {@link GenCodeTemplateDO}
     */
    @Named(value = "updateRequestToDo")
    GenCodeTemplateDO toDO(GenCodeTemplateUpdateRequest updateRequest);

    /**
     * {@link GenCodeTemplateQueryRequest} to {@link GenCodeTemplateDO}
     */
    @Named(value = "queryRequestToDo")
    GenCodeTemplateDO toDO(GenCodeTemplateQueryRequest queryRequest);

    /**
     * {@link GenCodeTemplateDO} to {@link GenCodeTemplateResponse}
     */
    @Named(value = "DoToResponse")
    GenCodeTemplateResponse toResponse(GenCodeTemplateDO testDO);


    /**
     * list转换 {@link GenCodeTemplateDO} to {@link GenCodeTemplateResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenCodeTemplateResponse> toResponse(List<GenCodeTemplateDO> testDO);

    /**
     * 分页转换 {@link GenCodeTemplateDO} to {@link GenCodeTemplateResponse}
     */
    default PageResponse<GenCodeTemplateResponse> toPageResponse(IPage<GenCodeTemplateDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenCodeTemplateResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
