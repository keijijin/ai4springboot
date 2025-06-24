# 🧠 Java & Spring Boot で作る ChatGPT ストリーミングAPI

このプロジェクトは、**OpenAI GPT API** を用いて、**リアルタイムでストリーミング応答を返す**チャット風のAPIを構築するSpring Bootアプリケーションです。

本プロジェクトは、[AI with Java & Spring Boot – Part 2: Streaming ChatGPT Responses](https://dev.to/devkaykay/ai-with-java-spring-boot-part-2-streaming-chatgpt-responses-3l13) に基づいています。

---

## 🚀 概要

- OpenAI API（`stream=true`）を利用してチャンク単位でデータを受信
- Spring WebFlux + WebClient による非同期処理
- クライアントには Server-Sent Events (SSE) でリアルタイムに返却
- `curl` や JavaScript からの接続が可能

---

## 🧰 前提条件

- Java 17 以上（推奨: Java 21）
- Maven 3.8+
- OpenAI API キー（https://platform.openai.com/ にて取得）

---

## ⚙️ セットアップ手順

### 1. プロジェクトをクローン

```bash
git clone https://github.com/your-username/ai-tutorial.git
cd ai-tutorial
```

### 2. OpenAI APIキーの設定

以下のいずれかで設定します。

#### 方法①: 環境変数で設定

```bash
export OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

#### 方法②: `application.yaml` を編集

```yaml
openai:
  api-key: sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  model: gpt-3.5-turbo
  temperature: 0.7
  max-tokens: 1000
```

### 3. 起動

```bash
./mvnw spring-boot:run
```

---

## 🔍 API エンドポイント

### 📨 ストリーミングチャット応答

```
GET /api/ai/chat-stream?prompt=こんにちは、面白いジョークを教えて
```

- クエリパラメータ: `prompt` に対話内容を指定
- レスポンス形式: `text/event-stream`（SSE）

---

## 🧪 実行例

### curl を使う場合

```bash
curl http://localhost:8080/api/ai/chat-stream?prompt=面白いジョークを教えて
```

### JavaScript (SSE クライアント)

```javascript
const evtSource = new EventSource("/api/ai/chat-stream?prompt=Tell me a joke");

evtSource.onmessage = function(event) {
    console.log("🧠", event.data);
    // 画面に出力するなどの処理
};
```

---

## 🧩 プロジェクト構成

```text
src
├── main
│   ├── java/com/sample
│   │   ├── AiTutorialApplication.java      # アプリ起動クラス
│   │   ├── controller
│   │   │   ├── AIController.java           # テキスト要約API（Part 1）
│   │   │   └── AIStreamController.java     # SSEストリーミングAPI（Part 2）
│   │   └── service
│   │       └── OpenAIService.java          # OpenAI APIとのやり取り
│   └── resources
│       └── application.yaml                # 設定ファイル
```

---

## 📦 使用技術・ライブラリ

| ライブラリ名                     | 説明                                       |
|----------------------------------|--------------------------------------------|
| `spring-boot-starter-webflux`    | WebClientとFluxによる非同期通信            |
| `jackson-databind`               | JSONのパース処理（OpenAI応答処理に使用）   |
| `spring-boot-starter-web`        | REST APIベースの基本機能                   |

---

## 📖 関連リンク

- 🔗 [Part 2: Streaming ChatGPT Responses（英語記事）](https://dev.to/devkaykay/ai-with-java-spring-boot-part-2-streaming-chatgpt-responses-3l13)
- 🔗 [OpenAI API ドキュメント](https://platform.openai.com/docs/guides/gpt)
- 🔗 [Spring WebFlux ドキュメント](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

---

## 📝 ライセンス

このプロジェクトは学習・検証用途向けに公開されています。
