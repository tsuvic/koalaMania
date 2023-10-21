# koalaMania
コアラマニアのリポジトリです。  
DockerとMakeコマンドのインストールが必要です。

# 起動方法
.env.sampleを.envにコピー  
`cp .env.sample .env`  
.envに必要な環境変数をセット

## 以下のコマンドでappとpostgresが起動する  
`make up`

## アプリのbuidlと実行コマンド
`make app`

## テストの実行コマンド
`make test`

## DBに変更がある場合
`src/main/resources/db/migration` ディレクトリに`V[番号]__[任意の文字列].sql` ファイルを作り、`make app` コマンドを実行すると、マイグレーションされ、DBが変更される。