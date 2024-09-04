package com.xht.cloud.framework.web;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.xht.cloud.framework.constant.StringConstant;
import com.xht.cloud.framework.exception.SysException;
import com.xht.cloud.framework.utils.ResourceUtils;
import com.xht.cloud.framework.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 描述 ：ip工具类
 *
 * @author 小糊涂
 **/
@Slf4j
@SuppressWarnings("unused")
public final class IPUtils {

    /**
     * 未解析的位置.
     */
    private static final String EMPTY_ADDR = "0";

    /**
     * 本地的位置.
     */
    private static final String LOCAL_ADDR = "内网";

    /**
     * 本地IP-IPV4.
     */
    public static final String LOCAL_IPV4 = "127.0.0.1";

    /**
     * 未知IP.
     */
    private static final String UNKNOWN_IP = "unknown";

    /**
     * 本地IP-IPV6.
     */
    private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";

    private final static String localIp = "127.0.0.1";

    private final static Searcher searcher;

    static {
        try (InputStream inputStream = ResourceUtils.getResource("ip2region.xdb").getInputStream()) {
            searcher = Searcher.newWithBuffer(inputStream.readAllBytes());
            log.debug("缓存成功！！！！");
        } catch (IOException e) {
            log.error("解析ip地址失败,无法创建搜索器: {}", e.getMessage(), e);
            throw new SysException();
        }
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <a href="http://developer.51cto.com/art/201111/305181.htm">参考文章</a>
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为：192.168.1.110
     *
     * @param request {@link HttpServletRequest}
     * @return {@link String} 真实IP地址
     */
    public static String getIp(HttpServletRequest request) {
        if (ObjectUtil.isNull(request)) {
            return UNKNOWN_IP;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (conditionNull(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (conditionNull(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (conditionNull(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (conditionNull(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (conditionNull(ip)) {
            ip = request.getRemoteAddr();
        }
        return LOCAL_IPV6.equals(ip) ? LOCAL_IPV4 : ip.split(StringConstant.DEFAULT_DELIMITER)[0];
    }


    /**
     * 判断IP不存在或未知.
     *
     * @param ip IP地址
     * @return 判断结果
     */
    private static boolean conditionNull(String ip) {
        return StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip);
    }

    /**
     * 获取访问设备
     *
     * @param request {@link HttpServletRequest}
     * @return {@link UserAgent} 访问设备
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgentUtil.parse(request.getHeader("User-Agent"));
    }


    /**
     * 判断内部IP.
     *
     * @param addr 字节数组
     * @return 判断结果
     */
    private static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte section1 = 0x0A;
        // 172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        return switch (b0) {
            case section1 -> true;
            case section2 -> b1 >= section3 && b1 <= section4;
            case section5 -> b1 == section6;
            default -> false;
        };
    }


    /**
     * 判断内部IP.
     *
     * @param ip IP地址
     * @return 判断结果
     */
    public static boolean internalIp(String ip) {
        if (LOCAL_IPV6.equals(ip)) {
            return true;
        }
        byte[] bytes = textToNumericFormatV4(ip);
        return bytes.length > 0 && (internalIp(bytes) || LOCAL_IPV4.equals(ip));
    }

    /**
     * 将IPv4地址转换成字节.
     *
     * @param text IPv4地址
     * @return 字节
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.isEmpty()) {
            return new byte[0];
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            long j;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    j = 4294967295L;
                    if ((l < 0L) || (l > j)) {
                        return new byte[0];
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    j = 255;
                    if (l < 0L || l > j) {
                        return new byte[0];
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    j = 16777215;
                    if (l < 0L || l > j) {
                        return new byte[0];
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    j = 2;
                    for (int i = 0; i < j; i++) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return new byte[0];
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    j = 65535L;
                    if ((l < 0L) || (l > j)) {
                        return new byte[0];
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    j = 4;
                    for (int i = 0; i < j; i++) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return new byte[0];
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return new byte[0];
            }
        } catch (NumberFormatException e) {
            log.error("格式化失败，错误信息：{}，详情见日志", e.getMessage(), e);
            return new byte[0];
        }
        return bytes;
    }

    /**
     * 获取IP地址
     *
     * @return 本地IP地址
     */
    public static String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info("获取IP地址失败 {}", e.getMessage(), e);
        }
        return localIp;
    }


    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.info("获取主机名失败 {}", e.getMessage(), e);
        }
        return "未知";
    }


    /**
     * 根据IP获取所属位置.
     *
     * @param ip IP
     * @return 所属位置
     */
    @SneakyThrows
    public static String getRealAddress(String ip) {
        return internalIp(ip) ? LOCAL_ADDR : addressFormat(searcher.search(ip));
    }

    /**
     * 位置格式化.
     *
     * @param address 所属位置
     * @return 格式化后的位置
     */
    private static String addressFormat(String address) {
        StringBuilder stringBuilder = new StringBuilder(address.length());
        String[] info = address.split("\\|");
        Arrays.stream(info)
                .forEach(str -> stringBuilder.append(ObjectUtil.equals(EMPTY_ADDR, str) ? StringConstant.EMPTY_STR : str));
        return stringBuilder.toString().trim();
    }
}