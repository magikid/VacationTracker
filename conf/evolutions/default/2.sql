# Users schema

# --- !Ups

ALTER TABLE Users ADD last_sign_in TIMESTAMP;
ALTER TABLE Users ADD is_hr_approved BOOLEAN;

# --- !Downs

ALTER TABLE Users DROP COLUMN last_sign_in;
ALTER TABLE Users DROP COLUMN is_hr_approved;