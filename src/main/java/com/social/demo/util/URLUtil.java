package com.social.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author 杨世博
 * @date 2024/4/7 21:00
 * @description URLUtil
 */
@Component
@Slf4j
public class URLUtil implements InitializingBean {

    private String ip;

    @Value("${server.address}")
    public void setAddress(String address) {
        ip = address;
    }

    private String port;
    @Value("${server.port}")
    public void setPort(String port) {
        this.port = port;
    }

    private String URL_PRF;

    public String getUrl(String url) throws UnknownHostException {
        return URL_PRF + url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("ip:{}",ip);
        if (ip != null && ip.equals("0.0.0.0")){
            URL_PRF =  "http://" + Objects.requireNonNull(getLocalHostExactAddress()).getHostAddress() + ":" + port;
        }else {
            URL_PRF =  "http://" + ip + ":" + port;
        }
        log.info("URL_PRF load success :{}.", URL_PRF);
    }

    public static InetAddress getLocalHostExactAddress() {
        try {
            InetAddress candidateAddress = null;

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = networkInterfaces.nextElement();
                // 该网卡接口下的ip会有多个，也需要一个个的遍历，找到自己所需要的
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    // 排除loopback回环类型地址（不管是IPv4还是IPv6 只要是回环地址都会返回true）
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了 就是我们要找的
                            // ~~~~~~~~~~~~~绝大部分情况下都会在此处返回你的ip地址值~~~~~~~~~~~~~
                            return inetAddr;
                        }

                        // 若不是site-local地址 那就记录下该地址当作候选
                        if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }

                    }
                }
            }

            // 如果出去loopback回环地之外无其它地址了，那就回退到原始方案吧
            return candidateAddress == null ? InetAddress.getLocalHost() : candidateAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

