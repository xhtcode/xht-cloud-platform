package com.xht.cloud.framework.web.xss.filter;

import com.xht.cloud.framework.web.xss.XSSProperties;
import com.xht.cloud.framework.web.xss.wrapper.XSSRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 描述 ：xss 防范
 *
 * @author 小糊涂
 **/
@Slf4j
public class XSSFilter extends OncePerRequestFilter {

    /**
     * 属性
     */
    private final XSSProperties properties;
    /**
     * 路径匹配器
     */
    private final PathMatcher pathMatcher;

    public XSSFilter(XSSProperties properties, PathMatcher pathMatcher) {
        this.properties = properties;
        this.pathMatcher = pathMatcher;
        log.debug(">>>>>>web-start xss 防范<<<<<<");
    }

    /**
     * @param request     {@link HttpServletRequest}
     * @param response    {@link HttpServletResponse}
     * @param filterChain {@link FilterChain}
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new XSSRequestWrapper(request), response);
    }

    /**
     * @param request {@link HttpServletRequest}
     * @return {@link Boolean}
     */
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        // 如果匹配到无需过滤，则过滤
        String uri = request.getRequestURI();
        return properties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, uri));
    }
}
