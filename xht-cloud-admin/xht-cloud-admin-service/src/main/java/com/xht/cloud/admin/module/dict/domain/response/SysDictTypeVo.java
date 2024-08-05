package com.xht.cloud.admin.module.dict.domain.response;

import lombok.Data;

import java.util.List;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
public class SysDictTypeVo extends SysDictTypeResponse {

    private List<SysDictItemResponse> children;

}
