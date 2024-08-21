package com.xht.cloud.demo.service;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
public class Segment {
private AtomicLong value = new AtomicLong(0);

    /**
     * 最大值
     */private volatile long max;

    /**
     * 步值
     */private volatile long step;


    public Segment() {
    }

    public Long getCurrentValue() {
        long currentValue = value.getAndIncrement();
        if (currentValue < this.max) {
            return currentValue;
        }
        return null;
    }

    public boolean isThresholdValue() {
        return this.max - value.get() < 0.9 * this.step;
    }
}
