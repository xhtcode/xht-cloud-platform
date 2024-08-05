package com.xht.cloud.demo.service;

import com.xht.cloud.demo.pojo.IdResult;
import org.springframework.stereotype.Service;

import static com.xht.cloud.demo.constant.IdStatus.SUCCESS;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class ZeroIDGenerate implements IDGenerate {
    @Override
    public IdResult get(String key) {
        return new IdResult(0, SUCCESS);
    }

    @Override
    public void init() {

    }
}
