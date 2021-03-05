package com.fego.transaction.common.enumeration;

public enum SavingPreference {
    NEVER_SAVED(0),
    ONCE_IN_WHILE(1),
    AGGRESSIVE_SAVER(2);

    private final int code;

    SavingPreference(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}