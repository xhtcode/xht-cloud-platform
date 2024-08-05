package com.xht.cloud.framework.core.convert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述 ：自定义转换器
 * 
 * @param <Target> 源数据
 * @param <Source> 目标数据容器
 * @author 小糊涂
 **/
public interface IConvert<Target, Source> {

    /**
     * 转换
     *
     * @param target 源数据
     * @return 目标数据
     */
    Source convert(Target target);

    /**
     * 反转
     *
     * @param source 目标数据
     * @return 源数据
     */
    Target reversal(Source source);

    /**
     * 转换
     *
     * @param targetList 源数据
     * @return 目标数据
     */
    default List<Source> convert(List<Target> targetList) {
        if (targetList == null) {
            return null;
        }
        return targetList.stream().map(this::convert).collect(Collectors.toList());
    }

    /**
     * 反转
     *
     * @param sourceList 目标数据
     * @return 源数据
     */
    default List<Target> reversal(List<Source> sourceList) {
        if (sourceList == null) {
            return null;
        }
        return sourceList.stream().map(this::reversal).collect(Collectors.toList());
    }

}
