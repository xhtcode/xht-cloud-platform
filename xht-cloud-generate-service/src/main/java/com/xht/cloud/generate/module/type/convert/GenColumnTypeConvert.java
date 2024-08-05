package com.xht.cloud.generate.module.type.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeCreateRequest;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeQueryRequest;
import com.xht.cloud.generate.module.type.domain.request.GenColumnTypeUpdateRequest;
import com.xht.cloud.generate.module.type.domain.response.GenColumnTypeResponse;
import com.xht.cloud.generate.module.type.domain.dataobject.GenColumnTypeDO;
import java.util.List;
import java.util.Objects;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：代码生成器-字段类型对应
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenColumnTypeConvert {

    /**
     * {@link GenColumnTypeCreateRequest} to {@link GenColumnTypeDO}
     */
    @Named(value = "addRequestToDo")
    GenColumnTypeDO toDO(GenColumnTypeCreateRequest createRequest);

    /**
     * {@link GenColumnTypeUpdateRequest} to {@link GenColumnTypeDO}
     */
    @Named(value = "updateRequestToDo")
    GenColumnTypeDO toDO(GenColumnTypeUpdateRequest updateRequest);

    /**
     * {@link GenColumnTypeQueryRequest} to {@link GenColumnTypeDO}
     */
    @Named(value = "queryRequestToDo")
    GenColumnTypeDO toDO(GenColumnTypeQueryRequest queryRequest);

    /**
     * {@link GenColumnTypeDO} to {@link GenColumnTypeResponse}
     */
    @Named(value = "DoToResponse")
    GenColumnTypeResponse toResponse(GenColumnTypeDO testDO);


    /**
     * list转换 {@link GenColumnTypeDO} to {@link GenColumnTypeResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenColumnTypeResponse> toResponse(List<GenColumnTypeDO> testDO);

    /**
     * 分页转换 {@link GenColumnTypeDO} to {@link GenColumnTypeResponse}
     */
    default PageResponse<GenColumnTypeResponse> toPageResponse(IPage<GenColumnTypeDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenColumnTypeResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
