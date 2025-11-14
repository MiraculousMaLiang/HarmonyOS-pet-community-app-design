package com.petcommunity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author Pet Community Team
 * @since 2025-11-14
 */
@Getter
@AllArgsConstructor
public enum Gender {

    /**
     * 未知
     */
    UNKNOWN(0, "未知"),

    /**
     * 男/公
     */
    MALE(1, "男/公"),

    /**
     * 女/母
     */
    FEMALE(2, "女/母");

    private final Integer code;
    private final String desc;

}
