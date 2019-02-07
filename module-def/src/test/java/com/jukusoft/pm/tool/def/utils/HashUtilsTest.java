package com.jukusoft.pm.tool.def.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Justin on 18.01.2017.
 */
public class HashUtilsTest {

    @Test
    public void testConstructor () {
        new HashUtils();
    }

    @Test
    public void testComputeMD5Hash () {
        String text = "text";

        //expected hash calculated with PHP md5 function (so it should be compatible with PHP, so we can use this hash method also on website).
        String expectedMD5Hash = "1cb251ec0d568de6a929b520c4aed8d1";

        String hash = HashUtils.computeMD5Hash(text);

        assertEquals(expectedMD5Hash, hash);
    }

    @Test
    public void testComputeSHAHash () {
        String text = "text";

        //expected hash calculated with PHP sha1 function (so it should be compatible with PHP, so we can use this hash method also on website).
        //String expectedMD5Hash = "372ea08cab33e71c02c651dbc83a474d32c676ea";

        String expectedMD5Hash = "Ny6gjKsz5xwCxlHbyDpHTTLGduo=";

        String hash = HashUtils.computeSHAHash(text);

        assertEquals(expectedMD5Hash, hash);
    }

    @Test
    public void testComputePasswordSHAHash () {
        String text = "test";
        String expectedHash = "7iaw3Ur350mqGo7jwQrpkj9hiYB3Lkc/iBml1JQODbJ6wYX4oOHV+E+IvIh/1nsUNzLDBMxfqa2Ob1f1ACio/w==";

        assertEquals(expectedHash, HashUtils.computePasswordSHAHash(text));

        //check for same length
        assertEquals(88, HashUtils.computePasswordSHAHash(text).length());

        //check, that all passwords have same length
        for (int i = 0; i < 10; i++) {
            assertEquals(88, HashUtils.computePasswordSHAHash(HashUtils.generateSalt()).length());
        }
    }

    @Test
    public void testGenerateSalt () {
        assertNotNull(HashUtils.generateSalt());
        assertEquals(24, HashUtils.generateSalt().length());

        //check, that no equal saltes are returned
        String salt1 = HashUtils.generateSalt();
        String salt2 = HashUtils.generateSalt();
        assertNotEquals(salt1, salt2);

        assertEquals(false, salt1.isEmpty());
        assertEquals(false, salt2.isEmpty());
    }

}
