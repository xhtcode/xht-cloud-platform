package com.xht.cloud.generate.module.entity.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import com.xht.cloud.generate.module.entity.domain.request.GenCodeBaseClassCreateRequest;
import com.xht.cloud.generate.module.entity.domain.request.GenCodeBaseClassQueryRequest;
import com.xht.cloud.generate.module.entity.domain.request.GenCodeBaseClassUpdateRequest;
import com.xht.cloud.generate.module.entity.domain.response.GenCodeBaseClassResponse;
import com.xht.cloud.generate.module.entity.domain.dataobject.GenCodeBaseClassDO;
import java.util.List;
import java.util.Objects;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * 描述 ：代码生成器-基类
 *
 * @author 小糊涂
 **/
@Mapper(componentModel = "spring")
public interface GenCodeBaseClassConvert {

    /**
     * {@link GenCodeBaseClassCreateRequest} to {@link GenCodeBaseClassDO}
     */
    @Named(value = "addRequestToDo")
    GenCodeBaseClassDO toDO(GenCodeBaseClassCreateRequest createRequest);

    /**
     * {@link GenCodeBaseClassUpdateRequest} to {@link GenCodeBaseClassDO}
     */
    @Named(value = "updateRequestToDo")
    GenCodeBaseClassDO toDO(GenCodeBaseClassUpdateRequest updateRequest);

    /**
     * {@link GenCodeBaseClassQueryRequest} to {@link GenCodeBaseClassDO}
     */
    @Named(value = "queryRequestToDo")
    GenCodeBaseClassDO toDO(GenCodeBaseClassQueryRequest queryRequest);

    /**
     * {@link GenCodeBaseClassDO} to {@link GenCodeBaseClassResponse}
     */
    @Named(value = "DoToResponse")
    GenCodeBaseClassResponse toResponse(GenCodeBaseClassDO testDO);


    /**
     * list转换 {@link GenCodeBaseClassDO} to {@link GenCodeBaseClassResponse}
     */
    @Named(value = "DoToResponseCollection")
    @IterableMapping(qualifiedByName = "DoToResponse")
    List<GenCodeBaseClassResponse> toResponse(List<GenCodeBaseClassDO> testDO);

    /**
     * 分页转换 {@link GenCodeBaseClassDO} to {@link GenCodeBaseClassResponse}
     */
    default PageResponse<GenCodeBaseClassResponse> toPageResponse(IPage<GenCodeBaseClassDO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<GenCodeBaseClassResponse> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

}
