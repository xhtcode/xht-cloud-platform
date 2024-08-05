package com.xht.cloud.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.xht.cloud.demo.mapper.IDAllocMapper;
import com.xht.cloud.demo.pojo.IdResult;
import com.xht.cloud.demo.pojo.MessageVo;
import com.xht.cloud.demo.pojo.vo.PersonalInfoVo;
import com.xht.cloud.demo.service.SegmentService;
import com.xht.cloud.demo.websocket.GlobalWebsocket;
import com.xht.cloud.demo.websocket.WebSocket;
import com.xht.cloud.framework.core.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述 ：测试
 *
 * @author 小糊涂
 **/
@Tag(name = "测试")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final GlobalWebsocket globalWebsocket;
    private final WebSocket webSocket;
    private final IDAllocMapper mapper;

    @GetMapping("/index")
    public R<String> websocket() {
        String uuid = IdUtil.simpleUUID();
        globalWebsocket.sendMessage(new MessageVo(uuid));
        return R.ok(uuid);
    }

    @GetMapping("/index2")
    public R<String> websocket2() {
        String uuid = IdUtil.simpleUUID();
        webSocket.sendAllMessage(uuid);
        webSocket.sendOneMessage("1", uuid);
        return R.ok(uuid);
    }

    /**
     * 脱敏测试接口
     */
    @Operation(summary = "脱敏测试接口")
    @GetMapping
    public R<PersonalInfoVo> desensitization() {
        List<String> allTags = mapper.getAllTags();
        System.out.println(allTags.size());
        allTags.forEach(System.out::println);
        return R.ok(PersonalInfoVo.builder()
                .name("小糊涂")
                .phone("13112312312")
                .fixedPhone("021- 65656539")
                .email("123456789@qq.com")
                .idCard("123456789012345678")
                .address("四川省成都市郫都区百草路一号")
                .password("1234567890")
                .bankCard("62220210001234567890")
                .build());
    }

    private final SegmentService segmentService;

    @GetMapping("/id")
    public R<IdResult> id(@RequestParam(value = "key", required = false, defaultValue = "test")String key) {
        return R.ok(segmentService.getId(key));
    }
}
