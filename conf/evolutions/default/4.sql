# Users schema

# --- !Ups
CREATE TABLE linked_account (
  id SERIAL PRIMARY KEY,
  user_id integer,
  provider_user_id integer,
  provider_key TEXT
);

# --- !Downs
DROP TABLE linked_account;