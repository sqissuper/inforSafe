import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Scanner;


public class RSASignature {
//    private static String src="我爱你";
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()) {
            String src = sc.nextLine();
            rsa(src);
        }

    }
    public static void rsa(String src){
        try {
            /*
             * 初始化签名
             */
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey=(RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey=(RSAPrivateKey) keyPair.getPrivate();
            /*
             * 执行签名
             */
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature=Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result=signature.sign();
            System.out.println("jdk rsa sign:" + Arrays.toString(result) );
            /*
             * 验证签名
             */
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory=KeyFactory.getInstance("RSA");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
            signature=Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool=signature.verify(result);
            System.out.println("rsa verify is:"+bool);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}