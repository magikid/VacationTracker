# Users schema

# --- !Ups

CREATE TABLE Users (
  id SERIAL PRIMARY KEY,
  name text NOT NULL,
  email text NOT NULL,
  password text NOT NULL,
  email_validated boolean,
  active boolean
);

# --- !Downs

DROP TABLE Users;