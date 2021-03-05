package com.fego.transaction.common.enumeration;

import org.springframework.lang.Nullable;

public enum InstantSaving {
    EVERYTIME_LOGIN(1),
    ONCE_IN_A_WEEK(2),
    ONCE_IN_TWO_WEEK(3),
    ONCE_IN_MONTH(4);

    private final int code;

    InstantSaving(int code) {
        this.code = code;
    }

    public static InstantSaving valueOf(int code) {
        InstantSaving instantSaving = resolve(code);
        if (instantSaving == null) {
            throw new IllegalArgumentException("No matching constant found for [" + code + "]");
        }
        return instantSaving;
    }

    @Nullable
    public static InstantSaving resolve(int code) {
        for (InstantSaving instantSaving : values()) {
            if (instantSaving.code() == code) {
                return instantSaving;
            }
        }
        return null;
    }

    public int code() {
        return this.code;
    }
}