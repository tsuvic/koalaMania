--
-- PostgreSQL database dump
--

-- Dumped from database version 13.8 (Ubuntu 13.8-1.pgdg20.04+1)
-- Dumped by pg_dump version 14.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: animal; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.animal (
    animal_id integer NOT NULL,
    name text NOT NULL,
    sex integer NOT NULL,
    birthdate date NOT NULL,
    is_alive integer NOT NULL,
    deathdate date NOT NULL,
    mother integer NOT NULL,
    father integer NOT NULL,
    details text NOT NULL,
    feature text NOT NULL,
    profile_image_type character varying,
    created_by integer,
    updated_by integer,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    animal_type_id integer DEFAULT 1 NOT NULL
);

--
-- Name: animal_post_tag; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.animal_post_tag (
    id integer NOT NULL,
    animal_id integer NOT NULL,
    post_id integer NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer
);

--
-- Name: animal_type; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.animal_type (
    animal_type_id integer NOT NULL,
    name text NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer
);

--
-- Name: animal_type_animaltype_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.animal_type_animaltype_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: animal_type_animaltype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.animal_type_animaltype_id_seq OWNED BY public.animal_type.animal_type_id;


--
-- Name: animal_zoo_history; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.animal_zoo_history (
    animal_zoo_history_id integer NOT NULL,
    animal_id integer NOT NULL,
    zoo_id integer NOT NULL,
    admission_date date NOT NULL,
    exit_date date,
    created_by integer,
    updated_by integer,
    created_date timestamp without time zone,
    updated_date timestamp without time zone
);

--
-- Name: koala_koala_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.koala_koala_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: koala_koala_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--


--
-- Name: koala_zoo_history_koala_zoo_history_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.koala_zoo_history_koala_zoo_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: koala_zoo_history_koala_zoo_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

--
-- Name: login_user; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.login_user (
    user_id integer NOT NULL,
    provider character varying(100) NOT NULL,
    provider_id bigint NOT NULL,
    provider_adress text NOT NULL,
    user_name text NOT NULL,
    profile text,
    role text NOT NULL,
    status integer NOT NULL,
    auto_login text,
    login_date timestamp without time zone NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer,
    profileimagepath text,
    twitterlinkflag boolean,
    favorite_zoo bigint
);

--
-- Name: login_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.login_user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: login_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.login_user_user_id_seq OWNED BY public.login_user.user_id;


--
-- Name: post; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.post (
    post_id integer NOT NULL,
    user_id integer NOT NULL,
    zoo_id integer NOT NULL,
    parent_id integer,
    contents text NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer,
    visit_date date,
    title character varying(30)
);

--
-- Name: post_favorite; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.post_favorite (
    post_favorite_id integer NOT NULL,
    user_id integer NOT NULL,
    post_id integer NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer
);

--
-- Name: post_favorite_post_favorite_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.post_favorite_post_favorite_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: post_favorite_post_favorite_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.post_favorite_post_favorite_id_seq OWNED BY public.post_favorite.post_favorite_id;


--
-- Name: post_image; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.post_image (
    postimage_id integer NOT NULL,
    post_id integer NOT NULL,
    animal_id integer,
    image_address text,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer
);


--
-- Name: post_image_favorite; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.post_image_favorite (
    postimagefavorite_id integer NOT NULL,
    user_id integer NOT NULL,
    postimage_id integer NOT NULL,
    created_date timestamp without time zone,
    updated_date timestamp without time zone,
    created_by integer,
    updated_by integer
);

--
-- Name: post_image_favorite_postimagefavorite_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.post_image_favorite_postimagefavorite_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: post_image_favorite_postimagefavorite_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.post_image_favorite_postimagefavorite_id_seq OWNED BY public.post_image_favorite.postimagefavorite_id;


--
-- Name: post_image_postimage_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.post_image_postimage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: post_image_postimage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.post_image_postimage_id_seq OWNED BY public.post_image.postimage_id;


--
-- Name: post_post_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.post_post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: post_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.post_post_id_seq OWNED BY public.post.post_id;


--
-- Name: prefecture; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.prefecture (
    prefecture_id integer NOT NULL,
    prefecture_name text,
    prefecture_name_kana text
);

--
-- Name: prefecture_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.prefecture_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: prefecture_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.prefecture_id_seq OWNED BY public.prefecture.prefecture_id;


--
-- Name: zoo; Type: TABLE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE TABLE public.zoo (
    zoo_id integer NOT NULL,
    zoo_name text NOT NULL,
    prefecture_id integer,
    created_by integer,
    updated_by integer,
    created_date timestamp without time zone,
    updated_date timestamp without time zone
);

--
-- Name: zoo_zoo_id_seq; Type: SEQUENCE; Schema: public; Owner: vqzsyccrrgrcog
--

CREATE SEQUENCE public.zoo_zoo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: zoo_zoo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER SEQUENCE public.zoo_zoo_id_seq OWNED BY public.zoo.zoo_id;


--
-- Name: animal animal_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal ALTER COLUMN animal_id SET DEFAULT nextval('public.koala_koala_id_seq'::regclass);


--
-- Name: animal_type animal_type_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_type ALTER COLUMN animal_type_id SET DEFAULT nextval('public.animal_type_animaltype_id_seq'::regclass);


--
-- Name: animal_zoo_history animal_zoo_history_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_zoo_history ALTER COLUMN animal_zoo_history_id SET DEFAULT nextval('public.koala_zoo_history_koala_zoo_history_id_seq'::regclass);


--
-- Name: login_user user_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.login_user ALTER COLUMN user_id SET DEFAULT nextval('public.login_user_user_id_seq'::regclass);


--
-- Name: post post_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post ALTER COLUMN post_id SET DEFAULT nextval('public.post_post_id_seq'::regclass);


--
-- Name: post_favorite post_favorite_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_favorite ALTER COLUMN post_favorite_id SET DEFAULT nextval('public.post_favorite_post_favorite_id_seq'::regclass);


--
-- Name: post_image postimage_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image ALTER COLUMN postimage_id SET DEFAULT nextval('public.post_image_postimage_id_seq'::regclass);


--
-- Name: post_image_favorite postimagefavorite_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image_favorite ALTER COLUMN postimagefavorite_id SET DEFAULT nextval('public.post_image_favorite_postimagefavorite_id_seq'::regclass);


--
-- Name: prefecture prefecture_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.prefecture ALTER COLUMN prefecture_id SET DEFAULT nextval('public.prefecture_id_seq'::regclass);


--
-- Name: zoo zoo_id; Type: DEFAULT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.zoo ALTER COLUMN zoo_id SET DEFAULT nextval('public.zoo_zoo_id_seq'::regclass);


