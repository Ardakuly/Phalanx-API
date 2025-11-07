package com.backbone.phalanx.util;

import java.util.Date;

public class CommonDateUtils {

    public static Date getCurrentDay() {
        return new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
    }

    public static Date getCurrentWeek() {
        return new Date(System.currentTimeMillis() + (7 * 1000 * 60 * 60 * 24));
    }
}