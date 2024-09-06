package com.xht.cloud.framework.jackson.translation.factory;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.jackson.translation.enums.TransDictEnum;
import com.xht.cloud.framework.jackson.translation.strategy.AbstractTransDictStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：翻译策略工厂
 *
 * @author : 小糊涂
 **/
public final class TransDictFactory {

    /**
     * 策略存储器
     */
    private static final Map<TransDictEnum, AbstractTransDictStrategy> TRANS_STRATEGY_MAP = new HashMap<>();

    private static class InnerClass {
        private static final TransDictFactory INSTANCE = new TransDictFactory();
    }

    /**
     * 获取工厂类
     *
     * @return {@link TransDictFactory}
     */
    public static TransDictFactory getInstance() {
        return TransDictFactory.InnerClass.INSTANCE;
    }

    /**
     * 初始化序列化 转换器
     */
    private TransDictFactory() {
    }

    /**
     * 注册策略执行器
     *
     * @param transDictEnum {@link TransDictEnum} 翻译类型
     * @param strategy      策略执行器
     */
    public void register(TransDictEnum transDictEnum, AbstractTransDictStrategy strategy) {
        Assert.notNull(transDictEnum, "翻译类型获取失败");
        TRANS_STRATEGY_MAP.put(transDictEnum, strategy);
    }

    /**
     * 获取一个非空的策略执行器
     *
     * @param transDictEnum {@link TransDictEnum} 类型
     * @return 策略执行器
     */
    public AbstractTransDictStrategy getStrategy(TransDictEnum transDictEnum) {
        return TRANS_STRATEGY_MAP.get(transDictEnum);
    }
}