--
-- Data for Name: animal; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.animal (animal_id, name, sex, birthdate, is_alive, deathdate, mother, father, details, feature, profile_image_type, created_by, updated_by, created_date, updated_date, animal_type_id) FROM stdin;
9	ニシチ	1	2019-02-07	0	2021-03-31	51	52	一番好きだったコアラ。可愛かった。。	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628385384/animal/9/profile/9.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 10:16:43.602467	1
51	ニーナ	2	2016-09-27	0	2020-05-04	0	10	父と同じ名前のコアラから子を作った	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628385473/animal/51/profile/51.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 16:09:46.223299	1
11	きらら	2	2018-09-06	1	9999-01-01	25	27	未入力	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628385661/animal/11/profile/11.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 10:21:01.997034	1
133	テストコアラ	2	2013-01-01	1	9999-01-01	98	89	ｄじゃｇｐｓじゃ	ｐｊｄｐそｊふぁ	https://res.cloudinary.com/honx7tf4c/image/upload/v1628492989/animal/133/profile/133.png	1	2	2021-07-10 12:33:38.80376	2021-08-10 10:19:57.716242	1
53	ドリー	2	2012-10-24	0	2020-02-27	0	0	2015年1月にオーストラリアから来園したオスのボウ、メスのドリー、ジンベランの3頭のうちの1頭	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628601619/animal/53/profile/53.png	1	2	2021-06-19 14:43:59.213189	2021-08-10 22:20:20.162104	1
17	コロン	1	2014-11-22	1	9999-01-01	0	0	未入力	3から5	https://res.cloudinary.com/honx7tf4c/image/upload/v1628385689/animal/17/profile/17.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 10:21:29.944608	1
12	こまち	2	2017-04-27	1	9999-01-01	28	57	おっとり。母親のホリー似。	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628385756/animal/12/profile/12.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 10:22:36.369858	1
181	test	2	2021-03-04	1	9999-01-01	0	0	未入力	未入力	/images/defaultAnimal.png	2	2	2021-12-03 20:02:55.417944	2021-12-03 20:11:22.161845	1
182	たんぽぽ	2	2020-04-26	1	9999-01-01	13	14	未入力	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1640432681/animal/182/profile/182.jpg	2	2	2021-12-19 13:58:48.56305	2021-12-25 20:44:42.120953	1
35	ユウキ	1	2009-06-01	0	2019-08-21	0	0	未入力	2から6	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
37	のぞみ	2	2008-03-01	1	9999-01-01	0	0	自由奔放さと愛嬌がある小悪魔的コアラ	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
88	母親	2	1933-01-01	1	9999-01-01	101	99	aaa	dddd	https://res.cloudinary.com/honx7tf4c/image/upload/v1624066315/koala/88/profile/88.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:23:04.772092	1
142	メイ	2	2004-01-01	0	2014-01-01	0	0	未入力	未入力	/images/defaultAnimal.png	2	2	2021-08-08 15:34:13.078077	2021-08-10 17:19:04.51628	1
4	ふく	2	2019-06-12	1	9999-01-01	53	10	クインが育ての母	目がぱっちり。鼻が真っ黒。	https://res.cloudinary.com/honx7tf4c/image/upload/v1628402995/animal/4/profile/4.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 15:10:56.299987	1
10	コタロウ	1	2011-07-28	0	2021-07-23	72	0	貫禄のある元気なコアラ。	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628403618/animal/10/profile/10.png	1	2	2021-06-19 14:43:59.213189	2021-08-08 16:25:29.762381	1
141	コウ	1	1995-07-15	0	9999-01-01	0	0	https://www.tokyo-zoo.net/topic/topics_detail?kind=news&inst=tama&link_num=9105	未入力	/images/defaultAnimal.png	2	2	2021-08-08 15:33:12.507163	2021-08-22 23:36:00.564606	1
55	ユイ	2	2013-07-27	0	2021-11-08	77	0	金沢動物園で誕生し、幼獣時代は体が小さく自力で止まり木に\r\n止まることができなかったが、介添え飼育のおかげでその後は順調に育つ。	未入力	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-12-19 10:06:37.223321	1
58	アーチャー	1	2007-03-08	0	2015-04-03	0	0	http://blog.livedoor.jp/minami758/archives/1564079.html	未入力	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-09 17:51:23.921603	1
108	更新テスト	2	2012-07-16	1	9999-01-01	37	35	更新	更新	https://res.cloudinary.com/honx7tf4c/image/upload/v1624187315/koala/108/profile/108.jpg	6	1	2021-06-20 19:43:33.341305	2021-07-10 17:04:37.98235	1
6	コハル	2	2019-04-02	1	9999-01-01	19	17	毛の色が濃い	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1640432583/animal/6/profile/6.jpg	1	2	2021-06-19 14:43:59.213189	2021-12-25 20:43:03.80268	1
36	だいち	1	2013-08-18	1	9999-01-01	44	59	寝相がおっさん	https://withnews.jp/article/f0180608005qq000000000000000W00o10101qq000017461A	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
56	ユウキ	1	2010-09-07	0	2019-08-21	0	0	未入力	多摩から金沢へ	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
38	ユイ	2	2018-06-25	1	9999-01-01	44	42	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
57	マックス	1	2013-01-01	0	2017-12-17	0	0	りんの父。タロンガ動物園開園 100 周年と東山動植物園との姉妹動物園提携 20 周年を記念して、今年 3 月に寄贈された仔。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
39	レイ	2	2018-08-12	1	9999-01-01	43	42	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
40	イト	2	2017-05-04	1	9999-01-01	65	42	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
41	バンブラ	1	2013-03-22	1	9999-01-01	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
42	ブンダ	1	2010-09-15	1	9999-01-01	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
2	キボウ	2	2019-10-17	1	9999-01-01	43	41	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
3	ヒマワリ	2	2019-06-22	1	9999-01-01	44	41	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
5	ビー	1	2019-05-21	1	9999-01-01	15	17	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
7	ハナ	2	2019-05-20	1	9999-01-01	33	32	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
8	マイ	2	2019-05-15	1	9999-01-01	50	32	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
20	ピリー	1	2018-04-20	1	9999-01-01	18	54	親の故郷であるオーストラリアクイーンズランド州のユーカリの木の下に咲く花「リリーピリー」から命名。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
21	ななみ	2	2018-07-07	1	9999-01-01	28	27	お転婆。きららと腹違いの姉妹。自分の父親と子供を作る？ななみの父親はタイチ？？	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
22	りん	2	2017-08-23	1	9999-01-01	25	57	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
23	イシン	1	2017-05-14	1	9999-01-01	44	42	オスらしい立派な鼻と、キュッと上がった口角がかわいいイシン。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
24	クレメンツ	2	1997-11-20	1	9999-01-01	0	0	長寿。日本におけるコアラの長寿最高記録は、以前東山で暮らしていたオスの「ラム」の23歳11か月です。世界最高記録が25歳のようなので、あと4か月ほどで21歳になるクレメンツが、コアラとしてはかなりの高齢だということを感じて頂けるでしょうか。	http://www.higashiyama.city.nagoya.jp/blog/2018/07/post-3625.html	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
25	ティリー	2	2009-12-15	1	9999-01-01	0	0	"りん" "きらら" "インディコ"を育てあげたビッグママ（実際、体もビッグ!）。６児の母。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
26	ココ	2	2010-05-05	1	9999-01-01	72	71	小柄で可愛らしいココですが、実は...東山のメスたちの中で１番のマッチョ！腕から肩にかけて筋肉ムキムキで、枝の間も軽々と移動していく運動神経抜群。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
27	タイチ	1	2012-05-03	1	9999-01-01	0	0	未入力	5から1	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
28	ホリー	2	2013-12-15	1	9999-01-01	0	0	未入力	https://www.sankei.com/photo/daily/news/160328/dly1603280032-n1.html	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
29	エマ	2	2018-12-06	1	9999-01-01	30	32	投票で命名。外国語で「大きな愛情をもってほしい」という願いが込められている	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
32	ピーター	1	2016-03-28	1	9999-01-01	25	67	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
33	オウカ	2	2016-09-25	1	9999-01-01	30	49	桜の花のように美しいコアラに育つように。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
34	みどり	2	1997-02-01	1	9999-01-01	0	0	最長寿。ギネス世界記録に認定。2003（平成15）年3月21日にオーストラリアの西オーストラリア州パースにあるヤンチャップ国立公園。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
43	ジェイン	2	2014-11-19	1	9999-01-01	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
44	ユメ	2	2015-01-02	1	9999-01-01	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
45	リオ	2	2015-12-10	1	9999-01-01	0	0		未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
46	ワトル	2	2020-05-14	1	9999-01-01	21	27	とにかく可愛い。小柄な体格。オーストラリアの人たちになじみの深い国花にちなみ「ワトルWattle」と命名。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
49	ウルル	1	2007-05-26	0	2017-12-18	0	0	コタロウ(10)の父	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
50	ウメ	2	2010-08-05	1	9999-01-01	74	78	コタロウ(10)の母	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
14	チャーリー	1	2014-11-27	1	9999-01-01	25	58	母親のティリーがオーストラリアから来て日本で初めて産んだ子どもであり、父親のアーチャーが最後に残してくれた子	なし	https://res.cloudinary.com/honx7tf4c/image/upload/v1640432608/animal/14/profile/14.jpg	1	2	2021-06-19 14:43:59.213189	2021-12-25 20:43:28.718919	1
13	ぼたん	2	2017-05-12	1	9999-01-01	50	60	未入力	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1640432652/animal/13/profile/13.jpg	1	2	2021-06-19 14:43:59.213189	2021-12-25 20:44:13.505989	1
54	ピノ	1	2015-12-13	0	2020-05-25	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
60	アーティー	1	2012-08-11	0	2017-07-27	0	58	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
61	ひかり	2	1997-02-01	0	2020-02-01	0	0	未入力	https://www.kobe-np.co.jp/news/sougou/202002/0013080532.shtml	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
62	みなみ	1	1996-05-01	0	2018-08-05	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
63	ミライ	2	2009-10-25	0	2020-06-10	79	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
64	セイ	1	2018-06-26	0	2020-03-23	65	41	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
65	ココロ	2	2011-09-26	0	2019-12-25	0	49	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
18	ジンベラン	2	2013-02-23	1	9999-01-01	0	0	2015年1月にオーストラリアから来園したオスのボウ、メスのドリー、ジンベランの3頭のうちの1頭	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628402732/animal/18/profile/18.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 15:05:33.024509	1
16	リリー	2	2018-03-28	1	9999-01-01	53	17	親の故郷であるオーストラリアクイーンズランド州のユーカリの木の下に咲く花「リリーピリー」から命名。	元気いっぱい！食いしん坊！	https://res.cloudinary.com/honx7tf4c/image/upload/v1628407737/animal/16/profile/16.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 16:28:57.901481	1
19	クイン	2	2015-03-23	1	9999-01-01	53	69	大きなお顔。	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628407811/animal/19/profile/19.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-09 15:43:33.916679	1
15	ハニー	2	2014-08-03	1	9999-01-01	37	67	ハチミツの日にちなんでハニーと命名。	顔が真ん丸。	https://res.cloudinary.com/honx7tf4c/image/upload/v1628407909/animal/15/profile/15.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 16:31:49.47885	1
59	アーク	1	2006-12-15	1	9999-01-01	0	0	イギリスのロングリートサファリパークへ出園（2019年10月10日）。メルボルン動物園生まれ。	https://www.ktv.jp/news/feature/20191224/	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-09 17:53:04.58682	1
30	ウミ	2	2014-06-13	1	9999-01-01	50	49	未入力	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628524967/animal/30/profile/30.png	1	2	2021-06-19 14:43:59.213189	2021-08-10 01:02:47.850394	1
66	エミ	2	2013-08-08	0	2019-07-25	68	71	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
67	ピース	1	2011-06-06	0	2016-12-09	0	58	ハニーの父	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
70	ラム	1	1988-04-28	0	2012-03-30	0	0	長寿。	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
71	ティムタム	1	2005-09-03	0	2013-07-26	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
73	ミナト	1	2014-06-12	0	2018-12-20	74	49	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
74	モミジ	2	2006-05-29	1	9999-01-01	79	80	未入力	6から4	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
76	そら	1	2013-02-13	0	2017-10-17	44	59	香港へ	https://note.com/hapizooooo/n/ndab92b0ab75f	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
77	テル	2	2008-11-22	0	2016-01-29	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
78	パイン	1	2011-06-04	0	9999-01-01	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
79	ミリー	2	2020-08-23	1	9999-01-01	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
80	モモジ	1	1997-02-07	0	2007-02-17	0	0	未入力	未入力	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
1	インディコ	2	2019-12-22	1	9999-01-01	37	35	イシンにとって初めて、ティリーにとって６人目の赤ちゃん。オーストラリア先住民の言葉で月の意味。	丸顔。	\N	1	1	2021-06-19 14:43:59.213189	2021-06-19 14:43:59.213189	1
75	ゆめ	2	2009-01-01	0	2018-07-02	0	0	だいちとそらの母親。	未入力	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-10 14:18:21.553922	1
98	父方の祖母	2	1904-01-01	1	9999-01-01	0	0	aaa	aa	https://res.cloudinary.com/honx7tf4c/image/upload/v1624853620/koala/98/profile/98.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 20:20:55.201271	1
47	いぶき	1	2020-08-27	1	9999-01-01	28	23	父親のイシンに似ている..!?	誕生日要確認	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-10 09:52:55.321546	1
86	父親	1	1975-01-01	1	9999-01-01	98	89	ああ	あ	https://res.cloudinary.com/honx7tf4c/image/upload/v1624066292/koala/86/profile/86.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:27:15.154346	1
101	母方の祖母	2	1901-01-01	1	9999-01-01	0	0	aaa	aa	https://res.cloudinary.com/honx7tf4c/image/upload/v1621142995/koala/101/profile/101.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:20:31.033245	1
69	ボウ	1	2011-01-01	0	2016-07-26	0	0	2015年1月にオーストラリアから来園したオスのボウ、メスのドリー、ジンベランの3頭のうちの1頭	未入力	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-10 15:32:52.029777	1
48	つくし	2	2020-09-03	1	9999-01-01	22	23	母親譲りの濃い毛色	誕生日要確認	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-10 10:18:42.428947	1
104	義理の母①	2	2002-01-01	1	9999-01-01	0	0	あああ	ああああ	https://res.cloudinary.com/honx7tf4c/image/upload/v1621146289/koala/104/profile/104.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:28:39.452566	1
106	義理の兄	1	2019-01-01	0	9999-01-01	104	86	herokuのエラー画面が無条件で表示されてしまう	テスト	https://res.cloudinary.com/honx7tf4c/image/upload/v1621863231/koala/106/profile/106.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:25:01.227994	1
102	兄	1	1992-01-01	1	9999-01-01	88	86	aa	aa	https://res.cloudinary.com/honx7tf4c/image/upload/v1621143609/koala/102/profile/102.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:21:49.03538	1
85	メインコアラ	1	2008-01-01	1	9999-01-01	88	86	あ	あ	https://res.cloudinary.com/honx7tf4c/image/upload/v1624066247/koala/85/profile/85.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:22:33.468857	1
103	妹	2	2014-12-01	1	9999-01-01	88	86	aa	aa	https://res.cloudinary.com/honx7tf4c/image/upload/v1621143855/koala/103/profile/103.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-10 10:19:11.51419	1
89	父方の祖父	1	1901-01-01	1	9999-01-01	0	0	yhky	kgjgkj	https://res.cloudinary.com/honx7tf4c/image/upload/v1624066352/koala/89/profile/89.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:26:58.282225	1
52	コタロウ	1	2015-09-25	1	9999-01-01	50	49	ウルルとウメの２頭目の子供。	4から5	https://res.cloudinary.com/honx7tf4c/image/upload/v1628385729/animal/52/profile/52.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-08 10:22:09.55352	1
105	義理の妹	2	2013-01-01	1	9999-01-01	104	86	a	a	https://res.cloudinary.com/honx7tf4c/image/upload/v1621511259/koala/105/profile/105.jpg	1	2	2021-06-19 14:43:59.213189	2021-08-10 10:20:28.470126	1
99	母方の祖父	1	1906-01-01	1	9999-01-01	0	0	aaa	aaa	https://res.cloudinary.com/honx7tf4c/image/upload/v1621139530/koala/99/profile/99.jpg	1	2	2021-06-19 14:43:59.213189	2021-06-28 21:31:38.283195	1
72	コウメ	2	2007-07-22	0	2015-05-28	142	141	コタロウの母。メイの最後の子。	未入力	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-09 17:55:37.392877	1
31	シャイン	1	2017-08-31	1	9999-01-01	19	17	名前募集の期間中はお母さんの袋に入っていることが多く、なかなか顔を見せてくれなかったための命名	未入力	https://res.cloudinary.com/honx7tf4c/image/upload/v1628496971/animal/31/profile/31.png	1	6	2021-06-19 14:43:59.213189	2021-08-10 12:44:05.141738	1
68	ライチ	2	2009-11-11	0	2015-05-28	0	0	ハニーの母	未入力	/images/defaultAnimal.png	1	2	2021-06-19 14:43:59.213189	2021-08-09 17:56:10.930537	1
\.


