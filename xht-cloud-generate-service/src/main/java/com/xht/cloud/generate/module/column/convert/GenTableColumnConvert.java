package com.xht.cloud.generate.module.column.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnCreateRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnQueryRequest;
import com.xht.cloud.generate.module.column.domain.request.GenTableColumnUpdateRequest;
import com.xht.cloud.generate.module.column.domain.response.GenTableColumnResponse;
import com.xht.cloud.generate.module.column.domain.dataobject.GenTableColumnDO;
import java.util.List;
import java.util.Objects;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：代码生成业务字段
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenTableColumnConvert {

    /**
     * {@link GenTableColumnCreateRequest} to {@link GenTableColumnDO}
     */
    @Named(value = "addRequestToDo")
    GenTableColumnDO toDO(GenTableColumnCreateRequest createRequest);

    /**
     * {@link GenTableColumnUpdateRequest} to {@link GenTableColumnDO}
     */
    @Named(value = "updateRequestToDo")
    GenTableColumnDO toDO(GenTableColumnUpdateRequest updateRequest);

    /**
     * {@link GenTableColumnQueryRequest} to {@link GenTableColumnDO}
     */
    @Named(value = "queryRequestToDo")
    GenTableColumnDO toDO(GenTableColumnQueryRequest queryRequest);

    /**
     * {@link GenTableColumnDO} to {@link GenTableColumnResponse}
     */
    @Named(value = "DoToResponse")
    GenTableColumnResponse toResponse(GenTableColumnDO testDO);


    /**
     * list转换 {@link GenTableColumnDO} to {@link GenTableColumnResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenTableColumnResponse> toResponse(List<GenTableColumnDO> testDO);

    /**
     * 分页转换 {@link GenTableColumnDO} to {@link GenTableColumnResponse}
     */
    default PageResponse<GenTableColumnResponse> toPageResponse(IPage<GenTableColumnDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenTableColumnResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
