package neko.kyuubit;

import neko.kyuubit.command.CommandControl;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.LoggerFactory;

public class ChatClient implements ClientModInitializer {
	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("Chat2GPT");
	public static CommandControl commandControl;

	/**
	 * initialises the command control on startup
	 */
	@Override
	public void onInitializeClient() {
		commandControl = CommandControl.getInstance();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}