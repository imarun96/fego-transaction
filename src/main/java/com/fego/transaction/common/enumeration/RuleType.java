package com.fego.transaction.common.enumeration;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RuleType {
    AUTO_DEDUCT(0, "Auto Deduct", "Automatically deduct some money from income as savings in a recurring period."),
    INSTANT_SAVING_POP_UP(1, "Instant Saving Pop", "An instant saving notification pops in to choose one out of three small amounts of savings."),
    ROUND_UP(2, "Round Up Transaction", "On every transaction round up your change to the nearest ₹2 or ₹3 and watch it add up faster.");

    private final int code;
    private final String rule;
    private final String description;

    RuleType(int code, String rule, String description) {
        this.code = code;
        this.rule = rule;
        this.description = description;
    }

    public static Map<Integer, List<String>> getAll() {
        Map<Integer, List<String>> value = new HashMap<>();
        for (RuleType code : RuleType.values()) {
            List<String> keys = new ArrayList<>();
            keys.add(code.rule());
            keys.add(code.description());
            value.put(code.code(), keys);
        }
        return value;
    }

    public static RuleType valueOf(int code) {
        RuleType ruleType = resolve(code);
        if (ruleType == null) {
            throw new IllegalArgumentException("No matching constant found for [" + code + "]");
        }
        return ruleType;
    }

    @Nullable
    public static RuleType resolve(int code) {
        for (RuleType ruleType : values()) {
            if (ruleType.code() == code) {
                return ruleType;
            }
        }
        return null;
    }

    public int code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public String rule() {
        return this.rule;
    }
}