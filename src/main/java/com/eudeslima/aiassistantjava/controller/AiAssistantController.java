package com.eudeslima.aiassistantjava.controller;

import com.eudeslima.aiassistantjava.dto.MessageDTO;
import com.eudeslima.aiassistantjava.factory.AiAssistantFactory;
import com.eudeslima.aiassistantjava.factory.ContentRetrieverFactory;
import com.eudeslima.aiassistantjava.service.DocumentAssistant;
import com.eudeslima.aiassistantjava.factory.EmbeddingFactory;
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

    @PostMapping
    public ResponseEntity chat(@RequestBody MessageDTO messageDTO) {
        ChatLanguageModel chatModel = AiAssistantFactory.createLocalChatModel();
        var embeddingModel = EmbeddingFactory.createEmbeddingModel();
        var embeddingStore = EmbeddingFactory.createEmbeddingStore();
        var fileContentRetriever = ContentRetrieverFactory.createFileContentRetriever(
                embeddingModel,
                embeddingStore,
                "movies.txt"
        );

        var documentAssistant = new DocumentAssistant(chatModel, fileContentRetriever);
        String response = documentAssistant.chat("List all movies with Comedy category");
        return ResponseEntity.ok().body(response);
    }
}
