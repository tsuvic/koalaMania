INSERT INTO public.prefecture (prefecture_name,prefecture_name_kana) VALUES
	 ('北海道','ホッカイドウ'),
	 ('青森県','アオモリケン'),
	 ('岩手県','イワテケン'),
	 ('宮城県','ミヤギケン'),
	 ('秋田県','アキタケン'),
	 ('山形県','ヤマガタケン'),
	 ('福島県','フクシマケン'),
	 ('茨城県','イバラキケン'),
	 ('栃木県','トチギケン'),
	 ('群馬県','グンマケン');
INSERT INTO public.prefecture (prefecture_name,prefecture_name_kana) VALUES
	 ('埼玉県','サイタマケン'),
	 ('千葉県','チバケン'),
	 ('東京都','トウキョウト'),
	 ('神奈川県','カナガワケン'),
	 ('新潟県','ニイガタケン'),
	 ('富山県','トヤマケン'),
	 ('石川県','イシカワケン'),
	 ('福井県','フクイケン'),
	 ('山梨県','ヤマナシケン'),
	 ('長野県','ナガノケン');
INSERT INTO public.prefecture (prefecture_name,prefecture_name_kana) VALUES
	 ('岐阜県','ギフケン'),
	 ('静岡県','シズオカケン'),
	 ('愛知県','アイチケン'),
	 ('三重県','ミエケン'),
	 ('滋賀県','シガケン'),
	 ('京都府','キョウトフ'),
	 ('大阪府','オオサカフ'),
	 ('兵庫県','ヒョウゴケン'),
	 ('奈良県','ナラケン'),
	 ('和歌山県','ワカヤマケン');
INSERT INTO public.prefecture (prefecture_name,prefecture_name_kana) VALUES
	 ('鳥取県','トットリケン'),
	 ('島根県','シマネケン'),
	 ('岡山県','オカヤマケン'),
	 ('広島県','ヒロシマケン'),
	 ('山口県','ヤマグチケン'),
	 ('徳島県','トクシマケン'),
	 ('香川県','カガワケン'),
	 ('愛媛県','エヒメケン'),
	 ('高知県','コウチケン'),
	 ('福岡県','フクオカケン');
INSERT INTO public.prefecture (prefecture_name,prefecture_name_kana) VALUES
	 ('佐賀県','サガケン'),
	 ('長崎県','ナガサキケン'),
	 ('熊本県','クマモトケン'),
	 ('大分県','オオイタケン'),
	 ('宮崎県','ミヤザキケン'),
	 ('鹿児島県','カゴシマケン'),
	 ('沖縄県','オキナワケン'),
	 ('オーストラリア','オーストラリア'),
	 ('その他','その他');

