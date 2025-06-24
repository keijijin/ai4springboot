# 🧠 AI Tutorial: Memory-Aware Chatbot with LangChain4j + Spring Boot

このプロジェクトは、JavaとSpring Bootを使って、LangChain4jライブラリとOpenAI APIを活用した**メモリ対応チャットボット**を構築するデモアプリケーションです。

## 📚 シリーズの位置づけ

本リポジトリは、以下の「AI with Java & Spring Boot」シリーズの **第3部** に対応しています：

1. テキスト要約APIの構築（`part1-summarizeAPI`）
2. ストリーミング対応のチャットAPI（`part2-streamingAPI`）
3. ✅ メモリ対応チャットボット（本プロジェクト、`part3-memoryChat`）

---

## 🛠 使用技術

* **LangChain4j**: JavaでLLMアプリケーションを構築するライブラリ
* **Spring Boot 3.5.3**
* **OpenAI GPT-3.5 Turbo API**
* **セッションスコープでのメモリ保持**

---

## 📦 依存関係（pom.xml）

```xml
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j</artifactId>
    <version>1.1.0</version>
</dependency>
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-open-ai</artifactId>
    <version>1.1.0</version>
</dependency>
```

---

## ⚙️ 設定（`application.yaml`）

```yaml
openai:
  api-key: ${OPENAI_API_KEY}
  model: gpt-3.5-turbo
  temperature: 0.7
  max-tokens: 1000
```

`.env` などで `OPENAI_API_KEY` を環境変数として設定してください。

---

## 🧠 メモリ対応チャットサービス

```java
@Component
@Scope("session") // セッション単位のメモリを維持
public class MemoryChatService {

    private final ChatLanguageModel model;
    private final ChatMemory memory;

    public MemoryChatService(@Value("${openai.api-key}") String apiKey) {
        this.model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gpt-3.5-turbo")
            .temperature(0.7)
            .build();
        this.memory = MessageWindowChatMemory.withMaxMessages(10);
    }

    public String chat(String userInput) {
        return model.generate(userInput, memory).content();
    }
}
```

---

## 🌐 RESTエンドポイント

```java
@RestController
@RequestMapping("/api/langchain")
@SessionAttributes("memoryChatService")
public class LangChainController {

    private final MemoryChatService memoryChatService;

    public LangChainController(MemoryChatService memoryChatService) {
        this.memoryChatService = memoryChatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String response = memoryChatService.chat(prompt);
        return ResponseEntity.ok(response);
    }
}
```

---

## 🚀 実行方法

```bash
mvn spring-boot:run
```

---

## 💬 テスト方法

以下のように `curl` コマンドでチャットを行います：

```bash
curl -X POST http://localhost:8080/api/langchain/chat \
-H "Content-Type: application/json" \
-d '{"prompt": "Who is the CEO of Tesla?"}'

curl -X POST http://localhost:8080/api/langchain/chat \
-H "Content-Type: application/json" \
-d '{"prompt": "Where was he born?"}'
```

Botが「he = Elon Musk」と記憶して答えてくれるはずです。

---

## 🏷 タグ履歴

* `part1-summarizeAPI`: テキスト要約API
* `part2-streamingAPI`: ストリーミングチャットAPI
* `part3-memoryChat`: メモリ対応チャットボット（本ブランチ）

---

## 📄 ライセンス

このプロジェクトは学習目的で提供されており、商用利用はご自身でご判断ください。

---