package neko.kyuubit.openai;

import com.theokanning.openai.service.OpenAiService;

public class OpenAiUtil {
    /**
     * Tries to initialise an OpenAiService instance
     * @param apiKey
     * @return OpenAiService
     */
    public static OpenAiService getOpenAiServiceInstance(String apiKey) {
        return new OpenAiService(apiKey);
    }
}
