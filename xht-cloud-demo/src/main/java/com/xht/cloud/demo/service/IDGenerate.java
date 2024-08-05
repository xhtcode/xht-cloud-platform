package com.xht.cloud.demo.service;

import com.xht.cloud.demo.pojo.IdResult;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public interface IDGenerate {
    IdResult get(String key);

    void init();
}
