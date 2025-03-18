import java.util.Map;

public class App {
    public static void main(String[] args) {
        if (args.length == 4) {
            try {
                // Save
                CredentialManager.saveCredentials(args[0], args[1], args[2], args[3]);
                // Retrieve
                String[] credentials = CredentialManager.loadCredentials();
                System.out.println("Decrypted Credentials:");
                System.out.println("Hostname " + credentials[0]);
                System.out.println("Username: " + credentials[1]);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        LinuxAdminAgent laa = new LinuxAdminAgent();
        laa.generateCommandFromUserInput();
    }
}
