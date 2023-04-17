# Minecraft Chat2GPT

***Disclaimer - Using the API is not free, Messaging consumes tokens, which cost money. For pricing details, visit [OpenAI Pricing](https://openai.com/pricing)***

## Usage

To interact with ChatGPT, type commands prefixed with #.

### Register

First, register your API key, which can be found at [OpenAI API Key](https://platform.openai.com/account/api-keys). Note: An account is required.

Example usage (*ignore angle brakets*):
```
#register <YOUR_API_KEY_HERE>
```

The mod will confirm key registration. If the key is invalid, chatting will fail.

### Setting Parameters
The Default parameters are 
- Model: gpt-3.5-turbo [Available Models](https://platform.openai.com/docs/models)
- Max Tokens: 50 [What are Tokens?](https://help.openai.com/en/articles/4936856-what-are-tokens-and-how-to-count-them)
- Temperature: 1.0 (*This controls the randomness 1.0 is fully random, 0.0 is fully deterministic*) 

To modify these parameters, use the #setapi command:
```
#setapi model ada // set the model
#setapi tokens 30 // set the max tokens to 30
#setapi temp 0.2 // set the temperature to 0.2
```

### Chatting
To chat with ChatGPT, use the #chat command followed by the message you want to send:
```
#chat What is water?
```

This will ask ChatGPT "What is water?" and the response will appear in the chat. Responses are visible only to the player.

### Dependencies
This mod requires the [FabricAPI](https://www.curseforge.com/minecraft/mc-mods/fabric-api) to work

## Resources
[OpenAI API Documentation](https://platform.openai.com/docs/api-reference/models) for more information.