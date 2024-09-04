package com.xht.cloud.demo;

import com.xht.cloud.demo.pojo.vo.PersonalInfoVo;
import com.xht.cloud.framework.jackson.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        PersonalInfoVo build = PersonalInfoVo.builder()
                .name("小糊涂")
                .phone("13112312312")
                .fixedPhone("021- 65656539")
                .email("123456789@qq.com")
                .idCard("123456789012345678")
                .address("四川省成都市郫都区百草路一号")
                .password("1234567890")
                .bankCard("62220210001234567890")
                .build();
        System.out.println(JsonUtils.toJsonString(build));
    }

}
