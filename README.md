# Assistente de IA Java

Um assistente de inteligência artificial baseado em documentos, desenvolvido com Java, Spring Boot e LangChain4j. Este projeto implementa um sistema RAG (Retrieval-Augmented Generation) que permite fazer perguntas sobre um catálogo de filmes.

## Características

- Chat inteligente com consulta a documentos
- Sistema RAG para busca e geração de respostas
- Suporte a modelos locais (LM Studio) e HuggingFace
- Interface web moderna e responsiva
- Painel de métricas de uso e desempenho em tempo real

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.5
- LangChain4j 0.29.1
- Maven
- Lombok

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- LM Studio ou servidor OpenAI compatível (opcional para modelos locais)

## Instalação e Configuração


1. Configure o arquivo `src/main/resources/application.yml`:
   ```yaml
   ai:
     modelName: "google/gemma-3n-e4b"
   ```

2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse a interface web em:  
   `http://localhost:8080/`

## Uso da API

- **Chat via API:**  
  `POST /api/chat/web`  
  Body: `message=Sua pergunta aqui`

- **Métricas:**  
  `GET /api/metrics`

## Painel de Métricas

Na interface web, logo abaixo do chat, você verá um painel com:
- Modelo conectado
- Total de requisições
- Sucessos e falhas
- Taxa de sucesso
- Uptime
- Data/hora de início

## Como usar um dataset personalizado

1. Baixe um dataset de filmes
2. Use um script Python para converter o CSV para TXT no formato:
   ```
   Movie: Título
   Year: 1995
   Genres: Comédia, Aventura
   Synopsis: ...
   Rating: 7.7
   Runtime: 81

   Movie: ...
   ```
3. Coloque o arquivo `movies.txt` em `src/main/resources/`.
4. Reinicie a aplicação.

## Arquitetura do Projeto

```
src/main/java/com/eudeslima/aiassistantjava/
├── controller/
│   └── WebController.java         # Controlador principal (web e API)
├── service/
│   ├── DocumentAssistant.java     # Serviço principal
│   └── MetricsService.java        # Métricas de uso
├── factory/
│   ├── AiAssistantFactory.java
│   ├── EmbeddingFactory.java
│   └── ContentRetrieverFactory.java
└── utils/
    └── DocumentService.java
```

## Fontes

- LangChain4j: https://github.com/langchain4j/langchain4j
- Spring Boot: https://spring.io/projects/spring-boot
- HuggingFace: https://huggingface.co/

