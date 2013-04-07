create table users(
	id SERIAL,
	fname text not null,
	lname text not null,
	UUID text not null,
	salt text not null,
	shared_key text not null,
	verifier_v text not null,
	salt_s text not null,
	country text not null,
	areaycode text not null,
	city text not null,
	address text not null
);