--
-- Data for Name: animal_post_tag; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.animal_post_tag (id, animal_id, post_id, created_date, updated_date, created_by, updated_by) FROM stdin;
\.


--
-- Data for Name: animal_type; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.animal_type (animal_type_id, name, created_date, updated_date, created_by, updated_by) FROM stdin;
1	コアラ	2021-07-04 11:34:28.795197	2021-07-04 11:34:28.795197	1	1
\.


--
-- Data for Name: animal_zoo_history; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.animal_zoo_history (animal_zoo_history_id, animal_id, zoo_id, admission_date, exit_date, created_by, updated_by, created_date, updated_date) FROM stdin;
262	31	3	9999-01-01	9999-01-01	6	6	2021-08-10 12:44:05.709063	2021-08-10 12:44:05.709063
264	69	3	9999-01-01	9999-01-01	2	2	2021-08-10 15:32:52.037352	2021-08-10 15:32:52.037352
184	9	5	9999-01-01	9999-01-01	2	2	2021-08-08 10:16:43.611593	2021-08-08 10:16:43.611593
266	53	3	9999-01-01	9999-01-01	2	2	2021-08-10 22:20:20.223507	2021-08-10 22:20:20.223507
188	11	5	9999-01-01	9999-01-01	2	2	2021-08-08 10:21:02.004434	2021-08-08 10:21:02.004434
189	17	5	9999-01-01	9999-01-01	2	2	2021-08-08 10:21:29.950378	2021-08-08 10:21:29.950378
190	52	5	9999-01-01	9999-01-01	2	2	2021-08-08 10:22:09.560487	2021-08-08 10:22:09.560487
191	12	5	9999-01-01	9999-01-01	2	2	2021-08-08 10:22:36.37965	2021-08-08 10:22:36.37965
287	141	2	1995-07-15	2003-05-15	2	2	2021-08-22 23:36:00.571577	2021-08-22 23:36:00.571577
288	141	5	2003-05-15	2007-05-24	2	2	2021-08-22 23:36:00.575824	2021-08-22 23:36:00.575824
289	141	3	2007-05-24	2007-11-12	2	2	2021-08-22 23:36:00.579466	2021-08-22 23:36:00.579466
207	16	3	9999-01-01	9999-01-01	2	2	2021-08-08 16:28:57.910849	2021-08-08 16:28:57.910849
209	15	3	9999-01-01	9999-01-01	2	2	2021-08-08 16:31:49.490463	2021-08-08 16:31:49.490463
243	58	1	2010-11-08	9999-01-01	2	2	2021-08-09 17:51:24.48876	2021-08-09 17:51:24.48876
244	59	0	9999-01-01	9999-01-01	2	2	2021-08-09 17:53:05.142841	2021-08-09 17:53:05.142841
338	181	1	2021-03-04	2017-04-04	2	2	2021-12-03 20:11:22.722834	2021-12-03 20:11:22.722834
339	181	4	2017-04-04	2016-01-01	2	2	2021-12-03 20:11:23.098185	2021-12-03 20:11:23.098185
251	47	1	2020-08-27	9999-01-01	2	2	2021-08-10 09:52:55.330862	2021-08-10 09:52:55.330862
340	55	6	2013-07-27	2021-11-08	2	2	2021-12-19 10:06:37.24216	2021-12-19 10:06:37.24216
345	6	3	2019-04-02	2021-11-25	2	2	2021-12-25 20:43:03.808581	2021-12-25 20:43:03.808581
346	6	6	2021-11-25	9999-01-01	2	2	2021-12-25 20:43:03.811882	2021-12-25 20:43:03.811882
347	14	6	2014-11-27	9999-01-01	2	2	2021-12-25 20:43:28.724365	2021-12-25 20:43:28.724365
348	13	6	2017-05-12	9999-01-01	2	2	2021-12-25 20:44:13.511405	2021-12-25 20:44:13.511405
2	2	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
3	3	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
349	182	6	2020-04-26	9999-01-01	2	2	2021-12-25 20:44:42.126432	2021-12-25 20:44:42.126432
5	5	3	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
7	7	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
8	8	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
20	20	3	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
21	21	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
22	22	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
23	23	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
24	24	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
25	25	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
26	26	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
27	27	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
28	28	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
29	29	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
32	32	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
33	33	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
34	34	7	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
35	35	6	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
36	36	7	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
37	37	7	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
38	38	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
39	39	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
40	40	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
41	41	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
42	42	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
43	43	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
44	44	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
45	45	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
46	46	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
263	75	7	9999-01-01	9999-01-01	2	2	2021-08-10 14:18:21.560768	2021-08-10 14:18:21.560768
49	49	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
50	50	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
265	142	3	9999-01-01	9999-01-01	2	2	2021-08-10 17:19:04.528455	2021-08-10 17:19:04.528455
54	54	3	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
56	56	6	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
57	57	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
60	60	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
61	61	7	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
62	62	7	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
63	63	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
64	64	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
65	65	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
66	66	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
67	67	3	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
70	70	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
71	71	3	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
73	73	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
74	74	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
76	76	0	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
77	77	6	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
78	78	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
79	79	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
80	80	4	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
1	1	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
192	18	3	9999-01-01	9999-01-01	2	2	2021-08-08 15:05:33.081298	2021-08-08 15:05:33.081298
194	4	3	9999-01-01	9999-01-01	2	2	2021-08-08 15:10:56.306583	2021-08-08 15:10:56.306583
203	51	5	9999-01-01	9999-01-01	2	2	2021-08-08 16:09:46.711528	2021-08-08 16:09:46.711528
204	10	3	2011-07-28	2021-07-23	2	2	2021-08-08 16:25:30.350615	2021-08-08 16:25:30.350615
98	98	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
101	101	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
102	102	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
85	85	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
88	88	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
106	106	3	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
89	89	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
86	86	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
104	104	1	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
213	19	3	9999-01-01	9999-01-01	2	2	2021-08-09 15:43:34.426779	2021-08-09 15:43:34.426779
99	99	2	9999-01-01	9999-01-01	1	1	2021-06-19 14:44:53.43786	2021-06-19 14:44:53.43786
108	108	0	9999-01-01	9999-01-01	6	6	2021-06-20 19:43:33.341305	2021-06-20 19:43:33.341305
247	72	4	9999-01-01	9999-01-01	2	2	2021-08-09 17:55:37.9316	2021-08-09 17:55:37.9316
248	68	3	9999-01-01	9999-01-01	2	2	2021-08-09 17:56:11.479658	2021-08-09 17:56:11.479658
250	30	4	9999-01-01	9999-01-01	2	2	2021-08-10 01:02:47.885294	2021-08-10 01:02:47.885294
252	48	1	2020-09-03	9999-01-01	2	2	2021-08-10 10:18:42.516266	2021-08-10 10:18:42.516266
253	103	1	9999-01-01	9999-01-01	2	2	2021-08-10 10:19:11.520434	2021-08-10 10:19:11.520434
254	133	11	9999-01-01	9999-01-01	2	2	2021-08-10 10:19:57.722261	2021-08-10 10:19:57.722261
255	105	1	9999-01-01	9999-01-01	2	2	2021-08-10 10:20:28.477409	2021-08-10 10:20:28.477409
\.


