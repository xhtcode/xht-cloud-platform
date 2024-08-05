package com.xht.cloud.framework.mybatis.p6spy;

import com.p6spy.engine.spy.appender.StdoutLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 描述 ：sql打印
 *
 * @author 小糊涂
 **/
@Slf4j
public class SpyStdoutLogger extends StdoutLogger {
    @Override
    public void logText(String sql) {
        if (!StringUtils.hasText(sql)) return;
        System.err.println("执行结果:\t" + sql);
        System.err.println("\n==========================================  Sql  End   ==========================================");
    }
}
