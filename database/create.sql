create table users(
	id SERIAL,
	fname text not null,
	lname text not null,
	username text not null,
	password text not null,
	salt text,
	shared_key text,
	country text not null,
	areaycode text not null,
	city text not null,
	address text not null
);
