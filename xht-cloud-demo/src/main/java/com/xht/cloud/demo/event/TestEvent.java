package com.xht.cloud.demo.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Getter
public class TestEvent extends ApplicationEvent {
private final String name;

    public TestEvent(String source) {
        super(source);
        this.name = source;
    }
}
