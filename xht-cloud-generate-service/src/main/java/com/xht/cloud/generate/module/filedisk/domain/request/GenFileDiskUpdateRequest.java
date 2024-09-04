package com.xht.cloud.generate.module.filedisk.domain.request;

import com.xht.cloud.framework.domain.request.IUpdateRequestFun;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：文件管理
 *
 * @author : 小糊涂
 **/
@Data
@Schema(name = "GenFileDiskUpdateRequest", description = "文件管理")
public class GenFileDiskUpdateRequest extends GenFileDiskCreateRequest implements IUpdateRequestFun<String> {

    /**
     * id
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 获取主键
     */
    @Override
    public String getPkId() {
        return this.id;
    }
}
