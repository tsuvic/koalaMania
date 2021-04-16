@startgantt

title 想定作業スケジュール

hide footbox

printscale weekly
saturday are closed
sunday are closed

Project starts the 1 of april 2021

[Heroku上で公開予定] happens 2021-07-01
[画面・機能・データが揃い次第、サービスを周知] happens 2021-08-02

-- ユーザー数 --

[0人] lasts 90 days
then [0~5人] lasts 60 days
then [5~20人] lasts 60 days

-- アプリケーション --

[基本画面実装] lasts 35 days
note bottom
'note left
・メインメニュー簡易版
・検索画面
・検索結果一覧画面
・検索結果詳細画面
end note

then [追加画面実装] lasts 23 days
note bottom
'note left
・登録画面（Create）
・更新画面（Update）
・削除画面（Delete）
end note

[メイン画面検討] starts at [基本画面実装]'s end and lasts 53 days
note bottom
'note left
★家系図
★ハンバーガーメニュー
★UI/UX

end note

then [メイン画面実装] lasts 60 days

[基本機能実装] lasts 30 days
note bottom
'note left
・検索機能（Read）
・閲覧機能（Read）
end note

then [追加機能実装] lasts 23 days
note bottom
'note left
・登録機能（Create）
・更新機能（Update）
・削除機能（Delete）
end note

then [セキュリティ機能実装] lasts 30 days
note bottom
'note left
・アカウント登録機能
・ログイン機能
・アクセス制御　他
end note

[メイン機能検討] starts at [基本機能実装]'s end and lasts 53 days
note bottom
'note left
・Twitter API, Instagram API
・画像埋め込み
・チャット
・日記
・ランキング
end note

then [メイン機能実装] lasts 65 days

-- インフラ --

[インフラ学習・公開準備・運用検討] lasts 90 days
note bottom
'note left
・Heroku運用
・公開向け準備
    - データベース変更
    - レンタルサーバー等検討
end note

-- データ --

[簡易データ準備・テーブル設計・SQL実装] lasts 90 days
note bottom
'note left
・コアラ6匹程度の簡易データを用意
・データ登録/運用方法の検討
end note

-- 施策 --
[ツール選定・技術選定] lasts 90 days
note bottom
'note left
・試してみたい技術やツールの洗い出し、整理

end note


@endgantt