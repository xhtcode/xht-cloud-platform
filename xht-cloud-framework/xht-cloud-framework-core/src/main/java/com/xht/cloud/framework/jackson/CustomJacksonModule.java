package com.xht.cloud.framework.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.deser.key.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;

import java.time.*;

/**
 * 描述 ：序列化规则
 *
 * @author 小糊涂
 * @see com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 **/
public class CustomJacksonModule extends SimpleModule {

    public CustomJacksonModule() {
        super(PackageVersion.VERSION);
        // ======================= 序列化规则 ===============================
        addSerializer(Duration.class, DurationSerializer.INSTANCE);
        addSerializer(Instant.class, InstantSerializer.INSTANCE);
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER)); // yyyy-MM-dd HH:mm:ss
        addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER)); // yyyy-MM-dd
        addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));     // HH:mm:ss
        addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        addSerializer(Period.class, new ToStringSerializer(Period.class));
        addSerializer(Year.class, YearSerializer.INSTANCE);
        addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);
        addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        addSerializer(ZoneId.class, new ZoneIdSerializer());
        addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));
//        addSerializer(Long.class, ToStringSerializer.instance);
//        addSerializer(Long.TYPE, ToStringSerializer.instance);
        addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);

        // ======================= 反序列化规则 ==============================
        addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));  // yyyy-MM-dd HH:mm:ss
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));     // yyyy-MM-dd
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));  // HH:mm:ss
        addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        addDeserializer(Year.class, YearDeserializer.INSTANCE);
        addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
        addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
//        addDeserializer(SuperAdminRoleEnums.class, SuperAdminRoleEnumsDeserializer.INSTANCE);
//        addDeserializer(SuperAdminUserEnums.class, SuperAdminUserEnumsDeserializer.INSTANCE);
//        addDeserializer(SuperHashChildEnums.class, SuperHashChildEnumDeserializer.INSTANCE);
//        addDeserializer(UserStatusEnums.class, UserStatusEnumsDeserializer.INSTANCE);
//        addDeserializer(UserTypeEnums.class, UserTypeEnumsDeserializer.INSTANCE);


        // ======================= key序列化规则 ==============================
        addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        addKeyDeserializer(YearMonth.class, YearMonthKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);
    }
}
