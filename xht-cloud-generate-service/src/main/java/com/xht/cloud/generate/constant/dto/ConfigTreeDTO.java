package com.xht.cloud.generate.constant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeTemplateDO;
import lombok.*;

import java.util.List;

/**
 * 描述 ：树
 *
 * @author 小糊涂
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigTreeDTO {

    private String id;

    private String label;

    /**
     *  0 文件夹 1 文件
     */
    private String fileType;

    private String templateId;

    private List<ConfigTreeDTO> children;

    @JsonIgnore
    private GenCodeTemplateDO genCodeTemplateDO;

    @Override
    public String toString() {
        return "ConfigTreeDTO{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", fileType='" + fileType + '\'' +
                ", templateId='" + templateId + '\'' +
                '}';
    }
}
