package com.xht.cloud.framework.mongodb.document;

import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * 描述 ： id 文档基类
 *
 * @author 小糊涂
 **/
@Data
public class IdDoc extends AbstractDoc {

    /**
     * 主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;//主键

}
