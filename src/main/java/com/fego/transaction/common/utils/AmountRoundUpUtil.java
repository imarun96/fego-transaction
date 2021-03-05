package com.fego.transaction.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountRoundUpUtil {
    private AmountRoundUpUtil() {

    }

    public static BigDecimal roundUpAmount(BigDecimal number, BigDecimal multiple) {
        return number.divide(multiple, 0, RoundingMode.CEILING).multiply(multiple).subtract(number);
    }
}