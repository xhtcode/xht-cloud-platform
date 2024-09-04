package com.xht.cloud.framework.mybatis.tool;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.cloud.framework.domain.request.PageQueryRequest;
import com.xht.cloud.framework.domain.response.PageResponse;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * 描述 ：分页插件
 *
 * @author 小糊涂
 * @version : 1.0
 **/
public final class PageTool {

    private final static String[] KEYWORDS = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop", "sleep", "extractvalue", "concat"};

    private static final String[] EMP_ARRAY = new String[0];

    /**
     * 获取分页对象
     *
     * @param query 公共查询对象
     * @param <T>   泛型对象
     * @return IPage
     */
    public static <T> Page<T> getPage(PageQueryRequest query) {
        String[] ascArr = StringUtils.delimitedListToStringArray(query.getAscName(), ",");
        String[] descArr = StringUtils.delimitedListToStringArray(query.getDescName(), ",");
        return getPage(query.getCurrent(), query.getSize(), ascArr, descArr);
    }


    /**
     * 获取分页对象
     *
     * @param current 当前页
     * @param size    每页显示条数
     * @param <T>     泛型对象
     * @return IPage
     */
    public static <T> IPage<T> getPage(long current, long size) {
        return getPage(current, size, EMP_ARRAY, EMP_ARRAY);
    }

    /**
     * 获取分页对象
     *
     * @param current 当前页
     * @param size    每页显示条数
     * @param ascArr  排序的字段名
     * @param descArr 排序方式
     * @param <T>     泛型对象
     * @return IPage
     */
    public static <T> Page<T> getPage(long current, long size, String[] ascArr, String[] descArr) {
        if (size <= 0) {
            size = 10;
        }
        Page<T> page = new Page<>(current, size);
        List<OrderItem> orderItemList = new ArrayList<>();
        Optional.ofNullable(ascArr).ifPresent(s -> orderItemList.addAll(
                Arrays.stream(s).filter(sqlInjectPredicate()).map(t -> OrderItem.asc(StrUtil.toUnderlineCase(t).toUpperCase())).toList()));
        Optional.ofNullable(descArr).ifPresent(s -> orderItemList.addAll(
                Arrays.stream(s).filter(sqlInjectPredicate()).map(t -> OrderItem.desc(StrUtil.toUnderlineCase(t).toUpperCase())).toList()));
        page.addOrder(orderItemList);
        return page;
    }

    /**
     * 创建分页对象
     *
     * @param current 当前页
     * @param size    每页显示条数
     * @param total   总条目数
     * @param list    结果集
     * @param <T>     泛型对象
     * @return IPage
     */
    public static <T> PageResponse<T> createPageVo(int current, int size, Integer total, List<T> list) {
        PageResponse<T> resultVo = new PageResponse<>();
        resultVo.setCurrent(current);
        resultVo.setSize(size);
        resultVo.setTotal(total);
        resultVo.setPages(PageUtil.totalPage(total, size));
        resultVo.setList(list);
        return resultVo;
    }

    /**
     * 格式化分页对象
     *
     * @param iPage 数据库分页数据模型
     * @param <T>   泛型对象
     * @return IPage
     */
    public static <T> PageResponse<T> getPageVo(IPage<T> iPage) {
        PageResponse<T> resultVo = new PageResponse<>();
        resultVo.setList(iPage.getRecords());
        resultVo.setTotal(iPage.getTotal());
        resultVo.setSize(iPage.getSize());
        resultVo.setCurrent(iPage.getCurrent());
        resultVo.setPages(iPage.getPages());
        return resultVo;
    }


    /**
     * 返回值一个null的IPage对象
     *
     * @return IPage
     */
    public static <T> PageResponse<T> empty() {
        PageResponse<T> resultVo = new PageResponse<>();
        resultVo.setTotal(0);
        resultVo.setSize(0);
        resultVo.setCurrent(0);
        resultVo.setPages(0);
        return resultVo;
    }

    /**
     * 创建一个新的但是数据是null
     *
     * @return PageVO<T>
     */
    public static <T> PageResponse<T> cloneEmpty(IPage<?> iPage) {
        PageResponse<T> resultVo = new PageResponse<>();
        resultVo.setTotal(iPage.getTotal());
        resultVo.setSize(iPage.getSize());
        resultVo.setCurrent(iPage.getCurrent());
        resultVo.setPages(iPage.getPages());
        return resultVo;
    }

    /**
     * 判断用户输入里面有没有关键字
     *
     * @return Predicate
     */
    private static Predicate<String> sqlInjectPredicate() {
        return sql -> Arrays.stream(KEYWORDS).noneMatch(keyword -> StrUtil.containsIgnoreCase(sql, keyword));
    }

}
