package com.fourreau.itwapp.util;

import java.text.SimpleDateFormat;

/**
 * Created by Pierre on 11/05/2015.
 */
public class Utils {
    private Utils(){}
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

}
