-- Drop table

-- DROP TABLE public.perfil

CREATE TABLE public.perfil (
	id int8 NOT NULL,
	nome varchar(255) NULL,
	CONSTRAINT perfil_pkey PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Drop table

-- DROP TABLE public.usuario

CREATE TABLE public.usuario (
	id int8 NOT NULL,
	data_criacao timestamp NULL,
	numero_documento varchar(255) NULL,
	tipo_documento varchar(255) NULL,
	email varchar(255) NULL,
	sobrenome varchar(255) NULL,
	nome varchar(255) NULL,
	senha varchar(255) NULL,
	status varchar(255) NULL,
	data_atualizacao timestamp NULL,
	perfil_id int8 NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (id),
	CONSTRAINT fk9po12ytp6krwvwht1kmd0qgxf FOREIGN KEY (perfil_id) REFERENCES perfil(id)
)
WITH (
	OIDS=FALSE
) ;
