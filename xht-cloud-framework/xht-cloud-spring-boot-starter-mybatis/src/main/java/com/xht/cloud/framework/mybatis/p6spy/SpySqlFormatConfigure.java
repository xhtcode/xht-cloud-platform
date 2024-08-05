package com.xht.cloud.framework.mybatis.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述 ：打印sql日志信息配置类
 *
 * @author 小糊涂
 **/
@Slf4j
public class SpySqlFormatConfigure implements MessageFormattingStrategy {

    /**
     * <p>输出执行sql信息</p >
     *
     * @param now      执行时间
     * @param elapsed  耗时多少毫秒
     * @param prepared 准备执行的sql脚本
     * @param sql      执行的sql脚本
     * @param url      数据源连接地址
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        System.err.println("==========================================  Sql Start  ==========================================\n");
        System.err.println("线程名称:\t" + Thread.currentThread().getName());
        System.err.println("执行时间:\t" + now);
        System.err.println("执行耗时:\t" + elapsed + " 毫秒");
        return sql;
    }
}
