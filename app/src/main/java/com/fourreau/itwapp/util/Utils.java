package com.fourreau.itwapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 * Created by Pierre on 11/05/2015.
 */
public class Utils {
    private Utils(){}
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat sdfDateSimple = new SimpleDateFormat("dd/MM/yyyy");

    public static SimpleDateFormat sdfTimeSimple = new SimpleDateFormat("HH:mm");

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String URL_GRAVATAR_BEGIN = "http://www.gravatar.com/avatar/";
    public static String URL_GRAVATAR_END = "?s=400&r=pg&d=mm";
    public static String URL_GRAVATAR_END_SMALL = "?s=200&r=pg&d=mm";

    public static String getUrlGravatar(String hash) {
        return URL_GRAVATAR_BEGIN + hash + URL_GRAVATAR_END;
    }

    public static String getSmallUrlGravatar(String hash) {
        return URL_GRAVATAR_BEGIN + hash + URL_GRAVATAR_END_SMALL;
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
    public static String md5Hex (String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

}
