package com.xht.cloud.demo.service;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/

public class SegmentBuffer {

    /**
     * 初始化状态
     */
    @Getter
    @Setter
    private boolean initStatus;

    /**
     * 号段key
     */
    @Getter
    @Setter
    private String segmentKey;

    private final Segment[] segments;

    private int currentSegmentIndex;

    /**
     * 下一个segment是否处于可切换状态
     */
    @Getter
    @Setter
    private boolean nextReady;


    public SegmentBuffer() {
        this.initStatus = false;
        this.nextReady = false;
        this.currentSegmentIndex = 0;
        this.segments = new Segment[]{new Segment(), new Segment()};
    }

    public Segment getCurrentSegment() {
        return this.segments[currentSegmentIndex];
    }

    public int nextSegmentIndex() {
        return (this.currentSegmentIndex + 1) % 2;
    }

    public Segment getNextSegment() {
        return this.segments[this.nextSegmentIndex()];
    }

    public void switchSegment() {
        this.currentSegmentIndex = nextSegmentIndex();
    }
}
