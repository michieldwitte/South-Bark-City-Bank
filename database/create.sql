create table users(
	id SERIAL,
	fname text not null,
	lname text not null,
	UUID text not null,
	salt text not null,
	shared_key text not null,
	verifier_v text not null,
	country text not null,
	areaycode text not null,
	city text not null,
	address text not null,
	blocked integer not null,
	balance integer not null
);

create table usertransactions(
	id SERIAL,
	user_id_from text not null,
	user_id_to text not null,
	amount integer not null,
	date text not null,
	signature text not null,
	responsecode_from_client text not null	
);
