package com.fego.transaction.common.enumeration;

import org.springframework.lang.Nullable;

public enum GoalCategory {
    PLACE(0),
    ASSET(1),
    PAY_OFF_DEBTS(2),
    JUST_START_SAVING(3);

    private final int code;

    GoalCategory(int code) {
        this.code = code;
    }

    public static GoalCategory valueOf(int code) {
        GoalCategory goalCategory = resolve(code);
        if (goalCategory == null) {
            throw new IllegalArgumentException("No matching constant found for [" + code + "]");
        }
        return goalCategory;
    }

    @Nullable
    public static GoalCategory resolve(int code) {
        for (GoalCategory goalCategory : values()) {
            if (goalCategory.code() == code) {
                return goalCategory;
            }
        }
        return null;
    }

    public int code() {
        return this.code;
    }
}