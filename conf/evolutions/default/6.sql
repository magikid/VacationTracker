# Users schema

# --- !Ups
alter TABLE users alter password drop not null;

# --- !Downs
alter TABLE users alter password set not null;