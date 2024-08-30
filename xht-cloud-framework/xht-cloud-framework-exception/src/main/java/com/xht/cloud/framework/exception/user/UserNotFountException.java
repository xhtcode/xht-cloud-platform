package com.xht.cloud.framework.exception.user;

/**
 * 描述 ：用户查询不到
 *
 * @author 小糊涂
 **/
public class UserNotFountException extends UserException {

    public UserNotFountException() {
        super("查询不到用户信息");
    }
}
