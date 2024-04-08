package com.social.demo.util;

public class OsUtils {

    public final static String LINUX_FILE_SPLIT = "/";

    public final static String WINDOWS_FILE_SPLIT = "\\";

    public static boolean isLinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("linux");
    }
    public static boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows");
    }
    public static void main(String[] args) {
        System.out.println(isWindows());
    }
}
