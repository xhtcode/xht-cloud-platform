package com.xht.cloud.framework.jackson.desensitization.factory;

import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.jackson.desensitization.convert.ISensitiveFieldConvert;
import com.xht.cloud.framework.utils.spi.SpiUtils;
import com.xht.cloud.framework.utils.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 描述 ：转换工厂
 *
 * @author : 小糊涂
 **/
public final class SensitiveFieldConvertFactory {

    /**
     * 策略存储器
     */
    private static final Map<String, ISensitiveFieldConvert> DESENSITIZATION_CONVERT_MAP = new HashMap<>();

    private static class InnerClass {
        private static final SensitiveFieldConvertFactory INSTANCE = new SensitiveFieldConvertFactory();
    }

    /**
     * 获取工厂类
     *
     * @return {@link SensitiveFieldConvertFactory}
     */
    public static SensitiveFieldConvertFactory getInstance() {
        return InnerClass.INSTANCE;
    }

    /**
     * 初始化序列化 转换器
     */
    private SensitiveFieldConvertFactory() {
        Iterator<ISensitiveFieldConvert> beans = SpiUtils.getBeans(ISensitiveFieldConvert.class);
        while (beans.hasNext()) {
            ISensitiveFieldConvert next = beans.next();
            String support = next.support();
            if (StringUtils.isEmpty(support) || DESENSITIZATION_CONVERT_MAP.containsKey(support)) {
                throw new SysException();
            }
            DESENSITIZATION_CONVERT_MAP.put(support, next);
        }
    }

    /**
     * 获取一个非空的策略执行器
     *
     * @param desensitization {@link ISensitiveFieldConvert} 类型
     * @return 策略执行器
     */
    public ISensitiveFieldConvert getStrategy(String desensitization) {
        return DESENSITIZATION_CONVERT_MAP.get(desensitization);
    }
}
