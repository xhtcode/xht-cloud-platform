package com.xht.cloud.admin.module.area.domain.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.cloud.framework.mybatis.dataobject.BaseDO;
import lombok.Data;

/**
 * 描述 ：地区信息
 *
 * @author 小糊涂
 **/
@Data
@TableName(value = "sys_area_info")
public class SysAreaInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 父级区划代码
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 级别1-5,省市县镇村1级：省、直辖市、自治区2级：地级市3级：市辖区、县（旗）、县级市、自治县（自治旗）、特区、林区4级：镇、乡、民族乡、县辖区、街道5级：村、居委会
     */
    @TableField(value = "level")
    private String level;

    /**
     * 区划代码
     */
    @TableField(value = "area_no")
    private String areaNo;

    /**
     * 城乡分类111表示主城区；112表示城乡接合区；121表示镇中心区；122表示镇乡接合区；123表示特殊区域；210表示乡中心区；220表示村庄
     */
    @TableField(value = "category")
    private String category;

    /**
     * 描述
     */
    @TableField(value = "msg")
    private String msg;

    /**
     * 此字段数据冗余字段
     */
    @TableField(exist = false, value = "leaf")
    private String leaf;

}
