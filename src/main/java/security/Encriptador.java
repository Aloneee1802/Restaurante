package security;

import java.security.*;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.*;
import org.apache.commons.codec.binary.Base64;

public class Encriptador {

    // https://medium.com/el-acordeon-del-programador/encriptaci%C3%B3n-aes-en-java-ebb81ddf82b
    // https://ciberseguridad.com/servicios/algoritmos-cifrado/
    private final static String secretKey = "programando_software123";
    private final static String algorithm = "SHA"; // MD5    SHA
    private final static String typeAlgorithm = "AES"; // DESede  AES  Blowfish

    public static String Cifrar(String texto) {
        String base64EncryptedString = "";

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, typeAlgorithm);
            Cipher cipher = Cipher.getInstance(typeAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
            base64EncryptedString = null;
        }
        return base64EncryptedString;
    }

    public static String Descifrar(String textoEncriptado) {
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, typeAlgorithm);

            Cipher decipher = Cipher.getInstance(typeAlgorithm);
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            base64EncryptedString = null;
        }
        return base64EncryptedString;
    }

}