--
-- Data for Name: login_user; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.login_user (user_id, provider, provider_id, provider_adress, user_name, profile, role, status, auto_login, login_date, created_date, updated_date, created_by, updated_by, profileimagepath, twitterlinkflag, favorite_zoo) FROM stdin;
9	twitter	2574707400	yhaccont	ＨＹ	\N	ROLE_USER	0	3ef6ffa723e94948591d5f75f4c575987c0b7a9c15e02aa88ad8a3070b7ac913	2022-08-21 22:47:57.406613	2021-06-23 12:00:31.985961	2022-08-21 22:47:57.891357	9	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1624417283/user/9/profile/9.jpg	t	5
11	twitter	1209350576	takatsu_1117	たかぎ	\N	ROLE_USER	0	816d575e9978e50f1f24bc2dc7428c21a64d97b38eea1346fd44d3eeb83925b6	2022-08-13 16:48:24.942407	2022-07-19 22:59:12.643613	2022-08-13 16:48:24.763737	11	11	/images/users/profile/defaultUser.png	t	5
2	twitter	1257940078266998784	michi_taira_23	みち	趣味はカメラとサウナです。ネットフリックスもよく見ます。将来は全国の動物園を訪問したいと思っています。	ROLE_USER	0	19bc5594fcf1de4ecaecfc5193d71a0982d406cf837ef4e80c70a8b83acfe803	2022-08-27 20:23:14.771685	2021-06-19 14:41:03.551574	2022-08-27 20:23:15.488938	2	2	https://res.cloudinary.com/honx7tf4c/image/upload/v1628498241/user/2/profile/2.jpg	t	5
10	twitter	1421960080417648640	koamanitest	コアラテスト	\N	ROLE_USER	0	3ede8d2337318f18e80ec506cbf5d25fa6bb14076c061319e7c63a3957ee1684	2021-08-02 08:36:47.278395	2021-08-02 07:27:22.393308	2021-08-02 08:36:47.78769	10	10	/images/users/profile/defaultUser.png	t	5
1	twitter	1374679230374539267	zakowebengineer	雑魚キャラ	上野投手お疲れ様でした。\r\n\r\nお姉ちゃんと呼ばせてもらってます。\r\n\r\nパリでは、ソフトボールは競技から除外されます。\r\nしかし、あなたの功績は永遠に輝きます。	ROLE_USER	0	5ef9d10a9887bde0ada6c423b0965932eacf2f2e997b07407e272abf1af46730	2022-08-21 22:45:27.878809	2021-06-19 14:41:03.551574	2022-08-21 22:45:35.060223	1	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627868005/user/1/profile/1.jpg	t	5
6	twitter	961884733	yoshitowww	コアラマニア大好き	コアラが好きです。\r\n\r\nよろしくお願いします。	ROLE_USER	0	d9b4e5310fba8bc0da889aa7c5d17bc8575328963cc56b63f72b02462a06bed8	2021-08-10 21:11:50.674454	2021-06-20 15:30:45.417127	2021-08-10 21:11:50.49356	6	6	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779086/user/6/profile/6.png	t	5
\.


