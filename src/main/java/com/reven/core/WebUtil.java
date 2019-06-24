package com.reven.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: WebUtil
 * @author reven
 */
public class WebUtil {
    public static final String LOGGER_RETURN = "_logger_return";

    private WebUtil() {
    }

    /**
     * 获取客户端ip地址
     * 
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.trim() == "" || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取服务器ip地址
     * 
     * @return
     */
    public static String getServerIp() {
        InetAddress address;
        String serverIpAddress = null;
        try {
            // 获取的是本地的IP地址
            address = InetAddress.getLocalHost();
            serverIpAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
        }
        return serverIpAddress;
    }

    /**
     * 判断是否为ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}