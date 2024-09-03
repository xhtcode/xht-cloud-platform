/**
 * 描述 ：Java SPI 的实现原理基于 Java 类加载机制和反射机制。
 * <p>
 * 当使用 ServiceLoader.load(Class<T> service) 方法加载服务时，会检查 META-INF/services 目录下是否存在以接口全限定名命名的文件。如果存在，则读取文件内容，获取实现该接口的类的全限定名，并通过 Class.forName() 方法加载对应的类。
 * <p>
 * 在加载类之后，ServiceLoader 会通过反射机制创建对应类的实例，并将其缓存起来。
 * <p>
 * 这里涉及到一个「懒加载迭代器」的思想：
 * <p>
 * 当我们调用 ServiceLoader.load(Class<T> service) 方法时，并不会立即将所有实现了该接口的类都加载进来，而是返回一个懒加载迭代器。
 * <p>
 * 「只有在使用迭代器遍历时，才会按需加载对应的类并创建其实例。」
 * <p>
 * 这种懒加载思想有以下两个好处：
 * <p>
 * 节省内存如果一次性将所有实现类全部加载进来，可能会导致内存占用过大，影响程序的性能。
 * <p>
 * 增强灵活性由于 ServiceLoader 是动态加载的，因此可以在程序运行时添加或删除实现类，而无需修改代码或重新编译。
 * <p>
 * 总的来说，Java SPI 的实现原理比较简单，利用了 Java 类加载和反射机制，提供了一种轻量级的插件化机制，可以很方便地扩展功能。
 *
 * @author : 小糊涂
 **/
package com.xht.cloud.framework.utils.spi;