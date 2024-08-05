package com.xht.cloud.demo.listener;

import com.xht.cloud.demo.event.TestEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Component
public class Test1Listener implements ApplicationListener<TestEvent> {

    @Override
    public void onApplicationEvent(TestEvent event) {
        System.out.println("Test1Listener:"+event.getName());
    }
}
