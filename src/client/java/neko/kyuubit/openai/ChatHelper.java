package neko.kyuubit.openai;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import neko.kyuubit.ChatClient;

import java.util.ArrayList;
import java.util.List;

public class ChatHelper {
    public static OpenAiService apiServiceInstance;
    public static ChatCompletionResult serviceResult;
    public static ChatCompletionRequest currentCompletionRequest;
    public static String apiKey = "";
    public static String currentModel = "gpt-3.5-turbo";
    public static int maxTokens = 50;
    public static double temperature = 1.0f;

    /**
     * Returns true if the api service instance is valid
     * @return boolean
     */
    public static boolean isInstanceValid() {
        return apiServiceInstance != null;
    }

    /**
     * Returns true if the completion request is valid
     * @return String
     */
    public static boolean isCompletionRequestValid() {
        return currentCompletionRequest != null;
    }

    /**
     * Returns the chat response is valid
     * @return String
     */
    public static boolean isCompletionResultValid() {
        return serviceResult != null;
    }

    /**
     * Initialises the completion request
     * @param prompt
     */
    public static void initialiseCompletionRequest(String prompt) {
        ChatMessage chatMessage = new ChatMessage("user", prompt);
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(chatMessage);
        currentCompletionRequest = ChatCompletionRequest.builder()
                .model(currentModel)
                .messages(messages)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();
    }

    /**
     * Sets the api key
     * @param theApiKey
     */
    public static void setKey(String theApiKey) {
        apiKey = theApiKey;
    }

    /**
     * Registers the api service instance
     */
    public static void registerApiServiceInstance() {
        apiServiceInstance = OpenAiUtil.getOpenAiServiceInstance(apiKey);
    }

    /**
     * Sends the chat completion request
     */
    public static void sendChatCompletionRequest() {
        if(!isInstanceValid() || !isCompletionRequestValid())
        {
            ChatClient.LOGGER.info(
                    "ChatHelper: sendChatCompletionRequest: " +
                            "apiServiceInstance or currentCompletionRequest is null");
            return;
        }
        serviceResult = apiServiceInstance.createChatCompletion(currentCompletionRequest);
    }

    /**
     * Returns the chat response
     * @param prompt
     * @return String
     */
    public static String getChatResponse(String prompt) {
        if(!isCompletionResultValid()) return "Null response";
        if(serviceResult.getChoices().size() < 1) return "No response";
        return serviceResult.getChoices().get(0).getMessage().getContent()
                .replaceAll("[^a-zA-Z0-9'.#!\\-,\\s]", "").trim();
    }
}
