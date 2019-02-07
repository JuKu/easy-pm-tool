package com.jukusoft.pm.tool.def.utils;

public class StringUtils {

    /**
     * private constructor, so others cannot create an instance of this class
     */
    protected StringUtils() {
        //
    }

    public static void requireNonEmptyString(String str, String objName) {
        if (str == null) {
            throw new NullPointerException(objName + " cannot be null.");
        }

        if (str.isEmpty()) {
            throw new IllegalArgumentException(objName + " cannot be empty.");
        }
    }

    public static void requireNonEmptyString(String str) {
        StringUtils.requireNonEmptyString(str, "string");
    }


}
