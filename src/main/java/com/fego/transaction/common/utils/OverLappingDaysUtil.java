package com.fego.transaction.common.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OverLappingDaysUtil {
    private OverLappingDaysUtil() {

    }

    public static List<Integer> getOverlappingDays(int day) {
        String value = (day <= 9) ? "2021-03-0" + day : "2021-03-" + day;
        LocalDate start = LocalDate.parse(value);
        LocalDate end = LocalDate.parse(value).plusDays(9);
        List<Integer> overLappingDays = new ArrayList<>();
        while (!start.isAfter(end)) {
            overLappingDays.add(start.getDayOfMonth());
            start = start.plusDays(1);
        }
        return overLappingDays;
    }
}