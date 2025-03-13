import java.util.Map;

public class App {
    public static void main(String[] args) {
        if (args.length == 4) {
            try {
                // Save
                CredentialManager.saveEncryptedCredentials(args[0], args[1], args[2], args[3]);
                // Retrieve
                Map<String, String> credentials = CredentialManager.getDecryptedCredentials();
                System.out.println("Decrypted Credentials:");
                System.out.println("Hostname " + credentials.get("hostname"));
                System.out.println("Username: " + credentials.get("username"));
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        LinuxAdminAgent laa = new LinuxAdminAgent();
        laa.generateCommandFromUserInput();
    }
}
