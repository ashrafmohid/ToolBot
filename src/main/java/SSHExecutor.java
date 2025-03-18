import com.jcraft.jsch.*;

import java.io.InputStream;

public class SSHExecutor {
    private String host, username, password;
    public SSHExecutor(CredentialManager credentialManager) {
        String[] creds = credentialManager.loadCredentials();
        this.host = creds[0];
        this.username = creds[1];
        this.password = creds[2];
    }
    public String executeCommand(String command) {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setErrStream(System.err);

            InputStream input = channel.getInputStream();
            channel.connect();

            String result = new String(input.readAllBytes());
            channel.disconnect();
            session.disconnect();
            return result;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
