# 📄 PDF Q\&A Bot with LangChain4j & Spring Boot

このプロジェクトは、PDFファイルをアップロードして内容に関する質問を投げかけ、AI が文脈に基づいて回答を返す Q\&A システムです。LangChain4j、Spring Boot、OpenAI Embedding API を活用しています。

## 🧠 主な機能

* PDFファイルのアップロードと解析
* 文書の分割と OpenAI による埋め込み（text-embedding-ada-002 使用）
* インメモリベクトルストアへの保存
* gpt-3.5-turbo によるコンテキストに基づいた自然言語応答

---

## ⚙️ 使用技術

| 項目          | 内容                                       |
| ----------- | ---------------------------------------- |
| Java        | 21                                       |
| Spring Boot | 3.3.x（Spring Web）                        |
| LangChain4j | 1.1.0-beta7（`easy-rag`, `pdfbox-parser`） |
| OpenAI      | Embedding + Chat Completion API          |
| PDF解析       | Apache PDFBox v3.x                       |
| ベクトルストア     | InMemoryEmbeddingStore（LangChain4j 提供）   |

---

## 📦 Maven 依存関係（抜粋）

```xml
<!-- LangChain4j Easy RAG -->
<dependency>
  <groupId>dev.langchain4j</groupId>
  <artifactId>langchain4j-easy-rag</artifactId>
  <version>1.1.0-beta7</version>
</dependency>

<!-- PDFBox ドキュメントパーサー -->
<dependency>
  <groupId>dev.langchain4j</groupId>
  <artifactId>langchain4j-document-parser-apache-pdfbox</artifactId>
  <version>1.1.0-beta7</version>
</dependency>

<!-- OpenAI API -->
<dependency>
  <groupId>dev.langchain4j</groupId>
  <artifactId>langchain4j-open-ai</artifactId>
  <version>1.1.0</version>
</dependency>
```

---

## 🚀 実行方法

### 1. PDFをアップロードしてインデックス化

```bash
curl -X POST -F 'file=@camel4.pdf' http://localhost:8080/api/pdf/upload
```

### 2. 質問を投げる

```bash
curl -X POST http://localhost:8080/api/pdf/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "このドキュメントの目的は何ですか？"}'
```

---

## 📁 エンドポイント一覧

| メソッド | パス                | 機能                  |
| ---- | ----------------- | ------------------- |
| POST | `/api/pdf/upload` | PDFファイルのアップロードおよび解析 |
| POST | `/api/pdf/ask`    | 質問を投げてAIの回答を取得      |

---

## 🛠 実装の構成（概要）

### コントローラー

```java
@RestController
@RequestMapping("/api/pdf")
public class PdfQaController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) { ... }

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody Map<String, String> body) { ... }
}
```

### サービス

```java
public class PdfQaService {

    private final RetrievalAugmentedGeneration rag;

    public PdfQaService() {
        this.rag = RetrievalAugmentedGeneration.builder()
            .documentParser(new ApachePdfBoxDocumentParser())
            .embeddingModel(OpenAiEmbeddingModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("text-embedding-ada-002")
                .build())
            .chatLanguageModel(OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-3.5-turbo")
                .build())
            .build();
    }

    public void index(MultipartFile file) { ... }

    public String ask(String question) { ... }
}
```

---

## ✅ 注意点

* OpenAI の APIキーは **環境変数 `OPENAI_API_KEY`** に設定してください。
* Apache PDFBox は **v3系** を使用してください（`PDDocument.load(InputStream)` の互換性に注意）。
* 実運用では、ファイル永続化・認証・ログ出力等の拡張が必要です。

---

## 📚 参考元

* LangChain4j公式: [https://docs.langchain4j.dev/](https://docs.langchain4j.dev/)
* OpenAI Embeddings: [https://platform.openai.com/docs/guides/embeddings](https://platform.openai.com/docs/guides/embeddings)
* 記事: "AI with Java & Spring Boot – Part 4"（内容を `1.1.0-beta7` に更新）
