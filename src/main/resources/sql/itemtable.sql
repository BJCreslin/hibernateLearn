create table if not exists itemtable
(
	id serial not null
		constraint itemtable_pkey
		primary key,
	code integer not null,
	name varchar not null,
	needed integer,
	central integer,
	vystavka integer,
	group_id integer,
	FOREIGN KEY (group_id) REFERENCES grouptable(id)
)
;

TRUNCATE TABLE public.itemtable
CONTINUE IDENTITY
RESTRICT
;

ALTER TABLE public.itemtable DROP CONSTRAINT itemtable_pkey
;

