package signature;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


class Signature {
    public BigInteger p,q,g;
    public BigInteger x,y;

    public BigInteger _randomInZq(){
        BigInteger r= null;
        do {
            System.out.print(".");
            r = new BigInteger(160, new SecureRandom());
        }while(r.compareTo(q) >=0);
        System.out.print(".");
        return r;
    }

    public BigInteger _hashInZq(byte m[]){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(m);
            byte b[] = new byte[17];
            System.arraycopy(md.digest(), 0, b, 1, 16);
            return new BigInteger(b);
        }catch (NoSuchAlgorithmException e){
            System.out.print("This cannot happen!");
        }
        return null;
    }

    public void initKeys(){
        q = new BigInteger(160, 100, new SecureRandom());
        do {
            BigInteger t = new BigInteger(512, new SecureRandom());
            p = t.multiply(q).add(BigInteger.ONE);
            System.out.println("~");
        }while(!p.isProbablePrime(100));
        BigInteger h = _randomInZq();
        g = h.modPow(p.subtract(BigInteger.ONE).divide(q), p);
        x = _randomInZq();
        y = g.modPow(x, p);
        System.out.println("p : " + p);
        System.out.println("q : " + q);
        System.out.println("g : " + g);
        System.out.println("x : " + x);
        System.out.println("y : " + y);
    }

    public BigInteger[] signature(byte m[]){
        BigInteger k = _randomInZq();
        BigInteger sig[] = new BigInteger[2];
        sig[0] = g.modPow(k, p).mod(q);
        sig[1] = _hashInZq(m).add(x.multiply(sig[0])).mod(q)
                .multiply(k.modInverse(q)).mod(q);
        return sig;
    }
    public boolean verify(byte m[], BigInteger sig[]){
        BigInteger w = sig[1].modInverse(q);
        BigInteger u1 = _hashInZq(m).multiply(w).mod(q);
        BigInteger u2 = sig[0].multiply(w).mod(q);
        BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
        System.out.println("v = " + v);
        System.out.println("r = " + sig[0]);
        return v.compareTo(sig[0]) == 0;
    }

    public static void main(String args[]){
        Signature dsa = new Signature();
        dsa.initKeys();
        String message = "我爱你";
        System.out.println(message);
        BigInteger sig[] = dsa.signature(message.getBytes());
        System.out.println("DSASignture verifies result:" + dsa.verify(message.getBytes(),sig) );
    }

}