--
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.post (post_id, user_id, zoo_id, parent_id, contents, created_date, updated_date, created_by, updated_by, visit_date, title) FROM stdin;
24	1	5	0	携帯からテスト\r\n\r\nレスポンシブ対応はめんどくさいです。\r\n\r\n上野投手、お疲れ様です。	2021-08-01 23:48:48.153235	2021-08-01 23:48:48.153235	1	1	9999-01-01	title
25	6	5	0	本番でうまくいかない	2021-08-01 23:49:46.835142	2021-08-01 23:49:46.835142	6	6	2021-08-01	title
5	1	5	0	呪い呪われた僕の未来を想像して\r\nこの一瞬を\r\n上場的感情が揺らいでくバグ	2021-07-25 18:03:15.702089	2021-07-25 18:03:15.702089	1	1	2021-07-23	title
6	1	5	0	あとはパパらぱっぱ中身なき人間\r\n愛足して\r\nまだ止めないで\r\n誰よりも走って転んで　世界が待ってる\r\n順々に従ってく従順な罰\r\n\r\n命を投げ出せないで\r\nうちのもろさに浸って\r\n\r\n五条を解いて　\r\n\r\n五条を\r\n\r\nただ追いかけて\r\n君の運命すら\r\nただ\r\n\r\n深く深く落ち込んで	2021-07-25 18:08:09.665268	2021-07-25 18:08:09.665268	1	1	2021-07-07	title
7	1	5	0	世界が待ってる\r\nこのあｇｓｇ\r\n\r\nがｓｇｄさ\r\nがｓｇｄさ\r\n\r\nがｓｇさ\r\nがｓｆｄさ\r\nがｇｄヴぁ\r\n\r\nがｓ\r\n\r\nあｇｓｄ\r\nがｓｇｄ	2021-07-25 18:08:47.145598	2021-07-25 18:08:47.145598	1	1	2021-07-23	title
8	1	5	0	ｇｓだふぁｓ\r\nｇｓだ\r\n\r\nがｄふぁｓ\r\n\r\n\r\nがｓｄ\r\n\r\nあｓｇｄ\r\n\r\n\r\nｇさ\r\n\r\nあｇｓｄが	2021-07-25 18:10:27.64669	2021-07-25 18:10:27.64669	1	1	2021-07-14	title
9	1	5	0	闇を払って　\r\n\r\n夜のとばりが下りたら合図だ\r\n\r\n相対して、回る環状線	2021-07-31 22:33:33.390469	2021-07-31 22:33:33.390469	1	1	2021-07-28	title
102	2	0	0	テストです	2022-07-08 19:52:13.347346	2022-07-08 19:52:13.347346	2	2	2022-07-08	title
99	2	0	0	テストです	\N	\N	\N	\N	2022-07-07	title
98	2	0	0	テストです	2022-07-06 20:02:17.822696	2022-07-06 20:02:17.822696	2	2	\N	title
95	2	0	0	テストです	\N	\N	\N	\N	\N	title
111	1	1	88	つと	2022-07-24 12:24:28.354389	2022-07-24 12:24:28.354389	1	1	9999-01-01	\N
10	6	5	0	昨日、動物園に行きました。\r\nとても可愛かったです。\r\n\r\nまた、行きたいです。\r\n\r\n以上、よろしくお願いします。\r\n\r\n\r\n承知しました。	2021-08-01 00:03:41.647267	2021-08-01 00:03:41.647267	6	6	2021-07-31	title
11	6	5	0	今日あいさつします。\r\nこんばんは。\r\n\r\nおはよう。	2021-08-01 00:05:28.785609	2021-08-01 00:05:28.785609	6	6	2021-07-13	title
12	9	5	0	こんにちは！\r\n\r\n今日、ニシチをたくさん撮影しました。\r\n\r\n最高にかわいいです！💛\r\nあとキララも撮りました。\r\n乙です。	2021-08-01 09:58:56.595973	2021-08-01 09:58:56.595973	9	9	2021-07-23	title
13	9	5	0	腹減った	2021-08-01 13:56:38.767499	2021-08-01 13:56:38.767499	9	9	9999-01-01	title
14	9	0	12	いい写真ですね！	2021-08-01 14:21:02.558475	2021-08-01 14:21:02.558475	9	9	9999-01-01	title
15	9	0	12	最高の写真です！\r\n僕も撮りたいです。	2021-08-01 14:23:05.807199	2021-08-01 14:23:05.807199	9	9	9999-01-01	title
16	9	0	13	ニシチ～～～\r\n応援してます。	2021-08-01 21:40:10.073357	2021-08-01 21:40:10.073357	9	9	9999-01-01	title
103	2	0	0	テストです	2022-07-08 20:01:07.061806	2022-07-08 20:01:07.061806	2	2	2022-07-08	title
17	9	0	13	ニシチ～～～\r\n応援してます。	2021-08-01 21:41:38.633997	2021-08-01 21:41:38.633997	9	9	9999-01-01	title
100	2	0	0	テストです	2022-07-07 23:49:54.769634	2022-07-07 23:49:54.769634	2	2	2022-07-07	title
19	6	0	13	なんで	2021-08-01 21:51:24.203146	2021-08-01 21:51:24.203146	6	6	9999-01-01	title
96	2	0	0	テストです	2022-07-06 19:46:15.606535	2022-07-06 19:46:15.606535	2	2	\N	title
20	6	0	13	意味が分からない	2021-08-01 21:54:20.575058	2021-08-01 21:54:20.575058	6	6	9999-01-01	title
94	2	0	0	bbb	\N	\N	\N	\N	\N	title
21	6	0	13	ぎぎｇ	2021-08-01 22:02:35.584778	2021-08-01 22:02:35.584778	6	6	9999-01-01	title
22	1	0	13	こんにちは\r\nよろしくお願いします。	2021-08-01 22:13:43.750459	2021-08-01 22:13:43.750459	1	1	9999-01-01	title
23	6	5	0	テスト	2021-08-01 23:48:21.260135	2021-08-01 23:48:21.260135	6	6	2021-08-01	title
26	6	5	0	おかしい、うまくいかない	2021-08-02 00:00:10.245752	2021-08-02 00:00:10.245752	6	6	2021-08-01	title
27	6	5	0	写真があっぷできない	2021-08-02 00:02:11.707735	2021-08-02 00:02:11.707735	6	6	2021-07-07	title
28	6	5	0	写真があっぷできない	2021-08-02 00:05:15.697177	2021-08-02 00:05:15.697177	6	6	2021-08-02	title
29	6	5	0	意味が分からない	2021-08-02 00:11:24.074058	2021-08-02 00:11:24.074058	6	6	2021-08-01	title
30	6	5	0	もう疲れた。\r\nバグ多い、、	2021-08-02 00:51:10.811887	2021-08-02 00:51:10.811887	6	6	2021-08-02	title
31	1	5	0	herokuから投稿できるか試験	2021-08-02 00:58:42.583077	2021-08-02 00:58:42.583077	1	1	2021-08-02	title
32	1	0	31	上野投手お疲れ様！	2021-08-02 00:59:59.414251	2021-08-02 00:59:59.414251	1	1	9999-01-01	title
33	6	0	31	この川に入りたいです	2021-08-02 01:01:47.725179	2021-08-02 01:01:47.725179	6	6	9999-01-01	title
34	6	0	31	僕も入りたい！	2021-08-02 01:02:02.128571	2021-08-02 01:02:02.128571	6	6	9999-01-01	title
36	10	5	0	コアラテスト	2021-08-02 07:29:50.831916	2021-08-02 07:29:50.831916	10	10	9999-01-01	title
37	10	5	0	テスト	2021-08-02 07:59:39.939163	2021-08-02 07:59:39.939163	10	10	2021-08-02	title
38	10	0	31	眠いです。	2021-08-02 08:15:29.192643	2021-08-02 08:15:29.192643	10	10	9999-01-01	title
39	10	5	0	容量テスト	2021-08-02 08:30:37.215638	2021-08-02 08:30:37.215638	10	10	2021-08-01	title
40	10	5	0	herokuのメモリ容量が心配です。	2021-08-02 08:36:17.111333	2021-08-02 08:36:17.111333	10	10	2021-07-08	title
41	1	0	40	意味わかない、写真上げるのやめてもらっていいですか？\r\n通報します。	2021-08-02 08:37:45.09288	2021-08-02 08:37:45.09288	1	1	9999-01-01	title
87	2	1	0	test1	2022-06-24 20:43:52.498927	2022-06-24 20:43:52.498927	2	2	2022-06-23	title
42	1	0	40	まだ、通報機能ないんですけどね、、	2021-08-02 08:38:03.645574	2021-08-02 08:38:03.645574	1	1	9999-01-01	title
43	6	0	40	画質悪いな\r\n修正が必要ですね。	2021-08-02 08:39:27.272303	2021-08-02 08:39:27.272303	6	6	9999-01-01	title
49	1	5	0	七夕の日に上野投手に会いたい。\r\n\r\nそう、星に願いました。\r\n\r\nペンギンも撮ったので載せまーす。	2021-08-02 10:50:12.466582	2021-08-02 10:50:12.466582	1	1	2021-07-07	title
57	1	5	0	テスト投稿。\r\n何故か眠い	2021-08-03 09:12:11.451295	2021-08-03 09:12:11.451295	1	1	2021-08-03	title
59	9	0	31	川入れるわけねーだろ	2021-08-03 11:26:21.739955	2021-08-03 11:26:21.739955	9	9	9999-01-01	title
60	6	5	0	新しく投稿します。	2021-08-10 12:58:06.253316	2021-08-10 12:58:06.253316	6	6	2021-08-10	title
61	6	0	60	うざすぎる	2021-08-10 12:59:15.485899	2021-08-10 12:59:15.485899	6	6	9999-01-01	title
62	9	5	0	価格が、上がりません。	2021-08-10 13:12:28.85705	2021-08-10 13:12:28.85705	9	9	2021-08-09	title
63	6	0	60	さっきはごめんなさい。	2021-08-10 19:24:52.344732	2021-08-10 19:24:52.344732	6	6	9999-01-01	title
64	1	0	62	ありがとう。	2021-08-11 01:09:22.649287	2021-08-11 01:09:22.649287	1	1	9999-01-01	title
67	2	0	62	ありがとうな。	2021-08-11 22:29:13.024579	2021-08-11 22:29:13.024579	2	2	9999-01-01	title
101	2	0	0	テストです	2022-07-07 23:55:55.870864	2022-07-07 23:55:55.870864	2	2	2022-07-07	title
97	2	0	0	テストです	2022-07-06 19:53:17.132597	2022-07-06 19:53:17.132597	2	2	\N	title
88	2	1	0	test2	2022-06-24 20:44:32.750521	2022-06-24 20:44:32.750521	2	2	2022-06-23	title
122	1	3	0	ドリー	2022-07-24 18:29:38.540451	2022-07-24 18:29:38.540451	1	1	9999-01-01	\N
\.


