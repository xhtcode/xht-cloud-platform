package com.xht.cloud.demo.pojo.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "leaf_alloc")
public class LeafAlloc {

    @TableId(value = "biz_tag")
    private String key;
    @TableField(value = "max_id")
    private long maxId;
    @TableField(value = "step")
    private int step;
    @TableField(value = "update_time")
    private String updateTime;

}
