package com.weekly.common.type;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 性别枚举类型
 */
public enum GenderType {
    NOTSET, // 未设置
    MALE, // 男
    FEMALE; // 女

    @JsonValue
    public int getOrdinal() {
        return this.ordinal();
    }
}
