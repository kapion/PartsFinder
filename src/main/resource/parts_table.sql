-- Drop table

-- DROP TABLE public.parts_table

CREATE TABLE public.parts_table (
	id serial NOT NULL,
	part_number varchar NOT NULL,
	part_name varchar NOT NULL,
	vendor varchar NULL,
	qty int4 NULL,
	shipped timestamp NULL,
	receive timestamp NULL,
	CONSTRAINT parts_table_id_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;
CREATE UNIQUE INDEX parts_table_id_uindex ON public.parts_table USING btree (id) ;
