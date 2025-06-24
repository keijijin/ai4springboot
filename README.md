# 🧠 AI Agent with Java & Spring Boot (LangChain4j 1.1.0)

LangChain4j を使って、Java + Spring Boot アプリに AI Agent 機能を追加します。自然言語プロンプトを解析し、Javaメソッド（ツール）を呼び出して応答を生成します。

## 📦 機能概要

このプロジェクトでは、以下のようなプロンプトを受け取り…

> "What’s 25% of 160 if it’s sunny in Tokyo?"

AIが以下を実行します：

* `WeatherTool.getCurrentWeather("Tokyo")` を呼び出す
* `CalculatorTool.calculatePercentage("25% of 160")` を呼び出す
* 結果を自然言語でまとめて返答

## ⚙️ 前提条件

* Java 21+
* Spring Boot 3.5+
* OpenAI API キー（`.env` または `application.yaml` に設定）

## 🗂️ ディレクトリ構成

```
src/main/java/com/sample/ai_tutorial/
├── AiTutorialApplication.java                 // Spring Boot アプリ起動クラス
├── controller/
│   └── AgentController.java                   // RESTエンドポイント (/api/agent/ask)
├── service/
│   ├── ReasoningAgent.java                    // Agent初期化・実行
│   └── ToolAgent.java                         // LangChain4j Agent定義
└── tools/
    ├── CalculatorTool.java                    // "25% of 160" のような計算処理
    └── WeatherTool.java                       // モック天気API (都市名を元に返答)
```

## 🛠️ 依存ライブラリ（`pom.xml` 抜粋）

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

※ `langchain4j-easy-rag` も含まれていますが、このプロジェクトでは未使用です。

## 🚀 実行方法

1. OpenAI APIキーを `application.yaml` に設定：

```yaml
openai:
  api-key: sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

2. アプリ起動：

```bash
./mvnw spring-boot:run
```

## 🌐 API 実行例

```bash
curl -X POST http://localhost:8080/api/agent/ask \
  -H "Content-Type: application/json" \
  -d '{"prompt": "If it is sunny in Tokyo, what is 25% of 160?"}'
```

出力例：

```
It is sunny in Tokyo. 

25% of 160 is 40.
```

## 🔍 動作確認のポイント

* `ToolAgent` では、LangChain4j の `@Tool` アノテーションにより、AIがJavaメソッドを呼び出せるようになっています。
* `ToolAgent.class` は `@ToolSpecificationExtractor` を使って LangChain4j にツール仕様を登録します。
* LLM が `tools:` に対して適切なメソッド呼び出しを構築し、それが `ToolExecutor` を通じて呼び出されます。

## 🛡️ 応用と発展

* 実天気API（OpenWeatherなど）に差し替え可能
* 通貨換算・翻訳・金融計算などのツールを追加して拡張可能
* LangChain4j AgentExecutor による推論ログ出力・分岐制御も可能

## 🧠 学べること

* LangChain4j v1.1.0 の最新APIを使ったエージェント構築法
* JavaからLLMを使った自然言語インターフェースの設計
* Spring Bootと統合したAI APIの構築手法
