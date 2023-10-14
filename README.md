# koalaMania
コアラマニアのリポジトリです。

# 起動方法
.env.sampleを.envにコピー
`cp .env.sample .env`
.envに必要な環境変数をセット

その後、以下のコマンドでappとpostgresが起動する
`docker compose up -d`

プロジェクトのbuild方法
`docker compose exec app sh gradlew build`

プロジェクトの実行方法
`docker compose exec app java -jar build/libs/koalamania-api-0.0.1-SNAPSHOT.jar`
