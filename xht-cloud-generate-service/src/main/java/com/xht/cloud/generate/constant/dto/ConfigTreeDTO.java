package com.xht.cloud.generate.constant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xht.cloud.generate.module.template.domain.dataobject.GenCodeTemplateDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 描述 ：树
 *
 * @author 小糊涂
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigTreeDTO implements Serializable {

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

}
