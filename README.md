# 🧠 Java & Spring Boot で作る AI テキスト要約API

このプロジェクトは、**Java 21** と **Spring Boot**、そして **OpenAI GPT-3.5** を使って、テキスト要約機能を提供する簡易REST APIを構築するサンプルです。

本プロジェクトは [AI with Java & Spring Boot](https://dev.to/devkaykay/ai-with-java-spring-boot-part-1-getting-started-with-ai-in-the-java-ecosystem-4h22) というブログシリーズをベースにしています。

---

## 🚀 主な機能

- OpenAIのGPTを用いたテキスト要約API（REST形式）
- `application.yaml` または環境変数でのAPIキー設定
- `RestTemplate` を使ったシンプルな実装
- Spring Boot 3.5.xベース

---

## 📦 使用技術

- Java 21
- Spring Boot 3.5.3
- OpenAI API（`gpt-3.5-turbo` 使用）
- Maven

---

## 🧰 前提条件

- Java 17以上（推奨: Java 21）
- Maven 3.8+
- OpenAI APIキー（[OpenAI Platform](https://platform.openai.com) にて取得可能）

---

## 🛠️ セットアップ手順

### 1. リポジトリのクローン

```bash
git clone https://github.com/your-username/ai-tutorial.git
cd ai-tutorial
```

### 2. OpenAI APIキーの設定

以下のいずれかの方法でAPIキーを設定してください。

#### 環境変数を使う場合:

```bash
export OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

#### `src/main/resources/application.yaml` を直接編集する場合:

```yaml
openai:
  api-key: your_api_key_here
  model: gpt-3.5-turbo
```

### 3. アプリケーションのビルドと起動

```bash
./mvnw spring-boot:run
```

---

## 🔍 APIの使い方

### エンドポイント

```http
POST /api/ai/summarize
```

### リクエストボディ（JSON形式）

```json
{
  "text": "OpenAI provides powerful AI tools including GPT-4 and Codex..."
}
```

### curlによるサンプル実行

```bash
curl -X POST http://localhost:8080/api/ai/summarize \
  -H "Content-Type: application/json" \
  -d '{"text": "OpenAI provides powerful AI tools including GPT-4 and Codex..."}'
```

---

## 🧩 プロジェクト構成

```text
src
├── main
│   ├── java/com/sample
│   │   ├── AiTutorialApplication.java      # メインエントリポイント
│   │   ├── controller/AIController.java    # RESTコントローラー
│   │   └── service/OpenAIService.java      # OpenAI連携ロジック
│   └── resources
│       └── application.yaml                # 設定ファイル
```

---

## 📖 参考資料

- 🔗 [AI with Java & Spring Boot（ブログシリーズ）](https://dev.to/devkaykay/ai-with-java-spring-boot-part-1-getting-started-with-ai-in-the-java-ecosystem-4h22)
- 🔗 [OpenAI API ドキュメント](https://platform.openai.com/docs/guides/gpt)

---

## 📝 ライセンス

このプロジェクトは学習およびデモ目的で公開されています。
