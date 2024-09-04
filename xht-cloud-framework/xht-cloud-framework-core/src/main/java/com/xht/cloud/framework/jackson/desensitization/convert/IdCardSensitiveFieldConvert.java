package com.xht.cloud.framework.jackson.desensitization.convert;

import cn.hutool.core.util.DesensitizedUtil;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.framework.jackson.desensitization.constant.SensitiveFieldConstant;

/**
 * 描述 ：身份证号脱敏策略
 *
 * @author 小糊涂
 **/
public class IdCardSensitiveFieldConvert implements ISensitiveFieldConvert {

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

    /**
     * 判断策略是否执行
     *
     * @return 策略类型
     */
    @Override
    public String support() {
        return SensitiveFieldConstant.ID_CARD;
    }
}
