package com.xht.cloud.framework.jackson.translation.domain;

import com.xht.cloud.framework.domain.dto.DTO;
import lombok.Data;

/**
 * 描述 ：额外的字典信息
 *
 * @author : 小糊涂
 **/
@Data
public class DictExtraDTO extends DTO {

    /**
     * 字典值
     */
    private String filedValue;


}
