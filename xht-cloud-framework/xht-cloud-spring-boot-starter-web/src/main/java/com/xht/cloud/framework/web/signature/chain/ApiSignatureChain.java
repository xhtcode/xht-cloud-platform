package com.xht.cloud.framework.web.signature.chain;

import com.xht.cloud.framework.starter.signature.ApiSignatureBuilder;
import com.xht.cloud.framework.starter.signature.ApiSignatureFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述 ：api 签名 过滤filter 执行器
 *
 * @author 小糊涂
 **/
@Slf4j
public class ApiSignatureChain implements ApplicationContextAware {
    private static List<ApiSignatureFilter> prepareFilterList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ApiSignatureFilter> serviceMap = applicationContext.getBeansOfType(ApiSignatureFilter.class);
        Assert.notEmpty(serviceMap, "查找不到执行过滤器!");
        prepareFilterList = new ArrayList<>(serviceMap.values());
        prepareFilterList = prepareFilterList.stream().sorted(Comparator.comparing(ApiSignatureFilter::getSort)).collect(Collectors.toList());
    }

    public void doFilter(ApiSignatureBuilder signatureDTO, HttpServletRequest request) {
        if (!CollectionUtils.isEmpty(prepareFilterList)) {
            for (ApiSignatureFilter apiSignatureFilter : prepareFilterList) {
                apiSignatureFilter.doFilter(signatureDTO, request);
            }
        }
    }

}
