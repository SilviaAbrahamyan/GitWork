package com.aobyte.helper;

/**
 * Created by home on 2/5/2019.
 */
public class GitUrlHelper {
    public static String getRepositoryName(String url) {
        int firstIndex = url.lastIndexOf("/") + 1;
        return url.substring(firstIndex, url.length());
    }
}
