package org.bana.common.util.secure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class RSAHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RSAHelper.class);

    private static RSAHelper instance = null;

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
    public static final int KEYBIT = 2048;
    public static final int RESERVEBYTES = 11;

    private static PrivateKey localPrivKey;
    private static PublicKey peerPubKey;

    private RSAHelper() {
    }

    public static RSAHelper getInstance() {
        if (instance == null) {
            // 双重检查加锁，只有在第一次实例化时，才启用同步机制，提高了性能。
            synchronized (RSAHelper.class) {
                if (instance == null) {
                    String localPrivKeyBase64Str = "";// CmbcProps.getInstance().getConfigItem("OTHERLOCALPRIVKEY", "");
                    String peerPubKeyBase64Str = "";// CmbcProps.getInstance().getConfigItem("OTHERPEERPUBKEY", "");

                    try {
                        /**
                         * 初始化自己的私钥,对方的公钥以及密钥长度. localPrivKeyBase64Str Base64编码的私钥,PKCS#8编码.
                         * (去掉pem文件中的头尾标识) peerPubKeyBase64Str Base64编码的公钥. (去掉pem文件中的头尾标识)
                         */
                        BASE64Decoder base64Decoder = new BASE64Decoder();

                        byte[] buffer1 = base64Decoder.decodeBuffer(localPrivKeyBase64Str);
                        PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(buffer1);
                        KeyFactory keyFactory1 = KeyFactory.getInstance(KEY_ALGORITHM);
                        localPrivKey = keyFactory1.generatePrivate(keySpec1);

                        byte[] buffer2 = base64Decoder.decodeBuffer(peerPubKeyBase64Str);
                        KeyFactory keyFactory2 = KeyFactory.getInstance(KEY_ALGORITHM);
                        X509EncodedKeySpec keySpec2 = new X509EncodedKeySpec(buffer2);
                        peerPubKey = keyFactory2.generatePublic(keySpec2);

                        /**
                         * 实例化对象
                         */
                        instance = new RSAHelper();
                    } catch (NoSuchAlgorithmException e) {
                        LOG.debug("无此算法");
                    } catch (InvalidKeySpecException e) {
                        LOG.debug("公钥非法");
                    } catch (IOException e) {
                        LOG.debug("公钥数据内容读取错误");
                    } catch (NullPointerException e) {
                        LOG.debug("公钥数据为空");
                    } catch (Exception e) {
                        LOG.error("初始化密钥失败", e);
                    }
                }
            }
        }
        return instance;
    }

    /**
     * RAS加密
     *
     * @param plainBytes 待加密信息
     * @return byte[]
     * @throws Exception
     */
    public byte[] encryptRSA(byte[] plainBytes, boolean useBase64Code, String charset) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (plainBytes.length / encryptBlock);
        if ((plainBytes.length % encryptBlock) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }
        // 输出buffer, 大小为nBlock个decryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlock);
        cipher.init(Cipher.ENCRYPT_MODE, peerPubKey);
        // cryptedBase64Str =
        // Base64.encodeBase64String(cipher.doFinal(plaintext.getBytes()));
        // 分段加密
        for (int offset = 0; offset < plainBytes.length; offset += encryptBlock) {
            // block大小: encryptBlock 或 剩余字节数
            int inputLen = (plainBytes.length - offset);
            if (inputLen > encryptBlock) {
                inputLen = encryptBlock;
            }
            // 得到分段加密结果
            byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(encryptedBlock);
        }
        // 如果是Base64编码，则返回Base64编码后的数组
        if (useBase64Code) {
            return encodeBase64String(outbuf.toByteArray()).getBytes(charset);
        } else {
            return outbuf.toByteArray(); // ciphertext
        }
    }

    /**
     * RSA解密
     *
     * @param cryptedBytes 待解密信息
     * @return byte[]
     * @throws Exception
     */
    public byte[] decryptRSA(byte[] cryptedBytes, boolean useBase64Code, String charset) throws Exception {
        byte[] data;

        // 如果是Base64编码的话，则要Base64解码
        if (useBase64Code) {
            data = decodeBase64(new String(cryptedBytes, charset));
        } else {
            data = cryptedBytes;
        }

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段解密的block数 (理论上应该能整除)
        int nBlock = (data.length / decryptBlock);
        // 输出buffer, , 大小为nBlock个encryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * encryptBlock);
        cipher.init(Cipher.DECRYPT_MODE, localPrivKey);
        // plaintext = new
        // String(cipher.doFinal(Base64.decodeBase64(cryptedBase64Str)));
        // 分段解密
        for (int offset = 0; offset < data.length; offset += decryptBlock) {
            // block大小: decryptBlock 或 剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > decryptBlock) {
                inputLen = decryptBlock;
            }

            // 得到分段解密结果
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(decryptedBlock);
        }
        outbuf.flush();
        outbuf.close();
        return outbuf.toByteArray();
    }

    /**
     * RSA签名
     *
     * @param plainBytes 需要签名的信息
     * @return byte[]
     * @throws Exception
     */
    public byte[] signRSA(byte[] plainBytes, boolean useBase64Code, String charset) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (useBase64Code) {
            return encodeBase64String(signature.sign()).getBytes(charset);
        } else {
            return signature.sign();
        }
    }

    /**
     * 验签操作
     *
     * @param plainBytes 需要验签的信息
     * @param signBytes  签名信息
     * @return boolean
     */
    public boolean verifyRSA(byte[] plainBytes, byte[] signBytes, boolean useBase64Code, String charset)
            throws Exception {
        boolean isValid;
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(peerPubKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对验签的数组以Base64解码
        if (useBase64Code) {
            isValid = signature.verify(decodeBase64(new String(signBytes, charset)));
        } else {
            isValid = signature.verify(signBytes);
        }
        return isValid;
    }

    private String encodeBase64String(byte[] dataBytes) {
        return Base64.encodeBase64(dataBytes) == null ? null
                : new String(Base64.encodeBase64(dataBytes), Charset.forName("UTF-8"));

    }

    private byte[] decodeBase64(String base64String) {
        return Base64.decodeBase64(base64String == null ? null : base64String.getBytes(Charset.forName("UTF-8")));
    }

    public static void main(String[] args) throws Exception {

        String plaintext = "你好，测试";

        System.out.println("=====> init <=====");
        RSAHelper cipher = RSAHelper.getInstance();

        System.out.println("=====> sign & verify <=====");

        // 签名
        byte[] signBytes = cipher.signRSA(plaintext.getBytes("UTF-8"), false, "UTF-8");

        // 验证签名
        boolean isValid = cipher.verifyRSA(plaintext.getBytes("UTF-8"), signBytes, false, "UTF-8");
        System.out.println("isValid: " + isValid);

        // 加密和解密
        System.out.println("=====> encrypt & decrypt <=====");
        // 对明文加密
        byte[] cryptedBytes = cipher.encryptRSA(plaintext.getBytes("UTF-8"), false, "UTF-8");

        System.out.println("");// Convertor.byte2HexString(cryptedBytes));

        // 对密文解密
        byte[] decryptedBytes = cipher.decryptRSA(cryptedBytes, false, "UTF-8");
        System.out.println("decrypted: " + new String(decryptedBytes, "UTF-8"));
    }
}