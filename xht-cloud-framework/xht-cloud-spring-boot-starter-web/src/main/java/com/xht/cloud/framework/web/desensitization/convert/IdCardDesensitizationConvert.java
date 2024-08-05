package com.xht.cloud.framework.web.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.utils.support.StringUtils;

/**
 * 描述 ：身份证号脱敏策略
 *
 * @author 小糊涂
 **/
public class IdCardDesensitizationConvert implements DesensitizationConvert {

    /**
     * 身份证号脱敏策略，
     *
     * @param source 元数据
     * @return 脱敏后的数据
     */
    @Override
    public String desensitization(String source) {
        if (!StringUtils.hasText(source)) return source;
        if (source.length() == 15 || source.length() == 18) {
            source = DesensitizedUtil.idCardNum(source, 6, 3);
        }
        return source;
    }
}
