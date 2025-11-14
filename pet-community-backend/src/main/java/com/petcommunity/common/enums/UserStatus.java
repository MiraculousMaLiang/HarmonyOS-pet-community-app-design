package com.petcommunity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    /**
     * 禁用
     */
    DISABLED(0, "禁用"),

    /**
     * 正常
     */
    NORMAL(1, "正常");

    private final Integer code;
    private final String desc;

}
