package com.xht.cloud.framework.core.convert;

/**
 * 描述 ：消费转换
 *
 * @author 小糊涂
 **/
@FunctionalInterface
public interface ConsumerConvert<Target, Source> {
    Source convert(Target target);
}
