package com.reddate.chinatrust.utils;

import com.reddate.ddc.util.crypto.ISignHandle;
import com.reddate.ddc.util.crypto.SignException;
import com.reddate.ddc.util.crypto.SignUtil;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.Keys;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;



public class Secp256K1Util implements ISignHandle {
    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();

    private static final String BEGIN_EC_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    private static final String END_EC_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    private static final String BEGIN_EC_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    private static final String END_EC_PUBLIC_KEY = "-----END PUBLIC KEY-----";


    private String priKey;
    private String pubKey;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private ECKeyPair keyPair;
    private EncryptType encryptType;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public Secp256K1Util() throws Exception {
        this.encryptType = new EncryptType(0);

        //KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");


        keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();

        this.priKey = this.savePrivateKeyByPEM(this.privateKey);
        this.pubKey = this.savePublicKeyByPEM(this.publicKey);

        initECKeyPair();

    }

    public Secp256K1Util(String pri, String pub) throws Exception {
        this.encryptType = new EncryptType(0);

        this.priKey = pri;
        this.pubKey = pub;

        this.privateKey = SignUtil.isEmpty(this.priKey)?null:this.loadPrivateKey(priKey);
        this.publicKey =  SignUtil.isEmpty(this.pubKey)?null:this.loadPublicKey(pubKey);

        initECKeyPair();
    }

    //使用SHA256withECDSA的签名方式，非ETh或者FISCO的签名，只用于门户秘钥上传时的验证
    @Override
    public byte[] sign(byte[] data) throws Exception {
        if (this.privateKey == null) {
            throw new SignException("private key cannot be empty");
        }

        Signature signer = Signature.getInstance("SHA256withECDSA");
        signer.initSign(this.privateKey);
        signer.update(data);
        return  signer.sign();
    }

    @Override
    public boolean verify(byte[] data, byte[] mac) throws Exception {
        if (this.publicKey == null) {
            throw new SignException("public key cannot be empty");
        }
        Signature signer = Signature.getInstance("SHA256withECDSA");
        signer.initVerify(this.publicKey);
        signer.update(data);
        return signer.verify(mac);
    }


    public byte[] hash(byte[] data){
        //Keccak256 hash
        //Keccak256 sha =new Keccak256()
        //org.fisco.bcos.web3j.abi.datatypes.Address

        Keccak.DigestKeccak kecc = new Keccak.Digest256();
        kecc.update(data, 0, data.length);
        return kecc.digest();

    }

    @Override
    public String getAddress() {
        //去掉 0x
        String publicKeyNoPrefix = SignUtil.cleanHexPrefix(this.getHexedPublicKey());
        String publicKeyHash =  Hex.toHexString(hash(Hex.decode(publicKeyNoPrefix)));
        // right most 160 bits
        return "0x" + publicKeyHash.substring(publicKeyHash.length() - 40);

    }

    public int getEncryptType() {
        return encryptType.getEncryptType();
    }

    public ECKeyPair getKeyPair() {
        return this.keyPair;
    }

