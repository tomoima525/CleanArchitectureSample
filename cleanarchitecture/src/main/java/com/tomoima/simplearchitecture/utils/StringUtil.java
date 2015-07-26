package com.tomoima.simplearchitecture.utils;

/**
 * Created by tomoaki on 7/25/15.
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String str){
        return (str == null || str.trim().length() < 1);
    }
}