INSERT INTO public.zoo (zoo_name,prefecture_id,created_by,updated_by,created_date,updated_date) VALUES
	 ('東山動植物園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '愛知県'),1,1,now(),now()),
	 ('平川動物公園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '鹿児島県'),1,1,now(),now()),
	 ('こども動物自然公園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '埼玉県'),1,1,now(),now()),
	 ('神戸市立王子動物園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '兵庫県'),1,1,now(),now()),
	 ('多摩動物公園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '東京都'),1,1,now(),now()),
	 ('横浜市立金沢動物園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '神奈川県'),1,1,now(),now()),
	 ('淡路ファームパーク イングランドの丘',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '兵庫県'),1,1,now(),now()),
	 ('天王寺動物園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = '大阪府'),1,1,now(),now()),
	 ('ドリームワールド',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = 'オーストラリア'),1,1,now(),now()),
	 ('タロンガ動物園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = 'オーストラリア'),1,1,now(),now());
INSERT INTO public.zoo (zoo_name,prefecture_id,created_by,updated_by,created_date,updated_date) VALUES
	 ('ヤンチャップ国立公園',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = 'その他'),1,1,now(),now()),
	 ('その他',(SELECT prefecture_id FROM prefecture WHERE prefecture_name = 'その他'),1,1,now(),now());

INSERT INTO public.animal_type ("name",created_date,updated_date,created_by,updated_by) VALUES
	 ('コアラ',now(),now(),1,1);

INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('ニシチ',1,'2019-02-07',0,'2021-03-31',51,52,'一番好きだったコアラ。可愛かった。。','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628385384/animal/9/profile/9.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 10:16:43.602467',1),
	 ('ニーナ',2,'2016-09-27',0,'2020-05-04',0,10,'父と同じ名前のコアラから子を作った','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628385473/animal/51/profile/51.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 16:09:46.223299',1),
	 ('きらら',2,'2018-09-06',1,'9999-01-01',25,27,'未入力','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628385661/animal/11/profile/11.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 10:21:01.997034',1),
	 ('テストコアラ',2,'2013-01-01',1,'9999-01-01',98,89,'ｄじゃｇｐｓじゃ','ｐｊｄｐそｊふぁ','https://res.cloudinary.com/honx7tf4c/image/upload/v1628492989/animal/133/profile/133.png',1,2,'2021-07-10 12:33:38.80376','2021-08-10 10:19:57.716242',1),
	 ('ドリー',2,'2012-10-24',0,'2020-02-27',0,0,'2015年1月にオーストラリアから来園したオスのボウ、メスのドリー、ジンベランの3頭のうちの1頭','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628601619/animal/53/profile/53.png',1,2,'2021-06-19 14:43:59.213189','2021-08-10 22:20:20.162104',1),
	 ('コロン',1,'2014-11-22',1,'9999-01-01',0,0,'未入力','3から5','https://res.cloudinary.com/honx7tf4c/image/upload/v1628385689/animal/17/profile/17.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 10:21:29.944608',1),
	 ('こまち',2,'2017-04-27',1,'9999-01-01',28,57,'おっとり。母親のホリー似。','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628385756/animal/12/profile/12.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 10:22:36.369858',1),
	 ('test',2,'2021-03-04',1,'9999-01-01',0,0,'未入力','未入力','/images/defaultAnimal.png',2,2,'2021-12-03 20:02:55.417944','2021-12-03 20:11:22.161845',1),
	 ('たんぽぽ',2,'2020-04-26',1,'9999-01-01',13,14,'未入力','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1640432681/animal/182/profile/182.jpg',2,2,'2021-12-19 13:58:48.56305','2021-12-25 20:44:42.120953',1),
	 ('ユウキ',1,'2009-06-01',0,'2019-08-21',0,0,'未入力','2から6',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('のぞみ',2,'2008-03-01',1,'9999-01-01',0,0,'自由奔放さと愛嬌がある小悪魔的コアラ','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('母親',2,'1933-01-01',1,'9999-01-01',101,99,'aaa','dddd','https://res.cloudinary.com/honx7tf4c/image/upload/v1624066315/koala/88/profile/88.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:23:04.772092',1),
	 ('メイ',2,'2004-01-01',0,'2014-01-01',0,0,'未入力','未入力','/images/defaultAnimal.png',2,2,'2021-08-08 15:34:13.078077','2021-08-10 17:19:04.51628',1),
	 ('ふく',2,'2019-06-12',1,'9999-01-01',53,10,'クインが育ての母','目がぱっちり。鼻が真っ黒。','https://res.cloudinary.com/honx7tf4c/image/upload/v1628402995/animal/4/profile/4.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 15:10:56.299987',1),
	 ('コタロウ',1,'2011-07-28',0,'2021-07-23',72,0,'貫禄のある元気なコアラ。','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628403618/animal/10/profile/10.png',1,2,'2021-06-19 14:43:59.213189','2021-08-08 16:25:29.762381',1),
	 ('コウ',1,'1995-07-15',0,'9999-01-01',0,0,'https://www.tokyo-zoo.net/topic/topics_detail?kind=news&inst=tama&link_num=9105','未入力','/images/defaultAnimal.png',2,2,'2021-08-08 15:33:12.507163','2021-08-22 23:36:00.564606',1),
	 ('ユイ',2,'2013-07-27',0,'2021-11-08',77,0,'金沢動物園で誕生し、幼獣時代は体が小さく自力で止まり木に
止まることができなかったが、介添え飼育のおかげでその後は順調に育つ。','未入力','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-12-19 10:06:37.223321',1),
	 ('アーチャー',1,'2007-03-08',0,'2015-04-03',0,0,'http://blog.livedoor.jp/minami758/archives/1564079.html','未入力','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-09 17:51:23.921603',1),
	 ('更新テスト',2,'2012-07-16',1,'9999-01-01',37,35,'更新','更新','https://res.cloudinary.com/honx7tf4c/image/upload/v1624187315/koala/108/profile/108.jpg',6,1,'2021-06-20 19:43:33.341305','2021-07-10 17:04:37.98235',1),
	 ('コハル',2,'2019-04-02',1,'9999-01-01',19,17,'毛の色が濃い','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1640432583/animal/6/profile/6.jpg',1,2,'2021-06-19 14:43:59.213189','2021-12-25 20:43:03.80268',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('だいち',1,'2013-08-18',1,'9999-01-01',44,59,'寝相がおっさん','https://withnews.jp/article/f0180608005qq000000000000000W00o10101qq000017461A',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ユウキ',1,'2010-09-07',0,'2019-08-21',0,0,'未入力','多摩から金沢へ',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ユイ',2,'2018-06-25',1,'9999-01-01',44,42,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('マックス',1,'2013-01-01',0,'2017-12-17',0,0,'りんの父。タロンガ動物園開園 100 周年と東山動植物園との姉妹動物園提携 20 周年を記念して、今年 3 月に寄贈された仔。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('レイ',2,'2018-08-12',1,'9999-01-01',43,42,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('イト',2,'2017-05-04',1,'9999-01-01',65,42,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('バンブラ',1,'2013-03-22',1,'9999-01-01',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ブンダ',1,'2010-09-15',1,'9999-01-01',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('キボウ',2,'2019-10-17',1,'9999-01-01',43,41,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ヒマワリ',2,'2019-06-22',1,'9999-01-01',44,41,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('ビー',1,'2019-05-21',1,'9999-01-01',15,17,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ハナ',2,'2019-05-20',1,'9999-01-01',33,32,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('マイ',2,'2019-05-15',1,'9999-01-01',50,32,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ピリー',1,'2018-04-20',1,'9999-01-01',18,54,'親の故郷であるオーストラリアクイーンズランド州のユーカリの木の下に咲く花「リリーピリー」から命名。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ななみ',2,'2018-07-07',1,'9999-01-01',28,27,'お転婆。きららと腹違いの姉妹。自分の父親と子供を作る？ななみの父親はタイチ？？','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('りん',2,'2017-08-23',1,'9999-01-01',25,57,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('イシン',1,'2017-05-14',1,'9999-01-01',44,42,'オスらしい立派な鼻と、キュッと上がった口角がかわいいイシン。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('クレメンツ',2,'1997-11-20',1,'9999-01-01',0,0,'長寿。日本におけるコアラの長寿最高記録は、以前東山で暮らしていたオスの「ラム」の23歳11か月です。世界最高記録が25歳のようなので、あと4か月ほどで21歳になるクレメンツが、コアラとしてはかなりの高齢だということを感じて頂けるでしょうか。','http://www.higashiyama.city.nagoya.jp/blog/2018/07/post-3625.html',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ティリー',2,'2009-12-15',1,'9999-01-01',0,0,'"りん" "きらら" "インディコ"を育てあげたビッグママ（実際、体もビッグ!）。６児の母。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ココ',2,'2010-05-05',1,'9999-01-01',72,71,'小柄で可愛らしいココですが、実は...東山のメスたちの中で１番のマッチョ！腕から肩にかけて筋肉ムキムキで、枝の間も軽々と移動していく運動神経抜群。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('タイチ',1,'2012-05-03',1,'9999-01-01',0,0,'未入力','5から1',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ホリー',2,'2013-12-15',1,'9999-01-01',0,0,'未入力','https://www.sankei.com/photo/daily/news/160328/dly1603280032-n1.html',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('エマ',2,'2018-12-06',1,'9999-01-01',30,32,'投票で命名。外国語で「大きな愛情をもってほしい」という願いが込められている','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ピーター',1,'2016-03-28',1,'9999-01-01',25,67,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('オウカ',2,'2016-09-25',1,'9999-01-01',30,49,'桜の花のように美しいコアラに育つように。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('みどり',2,'1997-02-01',1,'9999-01-01',0,0,'最長寿。ギネス世界記録に認定。2003（平成15）年3月21日にオーストラリアの西オーストラリア州パースにあるヤンチャップ国立公園。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ジェイン',2,'2014-11-19',1,'9999-01-01',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ユメ',2,'2015-01-02',1,'9999-01-01',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('リオ',2,'2015-12-10',1,'9999-01-01',0,0,'','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ワトル',2,'2020-05-14',1,'9999-01-01',21,27,'とにかく可愛い。小柄な体格。オーストラリアの人たちになじみの深い国花にちなみ「ワトルWattle」と命名。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('ウルル',1,'2007-05-26',0,'2017-12-18',0,0,'コタロウ(10)の父','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ウメ',2,'2010-08-05',1,'9999-01-01',74,78,'コタロウ(10)の母','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('チャーリー',1,'2014-11-27',1,'9999-01-01',25,58,'母親のティリーがオーストラリアから来て日本で初めて産んだ子どもであり、父親のアーチャーが最後に残してくれた子','なし','https://res.cloudinary.com/honx7tf4c/image/upload/v1640432608/animal/14/profile/14.jpg',1,2,'2021-06-19 14:43:59.213189','2021-12-25 20:43:28.718919',1),
	 ('ぼたん',2,'2017-05-12',1,'9999-01-01',50,60,'未入力','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1640432652/animal/13/profile/13.jpg',1,2,'2021-06-19 14:43:59.213189','2021-12-25 20:44:13.505989',1),
	 ('ピノ',1,'2015-12-13',0,'2020-05-25',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('アーティー',1,'2012-08-11',0,'2017-07-27',0,58,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ひかり',2,'1997-02-01',0,'2020-02-01',0,0,'未入力','https://www.kobe-np.co.jp/news/sougou/202002/0013080532.shtml',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('みなみ',1,'1996-05-01',0,'2018-08-05',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ミライ',2,'2009-10-25',0,'2020-06-10',79,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('セイ',1,'2018-06-26',0,'2020-03-23',65,41,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('ココロ',2,'2011-09-26',0,'2019-12-25',0,49,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ジンベラン',2,'2013-02-23',1,'9999-01-01',0,0,'2015年1月にオーストラリアから来園したオスのボウ、メスのドリー、ジンベランの3頭のうちの1頭','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628402732/animal/18/profile/18.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 15:05:33.024509',1),
	 ('リリー',2,'2018-03-28',1,'9999-01-01',53,17,'親の故郷であるオーストラリアクイーンズランド州のユーカリの木の下に咲く花「リリーピリー」から命名。','元気いっぱい！食いしん坊！','https://res.cloudinary.com/honx7tf4c/image/upload/v1628407737/animal/16/profile/16.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 16:28:57.901481',1),
	 ('クイン',2,'2015-03-23',1,'9999-01-01',53,69,'大きなお顔。','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628407811/animal/19/profile/19.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-09 15:43:33.916679',1),
	 ('ハニー',2,'2014-08-03',1,'9999-01-01',37,67,'ハチミツの日にちなんでハニーと命名。','顔が真ん丸。','https://res.cloudinary.com/honx7tf4c/image/upload/v1628407909/animal/15/profile/15.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 16:31:49.47885',1),
	 ('アーク',1,'2006-12-15',1,'9999-01-01',0,0,'イギリスのロングリートサファリパークへ出園（2019年10月10日）。メルボルン動物園生まれ。','https://www.ktv.jp/news/feature/20191224/','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-09 17:53:04.58682',1),
	 ('ウミ',2,'2014-06-13',1,'9999-01-01',50,49,'未入力','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628524967/animal/30/profile/30.png',1,2,'2021-06-19 14:43:59.213189','2021-08-10 01:02:47.850394',1),
	 ('エミ',2,'2013-08-08',0,'2019-07-25',68,71,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ピース',1,'2011-06-06',0,'2016-12-09',0,58,'ハニーの父','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ラム',1,'1988-04-28',0,'2012-03-30',0,0,'長寿。','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('ティムタム',1,'2005-09-03',0,'2013-07-26',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ミナト',1,'2014-06-12',0,'2018-12-20',74,49,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('モミジ',2,'2006-05-29',1,'9999-01-01',79,80,'未入力','6から4',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('そら',1,'2013-02-13',0,'2017-10-17',44,59,'香港へ','https://note.com/hapizooooo/n/ndab92b0ab75f',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('テル',2,'2008-11-22',0,'2016-01-29',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('パイン',1,'2011-06-04',0,'9999-01-01',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ミリー',2,'2020-08-23',1,'9999-01-01',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('モモジ',1,'1997-02-07',0,'2007-02-17',0,0,'未入力','未入力',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('インディコ',2,'2019-12-22',1,'9999-01-01',37,35,'イシンにとって初めて、ティリーにとって６人目の赤ちゃん。オーストラリア先住民の言葉で月の意味。','丸顔。',NULL,1,1,'2021-06-19 14:43:59.213189','2021-06-19 14:43:59.213189',1),
	 ('ゆめ',2,'2009-01-01',0,'2018-07-02',0,0,'だいちとそらの母親。','未入力','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-10 14:18:21.553922',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('父方の祖母',2,'1904-01-01',1,'9999-01-01',0,0,'aaa','aa','https://res.cloudinary.com/honx7tf4c/image/upload/v1624853620/koala/98/profile/98.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 20:20:55.201271',1),
	 ('いぶき',1,'2020-08-27',1,'9999-01-01',28,23,'父親のイシンに似ている..!?','誕生日要確認','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-10 09:52:55.321546',1),
	 ('父親',1,'1975-01-01',1,'9999-01-01',98,89,'ああ','あ','https://res.cloudinary.com/honx7tf4c/image/upload/v1624066292/koala/86/profile/86.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:27:15.154346',1),
	 ('母方の祖母',2,'1901-01-01',1,'9999-01-01',0,0,'aaa','aa','https://res.cloudinary.com/honx7tf4c/image/upload/v1621142995/koala/101/profile/101.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:20:31.033245',1),
	 ('ボウ',1,'2011-01-01',0,'2016-07-26',0,0,'2015年1月にオーストラリアから来園したオスのボウ、メスのドリー、ジンベランの3頭のうちの1頭','未入力','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-10 15:32:52.029777',1),
	 ('つくし',2,'2020-09-03',1,'9999-01-01',22,23,'母親譲りの濃い毛色','誕生日要確認','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-10 10:18:42.428947',1),
	 ('義理の母①',2,'2002-01-01',1,'9999-01-01',0,0,'あああ','ああああ','https://res.cloudinary.com/honx7tf4c/image/upload/v1621146289/koala/104/profile/104.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:28:39.452566',1),
	 ('義理の兄',1,'2019-01-01',0,'9999-01-01',104,86,'herokuのエラー画面が無条件で表示されてしまう','テスト','https://res.cloudinary.com/honx7tf4c/image/upload/v1621863231/koala/106/profile/106.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:25:01.227994',1),
	 ('兄',1,'1992-01-01',1,'9999-01-01',88,86,'aa','aa','https://res.cloudinary.com/honx7tf4c/image/upload/v1621143609/koala/102/profile/102.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:21:49.03538',1),
	 ('メインコアラ',1,'2008-01-01',1,'9999-01-01',88,86,'あ','あ','https://res.cloudinary.com/honx7tf4c/image/upload/v1624066247/koala/85/profile/85.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:22:33.468857',1);
INSERT INTO public.animal ("name",sex,birth_date,is_alive,death_date,mother,father,details,feature,profile_image_type,created_by,updated_by,created_date,updated_date,animal_type_id) VALUES
	 ('妹',2,'2014-12-01',1,'9999-01-01',88,86,'aa','aa','https://res.cloudinary.com/honx7tf4c/image/upload/v1621143855/koala/103/profile/103.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-10 10:19:11.51419',1),
	 ('父方の祖父',1,'1901-01-01',1,'9999-01-01',0,0,'yhky','kgjgkj','https://res.cloudinary.com/honx7tf4c/image/upload/v1624066352/koala/89/profile/89.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:26:58.282225',1),
	 ('コタロウ',1,'2015-09-25',1,'9999-01-01',50,49,'ウルルとウメの２頭目の子供。','4から5','https://res.cloudinary.com/honx7tf4c/image/upload/v1628385729/animal/52/profile/52.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-08 10:22:09.55352',1),
	 ('義理の妹',2,'2013-01-01',1,'9999-01-01',104,86,'a','a','https://res.cloudinary.com/honx7tf4c/image/upload/v1621511259/koala/105/profile/105.jpg',1,2,'2021-06-19 14:43:59.213189','2021-08-10 10:20:28.470126',1),
	 ('母方の祖父',1,'1906-01-01',1,'9999-01-01',0,0,'aaa','aaa','https://res.cloudinary.com/honx7tf4c/image/upload/v1621139530/koala/99/profile/99.jpg',1,2,'2021-06-19 14:43:59.213189','2021-06-28 21:31:38.283195',1),
	 ('コウメ',2,'2007-07-22',0,'2015-05-28',142,141,'コタロウの母。メイの最後の子。','未入力','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-09 17:55:37.392877',1),
	 ('シャイン',1,'2017-08-31',1,'9999-01-01',19,17,'名前募集の期間中はお母さんの袋に入っていることが多く、なかなか顔を見せてくれなかったための命名','未入力','https://res.cloudinary.com/honx7tf4c/image/upload/v1628496971/animal/31/profile/31.png',1,6,'2021-06-19 14:43:59.213189','2021-08-10 12:44:05.141738',1),
	 ('ライチ',2,'2009-11-11',0,'2015-05-28',0,0,'ハニーの母','未入力','/images/defaultAnimal.png',1,2,'2021-06-19 14:43:59.213189','2021-08-09 17:56:10.930537',1);
