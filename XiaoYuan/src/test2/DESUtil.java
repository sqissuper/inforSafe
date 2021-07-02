import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES加密 解密算法
 *
 * @author peipei3514
 * @date 2017-8-11 上午10:12:50
 */
public class DESUtil {

    /** 算法名称 */
    public static final String KEY_ALGORITHM = "DES";
    /** 算法名称/加密模式/填充方式 */
    public static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /** 字符编码 ： 字符串转字节或字节转字符串时 一定要加上编码，否则可能出现乱码*/
    private final static String ENCODE = "UTF-8";
    /**
     * 加解密密钥<br/><br/>
     *
     * Creates a DESKeySpec object using the first 8 bytes in key as the key
     * material for the DES key. <br/>The bytes that constitute the DES key are those
     * between key[0] and key[7] inclusive.<br/><br/>
     *
     * 使用前8个字节(bytes)作为DES密钥的关键材料,创建一个DESKeySpec对象。 构成DES密钥的字节是键[0]和键[7]之间的字节。
     */
    private final static String DEFAULT_KEY = "A1B2C3D4E5F60708";

    /** Wrong IV length: must be 8 bytes long */
    private static String DES_IV = "JM23456*";

    /** 获取秘钥对象 */
    private static SecretKey keyGenerator(String keyStr) throws Exception {

        byte input[] = HexStringToBytes(keyStr);
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(input);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(desKey);

        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /** 从十六进制字符串到字节(二进制 )数组转换 */
    private static byte[] HexStringToBytes(String hexstr) {
        byte[] results = new byte[hexstr.length() / 2];

        for (int i = 0; i < results.length; i++) {
            char c0 = hexstr.charAt(i * 2 + 0);
            char c1 = hexstr.charAt(i * 2 + 1);
            results[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }

        return results;
    }

    /** 加密数据 */
    public static String encrypt(String data) throws Exception {
        // 获取秘钥对象
        Key deskey = keyGenerator(DEFAULT_KEY);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 生成初始化向量
        IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes("UTF-8"));
        // 用密钥初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
        // 执行加密操作
        byte[] results = cipher.doFinal(data.getBytes(ENCODE));

        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }

    /** 解密数据 */
    public static String decrypt(String data) throws Exception {
        // 获取秘钥对象
        Key deskey = keyGenerator(DEFAULT_KEY);
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 生成初始化向量
        IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes("UTF-8"));
        // 初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey, iv);
        // Base64解码
        byte[] results = Base64.decodeBase64(data);
        // 执行解密操作
        results = cipher.doFinal(results);
        // 变成字符串
        return new String(results, ENCODE);
    }

    public static void main(String[] args) throws Exception {
        String source = "wojiaoxiaoyuan";
        System.out.println("原文: " + source);
        String encryptData = encrypt(source);
        System.out.println("加密后: " + encryptData);
        String decryptData = decrypt(encryptData);
        System.out.println("解密后: " + decryptData);
    }
}
