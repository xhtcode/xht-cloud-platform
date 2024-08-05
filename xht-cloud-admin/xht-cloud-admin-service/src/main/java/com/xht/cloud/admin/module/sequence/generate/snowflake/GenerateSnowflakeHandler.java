package com.xht.cloud.admin.module.sequence.generate.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.xht.cloud.admin.module.sequence.domain.request.IdRequest;
import com.xht.cloud.admin.enums.GenerateIdType;
import com.xht.cloud.admin.module.sequence.generate.GenerateIdHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述 ：生成雪花算法
 *
 * @author 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateSnowflakeHandler extends GenerateIdHandler {

    private static final Snowflake snowflake = IdUtil.getSnowflake();

    /**
     * 生成id
     *
     * @return id
     */
    @Override
    public String generate(IdRequest request) {
        return snowflake.nextIdStr();
    }

    /**
     * 生成id
     *
     * @return {@link GenerateIdType}
     */
    @Override
    protected GenerateIdType getType() {
        return GenerateIdType.SNOWFLAKE;
    }
}
