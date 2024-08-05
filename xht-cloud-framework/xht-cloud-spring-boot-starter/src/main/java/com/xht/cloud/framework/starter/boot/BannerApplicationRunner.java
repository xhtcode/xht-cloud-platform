package com.xht.cloud.framework.starter.boot;

import com.xht.cloud.framework.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 描述 ：项目启动后打印的内容
 *
 * @author 小糊涂
 **/
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    /**
     * 服务名称
     */
    private final String applicationName;
    private final ServerProperties serverProperties;

    public BannerApplicationRunner(String applicationName, ServerProperties serverProperties) {
        this.applicationName = applicationName;
        this.serverProperties = serverProperties;
    }

    @Override
    public void run(ApplicationArguments args) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Integer port = serverProperties.getPort();
                port = Objects.nonNull(port) ? port : 8080;
                String contextPath = serverProperties.getServlet().getContextPath();
                contextPath = StringUtils.hasText(contextPath) ? contextPath : "";
                log.info("""
                                                                
                                -------------------------------------------------------------------------
                                \t项目启动成功!
                                \t项目名称: \t{}\s\s
                                \t接口文档: \thttp://127.0.0.1:{}{}/doc.html\s
                                \t接口文档: \thttp://127.0.0.1:{}{}/swagger-ui/index.html\s
                                \t健康检查: \thttp://127.0.0.1:{}{}/health\s
                                \t开发文档: \thttps://xhtcode.github.io/xht-cloud-doc/\s
                                -------------------------------------------------------------------------""",
                        applicationName,
                        port, contextPath,
                        port, contextPath,
                        port, contextPath
                );
            } catch (Exception e) {
                throw new SysException(e.getMessage());
            }
        }).start();
    }
}