    private void initECKeyPair(){
        BCECPrivateKey privateKey = (BCECPrivateKey)this.privateKey;
        BCECPublicKey publicKey = (BCECPublicKey)this.publicKey;
        BigInteger privateKeyValue = privateKey.getD();
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
        BigInteger publicKeyValue = new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));

        ECKeyPair ecKeyPair = new ECKeyPair(privateKeyValue, publicKeyValue);
        this.keyPair = ecKeyPair;
    }

    public static void main(String[] args) throws Exception{

        /*String ddcFormatPrivateKey = "MEcCAQAwEAYHKoZIzj0CAQYFK4EEAAoEMDAuAgEBBCCF0ynS6PO6VSsC/9mhebfkM1GsXQRpB7LSE6u5SUw7hKAHBgUrgQQACg==";
        String ddcFormatPublicKey = "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEM1W09TrTmivL3JCDuhr8LSKfC64e0wzvLqNFl+u3Yn+lpP324XRAU39BZg3Ru/SaFatzm5vmyGe1h3KWO+B9kQ==";*/
        String consumer = "-----BEGIN PRIVATE KEY-----\n" +
                "MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQgEWL4mAyD0V4cKcZ+RXS+\n" +
                "Y0b/Wt3WYOuHNynQQwCaGPGhRANCAAQojPfT83xRrijQNk6CXq1/w61/ZU5GC6CE\n" +
                "BTq8PEeUyqngCJCN0gkfRU1IEmusAsIGJb3ff2cQRvYTBqcismv1\n" +
                "-----END PRIVATE KEY-----\n";


        String cPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEjRHf7EbOKvUwRJW/kn4N6Vmf++n/gBu0\n" +
                "WEBUzovj+TAxwvgB26tCfoqk9X2gTdjwwKh6o/hvtx66EDB9GlzgTA==\n" +
                "-----END PUBLIC KEY-----";

        /*BCECPrivateKey privateKey = (BCECPrivateKey)loadPrivateKey(consumer);
        BCECPublicKey publicKey = (BCECPublicKey)loadPublicKey(cPublicKey);
        BigInteger privateKeyValue = privateKey.getD();
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);

        BigInteger publicKeyValue = new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
        System.out.println(privateKeyValue);
        System.out.println(publicKeyValue);
        System.out.println("0x" + Keys.getAddress(publicKeyValue));*/

        /*System.out.println(convertDccPriKey2DidPriKey(consumer));
        System.out.println(convertDccPubKey2DidPubKey(cPublicKey));
        System.out.println(convertDdcPubKey2Address(cPublicKey));*/

        String ddcFormatPubKey = "1539692229885558034439469495470931240897268830172903249805100468085817069083087002752573520690367811491923877793302778697202989555203720374331542623326503";
        System.out.println(convertDidPubKey2Address(ddcFormatPubKey));
    }

    public static String convertDccPriKey2DidPriKey(String ddcFormatPriKey) throws Exception{

        BCECPrivateKey privateKey = (BCECPrivateKey)loadPrivateKey(ddcFormatPriKey);
        return privateKey.getD().toString();
    }

    public static String convertDccPubKey2DidPubKey(String ddcFormatPubKey) throws Exception{

        BCECPublicKey publicKey = (BCECPublicKey)loadPublicKey(ddcFormatPubKey);
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
        BigInteger publicKeyValue = new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
        return publicKeyValue.toString();
    }

    public static String convertDdcPubKey2Address(String ddcFormatPubKey) throws Exception{

        String didPubKey = convertDccPubKey2DidPubKey(ddcFormatPubKey);
        return "0x" + Keys.getAddress(new BigInteger(didPubKey));
    }

    public static String convertDidPubKey2Address(String didFormatPubKey) throws Exception{

        return "0x" + Keys.getAddress(new BigInteger(didFormatPubKey));
    }

    public String getHexedPublicKey() {

        byte[] publicKeyBytes = ((BCECPublicKey) publicKey).getQ().getEncoded(false);
        BigInteger publicKeyValue = this.keyPair.getPublicKey();
        //new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
        return SignUtil.toHexStringNoPrefixZeroPadded(publicKeyValue, 128);
    }

    public String getHexedPrivateKey() {
        return SignUtil.toHexStringNoPrefixZeroPadded(this.keyPair.getPrivateKey(), 64);
    }

    public String getPrivateKeyPEMString(){
        return this.priKey;
    }

    public String getPublicKeyPEMString(){
        return this.pubKey;
    }

    private static PrivateKey loadPrivateKey(String priKey) throws Exception {
        String privateKeyPEM = priKey.replaceAll(BEGIN_EC_PRIVATE_KEY, "")
                .replaceAll(END_EC_PRIVATE_KEY, "").replace("\r", "").replace("\n", "");
        byte[] asBytes = decoder.decode(privateKeyPEM.replace("\r\n", ""));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(asBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        return keyFactory.generatePrivate(spec);
    }

    private static PublicKey loadPublicKey(String pubKey) throws Exception {
        String strPublicKey = pubKey.replaceAll(BEGIN_EC_PUBLIC_KEY, "")
                .replaceAll(END_EC_PUBLIC_KEY, "").replace("\r", "").replace("\n", "");
        byte[] asBytes = decoder.decode(strPublicKey.replace("\r\n", ""));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(asBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        return keyFactory.generatePublic(spec);
    }

    private String savePrivateKeyByPEM(PrivateKey privateKey) throws Exception {
        return getEncodeString(privateKey.getEncoded(), BEGIN_EC_PRIVATE_KEY, END_EC_PRIVATE_KEY);
    }

    private String getEncodeString(byte[] encoded, String beginEcPrivateKey, String endEcPrivateKey) {
        String content = encoder.encodeToString(encoded);
        StringBuilder sb = new StringBuilder();
        sb.append(beginEcPrivateKey);
        sb.append("\r\n");
        int i = 0;
        for (; i<(content.length() - (content.length() % 64)); i+=64) {
            sb.append(content.substring(i, i + 64));
            sb.append("\r\n");
        }
        //System.out.println();
       //System.out.println(i);
        if (i!=content.length()) {
            sb.append(content.substring(i));
            sb.append("\r\n");
        }
        sb.append(endEcPrivateKey);
        return sb.toString();
    }

    private String savePublicKeyByPEM(PublicKey publicKey) throws Exception {
        return getEncodeString(publicKey.getEncoded(), BEGIN_EC_PUBLIC_KEY, END_EC_PUBLIC_KEY);
    }
}
