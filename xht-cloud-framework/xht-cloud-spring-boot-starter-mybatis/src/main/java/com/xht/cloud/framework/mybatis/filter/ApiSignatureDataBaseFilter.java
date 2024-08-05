package com.xht.cloud.framework.mybatis.filter;

import com.xht.cloud.framework.starter.exception.ApiSignatureErrorStatusCode;
import com.xht.cloud.framework.starter.exception.ApiSignatureException;
import com.xht.cloud.framework.starter.signature.*;
import com.xht.cloud.framework.mybatis.tool.ApiSignatureCache;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：数据库过滤请求
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignatureDataBaseFilter implements ApiSignatureFilter {
    private final static String SQL = "select app_id , app_key from sys_api_signature where app_id = ?";
    private final ApiSignatureProperties apiSignatureProperties;
    private final JdbcTemplate jdbcTemplate;

    public ApiSignatureDataBaseFilter(ApiSignatureProperties apiSignatureProperties, JdbcTemplate jdbcTemplate) {
        this.apiSignatureProperties = apiSignatureProperties;
        this.jdbcTemplate = jdbcTemplate;
        log.debug(">>>>>>mybatis-plus-start 接口签名过滤器 查询数据库验签 <<<<<<");
    }

    /**
     * 过滤链执行器
     *
     * @param apiSignatureBuilder {@link ApiSignatureBuilder}
     * @param request             {@link HttpServletRequest}
     */
    @Override
    public void doFilter(ApiSignatureBuilder apiSignatureBuilder, HttpServletRequest request) {
        if (Objects.equals(ApiSignatureType.DATABASE, apiSignatureProperties.getType())) {
            ApiSignatureBO apiSignatureBO = ApiSignatureCache.get(apiSignatureBuilder.getAppId());
            if (Objects.isNull(apiSignatureBO)) {
                List<ApiSignatureBO> query = jdbcTemplate.query(SQL,
                        (rs, rowNum) -> ApiSignatureBO.builder().appId(rs.getString("app_id")).appKey(rs.getString("app_key")).build(),
                        apiSignatureBuilder.getAppId()
                );
                if (CollectionUtils.isEmpty(query)) {
                    throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_ID_INVALID);
                }
                apiSignatureBO = query.get(0);
                ApiSignatureCache.set(apiSignatureBO);
            }
            String appId = apiSignatureBO.getAppId();
            String appKey = apiSignatureBO.getAppKey();
            if (!Objects.equals(apiSignatureBuilder.getAppId(), appId)) {
                throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_ID_INVALID);
            }
            if (!Objects.equals(apiSignatureBuilder.getAppKey(), appKey)) {
                throw new ApiSignatureException(ApiSignatureErrorStatusCode.API_KEY_ERROR);
            }
        }
    }

    /**
     * 排序
     *
     * @return 数值越少越前 越大就越后
     */
    @Override
    public Integer getSort() {
        return MAX_SORT - 1;
    }
}
