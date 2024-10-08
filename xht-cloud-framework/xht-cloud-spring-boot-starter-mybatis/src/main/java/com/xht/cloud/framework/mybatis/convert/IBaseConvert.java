package com.xht.cloud.framework.mybatis.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.domain.request.Request;
import com.xht.cloud.framework.domain.response.AbstractResponse;
import com.xht.cloud.framework.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.tool.PageTool;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述 ：公共Convert转换器
 *
 * @author 小糊涂
 **/
public interface IBaseConvert<CreateReq extends Request, Res extends AbstractResponse, DO extends AbstractDO> {

    /**
     * {@link CreateReq} to {@link DO}
     */
    @Named(value = "addRequestToDo")
    DO toDO(CreateReq createRequest);

    /**
     * {@link DO} to {@link Res}
     */
    @Named(value = "DoToResponse")
    Res toResponse(DO dataBaseDO);

    /**
     * list转换 {@link DO} to {@link Res}
     */
    default List<Res> toResponse(List<DO> argDos) {
        if (CollectionUtils.isEmpty(argDos)) return Collections.emptyList();
        List<Res> list = new ArrayList<>(argDos.size());
        for (DO dataBaseDO : argDos) {
            list.add(toResponse(dataBaseDO));
        }
        return list;
    }

    /**
     * 分页转换 {@link DO} to {@link Res}
     */
    default PageResponse<Res> toPageResponse(IPage<DO> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<Res> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(toResponse(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }
}
