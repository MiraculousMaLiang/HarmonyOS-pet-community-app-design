package com.petcommunity.common.constant;

/**
 * 系统常量
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "登录成功";

    /**
     * 注销成功
     */
    public static final String LOGOUT_SUCCESS = "退出成功";

    /**
     * 注册成功
     */
    public static final String REGISTER_SUCCESS = "注册成功";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 5;

    /**
     * Token有效期（秒）
     */
    public static final Integer TOKEN_EXPIRATION = 7 * 24 * 3600;

    /**
     * 默认头像
     */
    public static final String DEFAULT_AVATAR = "/images/default-avatar.png";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 分页-每页显示记录数
     */
    public static final Integer PAGE_SIZE = 10;

    /**
     * 分页-当前页码
     */
    public static final Integer PAGE_NUM = 1;

}
