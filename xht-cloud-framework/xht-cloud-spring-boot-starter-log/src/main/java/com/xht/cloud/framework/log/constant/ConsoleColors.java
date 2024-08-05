package com.xht.cloud.framework.log.constant;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public interface ConsoleColors {
    // Reset
    String RESET = "\033[0m";  // 文字重置

    // 常规的颜色
    String WHITE = "\033[0;30m";   // 白色
    String RED = "\033[0;31m";     // 红色
    String GREEN = "\033[0;32m";   // 绿色
    String YELLOW = "\033[0;33m";  // 黄色
    String BLUE = "\033[0;34m";    // 蓝色
    String PURPLE = "\033[0;35m";  // 紫色的
    String CYAN = "\033[0;36m";    // 青色
    String GREY = "\033[0;37m";   // 灰色

    // ==============================加粗==============================
    String WHITE_BOLD = "\033[1;30m";  // WHITE
    String RED_BOLD = "\033[1;31m";    // RED
    String GREEN_BOLD = "\033[1;32m";  // GREEN
    String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    String BLUE_BOLD = "\033[1;34m";   // BLUE
    String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    String CYAN_BOLD = "\033[1;36m";   // CYAN
    String GREY_BOLD = "\033[1;37m";  // GREY

    // ==============================下划线==============================
    String WHITE_UNDERLINED = "\033[4;30m";  // WHITE
    String RED_UNDERLINED = "\033[4;31m";    // RED
    String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    String GREY_UNDERLINED = "\033[4;37m";  // GREY


}
