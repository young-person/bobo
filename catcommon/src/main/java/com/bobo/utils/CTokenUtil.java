package com.bobo.utils;

import com.bobo.domain.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 生成token规则
 *
 * 需要重写 T对象的 equals hashCode方法
 */
public class CTokenUtil {
    static Logger logger = LoggerFactory.getLogger(CTokenUtil.class);
    private  static String key="10lbfgXgYhubdzzs3W4hgQ4etJhdCfe4MtdfBpm52xzerSDegMJtghg";
    public static <T> String generatorT_Token(T t){
        try {
            return  DESUtil.encrypt(t.toString(), key);
        } catch (Exception e) {
            logger.error("{}",e.getMessage(),e);
        }
        return null;
    }

    public static AuthUser generatorToken_T(String token, AuthUser user){
        try {
            String str = DESUtil.decrypt(token,key);
            String[] ss = str.split("|");
            // TODO 时间搓验证
            Long l2 = System.currentTimeMillis();
            Long l1 = Long.valueOf(ss[2]);

            user.setUsername(ss[0]);
            user.setPassword(ss[1]);

            return user;
        } catch (Exception e) {
            logger.error("解密失败{}",e.getMessage(),e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String name = "2";
        System.out.println(name.toString());

    }

    private static class DESUtil {
        // 算法名称
        public static final String KEY_ALGORITHM = "DESede";
        // 算法名称/加密模式/填充方式
        // DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
        public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";//NoPadding

        public static byte[] GetKeyBytes(String strKey) throws Exception {

            if (null == strKey || strKey.length() < 1)

                throw new Exception("key is null or empty!");

            java.security.MessageDigest alg = java.security.MessageDigest
                    .getInstance("MD5");

            alg.update(strKey.getBytes());

            byte[] bkey = alg.digest();

            // System.out.println("md5key.length=" + bkey.length);

            // System.out.println("md5key=" + bytes2Hex(bkey));

            int start = bkey.length;

            byte[] bkey24 = new byte[24];

            for (int i = 0; i < start; i++) {

                bkey24[i] = bkey[i];

            }

            for (int i = start; i < 24; i++) {// 为了与.net16位key兼容

                bkey24[i] = bkey[i - start];

            }

            // System.out.println("byte24key.length=" + bkey24.length);

            // System.out.println("byte24key=" + bytes2Hex(bkey24));

            return bkey24;

        }

        /** */
        /**
         *
         * 生成密钥key对象
         *
         * @param keyStr
         *            密钥字符串
         * @throws Exception
         */
        private static SecretKey keyGenerator(String keyStr) throws Exception {
            byte input[] = GetKeyBytes(keyStr);// keyStr.getBytes();//HexString2Bytes(keyStr);
            // DESKeySpec desKey = new DESKeySpec(input);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成

            SecretKey securekey = new SecretKeySpec(input, KEY_ALGORITHM);
            // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            // SecretKey securekey = keyFactory.generateSecret(desKey);
            return securekey;
        }

        private static int parse(char c) {
            if (c >= 'a')
                return (c - 'a' + 10) & 0x0f;
            if (c >= 'A')
                return (c - 'A' + 10) & 0x0f;
            return (c - '0') & 0x0f;
        }

        // 从十六进制字符串到字节数组转换
        public static byte[] HexString2Bytes(String hexstr) {
            byte[] b = new byte[hexstr.length() / 2];
            int j = 0;
            for (int i = 0; i < b.length; i++) {
                char c0 = hexstr.charAt(j++);
                char c1 = hexstr.charAt(j++);
                b[i] = (byte) ((parse(c0) << 4) | parse(c1));
            }
            return b;
        }

        public static String bytes2Hex(byte[] src) {
            char[] res = new char[src.length * 2];
            final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            for (int i = 0, j = 0; i < src.length; i++) {
                res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
                res[j++] = hexDigits[src[i] & 0x0f];
            }
            return new String(res);
        }

        /** */
        /**
         * 加密数据
         *
         * @param data
         *            待加密数据
         * @param key
         *            密钥
         * @return 加密后的数据
         */
        public static String encrypt(String data, String key) throws Exception {
            Key deskey = keyGenerator(key);

            return encrypt(data, deskey);
        }

        /** */
        /**
         * 加密数据
         *
         * @param data
         *            待加密数据
         * @param key
         *            密钥
         * @return 加密后的数据
         */
        public static String encrypt(String data, Key key) throws Exception {
            // Key deskey = keyGenerator(key);
            // 实例化Cipher对象，它用于完成实际的加密操作
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecureRandom random = new SecureRandom();
            // 初始化Cipher对象，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key, random);
            byte[] results = cipher.doFinal(data.getBytes("utf-8"));
            // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
            // for (int i = 0; i < results.length; i++) {
            // System.out.print(results[i] + " ");
            // }
            // System.out.println();
            // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
            return bytes2Hex(results);
        }

        /** */
        /**
         * 解密数据
         *
         * @param data
         *            待解密数据
         * @param key
         *            密钥
         * @return 解密后的数据
         */
        public static String decrypt(String data, String key) throws Exception {
            Key deskey = keyGenerator(key);

            // 执行解密操作
            return decrypt(data, deskey);
        }

        /** */
        /**
         * 解密数据
         *
         * @param data
         *            待解密数据
         * @param key
         *            密钥
         * @return 解密后的数据
         */
        public static String decrypt(String data, Key key) throws Exception {

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化Cipher对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 执行解密操作
            return new String(cipher.doFinal(HexString2Bytes(data)),"utf-8");
        }

        public static void main(String[] args) throws Exception {
            String source = "amigoxie";
            System.out.println("原文: " + source);
            String key = "A1B2C3D4E5F60708";
            String encryptData = encrypt(source, key);
            System.out.println("加密后: " + encryptData);
            String decryptData = decrypt(encryptData, key);
            System.out.println("解密后: " + decryptData);
            long bt = System.currentTimeMillis();
            int ts = 100000;
            for (int i = 0; i < ts; i++) {
                encrypt(source, key);
                // System.out.println("加密后: " + encryptData);
                decrypt(encryptData, key);
                // System.out.println("解密后: " + decryptData);

            }
            System.out.println(ts / (System.currentTimeMillis() - bt));
        }
    }
}
