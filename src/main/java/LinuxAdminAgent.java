import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

import java.util.*;
import java.util.regex.*;


public class LinuxAdminAgent {
    static String MODEL_NAME = "llama3.2:3b";
    static String BASE_URL = "http://localhost:11434";

    ChatLanguageModel model;
    PromptTemplate promptTemplate;
    Assistant assistant;
    Pattern pattern;

    // Returns a structured output
    interface Assistant {
        String chat(String task);
    }

    public LinuxAdminAgent() {
        // Initialize Ollama llm
        model = OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .temperature(0.0)
                .build();

        // Create a prompt template for generating Linux commands
        promptTemplate = PromptTemplate.from("Generate a Linux command to: {{it}}");
        // Create ai service
        assistant = AiServices.create(Assistant.class, model);
        // Create regex
        pattern = Pattern.compile("```bash\\n(.*?)\\n```", Pattern.DOTALL);
    }

    public void printCommand(String prompt) {
        String answer = assistant.chat(prompt);
        System.out.println(answer);
    }

    public List<String> extractCommand(String response) {
        Matcher matcher = pattern.matcher(response);
        List<String> commands = new ArrayList<>();
        while (matcher.find()) {
            commands.add(matcher.group(1).trim());
        }
        return commands;
    }

    public String generateCommandFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        // Prompt user
        System.out.print("Enter the task you want to perform (e.g., 'delete all files starting with the letter 'd')");
        String userTask = scanner.nextLine();
        Prompt prompt = promptTemplate.apply(userTask);
        String response = model.chat(prompt.text());
        String command = "";

        // Extract command
        List<String> commands = extractCommand(response);
        int choice = -1;

        // Ask user to pick command if more than one
        if (commands.size() == 1) {
            command = commands.getFirst();
            System.out.println("Generated command: " + command);

        } else {
            System.out.println("Generated commands (choose one): ");
            for (int i = 0; i < commands.size(); i++) {
                System.out.println((i + 1) + ": " + commands.get(i));
            }
            while (true) {
                try {
                    choice = scanner.nextInt();
                    if (choice > 0 && choice <= commands.size()) {
                        command = commands.get(choice - 1);
                        scanner.close();
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and " + commands.size());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
        }
        return command;
    }
}