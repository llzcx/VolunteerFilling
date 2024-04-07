package com.social.demo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 杨世博
 * @date 2024/4/7 21:00
 * @description URLUtil
 */
public class URLUtil {

    public static String getPictureUrl(HttpServletRequest request) throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress() + ":" + request.getServletPath();
    }
}
