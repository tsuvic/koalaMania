CREATE SEQUENCE public.prefecture_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.prefecture (
	prefecture_id int4 NOT NULL DEFAULT nextval('prefecture_id_seq'::regclass),
	prefecture_name text NULL,
	prefecture_name_kana text NULL,
	CONSTRAINT prefecture_pkey PRIMARY KEY (prefecture_id)
);

CREATE SEQUENCE public.zoo_zoo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.zoo (
	zoo_id serial4 NOT NULL,
	zoo_name text NOT NULL,
	prefecture_id int4 NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	CONSTRAINT zoo_pkey PRIMARY KEY (zoo_id),
	CONSTRAINT zoo_zoo_prefecture_fkey FOREIGN KEY (prefecture_id) REFERENCES public.prefecture(prefecture_id) ON DELETE CASCADE
);

CREATE SEQUENCE public.animal_type_animaltype_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.animal_type (
	animal_type_id int4 NOT NULL DEFAULT nextval('animal_type_animaltype_id_seq'::regclass),
	"name" text NOT NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	CONSTRAINT animal_type_pkey PRIMARY KEY (animal_type_id)
);

CREATE SEQUENCE public.login_user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.login_user (
	user_id serial4 NOT NULL,
	provider varchar(100) NOT NULL,
	provider_id int8 NOT NULL,
	provider_adress text NOT NULL,
	user_name text NOT NULL,
	profile text NULL,
	"role" text NOT NULL,
	status int4 NOT NULL,
	auto_login text NULL,
	login_date timestamp NOT NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	profileimagepath text NULL,
	twitterlinkflag bool NULL,
	favorite_zoo int8 NULL,
	CONSTRAINT user_pkey PRIMARY KEY (user_id),
	CONSTRAINT user_provider_key UNIQUE (provider, provider_id),
	CONSTRAINT login_user_favorite_zoo_fkey FOREIGN KEY (favorite_zoo) REFERENCES public.zoo(zoo_id)
);

CREATE SEQUENCE public.koala_koala_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.animal (
	animal_id int4 NOT NULL DEFAULT nextval('koala_koala_id_seq'::regclass),
	name text NOT NULL,
	sex int4 NOT NULL,
	birth_date date NOT NULL,
	is_alive int4 NOT NULL,
	death_date date NOT NULL,
	mother int4 NOT NULL,
	father int4 NOT NULL,
	details text NOT NULL,
	feature text NOT NULL,
	profile_image_type varchar NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	animal_type_id int4 NOT NULL DEFAULT 1,
	CONSTRAINT koala_pkey PRIMARY KEY (animal_id),
	CONSTRAINT animal_animaltype_id_fkey FOREIGN KEY (animal_type_id) REFERENCES public.animal_type(animal_type_id)
);

CREATE SEQUENCE public.post_post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.post (
	post_id serial4 NOT NULL,
	user_id int4 NOT NULL,
	zoo_id int4 NOT NULL,
	parent_id int4 NULL,
	contents text NOT NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	visit_date date NULL,
	title varchar(30) NULL,
	CONSTRAINT post_pkey PRIMARY KEY (post_id),
	CONSTRAINT post_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.login_user(user_id) ON DELETE CASCADE,
	CONSTRAINT post_zoo_id_fkey FOREIGN KEY (zoo_id) REFERENCES public.zoo(zoo_id) ON DELETE CASCADE
);

CREATE TABLE public.animal_post_tag (
	id int4 NOT NULL,
	animal_id int4 NOT NULL,
	post_id int4 NOT NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	CONSTRAINT animal_post_tag_pkey PRIMARY KEY (id),
	CONSTRAINT animal_post_tag_animal_id_fkey FOREIGN KEY (animal_id) REFERENCES public.animal(animal_id),
	CONSTRAINT animal_post_tag_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(post_id)
);

CREATE SEQUENCE public.koala_zoo_history_koala_zoo_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- public.animal_zoo_history definition

-- Drop table

-- DROP TABLE public.animal_zoo_history;

CREATE TABLE public.animal_zoo_history (
	animal_zoo_history_id int4 NOT NULL DEFAULT nextval('koala_zoo_history_koala_zoo_history_id_seq'::regclass),
	animal_id int4 NOT NULL,
	zoo_id int4 NOT NULL,
	admission_date date NOT NULL,
	exit_date date NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	CONSTRAINT koala_zoo_history_pkey PRIMARY KEY (animal_zoo_history_id),
	CONSTRAINT koala_zoo_history_koala_id_fkey FOREIGN KEY (animal_id) REFERENCES public.animal(animal_id) ON DELETE CASCADE,
	CONSTRAINT koala_zoo_history_zoo_id_fkey FOREIGN KEY (zoo_id) REFERENCES public.zoo(zoo_id) ON DELETE CASCADE
);

CREATE SEQUENCE public.post_favorite_post_favorite_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- public.post_favorite definition

-- Drop table

-- DROP TABLE public.post_favorite;

CREATE TABLE public.post_favorite (
	post_favorite_id serial4 NOT NULL,
	user_id int4 NOT NULL,
	post_id int4 NOT NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	CONSTRAINT post_favorite_pkey PRIMARY KEY (post_favorite_id),
	CONSTRAINT post_favorite_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(post_id) ON DELETE CASCADE,
	CONSTRAINT post_favorite_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.login_user(user_id) ON DELETE CASCADE
);

CREATE SEQUENCE public.post_image_postimage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
-- public.post_image definition

-- Drop table

-- DROP TABLE public.post_image;

CREATE TABLE public.post_image (
	postimage_id serial4 NOT NULL,
	post_id int4 NOT NULL,
	animal_id int4 NULL,
	image_address text NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	CONSTRAINT post_image_pkey PRIMARY KEY (postimage_id),
	CONSTRAINT post_image_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(post_id) ON DELETE CASCADE
);

CREATE SEQUENCE public.post_image_favorite_postimagefavorite_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- public.post_image_favorite definition

-- Drop table

-- DROP TABLE public.post_image_favorite;

CREATE TABLE public.post_image_favorite (
	postimagefavorite_id serial4 NOT NULL,
	user_id int4 NOT NULL,
	postimage_id int4 NOT NULL,
	created_date timestamp NULL,
	updated_date timestamp NULL,
	created_by int4 NULL,
	updated_by int4 NULL,
	CONSTRAINT postimagefavorite_pkey PRIMARY KEY (postimagefavorite_id),
	CONSTRAINT postimagefavorite_postiamge_id_fkey FOREIGN KEY (postimage_id) REFERENCES public.post_image(postimage_id) ON DELETE CASCADE,
	CONSTRAINT postimagefavorite_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.login_user(user_id) ON DELETE CASCADE
);