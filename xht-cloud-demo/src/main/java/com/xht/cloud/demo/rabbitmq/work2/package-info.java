/**
 * 描述 ：注意点：
 * 1. 生产者、消费者指定：channel.basicQos(1);
 * 2. 消费者消费完消息自动发送确认消息：channel.basicAck(envelope.getDeliveryTag(), false);
 * 3. 消费者必须关闭自动应答：autoAck = false;
 * 4. 一般消费者如果处理消息的时间较短(效率较高)，那么它处理的消息会比较多一些;
 *
 * @author : 小糊涂
 **/
package com.xht.cloud.demo.rabbitmq.work2;