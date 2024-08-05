package com.xht.cloud.framework.exception.user;

/**
 * 描述 ：用户查询不到
 *
 * @author 小糊涂
 **/
public class UserNotFountException extends UserException {

    public UserNotFountException(String userName) {
        super(String.format("账号：`%s`不存在", userName));
    }

    public UserNotFountException() {
        super("用户信息不存在");
    }
}
