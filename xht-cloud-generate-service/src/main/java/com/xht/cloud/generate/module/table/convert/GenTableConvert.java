package com.xht.cloud.generate.module.table.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.table.domain.request.GenTableCreateRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableQueryRequest;
import com.xht.cloud.generate.module.table.domain.request.GenTableUpdateRequest;
import com.xht.cloud.generate.module.table.domain.response.GenTableResponse;
import com.xht.cloud.generate.module.table.domain.dataobject.GenTableDO;
import java.util.List;
import java.util.Objects;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：代码生成器-数据库信息
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenTableConvert {

    /**
     * {@link GenTableCreateRequest} to {@link GenTableDO}
     */
    @Named(value = "addRequestToDo")
    GenTableDO toDO(GenTableCreateRequest createRequest);

    /**
     * {@link GenTableUpdateRequest} to {@link GenTableDO}
     */
    @Named(value = "updateRequestToDo")
    GenTableDO toDO(GenTableUpdateRequest updateRequest);

    /**
     * {@link GenTableQueryRequest} to {@link GenTableDO}
     */
    @Named(value = "queryRequestToDo")
    GenTableDO toDO(GenTableQueryRequest queryRequest);

    /**
     * {@link GenTableDO} to {@link GenTableResponse}
     */
    @Named(value = "DoToResponse")
    GenTableResponse toResponse(GenTableDO testDO);


    /**
     * list转换 {@link GenTableDO} to {@link GenTableResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenTableResponse> toResponse(List<GenTableDO> testDO);

    /**
     * 分页转换 {@link GenTableDO} to {@link GenTableResponse}
     */
    default PageResponse<GenTableResponse> toPageResponse(IPage<GenTableDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenTableResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
