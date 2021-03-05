package com.fego.transaction.common.enumeration;

import com.fego.transaction.common.constants.Constants;
import org.springframework.lang.Nullable;

public enum AutoDeduct {
    ONE(1, 1, Constants.MONTH),
    TWO(2, 2, Constants.MONTH),
    THREE(3, 3, Constants.MONTH),
    FOUR(4, 4, Constants.MONTH),
    FIVE(5, 5, Constants.MONTH),
    SIX(6, 6, Constants.MONTH),
    SEVEN(7, 7, Constants.MONTH),
    EIGHT(8, 8, Constants.MONTH),
    NINE(9, 9, Constants.MONTH),
    TEN(10, 10, Constants.MONTH),
    ELEVEN(11, 11, Constants.MONTH),
    TWELVE(12, 12, Constants.MONTH),
    THIRTEEN(13, 13, Constants.MONTH),
    FOURTEEN(14, 14, Constants.MONTH),
    FIFTEEN(15, 15, Constants.MONTH),
    SIXTEEN(16, 16, Constants.MONTH),
    SEVENTEEN(17, 17, Constants.MONTH),
    EIGHTEEN(18, 18, Constants.MONTH),
    NINETEEN(19, 19, Constants.MONTH),
    TWENTY(20, 20, Constants.MONTH),
    TWENTY_ONE(21, 21, Constants.MONTH),
    TWENTY_TWO(22, 22, Constants.MONTH),
    TWENTY_THREE(23, 23, Constants.MONTH),
    TWENTY_FOUR(24, 24, Constants.MONTH),
    TWENTY_FIVE(25, 25, Constants.MONTH),
    TWENTY_SIX(26, 26, Constants.MONTH),
    TWENTY_SEVEN(27, 27, Constants.MONTH),
    TWENTY_EIGHT(28, 28, Constants.MONTH),
    TWENTY_NINE(29, 1, Constants.MONTH),
    THIRTY(30, 2, Constants.WEEK),
    THIRTY_ONE(31, 3, Constants.WEEK),
    THIRTY_TWO(32, 4, Constants.WEEK),
    THIRTY_THREE(33, 5, Constants.WEEK),
    THIRTY_FOUR(34, 6, Constants.WEEK),
    THIRTY_FIVE(35, 7, Constants.WEEK),
    THIRTY_SIX(36, 0, "DAY");

    private final int id;
    private final int frequency;
    private final String frequencyPeriod;

    AutoDeduct(int id, int frequency, String frequencyPeriod) {
        this.id = id;
        this.frequency = frequency;
        this.frequencyPeriod = frequencyPeriod;
    }

    public static AutoDeduct valueOf(int frequency, String frequencyPeriod) {
        AutoDeduct autoDeduct = resolve(frequency, frequencyPeriod);
        if (autoDeduct == null) {
            throw new IllegalArgumentException("No matching constant found for [" + frequency + "and " + frequencyPeriod + "]");
        }
        return autoDeduct;
    }

    @Nullable
    public static AutoDeduct resolve(int frequency, String frequencyPeriod) {
        for (AutoDeduct autoDeduct : values()) {
            if (autoDeduct.frequencyPeriod().equals(frequencyPeriod) && autoDeduct.frequency() == frequency) {
                return autoDeduct;
            }
        }
        return null;
    }

    public static AutoDeduct valueOf(int id) {
        AutoDeduct autoDeduct = resolve(id);
        if (autoDeduct == null) {
            throw new IllegalArgumentException("No matching constant found for [" + id + "]");
        }
        return autoDeduct;
    }

    @Nullable
    public static AutoDeduct resolve(int id) {
        for (AutoDeduct autoDeduct : values()) {
            if (autoDeduct.id() == id) {
                return autoDeduct;
            }
        }
        return null;
    }

    public int frequency() {
        return this.frequency;
    }

    public int id() {
        return this.id;
    }

    public String frequencyPeriod() {
        return this.frequencyPeriod;
    }
}