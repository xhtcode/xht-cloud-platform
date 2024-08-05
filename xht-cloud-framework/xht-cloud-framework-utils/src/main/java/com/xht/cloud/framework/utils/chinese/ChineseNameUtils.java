package com.xht.cloud.framework.utils.chinese;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.cloud.framework.utils.support.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述 ：中午名字处理工具类
 *
 * @author 小糊涂
 **/
public final class ChineseNameUtils {

    public static Set<String> DOUBLE_SURNAME = new HashSet<>();

    static {
        DOUBLE_SURNAME.add("欧阳");
        DOUBLE_SURNAME.add("太史");
        DOUBLE_SURNAME.add("端木");
        DOUBLE_SURNAME.add("上官");
        DOUBLE_SURNAME.add("司马");
        DOUBLE_SURNAME.add("东方");
        DOUBLE_SURNAME.add("独孤");
        DOUBLE_SURNAME.add("南宫");
        DOUBLE_SURNAME.add("万俟");
        DOUBLE_SURNAME.add("闻人");
        DOUBLE_SURNAME.add("夏侯");
        DOUBLE_SURNAME.add("诸葛");
        DOUBLE_SURNAME.add("尉迟");
        DOUBLE_SURNAME.add("公羊");
        DOUBLE_SURNAME.add("赫连");
        DOUBLE_SURNAME.add("澹台");
        DOUBLE_SURNAME.add("皇甫");
        DOUBLE_SURNAME.add("宗政");
        DOUBLE_SURNAME.add("濮阳");
        DOUBLE_SURNAME.add("公冶");
        DOUBLE_SURNAME.add("太叔");
        DOUBLE_SURNAME.add("申屠");
        DOUBLE_SURNAME.add("公孙");
        DOUBLE_SURNAME.add("慕容");
        DOUBLE_SURNAME.add("仲孙");
        DOUBLE_SURNAME.add("钟离");
        DOUBLE_SURNAME.add("长孙");
        DOUBLE_SURNAME.add("宇文");
        DOUBLE_SURNAME.add("司徒");
        DOUBLE_SURNAME.add("鲜于");
        DOUBLE_SURNAME.add("司空");
        DOUBLE_SURNAME.add("闾丘");
        DOUBLE_SURNAME.add("子车");
        DOUBLE_SURNAME.add("亓官");
        DOUBLE_SURNAME.add("司寇");
        DOUBLE_SURNAME.add("巫马");
        DOUBLE_SURNAME.add("公西");
        DOUBLE_SURNAME.add("颛孙");
        DOUBLE_SURNAME.add("壤驷");
        DOUBLE_SURNAME.add("公良");
        DOUBLE_SURNAME.add("漆雕");
        DOUBLE_SURNAME.add("乐正");
        DOUBLE_SURNAME.add("宰父");
        DOUBLE_SURNAME.add("谷梁");
        DOUBLE_SURNAME.add("拓跋");
        DOUBLE_SURNAME.add("夹谷");
        DOUBLE_SURNAME.add("轩辕");
        DOUBLE_SURNAME.add("令狐");
        DOUBLE_SURNAME.add("段干");
        DOUBLE_SURNAME.add("百里");
        DOUBLE_SURNAME.add("呼延");
        DOUBLE_SURNAME.add("东郭");
        DOUBLE_SURNAME.add("南门");
        DOUBLE_SURNAME.add("羊舌");
        DOUBLE_SURNAME.add("微生");
        DOUBLE_SURNAME.add("公户");
        DOUBLE_SURNAME.add("公玉");
        DOUBLE_SURNAME.add("公仪");
        DOUBLE_SURNAME.add("梁丘");
        DOUBLE_SURNAME.add("公仲");
        DOUBLE_SURNAME.add("公上");
        DOUBLE_SURNAME.add("公门");
        DOUBLE_SURNAME.add("公山");
        DOUBLE_SURNAME.add("公坚");
        DOUBLE_SURNAME.add("左丘");
        DOUBLE_SURNAME.add("公伯");
        DOUBLE_SURNAME.add("西门");
        DOUBLE_SURNAME.add("公祖");
        DOUBLE_SURNAME.add("第五");
        DOUBLE_SURNAME.add("公乘");
        DOUBLE_SURNAME.add("贯丘");
        DOUBLE_SURNAME.add("公皙");
        DOUBLE_SURNAME.add("南荣");
        DOUBLE_SURNAME.add("东里");
        DOUBLE_SURNAME.add("东宫");
        DOUBLE_SURNAME.add("仲长");
        DOUBLE_SURNAME.add("子书");
        DOUBLE_SURNAME.add("子桑");
        DOUBLE_SURNAME.add("即墨");
        DOUBLE_SURNAME.add("淳于");
        DOUBLE_SURNAME.add("达奚");
        DOUBLE_SURNAME.add("褚师");
        DOUBLE_SURNAME.add("吴铭");
        DOUBLE_SURNAME.add("纳兰");
        DOUBLE_SURNAME.add("归海");
        DOUBLE_SURNAME.add("刘付");
    }

    /**
     * @return 脱敏只保留姓
     */
    public static String desensitization(String name) {
        if (!StringUtils.hasText(name)) return null;
        int countLength = name.length();
        if (countLength == 1) {
            return name;
        }
        if (countLength > 2) {
            String oldName = DOUBLE_SURNAME.stream().filter(item -> StrUtil.contains(name, item)).findFirst().orElse(null);
            if (StringUtils.hasText(oldName)) return StrUtil.hide(name, oldName.length(), name.length());
        }
        return DesensitizedUtil.chineseName(name);
    }

}