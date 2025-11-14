package com.petcommunity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    /**
     * 已取消
     */
    CANCELED(0, "已取消"),

    /**
     * 待付款
     */
    PENDING_PAYMENT(1, "待付款"),

    /**
     * 待发货
     */
    PENDING_DELIVERY(2, "待发货"),

    /**
     * 已发货
     */
    DELIVERED(3, "已发货"),

    /**
     * 已完成
     */
    COMPLETED(4, "已完成");

    private final Integer code;
    private final String desc;

}