--
-- Data for Name: post_favorite; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.post_favorite (post_favorite_id, user_id, post_id, created_date, updated_date, created_by, updated_by) FROM stdin;
52	6	62	2021-08-11 22:25:39.493061	2021-08-11 22:25:39.493061	2	2
54	2	62	2021-08-11 22:25:39.493061	2021-08-11 22:25:39.493061	2	2
82	1	88	2022-06-25 10:34:06.218456	2022-06-25 10:34:06.218456	1	1
58	2	57	2021-08-11 22:39:33.812101	2021-08-11 22:39:33.812101	1	1
60	1	57	2021-08-11 22:39:33.812101	2021-08-11 22:39:33.812101	1	1
55	2	60	2021-08-11 22:39:38.465845	2021-08-11 22:39:38.465845	1	1
51	6	60	2021-08-11 22:39:38.465845	2021-08-11 22:39:38.465845	1	1
61	1	60	2021-08-11 22:39:38.465845	2021-08-11 22:39:38.465845	1	1
83	1	87	2022-06-25 10:34:09.208493	2022-06-25 10:34:09.208493	1	1
87	1	122	2022-07-24 18:32:04.527763	2022-07-24 18:32:04.527763	1	1
42	6	40	2021-08-09 11:26:33.961687	2021-08-09 11:26:33.961687	6	6
46	6	31	2021-08-09 11:57:35.175587	2021-08-09 11:57:35.175587	6	6
47	6	28	2021-08-09 12:11:38.461153	2021-08-09 12:11:38.461153	6	6
48	6	34	2021-08-10 11:59:42.334533	2021-08-10 11:59:42.334533	6	6
49	6	42	2021-08-10 12:08:52.058884	2021-08-10 12:08:52.058884	6	6
50	6	12	2021-08-10 12:21:39.173246	2021-08-10 12:21:39.173246	6	6
\.


