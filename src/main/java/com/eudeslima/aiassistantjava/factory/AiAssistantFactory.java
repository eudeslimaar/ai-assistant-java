package com.eudeslima.aiassistantjava.factory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.time.Duration;

 public class AiAssistantFactory {

     public static ChatLanguageModel createLocalChatModel() {
         return OpenAiChatModel.builder()
                 .baseUrl("http://localhost:1234/v1")
                 .apiKey("ignore")
                 .logRequests(true)
                 .timeout(Duration.ofSeconds(300))
                 .build();
     }
}
