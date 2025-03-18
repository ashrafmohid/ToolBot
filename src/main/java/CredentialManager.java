import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class CredentialManager {
    private static final String ALGORITHM = "AES";
    private static final String CREDENTIALS_FILE = "credentials.enc";
    private static final String SECRET_KEY_FILE = "secret.key";

    // Encrypts and saves SSH credentials to a file
    public static void saveCredentials(String host, String username, String password, String privateKeyPath) {
        try {
            SecretKey secretKey = generateOrLoadKey();
            // Encrypt returns Base64
            String encryptedData = encrypt(host + ":" + username + ":" +
                    (password != null ? password : "") + ":" +
                    (privateKeyPath != null ? privateKeyPath : ""), secretKey);
            FileHelper.writeToFile(CREDENTIALS_FILE, encryptedData);
            System.out.println("Credentials securely stored.");
        } catch (Exception e) {
            System.err.println("Failed to save credentials: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String[] loadCredentials() {
        try {
            SecretKey secretKey = generateOrLoadKey();
            String decryptedData = decrypt(FileHelper.readFromFile(CREDENTIALS_FILE), secretKey);
            System.out.println("Credentials loaded.");
            return decryptedData.split(":");
        } catch (Exception e) {
            System.err.println("Failed to load credentials: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Generate or locate AES key
    private static SecretKey generateOrLoadKey() throws Exception {
        File keyFile = new File(SECRET_KEY_FILE);
        if (keyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
                String encodedKey = reader.readLine();
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                return new SecretKeySpec(decodedKey, ALGORITHM);
            }
        }
        // Generate AES 256-bit size
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        // Save key base64
        try(FileWriter writer = new FileWriter(SECRET_KEY_FILE)) {
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            writer.write(encodedKey);
        }

        return secretKey;
    }

    // Encrypts a string using AES
    private static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    // Decrypts a string using AES
    private static String decrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
    }
}