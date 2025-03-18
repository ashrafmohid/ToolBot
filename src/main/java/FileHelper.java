import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileHelper {
    // Writing to file
    public static void writeToFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("Data successfully written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Reading from file
    public static String readFromFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }
    // Writing to Base64 file
    public static void writeToFile64(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            String encodedContent = Base64.getEncoder().encodeToString(data.getBytes());
            writer.write(encodedContent);
            System.out.println("Base64-encoded data written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file " + e.getMessage());
        }
    }
    // Decode and read Base64 file
    public static String readFromFile64(String filePath) {
        try {
            String encodedData = Files.readString(Paths.get(filePath));
            return new String(Base64.getDecoder().decode(encodedData));
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }
}
