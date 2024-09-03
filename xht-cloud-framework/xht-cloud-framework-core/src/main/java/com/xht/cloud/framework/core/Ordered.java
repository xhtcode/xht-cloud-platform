package com.xht.cloud.framework.core;

/**
 * 描述 ：Ordered是一个接口，可以由应该排序的对象实现，例如在Collection中。
 *
 * @author : 小糊涂
 **/
public interface Ordered {

    /**
     * 最高优先级值的有用常数。
     *
     * @see java.lang.Integer#MIN_VALUE
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 最低优先级值的有用常数。
     *
     * @see java.lang.Integer#MAX_VALUE
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;


    /**
     * 获取该对象的顺序值。
     * <p>值越高优先级越低。因此，
     * 具有最低值的对象具有最高优先级(在某种程度上)
     * 类似于Servlet {@code load-on-startup}值)。
     * <p>相同的顺序值将导致任意的排序位置
     * 受影响的对象。
     * ＊
     *
     * @return 顺序值
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    int getOrder();
}
