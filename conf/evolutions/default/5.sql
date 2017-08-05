# Users schema

# --- !Ups
alter TABLE linked_account alter provider_user_id set data type text;

# --- !Downs
alter TABLE linked_account alter provider_user_id set data type integer  USING provider_user_id::integer;;