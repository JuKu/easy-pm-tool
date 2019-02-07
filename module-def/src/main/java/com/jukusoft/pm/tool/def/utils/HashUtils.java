/*
* Coypright (c) 2015 Justin Kuenzel
* Apache 2.0 License
*
* This file doesnt belongs to the Pentaquin Project.
* This class is owned by Justin Kuenzel and licensed under the Apache 2.0 license.
* Many projects use this class.
*/

package com.jukusoft.pm.tool.def.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * Created by Justin on 26.01.2015.
 */
public class HashUtils {

    protected static final Logger logger = LoggerFactory.getLogger(HashUtils.class);
    protected static final Random random = new Random();

    protected HashUtils() {
        //
    }

    /**
    * convert byte data to hex
    */
    private static String convertToHex(byte[] data) {
        //create new instance of string buffer
        StringBuilder StringBuilder = new StringBuilder();
        String hex = "";

        //encode byte data with base64
        hex = Base64.getEncoder().encodeToString(data);
        StringBuilder.append(hex);

        //return string
        return StringBuilder.toString();
    }

    /**
    * generates SHA Hash
     *
     * @param password text
     *
     * @return hash
    */
    public static String computeSHAHash(String password) {
        MessageDigest mdSha1 = null;
        String SHAHash = "";

        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            logger.error("NoSuchAlgorithmException: ", e1);
            throw new IllegalStateException("Cannot find SHA-1 algorithm.", e1);
        }

        mdSha1.update(password.getBytes(StandardCharsets.US_ASCII));
        byte[] data = mdSha1.digest();

        SHAHash = convertToHex(data);

        return SHAHash;
    }

    /**
     * generates SHA-512 Hash for passwords
     *
     * @param password text
     *
     * @return hash
     */
    public static String computePasswordSHAHash(String password) {
        MessageDigest mdSha1 = null;
        String SHAHash = "";

        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e1) {
            logger.error("NoSuchAlgorithmException: ", e1);

            throw new IllegalStateException("encryption algorithm isn't available: ", e1);
        }

        mdSha1.update(password.getBytes(StandardCharsets.US_ASCII));
        byte[] data = mdSha1.digest();
        SHAHash = convertToHex(data);

        return SHAHash;
    }

    /**
     * generates MD5 hash
     *
     * This method is compatible to PHP 5 and Java 8.
     *
     * @param password text
     * @return hash
    */
    public static String computeMD5Hash(String password) {
        StringBuilder md5Hash = new StringBuilder();

        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] messageDigest = digest.digest();

            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                md5Hash.append(h);
            }

        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException: ", e);
        }

        return md5Hash.toString();
    }

    public static String generateSalt () {
        byte[] salt = new byte[18];
        random.nextBytes(salt);

        return Base64Utils.encodeToString(salt);
    }

}
