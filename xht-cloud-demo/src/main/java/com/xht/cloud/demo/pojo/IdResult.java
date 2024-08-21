package com.xht.cloud.demo.pojo;

import com.xht.cloud.demo.constant.IdStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdResult {
private long id;
private IdStatus status;
}
