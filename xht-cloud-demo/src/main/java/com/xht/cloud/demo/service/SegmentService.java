package com.xht.cloud.demo.service;

import com.xht.cloud.demo.pojo.IdResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述 ： 号段模式
 *
 * @author 小糊涂
 **/
@Service
@Slf4j
public class SegmentService {

    private final IDGenerate idGenerate;

    public SegmentService(IDGenerate idGenerate) {
        this.idGenerate = idGenerate;
     //   idGenerate.init();
    }

    public IdResult getId(String key) {
        log.info("***************************");
        return idGenerate.get(key);
    }
}
