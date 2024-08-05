package com.xht.cloud.framework.mybatis.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xht.cloud.framework.core.convert.IConvert;
import com.xht.cloud.framework.core.domain.response.PageResponse;
import com.xht.cloud.framework.mybatis.tool.PageTool;

import java.util.Objects;

/**
 * 描述 ：分页转换
 *
 * @param <Target> 源数据
 * @param <Source> 目标数据容器
 * @author 小糊涂
 **/
public interface PageConvert<Target, Source> extends IConvert<Target, Source> {

    /**
     * 分页转换
     */
    default PageResponse<Target> reversal(IPage<Source> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<Target> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(reversal(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }

    /**
     * 分页转换
     */
    default PageResponse<Source> convert(IPage<Target> iPage) {
        if (Objects.nonNull(iPage)) {
            PageResponse<Source> pageResponse = PageTool.cloneEmpty(iPage);
            pageResponse.setList(convert(iPage.getRecords()));
            return pageResponse;
        }
        return PageTool.empty();
    }
}
