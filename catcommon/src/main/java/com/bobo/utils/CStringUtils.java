package com.bobo.utils;

import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.OperatingSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class CStringUtils {

    public static String conventFromGzip(String str,String chartset) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in;
        GZIPInputStream gunzip = null;

        in = new ByteArrayInputStream(str.getBytes(chartset));
        gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[1024];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }

        return out.toString();
    }

    /**
     * gzip字符串解压
     * @param str
     * @return
     * @throws IOException
     */
    public static String conventFromGzip(String str) throws IOException {
        return conventFromGzip( str,"ISO-8859-1");
    }

    private static final String WINDOWS = "Windows";

    public static String getLinuxHostIP() throws Exception {
        String localHostIP = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    String ip = ips.nextElement().getHostAddress();
                    if (isIp(ip) && !"127.0.0.1".equals(ip)) {
                        localHostIP = ip;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (localHostIP == null) {
            throw new Exception("get linux ip error. local_ip = null.");
        }
        return localHostIP;
    }

    public static String getWindowsHostIP() throws Exception {
        String localHostIP = null;
        try {
            localHostIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (localHostIP == null) {
            throw new Exception("get windows ip error. local_ip = null.");
        }
        return localHostIP;
    }

    public static String getHostIP() throws Exception {
        OperatingSystem os = OperatingSystem.getInstance();
        String vendorName = os.getVendorName();
        if (vendorName.contains(WINDOWS)) {
            return getWindowsHostIP();
        } else {
            return getLinuxHostIP();
        }

    }

    private static boolean isIp(String ipAddress) {
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ipAddress.matches(regex);
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }
    /**
     * 驼峰转下划线,效率比上面高
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine(String)})
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}
