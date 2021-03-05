package com.fego.transaction.common.utils;

import org.springframework.stereotype.Service;

@Service
public class CacheDataUtil {

    private CacheDataUtil() {

    }

    public static long getUserId() {
        // Will add code to return the current user id once redis cache is created
        return 1L;
    }
}