--
-- Data for Name: post_image; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.post_image (postimage_id, post_id, animal_id, image_address, created_date, updated_date, created_by, updated_by) FROM stdin;
27	11	51	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743934/post/11/27.png	2021-08-01 00:05:33.384042	2021-08-01 00:05:33.384042	6	6
58	39	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860639/post/39/58.jpg	2021-08-02 08:30:37.246296	2021-08-02 08:30:37.246296	10	10
12	5	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627203803/post/5/12.png	2021-07-25 18:03:22.172876	2021-07-25 18:03:22.172876	1	1
28	12	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779538/post/12/28.png	2021-08-01 09:58:57.548234	2021-08-01 09:58:57.548234	9	9
59	39	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860642/post/39/59.jpg	2021-08-02 08:30:42.110823	2021-08-02 08:30:42.110823	10	10
29	12	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779542/post/12/29.jpg	2021-08-01 09:59:01.626738	2021-08-01 09:59:01.626738	9	9
9	5	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627203797/post/5/9.png	2021-07-25 18:03:16.212017	2021-07-25 18:03:16.212017	1	1
10	5	140	https://res.cloudinary.com/honx7tf4c/image/upload/v1627203799/post/5/10.png	2021-07-25 18:03:18.357577	2021-07-25 18:03:18.357577	1	1
30	12	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779546/post/12/30.jpg	2021-08-01 09:59:04.917963	2021-08-01 09:59:04.917963	9	9
32	12	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779552/post/12/32.jpg	2021-08-01 09:59:11.80593	2021-08-01 09:59:11.80593	9	9
21	10	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743822/post/10/21.jpg	2021-08-01 00:03:42.143751	2021-08-01 00:03:42.143751	6	6
13	6	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627204091/post/6/13.png	2021-07-25 18:08:10.158626	2021-07-25 18:08:10.158626	1	1
31	12	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-01 09:59:08.664804	2021-08-01 09:59:08.664804	9	9
14	6	17	https://res.cloudinary.com/honx7tf4c/image/upload/v1627204093/post/6/14.jpg	2021-07-25 18:08:12.121563	2021-07-25 18:08:12.121563	1	1
15	6	140	https://res.cloudinary.com/honx7tf4c/image/upload/v1627204094/post/6/15.png	2021-07-25 18:08:14.067318	2021-07-25 18:08:14.067318	1	1
60	39	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860646/post/39/60.jpg	2021-08-02 08:30:45.325583	2021-08-02 08:30:45.325583	10	10
16	6	140	https://res.cloudinary.com/honx7tf4c/image/upload/v1627204096/post/6/16.jpg	2021-07-25 18:08:16.108493	2021-07-25 18:08:16.108493	1	1
23	10	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743827/post/10/23.jpg	2021-08-01 00:03:47.096195	2021-08-01 00:03:47.096195	6	6
17	6	140	https://res.cloudinary.com/honx7tf4c/image/upload/v1627204099/post/6/17.png	2021-07-25 18:08:18.007071	2021-07-25 18:08:18.007071	1	1
18	9	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627738416/post/9/18.png	2021-07-31 22:33:33.900987	2021-07-31 22:33:33.900987	1	1
39	26	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-02 00:00:10.751613	2021-08-02 00:00:10.751613	6	6
19	9	51	https://res.cloudinary.com/honx7tf4c/image/upload/v1627738417/post/9/19.png	2021-07-31 22:33:37.15144	2021-07-31 22:33:37.15144	1	1
33	13	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627793800/post/13/33.jpg	2021-08-01 13:56:39.477373	2021-08-01 13:56:39.477373	9	9
20	9	17	https://res.cloudinary.com/honx7tf4c/image/upload/v1627738420/post/9/20.jpg	2021-07-31 22:33:39.167865	2021-07-31 22:33:39.167865	1	1
25	11	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743930/post/11/25.png	2021-08-01 00:05:29.273993	2021-08-01 00:05:29.273993	6	6
22	10	12	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743825/post/10/22.png	2021-08-01 00:03:44.369663	2021-08-01 00:03:44.369663	6	6
62	39	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860650/post/39/62.jpg	2021-08-02 08:30:49.652927	2021-08-02 08:30:49.652927	10	10
24	10	52	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743829/post/10/24.png	2021-08-01 00:03:48.908477	2021-08-01 00:03:48.908477	6	6
61	39	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860648/post/39/61.jpg	2021-08-02 08:30:48.256365	2021-08-02 08:30:48.256365	10	10
63	40	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860977/post/40/63.jpg	2021-08-02 08:36:17.117374	2021-08-02 08:36:17.117374	10	10
26	11	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627743932/post/11/26.png	2021-08-01 00:05:31.497021	2021-08-01 00:05:31.497021	6	6
34	23	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627793800/post/13/33.jpg	2021-08-01 23:48:21.764847	2021-08-01 23:48:21.764847	6	6
11	5	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627203801/post/5/11.png	2021-07-25 18:03:20.272551	2021-07-25 18:03:20.272551	1	1
38	25	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627793800/post/13/33.jpg	2021-08-01 23:49:48.20907	2021-08-01 23:49:48.20907	6	6
114	87	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1656071035/post/87/114.png	2022-06-24 20:43:53.055987	2022-06-24 20:43:53.055987	2	2
35	24	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627793800/post/13/33.jpg	2021-08-01 23:48:48.184236	2021-08-01 23:48:48.184236	1	1
36	24	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627793800/post/13/33.jpg	2021-08-01 23:48:49.565692	2021-08-01 23:48:49.565692	1	1
37	25	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627793800/post/13/33.jpg	2021-08-01 23:49:47.319465	2021-08-01 23:49:47.319465	6	6
42	27	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-02 00:02:13.955449	2021-08-02 00:02:13.955449	6	6
41	27	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-02 00:02:13.072589	2021-08-02 00:02:13.072589	6	6
64	40	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860979/post/40/64.jpg	2021-08-02 08:36:17.705616	2021-08-02 08:36:17.705616	10	10
55	36	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627856992/post/36/55.jpg	2021-08-02 07:29:50.838378	2021-08-02 07:29:50.838378	10	10
40	27	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-02 00:02:12.195579	2021-08-02 00:02:12.195579	6	6
43	28	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-02 00:05:37.077382	2021-08-02 00:05:37.077382	6	6
44	28	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627779549/post/12/31.png	2021-08-02 00:05:59.132988	2021-08-02 00:05:59.132988	6	6
45	29	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627830685/post/29/45.png	2021-08-02 00:11:24.561841	2021-08-02 00:11:24.561841	6	6
66	40	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860981/post/40/66.jpg	2021-08-02 08:36:20.944144	2021-08-02 08:36:20.944144	10	10
67	40	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860983/post/40/67.jpg	2021-08-02 08:36:22.105032	2021-08-02 08:36:22.105032	10	10
47	30	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833072/post/30/47.png	2021-08-02 00:51:11.302602	2021-08-02 00:51:11.302602	6	6
85	57	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627949539/post/57/85.jpg	2021-08-03 09:12:19.098943	2021-08-03 09:12:19.098943	1	1
48	30	12	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833074/post/30/48.jpg	2021-08-02 00:51:13.459565	2021-08-02 00:51:13.459565	6	6
71	49	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627869016/post/49/71.jpg	2021-08-02 10:50:15.64169	2021-08-02 10:50:15.64169	1	1
84	57	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627949538/post/57/84.jpg	2021-08-03 09:12:18.35114	2021-08-03 09:12:18.35114	1	1
88	60	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1628567893/post/60/88.jpg	2021-08-10 12:58:11.652449	2021-08-10 12:58:11.652449	6	6
89	60	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1628567895/post/60/89.jpg	2021-08-10 12:58:14.522502	2021-08-10 12:58:14.522502	6	6
51	30	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833080/post/30/51.jpg	2021-08-02 00:51:19.645055	2021-08-02 00:51:19.645055	6	6
52	31	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833525/post/31/52.jpg	2021-08-02 00:58:42.638084	2021-08-02 00:58:42.638084	1	1
53	31	17	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833525/post/31/53.jpg	2021-08-02 00:58:45.516732	2021-08-02 00:58:45.516732	1	1
90	60	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1628567898/post/60/90.png	2021-08-10 12:58:16.914314	2021-08-10 12:58:16.914314	6	6
86	60	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1628567888/post/60/86.jpg	2021-08-10 12:58:06.783715	2021-08-10 12:58:06.783715	6	6
69	49	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627869013/post/49/69.jpg	2021-08-02 10:50:12.482575	2021-08-02 10:50:12.482575	1	1
70	49	52	https://res.cloudinary.com/honx7tf4c/image/upload/v1627869015/post/49/70.jpg	2021-08-02 10:50:13.965436	2021-08-02 10:50:13.965436	1	1
46	29	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627830688/post/29/46.png	2021-08-02 00:11:27.267984	2021-08-02 00:11:27.267984	6	6
87	60	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1628567890/post/60/87.jpg	2021-08-10 12:58:09.266782	2021-08-10 12:58:09.266782	6	6
56	37	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627858780/post/37/56.jpg	2021-08-02 07:59:39.949981	2021-08-02 07:59:39.949981	10	10
49	30	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833076/post/30/49.png	2021-08-02 00:51:15.560098	2021-08-02 00:51:15.560098	6	6
50	30	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833078/post/30/50.jpg	2021-08-02 00:51:17.696117	2021-08-02 00:51:17.696117	6	6
57	37	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627858781/post/37/57.jpg	2021-08-02 07:59:40.36763	2021-08-02 07:59:40.36763	10	10
91	62	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1628568748/post/62/91.jpg	2021-08-10 13:12:28.899183	2021-08-10 13:12:28.899183	9	9
65	40	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627860980/post/40/65.jpg	2021-08-02 08:36:19.232881	2021-08-02 08:36:19.232881	10	10
54	31	1	https://res.cloudinary.com/honx7tf4c/image/upload/v1627833527/post/31/54.jpg	2021-08-02 00:58:46.227201	2021-08-02 00:58:46.227201	1	1
92	62	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1628568749/post/62/92.jpg	2021-08-10 13:12:29.314129	2021-08-10 13:12:29.314129	9	9
82	57	9	https://res.cloudinary.com/honx7tf4c/image/upload/v1627949535/post/57/82.jpg	2021-08-03 09:12:11.582509	2021-08-03 09:12:11.582509	1	1
123	122	53	https://res.cloudinary.com/honx7tf4c/image/upload/v1658654981/post/122/123.jpg	2022-07-24 18:29:39.026904	2022-07-24 18:29:39.026904	1	1
83	57	11	https://res.cloudinary.com/honx7tf4c/image/upload/v1627949537/post/57/83.jpg	2021-08-03 09:12:16.12605	2021-08-03 09:12:16.12605	1	1
\.


--
-- Data for Name: post_image_favorite; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.post_image_favorite (postimagefavorite_id, user_id, postimage_id, created_date, updated_date, created_by, updated_by) FROM stdin;
30	6	70	2021-08-10 17:51:29.879936	2021-08-10 17:51:29.879936	6	6
31	6	26	2021-08-10 19:38:41.748832	2021-08-10 19:38:41.748832	6	6
33	6	24	2021-08-10 23:24:06.850451	2021-08-10 23:24:06.850451	6	6
35	9	84	2021-08-11 08:41:57.515558	2021-08-11 08:41:57.515558	9	9
36	9	69	2021-08-11 08:42:16.750664	2021-08-11 08:42:16.750664	9	9
37	9	47	2021-08-11 08:42:23.839767	2021-08-11 08:42:23.839767	9	9
40	1	84	2021-08-12 00:15:09.918651	2021-08-12 00:15:09.918651	1	1
41	1	55	2022-07-24 14:11:02.331629	2022-07-24 14:11:02.331629	1	1
\.


