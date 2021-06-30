/**
 * ClassName:TextRSA
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Author:HP
 * @date:2021/6/30 15:15
 */
import java.util.Map;

public class TextRSA {

    /**
     * A和B进行加密通信时,A要给B发送消息,B首先要生成一对密钥。B自己持有私钥,公钥发送给A。A使用B发送的公钥加密内容，B通过自己的私钥解密内容
     * 若B给A发送消息,B首先使用自己的私钥生成签名并发送给A,A使用B发送的公钥验证签名,验证成功使用公钥解密内容
     */
    public static void main(String[] args) throws Exception {
        try {
            //B生成一对密钥
            Map<String, Object> keyMap = RSA.genKeyPair();
            String publicKey = RSA.getPublicKey(keyMap);
            String privateKey = RSA.getPrivateKey(keyMap);

            //公钥加密——私钥解密(A给B发送消息)
            String sourcePuk = "公钥加密——私钥解密";
            System.out.println("加密前：\n" + sourcePuk);
            //A使用B发送的公钥加密内容
            byte[] encodedDataPuk = RSA.encryptByPublicKey(sourcePuk.getBytes(), publicKey);
            System.out.println("加密后：\n" + new String(encodedDataPuk));
            //B使用自己的私钥解密内容
            byte[] decodedDataPuk = RSA.decryptByPrivateKey(encodedDataPuk, privateKey);
            String targetPuk = new String(decodedDataPuk);
            System.out.println("解密后: \n" + targetPuk);

            //私钥签名——公钥验证签名(B给A发送消息)
            String sourcePrk = "私钥签名——公钥验证签名";
            System.out.println("\n加密前：\r\n" + sourcePrk);
            byte[] encodedDataPrk = RSA.encryptByPrivateKey(sourcePrk.getBytes(), privateKey);
            System.out.println("加密后：\r\n" + new String(encodedDataPrk));
            //B使用自己的私钥生成签名并发送给A
            String sign = RSA.sign(encodedDataPrk, privateKey);
            //A使用B发送的公钥验证签名
            boolean status = RSA.verify(encodedDataPrk, publicKey, sign);
            if(status){
                //验证成功,A使用公钥解密内容
                byte[] decodedDataPrk = RSA.decryptByPublicKey(encodedDataPrk, publicKey);
                String targetPrk = new String(decodedDataPrk);
                System.out.println("解密后: \r\n" + targetPrk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
