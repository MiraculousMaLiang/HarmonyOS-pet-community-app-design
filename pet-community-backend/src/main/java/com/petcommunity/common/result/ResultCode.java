package com.petcommunity.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    ERROR(500, "操作失败"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权,请先登录"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(1001, "用户名或密码错误"),

    /**
     * 用户已存在
     */
    USER_EXIST(1002, "用户已存在"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(1003, "用户不存在"),

    /**
     * 账号已被禁用
     */
    USER_DISABLED(1004, "账号已被禁用"),

    /**
     * Token无效
     */
    TOKEN_INVALID(1005, "Token无效"),

    /**
     * Token已过期
     */
    TOKEN_EXPIRED(1006, "Token已过期"),

    /**
     * 宠物不存在
     */
    PET_NOT_EXIST(2001, "宠物不存在"),

    /**
     * 动态不存在
     */
    POST_NOT_EXIST(3001, "动态不存在"),

    /**
     * 商品不存在
     */
    PRODUCT_NOT_EXIST(4001, "商品不存在"),

    /**
     * 库存不足
     */
    STOCK_NOT_ENOUGH(4002, "库存不足"),

    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST(5001, "订单不存在"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(6001, "文件上传失败"),

    /**
     * 文件类型不支持
     */
    FILE_TYPE_ERROR(6002, "文件类型不支持"),

    /**
     * 文件大小超限
     */
    FILE_SIZE_ERROR(6003, "文件大小超限");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

}
