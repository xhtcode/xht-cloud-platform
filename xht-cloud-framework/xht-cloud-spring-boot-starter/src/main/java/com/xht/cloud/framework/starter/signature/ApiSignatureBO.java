package com.xht.cloud.framework.starter.signature;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiSignatureBO {

    /**
     * 应用的唯一标识
     */
    private final String appId;

    /**
     * 应用key系统分配
     */
    private final String appKey;
}
