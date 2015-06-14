package com.fourreau.itwapp.util;

import android.app.Activity;
import android.util.DisplayMetrics;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pierre on 11/05/2015.
 */
public class Utils {
    private Utils(){}
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat sdfFromString = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static String URL_HELP = "http://itwapp.io/faq";

    public static String URL_THUMBNAIL_YOUTUBE_BEGIN = "http://img.youtube.com/vi/";
    //320*180
    public static String URL_THUMBNAIL_YOUTUBE_END = "/mqdefault.jpg";
    //full size
    public static String URL_THUMBNAIL_YOUTUBE_FULL_SIZE_END = "/0.jpg";

    public static String URL_THUMBNAIL_YOUTUBE_DEFAULT_END = "/default.jpg";

    public static SimpleDateFormat sdfDateSimple = new SimpleDateFormat("dd/MM/yyyy");

    public static SimpleDateFormat sdfTimeSimple = new SimpleDateFormat("HH:mm");

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String URL_GRAVATAR_BEGIN = "http://www.gravatar.com/avatar/";
    public static String URL_GRAVATAR_END = "?s=300&r=pg&d=mm";
    public static String URL_GRAVATAR_END_SMALL = "?s=200&r=pg&d=mm";

    public static String URL_GRAVATAR_END_4INCH = "?s=200&r=pg&d=mm";
    public static String URL_GRAVATAR_END_SMALL_4INCH = "?s=100&r=pg&d=mm";

    public static String getUrlGravatar(String hash) {
        return URL_GRAVATAR_BEGIN + hash + URL_GRAVATAR_END;
    }

    public static String getSmallUrlGravatar(String hash) {
        return URL_GRAVATAR_BEGIN + hash + URL_GRAVATAR_END_SMALL;
    }

    public static String getUrlGravatar4Inch(String hash) {
        return URL_GRAVATAR_BEGIN + hash + URL_GRAVATAR_END_4INCH;
    }

    public static String getSmallUrlGravatar4Inch(String hash) {
        return URL_GRAVATAR_BEGIN + hash + URL_GRAVATAR_END_SMALL_4INCH;
    }

    public static Boolean is4InchOrLessScreen(Activity a) {
        DisplayMetrics metrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        if(widthPixels <= 720) {
            return true;
        }
        else {
            return false;
        }
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

    public static String extractYoutubeId(String youtubeUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }
}
