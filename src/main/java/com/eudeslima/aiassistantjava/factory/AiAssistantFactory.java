package com.eudeslima.aiassistantjava.factory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;

import java.time.Duration;

 public class AiAssistantFactory {

        public static ChatLanguageModel createHuggingFace(String accessToken) {
            return HuggingFaceChatModel.builder()
                    .accessToken(accessToken)
                    .modelId("facebook/dpr-ctx_encoder-single-nq-base")
                    .timeout(Duration.ofSeconds(300))
                    .build();
    }
}
