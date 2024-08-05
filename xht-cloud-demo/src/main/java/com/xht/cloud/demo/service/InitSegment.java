package com.xht.cloud.demo.service;

import com.xht.cloud.demo.mapper.IDAllocMapper;
import com.xht.cloud.demo.pojo.dataobject.LeafAlloc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class InitSegment {

    private final IDAllocMapper idAllocMapper;

    /**
     * 初始化号段
     */
    @Transactional(rollbackFor = Exception.class)
    public void init(String segmentKey, Segment current) {
        LeafAlloc leafAlloc;
        idAllocMapper.updateMaxId(segmentKey);
        leafAlloc = idAllocMapper.getLeafAlloc(segmentKey);
        current.getValue().set(leafAlloc.getMaxId() - leafAlloc.getStep());
        current.setMax(leafAlloc.getMaxId());
        current.setStep(leafAlloc.getStep());

        log.info("号段初始化成功!");
    }
}
