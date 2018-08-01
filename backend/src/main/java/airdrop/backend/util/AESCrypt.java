package airdrop.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class AESCrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESCrypt.class);
    private static String signature = "data-airdrop";

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public String encrypt(String key, String signature, String target) throws Exception{
        String genMD5 = getKeyAndIv(key, signature);
        System.out.println(genMD5.substring(0, 16));
        System.out.println(genMD5.substring(16, 32));
        SecretKey aesKey = new SecretKeySpec(genMD5.substring(0, 16).getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(genMD5.substring(16, 32).getBytes());

        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);

        byte[] encryptBytes = cipher.doFinal(target.getBytes());

        return new String(Base64.getEncoder().encode(encryptBytes));
    }

    public String decrypt(String key, String signature, String target) throws Exception {
        String genMD5 = getKeyAndIv(key, signature);
        SecretKey aesKey = new SecretKeySpec(genMD5.substring(0, 16).getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(genMD5.substring(16, 32).getBytes());

        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

        byte[] decryptBytes = cipher.doFinal(Base64.getMimeDecoder().decode(target));
        return new String(decryptBytes);
    }

    public static String getEncryptedValue(String key, String target) {
        AESCrypt aesCrypt = new AESCrypt();
        String result = null;
        try {
            result = aesCrypt.encrypt(key, signature, target);
            LOGGER.debug("getEncryptedValue success - key: " + key);
        } catch (Exception e) {
            LOGGER.error("getEncryptedValue failed - key: " + key, e);
            e.printStackTrace();
        }
        return result;
    }

    public static String getDecryptedValue(String key, String target) {
        AESCrypt aesCrypt = new AESCrypt();
        String result = null;
        try {
            result = aesCrypt.decrypt(key, signature, target);
            LOGGER.debug("getDecryptedValue success - key: " + key );
        } catch (Exception e) {
            LOGGER.error("getDecryptedValue failed - key: " + key , e);
            e.printStackTrace();
        }
        return result;
    }

    public String getKeyAndIv(String key, String signature) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update((key + signature).getBytes());
        byte[] ret = messageDigest.digest();
        return new String(encodeHex(ret, DIGITS_LOWER));
    }

    public char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int len = data.length;
        final char[] out = new char[len << 1];
        // two characters form the hex value
        for(int i = 0, j = 0; i < len; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
}
