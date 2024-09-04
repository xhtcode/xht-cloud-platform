package com.xht.cloud.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述 ：Key Value 的键值对
 *
 * @author 小糊涂
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue<K, V> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    private K key;

    /**
     * 值
     */
    private V value;

}
