package neko.kyuubit.command;

public class CommandUtil {
    /**
     * Returns true if the message is a command
     * @param message
     * @return boolean
     */
    public static boolean isCommand(String message) {
        return message.startsWith("#");
    }

    /**
     * Returns the command type of the message
     * @param message
     * @return CommandType
     */
    public static CommandType getCommandType(String message) {
        String[] split = message.split(" ");
        if(split[0].equals("#register")) {
            return CommandType.REGISTER;
        }
        if(split[0].equals("#chat")) {
            return CommandType.CHAT;
        }
        if(split[0].equals("#setapi")) {
            return CommandType.SET_API;
        }
        return CommandType.NONE;
    }
}
