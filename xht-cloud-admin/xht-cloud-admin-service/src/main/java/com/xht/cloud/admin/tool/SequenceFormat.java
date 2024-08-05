package com.xht.cloud.admin.tool;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.xht.cloud.admin.enums.SeqLoopEnum;
import com.xht.cloud.admin.exceptions.SequenceException;
import com.xht.cloud.admin.module.sequence.domain.dataobject.SysSequenceDO;
import com.xht.cloud.framework.exception.Assert;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述 ：序列格式化
 *
 * @author 小糊涂
 **/
public class SequenceFormat {
    private final static Pattern DATA_FORMAT = Pattern.compile("\\{(yyyy|MM|dd|yyyyMM|yyyydd|yyyyMMdd|MMdd)\\}", Pattern.CASE_INSENSITIVE);

    /**
     * 格式化
     *
     * @param sysSequenceDO 序列信息
     * @return 格式化后的字符串
     */
    public static String format(SysSequenceDO sysSequenceDO) {
        Assert.notNull(sysSequenceDO, "序列查询不到");
        Integer currentValue = sysSequenceDO.getCurrentValue();
        Integer stepValue = sysSequenceDO.getStepValue();
        Integer maxValue = sysSequenceDO.getMaxValue();
        Integer minValue = sysSequenceDO.getMinValue();
        SeqLoopEnum seqLoop = sysSequenceDO.getSeqLoop();
        String seqFormat = sysSequenceDO.getSeqFormat();
        Assert.isTrue(currentValue > maxValue && Objects.equals(SeqLoopEnum.NO, seqLoop), () -> new SequenceException("当前值大于该序列最大值"));
        if (currentValue > maxValue) {
            currentValue = minValue;
        }
        sysSequenceDO.setCurrentValue(currentValue + stepValue);
        Assert.isTrue(currentValue < minValue, "当前值小于该序列最小值");
        String[] arr = seqFormat.split("[{}]");
        Date date = new Date();
        StringBuilder builder = new StringBuilder();
        for (String item : arr) {
            if (Pattern.compile(".*(yyyy|MM|dd|yyyyMM|yyyydd|MMdd|yyyyMMdd|yyyy-MM-dd).*").matcher(item).matches()) {
                Matcher matcher = DATA_FORMAT.matcher(String.format("{%s}", item));
                builder.append(matcher.replaceAll(DateUtil.format(date, item)));
            } else if (Pattern.compile("(^N\\d+|^N)").matcher(item).matches()) {
                String[] split = item.split("N");
                if (ArrayUtil.isNotEmpty(item) && split.length > 1) {
                    Integer bit = Convert.toInt(split[1], 0);
                    if (bit > 0) {
                        builder.append(String.format("%0" + bit + "d", currentValue));
                    } else {
                        builder.append(currentValue);
                    }
                } else {
                    builder.append(currentValue);
                }
            } else {
                builder.append(item);
            }
        }
        return builder.toString();
    }


}
