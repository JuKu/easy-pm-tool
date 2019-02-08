package com.jukusoft.pm.tool.def.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteUtilsTest {

    @Test
    public void testConstructor () {
        //
    }

    @Test
    public void testGetBit () {
        int n = 4;//binary: 0000 0100 (--> third bit is set)

        assertEquals(0, ByteUtils.getBit(n, 0));
        assertEquals(0, ByteUtils.getBit(n, 1));
        assertEquals(1, ByteUtils.getBit(n, 2));
        assertEquals(0, ByteUtils.getBit(n, 3));
        assertEquals(0, ByteUtils.getBit(n, 4));
        assertEquals(0, ByteUtils.getBit(n, 5));
        assertEquals(0, ByteUtils.getBit(n, 6));
        assertEquals(0, ByteUtils.getBit(n, 7));
    }

    @Test
    public void testSetBit () {
        int n = 0;

        //check, that no bit is set
        for (int i = 0; i < 8; i++) {
            assertEquals(0, ByteUtils.getBit(n, i));
        }

        //set bit
        n = ByteUtils.setBit(n, 2, true);
        System.err.println("n: " + n);

        assertEquals(0, ByteUtils.getBit(n, 0));
        assertEquals(0, ByteUtils.getBit(n, 1));
        assertEquals(1, ByteUtils.getBit(n, 2));
        assertEquals(0, ByteUtils.getBit(n, 3));
        assertEquals(0, ByteUtils.getBit(n, 4));
        assertEquals(0, ByteUtils.getBit(n, 5));
        assertEquals(0, ByteUtils.getBit(n, 6));
        assertEquals(0, ByteUtils.getBit(n, 7));

        //unset bit
        n = ByteUtils.setBit(n, 2, false);

        //check, that no bit is set
        for (int i = 0; i < 8; i++) {
            assertEquals("bit is set, but shouldn't be set: " + i, 0, ByteUtils.getBit(n, i));
        }
    }

}
