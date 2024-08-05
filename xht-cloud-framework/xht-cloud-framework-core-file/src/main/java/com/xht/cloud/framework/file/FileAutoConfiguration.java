package com.xht.cloud.framework.file;

import com.xht.cloud.framework.file.core.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 描述 ：文件自动注入类
 *
 * @author 小糊涂
 **/
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
public class FileAutoConfiguration {
}
