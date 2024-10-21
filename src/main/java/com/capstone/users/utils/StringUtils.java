package com.capstone.users.utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String value) {
        return (value == null || value.isEmpty() || value.trim().isEmpty());
    }
}
