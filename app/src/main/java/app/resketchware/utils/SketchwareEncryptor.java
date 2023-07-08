package app.resketchware.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SketchwareEncryptor {
    private static final byte[] encryptKey = "sketchwaresecure".getBytes(StandardCharsets.UTF_8);

    /**
     * Decryption of the incoming [byteArray] by [encryptKey].
     * @return [byte[]] of decrypted [byteArray].
     */
    public static byte[] decrypt(byte[] byteArray) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey, "AES"), new IvParameterSpec(encryptKey));
        return cipher.doFinal(byteArray);
    }

    /**
     * Encryption of the incoming [byteArray] by [encryptKey].
     * @return [byte[]] of encrypted [byteArray].
     */
    public static byte[] encrypt(byte[] byteArray) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey, "AES"), new IvParameterSpec(encryptKey));
        return cipher.doFinal(byteArray);
    }

    public static byte[] decrypt(String string) throws Exception {
        return decrypt(string.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encrypt(String string) throws Exception {
        return encrypt(string.getBytes(StandardCharsets.UTF_8));
    }
}