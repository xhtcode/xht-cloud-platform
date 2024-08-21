package com.xht.cloud.demo.service;

import com.xht.cloud.demo.constant.IdStatus;
import com.xht.cloud.demo.mapper.IDAllocMapper;
import com.xht.cloud.demo.pojo.IdResult;
import com.xht.cloud.demo.pojo.dataobject.LeafAlloc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SegmentIDGenerate implements IDGenerate {private final IDAllocMapper idAllocMapper;private final InitSegment initSegment;
private final Map<String, SegmentBuffer> cache = new ConcurrentHashMap<>();
    /**
     * 是否已经进行初始化
     */private volatile boolean initOK = false;

    @Override
    public void init() {
        List<String> dbTags = idAllocMapper.getAllTags();
        for (String tag : dbTags) {
            SegmentBuffer buffer = new SegmentBuffer();
            buffer.setSegmentKey(tag);
            Segment segment = buffer.getCurrentSegment();
            segment.setValue(new AtomicLong(0));
            segment.setMax(0);
            segment.setStep(0);
            cache.put(tag, buffer);
        }
        this.initOK = true;
        log.info("号段初始化成功");
    }


    @Override
    public IdResult get(String segmentKey) {
        while (true) {
            SegmentBuffer buffer = cache.get(segmentKey);
            if (Objects.isNull(buffer)) {
                LeafAlloc leafAlloc = idAllocMapper.getLeafAlloc(segmentKey);
                if (Objects.isNull(leafAlloc)) {
                    throw new RuntimeException("查询不到对应的号段!");
                }
                buffer = new SegmentBuffer();
                buffer.setSegmentKey(segmentKey);
                Segment segment = buffer.getCurrentSegment();
                segment.setValue(new AtomicLong(0));
                segment.setMax(0);
                segment.setStep(0);
                cache.put(segmentKey, buffer);
            }
            Segment segment = buffer.getCurrentSegment();
            if (!buffer.isInitStatus()) {
                initSegment.init(segmentKey, buffer.getCurrentSegment());
                buffer.setInitStatus(true);
            }
            if (!buffer.isNextReady() && segment.isThresholdValue()) {
                initSegment.init(segmentKey, buffer.getNextSegment());
                buffer.setNextReady(true);
            }
            Long currentValue = segment.getCurrentValue();
            if (Objects.nonNull(currentValue)) {
                return new IdResult(currentValue, IdStatus.SUCCESS);
            }
            if (buffer.isNextReady()) {
                buffer.switchSegment();
                buffer.setNextReady(false);
            }
        }
    }

}
