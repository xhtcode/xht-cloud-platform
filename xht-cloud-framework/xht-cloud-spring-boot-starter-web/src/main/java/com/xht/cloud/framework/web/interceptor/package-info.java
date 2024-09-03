/**
 * 描述 ：自定义拦截器
 * <p>
 * {@code preHandle}：此方法的作用是在请求进入到Controller进行拦截，有返回值。（返回true则将请求放行进入Controller控制层，false则请求结束返回错误信息）
 * <p>
 * 用法：登录验证（判断用户是否登录）权限验证：判断用户是否有权访问资源（校验token）
 * <p>
 * {@code postHandle}：该方法是在Controller控制器执行完成但是还没有返回模板进行渲染拦截。没有返回值。就是Controller----->拦截------>ModelAndView。
 * <p>
 * 用法：因此我们可以将Controller层返回来的参数进行一些修改，它就包含在ModelAndView中，所以该方法多了一个ModelAndView参数。
 * <p>
 * {@code afterCompletion}：该方法是在ModelAndView返回给前端渲染后执行。
 * <p>
 * 用法：例如登录的时候，我们经常把用户信息放到ThreadLocal中，为了防止内存泄漏，就需要将其remove掉，该操作就是在这里执行的
 *
 * @author : 小糊涂
 **/
@NonNullApi
package com.xht.cloud.framework.web.interceptor;

import org.springframework.lang.NonNullApi;