package com.xht.cloud.generate.support;

import com.xht.cloud.framework.exception.Assert;
import com.xht.cloud.framework.utils.StringUtils;
import com.xht.cloud.generate.exception.GenerateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述 ：
 * <pre> </pre>
 *
 * @author 小糊涂
 * @version : 1.0
 **/
@Component
@RequiredArgsConstructor
public class DataBaseQueryFactory {

    private final List<IDataBaseQuery> dataBaseQueries;

    public IDataBaseQuery getStrategy(String dbType) {
        Assert.isTrue(!StringUtils.hasText(dbType), "dbType is empty.");
        return dataBaseQueries.stream().parallel().filter(item -> item.support(dbType)).findFirst().orElseThrow(
                () -> new GenerateException("找不到所选择的数据源处理Handler dbType:" + dbType + " 不匹配"));
    }

}
