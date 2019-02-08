package com.jukusoft.pm.tool.def.utils;

public class ByteUtils {

    protected ByteUtils () {
        //
    }

    public static int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    public static boolean isBitSet (int n, int k) {
        return getBit(n, k) == 1;
    }

    public static int setBit(int target, int bit, boolean value) {
        if (value) {
            target |= (1 << bit);
        } else {
            target &= ~(1 << bit);
        }

        return target;
    }

}
