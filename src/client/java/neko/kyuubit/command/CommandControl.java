package neko.kyuubit.command;

import com.theokanning.openai.OpenAiHttpException;
import neko.kyuubit.ChatClient;
import neko.kyuubit.openai.ChatHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class CommandControl implements Runnable{
    public static CommandControl INSTANCE = new CommandControl();
    private CommandControl() {}

    /**
     * Singleton pattern
     * @return CommandControl
     */
    public static CommandControl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CommandControl();
        }
        return INSTANCE;
    }

    /**
     * Full command message input
     */
    public static String commandMessage = "";

    /**
     * Response message to be sent to the player
     */
    private static String responseMessage = "";

    /**
     * Starts the command processing on separate thread
     */
    @Override
    public void run() {
        if(CommandUtil.isCommand(commandMessage)) {
            try {
                processCommand(commandMessage);
            } catch (OpenAiHttpException e) {
                ChatClient.LOGGER.error(e.getMessage());
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(e.getMessage()));
                return;
            }

            ChatClient.LOGGER.info(getResponseMessage());
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(Text.of(
                            "[ChatGPT] " +
                            getResponseMessage())
                    );
        }
    }

    /**
     * Sets the command message to be processed
     * @param message
     */
    public static void setMessageToSend(String message) {
        commandMessage = message;
    }

    /**
     * Gets the response message to be sent to the player
     * @return String
     */
    public static String getResponseMessage() {
        return responseMessage;
    }

    /**
     * Handles Command cases and calls the appropriate method
     * @param message
     */
    public static void processCommand(String message) {
        switch (CommandUtil.getCommandType(message)) {
            case REGISTER:
                responseMessage = processRegisterCommand(message);
                return;
            case CHAT:
                responseMessage = processChatCommand(message);
                return;
            case SET_API:
                responseMessage = processSetCommand(message);
                return;
            case NONE:
                responseMessage = "Invalid command";
                return;
            default:
                responseMessage = "Invalid command";
        }
    }

    /**
     * Processes the register command
     * This will set the key and try to register the api service
     * @param message
     * @return String
     */
    public static String processRegisterCommand(String message) {
        String[] command = message.split(" ");
        if(command.length == 2) {
            ChatHelper.setKey(message.split(" ")[1]);
            ChatHelper.registerApiServiceInstance();
            ChatClient.LOGGER.info("Setting api key to " + message.split(" ")[1]);
            return "API key set";
        }
        return "Register failed - No key provided";
    }

    /**
     * Processes the chat command
     * This will initialise the message
     * and then send the request to the api service and then returns the response
     * @param message
     * @return String
     */
    public static String processChatCommand(String message) {
        String[] command = message.split(" ");
        if(command.length > 1) {
            ChatHelper.initialiseCompletionRequest(message.split(" ",2)[1]);
            ChatHelper.sendChatCompletionRequest();
            return ChatHelper.getChatResponse(message.split(" ")[1]);
        }
        return "No response";
    }

    /**
     * Processes the set command
     * This will set the model, max tokens or temperature
     * @param message
     * @return String
     */
    public static String processSetCommand(String message) {
        String[] command = message.split(" ");
        if(command.length < 3) return "Invalid command";
        switch (command[1]) {
            case "model":
                ChatHelper.currentModel = command[2];
                return "Model set to " + command[2];
            case "tokens":
                try {
                    ChatHelper.maxTokens = Integer.parseInt(command[2]);
                } catch (NumberFormatException e) {
                    return "Invalid max tokens - Must be an integer";
                }
                return "Max tokens set to " + command[2];
            case "temp":
                try {
                    ChatHelper.temperature = Double.parseDouble(command[2]);
                } catch (NumberFormatException e) {
                    return "Invalid temperature - Must be a double";
                }
                return "Temperature set to " + command[2];
            default:
                return "try \"set model <model> <modelName>\" or" +
                        " \"set tokens <tokens> <maxTokens>\" or" +
                        " \"set temperature <temperature>\"";
        }
    }
}
