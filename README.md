# koalaMania
コアラマニアのリポジトリです。

# 起動方法
.env.sampleを.envにコピー  
`cp .env.sample .env`  
.envに必要な環境変数をセット

## その後、以下のコマンドでappとpostgresが起動する  
`docker compose up -d`

## プロジェクトの実行方法  
`docker compose exec app sh gradlew bootRun`

## テストの実行方法
`docker compose exec app sh gradlew test`