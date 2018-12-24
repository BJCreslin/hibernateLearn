create table if not exists grouptable
(
	id serial not null
		constraint grouptable_pkey
		primary key,
	  name varchar not null,
		logist_id integer,
	  FOREIGN KEY (logist_id) REFERENCES logisttable(id)
)
;


