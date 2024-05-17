package com.eudeslima.aiassistantjava.controller;

import com.eudeslima.aiassistantjava.dto.MessageDTO;
import com.eudeslima.aiassistantjava.factory.AiAssistantFactory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chat")
public class AiAssistantController {
    @Value("${langchain.huggingface.accessToken}")
    private String accessToken;

    @PostMapping
    public ResponseEntity chat(@RequestBody MessageDTO messageDTO) {
        ChatLanguageModel chatLanguageModel = AiAssistantFactory.createHuggingFace(accessToken);
        String response = chatLanguageModel.generate(messageDTO.message());
        return ResponseEntity.ok().body(response);
    }
}
