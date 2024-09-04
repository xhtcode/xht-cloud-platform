package com.xht.cloud.admin.module.org.exception;

import cn.hutool.core.util.StrUtil;
import com.xht.cloud.framework.exception.BizException;

/**
 * 描述 ：组织机构异常
 *
 * @author : 小糊涂
 **/
public class SysOrgException extends BizException {


    public SysOrgException(String msg, Object... arguments) {
        super(StrUtil.format(msg, arguments));
    }
}
