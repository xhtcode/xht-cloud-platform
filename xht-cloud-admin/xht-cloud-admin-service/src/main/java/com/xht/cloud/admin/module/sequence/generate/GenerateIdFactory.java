package com.xht.cloud.admin.module.sequence.generate;

import com.xht.cloud.admin.enums.GenerateIdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
public class GenerateIdFactory {

    private static final Map<Integer, GenerateIdHandler> handlers = new HashMap<>();


    public static void register(@NonNull GenerateIdType generateIdType, GenerateIdHandler handler) {
        handlers.put(generateIdType.getValue(), handler);
    }


    public static Optional<GenerateIdHandler> getHandler(@NonNull GenerateIdType generateIdType) {
        return Optional.ofNullable(handlers.get(generateIdType.getValue()));
    }

}
