package com.xht.cloud.generate.core.mananger;

import com.xht.cloud.framework.utils.support.StringUtils;
import com.xht.cloud.generate.module.type.domain.dataobject.GenColumnTypeDO;
import com.xht.cloud.generate.module.type.mapper.GenColumnTypeMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述 ：
 *
 * @author : 小糊涂
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ColumnTypeManager {

    private final GenColumnTypeMapper genColumnTypeMapper;

    private static final Map<String, String> JAVA_TYPE = new ConcurrentHashMap<>();

    private static final Map<String, String> TS_TYPE = new ConcurrentHashMap<>();

    /**
     * 获取java类型
     * @param columnCodeName 字段名称
     */
    public String getJavaType(String columnCodeName) {
        if (!StringUtils.hasText(columnCodeName)) return "String";
        String item = JAVA_TYPE.get(columnCodeName.toLowerCase());
        return StringUtils.hasText(item) ? item : "String";
    }

    /**
     * 获取ts类型
     * @param columnCodeName 字段名称
     */
    public String getTsType(String columnCodeName) {
        if (!StringUtils.hasText(columnCodeName)) return "any";
        String item = TS_TYPE.get(columnCodeName.toLowerCase());
        return StringUtils.hasText(item) ? item : "any";
    }

    @PostConstruct
    private void init() {
        List<GenColumnTypeDO> tsTypes = genColumnTypeMapper.selectList(GenColumnTypeDO::getLabel, "TS");
        List<GenColumnTypeDO> javaTypes = genColumnTypeMapper.selectList(GenColumnTypeDO::getLabel, "Java");
        for (GenColumnTypeDO typeDO : tsTypes) {
            TS_TYPE.put(typeDO.getDbValue().toLowerCase(), typeDO.getValue());
        }
        for (GenColumnTypeDO typeDO : javaTypes) {
            JAVA_TYPE.put(typeDO.getDbValue().toLowerCase(), typeDO.getValue());
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void flushInfo() {
        TS_TYPE.clear();
        JAVA_TYPE.clear();
        init();
        log.info("数据刷新成功");
    }
}