--
-- Data for Name: prefecture; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.prefecture (prefecture_id, prefecture_name, prefecture_name_kana) FROM stdin;
1	北海道	ホッカイドウ
2	青森県	アオモリケン
3	岩手県	イワテケン
4	宮城県	ミヤギケン
5	秋田県	アキタケン
6	山形県	ヤマガタケン
7	福島県	フクシマケン
8	茨城県	イバラキケン
9	栃木県	トチギケン
10	群馬県	グンマケン
11	埼玉県	サイタマケン
12	千葉県	チバケン
13	東京都	トウキョウト
14	神奈川県	カナガワケン
15	新潟県	ニイガタケン
16	富山県	トヤマケン
17	石川県	イシカワケン
18	福井県	フクイケン
19	山梨県	ヤマナシケン
20	長野県	ナガノケン
21	岐阜県	ギフケン
22	静岡県	シズオカケン
23	愛知県	アイチケン
24	三重県	ミエケン
25	滋賀県	シガケン
26	京都府	キョウトフ
27	大阪府	オオサカフ
28	兵庫県	ヒョウゴケン
29	奈良県	ナラケン
30	和歌山県	ワカヤマケン
31	鳥取県	トットリケン
32	島根県	シマネケン
33	岡山県	オカヤマケン
34	広島県	ヒロシマケン
35	山口県	ヤマグチケン
36	徳島県	トクシマケン
37	香川県	カガワケン
38	愛媛県	エヒメケン
39	高知県	コウチケン
40	福岡県	フクオカケン
41	佐賀県	サガケン
42	長崎県	ナガサキケン
43	熊本県	クマモトケン
44	大分県	オオイタケン
45	宮崎県	ミヤザキケン
46	鹿児島県	カゴシマケン
47	沖縄県	オキナワケン
48	オーストラリア	オーストラリア
0	その他	その他
\.


--
-- Data for Name: zoo; Type: TABLE DATA; Schema: public; Owner: vqzsyccrrgrcog
--

COPY public.zoo (zoo_id, zoo_name, prefecture_id, created_by, updated_by, created_date, updated_date) FROM stdin;
1	東山動植物園	23	\N	\N	\N	\N
2	平川動物公園	46	\N	\N	\N	\N
3	こども動物自然公園	11	\N	\N	\N	\N
4	神戸市立王子動物園	28	\N	\N	\N	\N
5	多摩動物公園	13	\N	\N	\N	\N
6	横浜市立金沢動物園	14	\N	\N	\N	\N
7	淡路ファームパーク イングランドの丘	28	\N	\N	\N	\N
8	天王寺動物園	27	\N	\N	\N	\N
9	ドリームワールド	48	\N	\N	\N	\N
10	タロンガ動物園	48	\N	\N	\N	\N
11	ヤンチャップ国立公園	48	\N	\N	\N	\N
0	その他	0	\N	\N	\N	\N
\.


--
-- Name: animal_type_animaltype_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.animal_type_animaltype_id_seq', 1, true);


--
-- Name: koala_koala_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.koala_koala_id_seq', 182, true);


--
-- Name: koala_zoo_history_koala_zoo_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.koala_zoo_history_koala_zoo_history_id_seq', 349, true);


--
-- Name: login_user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.login_user_user_id_seq', 11, true);


--
-- Name: post_favorite_post_favorite_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.post_favorite_post_favorite_id_seq', 87, true);


--
-- Name: post_image_favorite_postimagefavorite_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.post_image_favorite_postimagefavorite_id_seq', 41, true);


--
-- Name: post_image_postimage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.post_image_postimage_id_seq', 123, true);


--
-- Name: post_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.post_post_id_seq', 122, true);


--
-- Name: prefecture_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.prefecture_id_seq', 1, false);


--
-- Name: zoo_zoo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: vqzsyccrrgrcog
--

SELECT pg_catalog.setval('public.zoo_zoo_id_seq', 1, false);


--
-- Name: animal_post_tag animal_post_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_post_tag
    ADD CONSTRAINT animal_post_tag_pkey PRIMARY KEY (id);


--
-- Name: animal_type animal_type_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_type
    ADD CONSTRAINT animal_type_pkey PRIMARY KEY (animal_type_id);


--
-- Name: animal koala_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal
    ADD CONSTRAINT koala_pkey PRIMARY KEY (animal_id);


--
-- Name: animal_zoo_history koala_zoo_history_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_zoo_history
    ADD CONSTRAINT koala_zoo_history_pkey PRIMARY KEY (animal_zoo_history_id);


--
-- Name: post_favorite post_favorite_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_favorite
    ADD CONSTRAINT post_favorite_pkey PRIMARY KEY (post_favorite_id);


--
-- Name: post_image post_image_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image
    ADD CONSTRAINT post_image_pkey PRIMARY KEY (postimage_id);


--
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (post_id);


--
-- Name: post_image_favorite postimagefavorite_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image_favorite
    ADD CONSTRAINT postimagefavorite_pkey PRIMARY KEY (postimagefavorite_id);


--
-- Name: prefecture prefecture_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.prefecture
    ADD CONSTRAINT prefecture_pkey PRIMARY KEY (prefecture_id);


--
-- Name: login_user user_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.login_user
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- Name: login_user user_provider_key; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.login_user
    ADD CONSTRAINT user_provider_key UNIQUE (provider, provider_id);


--
-- Name: zoo zoo_pkey; Type: CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.zoo
    ADD CONSTRAINT zoo_pkey PRIMARY KEY (zoo_id);


--
-- Name: animal animal_animaltype_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal
    ADD CONSTRAINT animal_animaltype_id_fkey FOREIGN KEY (animal_type_id) REFERENCES public.animal_type(animal_type_id);


--
-- Name: animal_post_tag animal_post_tag_animal_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_post_tag
    ADD CONSTRAINT animal_post_tag_animal_id_fkey FOREIGN KEY (animal_id) REFERENCES public.animal(animal_id);


--
-- Name: animal_post_tag animal_post_tag_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_post_tag
    ADD CONSTRAINT animal_post_tag_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(post_id);


--
-- Name: animal_zoo_history koala_zoo_history_koala_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_zoo_history
    ADD CONSTRAINT koala_zoo_history_koala_id_fkey FOREIGN KEY (animal_id) REFERENCES public.animal(animal_id) ON DELETE CASCADE;


--
-- Name: animal_zoo_history koala_zoo_history_zoo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.animal_zoo_history
    ADD CONSTRAINT koala_zoo_history_zoo_id_fkey FOREIGN KEY (zoo_id) REFERENCES public.zoo(zoo_id) ON DELETE CASCADE;


--
-- Name: login_user login_user_favorite_zoo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.login_user
    ADD CONSTRAINT login_user_favorite_zoo_fkey FOREIGN KEY (favorite_zoo) REFERENCES public.zoo(zoo_id);


--
-- Name: post_favorite post_favorite_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_favorite
    ADD CONSTRAINT post_favorite_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(post_id) ON DELETE CASCADE;


--
-- Name: post_favorite post_favorite_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_favorite
    ADD CONSTRAINT post_favorite_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.login_user(user_id) ON DELETE CASCADE;


--
-- Name: post_image post_image_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image
    ADD CONSTRAINT post_image_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(post_id) ON DELETE CASCADE;


--
-- Name: post post_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.login_user(user_id) ON DELETE CASCADE;


--
-- Name: post post_zoo_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_zoo_id_fkey FOREIGN KEY (zoo_id) REFERENCES public.zoo(zoo_id) ON DELETE CASCADE;


--
-- Name: post_image_favorite postimagefavorite_postiamge_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image_favorite
    ADD CONSTRAINT postimagefavorite_postiamge_id_fkey FOREIGN KEY (postimage_id) REFERENCES public.post_image(postimage_id) ON DELETE CASCADE;


--
-- Name: post_image_favorite postimagefavorite_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.post_image_favorite
    ADD CONSTRAINT postimagefavorite_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.login_user(user_id) ON DELETE CASCADE;


--
-- Name: zoo zoo_zoo_prefecture_fkey; Type: FK CONSTRAINT; Schema: public; Owner: vqzsyccrrgrcog
--

ALTER TABLE ONLY public.zoo
    ADD CONSTRAINT zoo_zoo_prefecture_fkey FOREIGN KEY (prefecture_id) REFERENCES public.prefecture(prefecture_id) ON DELETE CASCADE;



--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: vqzsyccrrgrcog
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: LANGUAGE plpgsql; Type: ACL; Schema: -; Owner: postgres
--


--
-- PostgreSQL database dump complete
--

