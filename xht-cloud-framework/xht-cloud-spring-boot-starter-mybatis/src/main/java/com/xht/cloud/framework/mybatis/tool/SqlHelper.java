package com.xht.cloud.framework.mybatis.tool;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
public class SqlHelper {

    /**
     * 判断数据库插入操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean save(Integer result) {
        return null != result && result > 0;
    }


    /**
     * 判断数据库删除操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean remove(Integer result) {
        return null != result && result > 0;
    }

    /**
     * 判断数据库修改操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean update(Integer result) {
        return null != result && result > 0;
    }

    /**
     * 判断数据是否存在
     *
     * @param result 数据库操作返回影响条数
     * @return boolean true 存在 false不存在
     */
    public static boolean exist(Long result) {
        return null != result && result > 0;
    }
}
