package com.xht.cloud.framework.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xht.cloud.framework.core.developer.SuperSystemStatus;
import com.xht.cloud.framework.mybatis.enums.DelFlagEnum;
import com.xht.cloud.framework.security.utils.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

/**
 * 描述 ： mybatis 自动配置
 *
 * @author 小糊涂
 **/
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class MybatisAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = properties.getGlobalConfig();
            globalConfig.setBanner(false);
        };
    }



    /**
     * 设置自动填充
     */
    @Bean
    @ConditionalOnClass(value = SecurityContextUtil.class)
    public MetaObjectHandler myMetaObjectHandlerSecurity() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
                this.strictInsertFill(metaObject, "createBy", SecurityContextUtil::getUserAccount, String.class);
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
                this.strictInsertFill(metaObject, "updateBy", SecurityContextUtil::getUserAccount, String.class);
                this.strictInsertFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
                this.strictInsertFill(metaObject, "system_status", () -> SuperSystemStatus.NO, SuperSystemStatus.class);
            }


            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
                this.strictUpdateFill(metaObject, "updateBy", SecurityContextUtil::getUserAccount, String.class);
                this.strictUpdateFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
            }
        };
    }

    @Bean
    @ConditionalOnMissingClass(value = "com.xht.cloud.framework.security.utils.SecurityContextUtil")
    public MetaObjectHandler myMetaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
                this.strictInsertFill(metaObject, "createBy", () -> "anonymity", String.class);
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
                this.strictInsertFill(metaObject, "updateBy", () -> "anonymity", String.class);
                this.strictInsertFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
                this.strictInsertFill(metaObject, "system_status", () -> SuperSystemStatus.NO, SuperSystemStatus.class);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
                this.strictUpdateFill(metaObject, "updateBy", () -> "anonymity", String.class);
                this.strictUpdateFill(metaObject, "delFlag", () -> DelFlagEnum.NORMAL, DelFlagEnum.class);
            }
        };
    }


}

