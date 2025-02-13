import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class LinuxAdminAgent {
    static String MODEL_NAME = "llama3.2:3b";
    static String BASE_URL = "http://localhost:11434";

    ChatLanguageModel model;
    PromptTemplate promptTemplate;
    Assistant assistant;

    // Returns a structured output
    interface Assistant {
        String chat(String task);
    }
    interface Prompt {
        Prompt toPrompt(PromptTemplate promptTemplate);
    }
    public LinuxAdminAgent() {
        // Initialize Ollama llm
        model = OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .temperature(0.0)
                .build();

        // Create a prompt template for generating Linux commands
        promptTemplate = PromptTemplate.from("Generate a Linux command to: {{task}}");
        Prompt prompt = Prompt.toPrompt(promptTemplate);
        // Create ai service
        assistant = AiServices.create(Assistant.class, model);
    }

    public void printAnswer() {
        String answer = assistant.chat("Generate a Linux command to: start docker with a program");
        System.out.println(answer);
    }
}
