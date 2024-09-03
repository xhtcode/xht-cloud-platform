package com.xht.cloud.framework.utils.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 描述 ：Java SPI
 *
 * @author : 小糊涂
 **/
public final class SpiUtils {

    /**
     * 获取一个对象
     */
    public static <S> Iterator<S> getBeans(Class<S> service) {
        ServiceLoader<S> serviceLoader = ServiceLoader.load(service);
        return serviceLoader.iterator();
    }
}
