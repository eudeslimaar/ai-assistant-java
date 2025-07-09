package com.eudeslima.aiassistantjava.controller;

import com.eudeslima.aiassistantjava.factory.AiAssistantFactory;
import com.eudeslima.aiassistantjava.factory.ContentRetrieverFactory;
import com.eudeslima.aiassistantjava.factory.EmbeddingFactory;
import com.eudeslima.aiassistantjava.service.DocumentAssistant;
import com.eudeslima.aiassistantjava.service.MetricsService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private MetricsService metricsService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "AI Assistant - Chat de Filmes");
        model.addAttribute("description", "Assistente de IA para consultar catálogo de filmes");
        model.addAttribute("metrics", metricsService.getMetrics());
        return "index";
    }

    @PostMapping("/api/chat/web")
    @ResponseBody
    public Map<String, Object> chatWeb(@RequestParam String message) {
        Map<String, Object> response = new HashMap<>();
        
        metricsService.incrementTotalRequests();
        
        try {
            ChatLanguageModel chatModel = AiAssistantFactory.createLocalChatModel();
            var embeddingModel = EmbeddingFactory.createEmbeddingModel();
            var embeddingStore = EmbeddingFactory.createEmbeddingStore();
            var fileContentRetriever = ContentRetrieverFactory.createFileContentRetriever(
                    embeddingModel,
                    embeddingStore,
                    "movies.txt"
            );

            var documentAssistant = new DocumentAssistant(chatModel, fileContentRetriever);
            String aiResponse = documentAssistant.chat(message);
            
            response.put("success", true);
            response.put("response", aiResponse);
            response.put("message", message);
            
            metricsService.incrementSuccessfulRequests();
            
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg != null && (errorMsg.contains("No models loaded") || errorMsg.contains("model_not_found"))) {
                response.put("success", false);
                response.put("error", "Nenhum modelo está carregado no LM Studio. Por favor, carregue um modelo e tente novamente.");
            } else {
                response.put("success", false);
                response.put("error", "Tente novamente em alguns minutos.");
            }
            metricsService.incrementFailedRequests();
        }
        
        return response;
    }
} 